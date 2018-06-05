package kz.foodmaster.filial.controller;


import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import kz.foodmaster.filial.business.Client;
import kz.foodmaster.filial.business.Message;
import kz.foodmaster.filial.business.Topic;
import kz.foodmaster.filial.business.User;
import kz.foodmaster.filial.data.MessageDB;
import kz.foodmaster.filial.data.TopicDB;
import kz.foodmaster.filial.data.UserDB;

public class UserController extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
    	
    	System.out.println("In doGet with request url = " + request.getRequestURL());
    	
        String requestURI = request.getRequestURI();
        String url = "";
        if (requestURI.endsWith("/forum")) {
            url = showForum(request, response);
        } else if (requestURI.endsWith("/enterTopic")) {
            url = showTopic(request, response);
        } else if (requestURI.endsWith("/addMessage")) {
            url = addMessage(request, response);
        } else if (requestURI.endsWith("/editMessage")) {
            url = editMessage(request, response);
        }
        
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

    	System.out.println("In doPost with request url = " + request.getRequestURL());
    	
        String requestURI = request.getRequestURI();
        String url = "";
        
        if (requestURI.endsWith("/insertMessage")) {
            url = insertMessage(request, response);
        } else if (requestURI.endsWith("/updateMessage")) {
            url = updateMessage(request, response);
        } else if (requestURI.endsWith("/enterTopic")) {
            url = showTopic(request, response);
        }
        
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }

    private String showForum(HttpServletRequest request,
            HttpServletResponse response) {

    	List<Topic> topics = TopicDB.selectTopics();
    	request.setAttribute("topics", topics);
    	
        return "/forum/forum.jsp";
    }
    
    private String showTopic(HttpServletRequest request,
            HttpServletResponse response) {

    	int topicID = Integer.parseInt(request.getParameter("topicID"));
    	
    	System.out.println("Topic ID = " + topicID);
    	
    	Topic topic = TopicDB.selectTopic(topicID);
    	request.setAttribute("topic", topic);
    	
        return "/forum/topic.jsp";
    }
    
    
    private String addMessage(HttpServletRequest request,
            HttpServletResponse response) {

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
    
    
    private String editMessage(HttpServletRequest request,
            HttpServletResponse response) {

    	int messageID = Integer.parseInt(request.getParameter("messageID"));
    	Message message = MessageDB.selectMessage(messageID);
    	request.setAttribute("topicID", message.getTopicID());
    	
    	HttpSession session = request.getSession();
    	Client client = (Client)session.getAttribute("client");
    	String info = "";
    	if (client == null) {
    		info = "Вы не авторизованы. Авторизуйтесь в системе, чтобы оставлять сообщения";
    		request.setAttribute("info", info);
    		return "/forumController/enterTopic";
    	}
    	
    	
    	
    	if (client.getClientLogin() != message.getUserLogin()) {
    		info = "Вы не можете редактировать чужие сообщения.";
    		request.setAttribute("info", info);
    		return "/forumController/enterTopic";
    	}
    	
    	request.setAttribute("message", message);
    	
        return "/forum/messageForm.jsp";
    }
    
    
    private String insertMessage(HttpServletRequest request,
            HttpServletResponse response) {
    	
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
    
    private String updateMessage(HttpServletRequest request,
            HttpServletResponse response) {

    	int messageID = Integer.parseInt(request.getParameter("messageID"));
    	int topicID = Integer.parseInt(request.getParameter("topicID"));
    	String text = request.getParameter("text");
    	
    	Message message = new Message();
    	message.setID(messageID);
    	message.setText(text);
    	
    	MessageDB.update(message);
    	
    	System.out.println("Update message with Topic ID = " + topicID);
    	
    	Topic topic = TopicDB.selectTopic(message.getTopicID());
    	
        return "/forumController/enterTopic";
    }

    private String subscribeToLogin(HttpServletRequest request,
            HttpServletResponse response) {

        String login = request.getParameter("login");
        String pass = request.getParameter("pass");
        String roleID = request.getParameter("roleID");

        User user = new User();
        user.setUserLogin(login);
        user.setUserPass(pass);
        user.setUserRoleId(Integer.parseInt(roleID));

        request.setAttribute("user", user);

        String url;
        String message;
        //check that email address doesn't already exist
        if (UserDB.loginExists(login)) {
            message = "This login already exists. <br>"
                    + "Please enter another login.";
            request.setAttribute("message", message);
            url = "/login/index.jsp";
        } else {
            UserDB.insert(user);
            message = "";
            request.setAttribute("message", message);
            url = "/login/thanks.jsp";
        }
        return url;
    }
}
