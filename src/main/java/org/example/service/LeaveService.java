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

    @Inject
    private LeaveDao leaveDao;

    @Inject
    private PersonnelDao personnelDao;

    public LeaveService() throws SQLException {
        this.leaveDao = new LeaveDao();
        this.personnelDao = new PersonnelDao();
    }

    public Optional<Leave> insert(Leave entity) {
        try {
            return leaveDao.save(entity);
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


}
