package office;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection implements DBConnectionRepository{

    private final String url;

    public DBConnection(String url) {
        this.url = url;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }

    @Override
    public void closeConnection(Connection connection) throws SQLException {
        connection.close();
    }
}
