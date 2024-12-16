package org.example.service;



import org.example.dao.LeaveDao;
import org.example.dao.PersonnelDao;
import org.example.entity.Leave;
import org.example.entity.Personnel;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class LeaveService {


    LeaveDao leaveDao = new LeaveDao();
    PersonnelDao personnelDao = new PersonnelDao();

    public LeaveService() throws SQLException {

    }

    public Optional<Leave> insert(Leave entity) throws SQLException {
        return leaveDao.insert(entity);
    }
 /*   public Optional<Leave> findById(int id) throws SQLException {
        return leaveDao.findById(id);
    }*/

    public List<Leave> findAll() throws SQLException {
        return leaveDao.findAll();
    }


    public void addLeaveByPersonnelCode(long personnelCode, Leave leave) throws SQLException {
        Personnel personnel = personnelDao.findByPersonnelCode(personnelCode);
        leave.setPersonnelId(personnel.getId());
        leaveDao.insert(leave);
    }

    public List<Leave> findLeavesByPersonnelCode(Long personnelCode) throws SQLException {
        return leaveDao.findLeaveByPersonnelCode(personnelCode);
    }

    public List<Leave> findLeaveByUsername(String username) throws SQLException {
        return leaveDao.getByName(username);
    }

    public List<Leave> findLeaveByPersonnelId(Long personnelId) throws SQLException {
        return leaveDao.findLeaveByPersonnelId(personnelId);
    }
}
/*
package com.mehrana.test.service;

import com.mehrana.test.dao.LeaveDao;
import com.mehrana.test.entity.Leave;

import java.sql.SQLException;
import java.util.Optional;

public class LeaveService {


    LeaveDao leaveDao = new LeaveDao();

    public LeaveService() throws SQLException {

    }

    public Optional<Leave> insert(Leave entity) throws SQLException {
        return leaveDao.insert(entity);
    }

    public Optional<Object> update(Optional<Leave> entity) throws SQLException {
        return leaveDao.update(entity);
    }


    public Optional<Leave> findLeaveByPersonnelId(Long id) throws SQLException {
        return leaveDao.findLeaveByPersonnelCode(id);
    }
}
*/
