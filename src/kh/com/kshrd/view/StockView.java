
package kh.com.kshrd.view;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import kh.com.kshrd.model.Product;
import kh.com.kshrd.model.StockDaoImpl;


public class StockView {
    BufferedReader read =  new BufferedReader(new InputStreamReader(System.in));
    StockDaoImpl sto = new StockDaoImpl();
    DrawTable dt = new DrawTable();
    SetConfiguration setCon = new SetConfiguration();
    DateFormat dateFormate;
    Date date;
    Product pro;
    
    public StockView()
    {
        System.out.println("\t\t\t\t==========================");
        System.out.println("\t\t\t\t Stock Management System");
        System.out.println("\t\t\t\t==========================");
        setCon.setConfigure();
        Display();
    }
    
    public String makeMenu()
    {
        String op = null;
        String tmp = null;
        try {
            
            System.out.println("");
            System.out.format("+----------------------------------------------------------------------------------------+%n");
            System.out.println("|  *)Display | W)write | R)ead | U)pdate | D)elete | F)irst | P)revious | N)ext | L)ast  |");
            System.out.println("|                                                                                        |");
            System.out.println("|  S)earch | G)o to | Se)t row | Ex)port | Im)port | B)ackup | Re)store | H)elp | E)xit  |");
            System.out.format("+----------------------------------------------------------------------------------------+%n");
            
            System.out.println("");
            System.out.print("Option: ");
            op = tmp = read.readLine(); 
            if(tmp.contains("@"))
            {
               sto.shortcutManipulate(tmp);
            }       
        } 
        catch (IOException ex) {
            Logger.getLogger(StockView.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (NumberFormatException e)
        {
            System.out.println("\n");
            System.err.println("Sorry Invalid Input! Please Try again!");
        }     
        return op ;
    }
    
    public void addRecord()
    {
        try {
            pro = new Product();
            dateFormate = new SimpleDateFormat("yyyy/MM/dd");
            date = new Date();
            System.out.print("Product Name: ");
            pro.setpName(read.readLine());
            System.out.print("Product Unit Price: ");
            pro.setpUnitPrice(Double.parseDouble(read.readLine()));
            System.out.print("Product Stock Quantity: ");
            pro.setpStockQty(Integer.parseInt(read.readLine()));
            System.out.println("Imported Date is: " + dateFormate.format(date));
            pro.setpImportDate(dateFormate.format(date));     
            if(saveDecision())
            {
                sto.insertProduct(pro);
                System.out.println("Save Succefully!");
            }
            else
            {
                System.err.println("This record was not save!");
            }
        } catch (IOException ex) {
            Logger.getLogger(StockView.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (NumberFormatException e)
        {
            System.out.println("\n");
            System.err.println("Sorry Invalid Input! Please Try again!");
        }
    }
    
    public boolean saveDecision()
    {
        boolean result = false;
        try { 
            System.out.print("Do you want to save this record?[y/n] : ");
            result = sto.makeDecision(read.readLine());
        } 
        catch (IOException ex) {
            Logger.getLogger(StockView.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public void readById()
    {
        try {
            System.out.print("Enter Product ID to Search: ");
            pro = sto.searchProduct(Integer.parseInt(read.readLine()));
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
                System.out.println("This product not found!");
            }
        } catch (IOException ex) {
            Logger.getLogger(StockView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean checkUpdate()
    {
        boolean op = false;
        try {    
            System.out.print("Enter Product ID to Search: ");
            pro = sto.searchProduct(Integer.parseInt(read.readLine()));
            if(pro!=null)
            {
                op = true;
                System.out.println("Product ID: " + pro.getpId());
                System.out.println("Product Name: " + pro.getpName());
                System.out.format("Product Unit Price: %6.2f $%n" , pro.getpUnitPrice());
                System.out.println("Unit available In Stock: " + pro.getpStockQty());       
                System.out.println("Imported Date: " + pro.getpImportDate());
            }
            else
            {
                System.out.println("This product not found!");
                op = false;
            }   
        } 
        catch (IOException ex) {
            Logger.getLogger(StockView.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (NumberFormatException e)
        {
            System.out.println("\n");
            System.err.println("Sorry Invalid Input! Please Try again!");
        } 
        return op;
    }
    
    public String updateOption()
    {
        String op = null;
        try {
            System.out.println("");
            System.out.println("Whate do you want to update?");
            System.out.println("=====================================");
            System.out.println("Al)l | Na)me | Pr)ice | Un)it | Ba)ck");
            System.out.println("=====================================");
            System.out.print("Update Option: ");
            op = read.readLine();
        } 
        catch (IOException ex) {
            Logger.getLogger(StockView.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (NumberFormatException e)
        {
            System.out.println("\n");
            System.err.println("Sorry Invalid Input! Please Try again!");
        } 
        return op;
    }
    
    public boolean updateDecision()
    {
        boolean result = false;
        try {    
            System.out.print("Are you sure to update this record?[y/n] : ");
            result = sto.makeDecision(read.readLine());
        } 
        catch (IOException ex) {
            Logger.getLogger(StockView.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public void doUpdatAll()
    {
        try {
            long id = pro.getpId();
            String date = pro.getpImportDate(); 
            String name;
            double price;
            int unit;
            System.out.println("ID: " + id); 
            System.out.print("New Product Name : ");
            name = read.readLine();
            System.out.print("New Product Unit Price : ");
            price = Double.parseDouble(read.readLine());
            System.out.print("New Product Stock : ");
            unit = Integer.parseInt(read.readLine());
            System.out.print("Imported Date: " + date);
            
            pro = new Product();
            pro.setpId(id);
            pro.setpName(name);
            pro.setpUnitPrice(price);
            pro.setpStockQty(unit);
            pro.setpImportDate(date);
            System.out.println("");
            if(updateDecision())
            {
               sto.updateProduct(pro);
               System.err.println("Update Successfully!");  
            }
            else
            {
                System.err.println("This record did not change!");
            }
        } 
        catch (IOException ex) {
            Logger.getLogger(StockView.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (NumberFormatException e)
        {
            System.err.println("Sorry Invalid Input! Please Try again!");
        } 
    }
    
    public void doUpdateName()
    {
        try {
            long id = pro.getpId();
            double price = pro.getpUnitPrice();
            int unit = pro.getpStockQty();
            String date = pro.getpImportDate();
            pro = new Product();
            System.out.println("New Product Name : ");
            pro.setpName(read.readLine());
            pro.setpId(id);
            pro.setpUnitPrice(price);
            pro.setpStockQty(unit);
            pro.setpImportDate(date);
            if(updateDecision())
            {
                sto.updateProduct(pro);
                System.out.println("");
                System.err.println("Update Successfully!"); 
            }
            else
            {
                System.err.println("This record did not change!");
            }
        } 
        catch (IOException ex) {
            Logger.getLogger(StockView.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (NumberFormatException e)
        {
            System.out.println("\n\n");
            System.err.println("Sorry Invalid Input! Please Try again!");
        } 
    }
    
    public void doUpdatePrice()
    {
        try {
            long id = pro.getpId();
            String name = pro.getpName();
            int unit = pro.getpStockQty();
            String date = pro.getpImportDate();
            double price = 0;
            System.out.print("New Product Price: ");
            price = Double.parseDouble(read.readLine());
            pro = new Product();  
            pro.setpId(id);
            pro.setpName(name);
            pro.setpUnitPrice(price);
            pro.setpStockQty(unit);
            pro.setpImportDate(date);
            if(updateDecision())
            {
                sto.updateProduct(pro);
                System.out.println("");
                System.err.println("Update Successfully!");
            }
            else
            {
                System.err.println("This record did not change!");
            }
        } 
        catch (IOException ex) {
            Logger.getLogger(StockView.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (NumberFormatException e)
        {
            System.out.println("\n\n");
            System.err.println("Sorry Invalid Input! Please Try again!");
        } 
    }
    
    public void doUpdateUnit()
    {
        try {
            long id = pro.getpId();
            String name = pro.getpName();
            Double price = pro.getpUnitPrice();
            String date = pro.getpImportDate();
            int unit = 0;     
            System.out.println("New Stock Quantity : ");
            unit = Integer.parseInt(read.readLine());
            pro = new Product();    
            pro.setpId(id);
            pro.setpName(name);
            pro.setpUnitPrice(price);
            pro.setpStockQty(unit);
            pro.setpImportDate(date);
            if(updateDecision())
            {
                sto.updateProduct(pro);
                System.out.println("");
                System.err.println("Update Successfully!");
            }
            else
            {
                System.err.println("This record did not change!");
            }
        } 
        catch (IOException ex) {
            Logger.getLogger(StockView.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (NumberFormatException e)
        {
            System.out.println("\n\n");
            System.err.println("Sorry Invalid Input! Please Try again!");
        } 
    }
    
    public void makeDelete()
    {
        try {
            System.out.print("Enter Product ID to Delete: ");
            pro = sto.searchProduct(Integer.parseInt(read.readLine()));
            if(pro!=null)
            {
                if(deleteDecision())
                {
                    sto.deleteProduct((int) pro.getpId());
                    System.out.println("Delete Successfully!");
                }
                else
                {
                    System.out.println("This record is not delete!");
                }       
            }
            else
            {
                System.out.println("This Product Not found!");
            }
        } 
        catch (IOException ex) {
            Logger.getLogger(StockView.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (NumberFormatException e)
        {
            System.out.println("\n");
            System.err.println("Sorry Invalid Input! Please Try again!");
        }   
    }
    
    public boolean deleteDecision()
    {
        boolean result = false;
        try {
            System.out.print("Do you want to delete this record?[y/n] : ");
            result = sto.makeDecision(read.readLine());
        } catch (IOException ex) {
            Logger.getLogger(StockView.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public void readByName()
    {
        ArrayList<Product> Listpro = new ArrayList<>();
        try {
            System.out.print("Enter Product Name to Search: ");
            Listpro = sto.searchByName(read.readLine());
            if(Listpro.size() > 0)
            {
                dt.SearchView(Listpro);
            }
            else
            {
                System.out.println("This Product Not Found!");
            }
        } 
        catch (IOException ex) {
            Logger.getLogger(StockView.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (NumberFormatException e)
        {
            System.out.println("\n");
            System.err.println("Sorry Invalid Input! Please Try again!");
        }  
    }
    
    public void Display()
    {
        dt.mainView(sto.getCertainRows(),sto.getPageNumber(),sto.getAvailablePage(),sto.getRecordNumber());
    }
    
    public void nextPage()
    {
        sto.moveNext();
        Display();
    }
    
    public void previousPage()
    {
        sto.movePrevious();
        Display();
    }
    
    public void firstPage()
    {
        sto.moveFirst();
        Display();
    }
    
    public void lastPage()
    {
        sto.moveLast();
        Display();
    }
    
    public void viewSpecificPage()
    {
        try {
            System.out.print("Enter Page Number: ");
            sto.setSpecificPage(Integer.parseInt(read.readLine()));
            Display();    
        } 
        catch (IOException ex) {
            Logger.getLogger(StockView.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (NumberFormatException e)
        {
            System.out.println("\n");
            System.err.println("Sorry Invalid Input! Please Try again!");
        } 
    }
    
    public void makeCustomRow()
    {
        try {
            System.out.print("Enter Number of Rows You want: ");
            sto.setRows(Integer.parseInt(read.readLine()));
        } 
        catch (IOException ex) {
            Logger.getLogger(StockView.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (NumberFormatException e)
        {
            System.out.println("\n");
            System.err.println("Sorry Invalid Input! Please Try again!");
        } 
    }
    
    public String getHelp()
    {
        String op = null;
        System.out.println("+----------------------------------------------------------------------------------------+");
        System.out.println("|\tHelps                                                                            |");
        System.out.println("|\t1.Write:                                                                         |");
        System.out.println("|\t  - Press w and then enter to write normally.                                    |");
        System.out.println("|\t  - Press w@[Product-name]@[Unit-Price]@[Stock-Qty]                              |");
        System.out.println("|\t2.Read                                                                           |");
        System.out.println("|\t  - Press r and then enter to read normally                                      |");
        System.out.println("|\t  - Press r@[Product-ID] and then to read with shortcut                          |");
        System.out.println("|\t3.Update                                                                         |");
        System.out.println("|\t  - Press u and then enter to update normally.                                   |");
        System.out.println("|\t  - Press u@[Product-id]@[Product-name]@[Unit-price]@[Stock.]                    |");
        System.out.println("|\t4.Delete                                                                         |");
        System.out.println("|\t  - Press d then enter to delete normally                                        |");
        System.out.println("|\t  - Press d@[ProductId]                                                          |");
        System.out.println("|\t5.Search                                                                         |");
        System.out.println("|\t  - Press s and then enter to search normally.                                   |");
        System.out.println("|\t  - Press s@[Product-name] to search record                                      |");
        System.out.println("|\t6.Export Data                                                                    |");
        System.out.println("|\t  - you can find you export file in folder [export-stock]                        |");
        System.out.println("|\t7.Import Data                                                                    |");
        System.out.println("|\t  - make sure import file name must be [import.xlsx] then copy to                |");
        System.out.println("|\t    folder [import-stock] befor starting import data                             |");
        System.out.println("|\t8.Developed By                                                                   |");
        System.out.println("|\t                    - Tith Kuylim                                                |");
        System.out.println("|\t                    - Chun Chamroeun                                             |");
        System.out.println("|\t                    - Chea Navy                                                  |");
        System.out.println("|\t                    - Sim Vicheara                                               |");
        System.out.println("|\t                    - Ngan Thanak                                                |");
        System.out.println("+----------------------------------------------------------------------------------------+");
        System.out.println("\nPress b to go back");
        try {
            op = read.readLine();
        } 
        catch (IOException ex) {
            Logger.getLogger(StockView.class.getName()).log(Level.SEVERE, null, ex);
        }
        return op;
    }
    
    public boolean restoreDecision()
    {
        boolean result = false;
        try {
            System.out.print("Are you sure to restore database?[y/n] : ");
            result = sto.makeDecision(read.readLine());
        } catch (IOException ex) {
            Logger.getLogger(StockView.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public void restore()
    {
        if(restoreDecision())
        {
            sto.RestoreData();
            System.err.println("Restore data successfully!");
        }
        else
        {
            System.err.println("Restore process has stop!");
        }   
    }
    
    public boolean backupDecision()
    {
        boolean result = false;
        try {
            System.out.print("Are you sure to backup database?[y/n] : ");
            result = sto.makeDecision(read.readLine());
        } catch (IOException ex) {
            Logger.getLogger(StockView.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public void backupDatabase()
    {
        if(backupDecision())
        {
            sto.backupData();
            System.err.println("Backup data successfully!");
        } 
        else
        {
            System.err.println("Backup process has stop!");
        }
    } 
    
    public boolean exportDecision()
    {
        boolean result = false;
        try {
            System.out.print("Are you sure export these record?[y/n] : ");
            result = sto.makeDecision(read.readLine());
        } catch (IOException ex) {
            Logger.getLogger(StockView.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public void exportToExcel()
    {
        if(exportDecision())
        {
            sto.exportdata();
            System.err.println("Stock export successfully!");
        }
        else
        {
            System.err.println("Export process has stop!");
        }
        
    }
    
    public boolean importDecision()
    {
        boolean result = false;
        try {
            System.out.print("Are you sure import these record?[y/n] : ");
            result = sto.makeDecision(read.readLine());
        } catch (IOException ex) {
            Logger.getLogger(StockView.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public void importToDatabase()
    {
        sto.startExcel("import-stock"+File.separator+"import.xlsx");
        if(importDecision())
        {
            sto.importData();
            System.err.println("Imported data sucessfully!");
        }
        else
        {
            System.err.println("Data did not import!");
        }
    }
    
    public boolean exitDecision()
    {
        boolean result = false;
        try {
            System.out.print("Are you sure to exit?[y/n] : ");
            result = sto.makeDecision(read.readLine());
        } catch (IOException ex) {
            Logger.getLogger(StockView.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public void stopApp()
    {
        if(exitDecision())
        {
            System.exit(0);
        }
    }
}
