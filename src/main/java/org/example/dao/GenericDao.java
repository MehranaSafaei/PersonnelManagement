package org.example.dao;
/* package com.mehrana.test.dao;
=======
 /* package com.mehrana.test.dao;
>>>>>>> 33eb8d3f7046fe029b62c1cb1efdadb817d245ab

import com.mehrana.test.connection.SimpleConnectionPool;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public abstract class GenericDao<T> {

   // constructor            System.out.println();

   public GenericDao() {
       try {
           SimpleConnectionPool connectionPool = new SimpleConnectionPool(); // create connection pool
       } catch (SQLException e) {
           throw new ExceptionInInitializerError("Failed to initialize com.mehrana.test.connection pool: " + e.getMessage());
       }
   }


   public abstract Optional<T> insert(T entity) throws SQLException;

//    public abstract Optional<Personnel> getById(long id) throws SQLException;

<<<<<<< HEAD
   public abstract Optional<T> getById(long id);

   public abstract List<T> getAll();

   public abstract T update(T entity);
=======
    public abstract Optional<T> getById(long id);

    public abstract List<T> getAll();
>>>>>>> 33eb8d3f7046fe029b62c1cb1efdadb817d245ab

   public abstract void delete(Long id);

<<<<<<< HEAD
}*/
