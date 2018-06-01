package kz.foodmaster.filial.controller;


import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kz.foodmaster.filial.business.Category;
import kz.foodmaster.filial.business.Discount;
import kz.foodmaster.filial.business.Measure;
import kz.foodmaster.filial.business.Order;
import kz.foodmaster.filial.business.Product;
import kz.foodmaster.filial.business.Topic;
import kz.foodmaster.filial.data.CategoryDB;
import kz.foodmaster.filial.data.DiscountDB;
import kz.foodmaster.filial.data.MeasureDB;
import kz.foodmaster.filial.data.OrderDB;
import kz.foodmaster.filial.data.ProductDB;
import kz.foodmaster.filial.data.TopicDB;

public class AdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        String requestURI = request.getRequestURI();
        System.out.println("The " + requestURI + " was got in doPost.");
        String url = "/admin";
        if (requestURI.endsWith("/updateCategory")) {
            url = updateCategory(request, response);
        } else if (requestURI.endsWith("/insertCategory")) {
            url = insertCategory(request, response);
        } else if (requestURI.endsWith("/displayCategories")) {
        	url = displayCategories(request, response);
        } else if (requestURI.endsWith("/updateProduct")) {
            url = updateProduct(request, response);
        } else if (requestURI.endsWith("/insertProduct")) {
            url = insertProduct(request, response);
        } else if (requestURI.endsWith("/displayProducts")) {
        	url = displayProducts(request, response);
        }  else if (requestURI.endsWith("/updateDiscount")) {
            url = updateDiscount(request, response);
        } else if (requestURI.endsWith("/insertDiscount")) {
            url = insertDiscount(request, response);
        } else if (requestURI.endsWith("/displayDiscounts")) {
        	url = displayDiscounts(request, response);
        }  else if (requestURI.endsWith("/confirmOrder")) {
        	url = confirmOrder(request, response);
        }   else if (requestURI.endsWith("/cancelOrder")) {
        	url = cancelOrder(request, response);
        }   else if (requestURI.endsWith("/displayOrders")) {
        	url = displayOrders(request, response);
        }   else if (requestURI.endsWith("/completeOrder")) {
        	url = completeOrder(request, response);
        } else if (requestURI.endsWith("/updateTopic")) {
            url = updateTopic(request, response);
        } else if (requestURI.endsWith("/insertTopic")) {
            url = insertTopic(request, response);
        } else if (requestURI.endsWith("/displayTopics")) {
        	url = displayTopics(request, response);
        }

        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }

    
    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        String requestURI = request.getRequestURI();
        System.out.println("The " + requestURI + " was got in doGet.");
        String url = "/admin";
        if (requestURI.endsWith("/displayCategories")) {
            url = displayCategories(request, response);
        } else if (requestURI.endsWith("/editCategory")) {
            url = editCategory(request, response);
        } else if (requestURI.endsWith("/addCategory")) {
            url = addCategory(request, response);
        } else if (requestURI.endsWith("/deleteCategory")) {
            url = deleteCategory(request, response);
        } else if (requestURI.endsWith("/displayProducts")) {
            url = displayProducts(request, response);
        } else if (requestURI.endsWith("/editProduct")) {
            url = editProduct(request, response);
        } else if (requestURI.endsWith("/addProduct")) {
            url = addProduct(request, response);
        } else if (requestURI.endsWith("/deleteProduct")) {
            url = deleteProduct(request, response);
        } else if (requestURI.endsWith("/displayDiscounts")) {
            url = displayDiscounts(request, response);
        } else if (requestURI.endsWith("/editDiscount")) {
            url = editDiscount(request, response);
        } else if (requestURI.endsWith("/addDiscount")) {
            url = addDiscount(request, response);
        } else if (requestURI.endsWith("/deleteDiscount")) {
            url = deleteDiscount(request, response);
        } else if (requestURI.endsWith("/displayOrders")) {
            url = displayOrders(request, response);
        } else if (requestURI.endsWith("/displayOrder")) {
            url = displayOrder(request, response);
        } else if (requestURI.endsWith("/displayTopics")) {
            url = displayTopics(request, response);
        } else if (requestURI.endsWith("/addTopic")) {
            url = addTopic(request, response);
        }  else if (requestURI.endsWith("/editTopic")) {
            url = editTopic(request, response);
        }   else if (requestURI.endsWith("/deleteTopic")) {
            url = deleteTopic(request, response);
        }

        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }

    
    private String displayCategories(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        List<Category> categories = CategoryDB.selectCategories();
        
        String url;
        if (categories != null) {
            if (categories.size() <= 0) {
            	categories = null;
            }
        }
        
        HttpSession session = request.getSession();
        session.setAttribute("categories", categories);
        url = "/admin/categories.jsp";
        return url;
    }
    
    
    private String editCategory(HttpServletRequest request,
            HttpServletResponse response) {

        String categoryID = request.getParameter("categoryID");
        
        Category category = CategoryDB.selectCategory(categoryID);

        request.setAttribute("category", category);

        return "/admin/CategoryForm.jsp";
    }

    
    private String updateCategory(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        int categoryID = Integer.parseInt(request.getParameter("categoryID"));
        String categoryName = request.getParameter("categoryName");
        String categoryNote = request.getParameter("categoryNote");
        
        Category category = new Category(categoryID, categoryName, categoryNote);

        CategoryDB.updateCategory(category);

        return "/adminController/displayCategories";
    }
    
    
    private String insertCategory(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
    	
        Category category = new Category();
        category.setCategoryName(request.getParameter("categoryName"));
        category.setCategoryNote(request.getParameter("categoryNote"));

        CategoryDB.insertCategory(category);

        return "/adminController/displayCategories";
    }
    
    
    private String addCategory(HttpServletRequest request,
            HttpServletResponse response) {

        return "/admin/CategoryForm.jsp";
    }
    
    
    private String deleteCategory(HttpServletRequest request,
            HttpServletResponse response) {

    	int categoryID = Integer.parseInt(request.getParameter("categoryID"));
    	CategoryDB.deleteCategory(categoryID);
        return "/adminController/displayCategories";
    }
    
    
    private String displayProducts(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        List<Product> products = ProductDB.selectProducts();
        List<Category> categories = CategoryDB.selectCategories();
        List<Measure> measures = MeasureDB.selectMeasures();
        
        String url;
        if (products != null) {
            if (products.size() <= 0) {
            	products = null;
            }
        }
        
        request.setAttribute("categories", categories);
        request.setAttribute("products", products);
        request.setAttribute("measures", measures);
        
        url = "/admin/products.jsp";
        return url;
    }
    
    
    private String editProduct(HttpServletRequest request,
            HttpServletResponse response) {

        String productID = request.getParameter("productID");
        
        Product product = ProductDB.selectProduct(productID);

        request.setAttribute("product", product);
        
        List<Category> categories = CategoryDB.selectCategories();
        List<Measure> measures = MeasureDB.selectMeasures();
        request.setAttribute("categories", categories);
        request.setAttribute("measures", measures);
        
        return "/admin/ProductForm.jsp";
    }

    private String updateProduct(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        
    	int productID = Integer.parseInt(request.getParameter("productID"));
        String productName = request.getParameter("productName");
        int productMeasureID = Integer.parseInt(request.getParameter("productMeasureID"));
        int productCategoryID = Integer.parseInt(request.getParameter("productCategoryID"));
        float productQuantity = Float.parseFloat(request.getParameter("productQuantity"));
        BigDecimal productPrice = BigDecimal.valueOf(Float.parseFloat(request.getParameter("productPrice")));
        String productNote = request.getParameter("productNote");
        
        Product product = new Product();
        
        product.setProductID(productID);
        product.setProductName(productName);
        product.setProductMeasureID(productMeasureID);
        product.setProductCategoryID(productCategoryID);
        product.setProductQuantity(productQuantity);
        product.setProductPrice(productPrice);
        product.setProductNote(productNote);

        ProductDB.updateProduct(product);

        return "/adminController/displayProducts";
    }
    
    
    private String addProduct(HttpServletRequest request,
            HttpServletResponse response) {
    	
    	List<Category> categories = CategoryDB.selectCategories();
        List<Measure> measures = MeasureDB.selectMeasures();
        request.removeAttribute("categories");
        request.setAttribute("categories", categories);
        request.removeAttribute("measures");
        request.setAttribute("measures", measures);
    	
        return "/admin/ProductForm.jsp";
    }
    
    
    private String insertProduct(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
    	
        String productName = request.getParameter("productName");
        
        String productMeasureIDString = request.getParameter("productMeasureID");
        System.out.println("MeasureID = " + productMeasureIDString);
        int productMeasureID = 0;
        if (productMeasureIDString != null)
        	productMeasureID = Integer.parseInt(productMeasureIDString);       
        
        int productCategoryID = 0;
        String productCategoryIDString = request.getParameter("productCategoryID");
        System.out.println("CategoryID = " + productCategoryIDString);
        if (productCategoryIDString != null)
        	productCategoryID = Integer.parseInt(productCategoryIDString);
        
        float productQuantity = Float.parseFloat(request.getParameter("productQuantity"));
        BigDecimal productPrice = BigDecimal.valueOf(Float.parseFloat(request.getParameter("productPrice")));
        String productNote = request.getParameter("productNote");
        
        Product product = new Product();
        
        product.setProductName(productName);
        product.setProductMeasureID(productMeasureID);
        product.setProductCategoryID(productCategoryID);
        product.setProductQuantity(productQuantity);
        product.setProductPrice(productPrice);
        product.setProductNote(productNote);

        ProductDB.insertProduct(product);

        return "/adminController/displayProducts";
    }
    
        
    private String deleteProduct(HttpServletRequest request,
            HttpServletResponse response) {

    	int productID = Integer.parseInt(request.getParameter("productID"));
    	ProductDB.deleteProduct(productID);
        return "/adminController/displayProducts";
    }
    
    
    private String displayDiscounts(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        List<Discount> discounts = DiscountDB.selectDiscounts();
        
        String url;
        if (discounts != null) {
            if (discounts.size() <= 0) {
            	discounts = null;
            }
        }
        
        request.setAttribute("discounts", discounts);
        
        url = "/admin/discounts.jsp";
        
        return url;
    }
    
    
    private String editDiscount(HttpServletRequest request,
            HttpServletResponse response) {

        String discountID = request.getParameter("discountID");
        
        Discount discount = DiscountDB.selectDiscount(discountID);

        request.setAttribute("discount", discount);
        
        return "/admin/DiscountForm.jsp";
    }

    private String updateDiscount(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        
    	int discountID = Integer.parseInt(request.getParameter("discountID"));
        String discountName = request.getParameter("discountName");
        float discountAmount = Float.parseFloat(request.getParameter("discountAmount"));
        Date discountStart = Date.valueOf(request.getParameter("discountStart"));
        Date discountEnd = Date.valueOf(request.getParameter("discountEnd"));
        
        Discount discount= new Discount();
        
        discount.setDiscountID(discountID);
        discount.setDiscountName(discountName);
        discount.setDiscountAmount(discountAmount);
        discount.setDiscountStart(discountStart);
        discount.setDiscountEnd(discountEnd);

        DiscountDB.updateDiscount(discount);

        return "/adminController/displayDiscounts";
    }
    
    
    private String addDiscount(HttpServletRequest request,
            HttpServletResponse response) {
    	
        return "/admin/DiscountForm.jsp";
    }
    
    
    private String insertDiscount(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
    	
        String discountName = request.getParameter("discountName");       
        float discountAmount = Float.parseFloat(request.getParameter("discountAmount"));  
        Date discountStart = Date.valueOf(request.getParameter("discountStart"));
        Date discountEnd = Date.valueOf(request.getParameter("discountEnd"));
        
        Discount discount = new Discount();
        
        discount.setDiscountName(discountName);;
        discount.setDiscountAmount(discountAmount);;
        discount.setDiscountStart(discountStart);;
        discount.setDiscountEnd(discountEnd);;

        DiscountDB.insertDiscount(discount);

        return "/adminController/displayDiscounts";
    }
    
        
    private String deleteDiscount(HttpServletRequest request,
            HttpServletResponse response) {

    	int discountID = Integer.parseInt(request.getParameter("discountID"));
    	DiscountDB.deleteDiscount(discountID);
        return "/adminController/displayDiscounts";
    }
    
    
    private String displayOrders(HttpServletRequest request,
            HttpServletResponse response) {

        int filter = 1;
        String filterStr = request.getParameter("orderFilter");
        
        if (!(filterStr == null)) filter = Integer.parseInt(filterStr);
        ArrayList<Order> orders = OrderDB.selectOrders(filter);
        
        request.setAttribute("orders", orders);
        request.setAttribute("orderFilter", filterStr);
        
        return "/admin/orders.jsp";
    }
    
    
    private String displayOrder(HttpServletRequest request,
            HttpServletResponse response) {

        int orderID = Integer.parseInt(request.getParameter("orderID"));
        Order order = OrderDB.selectOrder(orderID);
        
        request.setAttribute("order", order);
        
        return "/admin/OrderForm.jsp";
    }
    
    
    private String confirmOrder(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        
    	int orderID = Integer.parseInt(request.getParameter("orderID"));

        OrderDB.confirmOrder(orderID);

        return "/adminController/displayOrders";
    }
    
    
    private String cancelOrder(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        
    	int orderID = Integer.parseInt(request.getParameter("orderID"));

        OrderDB.cancelOrder(orderID);

        return "/adminController/displayOrders";
    }
    
    
    private String completeOrder(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        
    	int orderID = Integer.parseInt(request.getParameter("orderID"));

        OrderDB.finishOrder(orderID);

        return "/adminController/displayOrders";
    }
    
    
    private String displayTopics(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        
    	List<Topic> topics = TopicDB.selectTopics();
    	request.setAttribute("topics", topics);

        return "/admin/topics.jsp";
    }
    
    
    private String addTopic(HttpServletRequest request,
            HttpServletResponse response) {

        return "/admin/TopicForm.jsp";
    }
    
    private String editTopic(HttpServletRequest request,
            HttpServletResponse response) {

        int topicID = Integer.parseInt(request.getParameter("topicID"));  
        
        Topic topic = TopicDB.selectTopic(topicID);

        request.setAttribute("topic", topic);
        
        System.out.println("Topic name = " + topic.getName());

        return "/admin/TopicForm.jsp";
    }

    
    private String updateTopic(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        int topicID = Integer.parseInt(request.getParameter("topicID"));
        String topicName = request.getParameter("topicName");
        
        Topic topic = new Topic();
        
        topic.setID(topicID);
        topic.setName(topicName);

        TopicDB.updateTopic(topic);

        return "/adminController/displayTopics";
    }
    
    
    private String insertTopic(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
    	
        Topic topic = new Topic();
        topic.setName(request.getParameter("topicName"));

        TopicDB.insertTopic(topic);

        return "/adminController/displayTopics";
    }
    
    
    private String deleteTopic(HttpServletRequest request,
            HttpServletResponse response) {

    	int topicID = Integer.parseInt(request.getParameter("topicID"));
    	TopicDB.deleteTopic(topicID);
        return "/adminController/displayTopics";
    }
}
