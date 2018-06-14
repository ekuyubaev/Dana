package kz.foodmaster.filial.business;


import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

public class Product implements Serializable {
	private static final long serialVersionUID = 1L;
	
    private int productID;    
    private String productName;
    private int productMeasureID;
    private int productCategoryID;
    private BigDecimal productPrice;
    private float productQuantity;
    private String productNote;

    public Product() {
    	productName = null;
    	productNote = null;
    	productQuantity = 0.0F;
    	productPrice = new BigDecimal(0.00);
    }

    
    public int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getProductMeasureID() {
		return productMeasureID;
	}

	public void setProductMeasureID(int productMeasureID) {
		this.productMeasureID = productMeasureID;
	}

	public int getProductCategoryID() {
		return productCategoryID;
	}

	public void setProductCategoryID(int productCategoryID) {
		this.productCategoryID = productCategoryID;
	}

	public BigDecimal getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}

	public float getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(float productQuantity) {
		this.productQuantity = productQuantity;
	}

	public String getProductNote() {
		return productNote;
	}

	public void setProductNote(String productNote) {
		this.productNote = productNote;
	}

	public String getPriceCurrencyFormat() {
		NumberFormat currency = NumberFormat.getCurrencyInstance();
        if (currency instanceof DecimalFormat) {
            DecimalFormat df = (DecimalFormat) currency;
            DecimalFormatSymbols dfs = new DecimalFormat().getDecimalFormatSymbols();
            dfs.setCurrencySymbol("тенге");
            df.setDecimalFormatSymbols(dfs);
        }
        String formattedPrice = currency.format(this.productPrice);
        return formattedPrice;
    }
}