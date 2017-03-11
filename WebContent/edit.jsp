<%@ page language="java" contentType="text/html; charset=UTF-8"
    import="il.ac.shenkar.javaee.model.*, java.util.* , il.ac.shenkar.javaee.controller.*, il.ac.shenkar.javaee.view.*"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>ToDo</title>
    <%= IViewValues.HEAD_ICON %>
    <%= IViewValues.HEAD_META_CONTENT_TYPE %>
	<%= IViewValues.HEAD_STYLE %>
	<%= IViewValues.HEAD_META_VIEWPORT %>
	<%= IViewValues.HEAD_SCRIPT_JQUERY_AJAX %>
    <%= IViewValues.HEAD_SCRIPT_JQUERY %>
    <%= IViewValues.HEAD_STYLE_BOOTSTRAP_MIN %>
    <%= IViewValues.HEAD_STYLE_BOOTSTRAP %>
    <%= IViewValues.HEAD_SCRIPT_BOOTSTRAP %></head></head>
<body>
    <div id="container">
    <%
		User user = (User)session.getAttribute("sessionCurrentUser");
    	List<Task> currentUserTasks=null;

		String Fname = null, Lname = null;
        if(user!=null){
        	Fname = user.getFname();
        	Lname = user.getLname();
        	currentUserTasks =  (List<Task>)session.getAttribute("currentUserTasks");
        }
						
	%>
        <header>
            <div class="logo">
                <img src="/ToDo/images/icon.png" alt="logo" title="ToDo"/>
            </div>
            <nav>
                <ul class="nav nav-pills nav-justified">
                    
                    <li role="presentation"  class="disabled">
                        <a href="login">
                            <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
                            Login 
                        </a>
                    </li>
                    
                    <li role="presentation" class="active">
                        <a href="tasks.jsp">
                            <span class="glyphicon glyphicon-list" aria-hidden="true"></span>
                            Tasks 
                            <% 
                            int numOfTasks = 0;
                            if(currentUserTasks.size()>0){
                            	numOfTasks = currentUserTasks.size();
                            	out.println("<span class='badge'>"+numOfTasks+"</span>");
                            } %>
                        </a>
                    </li>
                    
                    <li role="presentation" >
                        <a href="logout">
                            <span class="glyphicon glyphicon-log-out" aria-hidden="true"></span>
                            Log Out
                        </a>
                    </li>
                    
                    <li role="presentation">
                        <a href="about.jsp">
                            <span class="glyphicon glyphicon-thumbs-up" aria-hidden="true"></span>
                            About
                        </a>
                    </li>
                
                </ul>
            </nav>
            <div class="clear"></div>
        </header>
        <main id="edit">
            <div class="panel panel-primary">
                <div class="panel-heading">
                <%
	                String editStatus = null;
	                editStatus = (String)request.getAttribute("EDIT_STATUS_TASK");
	                
                %>
                
                <%= editStatus %> Task // <%= "  "+Fname +" "+Lname %>
                </div>
                <%
                	Task task = null;
                	int taskID;
                	String thisTask = null, taskT = "" , taskD = "";
                	thisTask = (String)request.getAttribute("EDIT_NEW_FIELDS");
                	if(thisTask!=null){
                		switch (thisTask){
                		case "New":
                			taskT="Shopping...";
                			taskD="Milk and Cheese";
                			break;
                		case "Edit":
                			task = (Task)request.getAttribute("CURRENT_TASK");
                			taskID = task.getId();
                			taskT = task.getTitle();
                			taskD = task.getBody();
                			break;
                		}
                	}
                	String borderStyle = null;
                	borderStyle = (String)request.getAttribute("INPUT_TITLE_BORDER_STYLE");
                	if(borderStyle==null) borderStyle = "auto";
                %>
                <form action="tasks" method="post">
                  <div class="form-group">
                    <label for="exampleInputEmail1">Task Title</label>
                    <input autofocus value="<%= taskT %>" style="border:<%= borderStyle %>;" type="text" class="form-control" id="taskTitle" name="taskTitle">
                  </div>
                  <div class="form-group">
                    <label for="exampleTextarea">Task Description</label>
                    <textarea class="form-control" id="taskDescription" name="taskDescription" rows="3"><%= taskD %></textarea>
                  </div>
                  
                <button type="submit" name="submitTaskButton" value="<%= (thisTask=="New")?"Submit":"Update"%>" class="btn btn-primary">
                	<%= (thisTask=="New")?"Submit":"Update" %>
                </button>
                
                <a href="tasks.jsp" style="float:right;">
                    <button type="button" class="btn btn-default pad">
                       Back
                    </button>
                </a>
                <%
					String displayAlert = (String)request.getAttribute("ALERT_TITLE_VISIBLE");
					String styleAlert = null, reasonDesAlert = null;
					if(displayAlert!=null){
						styleAlert = (String)request.getAttribute("ALERT_TITLE_STYLE");
						reasonDesAlert = (String)request.getAttribute("ALERT_TITLE_REASON_MAIN");
					} else {
						displayAlert = "none";
					}
				%>
                <div class="alert alert-<%= styleAlert %> pad" style="display:<%=displayAlert%>;margin-top:1%;" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <strong><%= reasonDesAlert %>!</strong>
                </div>
                </form>
            </div>
         </main>
    </div>
</body>
</html>