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
    <%= IViewValues.HEAD_SCRIPT_BOOTSTRAP %>
</head>
<body>
	<jsp:useBean id="userinfo" class="il.ac.shenkar.javaee.model.User"></jsp:useBean> 
	<jsp:setProperty property="*" name="userinfo"/> 
	 
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
                        <a>
                            <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
                            Login 
                        </a>
                    </li>
                    
                    <li role="presentation" class="active">
                        <a>
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
                        <a href="about">
                            <span class="glyphicon glyphicon-thumbs-up" aria-hidden="true"></span>
                            About
                        </a>
                    </li>
                
                </ul>
            </nav>
            <div class="clear"></div>
        </header>
        <main id="tasks">
                <div class="panel panel-primary">
                <div class="panel-heading">
                    Tasks  // <%= "  "+Fname +" "+Lname %> // 
                    <% 
                    	String email = request.getParameter("email");
                    	if(email!=null){
                    		%><jsp:getProperty property="email" name="userinfo"/><%
                    	} 
                    %>
                </div>
                 
                 <div class="table-responsive">          
                   <table class="table table-hover">
                     <thead>
                       <tr>
                         <th>#</th>
                         <th>Title</th>
                         <th>Description</th>
<!--                     <th>Email</th>           -->
                          <th>Edit</th>
                         <th>Remove</th>
                       </tr>
                     </thead>
                     <tbody>
                     	<% 
                     		//response.setHeader("REFRESH", "0"); 
                     		String title=null, body=null, usere=null;
                     		int id = 0 ;
                     		if(currentUserTasks != null)
                     		{
                     			for(int i=0 ; i < currentUserTasks.size(); i++){
                     				id = currentUserTasks.get(i).getId();
                         			title = currentUserTasks.get(i).getTitle();
                         			body = currentUserTasks.get(i).getBody();
                         			usere = currentUserTasks.get(i).getUserEmail();
                         			%>
                         			<tr>
				                         <td><%= id %></td>
				                         <td><%= title %></td>
				                         <td><%= body %></td>
<!--				                     <td><%= usere %></td>        -->
				                         <td>  
				                            <a href="edit?to=exists&id=<%= id %>" type="button" class="btn btn-default">
				                                <span class="glyphicon glyphicon-edit" aria-hidden="true"></span> 
				                            </a>
				                        </td>
				                        <td>  
				                            <a href="tasks?&to=remove_exists&id=<%= id %>" type="button" class="btn btn-default">
				                                <span class="glyphicon glyphicon-remove" aria-hidden="true"></span> 
				                            </a>
				                        </td>
				                     </tr>	
                         			<%
                     			}
                     		}
                     	%>
                     </tbody>
                   </table>
                </div>
            </div>
                        
            <a href="edit?to=New" type="button" value="addNewTaskButtonValue" name="addNewTaskButtonName" class="btn btn-default">
                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>  Add Task   
            </a>
            
            <%
				String displayTasksAlert = (String)request.getAttribute("ALERT_TASKS_VISIBLE");
				String styleTasksAlert = null, reasonTasksInfo = null, reasonTasksDesAlert = null;
				if(displayTasksAlert!=null){
					styleTasksAlert = (String)request.getAttribute("ALERT_TASKS_STYLE");
					reasonTasksInfo = (String)request.getAttribute("ALERT_TASKS_INFO");
					//reasonDesAlert = (String)request.getAttribute("ALERT_LOGIN_REASON_DES");
				} else {
					displayTasksAlert = "none";
				}
			%>
            
            <div class="alert alert-<%= styleTasksAlert %> pad" style="display:<%= displayTasksAlert %>" role="alert">
                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <strong><%= reasonTasksInfo %></strong>
            </div> 
            
<!--             <div class="alert alert-danger pad" style="display:visible" role="alert">
                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <strong>Connection Problems</strong>
            </div> -->
<!--              </form>
 -->         </main>
    </div>
    <%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
/* response.setDateHeader ("Expires", 0);
 *///prevents caching at the proxy server
%>
</body>
</html>