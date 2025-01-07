package org.example.dao;

import org.example.connection.SimpleConnectionPool;
import org.example.entity.Leave;
import org.example.entity.LeaveStatus;
import org.example.entity.Personnel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LeaveDao {

    private static final Logger logger = LoggerFactory.getLogger(LeaveDao.class);

    // SQL Queries
    private static final String INSERT = "INSERT INTO leaves (startDate, endDate, description, personnel_id, loginTime, status) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE leaves SET startDate = ?, endDate = ?, description = ?, personnel_id = ?, status = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM leaves WHERE id = ?";
    private static final String SOFT_DELETE = "UPDATE leaves SET isDeleted = TRUE WHERE id = ?";
    private static final String SELECT_BY_ID = "SELECT * FROM leaves WHERE id = ?";
    private static final String SELECT_ALL = "SELECT * FROM leaves WHERE isDeleted = FALSE";
    private static final String SELECT_BY_PERSONNEL_ID = "SELECT * FROM leaves WHERE personnel_id = ? AND isDeleted = FALSE";
    private static final String FIND_ACTIVE_LEAVES_WITH_DATE_RANGE = "SELECT * FROM leaves WHERE startDate <= ? AND endDate >= ? AND isDeleted = FALSE";
    private static final String SEARCH_QUERY = "SELECT * FROM leaves WHERE isDeleted = FALSE AND " +
            "(description LIKE ? OR status = ? OR startDate >= ? OR endDate <= ?)";

    // Constructor
    public LeaveDao() throws SQLException {
        try {
            new SimpleConnectionPool(); // Initialize connection pool
        } catch (SQLException e) {
            logger.error("Failed to initialize connection pool", e);
            throw new ExceptionInInitializerError("Failed to initialize connection pool: " + e.getMessage());
        }
    }

    // Insert a new leave
    public Optional<Leave> insert(Leave entity) throws SQLException {
        if (entity.getPersonnel().getId() == null) {
            throw new IllegalArgumentException("Personnel ID cannot be null.");
        }

        try (Connection connection = SimpleConnectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, Date.valueOf(entity.getStartDate()));
            ps.setDate(2, Date.valueOf(entity.getEndDate()));
            ps.setString(3, entity.getDescription());
            ps.setLong(4, entity.getPersonnel().getId());
            ps.setTimestamp(5, entity.getLoginTime() != null ? Timestamp.valueOf(entity.getLoginTime()) : null);
            ps.setString(6, entity.getStatus() != null ? entity.getStatus().name() : null);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        entity.setId(rs.getLong(1));
                    }
                }
                logger.info("Inserted leave: {}", entity);
            }
        } catch (SQLException e) {
            logger.error("Error inserting leave: {}", entity, e);
            throw e;
        }
        return Optional.of(entity);
    }

    // Soft delete a leave
    public void softDelete(Long id) {
        try (Connection connection = SimpleConnectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(SOFT_DELETE)) {

            ps.setLong(1, id);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Soft deleted leave with id: {}", id);
            }
        } catch (SQLException e) {
            logger.error("Error soft deleting leave with id: {}", id, e);
        }
    }

    // Advanced search
    public List<Leave> search(String description, LeaveStatus status, LocalDate startDate, LocalDate endDate) {
        List<Leave> leaveList = new ArrayList<>();
        try (Connection connection = SimpleConnectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(SEARCH_QUERY)) {

            ps.setString(1, description != null ? "%" + description + "%" : "%");
            ps.setString(2, status != null ? status.name() : null);
            ps.setDate(3, startDate != null ? Date.valueOf(startDate) : null);
            ps.setDate(4, endDate != null ? Date.valueOf(endDate) : null);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    leaveList.add(mapResultSetToLeave(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Error performing search with criteria - description: {}, status: {}, startDate: {}, endDate: {}",
                    description, status, startDate, endDate, e);
        }
        return leaveList;
    }

    // Paginated findAll
    public List<Leave> findAllPaginated(int page, int size) {
        List<Leave> leaveList = new ArrayList<>();
        String paginatedQuery = SELECT_ALL + " LIMIT ? OFFSET ?";
        try (Connection connection = SimpleConnectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(paginatedQuery)) {

            ps.setInt(1, size);
            ps.setInt(2, page * size);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    leaveList.add(mapResultSetToLeave(rs));
                }
            }
        } catch (SQLException e) {
            logger.error("Error fetching paginated leaves - page: {}, size: {}", page, size, e);
        }
        return leaveList;
    }

    // Map ResultSet to Leave object
    private Leave mapResultSetToLeave(ResultSet rs) throws SQLException {
        LocalDate startDate = rs.getDate("startDate").toLocalDate();
        LocalDate endDate = rs.getDate("endDate").toLocalDate();
        LocalDateTime loginTime = rs.getTimestamp("loginTime") != null ? rs.getTimestamp("loginTime").toLocalDateTime() : null;
        LeaveStatus status = rs.getString("status") != null ? LeaveStatus.valueOf(rs.getString("status")) : null;

        Personnel personnel = new Personnel();
        personnel.setId(rs.getLong("personnel_id"));

        return new Leave(
                rs.getLong("id"),
                startDate,
                endDate,
                rs.getString("description"),
                status,
                loginTime,
                personnel
        );
    }
}
