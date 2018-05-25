package kz.foodmaster.filial.controller;


import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import kz.foodmaster.filial.business.*;
import kz.foodmaster.filial.data.*;

public class CatalogController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    @Override
    public void doGet(HttpServletRequest request, 
            HttpServletResponse response)
            throws ServletException, IOException {
    	String categoryCode = request.getParameter("categoryID");
        String action = request.getServletPath();

        String url = null;
        url = showProductsByCategory(request, response, categoryCode);
 
        request.getRequestDispatcher(url)
            .forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
    	doGet(request, response);
    }
    
    private String showProduct(HttpServletRequest request, 
            HttpServletResponse response) {   	
    	List<Category> categories = CategoryDB.selectCategories();
        HttpSession session = request.getSession();
        session.setAttribute("categories", categories);   
        return "/catalog/index.jsp";
    }
    
    private String showProductsByCategory(HttpServletRequest request, 
            HttpServletResponse response, String categoryCode) {
    	
    	HttpSession session = request.getSession();
    	
    	if (categoryCode != null) {
            List<Product> products = ProductDB.selectProductsByCategory(categoryCode);     
            session.setAttribute("products", products);       
        } 
    	
        List<Category> categories = CategoryDB.selectCategories();
        session.setAttribute("categories", categories);
        return "/catalog/index.jsp";
    }
  
    
    /*private String registerUser(HttpServletRequest request,
            HttpServletResponse response) {

        HttpSession session = request.getSession();
        String clientName = request.getParameter("clientName");
        String clientPhone = request.getParameter("clientPhone");
        String clientMail = request.getParameter("clientMail");
        String clientNotes = request.getParameter("clientNotes");
        String clientBirthDate = request.getParameter("clientBirthDate");
        String login = request.getParameter("login");
        String pass = request.getParameter("pass");
        String roleIDString = request.getParameter("roleID"); 

        User user;
        if (UserDB.loginExists(login)) {
            user = UserDB.selectUser(login);
            user.setUserPass(pass);
            
            Client client = new Client();
            client.setClientId(user.getClient().getClientId());
            client.setClientName(clientName);
            try {
	            DateFormat format = new SimpleDateFormat("dd.mm.yyyy", Locale.getDefault());
	            Date birthDate = format.parse(clientBirthDate);
	            client.setClientBirthDate(birthDate);
            } catch (Exception e) {
            	e.printStackTrace();
            }
            client.setClientMail(clientMail);
            client.setClientPhone(clientPhone);
            client.setClientNotes(clientNotes);
            user.setClient(client); 
            
            UserDB.update(user);
        } else {
            user = new User();
            user.setUserLogin(login);
            user.setUserPass(pass);
            if (roleIDString != null)
            	user.setUserRoleId(Integer.parseInt(roleIDString));  
            
            Client client = new Client();
            client.setClientName(clientName);
            try {
	            DateFormat format = new SimpleDateFormat("dd.mm.yyyy", Locale.getDefault());
	            Date birthDate = format.parse(clientBirthDate);
	            client.setClientBirthDate(birthDate);
            } catch (Exception e) {
            	e.printStackTrace();
            }
            client.setClientMail(clientMail);
            client.setClientPhone(clientPhone);
            client.setClientNotes(clientNotes);
            user.setClient(client); 
            
            UserDB.insert(user);
        }

        session.setAttribute("user", user);

        Cookie loginCookie = new Cookie("loginCookie", login);
        loginCookie.setMaxAge(60 * 60 * 24 * 365 * 2);
        loginCookie.setPath("/");
        response.addCookie(loginCookie);
        
        Product product = (Product) session.getAttribute("product");
        String url = "/catalog/" + product.getProductId() + "/sound.jsp";
        return url;
    }   */ 
}
