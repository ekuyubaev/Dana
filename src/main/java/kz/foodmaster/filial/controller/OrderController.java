package kz.foodmaster.filial.controller;


import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import kz.foodmaster.filial.business.*;
import kz.foodmaster.filial.data.*;
import kz.foodmaster.filial.util.*;

public class OrderController extends HttpServlet {
    private static final String defaultURL = "/catalog";
    
    @Override
    public void doPost(HttpServletRequest request, 
            HttpServletResponse response)
            throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String url = "";
        
        System.out.println("In doPost with request URI = " + requestURI);
          
        if (requestURI.endsWith("/updateItem")) {
            url = updateItem(request, response);
        } else if (requestURI.endsWith("/removeItem")) {
            url = removeItem(request, response);
        } else if (requestURI.endsWith("/checkUser")) {
            url = checkUser(request, response);
        } else if (requestURI.endsWith("/processUser")) {
            url = processUser(request, response);
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
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String url = defaultURL;
        
        System.out.println("In doGet with request URI = " + requestURI);
        
        if (requestURI.endsWith("/showCart")) {
            url = showCart(request, response);
        } else if (requestURI.endsWith("/checkUser")) {
            url = checkUser(request, response);
        } else if (requestURI.endsWith("/addItem")) {
            url = addItem(request, response);
        } else if (requestURI.endsWith("/login")) {
            url = "/login/login.jsp";
        }
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }
    
    
    private String authorizeUser(HttpServletRequest request,
            HttpServletResponse response) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        
        String errMessage = "Произошла неизвестная ошибка.";
        
        String url = "/login/login.jsp";
        
        if (!ClientDB.clientExists(login)) {
        	errMessage = "Клиента с таким логином/паролем не существует.";
        	request.setAttribute("message", errMessage);
        } else {
        	Client client = ClientDB.selectClientByLoginAndPassword(login, password);
        	if (client != null) {
            	url = "/login/welcome.jsp";
            	System.out.println("Клиент авторизовался на сайте.");
        	} else {
        		errMessage = "Неверный пароль.";
            	request.setAttribute("message", errMessage);
        	}
        }
        
        return url;
    }
    
    
    private String showCart(HttpServletRequest request,
            HttpServletResponse response) {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null || cart.getCount() == 0) {
            request.setAttribute("emptyCart", "Your cart is empty");
        } else {
            request.getSession().setAttribute("cart", cart);
        }
        return "/cart/cart.jsp";
    }
    
    private String addItem(HttpServletRequest request,
            HttpServletResponse response) {
        HttpSession session = request.getSession();
        Cart cart = (Cart)session.getAttribute("cart");
        if (cart == null) cart = new Cart();
        
        String productID = request.getParameter("productID");
        String categoryID = request.getParameter("categoryID");
        
        Product product = ProductDB.selectProduct(productID);
        if (product != null) {
            LineItem lineItem = new LineItem();
            lineItem.setProduct(product);
            cart.addItem(lineItem);
        }
        session.setAttribute("cart", cart);
        return "/catalog?categoryID=" + categoryID;
    }
    
    private String updateItem(HttpServletRequest request,
            HttpServletResponse response) {
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
        return "/cart/cart.jsp";
    }
    
    private String removeItem(HttpServletRequest request,
            HttpServletResponse response) {
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
    
    private String checkUser(HttpServletRequest request,
            HttpServletResponse response) {

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

    private String processUser(HttpServletRequest request,
            HttpServletResponse response) {
        
        String name = request.getParameter("name");
        String birthDate = request.getParameter("birthDate");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String notes = request.getParameter("notes");
        String login = request.getParameter("login");
        String pass = request.getParameter("pass");
        String roleID = request.getParameter("roleID");

        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
        }

        if (UserDB.loginExists(login)) {
            user = UserDB.selectUser(login);
            user.setUserPass(pass);
            user.setUserRoleId(Integer.parseInt(roleID));
            Client client = new Client();
            client.setClientName(name);
            try {
            	DateFormat format = new SimpleDateFormat("dd.mm.yyyy", Locale.getDefault());
            	client.setClientBirthDate(format.parse(birthDate));
            } catch(Exception e) {
            	e.printStackTrace();
            }
            client.setClientMail(email);
            client.setClientPhone(phone);
            client.setClientNotes(notes);
            
            //user.setClient(client);
            
            UserDB.update(user);
        } else {
        	user.setUserLogin(login);
            user.setUserPass(pass);
            user.setUserRoleId(Integer.parseInt(roleID));
            Client client = new Client();
            client.setClientName(name);
            try {
            	DateFormat format = new SimpleDateFormat("dd.mm.yyyy", Locale.getDefault());
            	client.setClientBirthDate(format.parse(birthDate));
            } catch(Exception e) {
            	e.printStackTrace();
            }
            client.setClientMail(email);
            client.setClientPhone(phone);
            client.setClientNotes(notes);
            
            //user.setClient(client);
            
            UserDB.update(user);
        }

        session.setAttribute("user", user);

        return "/order/displayOrder";
    }

    private String displayOrder(HttpServletRequest request,
            HttpServletResponse response) {

        HttpSession session = request.getSession();        
        User user = (User) session.getAttribute("user");       
        Cart cart = (Cart) session.getAttribute("cart");

        java.util.Date today = new java.util.Date();

        Order order = new Order();
        order.setUser(user);
        order.setOrdereDate(today);
        order.setLineItems(cart.getItems());
        
        session.setAttribute("order", order);
        
        return "/cart/order.jsp";
    }
    
    private String completeOrder(HttpServletRequest request,
            HttpServletResponse response) {

        HttpSession session = request.getSession();
        Order order = (Order) session.getAttribute("order");


        User user = order.getUser();
        
        // if a record for the User object exists, update it
        if (UserDB.loginExists(user.getUserLogin())) {
            UserDB.update(user);
        } else { // otherwise, write a new record for the user            
        	UserDB.insert(user);
        }        
        order.setUser(user);
        
        // write a new order record
        OrderDB.insert(order);
        
        // set the emailCookie in the user's browser.
        Cookie loginCookie = new Cookie("loginCookie",
        		user.getUserLogin());
        loginCookie.setMaxAge(60*24*365*2*60);
        loginCookie.setPath("/");
        response.addCookie(loginCookie);

        // remove all items from the user's cart
        session.setAttribute("cart", null);
        
        // send an email to the user to confirm the order.
        //String to = user.getClient().getClientMail();
        String from = "kzfoodmasterfilial@gmail.com";
        String subject = "Order Confirmation";
        /*String body = "�������(-��)" + user.getClient().getClientName() + ",\n\n" +
            "������� �� ��� �����. " +
            "�� ������ �������� ���� ����� � ������� 3-5 ������� ����. " + 
            "� ���� �������� ��� ��������� ��� ������������� ������.\n" +
            "�������� ��� � ������� ��� ���!\n\n" +
            "������ ��� ������\n";*/
        boolean isBodyHTML = false;
        /*try {
            //MailUtil.sendMail(to, from, subject, body, isBodyHTML);
        }
        catch(MessagingException e) {
            this.log(
                "Unable to send email. \n" +
                "You may need to configure your system as " +
                "described in chapter 15. \n" +
                "Here is the email you tried to send: \n" +
                "=====================================\n" +
                "TO: " + to + "\n" +
                "FROM: " + from + "\n" +
                "SUBJECT: " + subject + "\n" +
                "\n" +
                body + "\n\n");
        }*/
        
        return "/cart/complete.jsp";
    }    
}
