<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="il.ac.shenkar.javaee.controller.* , il.ac.shenkar.javaee.view.*" 
    pageEncoding="UTF-8" %>
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
    <div id="container">
        <header>
            <div class="logo">
                <img src="<%=IViewValues.ICON_ABSOLUTE_PATH%>" alt="logo" title="ToDo"/>
            </div>
            <nav>
                <ul class="nav nav-pills nav-justified">
                    
                    <li role="presentation" class="active" >
                        <a>
                            <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
                            Login 
                        </a>
                    </li>
                    
                    <li role="presentation" class="disabled">
                        <a>
                            <span class="glyphicon glyphicon-list" aria-hidden="true"></span>
                            Tasks 
                            <span class="badge"></span>
                        </a>
                    </li>
                    
                    <li role="presentation" class="disabled">
                        <a >
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
        <main id="login">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    Please Login
                </div>

                <div class="panel-body">
   					<form action="tasks" method="post">
 
                        <div class="input-group pad">
                          <span class="input-group-addon" id="basic-addon1">@</span>
                          <input autofocus name="email" type="email" class="form-control" placeholder="Email" aria-describedby="basic-addon1">
                        </div>

                        <div class="input-group pad">
                          <span class="input-group-addon" id="basic-addon1">**</span>
                          <input name="pass" type="password" class="form-control" placeholder="Password" aria-describedby="basic-addon1">
                        </div>

                        <button name="loginButton" value="submitButton" type="submit" class="btn btn-default pad"/>Submit</button>
                        
                        
                        <a style="float:right;" href="signup.jsp" class="btn btn-default pad"/>
                          <span class="glyphicon glyphicon-star" aria-hidden="true"></span> Sign Up
                        </a>
                        
                    </form>
					<%
						String displayAlert = (String)request.getAttribute("ALERT_LOGIN_VISIBLE");
						String styleAlert = null, reasonMainAlert = null, reasonDesAlert = null;
						if(displayAlert!=null){
							styleAlert = (String)request.getAttribute("ALERT_LOGIN_STYLE");
							reasonMainAlert = (String)request.getAttribute("ALERT_LOGIN_REASON_MAIN");
							reasonDesAlert = (String)request.getAttribute("ALERT_LOGIN_REASON_DES");
						} else {
							displayAlert = "none";
						}
					%> 
                    <div class="alert alert-warning alert-<%= styleAlert %> pad" style="display:<%= displayAlert %>" role="alert">
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <strong><%=reasonMainAlert %></strong> <%= reasonDesAlert %>
                    </div>
                </div>
            </div>
         </main>
    </div>
</body>
</html>