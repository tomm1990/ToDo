package il.ac.shenkar.javaee.model;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

import il.ac.shenkar.javaee.controller.DaoController;
import il.ac.shenkar.javaee.controller.IControllerValues;

/**
 * ToDoListDao data access object implementation responsible for delivering models
 * @author TomGoldberg
 * @version 1.1
 */
public class ToDoListDao implements IToDoListDao, IModelValues {
	
	/**
	 * ToDoListDao instance using singleton
	 */
	private static ToDoListDao instance;
	
	/**
	 * SessionFactory responsible for managing sessions
	 */
	private static SessionFactory factory;
	
	/**
	 * Session current session
	 */
	private Session session;
	
	/**
	 * ToDoListDaoLog log4j variable for logging messages
	 */
	private static Logger ToDoListDaoLog = Logger.getLogger(DaoController.class);

	/**
	 * Default C'tor
	 */
	private ToDoListDao() { }
	
	/**
	 * Singleton design pattern getInstance implementation
	 */
	public static ToDoListDao getInstance() {
		if (instance == null) {
			ToDoListDaoLog.warn(GET_INSTANCE);
			instance = new ToDoListDao();
			factory = new AnnotationConfiguration()
					.configure()
					.addAnnotatedClass(User.class)
					.addAnnotatedClass(Task.class)
					.buildSessionFactory();
		}
		return instance;
	}
	
	/*
	 * Add a new task to the current user
	 * @param title (new title to new task)
	 * @param description (new description to new task)
	 * @param userEmail (full user email)
	 * @return if successful then returns true, otherwise returns false
	 * @throws TaskPlatformException
	 */
	@Override
	public boolean addTask(final String title, final String description, final String userEmail) throws TaskPlatformException{
		Task task = new Task(title,description,userEmail);
		boolean success = true;
		session = factory.openSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(task);
			session.getTransaction().commit();
			ToDoListDaoLog.warn(ADD_TASK_SUCCESS);
		} catch  (HibernateException e){
			ToDoListDaoLog.error(UNABLE_TO_ADD_TASK);
			if (session.getTransaction() != null) 
				session.getTransaction().rollback();
			success = false;
			throw new TaskPlatformException(ADD_TASK_ERROR_MESSAGE+task.getTitle()+" - "+e.getMessage());
		}finally {
			session.close();
		}
		return success;
	}

	/*
	 * Updated user existing task by task id
	 * @param taskId (task id to be updated)
	 * @param updatedTask (updated task)
	 * @param user
	 * @return if successful then returns true, otherwise returns false
	 * @throws TaskPlatformException
	 */
	@Override
	public boolean updateTask(final int taskId, Task updatedTask ,final User user) throws TaskPlatformException{
		boolean updated = true;
		List<Task> list = null;
		list = getTasks(user);
		Task taskToUpdate = null;
		for(int i=0 ; i<list.size() ; i++){
			if(list.get(i).getId()==taskId){
				taskToUpdate = list.get(i);
				break;
			}
		}

		session = factory.openSession();
		
		try {
			session.beginTransaction();
			session.saveOrUpdate(taskToUpdate);
			taskToUpdate.setBody(updatedTask.getBody());
			taskToUpdate.setTitle(updatedTask.getTitle());
			Transaction transaction = session.getTransaction();
			if(!transaction.wasCommitted()) {
				ToDoListDaoLog.warn(UPDATE_TASK_SUCCESSFUL);
				transaction.commit();
			}		
		} catch(HibernateException e) {
			updated = false;
			ToDoListDaoLog.error(UPDATE_TASK_NOT_SUCCESSFUL);
			if (session.getTransaction() != null)
				session.getTransaction().rollback();
			throw new TaskPlatformException(UPDATE_TASK_ERROR_MESSAGE+taskToUpdate.getId()+" - "+e.getMessage());
		} finally {
			session.close();
		}
		return updated;
	}
	
	/*
	 * Deletes task from a user account
	 * @param userEmail (full user email)
	 * @param taskId (task id to delete)
	 * @throws TaskPlatformException
	 */
	@Override
	public void deleteTask(final String userEmail,final int taskId) throws TaskPlatformException{
		session = factory.openSession();
		try  {
			session.beginTransaction();
			Task taskToDelete = (Task)session.get(Task.class, taskId);
			if ( taskToDelete != null && taskToDelete.getUserEmail().equals(userEmail) ) {
				session.delete(taskToDelete);
				session.getTransaction().commit();
				ToDoListDaoLog.warn(TASK_DELETED);
			}
		} catch(HibernateException e)  {
			ToDoListDaoLog.error(TASK_NOT_DELETED);
			if (session.getTransaction() != null)
				session.getTransaction().rollback();
			throw new TaskPlatformException(DELETE_TASK_ERROR_MESSAGE + taskId);
		} finally {
			session.close();
		}
	}
	
	/*
	 * Get the user tasks list
	 * @param user
	 * @return if is successful then returns List<Task>, otherwise returns null
	 * @throws TaskPlatformException
	 */
	@Override
	public List<Task> getTasks(final User user) throws TaskPlatformException{
		session = factory.openSession();
		List<Task> list = null;
		try {
			session.beginTransaction();
			list = session.createQuery("from Task task where USEREMAIL = '" + user.getEmail() + "'").list();
			session.getTransaction().commit();
			ToDoListDaoLog.warn(GET_TASKS_GOOD);
		} catch(HibernateException e)  {
			ToDoListDaoLog.error(GET_TASKS_NOT_GOOD);
			if (session.getTransaction() != null)
				session.getTransaction().rollback();
			throw new TaskPlatformException(GET_TASK_LIST_ERROR_MESSAGE+user.getEmail());
		} finally  {
			session.close();
		}
		return list;
	}
	
	/*
	 * Signs up a new user
	 * @param user
	 * @return (success) ? User : null ;
	 * @throws UserPlatformException
	 */
	@Override
	public User signUp(final User user) throws UserPlatformException {
		session = factory.openSession();
		int newUserId = -1;
		try  {
			session.beginTransaction();
			newUserId = (int)session.save(user);
			session.getTransaction().commit();
			ToDoListDaoLog.warn(SIGNUP_GOOD);
		} catch(HibernateException e)  {
			ToDoListDaoLog.error(SIGNUP_NOT_GOOD);
			if (session.getTransaction() != null)
				session.getTransaction().rollback();
			newUserId = -1;
			throw new UserPlatformException(SIGNUP_ERROR_MESSAGE+e.getMessage());
		} finally  {
			session.close();
		}
		if (newUserId > -1) 
			return user;
		else 
			return null;
	}
	
	/*
	 * get User entity from database using email and password
	 * @param email (full user email)
	 * @param password (full user password)
	 * @return if is successful then returns User, otherwise returns null
	 * @throws UserPlatformException
	 */
	public User getUser(final String email, final String password) throws UserPlatformException {
		session = factory.openSession();
		List<User> users = null;
		try {
			session.beginTransaction();
			String hql = "FROM User user WHERE user.email ='"+email+"' and user.password = '"+password+"'";
			users = session.createQuery(hql).list();
			session.getTransaction().commit();
			ToDoListDaoLog.warn(GET_USER_GOOD+email);
		} catch(HibernateException e)  {
			ToDoListDaoLog.error(GET_USER_NOT_GOOD+email);
			if (session.getTransaction() != null)
				try{
					session.getTransaction().rollback();
				} catch(HibernateException e1){
					ToDoListDaoLog.error(LOGIN_ROLLBACK_ERROR+email);
				}
			throw new UserPlatformException(GET_USER_ERROR_MESSAGE + email);
		} finally  {
			session.close();
		}
		if (users != null) {
			if (users.size() > 0){
				return users.get(0);
			}		
		}
		return null;
	}
}
