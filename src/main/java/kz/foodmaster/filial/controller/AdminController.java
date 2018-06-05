package kz.foodmaster.filial.controller;


import java.io.IOException;
import java.math.BigDecimal;
import java.security.Principal;
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
import kz.foodmaster.filial.business.Employee;
import kz.foodmaster.filial.business.Measure;
import kz.foodmaster.filial.business.News;
import kz.foodmaster.filial.business.Order;
import kz.foodmaster.filial.business.Position;
import kz.foodmaster.filial.business.Product;
import kz.foodmaster.filial.business.Topic;
import kz.foodmaster.filial.business.Transport;
import kz.foodmaster.filial.business.User;
import kz.foodmaster.filial.data.CategoryDB;
import kz.foodmaster.filial.data.DiscountDB;
import kz.foodmaster.filial.data.EmployeeDB;
import kz.foodmaster.filial.data.MeasureDB;
import kz.foodmaster.filial.data.NewsDB;
import kz.foodmaster.filial.data.OrderDB;
import kz.foodmaster.filial.data.PositionDB;
import kz.foodmaster.filial.data.ProductDB;
import kz.foodmaster.filial.data.TopicDB;
import kz.foodmaster.filial.data.TransportDB;

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
        } else if (requestURI.endsWith("/updateEmployee")) {
            url = updateEmployee(request, response);
        } else if (requestURI.endsWith("/insertEmployee")) {
            url = insertEmployee(request, response);
        } else if (requestURI.endsWith("/displayEmployees")) {
        	url = displayEmployees(request, response);
        } else if (requestURI.endsWith("/updateTransport")) {
            url = updateTransport(request, response);
        } else if (requestURI.endsWith("/insertTransport")) {
            url = insertTransport(request, response);
        } else if (requestURI.endsWith("/displayTransports")) {
        	url = displayTransports(request, response);
        } else if (requestURI.endsWith("/updateNews")) {
            url = updateNews(request, response);
        } else if (requestURI.endsWith("/insertNews")) {
            url = insertNews(request, response);
        } else if (requestURI.endsWith("/displayNews")) {
        	url = displayNews(request, response);
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
        } else if (requestURI.endsWith("/displayEmployees")) {
            url = displayEmployees(request, response);
        } else if (requestURI.endsWith("/addEmployee")) {
            url = addEmployee(request, response);
        }  else if (requestURI.endsWith("/editEmployee")) {
            url = editEmployee(request, response);
        }   else if (requestURI.endsWith("/deleteEmployee")) {
            url = deleteEmployee(request, response);
        } else if (requestURI.endsWith("/displayTransports")) {
            url = displayTransports(request, response);
        } else if (requestURI.endsWith("/addTransport")) {
            url = addTransport(request, response);
        }  else if (requestURI.endsWith("/editTransport")) {
            url = editTransport(request, response);
        }   else if (requestURI.endsWith("/deleteTransport")) {
            url = deleteTransport(request, response);
        } else if (requestURI.endsWith("/displayNews")) {
            url = displayNews(request, response);
        } else if (requestURI.endsWith("/addNews")) {
            url = addNews(request, response);
        }  else if (requestURI.endsWith("/editNews")) {
            url = editNews(request, response);
        }   else if (requestURI.endsWith("/deleteNews")) {
            url = deleteNews(request, response);
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
        int productMeasureID = 0;
        if (productMeasureIDString != null)
        	productMeasureID = Integer.parseInt(productMeasureIDString);       
        
        int productCategoryID = 0;
        String productCategoryIDString = request.getParameter("productCategoryID");
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
    
    
    private String displayEmployees(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        List<Employee> employees = EmployeeDB.selectEmployees();
        List<Position> positions = PositionDB.selectPositions();
        
        String url;
        
        request.setAttribute("employees", employees);
        request.setAttribute("positions", positions);
        
        url = "/admin/Employees.jsp";
        return url;
    }
    
    
    private String editEmployee(HttpServletRequest request,
            HttpServletResponse response) {

        int ID = Integer.parseInt(request.getParameter("ID"));
        
        Employee employee = EmployeeDB.selectEmployee(ID);

        request.setAttribute("employee", employee);
        
        List<Position> positions = PositionDB.selectPositions();
        request.setAttribute("positions", positions);
        
        return "/admin/EmployeeForm.jsp";
    }

    private String updateEmployee(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        
    	int ID = Integer.parseInt(request.getParameter("ID"));
        String name = request.getParameter("name");
        int positionID = Integer.parseInt(request.getParameter("positionID"));
        Date birthDate = Date.valueOf(request.getParameter("birthDate"));
        String phone = request.getParameter("phone");
        String notes = request.getParameter("notes");
        
        Employee employee = new Employee();
        
        employee.setEmployeeId(ID);
        employee.setEmployeeName(name);
        employee.setPositionId(positionID);
        employee.setEmployeeBirthDate(birthDate);
        employee.setEmployeePhone(phone);
        employee.setEmployeeNotes(notes);

        EmployeeDB.update(employee);

        return "/adminController/displayEmployees";
    }
    
    
    private String addEmployee(HttpServletRequest request,
            HttpServletResponse response) {
    	
    	List<Position> positions = PositionDB.selectPositions();
        request.removeAttribute("positions");
        request.setAttribute("positions", positions);
    	
        return "/admin/EmployeeForm.jsp";
    }
    
    
    private String insertEmployee(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
    	
        String name = request.getParameter("name");
        
        String positionIDString = request.getParameter("positionID");
        int positionID = 0;
        if (positionIDString != null)
        	positionID = Integer.parseInt(positionIDString);       
        
        Date birthDate = Date.valueOf(request.getParameter("birthDate"));
        String phone = request.getParameter("phone");
        String notes = request.getParameter("notes");
        
        Employee employee = new Employee();
        
        employee.setEmployeeName(name);
        employee.setPositionId(positionID);
        employee.setEmployeeBirthDate(birthDate);
        employee.setEmployeePhone(phone);
        employee.setEmployeeNotes(notes);

        EmployeeDB.insert(employee);

        return "/adminController/displayEmployees";
    }
    
        
    private String deleteEmployee(HttpServletRequest request,
            HttpServletResponse response) {

    	int ID = Integer.parseInt(request.getParameter("ID"));
    	EmployeeDB.delete(ID);
        return "/adminController/displayEmployees";
    }
    
    
    private String displayTransports(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        List<Transport> transports = TransportDB.selectTransports();
        
        String url;
        
        request.setAttribute("transports", transports);
        
        url = "/admin/Transports.jsp";
        return url;
    }
    
    
    private String editTransport(HttpServletRequest request,
            HttpServletResponse response) {

        int ID = Integer.parseInt(request.getParameter("ID"));
        
        Transport transport = TransportDB.selectTransport(ID);

        request.setAttribute("transport", transport);
        
        return "/admin/TransportForm.jsp";
    }
    

    private String updateTransport(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        
    	int ID = Integer.parseInt(request.getParameter("ID"));
        String model = request.getParameter("model");
        String number = request.getParameter("number");
        int capacity = Integer.parseInt(request.getParameter("capacity"));
        boolean fridge = (request.getParameter("fridge") != null &&
							request.getParameter("fridge").equals("on"));
        String notes = request.getParameter("notes");
        
        Transport transport = new Transport();
        
        transport.setID(ID);
        transport.setModel(model);
        transport.setNumber(number);
        transport.setCapacity(capacity);
        transport.setFridge(fridge);
        transport.setNotes(notes);

        TransportDB.update(transport);

        return "/adminController/displayTransports";
    }
    
    
    private String addTransport(HttpServletRequest request,
            HttpServletResponse response) {
    	
        return "/admin/TransportForm.jsp";
    }
    
    
    private String insertTransport(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
    	
        String model = request.getParameter("model");
        String number = request.getParameter("number");
        int capacity = Integer.parseInt(request.getParameter("capacity"));
        boolean fridge = (request.getParameter("fridge") != null &&
        					request.getParameter("fridge").equals("on"));
        String notes = request.getParameter("notes");
        
        Transport transport = new Transport();
        
        transport.setModel(model);
        transport.setNumber(number);
        transport.setCapacity(capacity);
        transport.setFridge(fridge);
        transport.setNotes(notes);

        TransportDB.insert(transport);

        return "/adminController/displayTransports";
    }
    
        
    private String deleteTransport(HttpServletRequest request,
            HttpServletResponse response) {

    	int ID = Integer.parseInt(request.getParameter("ID"));
    	TransportDB.delete(ID);
        return "/adminController/displayTransports";
    }
    
    
    private String displayNews(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        List<News> newsList = NewsDB.selectNewsList();
        
        String url;
        
        request.setAttribute("newsList", newsList);
        
        url = "/admin/News.jsp";
        return url;
    }
    
    
    private String editNews(HttpServletRequest request,
            HttpServletResponse response) {

        int ID = Integer.parseInt(request.getParameter("ID"));
        
        News news = NewsDB.selectNews(ID);

        request.setAttribute("news", news);
        
        return "/admin/NewsForm.jsp";
    }

    
    private String updateNews(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        
    	int ID = Integer.parseInt(request.getParameter("ID"));
        String title = request.getParameter("title");
        String text = request.getParameter("text");
        
        News news = new News();
        
        news.setID(ID);
        news.setTitle(title);
        news.setText(text);
        
        NewsDB.update(news);

        return "/adminController/displayNews";
    }
    
    
    private String addNews(HttpServletRequest request, HttpServletResponse response) {
        return "/admin/NewsForm.jsp";
    }
    
    
    private String insertNews(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
    	
        String title = request.getParameter("title");
        String text = request.getParameter("text");
        HttpSession session = request.getSession();
        Principal p = (Principal)request.getUserPrincipal();
        String message = null;
        String author = p.getName();
        
        if (p == null || author == null) {
        	message = "Вы не авторизованы в системе.";
        	request.setAttribute("message", message);
        	return "/adminController/addNews";
        }
          
        News news = new News();
        
        news.setTitle(title);
        news.setText(text);
        news.setAuthor(author);

        NewsDB.insert(news);

        return "/adminController/displayNews";
    }
    
        
    private String deleteNews(HttpServletRequest request,
            HttpServletResponse response) {

    	int ID = Integer.parseInt(request.getParameter("ID"));
    	NewsDB.delete(ID);
        return "/adminController/displayNews";
    }
}
