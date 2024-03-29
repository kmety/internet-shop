package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;

public abstract class AbstractDao<T> {
    protected final Connection connection;

    public AbstractDao(Connection connection) {
        this.connection = connection;
    }
}
