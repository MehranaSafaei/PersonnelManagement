package org.example.dao;


import org.example.connection.SimpleConnectionPool;
import org.example.entity.Leave;
import org.example.entity.LeaveStatus;
import org.example.entity.Personnel;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class LeaveDao {
    /*انیار قرداد نقلیه */

    private static final String INSERT = "INSERT INTO leave (userName, mobile, personnel_code, email, status) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE leave SET userName = ?, mobile = ?, personnel_code = ?, email = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM leave WHERE id = ?";
    private static final String SELECT_BY_ID = "SELECT * FROM leaves WHERE id = ?";
    private static final String SELECT_BY_USERNAME = "SELECT * FROM leave WHERE username = ?";
    private static final String SELECT_ALL = "SELECT * FROM leave";
    private static final String SELECT_BY_PERSONNEL_CODE = "SELECT * FROM leave WHERE personnel_code = ?";

    public LeaveDao() throws SQLException {
        try {
            SimpleConnectionPool connectionPool = new SimpleConnectionPool();
        } catch (SQLException e) {
            throw new ExceptionInInitializerError("Failed to initialize org.example.connection pool: " + e.getMessage());
        }
    }


    public Optional<Leave> save(Leave entity) throws SQLException {
        if (entity.getPersonnel().getId() == null) {
            throw new IllegalArgumentException("Personnel ID cannot be null.");
        }

        try (Connection connection = SimpleConnectionPool.getConnection(); PreparedStatement ps = connection.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, Date.valueOf(entity.getStartDate()));
            ps.setDate(2, Date.valueOf(entity.getEndDate()));
            ps.setLong(3, entity.getPersonnel().getId());
            ps.setString(4, entity.getDescription());
//            ps.setLong(4, entity.getPersonnelId()); // Set the employee ID value directly
            ps.setDate(5, Date.valueOf(entity.getLoginTime().toLocalDate()));
            ps.setString(6, entity.getLeaveStatus().toString());
            ps.setLong(7, entity.getPersonnel().getId());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    entity.setId(rs.getInt(1));
                }
            }
        }
        return Optional.of(entity);
    }


    public Optional<Leave> getById(long id) {
        try (Connection connection = SimpleConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Leave leave = mapResultSetToPersonnel(resultSet);
                return Optional.of(leave);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }


    public List<Leave> findAll() throws SQLException {
        List<Leave> leaveList = new ArrayList<>();
        try (Connection connection = SimpleConnectionPool.getConnection(); PreparedStatement statement = connection.prepareStatement(SELECT_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Leave leave = mapResultSetToPersonnel(resultSet);
                leaveList.add(leave);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return leaveList;
    }


    public List<Leave> getByName(String name) {
        return List.of();
    }

    public Optional<Leave> update(Leave entity) throws SQLException {
        try (Connection connection = SimpleConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {

            preparedStatement.setString(1, entity.getDescription());
            preparedStatement.setDate(2, Date.valueOf(entity.getStartDate()));
            preparedStatement.setDate(3, Date.valueOf(entity.getEndDate()));

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                return Optional.of(entity);
            }
        }
        return Optional.empty();
    }


    public void delete(Long id) {
        try (Connection connection = SimpleConnectionPool.getConnection(); PreparedStatement statement = connection.prepareStatement(DELETE)) {

            statement.setLong(1, id); // Set the ID parameter

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Leave> selectByUsername(String username) throws SQLException {
        List<Leave> leaves = new ArrayList<>();
        try (Connection connection = SimpleConnectionPool.getConnection(); PreparedStatement ps = connection.prepareStatement(SELECT_BY_USERNAME)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Leave leave = mapResultSetToPersonnel(rs);
            }
        }
        return leaves;
    }

    public List<Leave> findLeaveByPersonnelCode(Long personnelCode) throws SQLException {
        List<Leave> leaveList = new ArrayList<>();

        try (Connection connection = SimpleConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_PERSONNEL_CODE)) {

            statement.setLong(1, personnelCode);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Leave leave = mapResultSetToPersonnel(resultSet);
                    leaveList.add(leave);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error fetching leaves information for personnelCode: " + personnelCode, e);
        }

        return leaveList;
    }



    public List<Leave> findLeaveByPersonnelId(Long personnelId) throws SQLException {
        List<Leave> leaveList = new ArrayList<>();

        try (Connection connection = SimpleConnectionPool.getConnection(); PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {

            statement.setLong(1, personnelId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Leave leave = new Leave();
                    leave.setId(resultSet.getInt("id"));
//                    leave.setPersonnelId(resultSet.getLong("personnelId"));
                    leave.setDescription(resultSet.getString("description"));
                    leave.setStartDate(resultSet.getDate("startDate").toLocalDate());
                    leave.setEndDate(resultSet.getDate("endDate").toLocalDate());
                    Personnel personnel = new Personnel();
                    personnel.setId(resultSet.getLong("personnelId"));
                    leave.setPersonnel(personnel);

                    leaveList.add(leave);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error fetching leave information for personnelId: " + personnelId, e);
        }

        return leaveList;
    }


    private Leave mapResultSetToPersonnel(ResultSet resultSet) throws SQLException {
        LocalDate startDate = resultSet.getDate("startDate").toLocalDate();
        LocalDate endDate = resultSet.getDate("endDate").toLocalDate();

        Timestamp loginTimeTimestamp = resultSet.getTimestamp("loginTime");
        LocalDateTime loginTime = (loginTimeTimestamp != null) ? loginTimeTimestamp.toLocalDateTime() : null;

        String statusString = resultSet.getString("status");
        LeaveStatus status = (statusString != null) ? LeaveStatus.valueOf(statusString) : null;

        Long personnelId = resultSet.getLong("personnelId");
        Personnel personnel = null;
        if (personnelId != null) {
            personnel = new Personnel();
            personnel.setId(personnelId);
        }
        return new Leave(
                resultSet.getLong("id"),
                startDate,
                endDate,
                resultSet.getString("description"),
                status,
                loginTime,
                personnel
        );
    }
 /*public List<Leave> findLeaveByPersonnelCode(Long personnelCode) throws SQLException {
        List<Leave> leaveList = new ArrayList<>();

        try (Connection connection = SimpleConnectionPool.getConnection(); PreparedStatement statement = connection.prepareStatement(SELECT_BY_PERSONNEL_CODE)) {

            statement.setLong(1, personnelCode);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Leave leave = new Leave();
                    leave.setId(resultSet.getInt("id"));
//                    leave.setPersonnelId(resultSet.getLong("personnelCode"));
                    leave.setDescription(resultSet.getString("description"));
                    leave.setStartDate(resultSet.getDate("startDate").toLocalDate());
                    leave.setEndDate(resultSet.getDate("endDate").toLocalDate());
                    Personnel personnel = new Personnel();
                    personnel.setId(resultSet.getLong("personnel_id"));
                    leave.setPersonnel(personnel);

                    leaveList.add(leave);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error fetching leaves information for personnelCode: " + personnelCode, e);
        }

        return leaveList;
    }*/


}

