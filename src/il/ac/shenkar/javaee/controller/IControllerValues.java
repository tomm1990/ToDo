package il.ac.shenkar.javaee.controller;

public interface IControllerValues {
	// DaoController session values
	public static String SESSION_CURRENT_USER = "sessionCurrentUser";
	
	// DaoController full paths
	public static final String DEFAULT_PATH = "daocontroller";
	public static final String LOGIN_PATH = "login.jsp";
	public static final String MENU_PATH = "menu.jsp";
	public static final String ABOUT_PATH = "about.jsp";
	public static final String TASKS_PATH = "tasks.jsp";
	public static final String SIGNUP_PATH = "signup.jsp";
	public static final String EDIT_PATH = "edit.jsp";

	// DaoController short paths
	public static final String LOGIN = "login";
	public static final String EDIT = "edit";
	public static final String ABOUT = "about";
	public static final String SIGNUP = "signup";
	public static final String TASKS = "tasks";
	public static final String LOGOUT = "logout";
	
	// DaoController cases
	public static final String NEW = "New";
	public static final String EXISTS = "exists";
	public static final String REMOVE = "remove_exists";

	// DaoController tasks page attributes
	public static String EDIT_STATUS_TASK = "EDIT_STATUS_TASK";
	public static String EDIT_NEW_FIELDS = "EDIT_NEW_FIELDS";
	public static String CURRENT_TASK = "CURRENT_TASK";

	// DaoController logger 
	public static final String SIGNUP_ERROR_MESSAGE = "Unable to sign up new user : ";
	public static final String GET_USER_ERROR_MESSAGE = "Unable to get user : ";
	public static final String GET_TASK_LIST_ERROR_MESSAGE = "Uable to get user tasks list : ";
	public static final String DELETE_TASK_ERROR_MESSAGE = "Unable to delete task - taskId :";
	public static final String UPDATE_TASK_ERROR_MESSAGE = "Unable to update task - taskId :";
	public static final String ADD_TASK_ERROR_MESSAGE = "Unable to add new task - taskTitle :";
	public static final String WARN_LOGGING_OUT = "Logging out";
	public static final String ENTERING_TASKS_PAGE = "Entering tasks page";
	public static final String ENTERING_EDIT_PAGE = "Entering edit page";
	public static final String ENTERING_LOGIN_PAGE = "Entering login page";
	public static final String ENTERING_ABOUT_PAGE = "Entering about page";
	public static final String USER_TASKS_LIST_REFRESHED = "User tasks list was refreshed";
	public static final String NEW_TASK = "New task button was pressed";
	public static final String EDIT_TASK = "Edit task button was pressed";
	public static final String REMOVE_TASK = "Remove task button was pressed";
	public static final String LOGIN_PROCESS_INIT = "Login process initialized";
	public static final String LOGIN_AUTH_INIT = "Authentication process initialized";
}