package kz.foodmaster.filial.controller;


import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import kz.foodmaster.filial.business.*;
import kz.foodmaster.filial.data.*;
import kz.foodmaster.filial.util.*;

public class OrderController extends HttpServlet {
    private static final String defaultURL = "/catalog";
    
    @Override
    public void doPost(HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException {
        
    	String requestURI = request.getRequestURI();
        String url = "";
          
        if (requestURI.endsWith("/updateItem")) {
            url = updateItem(request, response);
        } else if (requestURI.endsWith("/removeItem")) {
            url = removeItem(request, response);
        } else if (requestURI.endsWith("/checkUser")) {
            url = checkUser(request, response);
        } else if (requestURI.endsWith("/displayOrder")) {
            url = displayOrder(request, response);
        } else if (requestURI.endsWith("/displayUser")) {
            url = "/cart/user.jsp";
        } else if (requestURI.endsWith("/completeOrder")) {
            url = completeOrder(request, response);
        }  else if (requestURI.endsWith("/login")) {
            url = authorizeUser(request, response);
        }
        
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    	String requestURI = request.getRequestURI();
        String url = defaultURL;
        
        if (requestURI.endsWith("/showCart")) {
            url = showCart(request, response);
        } else if (requestURI.endsWith("/checkUser")) {
            url = checkUser(request, response);
        } else if (requestURI.endsWith("/addItem")) {
            url = addItem(request, response);
        } else if (requestURI.endsWith("/login")) {
            url = checkAuthorized(request, response);
        }
        
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }
    
    
    private String authorizeUser(HttpServletRequest request, HttpServletResponse response) {
    	
    	HttpSession session = request.getSession();
    	
    	String url = "/login/login.jsp";
    	
    	String message = "";
    	
    	Client client = (Client)session.getAttribute("client");
    	
    	if (client != null) {
    		url = "/login/welcome.jsp";
    		message = "Вы уже авторизованы на сайте под логином: " + client.getClientLogin();
    		request.setAttribute("message", message);
    		return url;
    	}
    	
    	String login = request.getParameter("login");
        String password = request.getParameter("password");
        
        message = "Произошла неизвестная ошибка.";
        
        
        if (!ClientDB.clientExists(login)) {
        	message = "Клиента с таким логином/паролем не существует.";	
        } else {
        	client = ClientDB.selectClientByLoginAndPassword(login, password);
        	if (client != null) {
            	url = "/login/welcome.jsp";
            	message = "Вы успешно авторизованы!";
            	session.setAttribute("client", client);
        	} else {
        		message = "Клиента с таким логином/паролем не существует.";
        	}
        }

        request.setAttribute("message", message);
        return url;
    }
    
    
    private String showCart(HttpServletRequest request, HttpServletResponse response) {
        
    	HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null || cart.getCount() == 0) {
            request.setAttribute("emptyCart", "Your cart is empty");
        } else {
            request.getSession().setAttribute("cart", cart);
        }

        List<Measure> measures = MeasureDB.selectMeasures();
        List<Packaging> packages = PackageDB.selectPackages();
        
        request.setAttribute("measures", measures);
        request.setAttribute("packages", packages);
        
        return "/cart/cart.jsp";
    }
    
    
    private String addItem(HttpServletRequest request, HttpServletResponse response) {
        
    	HttpSession session = request.getSession();
        Cart cart = (Cart)session.getAttribute("cart");
        if (cart == null) cart = new Cart();
        
        String productID = request.getParameter("productID");
        String categoryID = request.getParameter("categoryID");
        
        Product product = ProductDB.selectProduct(productID);
        if (product != null) {
            LineItem lineItem = new LineItem();
            lineItem.setProduct(product);
            float discountAmount = DiscountDB.selectDiscountForProduct(Integer.parseInt(productID));
            lineItem.setDiscountAmount(discountAmount);
            cart.addItem(lineItem);
        }
        session.setAttribute("cart", cart);
        return "/catalog?categoryID=" + categoryID;
    }
    
    
    private String updateItem(HttpServletRequest request, HttpServletResponse response) {
        
    	String quantityString = request.getParameter("quantity");
        String productID = request.getParameter("productID");
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        int quantity;
        try {
            quantity = Integer.parseInt(quantityString);
            if (quantity < 0)
                quantity = 1;
        } catch (NumberFormatException ex) {
            quantity = 1;
        }
       
        Product product = ProductDB.selectProduct(productID);
        if (product != null && cart != null) {
            LineItem lineItem = new LineItem();
            lineItem.setProduct(product);
            lineItem.setQuantity(quantity);
            if (quantity > 0)
                cart.addItem(lineItem);
            else
                cart.removeItem(lineItem);
        }
        
        List<Measure> measures = MeasureDB.selectMeasures();
        List<Packaging> packages = PackageDB.selectPackages();
        
        request.setAttribute("measures", measures);
        request.setAttribute("packages", packages);
        
        return "/cart/cart.jsp";
    }
    
    
    private String removeItem(HttpServletRequest request, HttpServletResponse response) {
        
    	HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        String productID = request.getParameter("productID");
        Product product = ProductDB.selectProduct(productID);
        if (product != null && cart != null) {
            LineItem lineItem = new LineItem();
            lineItem.setProduct(product);
            cart.removeItem(lineItem);
        }
        
        if (cart.getCount() == 0) {
            request.setAttribute("emptyCart", "Your cart is empty");
        }
        
        return "/cart/cart.jsp";
    }
    
    
    private String checkAuthorized(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();
        Client client = (Client) session.getAttribute("client");
        String message;
        
        String url = "/login/login.jsp";
        
        if (client != null) {      
            message = "Вы уже авторизованы на сайте под логином: " + client.getClientLogin();
    		request.setAttribute("message", message);
    		url = "/login/welcome.jsp";
        }

        return url;
    }
    
    
    private String checkUser(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();
        Client client = (Client) session.getAttribute("client");
        String message;
        
        String url = "/cart/cart.jsp";
        
        if (client != null) {
            url = "/order/displayOrder";
        } else {
        	message = "Вы не авторизованы.\nПожалуйста авторизуйтесь в системе чтобы продолжить покупку";
            request.setAttribute("message", message);
        }

        return url;
    }

    
    private String displayOrder(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();        
        Client client = (Client) session.getAttribute("client");       
        Cart cart = (Cart) session.getAttribute("cart");

        java.util.Date today = new java.util.Date();

        Order order = new Order();
        order.setClient(client);
        order.setOrderDate(today);
        order.setLineItems(cart.getItems());
        
        session.setAttribute("order", order);
        
        return "/cart/order.jsp";
    }
    
    
    private String completeOrder(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();
        Order order = (Order)session.getAttribute("order");
        String message = "";

        if (OrderDB.insert(order)) {
        	session.setAttribute("cart", null);
        	
        	message = "Ваш заказ принят. Наш экспедитор получил письменное уведомление о Вашем заказе."
        			+ " В течение 8 рабочих часов он свяжется с Вами, чтобы подтвердить Ваш заказ.";
        	
        	
        	final String fromEmail = "foodmasterfilial@gmail.com"; //requires valid gmail id
    		final String password = "fD47mrtQ"; // correct password for gmail id
    		final String toEmail = "ekuyubaev@gmail.com"; // can be any email id 
    		String mailBody = "Новый заказ. " + ".\n\n" +
    				"Дата: " + order.getOrderDateDefaultFormat() + ". \n\n" +
    				"На сумму: " + order.getOrderTotalCurrencyFormat() + ". \n\n" +
    				"От клиента: " + order.getClient().getClientName() + ". \n\n" +
    				"Телефон: " + order.getClient().getClientPhone();
    		Properties props = new Properties();
    		props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
    		props.put("mail.smtp.port", "587"); //TLS Port
    		props.put("mail.smtp.auth", "true"); //enable authentication
    		props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
    		
                    //create Authenticator object to pass in Session.getInstance argument
    		Authenticator auth = new Authenticator() {
    			//override the getPasswordAuthentication method
    			protected PasswordAuthentication getPasswordAuthentication() {
    				return new PasswordAuthentication(fromEmail, password);
    			}
    		};
    		javax.mail.Session mailSession = javax.mail.Session.getInstance(props, auth);
    		
    		EmailUtil.sendEmail(mailSession, toEmail,"Новый заказ", mailBody);	
    	}
        else message = "В процессе оформления заказа произошла ошибка. Проверьте данные и попробуйте снова.";

        request.setAttribute("message", message);
        return "/cart/complete.jsp";
    } 
}
