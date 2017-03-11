package il.ac.shenkar.javaee.model;

import java.util.List;

/**
 * IToDoListDao interface for methods decelerations only
 * @author TomGoldberg
 */
public interface IToDoListDao {
	
	/**
	 * Add a new task to the current user
	 * @param title (new title to new task)
	 * @param description (new description to new task)
	 * @param userEmail (full user email)
	 * @return if successful then returns true, otherwise returns false
	 * @throws TaskPlatformException
	 */
	public boolean addTask(final String title, final String description, final String userEmail) throws TaskPlatformException;
	
	/**
	 * Updated user existing task by task id
	 * @param taskId (task id to be updated)
	 * @param updatedTask (updated task)
	 * @param user
	 * @return if successful then returns true, otherwise returns false
	 * @throws TaskPlatformException
	 */
	public boolean updateTask(final int taskId, Task updatedTask ,final User user) throws TaskPlatformException;
	
	/**
	 * Deletes task from a user account
	 * @param userEmail (full user email)
	 * @param taskId (task id to delete)
	 * @throws TaskPlatformException
	 */
	public void deleteTask(final String userEmail,final int taskId) throws TaskPlatformException;
	
	/**
	 * Get the user tasks list
	 * @param user
	 * @return if successful then returns List<Task>, otherwise returns null
	 * @throws TaskPlatformException
	 */
	public List<Task> getTasks(final User user) throws TaskPlatformException;
	
	/**
	 * Signs up a new user
	 * @param user
	 * @return if successful then returns User, otherwise returns null
	 * @throws UserPlatformException
	 */
	public User signUp(final User user) throws UserPlatformException;
	
	/**
	 * get User entity from database using email and password
	 * @param email (full user email)
	 * @param password (full user password)
	 * @return if successful then returns User, otherwise returns null
	 * @throws UserPlatformException
	 */
	public User getUser(final String email, final String password) throws UserPlatformException;	
}