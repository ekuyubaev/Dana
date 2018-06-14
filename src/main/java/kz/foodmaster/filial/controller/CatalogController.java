package kz.foodmaster.filial.controller;


import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kz.foodmaster.filial.business.Category;
import kz.foodmaster.filial.business.Measure;
import kz.foodmaster.filial.business.News;
import kz.foodmaster.filial.business.Product;
import kz.foodmaster.filial.data.CategoryDB;
import kz.foodmaster.filial.data.MeasureDB;
import kz.foodmaster.filial.data.NewsDB;
import kz.foodmaster.filial.data.ProductDB;

public class CatalogController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	String categoryCode = request.getParameter("categoryID");
        String action = request.getRequestURI();
        String url = null;
        
        if (action.endsWith("/catalog")) {
        	url = showProductsByCategory(request, response, categoryCode);
        } else if (action.endsWith("/") || action.endsWith("/index")) {
        	url = showMainPage(request, response, categoryCode);
        }
 
        request.getRequestDispatcher(url)
            .forward(request, response);
    }

    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	doGet(request, response);
    }
    
    
    private String showProduct(HttpServletRequest request, HttpServletResponse response) {   	
    	
    	List<Category> categories = CategoryDB.selectCategories();
        HttpSession session = request.getSession();
        session.setAttribute("categories", categories);   
        return "/catalog/index.jsp";
    }
    
    
    private String showProductsByCategory(HttpServletRequest request, HttpServletResponse response, String categoryCode) {
    	
    	HttpSession session = request.getSession();
    	
    	if (categoryCode != null) {
            List<Product> products = ProductDB.selectProductsByCategory(categoryCode);     
            session.setAttribute("products", products);       
        } 
    	
    	List<Measure> measures = MeasureDB.selectMeasures();
        request.setAttribute("measures", measures);
        List<Category> categories = CategoryDB.selectCategories();
        request.setAttribute("categories", categories);
        return "/catalog/index.jsp";
    }
  
    
    private String showMainPage(HttpServletRequest request, HttpServletResponse response, String categoryCode) {
    	
    	List<News> newsList = NewsDB.selectNewsList();     
        request.setAttribute("newsList", newsList);       
    	
        return "/index.jsp";
    }
}
