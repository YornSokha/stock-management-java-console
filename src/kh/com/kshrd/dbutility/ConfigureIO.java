
package kh.com.kshrd.dbutility;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfigureIO {
    DbConfiguration postConf;
    ObjectOutputStream out = null;
    ObjectInputStream in = null;
   
     
    public void saveConfigure(DbConfiguration postCon)
    {
        postConf = postCon;
        try {
            boolean dir = new File("conf").mkdir();
            File config = new File("conf"+File.separator+"PostgresConf.dk");
            out = new ObjectOutputStream (new BufferedOutputStream(new FileOutputStream(config)));
            out.writeObject(postCon);
            out.flush();
        } 
        catch (FileNotFoundException ex) {
            Logger.getLogger(ConfigureIO.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (IOException ex) {
            Logger.getLogger(ConfigureIO.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            try {
                out.close();
            } 
            catch (IOException ex) {
                Logger.getLogger(ConfigureIO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void readConfigure()
    { 
        try {
            File config = new File("conf"+File.separator+"PostgresConf.dk");
            in = new ObjectInputStream (new BufferedInputStream (new FileInputStream(config)));
            postConf = (DbConfiguration) in.readObject();
        } 
        catch (IOException ex) {
            Logger.getLogger(ConfigureIO.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (ClassNotFoundException ex) {
            Logger.getLogger(ConfigureIO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public DbConfiguration getConfigure()
    {
        return postConf;
    }
}
