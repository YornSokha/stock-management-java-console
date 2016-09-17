
package kh.com.kshrd.view;

import java.util.ArrayList;
import kh.com.kshrd.model.Product;

public class DrawTable {
    public void SearchView(ArrayList<Product> stock)
    {
        drawTable(stock);
        System.out.format("|                                                             Total Record: %-10d   |%n",stock.size());
        System.out.format("+----------------------------------------------------------------------------------------+%n");
    }

    public void mainView(ArrayList<Product> stock, int rec, int allRec, int records)
    {
        drawTable(stock);
        System.out.format("| Page: %-10d/%-10d                                 Total Record: %-10d   |%n",rec,allRec,records);
        System.out.format("+----------------------------------------------------------------------------------------+%n");
    }
    
    private void drawTable(ArrayList<Product> stock)
    {
        String leftAlignFormat = "| %-10d | %-17s | %-12.2f $ |  %-13d  |  %-16s  |%n";        
        System.out.format("+------------+-------------------+----------------+-----------------+--------------------+%n");
        System.out.format("|     ID     |         Name      |  Unit Price    |  Stock Quantity |    Imported Date   |%n");
        System.out.format("+------------+-------------------+----------------+-----------------+--------------------+%n");
        for (long i =  stock.size()-1 ; i >= 0; i--)
        {          
            System.out.format(leftAlignFormat, stock.get((int) i).getpId(), stock.get((int) i).getpName(), stock.get((int) i).getpUnitPrice(), stock.get((int) i).getpStockQty(), stock.get((int) i).getpImportDate());
            System.out.format("+------------+-------------------+----------------+-----------------+--------------------+%n");
        }
    }
}
