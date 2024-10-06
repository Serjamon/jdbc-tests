package office;

import java.sql.*;
import java.util.Scanner;

public enum Option {

    AddEmployee {
        String getText() {
            return this.ordinal() + ".Добавить сотрудника";
        }

        void action() throws SQLException {
            System.out.println("Введите его id:");
            int id=sc.nextInt();
            System.out.println("Введите его имя:");
            String name=sc.next();
            System.out.println("Введите id отдела:");
            int depid=sc.nextInt();
            Connection con = dbCon.getConnection();
            Service.addEmployee(new Employee(id,name,depid), con);
            con.close();
        }
    },
    DeleteEmployee {
        String getText() {
            return this.ordinal() + ".Удалить сотрудника";
        }

        void action() throws SQLException {
            System.out.println("Введите его id:");
            int id=sc.nextInt();
            Connection con = dbCon.getConnection();
            Service.removeEmployee(new Employee(id,"",0), con);
            con.close();
        }
    },
    AddDepartment {
        String getText() {
            return this.ordinal() + ".Добавить отдел";
        }

        void action() throws SQLException {
            System.out.println("Введите его id:");
            int id=sc.nextInt();
            System.out.println("Введите его название:");
            String name=sc.next();
            Connection con = dbCon.getConnection();
            Service.addDepartment(new Department(id,name), con);
            con.close();
        }
    },
    DeleteDepartment {
        String getText() {
            return this.ordinal() + ".Удалить отдел";
        }

        void action() throws SQLException {
            System.out.println("Введите его id:");
            int id=sc.nextInt();
            Connection con = dbCon.getConnection();
            Service.removeDepartment(new Department(id,""), con);
            con.close();
        }
    },
    CLEAR_DB {
        String getText() {
            return this.ordinal() + ".Сбросить базу данных";
        }

        void action() throws SQLException {
            Connection con = dbCon.getConnection();
            Service.createDB(con);
            con.close();
        }

    },
    PRINT_DEPS {
        String getText() {
            return this.ordinal() + ".Вывести на экран все отделы";
        }

        void action() {
            try(Connection con = DriverManager.getConnection("jdbc:h2:.\\Office")){
                PreparedStatement stm = con.prepareStatement(
                        "Select ID, NAME as txt from Department where name like ?",
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_UPDATABLE
                );
                String str="A%";
                //ResultSet rs= stm.executeQuery("Select ID, NAME as txt from Department");
                stm.setString(1,str);
                ResultSet rs=stm.executeQuery();
                System.out.println("------------------------------------");
                while(rs.next()){
                    System.out.println(rs.getInt("ID")+"\t"+rs.getString("name"));
                }
                System.out.println("------------------------------------");
            }catch (SQLException e) {
                System.out.println(e);
            }
        }
    },
    PRINT_EMPLOYEES {
        String getText() {
            return this.ordinal() + ".Вывести на экран всех сотрудников";
        }

        void action() {
            try(Connection con = DriverManager.getConnection("jdbc:h2:.\\Office")){
                Statement stm = con.createStatement();
                ResultSet rs= stm.executeQuery("Select Employee.ID, Employee.Name,Department.Name as DepName from Employee join Department on Employee.DepartmentID=Department.ID");
                //ResultSet rs= stm.executeQuery("Select Employee.ID, Employee.Name,Employee.DepartmentID as DepName from Employee");
                System.out.println("------------------------------------");
                ResultSetMetaData metaData= rs.getMetaData();
                while(rs.next()){
                    System.out.println(rs.getInt("ID")+"\t"+rs.getString("NAME")+"\t"+rs.getString("DepName"));
                }
                System.out.println("------------------------------------");
            }catch (SQLException e) {
                System.out.println(e);
            }
        }   
    },
    EXIT {
        String getText() {
            return this.ordinal() + ".Выход";
        }

        void action() {
            System.out.println("выход");
        }
    },;


    private static DBConnection dbCon = new DBConnection("jdbc:h2:.\\Office");
    Scanner sc = new Scanner(System.in);
    abstract String getText();
    abstract void action() throws SQLException;
}
