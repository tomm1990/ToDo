package il.ac.shenkar.javaee.model;

/**
 * UserPlatformException java class
 * To deal with Exceptions related to data access objects of User Entity by Hibernate
 * @author TomGoldberg, ItayNoama, OrBenDavid
 */
public class UserPlatformException extends Exception{

	private static final long serialVersionUID = 1L;

	/**
	 * UserPlatformException constructor
	 * @param message
	 */
	public UserPlatformException(String message) {
		super(message);
	}
	
	/**
	 * UserPlatformException constructor
	 * @param message
	 * @param throwable
	 */
	public UserPlatformException(String message, Throwable throwable) {
		super(message,throwable);
	}
}