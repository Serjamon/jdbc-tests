package office;

import java.sql.SQLException;
import java.util.Scanner;

public class Office {

    public static void main(String[] args) throws SQLException {
        Option opt=Option.AddDepartment;
        Scanner sc = new Scanner(System.in);
        
        while(!opt.equals(Option.EXIT)){
            System.out.println("Введите число:");
            for(Option o: Option.values()) System.out.println(o.getText());
            opt=Option.values()[sc.nextInt()];    
            opt.action();
        }        
    }
}
