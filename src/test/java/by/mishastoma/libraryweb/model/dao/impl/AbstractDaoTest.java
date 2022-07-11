package by.mishastoma.libraryweb.model.dao.impl;

import by.mishastoma.libraryweb.controller.pool.ConnectionPool;
import ch.vorburger.exec.ManagedProcessException;
import ch.vorburger.mariadb4j.DB;
import org.testng.annotations.BeforeTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public abstract class AbstractDaoTest {
    @BeforeTest
    public void dataBaseCreation() throws ManagedProcessException, FileNotFoundException {
        DB database = DB.newEmbeddedDB(9000);
        var is = new FileInputStream("src/test/resources/test_script.sql");
        database.start();
        database.source(is);
        ConnectionPool.getInstance();
    }
}
