
package kh.com.kshrd.view;

import java.io.File;
import java.util.Scanner;
import kh.com.kshrd.dbutility.ConfigureIO;
import kh.com.kshrd.dbutility.DbConfiguration;

public class SetConfiguration {
    
    Scanner sc = new Scanner(System.in);
    File config = new File("conf"+File.separator+"PostgresConf.dk");
    ConfigureIO conio = new ConfigureIO();
    DbConfiguration dbCon;
    public void setConfigure()
    {
        if(!config.exists())
        {
            dbCon = new DbConfiguration();
            System.out.println("Enter Port: ");
            dbCon.setPort_number(sc.nextInt());
            System.out.println("Enter Database Name: ");
            sc.nextLine();
            dbCon.setDatabase_name(sc.nextLine());
            System.out.println("Enter User name: ");
            dbCon.setUsername(sc.nextLine());
            System.out.println("Enter Password: ");
            dbCon.setPassword(sc.nextLine());
            conio.saveConfigure(dbCon);
        }
    }
}
