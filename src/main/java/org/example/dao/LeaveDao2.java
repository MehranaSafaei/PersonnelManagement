package org.example.dao;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class LeaveDao2 {

    @PersistenceContext(unitName = "LeaveUp")
    private EntityManager entityManager;

}
