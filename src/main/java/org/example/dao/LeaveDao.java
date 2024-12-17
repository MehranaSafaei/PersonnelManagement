package org.example.dao;


import org.example.connection.SimpleConnectionPool;
import org.example.entity.Leave;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class LeaveDao {
/*انیار قرداد نقلیه */

//    private String INSERT = "INSERT INTO Leaves (startDate, endDate, description, personelId) VALUES (?, ?, ?, ?)";
//    private String SELECT_BY_USERNAME = "SELECT * FROM Leaves WHERE username = ?";
//    private static final String SELECT_ALL = "SELECT * FROM Leaves";
//    private static final String SELECT_BY_ID = "SELECT * FROM Leaves WHERE id = ?";

    private static final String INSERT = "INSERT INTO leave (startDate, endDate, description, personnelId, loginTime) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE leave SET startDate = ?, endDate = ?, description = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM leave WHERE id = ?";
    private static final String SELECT_BY_USERNAME = "SELECT * FROM leave WHERE username = ?";
    private static final String SELECT_ALL = "SELECT * FROM leave";
    private static final String SELECT_BY_ID = "SELECT * FROM leave WHERE id = ?";
    private static final String SELECT_BY_PERSONNEL_ID = "SELECT * FROM leave WHERE personnelId = ?";


    public LeaveDao() throws SQLException {
        try {
            SimpleConnectionPool connectionPool = new SimpleConnectionPool();
        } catch (SQLException e) {
            throw new ExceptionInInitializerError("Failed to initialize org.example.connection pool: " + e.getMessage());
        }
    }


    public Optional<Leave> insert(Leave entity) throws SQLException {
        if (entity.getPersonnelId() == null) {
            throw new IllegalArgumentException("Personnel ID cannot be null.");
        }

        try (Connection connection = SimpleConnectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, new java.sql.Date(entity.getStartDate().getTime()));
            ps.setDate(2, new java.sql.Date(entity.getEndDate().getTime()));
            ps.setString(3, entity.getDescription());
            ps.setLong(4, entity.getPersonnelId()); // Set the employee ID value directly
            ps.setDate(5, Date.valueOf(entity.getLoginTime().toLocalDate()));

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
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID);) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
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
        try (Connection connection = SimpleConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL);
        ) {
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
            preparedStatement.setDate(2, new java.sql.Date(entity.getStartDate().getTime()));
            preparedStatement.setDate(3, new java.sql.Date(entity.getEndDate().getTime()));
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(entity);
            }
        }
        return Optional.empty();
    }

    public void delete(Long id) {
        try (Connection connection = SimpleConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE)) {

            statement.setLong(1, id); // Set the ID parameter

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Leave> selectByUsername(String username) throws SQLException {
        List<Leave> leaves = new ArrayList<>();
        try (Connection connection = SimpleConnectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_BY_USERNAME)) {
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
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_PERSONNEL_ID)) {

            statement.setLong(1, personnelCode);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Leave leave = new Leave();
                    leave.setId(resultSet.getInt("id"));
                    leave.setPersonnelId(resultSet.getLong("personnelCode"));
                    leave.setDescription(resultSet.getString("description"));
                    leave.setStartDate(resultSet.getDate("startDate"));
                    leave.setEndDate(resultSet.getDate("endDate"));
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

        try (Connection connection = SimpleConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_PERSONNEL_ID)) {

            statement.setLong(1, personnelId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Leave leave = new Leave();
                    leave.setId(resultSet.getInt("id"));
                    leave.setPersonnelId(resultSet.getLong("personnelId")); // Use personnelId instead of personnelCode
                    leave.setDescription(resultSet.getString("description"));
                    leave.setStartDate(resultSet.getDate("startDate"));
                    leave.setEndDate(resultSet.getDate("endDate"));
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
        return new Leave(
                resultSet.getLong("id"),
                resultSet.getDate("startDate"),
                resultSet.getDate("endDate"),
                resultSet.getString("description"),
                resultSet.getLong("personnelId")
        );
    }

}

