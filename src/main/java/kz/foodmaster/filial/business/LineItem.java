package kz.foodmaster.filial.business;


import java.text.NumberFormat;
import java.io.Serializable;
import java.math.BigDecimal;

public class LineItem implements Serializable {
	private static final long serialVersionUID = 1L;
	
    private int lineItemId;
    private Product product;
    private int quantity = 1;

    public LineItem() {
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
        BigDecimal total = product.getProductPrice().multiply(new BigDecimal(quantity));
        return total;
    }

    public String getTotalCurrencyFormat() {
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        return currency.format(this.getTotal());
    }
}