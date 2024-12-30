package org.example.service;

import jakarta.inject.Inject;
import org.example.dao.LeaveDao;
import org.example.dao.PersonnelDao;
import org.example.entity.Leave;
import org.example.entity.Personnel;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class LeaveService {

    @Inject
    LeaveDao leaveDao;

    @Inject
    PersonnelDao personnelDao;

    public LeaveService() {
    }

    public Optional<Leave> insert(Leave entity) {
        try {
            return leaveDao.insert(entity);
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting leave: " + e.getMessage(), e);
        }
    }

    public List<Leave> findAll() {
        try {
            return leaveDao.findAll();
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching all leaves: " + e.getMessage(), e);
        }
    }

    public void addLeaveByPersonnelCode(long personnelCode, Leave leave) {
        try {
            Personnel personnel = personnelDao.findByPersonnelCode(personnelCode);
            if (personnel == null) {
                throw new IllegalArgumentException("Personnel with code " + personnelCode + " not found");
            }
            leave.setPersonnel(personnel);
            leaveDao.insert(leave);
        } catch (SQLException e) {
            throw new RuntimeException("Error adding leave for personnel: " + e.getMessage(), e);
        }
    }

    public List<Leave> findLeavesByPersonnelCode(Long personnelCode) {
        try {
            return leaveDao.findLeaveByPersonnelCode(personnelCode);
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching leaves by personnel code: " + e.getMessage(), e);
        }
    }

    public List<Leave> findLeaveByUsername(String username) {
        return leaveDao.getByName(username);
    }

    public List<Leave> findLeaveByPersonnelId(Long personnelId) {
        try {
            return leaveDao.findLeaveByPersonnelId(personnelId);
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching leaves by personnel ID: " + e.getMessage(), e);
        }
    }
}
