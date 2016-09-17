
package kh.com.kshrd.control;

import kh.com.kshrd.view.StockView;

public class StockControl {
    StockView  sv = new StockView(); 
    String ch;
    String op;
    
    // display record to user
    private void show()
    {
        sv.firstPage();
    }
    
    private void write()
    {
       sv.addRecord();
    }
    
    private void read()
    {
        sv.readById();
    }
    
    private void updateAll()
    {
        sv.doUpdatAll();
    }
    
    private void updateName()
    {
        sv.doUpdateName();
    }
    
    private void updatePrice()
    {
        sv.doUpdatePrice();
    }
    
    private void updateUnit()
    {
        sv.doUpdateUnit();
    }
    
    private void update()
    {
        if(sv.checkUpdate())
        {
            do
            {
                op = sv.updateOption().toLowerCase();
                switch(op)
                {
                    case "al":
                    {
                        updateAll();
                    }
                    break;
                    case "na":
                    {
                        updateName();
                    }
                    break;
                    case "pr":
                    {
                        updatePrice();
                    }
                    break;
                    case "un":
                    {
                        updateUnit();
                    }
                    break;
                }
            }while(!op.equals("ba"));
        }
    }
   
    
    private void delete()
    {
       sv.makeDelete();
    }
    
    private void moveFirst()
    {
       sv.firstPage();
    }
    
    private void movePrevious()
    {
       sv.previousPage();
    }
    
    private void moveNext()
    {
        sv.nextPage();
    }
    
    private void moveLast()
    {
        sv.lastPage();
    }
    
    private void searchByName()
    {
       sv.readByName();
    }
    
    private void moveToSpecificPage()
    {
      sv.viewSpecificPage();
    }
    
    private void setRow()
    {
       sv.makeCustomRow();
    }
    
    private void doExport()
    {
        sv.exportToExcel();
        
    }
    
    private void doImport()
    {
        sv.importToDatabase();
    }
    
    private void doBackup()
    {
        sv.backupDatabase();
    }
    
    private void doRestore()
    {
        sv.restore();
    }
    
    private void getHelp()
    {
        String op = null;
        do
        {
            op = sv.getHelp();        
        }while(!op.toLowerCase().equals("b"));
    }
    
    public void startApp()
    {
        do
        {
            //show(); 
            ch = sv.makeMenu().toLowerCase();
            switch(ch)
            {
                // display to user
                case "*":
                {
                    show();   
                }
                break;
                // write new record
                case "w":
                {
                    write();
                }
                break;
                // read specific product id
                case "r":
                {
                    read();
                }
                break;
                //upadte
                case "u":
                {
                    update();
                }
                break;
                //delete
                case "d":
                {
                    delete();
                }
                break;
                //go to fisrt page
                case "f":
                {
                    moveFirst();
                }
                break;
                //go to previous page
                case "p":
                {
                    movePrevious();
                }
                break;
                // go to next page
                case "n":
                {
                    moveNext();
                }
                break;
                // go to last page
                case "l":
                {
                    moveLast();
                }
                break;
                //search record by name
                case "s":
                {
                    searchByName();
                }
                break;
                //go to specific page
                case "g":
                {
                    moveToSpecificPage();
                }
                break;
                //set row to display
                case "se":
                {
                    setRow();
                }
                break;
                //export data to excel
                case "ex":
                {
                    doExport();
                }
                break;
                // import data to database
                case "im":
                {
                    doImport();
                }
                break;
                //back up file
                case "b":
                {
                    doBackup();
                }
                break;
                // restore file
                case "re":
                {
                    doRestore();
                }
                break;
                //help
                case "h":
                {
                    getHelp();
                }
                break;
                //exit
                case "e":
                {
                    sv.stopApp();
                }
                break;
            }  
        }while(!ch.equals("E"));
        
    }
}
