package il.ac.shenkar.javaee.model;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * Standalone application for run all tests realted to ToDo project
 * @author TomGoldberg, ItayNoama, OrBenDavid
 */
public class TestRun {
	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(ToDoListDaoTest.class);
		
		for (Failure failure : result.getFailures()) {
	         System.out.println(failure.toString());
	    }
		
		System.out.println(result.wasSuccessful());

	}
}