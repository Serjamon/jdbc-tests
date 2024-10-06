package office;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

import static org.junit.jupiter.api.Assertions.*;

@Nested
class ServiceTest {

    private DBConnection dbCon = new DBConnection("jdbc:h2:.\\OfficeTest");

    @Test
    @SneakyThrows
    void createDB() {

        //проверим на созданное кол-во И на исключения при выполнении селектов, если будут
        Connection con = dbCon.getConnection();

        Service.createDB(con);
        int emplCount = countEmpls(con);
        int deptCount = countDepts(con);

        Assertions.assertEquals(5, emplCount);
        Assertions.assertEquals(3, deptCount);

        dbCon.closeConnection(con);

    }

    @Test
    @SneakyThrows
    void removeDepartment() {

        Department testDept = new Department(-1, "Test dept");
        Connection con = dbCon.getConnection();

        Service.createDB(con);
        createTestDept(con);
        Service.removeDepartment(testDept, con);

        int emplCount = countEmpls(con);
        Assertions.assertEquals(5, emplCount);

        dbCon.closeConnection(con);

    }

    @SneakyThrows
    private static void createTestDept(Connection con){

        //Не используем методы Service, чтобы тест был независимым
        //Кроме createDB, без нее не обойтись!
        String departmentInsert = "INSERT INTO Department (ID, Name) VALUES (?, ?)";
        PreparedStatement preparedStatement = con.prepareStatement(departmentInsert);
        preparedStatement.setInt(1, -1);
        preparedStatement.setString(2, "Test dept");
        preparedStatement.executeUpdate();

        String employeeInsert = "INSERT INTO Employee (ID, Name, DepartmentID) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement2 = con.prepareStatement(employeeInsert);
        preparedStatement2.setInt(1, -1);
        preparedStatement2.setString(2, "Vasya");
        preparedStatement2.setInt(3, -1);
        preparedStatement2.executeUpdate();

        PreparedStatement preparedStatement3 = con.prepareStatement(employeeInsert);
        preparedStatement3.setInt(1, -2);
        preparedStatement3.setString(2, "Petya");
        preparedStatement3.setInt(3, -1);
        preparedStatement3.executeUpdate();

    }

    @SneakyThrows
    private static int countEmpls(Connection con) {

        String query = "SELECT COUNT(*) FROM Employee";

        PreparedStatement preparedStatement = con.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }

        return -1;//>0 это у нас рабочие значения
    }

    @SneakyThrows
    private static int countDepts(Connection con) {

        String query = "SELECT COUNT(*) FROM Department";

        PreparedStatement preparedStatement = con.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }

        return -1;//0 у нас это ОК
    }



}