package kz.foodmaster.filial.business;

import java.util.*;
import java.text.*;
import java.io.Serializable;
import java.math.BigDecimal;

public class Order implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int orderId;
	private Client client;
    private List<LineItem> lineItems;
    private Date ordereDate;
    private Employee executor;
    private Date processedDate;
    private boolean isProcessed;

    public Order() {
    	processedDate = null;
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

	public Date getOrdereDate() {
		return ordereDate;
	}

	public void setOrdereDate(Date ordereDate) {
		this.ordereDate = ordereDate;
	}

	public String getOrderDateDefaultFormat() {
        DateFormat dateFormat = DateFormat.getDateInstance();
        String invoiceDateFormatted = dateFormat.format(ordereDate);
        return invoiceDateFormatted;
    }

    public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
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
		return isProcessed;
	}

	public void setProcessed(boolean isProcessed) {
		this.isProcessed = isProcessed;
	}
}
