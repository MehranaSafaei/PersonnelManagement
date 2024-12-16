package org.example.service;



import org.example.dao.PersonnelDao;
import org.example.entity.Personnel;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class PersonnelService {
   PersonnelDao personnelDao = new PersonnelDao();

    public PersonnelService() throws SQLException {
    }

    public Optional<Personnel> insert(Personnel personnel) throws SQLException {
       return personnelDao.insert(personnel);
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

    public  List<Personnel>  findPersonnelByName(String name) {
        return personnelDao.getByName(name);
    }

    public void deleteByPersonnelCode(long personnelCode) {
        personnelDao.delete(personnelCode);
    }
}
