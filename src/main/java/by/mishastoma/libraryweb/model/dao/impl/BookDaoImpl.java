package by.mishastoma.libraryweb.model.dao.impl;

public class BookDaoImpl {

    private static BookDaoImpl instance = new BookDaoImpl();

    private BookDaoImpl() {

    }

    public static BookDaoImpl getInstance() {
        return instance;
    }
}
