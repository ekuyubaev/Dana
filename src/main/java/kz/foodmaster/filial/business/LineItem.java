package kz.foodmaster.filial.business;


import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.io.Serializable;
import java.math.BigDecimal;

public class LineItem implements Serializable {
	private static final long serialVersionUID = 1L;
	
    private int lineItemId;
    private Product product;
    private int quantity = 1;
    private float discountAmount = 0;

    public LineItem() {
    }

    public float getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(float discountAmount) {
		this.discountAmount = discountAmount;
	}

	public int getLineItemId() {
        return lineItemId;
    }

    public void setLineItemId(int lineItemId) {
        this.lineItemId = lineItemId;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getTotal() {
    	float discount = (100 - discountAmount)/100;
    	BigDecimal price = product.getProductPrice().multiply(new BigDecimal(discount));
        BigDecimal total = price.multiply(new BigDecimal(quantity));
        return total;
    }

    public String getTotalCurrencyFormat() {
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        if (currency instanceof DecimalFormat) {
            DecimalFormat df = (DecimalFormat) currency;
            DecimalFormatSymbols dfs = new DecimalFormat().getDecimalFormatSymbols();
            dfs.setCurrencySymbol("тенге");
            df.setDecimalFormatSymbols(dfs);
        }
        return currency.format(this.getTotal());
    }
}