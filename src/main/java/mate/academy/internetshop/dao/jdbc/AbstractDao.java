package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;

public class AbstractDao<T> {
    protected static String DB_NAME = "internetshop";
    protected final Connection connection;

    public AbstractDao(Connection connection) {
        this.connection = connection;
    }
}
