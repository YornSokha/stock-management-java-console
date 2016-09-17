
package kh.com.kshrd.model;

import java.io.Serializable;
import java.util.Comparator;

public class Product implements Serializable{
    private long  pId;
    private String pName;
    private double pUnitPrice;
    private int pStockQty;
    private String pImportDate;

    /**
     * @return the pId
     */
    public long getpId() {
        return pId;
    }

    /**
     * @param pId the pId to set
     */
    public void setpId(long pId) {
        this.pId = pId;
    }

    /**
     * @return the pName
     */
    public String getpName() {
        return pName;
    }

    /**
     * @param pName the pName to set
     */
    public void setpName(String pName) {
        this.pName = pName;
    }

    /**
     * @return the pUnitPrice
     */
    public double getpUnitPrice() {
        return pUnitPrice;
    }

    /**
     * @param pUnitPrice the pUnitPrice to set
     */
    public void setpUnitPrice(double pUnitPrice) {
        this.pUnitPrice = pUnitPrice;
    }

    /**
     * @return the pStockQty
     */
    public int getpStockQty() {
        return pStockQty;
    }

    /**
     * @param pStockQty the pStockQty to set
     */
    public void setpStockQty(int pStockQty) {
        this.pStockQty = pStockQty;
    }

    /**
     * @return the pImportDate
     */
    public String getpImportDate() {
        return pImportDate;
    }

    /**
     * @param pImportDate the pImportDate to set
     */
    public void setpImportDate(String pImportDate) {
        this.pImportDate = pImportDate;
    }
    
    @SuppressWarnings("Convert2Lambda")
    public static Comparator<Product> ProCompare = new Comparator<Product>()
    {
        @Override
        public int compare(Product p1, Product p2)
        {
            int pid1 = (int) p1.getpId();
            int pid2 = (int) p2.getpId();
            
            return pid1 - pid2;
        }
    };
}
