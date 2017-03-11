package il.ac.shenkar.javaee.view;

public interface IViewValues {
	// head values
	public static final String HEAD_ICON = "<link rel='icon' type='image/png' href='/ToDo/images/icon.png'>";
	public static final String HEAD_META_CONTENT_TYPE = "<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>";
	public static final String HEAD_STYLE = "<link rel='stylesheet' href='/ToDo/css/style.css'>";
	public static final String HEAD_META_VIEWPORT = "<meta name='viewport' content='width=device-width, initial-scale=1.0'>";
	public static final String HEAD_SCRIPT_JQUERY_AJAX = "<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js'></script>";
	public static final String HEAD_SCRIPT_JQUERY = "<script src='https://code.jquery.com/jquery-latest.min.js'></script>";
	public static final String HEAD_STYLE_BOOTSTRAP_MIN = "<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css' integrity='sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u' crossorigin='anonymous'>";
	public static final String HEAD_STYLE_BOOTSTRAP = "<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css' integrity='sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp' crossorigin='anonymous'>";
	public static final String HEAD_SCRIPT_BOOTSTRAP = "<script src='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js' integrity='sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa' crossorigin='anonymous'></script>";
	
	// general alerts consts
	public static final String ALERT_VISIBLE = "visible";
	public static final String ALERT_STYLE_WARNING = "warning";
	public static final String ALERT_STYLE_INFO = "info";
	public static final String ALERT_STYLE_DANGER = "danger";

	// login.jsp alert consts
	public static final String ALERT_LOGIN_STYLE = "alert";
	public static final String ALERT_LOGIN_REASON_MAIN_USER_NOT_EXISTS = "User does not exists!";
	public static final String ALERT_LOGIN_REASON_DES_USER_NOT_EXISTS = " - <a class='alert-link' href='signup.jsp'>Please Sign Up</a>";
	public static final String ALERT_LOGIN_CONNECTION_PROBLEM = "Connection problem - ";
	public static final String ALERT_LOGIN_BAD_EMAIL_ADRRESS = "Bad Email Address";
	public static final String ALERT_LOGIN_REASON_MAIN_EMAIL = "Warning! -";
	public static final String ALERT_LOGIN_REASON_DES_PASS = "Password should be at least 2 characters";

	// tasks.jsp alert consts
	public static final String ALERT_TASKS_INFO = "Task saved";
	public static final String ALERT_TASKS_INFO_DELETED_SUCCESSFULLY = "Task was removed";

	// signup.jsp alert consts
	public static final String ALERT_SIGNUP_STYLE = "alert";
	public static final String ALERT_SIGNUP_INFO = "Unable to save, please check you inputs";
	public static final String ALERT_SIGNUP_SHORT_VALUE = "Please provide a value that contains at least 3 characters";
	public static final String ALERT_SIGNUP_SHORT_VALUE_BORDER = "1px solid red;";
	public static final String ALERT_SIGNUP_INVALID_EMAIL_ADDRESS = "Please provide a valid email address";
	public static final String ALERT_SIGNUP_PASSWORD_TOO_SHORT = "Password is too short. It should be atleast 3 characters long";
	public static final String ALERT_SIGNUP_PASSWORDS_ARE_NOT_EQUAL = "Passwords are not equal";

	// edit.jsp alert consts
	public static final String ALERT_EDIT_TASK_GENERAL = "Unable to update";
	public static final String ALERT_EDIT_NEW_TASK = "Unable to save new task";
}
