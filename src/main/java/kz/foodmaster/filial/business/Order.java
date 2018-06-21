package kz.foodmaster.filial.business;

import java.util.*;

import kz.foodmaster.filial.util.Num;

import java.text.*;
import java.io.Serializable;
import java.math.BigDecimal;

public class Order implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int orderID;
	private Client client;
    private List<LineItem> lineItems;
    private Date orderDate;
    private Date processedDate;
    private boolean processed;
    private boolean confirmed;
    private boolean cancelled;
    private boolean approved;
    private BigDecimal sum;

	public Order() {
    	processedDate = null;
    }
	
    public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
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
	
	
	public String num2str(boolean stripkop) {
        String[][] sex = {
            {"","один","два","три","четыре","пять","шесть","семь","восемь","девять"},
            {"","одна","две","три","четыре","пять","шесть","семь","восемь","девять"},
        };
        String[] str100= {"","сто","двести","триста","четыреста","пятьсот","шестьсот","семьсот", "восемьсот","девятьсот"};
        String[] str11 = {"","десять","одиннадцать","двенадцать","тринадцать","четырнадцать", "пятнадцать","шестнадцать","семнадцать","восемнадцать","девятнадцать","двадцать"};
        String[] str10 = {"","десять","двадцать","тридцать","сорок","пятьдесят","шестьдесят", "семьдесят","восемьдесят","девяносто"};
        String[][] forms = {
            {"тиын", "тиын", "тиын", "1"},
            {"тенге", "тенге", "тенге", "0"},
            {"тысяча", "тысячи", "тысяч", "1"},
            {"миллион", "миллиона", "миллионов", "0"},
            {"миллиард","миллиарда","миллиардов","0"},
            {"триллион","триллиона","триллионов","0"},

        };
        // получаем отдельно рубли и копейки
        Long rub = this.getOrderTotal().longValue();
        String[] moi = this.getOrderTotal().toString().split("\\.");
        Long kop = Long.valueOf(moi[1]);
        
        String result = Num.toString(rub.intValue()) + " тенге " + Num.toString(kop.intValue()) + " тиын";
        
        return result;
        
        
        /*if (!moi[1].substring(0,1).equals("0") ) {// начинается не с нуля
            if (kop<10 )
                kop *=10;
        }
        String kops = String.valueOf(kop);
        if (kops.length()==1 )
            kops = "0"+kops;
        
        long rub_tmp = rub;
        // Разбиватель суммы на сегменты по 3 цифры с конца
        ArrayList segments = new ArrayList();
        while(rub_tmp>999) {
            long seg = rub_tmp/1000;
            segments.add( rub_tmp-(seg*1000) );
            rub_tmp=seg;
        }
        segments.add( rub_tmp );
        Collections.reverse(segments);
        // Анализируем сегменты
        String o = "";
        if (rub == 0) {// если Ноль
            o = "ноль "+ morph( 0, forms[1][ 0],forms[1][1],forms[1][2]);
            if (stripkop)
                return o;
            else
                return o +" "+kop+" "+ morph(kop,forms[ 0][ 0],forms[ 0][1],forms[ 0][2]);
        }
        // Больше нуля
        int lev = segments.size();
        for (int i= 0; i<segments.size(); i++ ) {// перебираем сегменты
            int sexi = (int)Integer.valueOf( forms[lev][3].toString() );// определяем род
            int ri = (int)Integer.valueOf( segments.get(i).toString() );// текущий сегмент
            if (ri== 0 && lev>1) {// если сегмент ==0 И не последний уровень(там Units)
                lev--;
                continue;
            }
            String rs = String.valueOf(ri); // число в строку
            // нормализация
            if (rs.length()==1) rs = "00"+rs;// два нулика в префикс?
            if (rs.length()==2) rs = "0"+rs; // или лучше один?
            // получаем циферки для анализа
            int r1 = (int)Integer.valueOf( rs.substring( 0,1) ); //первая цифра
            int r2 = (int)Integer.valueOf( rs.substring(1,2) ); //вторая
            int r3 = (int)Integer.valueOf( rs.substring(2,3) ); //третья
            int r22= (int)Integer.valueOf( rs.substring(1,3) ); //вторая и третья
            // Супер-нано-анализатор циферок
            if (ri>99) o += str100[r1]+" "; // Сотни
            if (r22>20) {// >20
                o += str10[r2]+" ";
                o += sex[ sexi ][r3]+" ";
            }
            else { // <=20
                if (r22>9) o += str11[r22-9]+" "; // 10-20
                else o += sex[ sexi ][r3]+" "; // 0-9
            }
            // Единицы измерения (рубли...)
            o += morph(ri, forms[lev][ 0],forms[lev][1],forms[lev][2])+" ";
            lev--;
        }
        // Копейки в цифровом виде
        if (stripkop) {
            o = o.replaceAll(" {2,}", " ");
        }
        else {
            o = o+""+kops+" "+morph(kop,forms[ 0][ 0],forms[ 0][1],forms[ 0][2]);
            o = o.replaceAll(" {2,}", " ");
        }
        return o;*/
    }
 
    /**<br/>     * Склоняем словоформу<br/>     * @param n Long количество объектов<br/>     * @param f1 String вариант словоформы для одного объекта<br/>     * @param f2 String вариант словоформы для двух объектов<br/>     * @param f5 String вариант словоформы для пяти объектов<br/>     * @return String правильный вариант словоформы для указанного количества объектов<br/>     */
    public static String morph(long n, String f1, String f2, String f5) {
        n = Math.abs(n) % 100;
        long n1 = n % 10;
        if (n > 10 && n < 20) return f5;
        if (n1 > 1 && n1 < 5) return f2;
        if (n1 == 1) return f1;
        return f5;
    }
}
