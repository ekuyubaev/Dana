package kz.foodmaster.filial.controller;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import kz.foodmaster.filial.business.Category;
import kz.foodmaster.filial.business.Client;
import kz.foodmaster.filial.business.Discount;
import kz.foodmaster.filial.business.Distance;
import kz.foodmaster.filial.business.LineItem;
import kz.foodmaster.filial.business.Measure;
import kz.foodmaster.filial.business.Order;
import kz.foodmaster.filial.business.Packaging;
import kz.foodmaster.filial.business.Plan;
import kz.foodmaster.filial.business.Product;
import kz.foodmaster.filial.business.Transport;
import kz.foodmaster.filial.data.CategoryDB;
import kz.foodmaster.filial.data.ClientDB;
import kz.foodmaster.filial.data.DiscountDB;
import kz.foodmaster.filial.data.DistanceDB;
import kz.foodmaster.filial.data.MeasureDB;
import kz.foodmaster.filial.data.OrderDB;
import kz.foodmaster.filial.data.PackageDB;
import kz.foodmaster.filial.data.ProductDB;
import kz.foodmaster.filial.data.TransportDB;

public class AdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String requestURI = request.getRequestURI();
        String url = "/admin";
        
        if (requestURI.endsWith("/updatePackage")) {
            url = updatePackage(request, response);
        } else if (requestURI.endsWith("/insertPackage")) {
            url = insertPackage(request, response);
        } else if (requestURI.endsWith("/displayPackages")) {
        	url = displayPackages(request, response);
        } else if (requestURI.endsWith("/updateMeasure")) {
            url = updateMeasure(request, response);
        } else if (requestURI.endsWith("/insertMeasure")) {
            url = insertMeasure(request, response);
        } else if (requestURI.endsWith("/displayMeasures")) {
        	url = displayMeasures(request, response);
        } else if (requestURI.endsWith("/updateCategory")) {
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
        } else if (requestURI.endsWith("/akt")) {
        	printAkt(request, response);
        } else if (requestURI.endsWith("/nakladnaia")) {
        	printNakladnaia(request, response);
        } else if (requestURI.endsWith("/salesReport")) {
        	salesReport(request, response);
        } else if (requestURI.endsWith("/monthReport")) {
        	monthReport(request, response);
        }

        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }

    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String requestURI = request.getRequestURI();
        String url = "/admin";
        
        
        if (requestURI.endsWith("/startPage")) {
            url = startPage(request, response);
        } else if (requestURI.endsWith("/displayPackages")) {
            url = displayPackages(request, response);
        } else if (requestURI.endsWith("/editPackage")) {
            url = editPackage(request, response);
        } else if (requestURI.endsWith("/addPackage")) {
            url = addPackage(request, response);
        } else if (requestURI.endsWith("/deletePackage")) {
            url = deletePackage(request, response);
        } else if (requestURI.endsWith("/displayMeasures")) {
            url = displayMeasures(request, response);
        } else if (requestURI.endsWith("/editMeasure")) {
            url = editMeasure(request, response);
        } else if (requestURI.endsWith("/addMeasure")) {
            url = addMeasure(request, response);
        } else if (requestURI.endsWith("/deleteMeasure")) {
            url = deleteMeasure(request, response);
        } else if (requestURI.endsWith("/displayCategories")) {
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
        } else if (requestURI.endsWith("/displayPlan")) {
            url = "/admin/plan.jsp";
        } else if (requestURI.endsWith("/displayReports")) {
            url = "/admin/reports.jsp";
        } else if (requestURI.endsWith("/displayClients")) {
            url = displayClients(request, response);
        } else if (requestURI.endsWith("/startPage")) {
            url = startPage(request, response);
        } else if (requestURI.endsWith("/printPlan")) {
            printPlan(request, response);
        }

        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }

    
    private String startPage(HttpServletRequest request, HttpServletResponse response) {

        if (OrderDB.selectNotExecutedOrders()) {
        	String message = "Имеются не выполненные и/или новые заказы. Вы можете просмотреть их на странице заказов.";
        	request.setAttribute("message", message);
        }

        return "/admin/index.jsp";
    }
    
    
    private String displayPackages(HttpServletRequest request, HttpServletResponse response) {

        List<Packaging> packages = PackageDB.selectPackages();
        
        String url;
        if (packages != null) {
            if (packages.size() <= 0) {
            	packages = null;
            }
        }
        
        request.setAttribute("packages", packages);
        url = "/admin/Packages.jsp";
        return url;
    }
    
    
    private String editPackage(HttpServletRequest request, HttpServletResponse response) {

        String ID = request.getParameter("ID");
        
        Packaging p = PackageDB.selectPackage(ID);

        request.setAttribute("p", p);

        return "/admin/PackageForm.jsp";
    }

    
    private String updatePackage(HttpServletRequest request, HttpServletResponse response)
    {
        int ID = Integer.parseInt(request.getParameter("ID"));
        String name = request.getParameter("name");
        
        Packaging p = new Packaging();
        p.setID(ID);
        p.setName(name);

        PackageDB.updatePackage(p);

        return "/adminController/displayPackages";
    }
    
    
    private String insertPackage(HttpServletRequest request, HttpServletResponse response) {
    	
    	Packaging p = new Packaging();
    	p.setName(request.getParameter("name"));

        PackageDB.insertPackage(p);

        return "/adminController/displayPackages";
    }
    
    
    private String addPackage(HttpServletRequest request, HttpServletResponse response) {

        return "/admin/PackageForm.jsp";
    }
    
    
    private String deletePackage(HttpServletRequest request, HttpServletResponse response) {

    	int ID = Integer.parseInt(request.getParameter("ID"));
    	PackageDB.deletePackage(ID);
        return "/adminController/displayPackages";
    }
    
    
    private String displayMeasures(HttpServletRequest request, HttpServletResponse response) {

        List<Measure> measures = MeasureDB.selectMeasures();
        
        String url;
        if (measures != null) {
            if (measures.size() <= 0) {
            	measures = null;
            }
        }
        
        request.setAttribute("measures", measures);
        url = "/admin/Measures.jsp";
        return url;
    }
    
    
    private String editMeasure(HttpServletRequest request, HttpServletResponse response) {

        String measureID = request.getParameter("measureID");
        
        Measure measure = MeasureDB.selectMeasure(measureID);

        request.setAttribute("measure", measure);

        return "/admin/MeasureForm.jsp";
    }

    
    private String updateMeasure(HttpServletRequest request, HttpServletResponse response)
    {
        int measureID = Integer.parseInt(request.getParameter("measureID"));
        String measureName = request.getParameter("measureName");
        
        Measure measure = new Measure();
        measure.setMeasureID(measureID);
        measure.setMeasureName(measureName);

        MeasureDB.updateMeasure(measure);

        return "/adminController/displayMeasures";
    }
    
    
    private String insertMeasure(HttpServletRequest request, HttpServletResponse response) {
    	
        Measure measure = new Measure();
        measure.setMeasureName(request.getParameter("measureName"));

        MeasureDB.insertMeasure(measure);

        return "/adminController/displayMeasures";
    }
    
    
    private String addMeasure(HttpServletRequest request, HttpServletResponse response) {

        return "/admin/MeasureForm.jsp";
    }
    
    
    private String deleteMeasure(HttpServletRequest request, HttpServletResponse response) {

    	int measureID = Integer.parseInt(request.getParameter("measureID"));
    	MeasureDB.deleteMeasure(measureID);
        return "/adminController/displayMeasures";
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
        List<Packaging> packages = PackageDB.selectPackages();
        
        String url;
        if (products != null) {
            if (products.size() <= 0) {
            	products = null;
            }
        }
        
        request.setAttribute("categories", categories);
        request.setAttribute("products", products);
        request.setAttribute("measures", measures);
        request.setAttribute("packages", packages);
        
        url = "/admin/products.jsp";
        return url;
    }
    
    
    private String editProduct(HttpServletRequest request, HttpServletResponse response) {

        String productID = request.getParameter("productID");
        
        Product product = ProductDB.selectProduct(productID);

        request.setAttribute("product", product);
        
        List<Category> categories = CategoryDB.selectCategories();
        List<Measure> measures = MeasureDB.selectMeasures();
        List<Packaging> packages = PackageDB.selectPackages();
        request.setAttribute("categories", categories);
        request.setAttribute("measures", measures);
        request.setAttribute("packages", packages);
        
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
        int productPackagingID = Integer.parseInt(request.getParameter("productPackagingID"));
        
        Product product = new Product();
        
        product.setProductID(productID);
        product.setProductName(productName);
        product.setProductMeasureID(productMeasureID);
        product.setProductCategoryID(productCategoryID);
        product.setProductQuantity(productQuantity);
        product.setProductPrice(productPrice);
        product.setProductNote(productNote);
        product.setProductPackagingID(productPackagingID);

        ProductDB.updateProduct(product);

        return "/adminController/displayProducts";
    }
    
    
    private String addProduct(HttpServletRequest request, HttpServletResponse response) {
    	
    	List<Category> categories = CategoryDB.selectCategories();
        List<Measure> measures = MeasureDB.selectMeasures();
        List<Packaging> packages = PackageDB.selectPackages();
        request.setAttribute("categories", categories);
        request.setAttribute("measures", measures);
        request.setAttribute("packages", packages);
    	
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
        
        String productPackagingIDString = request.getParameter("productPackagingID");
        int productPackagingID = 0;
        if (productPackagingIDString != null)
        	productPackagingID = Integer.parseInt(productPackagingIDString);
        
        Product product = new Product();
        
        product.setProductName(productName);
        product.setProductMeasureID(productMeasureID);
        product.setProductCategoryID(productCategoryID);
        product.setProductQuantity(productQuantity);
        product.setProductPrice(productPrice);
        product.setProductNote(productNote);
        product.setProductPackagingID(productPackagingID);

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
		
		/*System.out.println("--------After reading from database------------");
		for (int i=1; i <= clients.size()+1; i++) {
			for (int j=1; j <= clients.size()+1; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println("");
		}
		System.out.println("-----------------------------------------");*/
		
		request.setAttribute("matrix", matrix);
		request.setAttribute("clients", clients);
    	
    	return "/admin/plan.jsp";
    }
    
    private String calcPlan(HttpServletRequest request, HttpServletResponse response) {	
    	String date = request.getParameter("date");
    	String plainPlan = request.getParameter("plainPlan");

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
    	HttpSession s = request.getSession();
    	s.setAttribute("clientsInPlan", clients);
    	s.setAttribute("ordersInPlan", orders);	
    	s.setAttribute("ordersDate", date);
    	
    	return "/admin/resultPlan.jsp";
    }
    
    
    private String displayClients(HttpServletRequest request, HttpServletResponse response) {	

    	List<Client> clients = ClientDB.selectClients();
    	request.setAttribute("clients", clients);
    			
    	
    	return "/admin/clients.jsp";
    }
    
    
    private void printAkt(HttpServletRequest request, HttpServletResponse response) {
    	
    	String orderID = request.getParameter("orderID");
    	String path = request.getSession().getServletContext().getRealPath("/");
    	path += "WEB-INF\\classes\\templates\\AKT.docx";

    	XWPFDocument  doc = null;
    	OutputStream  out = null;
    	
    	Order order = OrderDB.selectOrder(Integer.parseInt(orderID));
    	Calendar cal = Calendar.getInstance();
    	
    	try {
			doc = new XWPFDocument (new FileInputStream(path));

			replaceText(doc, "number", String.valueOf(order.getOrderID()));
			replaceText(doc, "dd", String.format("%02d", cal.get(Calendar.DAY_OF_MONTH)));
			replaceText(doc, "mm", String.format("%02d", cal.get(Calendar.MONTH)));
			replaceText(doc, "yyyy", String.valueOf(cal.get(Calendar.YEAR)));
			System.out.println(order.getClient().getClientName());
			replaceText(doc, "client", order.getClient().getClientName());
			replaceText(doc, "expeditor", "Иванов Иван Иванович (поменять)");
			replaceText(doc, "money", order.num2str(false));

			XWPFTable tbl = doc.getTables().get(1);
			
			for(int i=0; i < order.getLineItems().size(); i++) {
				tbl.createRow();
				//tbl.addRow(row);
        		tbl.getRow(i+1).getCell(0).setText(String.valueOf(i+1));
        		tbl.getRow(i+1).getCell(1).setText(order.getLineItems().get(i).getProduct().getProductName());
        		Measure m = MeasureDB.selectMeasure(String.valueOf(order.getLineItems().get(i).getProduct().getProductMeasureID()));
        		tbl.getRow(i+1).getCell(2).setText(m.getMeasureName());
        		tbl.getRow(i+1).getCell(3).setText(String.valueOf(order.getLineItems().get(i).getQuantity()));
        		
        		BigDecimal price = order.getLineItems().get(i).getTotal().divide(new BigDecimal(order.getLineItems().get(i).getQuantity()));
        		NumberFormat currency = NumberFormat.getCurrencyInstance();
                if (currency instanceof DecimalFormat) {
                    DecimalFormat df = (DecimalFormat) currency;
                    DecimalFormatSymbols dfs = new DecimalFormat().getDecimalFormatSymbols();
                    dfs.setCurrencySymbol("тенге");
                    df.setDecimalFormatSymbols(dfs);
                }
                String priceStr = currency.format(price);
                priceStr = priceStr.split(" ")[0];
        		
        		tbl.getRow(i+1).getCell(4).setText(priceStr);
        		tbl.getRow(i+1).getCell(5).setText(order.getLineItems().get(i).getTotalCurrencyFormat().split(" ")[0]);
        		tbl.getRow(i+1).getCell(7).setText(order.getLineItems().get(i).getTotalCurrencyFormat().split(" ")[0]);
        	}
			
			XWPFTableRow row =tbl.createRow();
			row.getCell(1).setText("Итого:");
			row.getCell(7).setText(order.getOrderTotalCurrencyFormat().split(" ")[0]);
			
			
	        response.setContentType("application/msword");
	        response.addHeader("Content-Disposition", "attachment; filename=AKT.docx");
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
    
    
    private void printNakladnaia(HttpServletRequest request, HttpServletResponse response) {
    	
    	String orderID = request.getParameter("orderID");
    	String path = request.getSession().getServletContext().getRealPath("/");
    	path += "WEB-INF\\classes\\templates\\Nakladnaia.docx";

    	XWPFDocument  doc = null;
    	OutputStream  out = null;
    	
    	Order order = OrderDB.selectOrder(Integer.parseInt(orderID));
    	Calendar cal = Calendar.getInstance();
    	
    	try {
			doc = new XWPFDocument (new FileInputStream(path));

			replaceText(doc, "nomer", String.valueOf(order.getOrderID()));
			replaceText(doc, "day", String.format("%02d", cal.get(Calendar.DAY_OF_MONTH)));
			replaceText(doc, "month", String.format("%02d", cal.get(Calendar.MONTH)));
			replaceText(doc, "year", String.valueOf(cal.get(Calendar.YEAR)));
			System.out.println(order.getClient().getClientName());
			replaceText(doc, "client", order.getClient().getClientName());
			replaceText(doc, "expeditor", "Иванов Иван Иванович (поменять)");
			replaceText(doc, "summa", order.num2str(false));

			XWPFTable tbl = doc.getTables().get(1);
			
			for(int i=0; i < order.getLineItems().size(); i++) {
				tbl.createRow();
				//tbl.addRow(row);
        		tbl.getRow(i+1).getCell(0).setText(String.valueOf(i+1));
        		tbl.getRow(i+1).getCell(1).setText(order.getLineItems().get(i).getProduct().getProductName());
        		Measure m = MeasureDB.selectMeasure(String.valueOf(order.getLineItems().get(i).getProduct().getProductMeasureID()));
        		tbl.getRow(i+1).getCell(2).setText(m.getMeasureName());
        		tbl.getRow(i+1).getCell(3).setText(String.valueOf(order.getLineItems().get(i).getQuantity()));
        		
        		BigDecimal price = order.getLineItems().get(i).getTotal().divide(new BigDecimal(order.getLineItems().get(i).getQuantity()));
        		NumberFormat currency = NumberFormat.getCurrencyInstance();
                if (currency instanceof DecimalFormat) {
                    DecimalFormat df = (DecimalFormat) currency;
                    DecimalFormatSymbols dfs = new DecimalFormat().getDecimalFormatSymbols();
                    dfs.setCurrencySymbol("тенге");
                    df.setDecimalFormatSymbols(dfs);
                }
                String priceStr = currency.format(price);
                priceStr = priceStr.split(" ")[0];
        		
        		tbl.getRow(i+1).getCell(4).setText(priceStr);
        		tbl.getRow(i+1).getCell(5).setText(order.getLineItems().get(i).getTotalCurrencyFormat().split(" ")[0]);
        		tbl.getRow(i+1).getCell(7).setText(order.getLineItems().get(i).getTotalCurrencyFormat().split(" ")[0]);
        	}
			
			XWPFTableRow row =tbl.createRow();
			row.getCell(1).setText("Итого:");
			row.getCell(7).setText(order.getOrderTotalCurrencyFormat().split(" ")[0]);
			
			
	        response.setContentType("application/msword");
	        response.addHeader("Content-Disposition", "attachment; filename=Nakladnaia.docx");
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
    
    
    private void printPlan(HttpServletRequest request, HttpServletResponse response) {
    	
    	String path = request.getSession().getServletContext().getRealPath("/");
    	path += "WEB-INF\\classes\\templates\\Plan_postavok.docx";

    	XWPFDocument  doc = null;
    	OutputStream  out = null;
    	
    	List<Order> orders = (List<Order>)request.getSession().getAttribute("ordersInPlan");
    	List<Client> clients = (List<Client>)request.getSession().getAttribute("clientsInPlan");
    	
    	String ordersDate = (String)request.getSession().getAttribute("ordersDate");
    	SimpleDateFormat in = new SimpleDateFormat("yyyy-MM-dd");
    	java.util.Date date = new java.util.Date();
    	try {
			date = in.parse(ordersDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	Calendar ordersCal = Calendar.getInstance();
    	ordersCal.setTime(date);
    	Calendar curCal = Calendar.getInstance();
    	
    	try {
			doc = new XWPFDocument (new FileInputStream(path));

			replaceText(doc, "orderday", String.format("%02d", ordersCal.get(Calendar.DAY_OF_MONTH)));
			replaceText(doc, "ordermonth", String.format("%02d", ordersCal.get(Calendar.MONTH)));
			replaceText(doc, "orderyear", String.valueOf(ordersCal.get(Calendar.YEAR)));
			
			replaceText(doc, "planday", String.format("%02d", curCal.get(Calendar.DAY_OF_MONTH)));
			replaceText(doc, "planmonth", String.format("%02d", curCal.get(Calendar.MONTH)));
			replaceText(doc, "planyear", String.valueOf(curCal.get(Calendar.YEAR)));

			XWPFTable tbl = doc.getTables().get(0);
			
			for(int i=0; i < clients.size(); i++) {
				tbl.createRow();

        		tbl.getRow(i+1).getCell(0).setText(String.valueOf(i+1));
        		tbl.getRow(i+1).getCell(1).setText(clients.get(i).getClientName());
        		tbl.getRow(i+1).getCell(2).setText(clients.get(i).getClientAdress());
        		
        		String items = "";
        		for (int j=0; j < orders.size(); j++) {
        			if (orders.get(j).getClient().getClientId() == clients.get(i).getClientId()) {
        				for (LineItem item : orders.get(j).getLineItems()) {
        					items = item.getProduct().getProductName() +" - "
        							+ item.getQuantity() + " шт.";
        					XWPFParagraph p = tbl.getRow(i+1).getCell(3).addParagraph();
        					XWPFRun r = p.createRun();
        					r.setText(items);
        					r.addBreak();
        					tbl.getRow(i+1).getCell(3).addParagraph(p);
        					
        				}
        			}
        		}
        	}
			
	        response.setContentType("application/msword");
	        response.addHeader("Content-Disposition", "attachment; filename=Plan_postavok.docx");
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
    
    
    private void salesReport(HttpServletRequest request, HttpServletResponse response) {

    	String path = request.getSession().getServletContext().getRealPath("/");
    	path += "WEB-INF\\classes\\templates\\OtchetPoProdazham.docx";

    	XWPFDocument  doc = null;
    	OutputStream  out = null;
    	
    	String startDateStr = request.getParameter("startDate");
    	String endDateStr = request.getParameter("endDate");
    	
    	SimpleDateFormat in = new SimpleDateFormat("yyyy-MM-dd");
    	java.util.Date startDate = new java.util.Date();
    	java.util.Date endDate = new java.util.Date();
    	try {
    		startDate = in.parse(startDateStr);
    		endDate = in.parse(endDateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	
    	
    	List<Order> orders = OrderDB.selectOrdersBetween(in.format(startDate), in.format(endDate));
    	List<Category> categories = CategoryDB.selectCategories();
    	List<Product> products = ProductDB.selectProducts();
    	
    	try {
			doc = new XWPFDocument (new FileInputStream(path));
			
			replaceText(doc, "startDate", startDateStr);
			replaceText(doc, "endDate", endDateStr);
    	
	    	XWPFTable tbl = doc.getTables().get(0);
	    	
	    	NumberFormat currency = NumberFormat.getCurrencyInstance();
	    	if (currency instanceof DecimalFormat) {
                DecimalFormat df = (DecimalFormat) currency;
                DecimalFormatSymbols dfs = new DecimalFormat().getDecimalFormatSymbols();
                dfs.setCurrencySymbol("тенге");
                df.setDecimalFormatSymbols(dfs);
            }
	    	
	    	int index = 0;
	    	BigDecimal totalSum = new BigDecimal(0);
			int totalCount = 0;
			
	    	for (int i=0; i < categories.size(); i++) {
	    		Category c = categories.get(i);
	    		
	    		tbl.createRow();
	    		index++;
				tbl.getRow(index).getCell(0).setText(c.getCategoryName());
	    		
	    		BigDecimal categorySum = new BigDecimal(0);
				int categoryCount = 0;
				
	    		for (int j=0; j < products.size(); j++) {
	    			Product p = products.get(j);
	
	    			if (p.getProductCategoryID() == c.getCategoryID()) {
	    				tbl.createRow();
	    				index++;
	    				tbl.getRow(index).getCell(1).setText(p.getProductName());
	    				
	    				BigDecimal sum = new BigDecimal(0);
	    				int count = 0;
	    				for (Order order : orders) {
	    					for (LineItem item : order.getLineItems()) {
	    						if (item.getProduct().getProductID() == p.getProductID()) {
	    							count += item.getQuantity();
	    							sum = sum.add(item.getTotal());
	    						}
	    					}
	    				}
	    				
	    				tbl.getRow(index).getCell(3).setText(currency.format(sum));
	    				tbl.getRow(index).getCell(2).setText(String.valueOf(count));
	    				
	    				categoryCount += count;
	    				categorySum = categorySum.add(sum);
	    			}
	    		}
	    		
	    		tbl.createRow();
	    		index++;
	    		//spanCellsAcrossRow(tbl, index, 0, 2);
	    		tbl.getRow(index).getCell(1).setText("Всего по категории:");
	    		tbl.getRow(index).getCell(3).setText(currency.format(categorySum));
				tbl.getRow(index).getCell(2).setText(String.valueOf(categoryCount));
				tbl.createRow();
	    		index++;
				
				totalCount += categoryCount;
				totalSum = totalSum.add(categorySum);
	    	}
	    	
	    	tbl.createRow();
	    	index++;
	    	tbl.getRow(index).getCell(1).setText("Итого:");
			tbl.getRow(index).getCell(3).setText(currency.format(totalSum));
			tbl.getRow(index).getCell(2).setText(String.valueOf(totalCount));
	               
	        response.setContentType("application/msword");
	        response.addHeader("Content-Disposition", "attachment; filename=OtchetPoProdazham.docx");
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
    }
    
    private void spanCellsAcrossRow(XWPFTable table, int rowNum, int colNum, int span) {
        XWPFTableCell  cell = table.getRow(rowNum).getCell(colNum);
        cell.getCTTc().getTcPr().addNewGridSpan();
        cell.getCTTc().getTcPr().getGridSpan().setVal(BigInteger.valueOf((long)span));
    }

    
    private void monthReport(HttpServletRequest request, HttpServletResponse response) {
    	
    	String orderID = request.getParameter("orderID");
    	String path = request.getSession().getServletContext().getRealPath("/");
    	path += "WEB-INF\\classes\\templates\\Ezhemeciachnyi_otchet.docx";

    	XWPFDocument  doc = null;
    	OutputStream  out = null;
    	
    	//Order order = OrderDB.selectOrder(Integer.parseInt(orderID));
    	//Calendar cal = Calendar.getInstance();
    	//cal.setTime(order.getOrderDate());
    	
    	try {
			doc = new XWPFDocument (new FileInputStream(path));

			/*replaceText(doc, "nomer", String.valueOf(order.getOrderID()));
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
        	}*/
	               
	        response.setContentType("application/msword");
	        response.addHeader("Content-Disposition", "attachment; filename=Ezhemeciachnyi_otchet.docx");
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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
