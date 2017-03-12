package il.ac.shenkar.javaee.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import il.ac.shenkar.javaee.model.Task;
import il.ac.shenkar.javaee.model.TaskPlatformException;
import il.ac.shenkar.javaee.model.ToDoListDao;
import il.ac.shenkar.javaee.model.User;
import il.ac.shenkar.javaee.model.UserPlatformException;
import il.ac.shenkar.javaee.view.IViewValues;

/**
 * Servlet implementation class DaoController
 * @author TomGoldberg, ItayNoama, OrBenDavid
 */
@WebServlet("/daocontroller/*")
public class DaoController extends HttpServlet implements IControllerValues, IViewValues{
	private static final long serialVersionUID = 1L;

	/**
	 * This is the central class in the log4j package. Most logging operations, except configuration, are done through this class.
	 */
	private static Logger DaoControllerLog = Logger.getLogger(DaoController.class);
     
	/**
	 * Provides a way to identify a user across more than one page request or visit to a Web site and to store information about that user.
	 */
    private HttpSession session; 
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DaoController() {
        super();
    }


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession(true);
		redirect(request,response);
		return;
	}
	
	/**
	 * Responsible to redirect doGet requests from client side
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void redirect(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fromUrl = request.getHeader("Referer"); 
		String fromUrlEndsWith = fromUrl.substring(fromUrl.lastIndexOf("/") + 1);
		
		String toUrl = request.getRequestURL().toString();
		String toUrlEndsWith = toUrl.substring(toUrl.lastIndexOf("/") + 1);

		String requestedUrl = null;
		
		if(	fromUrlEndsWith.contains(LOGIN) || fromUrlEndsWith.contains(DEFAULT_PATH) ){
			if(toUrlEndsWith.equals(ABOUT)){
				DaoControllerLog.warn(ENTERING_ABOUT_PAGE);
				requestedUrl = ABOUT;
			} else {
				DaoControllerLog.warn(ENTERING_LOGIN_PAGE);
				requestedUrl = LOGIN;
			}
		} else if(fromUrlEndsWith.contains(TASKS)){
			if(toUrlEndsWith.equals(ABOUT)) {
				DaoControllerLog.warn(ENTERING_LOGIN_PAGE);
				requestedUrl=ABOUT;
			}
			else if(toUrlEndsWith.equals(LOGOUT)){
				DaoControllerLog.warn(WARN_LOGGING_OUT);
				session.setAttribute("sessionCurrentUser", null);
				session.invalidate();
				requestedUrl=LOGIN;
			} else {
				requestedUrl = tasksPageHandler(request,response);
				if(requestedUrl.equals(TASKS)) toTasksPageWithDoGet();
			}
		} else if(fromUrlEndsWith.contains(EDIT)){
			DaoControllerLog.warn(ENTERING_EDIT_PAGE);
			requestedUrl = editPageHandler(request, response);
		} else if(fromUrlEndsWith.contains(ABOUT)||fromUrlEndsWith.contains(LOGOUT)){
			if(toUrlEndsWith.contains(TASKS)){
				DaoControllerLog.warn(ENTERING_TASKS_PAGE);
				requestedUrl=TASKS;
			} else if(toUrlEndsWith.equals(LOGOUT) || toUrlEndsWith.equals(ABOUT)){
				DaoControllerLog.warn(ENTERING_LOGIN_PAGE);
				session.invalidate();
				requestedUrl=LOGIN;
			} else {
				DaoControllerLog.warn(ENTERING_LOGIN_PAGE);
				requestedUrl=LOGIN;
			}
		} else if(fromUrlEndsWith.contains(SIGNUP)){
			if(toUrlEndsWith.equals(ABOUT)){
				DaoControllerLog.warn(ENTERING_ABOUT_PAGE);
				session.setAttribute("sessionCurrentUser", null);
				session.invalidate();
				requestedUrl=ABOUT;
			} else if(toUrlEndsWith.equals(LOGIN_PATH)){
				DaoControllerLog.warn(ENTERING_LOGIN_PAGE);
				session.setAttribute("sessionCurrentUser", null);
				session.invalidate();
				requestedUrl=LOGIN;
			}
		}
		
		if(toUrlEndsWith.equals(LOGOUT)){
			if(request.getSession(false)!=null) session.invalidate();
			DaoControllerLog.warn(ENTERING_LOGIN_PAGE);
			requestedUrl=LOGIN;
		}
		
		toNextJsp(request, response ,"/"+requestedUrl+".jsp");
	}
	
	/**
	 * Responsible to refresh the user tasks list
	 * @return List<Task>
	 */
	private List<Task> toTasksPageWithDoGet(){
		List<Task> tasks = null;
		User user = (User)session.getAttribute("sessionCurrentUser");
		try {
			tasks = ToDoListDao.getInstance().getTasks(user);
			session.setAttribute("currentUserTasks", tasks);
		} catch (TaskPlatformException e) {
			DaoControllerLog.error(GET_TASK_LIST_ERROR_MESSAGE + e.getMessage());
			e.printStackTrace();
		}
		if(tasks!=null)	DaoControllerLog.warn(USER_TASKS_LIST_REFRESHED);
		return tasks;
	}
	
	/**
	 * Responsible to handle tasks page which represent the current user tasks list
	 * @param request
	 * @param response
	 * @return the next url
	 * @throws ServletException
	 * @throws IOException
	 */
	private String tasksPageHandler(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String nextUrl = null, to = null;
		User user = null;
		int taskID;
		to = request.getParameter("to");
		if(to!=null){
			switch(to){
			case NEW:
				DaoControllerLog.warn(NEW_TASK);
				request.setAttribute(EDIT_STATUS_TASK, NEW);
				request.setAttribute(EDIT_NEW_FIELDS, NEW);
				nextUrl = EDIT;
				break;
			case EXISTS:
				try{
					DaoControllerLog.warn(EDIT_TASK);
					taskID = Integer.parseInt((String)request.getParameter("id"));
					user = (User)session.getAttribute("sessionCurrentUser");
					List<Task> tasks = ToDoListDao.getInstance().getTasks(user);
					for(Task t : tasks){
						if(t.getId()==taskID){
							System.out.println(t);
							request.setAttribute(EDIT_STATUS_TASK, "Edit");
							request.setAttribute(EDIT_NEW_FIELDS, "Edit");
							request.setAttribute(CURRENT_TASK, t);
							session.setAttribute(CURRENT_TASK, t);
							break;
						}
					}
				} catch(TaskPlatformException e){
					DaoControllerLog.error(GET_TASK_LIST_ERROR_MESSAGE + e.getMessage());
					e.printStackTrace();
				}
				nextUrl = EDIT;
				break;	
			case REMOVE:
				try{
					DaoControllerLog.warn(REMOVE_TASK);
					taskID = Integer.parseInt((String)request.getParameter("id"));
					user = (User)session.getAttribute("sessionCurrentUser");
					ToDoListDao.getInstance().deleteTask(user.getEmail(), taskID);
					request.setAttribute("ALERT_TASKS_VISIBLE", ALERT_VISIBLE);
					request.setAttribute("ALERT_TASKS_STYLE", ALERT_STYLE_INFO);
					request.setAttribute("ALERT_TASKS_INFO", ALERT_TASKS_INFO_DELETED_SUCCESSFULLY+" no."+taskID);	
					nextUrl = TASKS;
					break;
				} catch(TaskPlatformException e){
					DaoControllerLog.error(DELETE_TASK_ERROR_MESSAGE + e.getMessage());
					e.printStackTrace();
				}
			}
		} else nextUrl = LOGIN;
		return nextUrl;
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession(true);
		dispatcher(request,response);
		return;
	}
	
	/**
	 * Responsible for handling doPost requests
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void dispatcher(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fromUrl = request.getHeader("Referer"); 
		String fromUrlEndsWith = fromUrl.substring(fromUrl.lastIndexOf("/") + 1);
		
		String toUrl = request.getRequestURL().toString();
		String toUrlEndsWith = toUrl.substring(toUrl.lastIndexOf("/") + 1);
		
		String nextUrl = null;
		
		String s = (String)request.getParameter("signUpButton");
		
		if(	fromUrlEndsWith.contains(LOGIN) ||
				fromUrlEndsWith.contains(LOGOUT) ||
				(  fromUrlEndsWith.contains( TASKS ) && s.equals("submitSignUpButton") ) ){
			nextUrl = loginPageHandler(request, response);
		} else if( fromUrlEndsWith.contains( SIGNUP ) || (fromUrlEndsWith.contains( TASKS )&& request.getParameter("signUpButton").equals("submitSignUpButton")) ){
			nextUrl = signUpPageHandler(request, response);
		} else if(fromUrlEndsWith.contains(EDIT)){
			String button = request.getParameter("submitTaskButton");
			if ("Submit".equals(button)) {
				request.setAttribute("ALERT_TASKS_VISIBLE", ALERT_VISIBLE);
				if(addTaskButton(request, response)){
					request.setAttribute("ALERT_TASKS_STYLE", ALERT_STYLE_INFO);
					request.setAttribute("ALERT_TASKS_INFO", ALERT_TASKS_INFO);	
					toUrlEndsWith = "/"+TASKS_PATH;
					nextUrl = toUrlEndsWith;
				} else{
					request.setAttribute("ALERT_TASKS_STYLE", ALERT_STYLE_DANGER);
					request.setAttribute("ALERT_TASKS_INFO", ALERT_EDIT_NEW_TASK);	
					nextUrl = EDIT+"?to=edit_new";
				}
				toTasksPageWithDoGet();
			} else if("Update".equals(button)){
				editPageHandler(request, response);
				toUrlEndsWith = "/"+TASKS_PATH;
				nextUrl = toUrlEndsWith;
			}	
		}

		toNextJsp(request, response ,"/"+nextUrl);
	}
	
	/**
	 * Adds a new task to the current user
	 * @param request
	 * @param response
	 * @return if successful then returns true, otherwise returns false
	 * @throws ServletException
	 * @throws IOException
	 */
	private boolean addTaskButton(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String title = null, description = null;
		User user = null;
		boolean success = false;
		title = request.getParameter("taskTitle");
		description = request.getParameter("taskDescription");
		user = (User)session.getAttribute("sessionCurrentUser");
		if( user == null ){
			request.setAttribute("ALERT_TITLE_REASON_MAIN","Unable to fetch current user");
			request.setAttribute("ALERT_TITLE_STYLE",ALERT_STYLE_DANGER);
			request.setAttribute("ALERT_TITLE_VISIBLE", ALERT_VISIBLE);
		} else if ( title == null || title.length() == 0 ){
			request.setAttribute("ALERT_TITLE_REASON_MAIN","You need to provide title...");
			request.setAttribute("ALERT_TITLE_STYLE",ALERT_STYLE_DANGER);
			request.setAttribute("ALERT_TITLE_VISIBLE", ALERT_VISIBLE);
			request.setAttribute("INPUT_TITLE_BORDER_STYLE","1px solid red");
		}
		try {			
			success = ToDoListDao.getInstance().addTask(title,description,user.getEmail());
		} catch (TaskPlatformException e) {
			DaoControllerLog.error(ADD_TASK_ERROR_MESSAGE + e.getMessage());
			e.printStackTrace();
		} 
		
		return success;
	}
	
	/**
	 * Handles signup.jsp page which responsible for signs up a new user to the system
	 * @param request
	 * @param response
	 * @return the next page url
	 * @throws ServletException
	 * @throws IOException
	 */
	private String signUpPageHandler(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String button = request.getParameter("signUpButton");
		String fname = null , lname = null , email = null , password = null , repassword = null , nextUrl = null;
		if ("submitSignUpButton".equals(button)) {
			fname = request.getParameter("fname");
			request.setAttribute("fname", fname);
			lname = request.getParameter("lname");
			request.setAttribute("lname", lname);
			email = request.getParameter("email");
			request.setAttribute("email", email);
			password = request.getParameter("password");
			repassword = request.getParameter("repassword");
			if( isValidName(fname,request) && 
					isValidName(lname,request) && 
					isValidEmailAddress(email,request) && 
					isPasswordValid(password,request) && 
					isPasswordsEqual(password,repassword,request)){ 
				User user = new User(fname,lname,email,password);
				try {
					ToDoListDao.getInstance().signUp(user);
					user = ToDoListDao.getInstance().getUser(email, password);
					session.setAttribute("sessionCurrentUser", user);
					session.setAttribute("currentUserTasks", ToDoListDao.getInstance().getTasks(user));
					nextUrl = TASKS_PATH;
				} catch (UserPlatformException e) {
					DaoControllerLog.error(GET_USER_ERROR_MESSAGE + e.getMessage());
					nextUrl = SIGNUP_PATH;
					e.printStackTrace();
				} catch(TaskPlatformException e){
					DaoControllerLog.error(GET_TASK_LIST_ERROR_MESSAGE + e.getMessage());
					nextUrl = SIGNUP_PATH;
					e.printStackTrace();
				}
			} else {
				request.setAttribute("ALERT_SIGNUP_VISIBLE",ALERT_VISIBLE);
				nextUrl = SIGNUP_PATH;
			}
        } else if ("backFromSignUp".equals(button)) {
        	nextUrl = LOGIN_PATH;
        }		
		return nextUrl;
	}

	/**
	 * Handles login.jsp page which responsible for logging in an existing user or signing up new user
	 * @param request
	 * @param response
	 * @return the next page url
	 * @throws ServletException
	 * @throws IOException
	 */
	private String loginPageHandler(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String button = request.getParameter("loginButton");
		String button2 = request.getParameter("signUpButton");
		if(button != null&&button.equals("submitButton")){
			return loginProcess( request, response );
		}
		if(button2 != null&&button2.equals("submitSignUpButton")){
			return signUpPageHandler( request, response );
		}
		return LOGIN_PATH;
	}
	
	/**
	 * Handles edit.jsp page which responsible for editing an exists task or add a new task
	 * @param request
	 * @param response
	 * @return the next page url
	 * @throws ServletException
	 * @throws IOException
	 */
	private String editPageHandler(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String editTaskTitle = null , editTaskDes = null, reqUrl = null;
		User user = null;
		boolean success = true;
		user = (User)session.getAttribute("sessionCurrentUser");
		editTaskTitle = request.getParameter("taskTitle");
		editTaskDes = request.getParameter("taskDescription");
		Task newTask = new Task(editTaskTitle, editTaskDes, user.getEmail());
		Task task = (Task)session.getAttribute(CURRENT_TASK);
		try {
			success = ToDoListDao.getInstance().updateTask(task.getId(), newTask , user);
		} catch (TaskPlatformException e) {
			DaoControllerLog.error(UPDATE_TASK_ERROR_MESSAGE + e.getMessage());
			e.printStackTrace();
		}
		if(success){
			toTasksPageWithDoGet();
			request.setAttribute("ALERT_TASKS_VISIBLE", ALERT_VISIBLE);
			request.setAttribute("ALERT_TASKS_STYLE", ALERT_STYLE_INFO);
			request.setAttribute("ALERT_TASKS_INFO", ALERT_TASKS_INFO);	
			reqUrl = TASKS_PATH;
		} else {
			request.setAttribute("ALERT_TITLE_REASON_MAIN", ALERT_EDIT_TASK_GENERAL );
			request.setAttribute("ALERT_TITLE_STYLE",ALERT_STYLE_WARNING);
			request.setAttribute("ALERT_TITLE_VISIBLE", ALERT_VISIBLE);
			reqUrl = EDIT_PATH;
		}
		return reqUrl;
	}
	
	/**
	 * Responsible to process the login of an existing user
	 * @param request
	 * @param response
	 * @return the next page url
	 * @throws ServletException
	 * @throws IOException
	 */
	private String loginProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String URL = "/"+LOGIN_PATH;
		String email = request.getParameter("email");
		String password = request.getParameter("pass");
		DaoControllerLog.warn(LOGIN_PROCESS_INIT);
		if(!isValidEmailAddress(email,request)){
			request.setAttribute("ALERT_LOGIN_VISIBLE", ALERT_VISIBLE);
			request.setAttribute("ALERT_LOGIN_STYLE",ALERT_STYLE_WARNING);
			request.setAttribute("ALERT_LOGIN_REASON_MAIN",ALERT_LOGIN_REASON_MAIN_EMAIL);
			request.setAttribute("ALERT_LOGIN_REASON_DES",ALERT_LOGIN_BAD_EMAIL_ADRRESS);
		} else if (!isPasswordValid(password,request)){
			request.setAttribute("ALERT_LOGIN_VISIBLE", ALERT_VISIBLE);
			request.setAttribute("ALERT_LOGIN_STYLE",ALERT_STYLE_WARNING);
			request.setAttribute("ALERT_LOGIN_REASON_MAIN",ALERT_LOGIN_REASON_MAIN_EMAIL);
			request.setAttribute("ALERT_LOGIN_REASON_DES", ALERT_LOGIN_REASON_DES_PASS );
		} else {
			URL = isAuthenticated(email,password,request,response);
			if (URL==null) URL = "/"+LOGIN_PATH;
		}
		return URL;
	}
	
	/**
	 * Responsible to forward from one jsp to another
	 * @param request
	 * @param response
	 * @param url
	 * @throws ServletException
	 * @throws IOException
	 */
	private void toNextJsp(HttpServletRequest request, HttpServletResponse response, final String url) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher(url);
		rd.forward(request, response);
	}
	
	/**
	 * Will check if the email is valid
	 * @param email
	 * @param request
	 * @return if successful then returns true, otherwise returns false
	 */
    private boolean isValidEmailAddress(final String email,HttpServletRequest request) {
    	final String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        if(!email.matches(EMAIL_REGEX)){
        	request.setAttribute("ALERT_SIGNUP_VISIBLE", ALERT_VISIBLE);
			request.setAttribute("ALERT_SIGNUP_STYLE", ALERT_STYLE_WARNING);
			request.setAttribute("ALERT_SIGNUP_INFO", ALERT_SIGNUP_INVALID_EMAIL_ADDRESS);
			request.setAttribute("ALERT_SIGNUP_INVALID_EMAIL_BORDER",ALERT_SIGNUP_SHORT_VALUE_BORDER);
        	return false;
        }
       	return true;
    }
    
    /**
     * Will check if the password is valid
     * @param password
     * @param request
     * @return if successful then returns true, otherwise returns false
     */
	private boolean isPasswordValid(final String password,HttpServletRequest request){
		if(password.length()<2){
			request.setAttribute("ALERT_SIGNUP_VISIBLE", ALERT_VISIBLE);
			request.setAttribute("ALERT_SIGNUP_STYLE", ALERT_STYLE_WARNING);
			request.setAttribute("ALERT_SIGNUP_INFO", ALERT_SIGNUP_PASSWORD_TOO_SHORT);
			request.setAttribute("ALERT_SIGNUP_SHORT_VALUE_BORDER_PASSWORD",ALERT_SIGNUP_SHORT_VALUE_BORDER);
        	return false;
		}
		return true;
	}
	
	/**
	 * Will check if the name is valid
	 * @param name
	 * @param request
	 * @return if successful then returns true, otherwise returns false
	 */
	private boolean isValidName(final String name,HttpServletRequest request){
		if (name.length()<3){
			request.setAttribute("ALERT_SIGNUP_VISIBLE", ALERT_VISIBLE);
			request.setAttribute("ALERT_SIGNUP_STYLE", ALERT_STYLE_WARNING);
			request.setAttribute("ALERT_SIGNUP_INFO", ALERT_SIGNUP_SHORT_VALUE);
			request.setAttribute("ALERT_SIGNUP_SHORT_VALUE_BORDER", ALERT_SIGNUP_SHORT_VALUE_BORDER);
			return false;
		}
		return true;
	}
	
	/**
	 * Will check if the password was correctly retyped
	 * @param password
	 * @param repassword
	 * @param request
	 * @return if true then returns true, otherwise returns false
	 */
	private boolean isPasswordsEqual(String password, String repassword, HttpServletRequest request) {
		if(!password.equals(repassword)){
			request.setAttribute("ALERT_SIGNUP_VISIBLE", ALERT_VISIBLE);
			request.setAttribute("ALERT_SIGNUP_STYLE", ALERT_STYLE_WARNING);
			request.setAttribute("ALERT_SIGNUP_INFO", ALERT_SIGNUP_PASSWORDS_ARE_NOT_EQUAL);
			request.setAttribute("ALERT_SIGNUP_SHORT_VALUE_BORDER_PASSWORD",ALERT_SIGNUP_SHORT_VALUE_BORDER);
			return false;
		}
		return true;
	}
	
	/**
	 * Authenticating the user by a given email and password
	 * @param email
	 * @param password
	 * @param request
	 * @param response
	 * @return the next url
	 * @throws ServletException
	 * @throws IOException
	 */
	private String isAuthenticated(final String email,final String password,  HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String URL = null;
		User currentUser = null;
		DaoControllerLog.warn(LOGIN_AUTH_INIT);
		try {	
			currentUser = ToDoListDao.getInstance().getUser(email, password);
			if(null==currentUser){
				request.setAttribute("ALERT_LOGIN_VISIBLE", ALERT_VISIBLE);
				request.setAttribute("ALERT_LOGIN_STYLE","danger");
				request.setAttribute("ALERT_LOGIN_REASON_MAIN",ALERT_LOGIN_REASON_MAIN_USER_NOT_EXISTS);
				request.setAttribute("ALERT_LOGIN_REASON_DES",ALERT_LOGIN_REASON_DES_USER_NOT_EXISTS);
				URL = LOGIN_PATH;
			} else if(currentUser.getEmail().equals(email)  &&  currentUser.getPassword().equals(password)){
				session.setAttribute("sessionCurrentUser", currentUser);
				session.setAttribute("currentUserTasks", ToDoListDao.getInstance().getTasks(currentUser));
				URL = "tasks.jsp";
			} else {
				request.setAttribute("RESULT", "other problem");
				URL = LOGIN_PATH;
			}
		}  catch(UserPlatformException e){
			DaoControllerLog.error(GET_USER_ERROR_MESSAGE + e.getMessage());
			request.setAttribute("RESULT", e.getMessage());
			request.setAttribute("ALERT_LOGIN_VISIBLE", ALERT_VISIBLE);
			request.setAttribute("ALERT_LOGIN_STYLE",ALERT_STYLE_DANGER);
			request.setAttribute("ALERT_LOGIN_REASON_MAIN",ALERT_LOGIN_CONNECTION_PROBLEM);
			request.setAttribute("ALERT_LOGIN_REASON_DES",e.getMessage());
			URL = LOGIN_PATH;
			e.printStackTrace();
		} catch(TaskPlatformException e){
			DaoControllerLog.error(GET_TASK_LIST_ERROR_MESSAGE + e.getMessage());
			request.setAttribute("RESULT", e.getMessage());
			request.setAttribute("ALERT_LOGIN_VISIBLE", ALERT_VISIBLE);
			request.setAttribute("ALERT_LOGIN_STYLE",ALERT_STYLE_DANGER);
			request.setAttribute("ALERT_LOGIN_REASON_MAIN",ALERT_LOGIN_CONNECTION_PROBLEM);
			request.setAttribute("ALERT_LOGIN_REASON_DES",e.getMessage());
			URL = LOGIN_PATH;
			e.printStackTrace();
		}
		return URL;	
	}
}