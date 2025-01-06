package org.example.service;


import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.example.dao.PersonnelDao;
import org.example.entity.Personnel;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class PersonnelService {

    @Inject
    private PersonnelDao personnelDao;

    public PersonnelService() throws SQLException {
        this.personnelDao = new PersonnelDao();
    }

    public Optional<Personnel> createPersonnel(Personnel personnel) throws SQLException {
           return personnelDao.createPersonnel(personnel);
    }

    public List<Personnel> getListPersonnel() {
        return personnelDao.getAll();
    }

    public Personnel updatePersonnel(Personnel personnel) {
        return personnelDao.update(personnel);
    }

    public Personnel findPersonnelByCode(long personnelCode) {
        return personnelDao.findByPersonnelCode(personnelCode);
    }

    public Optional<Personnel> findById(long id) throws SQLException {
        return personnelDao.getById(id);
    }

    public void deleteById(long id) {
        personnelDao.delete(id);
    }

    public List<Personnel> findPersonnelByName(String name) {
        return personnelDao.getByName(name);
    }

    public void deleteByPersonnelCode(long personnelCode) {
        personnelDao.delete(personnelCode);
    }
}
