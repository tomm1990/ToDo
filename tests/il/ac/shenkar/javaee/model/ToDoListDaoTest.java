package il.ac.shenkar.javaee.model;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

/**
 * ToDoListDaoTest for test cases
 * @author TomGoldberg, ItayNoama, OrBenDavid
 * @version 1.0
 */
public class ToDoListDaoTest extends TestCase {
	ToDoListDao instance;
	Session session;
	SessionFactory factory;

	@Before
	protected void setUp() throws Exception {
		instance = ToDoListDao.getInstance();
		factory = instance.getSessionFactory();
	}

	@After
	protected void tearDown() throws Exception {
		
	}

	/**
	 * Tests if instance of ToDoListDao is available
	 */
	@Test
	public void testGetInstance() {
		assertNotNull("Test if instance of ToDoListDao is valid",instance);
	}

	/**
	 * Tests if task was successfully added
	 * @throws TaskPlatformException
	 */
	@Test
	public void testAddTask() throws TaskPlatformException {
		final Task task = new Task("junitTaskTitle","junitTaskDes","junitUserEmail");
		final User user = new User("junitUserFName", "junitUserLName", "junitUserEmail", "junitUserPassword");

		session = factory.openSession();
		
		session.beginTransaction();
		session.saveOrUpdate(task);
		session.getTransaction().commit();
		
		session.close();
		
		List<Task> tasks = instance.getTasks(user);
		assertEquals("Test if task was added to user", task, tasks.get(tasks.size()-1));
	}

	/**
	 * Tests if task was successfully added
	 * @throws TaskPlatformException
	 */
	@Test
	public void testUpdateTask() throws TaskPlatformException {
		final int taskId = 2; // Check if exists
		Task updatedTask = new Task("junitTaskTitle","junitTaskDes","orbenda1905@gmail.com");
		final User user = new User("junitUserFName", "junitUserLName", "orbenda1905@gmail.com", "junitUserPassword");
		
		List<Task> list = null;
		list = (List<Task>)instance.getTasks(user);
		Task taskToUpdate = null;
		for(int i=0 ; i<list.size() ; i++){
			if(list.get(i).getId()==taskId){
				taskToUpdate = list.get(i);
				break;
			}
		}
		
		session = factory.openSession();

		session.beginTransaction();
		session.saveOrUpdate(taskToUpdate);
		taskToUpdate.setBody(updatedTask.getBody());
		taskToUpdate.setTitle(updatedTask.getTitle());
		Transaction transaction = session.getTransaction();
		if(!transaction.wasCommitted()) {
			transaction.commit();
		}
		
		session.close();
		
		list = null;
		list = instance.getTasks(user);
		taskToUpdate = null;
		for(int i=0 ; i<list.size() ; i++){
			if(list.get(i).getId()==taskId){
				taskToUpdate = list.get(i);
				break;
			}
		}
		
		assertEquals("Test if the updated was successfull",updatedTask,taskToUpdate);
	}
	
	/**
	 * Tests if a task was successfully deleted
	 * @throws TaskPlatformException
	 */
	@Test
	public void testDeleteTask() throws TaskPlatformException{
		final String userEmail = "junitUserEmail";
		final int taskId = 7; // Check if exists
		
		session = factory.openSession();

		session.beginTransaction();
		Task taskToDelete = (Task)session.get(Task.class, taskId);
		session.delete(taskToDelete);
		session.getTransaction().commit();
		
		session.close();
		
		final User user = new User("", "", userEmail, "");

		List<Task> list = null;
		list = (List<Task>)instance.getTasks(user);
		Task taskToUpdate = null;
		for(int i=0 ; i<list.size() ; i++){
			if(list.get(i).getId()==taskId){
				taskToUpdate = list.get(i);
				break;
			}
		}
		
		assertNull("Test if task was succesfully deleted",taskToUpdate);
	}

	/**
	 * Tests if user task list is available
	 * @throws TaskPlatformException
	 */
	@Test
	public void testGetTasks() throws TaskPlatformException {
		final User user = new User("", "", "junitUserEmail", "");
		List<Task> list = null;
		
		session = factory.openSession();
		
		session.beginTransaction();
		list = session.createQuery("from Task task where USEREMAIL = '" + user.getEmail() + "'").list();
		session.getTransaction().commit();
		
		session.close();
		
		assertTrue("Test is user tasks list is not empty",list.size()>0);
	}

	/**
	 * Tests if user was successfully signed up
	 * @throws UserPlatformException
	 */
	@Test
	public void testSignUp() throws UserPlatformException{
		final String email = "junitUserEmail2", pass = "testPass";
		final User user = new User("", "", email, pass);
		session = factory.openSession();

		session.beginTransaction();
		session.save(user);
		session.getTransaction().commit();
		
		session.close();
		
		User newUser = instance.getUser(email, pass);
		
		assertEquals("Test if last user was successfully signed up", newUser, user);
	}

	/**
	 * Test if the user is available
	 */
	@Test
	public void testGetUser() {
		final String email = "junitUserEmail2", password = "testPass";
		User user = new User("", "", email, password);
		List<User> users = null;
		User userToTest = null;
		
		session = factory.openSession();
		session.beginTransaction();
		String hql = "FROM User user WHERE user.email ='"+email+"' and user.password = '"+password+"'";
		users = session.createQuery(hql).list();
		session.getTransaction().commit();
		
		session.close();
		
		if (users.size() > 0){
			userToTest = users.get(0);
		}
		
		assertEquals("Test if user was successfully fetched", userToTest, user);
	}
}