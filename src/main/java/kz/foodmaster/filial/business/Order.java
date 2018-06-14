package kz.foodmaster.filial.business;

import java.util.*;
import java.text.*;
import java.io.Serializable;
import java.math.BigDecimal;

public class Order implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int orderID;
	private Client client;
    private List<LineItem> lineItems;
    private Date orderDate;
    private Employee executor;
    private Date processedDate;
    private boolean processed;
    private boolean confirmed;
    private boolean cancelled;
    private BigDecimal sum;

	public Order() {
    	processedDate = null;
    }
	
    public String getSumCurrencyFormat() {
    	NumberFormat currency = NumberFormat.getCurrencyInstance();
        if (currency instanceof DecimalFormat) {
            DecimalFormat df = (DecimalFormat) currency;
            DecimalFormatSymbols dfs = new DecimalFormat().getDecimalFormatSymbols();
            dfs.setCurrencySymbol("тенге");
            df.setDecimalFormatSymbols(dfs);
        }
        String formattedSum = currency.format(this.getSum());
		return formattedSum;
	}

	public BigDecimal getSum() {
		return sum;
	}

	public void setSum(BigDecimal sum) {
		this.sum = sum;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public List<LineItem> getLineItems() {
		return lineItems;
	}

	public void setLineItems(List<LineItem> lineItems) {
		this.lineItems = lineItems;
	}

	public Date getOrderDate() {
		return orderDate;
	}
	
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderDateDefaultFormat() {
		SimpleDateFormat ft = 
			      new SimpleDateFormat ("dd.MM.yyyy HH:mm:ss");
        String invoiceDateFormatted = ft.format(orderDate);
        return invoiceDateFormatted;
    }

    public int getOrderID() {
		return orderID;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}


    public BigDecimal getOrderTotal() {
        BigDecimal orderTotal = BigDecimal.ZERO;
        for (LineItem item : lineItems) {
        	orderTotal = orderTotal.add(item.getTotal());
        }
        return orderTotal;
    }

    public String getOrderTotalCurrencyFormat() {
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        if (currency instanceof DecimalFormat) {
            DecimalFormat df = (DecimalFormat) currency;
            // use local/default decimal symbols with original currency symbol
            DecimalFormatSymbols dfs = new DecimalFormat().getDecimalFormatSymbols();
            dfs.setCurrencySymbol("тенге");
            df.setDecimalFormatSymbols(dfs);
        }
        String formattedTotal = currency.format(this.getOrderTotal());
        return formattedTotal;
    }

	public Employee getExecutor() {
		return executor;
	}

	public void setExecutor(Employee executor) {
		this.executor = executor;
	}

	public Date getProcessedDate() {
		return processedDate;
	}

	public void setProcessedDate(Date processedDate) {
		this.processedDate = processedDate;
	}

	public boolean isProcessed() {
		return processed;
	}

	public void setProcessed(boolean processed) {
		this.processed = processed;
	}

	public boolean isConfirmed() {
		return confirmed;
	}

	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

	public boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
}
