<%@ page language="java" contentType="text/html; charset=UTF-8"
    import="il.ac.shenkar.javaee.model.*, java.util.* , il.ac.shenkar.javaee.controller.*, il.ac.shenkar.javaee.view.*"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>ToDo</title>
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
			User user = null;
    		user = (User)session.getAttribute("sessionCurrentUser");
    		List<Task> currentUserTasks = null;
    		boolean userIsConnected = false;
       		if(user!=null){
       			userIsConnected = true;
      			currentUserTasks =  (List<Task>)session.getAttribute("currentUserTasks");
       		}
		%>
        <header>
            <div class="logo">
                <img src="<%=IViewValues.ICON_ABSOLUTE_PATH%>" alt="logo" title="ToDo"/>
            </div>
            <nav>
                <ul class="nav nav-pills nav-justified">
                    
                    <li role="presentation" class="<%= (userIsConnected)?"disabled":"" %>">
                        <a <%= (!userIsConnected)?"href='login.jsp'":"" %>>
                            <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
                            Login 
                        </a>
                    </li>
                    
                    <li role="presentation" class="<%= (!userIsConnected)?"disabled":"" %>">
                        <a <%= (!userIsConnected)?"":"href='tasks'" %>>
                            <span class="glyphicon glyphicon-list" aria-hidden="true"></span>
                            Tasks 
                            <% 
                            int numOfTasks = 0;
                            if(currentUserTasks!=null&&currentUserTasks.size()>0){
                            	numOfTasks = currentUserTasks.size();
                            	out.println("<span class='badge'>"+numOfTasks+"</span>");
                            } 
                            %>
                        </a>
                    </li>
                    
                    <li role="presentation" class="<%= (!userIsConnected)?"disabled":"" %>">
                        <a <%= (!userIsConnected)?"":"href='logout'" %>>
                            <span class="glyphicon glyphicon-log-out" aria-hidden="true"></span>
                            Log Out
                        </a>
                    </li>
                    
                    <li role="presentation" class="active">
                        <a>
                            <span class="glyphicon glyphicon-thumbs-up" aria-hidden="true"></span>
                            About
                        </a>
                    </li>
                
                </ul>
            </nav>
            <div class="clear"></div>
        </header>
        <main id="about">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    Authors
                </div>
                <div class="studContainer">
                    <div class="stud">
                        Itay Noama
                        <hr>
                        Software Engineer
                    </div>
                    <div class="stud">
                        Or Ben David
                        <hr>
                        Software Engineer
                    </div>
                    <div class="stud">
                        Tom Goldberg
                        <hr>
                        Software Engineer
                    </div>
                    <div class="clear"></div>
                </div>
                
            </div>
         </main>
    </div>
</body>
</html>