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
    <%= IViewValues.HEAD_SCRIPT_BOOTSTRAP %></head>
<body>
    <div id="container">
        <header>
            <div class="logo">
                <img src="/ToDo/images/icon.png" alt="logo" title="ToDo"/>
            </div>
            <nav>
                <ul class="nav nav-pills nav-justified">
                    
                    <li role="presentation">
                        <a href="login.jsp">
                            <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
                            Login 
                        </a>
                    </li>
                    
                    <li role="presentation" class="disabled">
                        <a href="tasks">
                            <span class="glyphicon glyphicon-list" aria-hidden="true"></span>
                            Tasks 
                            
                        </a>
                    </li>
                    
                    <li role="presentation" class="disabled">
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
        <main id="signup">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    Sign Up Form
                </div>
                <%
                	String fname = (String)request.getAttribute("fname") ,
                			lname = (String)request.getAttribute("lname") ,
                			email = (String)request.getAttribute("email"),
                			border_name = (String)request.getAttribute("ALERT_SIGNUP_SHORT_VALUE_BORDER"),
                			border_pass = (String)request.getAttribute("ALERT_SIGNUP_SHORT_VALUE_BORDER_PASSWORD"),
                			border_email = (String)request.getAttribute("ALERT_SIGNUP_INVALID_EMAIL_BORDER");
                	
                	fname = (fname!=null)?fname:"";
                	lname = (lname!=null)?lname:"";
                	email = (email!=null)?email:"";
                	
                	border_name = (border_name!=null)?border_name:"auto;";
                	border_pass = (border_pass!=null)?border_pass:"auto;";
                	border_email = (border_email!=null)?border_email:"auto;";
                %>
                <form action="tasks" method="post">
                  
                  <div class="form-group">
                    <label>First Name</label>
                    <input value="<%=fname%>" autocomplete autofocus style="border:<%=border_name%>" type="text" class="form-control" id="fname" name="fname">
                  </div>
                    
                  <div class="form-group">
                    <label>Last Name</label>
                    <input value="<%=lname%>" autocomplete style="border:<%=border_name%>" type="text" class="form-control" id="lname" name="lname">
                  </div>

                  <div class="form-group">
                    <label>Email Address</label>
                    <input value="<%=email%>" autocomplete style="border:<%=border_email%>" type="text" class="form-control" id="email" name="email">
                  </div>

                  <div class="form-group">
                    <label>Password</label>
                    <input style="border:<%=border_pass%>" type="password" class="form-control" id="password" name="password">
                  </div>
                    
                  <div class="form-group">
                    <label>Retype Password</label>
                    <input style="border:<%=border_pass%>" type="password" class="form-control" id="repassword" name="repassword">
                  </div>


                <button name="signUpButton" value="submitSignUpButton" type="submit" class="btn btn-primary">Sign Up</button>
                
                <a style="float:right;" href="login.jsp">
                    <button name="signUpButton" value="backFromSignUp" type="button" class="btn btn-default pad">
                       Back
                    </button>
                </a>
                
                <%
					String displayAlert = (String)request.getAttribute("ALERT_SIGNUP_VISIBLE");
					String styleAlert = null, reasonMainAlert = null;
					if(displayAlert!=null){
						styleAlert = (String)request.getAttribute("ALERT_SIGNUP_STYLE");
						reasonMainAlert = (String)request.getAttribute("ALERT_SIGNUP_INFO");
					} else {
						displayAlert = "none";
					}
				%> 
                    
                <div class="alert alert-warning alert-<%= styleAlert %> pad" style="display:<%= displayAlert %>;margin-top:1%;" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <%=reasonMainAlert %>
                </div>
                </form>
            </div>
         </main>
    </div>
</body>
</html>