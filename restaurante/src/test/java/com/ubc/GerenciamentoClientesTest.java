package com.ubc;

import static org.junit.Assert.assertEquals;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ubc.models.Cliente;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class GerenciamentoClientesTest {

    private static EntityManagerFactory emf;
    private static EntityManager em;

    @BeforeClass
    public static void setUpClass() {
        emf = TestEntityManagerFactory.getEntityManagerFactory();
        em = emf.createEntityManager();
    }

    @AfterClass
    public static void tearDownClass() {
        em.close();
        TestEntityManagerFactory.closeEntityManagerFactory();
    }

    @Test
    public void testDatabaseSupports100Records() {
        try {
            em.getTransaction().begin();
            long countInicial = (long) em.createQuery("SELECT COUNT(c) FROM Cliente c").getSingleResult();
            for (int i = 0; i < 100; i++) {
                Cliente cliente = new Cliente();
                cliente.setNome("Teste");
                // Set the date of registration to the current date for simplicity
                cliente.setDataDeCadastro(java.time.LocalDate.now());
                em.persist(cliente);
            }
            em.getTransaction().commit();

            // Verify if 100 Cliente objects are persisted
            long count = (long) em.createQuery("SELECT COUNT(c) FROM Cliente c").getSingleResult();
            assertEquals(countInicial + 100, count);
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
        
        em.getTransaction().begin();
        em.createQuery("DELETE FROM Cliente c WHERE c.Nome = 'Teste'").executeUpdate();
        em.getTransaction().commit();


    }
}
