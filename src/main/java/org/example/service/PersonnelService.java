package org.example.service;


import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import org.example.dao.PersonnelDao;
import org.example.entity.Personnel;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
@Stateless
public class PersonnelService {

    @Inject
    private PersonnelDao personnelDao;

    public PersonnelService() throws SQLException {
    }

    public Optional<Personnel> insert(Personnel personnel) throws SQLException {
        if (personnel != null && personnel.getId() != null && personnel.getId() > 0 && personnel.getId().describeConstable().isEmpty()){
            personnelDao.insert(personnel);
        }else {
            return Optional.empty();
        }
        return Optional.of(personnel);
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
