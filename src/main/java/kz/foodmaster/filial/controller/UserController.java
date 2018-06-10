package kz.foodmaster.filial.controller;


import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.*;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Section;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableCell;
import org.apache.poi.hwpf.usermodel.TableRow;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;

import kz.foodmaster.filial.business.Client;
import kz.foodmaster.filial.business.Message;
import kz.foodmaster.filial.business.Order;
import kz.foodmaster.filial.business.Topic;
import kz.foodmaster.filial.business.User;
import kz.foodmaster.filial.data.ClientDB;
import kz.foodmaster.filial.data.MessageDB;
import kz.foodmaster.filial.data.OrderDB;
import kz.foodmaster.filial.data.RoleDB;
import kz.foodmaster.filial.data.TopicDB;
import kz.foodmaster.filial.data.UserDB;

public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	
        String requestURI = request.getRequestURI();
        System.out.println("In doGet with URI = " + requestURI);
        String url = "";
        if (requestURI.endsWith("/forum")) {
            url = showForum(request, response);
        } else if (requestURI.endsWith("/enterTopic")) {
            url = showTopic(request, response);
        } else if (requestURI.endsWith("/addMessage")) {
            url = addMessage(request, response);
        } else if (requestURI.endsWith("/editMessage")) {
            url = editMessage(request, response);
        }  else if (requestURI.endsWith("/register")) {
            url = "/login/user.jsp";
        }  else if (requestURI.endsWith("/orders")) {
            url = displayClientOrders(request, response);
        }  else if (requestURI.endsWith("/showCabinet")) {
            url = "/cabinet/index.jsp";
        }  else if (requestURI.endsWith("/displayClientOrder")) {
            url = displayClientOrder(request, response);
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
        
        if (requestURI.endsWith("/insertMessage")) {
            url = insertMessage(request, response);
        } else if (requestURI.endsWith("/updateMessage")) {
            url = updateMessage(request, response);
        } else if (requestURI.endsWith("/enterTopic")) {
            url = showTopic(request, response);
        }  else if (requestURI.endsWith("/addClient")) {
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
        }
        
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }

    
    private String showForum(HttpServletRequest request, HttpServletResponse response) {

    	List<Topic> topics = TopicDB.selectTopics();
    	request.setAttribute("topics", topics);
    	
        return "/forum/forum.jsp";
    }
    
    
    private String showTopic(HttpServletRequest request, HttpServletResponse response) {

    	int topicID = Integer.parseInt(request.getParameter("topicID"));

    	Topic topic = TopicDB.selectTopic(topicID);
    	request.setAttribute("topic", topic);
    	
        return "/forum/topic.jsp";
    }
    
    
    private String addMessage(HttpServletRequest request, HttpServletResponse response) {

    	HttpSession session = request.getSession();
    	Client client = (Client)session.getAttribute("client");
    	String info = "";
    	if (client == null) {
    		info = "Вы не авторизованы. Авторизуйтесь в системе, чтобы оставлять сообщения";
    		request.setAttribute("info", info);
    		return "/forumController/enterTopic";
    	}
    	
    	String topicID = request.getParameter("topicID");
    	request.setAttribute("topicID", topicID);
    	
        return "/forum/messageForm.jsp";
    }
    
    
    private String editMessage(HttpServletRequest request, HttpServletResponse response) {

    	int messageID = Integer.parseInt(request.getParameter("messageID"));
    	Message message = MessageDB.selectMessage(messageID);
    	request.setAttribute("topicID", message.getTopicID());
    	
    	HttpSession session = request.getSession();
    	Client client = (Client)session.getAttribute("client");
    	String info = "";
    	
    	if (client == null) {
    		info = "Вы не авторизованы. Авторизуйтесь в системе, чтобы оставлять сообщения";
    		request.setAttribute("info", info);
    		return "/forumController/enterTopic?topicID=" + message.getTopicID();
    	}
    	
    	if (!client.getClientLogin().equals(message.getUserLogin())) {
    		info = "Вы не можете редактировать чужие сообщения.";
    		request.setAttribute("info", info);
    		return "/forumController/enterTopic?topicID=" + message.getTopicID();
    	}
    	
    	request.setAttribute("message", message);
    	
        return "/forum/messageForm.jsp";
    }
    
    
    private String insertMessage(HttpServletRequest request, HttpServletResponse response) {
    	
    	int topicID = Integer.parseInt(request.getParameter("topicID"));
    	String text = request.getParameter("text");
    	
    	HttpSession session = request.getSession();
    	Client client = (Client)session.getAttribute("client");
    	
    	Message message = new Message();
    	message.setUserLogin(client.getClientLogin());
    	message.setTopicID(topicID);
    	message.setText(text);
    	
    	MessageDB.insert(message);
    	
        return "/forumController/enterTopic";
    }
    
    
    private String updateMessage(HttpServletRequest request, HttpServletResponse response) {

    	int messageID = Integer.parseInt(request.getParameter("messageID"));
    	String text = request.getParameter("text");
    	
    	Message message = new Message();
    	message.setID(messageID);
    	message.setText(text);
    	
    	MessageDB.update(message);
    	
        return "/forumController/enterTopic";
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
        
        return "/cabinet/clientOrder.jsp";
    }
    
    
    private String cancelOrder(HttpServletRequest request, HttpServletResponse response) {

    	String orderID = request.getParameter("orderID");
    		
    	OrderDB.cancelOrder(Integer.parseInt(orderID));
        
        return "/userController/orders";
    }
    
    
    private void printContract(HttpServletRequest request, HttpServletResponse response) {
    	
    	String orderID = request.getParameter("orderID");
    	ClassLoader classLoader = getClass().getClassLoader();
    	String path = classLoader.getResource("templates/Dogovor_na_postavku.dot").getFile();
    	String [] pathParts = path.split("/");
    	String fileName = pathParts[pathParts.length-1].substring(0, pathParts[pathParts.length-1].indexOf('.')) + ".doc";

    	HWPFDocument doc = null;
    	OutputStream  out = null;
    	
    	Order order = OrderDB.selectOrder(Integer.parseInt(orderID));
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(order.getOrderDate());
    	
    	try {
			doc = new HWPFDocument(new FileInputStream(path));
			//XWPFTable tbl = doc.getTableArray(1);
			replaceText(doc, "nomer", String.valueOf(order.getOrderID()));
			replaceText(doc, "dd", String.format("%02d", cal.get(Calendar.DAY_OF_MONTH)));
			replaceText(doc, "mm", String.format("%02d", cal.get(Calendar.MONTH)));
			replaceText(doc, "yyyy", String.valueOf(cal.get(Calendar.YEAR)));
			System.out.println(order.getClient().getClientName());
			replaceText(doc, "client", order.getClient().getClientName());
			replaceText(doc, "adress", order.getClient().getClientAdress());
			replaceText(doc, "contractsum", order.getOrderTotalCurrencyFormat());

	        int numParas = 0;
	        ArrayList tables = null; 
	        Paragraph para = null;
	        boolean inTable = false;
	        Range range = null; 
	        int numRowsInTable = 0; 
	        int numCellsInRow = 0; 
	        
	        range = doc.getRange(); 
	        numParas = range.numParagraphs();
	        tables = new ArrayList();
	        
	        for(int i = 0; i < numParas; i++) { 
	        	para = range.getParagraph(i); 
	        	if(para.isInTable()) { 
	        		if(!inTable) { 
	        			tables.add(range.getTable(para)); 
	        		    inTable = true; 
	        		} 
	        	} 
	        	else { 
	        		inTable = false; 
	        	} 
	        } 
	        Table table;
	        TableRow row = null; 
	        TableCell cell = null; 

        	table = (Table)tables.get(1);
        	//row = new TableRow();
        	int curRow =1;
        	for(int i=0; i < order.getLineItems().size(); i++) {
        		row = table.getRow(curRow);
        		cell = row.getCell(0);
        		cell.getParagraph(0).insertBefore(String.valueOf(i+1));
        		cell = row.getCell(1);
        		cell.getParagraph(0).insertBefore(order.getLineItems().get(i).getProduct().getProductName());
        		cell = row.getCell(2);
        		cell.getParagraph(0).insertBefore(String.valueOf(order.getLineItems().get(i).getQuantity()));
        		cell = row.getCell(3);
        		cell.getParagraph(0).insertBefore(String.valueOf(order.getLineItems().get(i).getTotalCurrencyFormat()));
        		cell = row.getCell(4);
        		cell.getParagraph(0).insertBefore(String.valueOf(order.getLineItems().get(i).getTotalCurrencyFormat()));
        		curRow++;
        	}
        	
            numRowsInTable = table.numRows();
            for(int i=numRowsInTable-1; i >= numRowsInTable; i--) {
            	table.getRow(i).delete();
            }

	               
	        response.setContentType("application/msword");
	        response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
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
				//out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    	
        //return "/userController/displayClientOrder";
    }
    
    
    private HWPFDocument replaceText(HWPFDocument doc, String findText, String replaceText) {
        Range r = doc.getRange();
        for (int i = 0; i < r.numSections(); ++i) {
            Section s = r.getSection(i);
            for (int j = 0; j < s.numParagraphs(); j++) {
                Paragraph p = s.getParagraph(j);
                for (int k = 0; k < p.numCharacterRuns(); k++) {
                    CharacterRun run = p.getCharacterRun(k);
                    String text = run.text();
                    if (text.contains(findText)) {
                        run.replaceText(findText, replaceText);
                    }
                }
            }
        }
        return doc;
    }
}
