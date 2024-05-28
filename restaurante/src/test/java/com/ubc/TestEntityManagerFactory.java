package com.ubc;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class TestEntityManagerFactory {

    private static EntityManagerFactory emf;

    public static EntityManagerFactory getEntityManagerFactory() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("RestauranteUBC"); // "testPU" is the persistence unit name for testing
        }
        return emf;
    }

    public static void closeEntityManagerFactory() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
