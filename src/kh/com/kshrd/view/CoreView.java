
package kh.com.kshrd.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import kh.com.kshrd.model.Product;

public class CoreView {
    DrawTable dt = new DrawTable();
    BufferedReader read =  new BufferedReader(new InputStreamReader(System.in));
    
    public void readByName(ArrayList<Product> stock)
    {
            if(stock.size() > 0)
            {
                dt.SearchView(stock);
            }
            else
            {
                System.out.println("This Product Not Found!");
            } 
    }
    
    public void readById(Product pro)
    {
         if(pro!=null)
            {
                System.out.println("Product ID: " + pro.getpId());
                System.out.println("Product Name: " + pro.getpName());
                System.out.format("Product Unit Price: %6.2f $%n" , pro.getpUnitPrice());
                System.out.println("Unit available In Stock: " + pro.getpStockQty());
                System.out.println("Imported Date: " + pro.getpImportDate());
            }
            else
            {
                System.out.println("This Product Not Found!");
            }
    }
    
    public void insetMessage()
    {
        System.out.println("Add new record succesfully!");
    }
    
    public String insertRecord()
    {
        String op = null;
        try
        {
            System.out.println("Are you sure to insert this record [y/n] : ");
            op = read.readLine();
        }
        catch (IOException ex) {
            Logger.getLogger(CoreView.class.getName()).log(Level.SEVERE, null, ex);
        }
        return op;       
    }
    
    public String deleteRecord()
    {
        String op = null;
        try {      
            System.out.print("Are you sure to delete this record [y/n] : ");
            op = read.readLine();
        } 
        catch (IOException ex) {
            Logger.getLogger(CoreView.class.getName()).log(Level.SEVERE, null, ex);
        }
        return op;
    }
    
    public String updateRecord()
    {
        String op = null;
        try
        {
            System.out.println("Are you sure to update this record [y/n] : ");
            op = read.readLine();
        }
        catch (IOException ex) {
            Logger.getLogger(CoreView.class.getName()).log(Level.SEVERE, null, ex);
        }
        return op;       
    }
    
    public void deleteMessage()
    {
        System.out.println("Delete Successfully!");
    }
    
    public void RecordNotFound()
    {
        System.out.println("This Product Not Found!");
    }
    
    public void updateMessage()
    {
        System.out.println("Update Successfully!");
    }
    
    public String recoveryData()
    {
        String op = null;
        try {   
            System.out.println("Do you want to recovery data from last session? [y/n]");
            System.err.println("If [n] your data will lost forever!");
            op = read.readLine();
        } catch (IOException ex) {
            Logger.getLogger(CoreView.class.getName()).log(Level.SEVERE, null, ex);
        }
        return op;
    }
    
    public void recoveryMessage()
    {
        System.out.println("This operation may take serveral second! Please wait.");
    }
    
    public void recoveryDone()
    {
        System.out.println("Data recovery Successfully!");
    }
    
    public String restoreFileName()
    {
        String op = null;
        System.out.println("Enter File from above to restore: ");
        try {
            op = read.readLine();
        } 
        catch (IOException ex) {
            Logger.getLogger(CoreView.class.getName()).log(Level.SEVERE, null, ex);
        }
        return op;
    }
    
    public void noPage()
    {
        System.err.println("Invalid Page Number. Please try again!");
    }
}
