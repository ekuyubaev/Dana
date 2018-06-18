package kz.foodmaster.filial.controller;


import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kz.foodmaster.filial.business.Category;
import kz.foodmaster.filial.business.Client;
import kz.foodmaster.filial.business.Discount;
import kz.foodmaster.filial.business.Distance;
import kz.foodmaster.filial.business.Measure;
import kz.foodmaster.filial.business.Order;
import kz.foodmaster.filial.business.Plan;
import kz.foodmaster.filial.business.Product;
import kz.foodmaster.filial.business.Transport;
import kz.foodmaster.filial.data.CategoryDB;
import kz.foodmaster.filial.data.ClientDB;
import kz.foodmaster.filial.data.DiscountDB;
import kz.foodmaster.filial.data.DistanceDB;
import kz.foodmaster.filial.data.MeasureDB;
import kz.foodmaster.filial.data.OrderDB;
import kz.foodmaster.filial.data.ProductDB;
import kz.foodmaster.filial.data.TransportDB;

public class AdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String requestURI = request.getRequestURI();
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
        } else if (requestURI.endsWith("/updateTransport")) {
            url = updateTransport(request, response);
        } else if (requestURI.endsWith("/insertTransport")) {
            url = insertTransport(request, response);
        } else if (requestURI.endsWith("/displayTransports")) {
        	url = displayTransports(request, response);
        } else if (requestURI.endsWith("/findOrders")) {
        	url = findOrders(request, response);
        } else if (requestURI.endsWith("/calcPlan")) {
        	url = calcPlan(request, response);
        }

        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }

    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String requestURI = request.getRequestURI();
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
        } else if (requestURI.endsWith("/displayTransports")) {
            url = displayTransports(request, response);
        } else if (requestURI.endsWith("/addTransport")) {
            url = addTransport(request, response);
        } else if (requestURI.endsWith("/editTransport")) {
            url = editTransport(request, response);
        } else if (requestURI.endsWith("/deleteTransport")) {
            url = deleteTransport(request, response);
        } else if (requestURI.endsWith("/breakSession")) {
            url = breakSession(request, response);
        } else if (requestURI.endsWith("/displayPlan")) {
            url = "/admin/plan.jsp";
        } else if (requestURI.endsWith("/displayReports")) {
            url = "/admin/reports.jsp";
        } else if (requestURI.endsWith("/displayClients")) {
            url = displayClients(request, response);
        }

        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }

    
    private String displayCategories(HttpServletRequest request, HttpServletResponse response) {

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
    
    
    private String editCategory(HttpServletRequest request, HttpServletResponse response) {

        String categoryID = request.getParameter("categoryID");
        
        Category category = CategoryDB.selectCategory(categoryID);

        request.setAttribute("category", category);

        return "/admin/CategoryForm.jsp";
    }

    
    private String updateCategory(HttpServletRequest request, HttpServletResponse response)
    {
        int categoryID = Integer.parseInt(request.getParameter("categoryID"));
        String categoryName = request.getParameter("categoryName");
        String categoryNote = request.getParameter("categoryNote");
        
        Category category = new Category(categoryID, categoryName, categoryNote);

        CategoryDB.updateCategory(category);

        return "/adminController/displayCategories";
    }
    
    
    private String insertCategory(HttpServletRequest request, HttpServletResponse response) {
    	
        Category category = new Category();
        category.setCategoryName(request.getParameter("categoryName"));
        category.setCategoryNote(request.getParameter("categoryNote"));

        CategoryDB.insertCategory(category);

        return "/adminController/displayCategories";
    }
    
    
    private String addCategory(HttpServletRequest request, HttpServletResponse response) {

        return "/admin/CategoryForm.jsp";
    }
    
    
    private String deleteCategory(HttpServletRequest request, HttpServletResponse response) {

    	int categoryID = Integer.parseInt(request.getParameter("categoryID"));
    	CategoryDB.deleteCategory(categoryID);
        return "/adminController/displayCategories";
    }
    
    
    private String displayProducts(HttpServletRequest request, HttpServletResponse response) {

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
    
    
    private String editProduct(HttpServletRequest request, HttpServletResponse response) {

        String productID = request.getParameter("productID");
        
        Product product = ProductDB.selectProduct(productID);

        request.setAttribute("product", product);
        
        List<Category> categories = CategoryDB.selectCategories();
        List<Measure> measures = MeasureDB.selectMeasures();
        request.setAttribute("categories", categories);
        request.setAttribute("measures", measures);
        
        return "/admin/ProductForm.jsp";
    }

    
    private String updateProduct(HttpServletRequest request, HttpServletResponse response) {
        
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
    
    
    private String addProduct(HttpServletRequest request, HttpServletResponse response) {
    	
    	List<Category> categories = CategoryDB.selectCategories();
        List<Measure> measures = MeasureDB.selectMeasures();
        request.removeAttribute("categories");
        request.setAttribute("categories", categories);
        request.removeAttribute("measures");
        request.setAttribute("measures", measures);
    	
        return "/admin/ProductForm.jsp";
    }
    
    
    private String insertProduct(HttpServletRequest request, HttpServletResponse response) {
    	
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
    
        
    private String deleteProduct(HttpServletRequest request, HttpServletResponse response) {

    	int productID = Integer.parseInt(request.getParameter("productID"));
    	ProductDB.deleteProduct(productID);
        return "/adminController/displayProducts";
    }
    
    
    private String displayDiscounts(HttpServletRequest request, HttpServletResponse response) {

        List<Discount> discounts = DiscountDB.selectDiscounts();
        
        String url;
        if (discounts != null) {
            if (discounts.size() <= 0) {
            	discounts = null;
            }
        }
        
        request.setAttribute("discounts", discounts);
        
        List<Product> products = ProductDB.selectProducts();
        request.setAttribute("products", products);
        
        url = "/admin/discounts.jsp";
        
        return url;
    }
    
    
    private String editDiscount(HttpServletRequest request, HttpServletResponse response) {

        String discountID = request.getParameter("discountID");
        
        Discount discount = DiscountDB.selectDiscount(discountID);
        request.setAttribute("discount", discount);
        
        List<Product> products = ProductDB.selectProducts();
        request.setAttribute("products", products);
        
        return "/admin/DiscountForm.jsp";
    }

    
    private String updateDiscount(HttpServletRequest request, HttpServletResponse response) {
        
    	int discountID = Integer.parseInt(request.getParameter("discountID"));
        String discountName = request.getParameter("discountName");
        float discountAmount = Float.parseFloat(request.getParameter("discountAmount"));
        Date discountStart = Date.valueOf(request.getParameter("discountStart"));
        Date discountEnd = Date.valueOf(request.getParameter("discountEnd"));
        int productID = 0;
        if (request.getParameter("productID") != null)
        	productID = Integer.parseInt(request.getParameter("productID"));
        int categoryID = 0;
        if (request.getParameter("categoryID") != null)
        	productID = Integer.parseInt(request.getParameter("categoryID"));
        
        Discount discount= new Discount();
        
        discount.setDiscountID(discountID);
        discount.setDiscountName(discountName);
        discount.setDiscountAmount(discountAmount);
        discount.setDiscountStart(discountStart);
        discount.setDiscountEnd(discountEnd);
        discount.setProductID(productID);
        discount.setCategoryID(categoryID);

        DiscountDB.updateDiscount(discount);

        return "/adminController/displayDiscounts";
    }
    
    
    private String addDiscount(HttpServletRequest request, HttpServletResponse response) {
    	List<Product> products = ProductDB.selectProducts();
    	request.setAttribute("products", products);
        return "/admin/DiscountForm.jsp";
    }
    
    
    private String insertDiscount(HttpServletRequest request, HttpServletResponse response) {
    	
        String discountName = request.getParameter("discountName");       
        float discountAmount = Float.parseFloat(request.getParameter("discountAmount"));  
        Date discountStart = Date.valueOf(request.getParameter("discountStart"));
        Date discountEnd = Date.valueOf(request.getParameter("discountEnd"));
        int productID = 0;
        if (request.getParameter("productID") != null) 
        	productID = Integer.parseInt(request.getParameter("productID"));
        
        Discount discount = new Discount();
        
        discount.setDiscountName(discountName);
        discount.setDiscountAmount(discountAmount);
        discount.setDiscountStart(discountStart);
        discount.setDiscountEnd(discountEnd);
        discount.setProductID(productID);

        DiscountDB.insertDiscount(discount);

        return "/adminController/displayDiscounts";
    }
    
        
    private String deleteDiscount(HttpServletRequest request, HttpServletResponse response) {

    	int discountID = Integer.parseInt(request.getParameter("discountID"));
    	DiscountDB.deleteDiscount(discountID);
        return "/adminController/displayDiscounts";
    }
    
    
    private String displayOrders(HttpServletRequest request, HttpServletResponse response) {

        int filter = 1;
        String filterStr = request.getParameter("orderFilter");
        
        if (!(filterStr == null)) filter = Integer.parseInt(filterStr);
        ArrayList<Order> orders = OrderDB.selectOrders(filter);
        
        request.setAttribute("orders", orders);
        request.setAttribute("orderFilter", filterStr);
        
        return "/admin/orders.jsp";
    }
    
    
    private String displayOrder(HttpServletRequest request, HttpServletResponse response) {

        int orderID = Integer.parseInt(request.getParameter("orderID"));
        Order order = OrderDB.selectOrder(orderID);
        
        request.setAttribute("order", order);
        
        return "/admin/OrderForm.jsp";
    }
    
    
    private String confirmOrder(HttpServletRequest request, HttpServletResponse response) {
        
    	int orderID = Integer.parseInt(request.getParameter("orderID"));

        OrderDB.confirmOrder(orderID);

        return "/adminController/displayOrders";
    }
    
    
    private String cancelOrder(HttpServletRequest request, HttpServletResponse response) {
        
    	int orderID = Integer.parseInt(request.getParameter("orderID"));

        OrderDB.cancelOrder(orderID);

        return "/adminController/displayOrders";
    }
    
    
    private String completeOrder(HttpServletRequest request, HttpServletResponse response) {
        
    	int orderID = Integer.parseInt(request.getParameter("orderID"));

        OrderDB.finishOrder(orderID);

        return "/adminController/displayOrders";
    }
    
    
    private String displayTransports(HttpServletRequest request, HttpServletResponse response) {

        List<Transport> transports = TransportDB.selectTransports();
        
        String url;
        
        request.setAttribute("transports", transports);
        
        url = "/admin/Transports.jsp";
        return url;
    }
    
    
    private String editTransport(HttpServletRequest request, HttpServletResponse response) {

        int ID = Integer.parseInt(request.getParameter("ID"));
        
        Transport transport = TransportDB.selectTransport(ID);

        request.setAttribute("transport", transport);
        
        return "/admin/TransportForm.jsp";
    }
    

    private String updateTransport(HttpServletRequest request, HttpServletResponse response) {
        
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
    
    
    private String addTransport(HttpServletRequest request, HttpServletResponse response) {
    	
        return "/admin/TransportForm.jsp";
    }
    
    
    private String insertTransport(HttpServletRequest request, HttpServletResponse response) {
    	
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
    
        
    private String deleteTransport(HttpServletRequest request, HttpServletResponse response) {

    	int ID = Integer.parseInt(request.getParameter("ID"));
    	TransportDB.delete(ID);
        return "/adminController/displayTransports";
    }
    
    
    private String breakSession(HttpServletRequest request, HttpServletResponse response) {
    	
    	HttpSession session = request.getSession();
    	
    	if (session != null) session.invalidate();
    	request.getSession(true);
    	
    	return "/index";
    }
    
    
    private String findOrders(HttpServletRequest request, HttpServletResponse response) {
    	
    	String ordersDateStr = request.getParameter("ordersDate");
    	SimpleDateFormat in = new SimpleDateFormat("yyyy-MM-dd");
    	java.util.Date date = new java.util.Date();
		try {
			date = in.parse(ordersDateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	List<Order> orders = OrderDB.selectOrders(in.format(date));
    	request.setAttribute("date", date);
    	List<Client> clients = Plan.getClientsList(orders);
    	String [][] matrix = new String[clients.size()+2][clients.size()+2];
    	matrix[0][1] = "Склад";
		for (int j=1; j<= clients.size(); j++) {
			matrix[0][j+1] = clients.get(j-1).getClientName(); 
		}
		matrix[1][0] = "Склад";
		for (int i=1; i<= clients.size(); i++) {
			matrix[i+1][0] = clients.get(i-1).getClientName(); 
		}
		
		for (int i=1; i<=clients.size()+1; i++) {
			for(int j=1; j<= clients.size()+1; j++)
			{
				if (i==1 && j==1) matrix[i][j] = String.valueOf(0);
				else if (i == 1 && j != 1) {
					Distance d = DistanceDB.haveDistance(0, clients.get(j-2).getClientId());
					if (d != null) matrix[i][j] = String.valueOf(d.getValue());
				} else if (i != 1 && j == 1) {
					Distance d = DistanceDB.haveDistance(clients.get(i-2).getClientId(), 0);
					if (d != null) matrix[i][j] = String.valueOf(d.getValue());
				} else {
					Distance d = DistanceDB.haveDistance(clients.get(i-2).getClientId(), clients.get(j-2).getClientId());
					if (d != null) matrix[i][j] = String.valueOf(d.getValue());
				}
			}
		}
		
		System.out.println("--------After reading from database------------");
		for (int i=1; i <= clients.size()+1; i++) {
			for (int j=1; j <= clients.size()+1; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println("");
		}
		System.out.println("-----------------------------------------");
		
		request.setAttribute("matrix", matrix);
		request.setAttribute("clients", clients);
    	
    	return "/admin/plan.jsp";
    }
    
    private String calcPlan(HttpServletRequest request, HttpServletResponse response) {	
    	String date = request.getParameter("date");
    	String plainPlan = request.getParameter("plainPlan");
    	System.out.println(plainPlan);

    	List<Order> orders = OrderDB.selectOrders(date);
    	List<Client> clients = Plan.getClientsList(orders);
    	Plan plan = new Plan();
    	plan.setClients(orders);
    	
    	if (plainPlan != null && plainPlan.equals("on")) {
    		for(int i=1; i<=clients.size()+1; i++) 
    			for(int j=1; j<=clients.size()+1; j++) 
    				plan.setEl(i-1, j-1, 1);
    	}
    	else {
	    	for(int i=1; i<=clients.size()+1; i++) {
				for(int j=1; j<=clients.size()+1; j++) {
					String paramName = "cell"+i+j;
					if (request.getParameter(paramName)!=null && !request.getParameter(paramName).isEmpty()) { 
						float value = Float.parseFloat(request.getParameter(paramName)); 
						plan.setEl(i-1, j-1, value);
						if (i == 1 && j == 1) continue;
						else if (i == 1 && j != 1) {
							Distance d = DistanceDB.haveDistance(0, clients.get(j-2).getClientId());
							if (d == null) 
								DistanceDB.insertDistance(0, clients.get(j-2).getClientId(), value);
							else if (d.getValue() != value) 
								DistanceDB.updateDistance(0, clients.get(j-2).getClientId(), value);
						} else if (i != 1 && j == 1) {
							Distance d = DistanceDB.haveDistance(clients.get(i-2).getClientId(), 0);
							if (d == null) 
								DistanceDB.insertDistance(clients.get(i-2).getClientId(), 0, value);
							else if (d.getValue() != value) 
								DistanceDB.updateDistance(clients.get(i-2).getClientId(), 0, value);
						} else {
							Distance d = DistanceDB.haveDistance(clients.get(i-2).getClientId(), clients.get(j-2).getClientId());
							if (d == null) 
								DistanceDB.insertDistance(clients.get(i-2).getClientId(), clients.get(j-2).getClientId(), value);
							else if (d.getValue() != value) 
								DistanceDB.updateDistance(clients.get(i-2).getClientId(), clients.get(j-2).getClientId(), value);
						}
					}
					else {
						plan.setEl(i-1, j-1, 0);
					}
				}
	    	}
    	}
    	
    	clients.clear();
    	clients = plan.getPlan();
    	request.setAttribute("clients", clients);
    	request.setAttribute("orders", orders);
    			
    	
    	return "/admin/resultPlan.jsp";
    }
    
    
    private String displayClients(HttpServletRequest request, HttpServletResponse response) {	

    	List<Client> clients = ClientDB.selectClients();
    	request.setAttribute("clients", clients);
    			
    	
    	return "/admin/clients.jsp";
    }
}
