package il.ac.shenkar.javaee.model;

public interface IModelValues {
	// first message of instance
	public static final String GET_INSTANCE = "Initializing instance of ToDoListDao";
	
	// addTask values
	public static final String UNABLE_TO_ADD_TASK = "Unable to add task";
	public static final String ADD_TASK_ERROR_MESSAGE = "Unable to add new task - taskTitle :";
	public static final String ADD_TASK_SUCCESS = "Added new task";

	// updateTask values
	public static final String UPDATE_TASK_SUCCESSFUL = "Task was successfully updated";
	public static final String UPDATE_TASK_NOT_SUCCESSFUL = "Task was not successfully updated";
	public static final String UPDATE_TASK_ERROR_MESSAGE = "Unable to update task - taskId :";

	// deleteTask values
	public static final String TASK_DELETED = "Task was successfully deleted";
	public static final String TASK_NOT_DELETED = "Task was not successfully deleted";
	public static final String DELETE_TASK_ERROR_MESSAGE = "Unable to delete task - taskId :";

	// getTasks values
	public static final String GET_TASKS_GOOD = "Tasks list was successfully fetched";
	public static final String GET_TASKS_NOT_GOOD = "Tasks list was not successfully fetched";
	public static final String GET_TASK_LIST_ERROR_MESSAGE = "Uable to get user tasks list : ";

	// signUp values
	public static final String SIGNUP_GOOD = "Signup user was successfull";
	public static final String SIGNUP_NOT_GOOD = "Signup user was not successfull";
	public static final String SIGNUP_ERROR_MESSAGE = "Unable to sign up new user : ";

	// getUser values
	public static final String GET_USER_GOOD = "User was succesfuuly fetched : ";
	public static final String GET_USER_NOT_GOOD = "User was not succesfuuly fetched : ";
	public static final String GET_USER_ERROR_MESSAGE = "Unable to get user : ";
	public static final String LOGIN_ROLLBACK_ERROR = "Unable to rollback :";
}