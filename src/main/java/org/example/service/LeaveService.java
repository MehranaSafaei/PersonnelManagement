package org.example.service;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.example.dao.LeaveDao;
import org.example.dao.PersonnelDao;
import org.example.entity.Leave;
import org.example.entity.Personnel;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Named("leaveService")
public class LeaveService {


    public Optional<Leave> insert(Leave entity) throws SQLException {
        LeaveDao leaveDao = new LeaveDao();
        try {
            return leaveDao.save(entity);
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting leave: " + e.getMessage(), e);
        }
    }

    public List<Leave> findAll() throws SQLException {
        LeaveDao leaveDao = new LeaveDao();
        try {
            return leaveDao.findAll();
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching all leaves: " + e.getMessage(), e);
        }
    }

    public List<Leave> findLeaveByPersonnelId(Long personnelId) throws SQLException {
        LeaveDao leaveDao = new LeaveDao();
        try {
        return leaveDao.findLeaveByPersonnelId(personnelId);    } catch (SQLException e) {
        throw new RuntimeException("Error fetching leaves by personnel ID: " + e.getMessage(), e);    }
    }

}
