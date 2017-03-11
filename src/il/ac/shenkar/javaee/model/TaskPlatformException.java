package il.ac.shenkar.javaee.model;

/**
 * TasksPlatformException java class
 * To deal with Exceptions related to data access objects of Task Entity by Hibernate
 * @author TomGoldberg, ItayNoama, OrBenDavid
 */
public class TaskPlatformException extends Exception{

	private static final long serialVersionUID = 1L;
	
	/**
	 * TaskPlatformException constructor
	 * @param massage
	 */
	public TaskPlatformException(String massage) {
		super(massage);
	}
	
	/**
	 * TaskPlatformException constructor
	 * @param massage
	 * @param throwable
	 */
	public TaskPlatformException(String massage, Throwable throwable) {
		super(massage,throwable);
	}
}