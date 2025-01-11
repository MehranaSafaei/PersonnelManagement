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


    public Optional<Personnel> createPersonnel(Personnel personnel) throws SQLException {
        PersonnelDao personnelDao = new PersonnelDao();
        return personnelDao.createPersonnel(personnel);
    }

    public List<Personnel> getListPersonnel() {
        PersonnelDao personnelDao = new PersonnelDao();
        return personnelDao.getAll();
    }

    public Personnel updatePersonnel(Personnel personnel) {
        PersonnelDao personnelDao = new PersonnelDao();
        return personnelDao.update(personnel);
    }

    public Personnel findPersonnelByCode(long personnelCode) {
        PersonnelDao personnelDao = new PersonnelDao();
        return personnelDao.findByPersonnelCode(personnelCode);
    }

    public Optional<Personnel> findById(long id) throws SQLException {
        PersonnelDao personnelDao = new PersonnelDao();
        return personnelDao.getById(id);
    }

    public List<Personnel> findPersonnelByName(String name) {
        PersonnelDao personnelDao = new PersonnelDao();
        return personnelDao.getByName(name);
    }
    public void deleteById(long id) {
        PersonnelDao personnelDao = new PersonnelDao();
        personnelDao.delete(id);
    }
}
