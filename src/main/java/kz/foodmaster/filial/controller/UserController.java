package kz.foodmaster.filial.controller;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import kz.foodmaster.filial.business.Client;
import kz.foodmaster.filial.business.Measure;
import kz.foodmaster.filial.business.Order;
import kz.foodmaster.filial.business.Packaging;
import kz.foodmaster.filial.business.User;
import kz.foodmaster.filial.data.ClientDB;
import kz.foodmaster.filial.data.MeasureDB;
import kz.foodmaster.filial.data.OrderDB;
import kz.foodmaster.filial.data.PackageDB;
import kz.foodmaster.filial.data.RoleDB;
import kz.foodmaster.filial.data.UserDB;

public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	
        String requestURI = request.getRequestURI();
        System.out.println("In doGet with URI = " + requestURI);
        String url = "";
        
        if (requestURI.endsWith("/register")) {
            url = "/login/user.jsp";
        }  else if (requestURI.endsWith("/orders")) {
            url = displayClientOrders(request, response);
        }  else if (requestURI.endsWith("/showCabinet")) {
            url = "/cabinet/index.jsp";
        }  else if (requestURI.endsWith("/displayClientOrder")) {
            url = displayClientOrder(request, response);
        } else if (requestURI.endsWith("/breakSession")) {
            url = breakSession(request, response);
        } 
        
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }

    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	
        String requestURI = request.getRequestURI();
        System.out.println("In doPost with URI = " + requestURI);
        String url = "";
        
        if (requestURI.endsWith("/addClient")) {
        	url = addClient(request, response);
        }  else if (requestURI.endsWith("/register")) {
            url = "/login/user.jsp";
        }  else if (requestURI.endsWith("/cancelOrder")) {
            url = cancelOrder(request, response);
        }  else if (requestURI.endsWith("/orders")) {
            url = displayClientOrders(request, response);
        }  else if (requestURI.endsWith("/printContract")) {
            printContract(request, response);
        }  else if (requestURI.endsWith("/displayClientOrder")) {
            url = displayClientOrder(request, response);
        }  else if (requestURI.endsWith("/confirmOrder")) {
        	url = confirmOrder(request, response);
        } else if (requestURI.endsWith("/completeOrder")) {
        	url = completeOrder(request, response);
        }
        
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }

    
    private String completeOrder(HttpServletRequest request, HttpServletResponse response) {
        
    	int orderID = Integer.parseInt(request.getParameter("orderID"));

        OrderDB.finishOrder(orderID);

        return "/userController/orders";
    }
    
    
    private String confirmOrder(HttpServletRequest request, HttpServletResponse response) {
        
    	int orderID = Integer.parseInt(request.getParameter("orderID"));

        OrderDB.confirmOrder(orderID);

        return "/userController/orders";
    }
    
    
    private String breakSession(HttpServletRequest request, HttpServletResponse response) {
    	
    	HttpSession session = request.getSession(false);
    	//session.setAttribute("client", null);
    	
    	if (session != null) session.invalidate();
    	//request.getSession(true);
    	
    	return "/index.jsp";
    }
    
    
    private String addClient(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		
		String clientName = request.getParameter("clientName");
		String clientBirthDate = request.getParameter("clientBirthDate");
		String clientMail = request.getParameter("clientMail");
		String clientPhone = request.getParameter("clientPhone");
		String clientAdress = request.getParameter("clientAdress");	
		String clientLogin = request.getParameter("clientLogin");
		String password = request.getParameter("password");
		String passwordConfirmation = request.getParameter("passwordConfirmation"); 
		
		Client client = new Client();
		
		client.setClientName(clientName);
		client.setClientBirthDate(Date.valueOf(clientBirthDate));
		client.setClientMail(clientMail);
		client.setClientPhone(clientPhone);
		client.setClientAdress(clientAdress);
		client.setClientLogin(clientLogin);
		
		String message = "";
		
		if (ClientDB.clientExists(clientLogin)) {
			client.setClientLogin("");
			request.setAttribute("client", client);
		    request.setAttribute("password", password);
		    request.setAttribute("passwordConfirmation", passwordConfirmation);  
		    message = "Клиент с таким логином уже существует. Выберите другой логин.";
		    request.setAttribute("message", message);
		    
		    return "/userController/register";
		} else if (!password.equals(passwordConfirmation)) {
			request.setAttribute("client", client);  
		    message = "Пароли не совпадают.";
		    request.setAttribute("message", message);
		    
		    return "/userController/register";
		} else {
		    User user = new User();
		    user.setUserLogin(clientLogin);
		    user.setUserPass(password);
		    UserDB.insert(user);
		    session.setAttribute("user", user);
		    
		    ClientDB.insert(client);
		    session.setAttribute("client", ClientDB.selectClient(clientLogin));
		    
		    RoleDB.insertRole("guest", clientLogin);
		}
		
		String url = "/login/thanks.jsp";
		return url;
    } 
    
    
    private String displayClientOrders(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();
        Client client = (Client)session.getAttribute("client");
        
        if (client == null) {
        	String message = "Вы не авторизованы.";
        	request.setAttribute("message", message);
        	return "/cabinet/index.jsp";
        }
    	
    	int clientID = client.getClientId();
        
    	List<Order> orders = null;
    	String orderFilter = request.getParameter("orderFilter");
    	if (orderFilter == null) orderFilter = "4"; 
    		
    	orders = OrderDB.selectClientOrders(clientID, Integer.parseInt(orderFilter));
        
        request.setAttribute("orders", orders);
        request.setAttribute("orderFilter", orderFilter);
        
        return "/cabinet/clientOrders.jsp";
    }
    
    
    private String displayClientOrder(HttpServletRequest request, HttpServletResponse response) {

    	String orderID = request.getParameter("orderID");
    		
    	Order order = OrderDB.selectOrder(Integer.parseInt(orderID));     
        request.setAttribute("order", order);
        
        List<Measure> measures = MeasureDB.selectMeasures();
        request.setAttribute("measures", measures);
        
        List<Packaging> packages = PackageDB.selectPackages();
        request.setAttribute("packages", packages);
        
        return "/cabinet/clientOrder.jsp";
    }
    
    
    private String cancelOrder(HttpServletRequest request, HttpServletResponse response) {

    	String orderID = request.getParameter("orderID");
    		
    	OrderDB.cancelOrder(Integer.parseInt(orderID));
        
        return "/userController/orders";
    }
    
    
    private void printContract(HttpServletRequest request, HttpServletResponse response) {
    	
    	String orderID = request.getParameter("orderID");
    	String path = request.getSession().getServletContext().getRealPath("/");
    	path += "WEB-INF\\classes\\templates\\Dogovor_na_postavku.docx";

    	XWPFDocument  doc = null;
    	OutputStream  out = null;
    	
    	Order order = OrderDB.selectOrder(Integer.parseInt(orderID));
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(order.getOrderDate());
    	
    	try {
			doc = new XWPFDocument (new FileInputStream(path));

			replaceText(doc, "nomer", String.valueOf(order.getOrderID()));
			replaceText(doc, "dd", String.format("%02d", cal.get(Calendar.DAY_OF_MONTH)));
			replaceText(doc, "mm", String.format("%02d", cal.get(Calendar.MONTH)));
			replaceText(doc, "yyyy", String.valueOf(cal.get(Calendar.YEAR)));
			System.out.println(order.getClient().getClientName());
			replaceText(doc, "client", order.getClient().getClientName());
			replaceText(doc, "adress", order.getClient().getClientAdress());
			replaceText(doc, "contractsum", order.getOrderTotalCurrencyFormat());

			XWPFTable tbl = doc.getTables().get(1);
			
			for(int i=0; i < order.getLineItems().size(); i++) {
				XWPFTableRow row =tbl.createRow();
				//tbl.addRow(row);
        		tbl.getRow(i+1).getCell(0).setText(String.valueOf(i+1));
        		tbl.getRow(i+1).getCell(1).setText(order.getLineItems().get(i).getProduct().getProductName());
        		tbl.getRow(i+1).getCell(2).setText(String.valueOf(order.getLineItems().get(i).getQuantity()));
        		
        		BigDecimal price = order.getLineItems().get(i).getTotal().divide(new BigDecimal(order.getLineItems().get(i).getQuantity()));
        		NumberFormat currency = NumberFormat.getCurrencyInstance();
                if (currency instanceof DecimalFormat) {
                    DecimalFormat df = (DecimalFormat) currency;
                    DecimalFormatSymbols dfs = new DecimalFormat().getDecimalFormatSymbols();
                    dfs.setCurrencySymbol("тенге");
                    df.setDecimalFormatSymbols(dfs);
                }
                String priceStr = currency.format(price);
        		
        		tbl.getRow(i+1).getCell(3).setText(priceStr);
        		tbl.getRow(i+1).getCell(4).setText(String.valueOf(order.getLineItems().get(i).getTotalCurrencyFormat()));
        	}
	               
	        response.setContentType("application/msword");
	        response.addHeader("Content-Disposition", "attachment; filename=Dogovor_na_postavku.docx");
	        out = response.getOutputStream();
	        doc.write(out);
			out.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				doc.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    	
        //return "/userController/displayClientOrder";
    }
    
    
    private void replaceText(XWPFDocument doc, String findText, String replaceText) {
    	for (XWPFParagraph p : doc.getParagraphs()) {
    	    List<XWPFRun> runs = p.getRuns();
    	    if (runs != null) {
    	        for (XWPFRun r : runs) {
    	            String text = r.getText(0);
    	            if (text != null && text.contains(findText)) {
    	                text = text.replace(findText, replaceText);
    	                r.setText(text, 0);
    	            }
    	        }
    	    }
    	}
    	
    	for (XWPFTable tbl : doc.getTables()) {
    		   for (XWPFTableRow row : tbl.getRows()) {
    		      for (XWPFTableCell cell : row.getTableCells()) {
    		         for (XWPFParagraph p : cell.getParagraphs()) {
    		            for (XWPFRun r : p.getRuns()) {
    		              String text = r.getText(0);
    		              if (text != null && text.contains(findText)) {
    		                text = text.replace(findText, replaceText);
    		                r.setText(text,0);
    		              }
    		            }
    		         }
    		      }
    		   }
    		}
    }
}
