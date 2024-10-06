package office;

import java.sql.Connection;
import java.sql.SQLException;

public interface DBConnectionRepository {

    Connection getConnection() throws SQLException;
    void closeConnection(Connection connection) throws SQLException;

}
