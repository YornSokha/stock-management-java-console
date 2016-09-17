
package kh.com.kshrd.model;

import java.util.ArrayList;

public interface StockDAO {
    public void insertProduct (Product pro);
    public ArrayList<Product> getAllProduct();
    public void updateProduct (Product pro);
    public void deleteProduct (int id);
    public Product searchProduct (int id);
}
