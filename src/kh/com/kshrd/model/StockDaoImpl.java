
package kh.com.kshrd.model;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import kh.com.kshrd.dbutility.DbConnection;
import kh.com.kshrd.view.CoreView;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class StockDaoImpl implements StockDAO{

    ArrayList<Product> proList = new ArrayList<>();
    CoreView cv = new CoreView();
    Product pro;
    PreparedStatement ps;
    ResultSet rs;
    Connection cnn;
    static int limit = 5;
    static int offset, tmp;
    static int rows = 5;
    static int record;
    static int currentPage = 1;
    Date date = new Date();
    DateFormat dateFormate;

    
    public StockDaoImpl()
    {
        tmp = rows;
    }
    
    @Override
    public void insertProduct(Product pro) {
        cnn = new DbConnection().getConnection();
        String sqlCommand = "Insert Into tblstock (name,unitprice,sqty,impdate) values (?,?,?,?)";
        try
        {
            ps = cnn.prepareStatement(sqlCommand);
            ps.setString(1, pro.getpName());
            ps.setDouble(2, pro.getpUnitPrice());
            ps.setInt(3, pro.getpStockQty());
            ps.setString(4, pro.getpImportDate());
            ps.executeUpdate();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try {
                ps.close();
                cnn.close();
            } 
            catch (SQLException ex) {
                Logger.getLogger(StockDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public ArrayList<Product> getAllProduct() {
        cnn = new DbConnection().getConnection();
        String sqlCommand = "Select * from tblstock";
        try
        {
            ps = cnn.prepareStatement(sqlCommand);
            rs = ps.executeQuery();
            while(rs.next())
            {
                pro = new Product();
                pro.setpId(rs.getInt("stoid"));
                pro.setpName(rs.getString("name"));
                pro.setpUnitPrice(rs.getDouble("unitprice"));
                pro.setpStockQty(rs.getInt("sqty"));
                pro.setpImportDate(rs.getString("impdate"));
                
                proList.add(pro);
            }
        }
        catch (Exception e)
        {
            System.err.println("Query record failed!");
        }
        finally
        {
            try {
                rs.close();
                ps.close();
                cnn.close();
            } 
            catch (SQLException ex) {
                Logger.getLogger(StockDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return proList;
    }

    @Override
    public void updateProduct(Product pro) {
        cnn = new DbConnection().getConnection();
        String sqlCommand = "Update tblstock Set name = ?, unitPrice = ?, Sqty = ?, ImpDate = ? where stoId = ?";
        try
        {
            ps = cnn.prepareStatement(sqlCommand);
            ps.setString(1, pro.getpName());
            ps.setDouble(2, pro.getpUnitPrice());
            ps.setInt(3, pro.getpStockQty());
            ps.setString(4, pro.getpImportDate());
            ps.setInt(5, (int) pro.getpId());
            
            ps.executeUpdate();
        }
        catch (Exception e)
        {
            System.err.println("Update record failed!");
        }
        finally
        {
            try {
                ps.close();
                cnn.close();
            } 
            catch (SQLException ex) {
                Logger.getLogger(StockDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void deleteProduct(int id) {
        cnn = new DbConnection().getConnection();
        String sqlCommand = "Delete from tblstock where stoid = ?";
        try
        {
            ps = cnn.prepareStatement(sqlCommand);
            ps.setInt(1, id);
            ps.executeUpdate();
        }
        catch (Exception e)
        {
            System.err.println("Failed to delete record!");
        }
        finally
        {
            try {
                ps.close();
                cnn.close();
            } 
            catch (SQLException ex) {
                Logger.getLogger(StockDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public Product searchProduct(int id) {
        cnn = new DbConnection().getConnection();
        pro = null;
        String sqlCommand = "Select * from tblstock where stoid = ?";
        try
        {   
            ps = cnn.prepareStatement(sqlCommand);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            
            while(rs.next())
            {
                pro = new Product();
                pro.setpId(rs.getInt("stoid"));
                pro.setpName(rs.getString("name"));
                pro.setpUnitPrice(rs.getDouble("unitprice"));
                pro.setpStockQty(rs.getInt("sqty"));
                pro.setpImportDate(rs.getString("impdate"));
            }
        }
        catch (Exception e)
        {
            System.err.println("Failed to search record!");
        }
        finally
        {
            try {
                rs.close();
                ps.close();
                cnn.close();
            } 
            catch (SQLException ex) {
                Logger.getLogger(StockDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return pro;
    }
    
    public void getOffset()
    {
        cnn = new DbConnection().getConnection();
        offset = 0;
        try
        {
           String sqlCommand = "Select Count(*) from tblstock";
           ps = cnn.prepareStatement(sqlCommand); 
           rs = ps.executeQuery();
           while(rs.next())
           {
               record = offset = rs.getInt(1);
           }
           offset = offset - (limit); 
        }
        catch (Exception e)
        {
            System.out.println("Cann't count row!");
        }
        finally
        {
            try {
                rs.close();
                ps.close();
                cnn.close();
            } 
            catch (SQLException ex) {
                Logger.getLogger(StockDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void moveNext()
    {  
        rows = tmp;
        limit = limit + rows;
        if(limit > record)
        {   
            limit =  record; 
            tmp = rows;
            rows = offset;
            if(rows == 0 )
            {
                rows = tmp;
                moveLast();
            }
            if(currentPage< record/tmp)
                currentPage++;  
        }
        else
        {    
            if(currentPage< record/tmp)
                currentPage++;
        }
    }
    
    public void movePrevious()
    {
        if (limit <= rows)
        {
            moveFirst();
            if(currentPage> 1)
                currentPage--;
        }
        else
        {
            limit = limit - rows;
            if(currentPage> 1)
                currentPage--;
        }
    }
    
    public void moveFirst()
    {
        limit = rows; 
        currentPage = 1;
    }
    
    public void moveLast()
    {
        limit = record;
        currentPage = record/rows;
    }
    
    public void setSpecificPage(int page)
    {
        if(page == 1)
        {
            moveFirst();
        }
        else if (page == (record/rows))
        {
            moveLast();
        }
        else if (page > (record/rows))
        {
            cv.noPage();
        }
        else
        {
            limit = page * rows;
            currentPage = page;
        }  
    }
    
    public void setRows(int Customrows)
    {
        rows = Customrows;
        tmp = rows;
    }
    
    public ArrayList<Product> getCertainRows ()
    {  
        getOffset();
        cnn = new DbConnection().getConnection();
        String sqlCommand = "Select * from tblstock  Order By stoid ASC Limit ? Offset ?";
        proList.removeAll(proList);
        try
        {
            ps = cnn.prepareStatement(sqlCommand);
            ps.setInt(1, rows);
            ps.setInt(2, offset);
            rs = ps.executeQuery();
            while(rs.next())
            {
                pro = new Product();
                pro.setpId(rs.getInt("stoid"));
                pro.setpName(rs.getString("name"));
                pro.setpUnitPrice(rs.getDouble("unitprice"));
                pro.setpStockQty(rs.getInt("sqty"));
                pro.setpImportDate(rs.getString("impdate"));
                
                proList.add(pro);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try {
                rs.close();
                ps.close();
                cnn.close();
            } 
            catch (SQLException ex) {
                Logger.getLogger(StockDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return proList;
    }
    
    public ArrayList<Product> searchByName(String name)
    {
        proList.removeAll(proList);
        cnn = new DbConnection().getConnection();
        String sqlCommand = "Select * from tblstock where name like ?";
        try
        {
            ps = cnn.prepareStatement(sqlCommand);
            ps.setString(1, "%"+name+"%");
            rs = ps.executeQuery();
            
            while(rs.next())
            {
                pro = new Product();
                pro.setpId(rs.getInt("stoid"));
                pro.setpName(rs.getString("name"));
                pro.setpUnitPrice(rs.getDouble("unitprice"));
                pro.setpStockQty(rs.getInt("sqty"));
                pro.setpImportDate(rs.getString("impdate"));
                
                proList.add(pro);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try {
                ps.close();
                rs.close();
                cnn.close();
            } 
            catch (SQLException ex) {
                Logger.getLogger(StockDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return proList;
    }
    
    public int getAvailablePage()
    {
        rows = tmp;
        int aPage = record/rows;
        return aPage;
    }
    
    public int getPageNumber()
    {
        return currentPage;
    }
    
    public int getRecordNumber()
    {
        return record;
    }
    
    public boolean makeDecision(String op)
    {
        boolean result = false;
        if (op.toLowerCase().equals("y"))
        {
            result = true;
        }
        else if (op.toLowerCase().equals("n"))
        {
            result = false;
        }
        return result;
    }
    
    public void RestoreData()
    {
        cnn = new DbConnection().getConnection();
        String sqlCommand = "Drop Table tblstock";
        try {
            ps = cnn.prepareStatement(sqlCommand);
            ps.executeUpdate();
            
            Runtime r = Runtime.getRuntime();
            Process p = null;
            ProcessBuilder pb;
            r = Runtime.getRuntime();
            pb = new ProcessBuilder(
                    "C:"+File.separator+"Program Files"+File.separator+"PostgreSQL"+File.separator+"9.5"+File.separator+"bin"+File.separator+"pg_restore.exe",
                    "--host", "localhost",
                    "--port", "5432",
                    "--username", "postgres",
                    "--dbname", "StockManagement",
                    "--role", "postgres",
                    "--no-password",
                    "--verbose",
                    "backup"+File.separator+"stock.backup"
            );
            pb.redirectErrorStream(true);
            p = pb.start();  

            InputStream is = p.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String ll;
            while ((ll = br.readLine()) != null)
                {
                    System.out.println(ll);                  
                }    
        } 
        catch (SQLException ex) {
            Logger.getLogger(StockDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (IOException ex) {
            Logger.getLogger(StockDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            try {
                ps.close();
                cnn.close();
            } 
            catch (SQLException ex) {
                Logger.getLogger(StockDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }  
    
    public void backupData()
    {
        try {
            boolean dir = new File("backup").mkdir();
            Runtime r = Runtime.getRuntime();
            Process p = null;
            ProcessBuilder pb;
            r = Runtime.getRuntime();
            pb = new ProcessBuilder(
                    "C:"+File.separator+"Program Files"+File.separator+"PostgreSQL"+File.separator+"9.5"+File.separator+"bin"+File.separator+"pg_dump.exe",
                    "--host", "localhost",
                    "--port", "5432",
                    "--username", "postgres",
                    "--role", "postgres",
                    "--no-password",
                    "--format", "c",
                    "--verbose",
                    "--file","backup"+File.separator+"stock.backup",
                    "--dbname", "StockManagement"      
            );
            pb.redirectErrorStream(true);
            p = pb.start();
            
            InputStream is = p.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String ll;
            while ((ll = br.readLine()) != null)
            {
                System.out.println(ll);
            } 
        } 
        catch (IOException ex) {
            Logger.getLogger(StockDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void shortcutManipulate(String data)
    {
        String[] fields = null;
        fields = data.split("@");
        
        switch(fields[0].toLowerCase())
        {
            //search 
            case "s":
            {
                if(searchByName(fields[1])!=null)
                {
                    cv.readByName(searchByName(fields[1]));               
                }
            }
            break;
            //add new record
            case "w":
            {
                dateFormate = new SimpleDateFormat("yyyy/MM/dd");
                date = new Date();
                try
                {
                    if(makeDecision(cv.insertRecord()))
                    {
                        pro = new Product();
                        pro.setpName(fields[1]);
                        pro.setpUnitPrice(Double.parseDouble(fields[2]));
                        pro.setpStockQty(Integer.parseInt(fields[3]));
                        pro.setpImportDate(dateFormate.format(date));
                        insertProduct(pro);
                        cv.insetMessage();
                    } 
                }
                catch (Exception e)
                {
                    System.err.println("Invalid Input!");
                }                       
            }
            break;
            //delete
            case "d":
            {
                try
                {
                   pro = searchProduct(Integer.parseInt(fields[1]));
                    if(pro!=null)
                    {
                        if(makeDecision(cv.deleteRecord()))
                        {
                            deleteProduct(Integer.parseInt(fields[1]));
                            cv.deleteMessage();    
                        }
                    } 
                    else
                    {
                        cv.RecordNotFound();
                    } 
                }
                catch (Exception e)
                {
                    System.err.println("Invalid Input");
                }
            }
            break;
            //read with id
            case "r":
            {
                try
                {
                    pro = searchProduct(Integer.parseInt(fields[1]));
                    if(pro!=null)
                    {
                        cv.readById(pro);
                    }
                    else
                    {
                        cv.RecordNotFound();
                    }
                }
                catch (Exception e)
                {
                    System.err.println("Invalid Input!");
                }    
            }
            break;
            //update
            case "u":
            {
                try
                {
                    pro = searchProduct(Integer.parseInt(fields[1]));
                    if(pro!=null)
                    {
                        if(makeDecision(cv.updateRecord()))
                        {
                            String name = fields[2];
                            double price = Double.parseDouble(fields[3]);
                            int StockQty = Integer.parseInt(fields[4]);
                            String pdate = pro.getpImportDate();
                            pro.setpName(name);
                            pro.setpUnitPrice(price);
                            pro.setpStockQty(StockQty);
                            pro.setpImportDate(pdate);                       
                            updateProduct(pro);
                            cv.updateMessage();
                        }  
                    }
                    else
                    {
                        cv.RecordNotFound();
                    }  
                }
                catch (Exception e)
                {
                    System.err.println("Invalid Input!");
                }
            }
            break;
        }
    }
       
    public void exportdata()
    {
        boolean dir = new File("export-stock").mkdir();
        cnn = new DbConnection().getConnection();
        String sqlCommand = "Select * from tblstock";
        try
        {
            ps = cnn.prepareStatement(sqlCommand);
            rs = ps.executeQuery();
            
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet spreadsheet = workbook.createSheet("Stock Products");
            XSSFRow row = spreadsheet.createRow(1);
            XSSFCell cell;
            
            cell = row.createCell(1);
            cell.setCellValue("Product ID");
            cell = row.createCell(2);
            cell.setCellValue("Product Name");
            cell = row.createCell(3);
            cell.setCellValue("Unit Price");
            cell = row.createCell(4);
            cell.setCellValue("Stock QTY");
            cell = row.createCell(5);
            cell.setCellValue("Imported Date");
            int i = 2;
            while(rs.next())
            {
                row = spreadsheet.createRow(i);
                cell = row.createCell(1);
                cell.setCellValue(rs.getInt("stoid"));
                cell = row.createCell(2);
                cell.setCellValue(rs.getString("name"));
                cell = row.createCell(3);
                cell.setCellValue(rs.getDouble("unitprice"));
                cell = row.createCell(4);
                cell.setCellValue(rs.getInt("sqty"));
                cell = row.createCell(5);
                cell.setCellValue(rs.getString("impdate"));
                i++;
            }
            
            FileOutputStream out = new FileOutputStream(new File("export-stock"+File.separator+"Export-Stock.xlsx"));
            workbook.write(out);
            out.close();
            startExcel("export-stock"+File.separator+"Export-Stock.xlsx");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
           try {
               rs.close();
               ps.close();
               cnn.close();   
           } 
           catch (SQLException ex) {
               Logger.getLogger(StockDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
           }
        }
    }
    
    public void importData()
    {
        boolean dir = new File("import-stock").mkdir();
        cnn = new DbConnection().getConnection();
        String sqlCommand = "Insert Into tblstock (name,unitprice,sqty,impdate) values(?,?,?,?)";
        try {
            FileInputStream in = new FileInputStream(new File("import-stock"+File.separator+"import.xlsx"));
            XSSFWorkbook wb = new XSSFWorkbook(in);
            XSSFSheet spreadsheet = wb.getSheetAt(0);
            XSSFRow row;
            for(int i =2 ; i <= spreadsheet.getLastRowNum(); i++)
            {
                row = spreadsheet.getRow(i);
                String name = row.getCell(2).getStringCellValue();
                double price = row.getCell(3).getNumericCellValue();
                int qty = (int) row.getCell(4).getNumericCellValue();
                String Date = row.getCell(5).getStringCellValue();  
                
                ps = cnn.prepareStatement(sqlCommand);
                ps.setString(1, name);
                ps.setDouble(2, price);
                ps.setInt(3, qty);
                ps.setString(4, Date);
                ps.executeUpdate();
            }
            in.close();
        } 
        catch (FileNotFoundException ex) {
            Logger.getLogger(StockDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (IOException ex) {
            Logger.getLogger(StockDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (SQLException ex) {
            Logger.getLogger(StockDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        finally
        {
            try {
                ps.close();   
                cnn.close();
            } 
            catch (SQLException ex) {
                Logger.getLogger(StockDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void startExcel(String path)
    {
        try {
            Desktop.getDesktop().open(new File(path));
        } 
        catch (IOException ex) {
            Logger.getLogger(StockDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
