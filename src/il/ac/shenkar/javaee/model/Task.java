package il.ac.shenkar.javaee.model;
import javax.persistence.*;

/**
 * class Task to represent task with title, description and email
 */
@Entity
@Table(name="Task",uniqueConstraints = {@UniqueConstraint(columnNames={"id"})})
public class Task {
	
	@Column (name = "ID")
	private int _id;
	
	@Column (name = "TITLE")
	private String _title;
	
	@Column (name = "BODY")
	private String _body;
	
	@Column (name = "USEREMAIL")
	private String _userEmail;
	
	/**
	 * Default C'tor
	 */
	public Task() {}
	
	/**
	 * C'tor
	 * @param title
	 * @param body
	 * @param userName
	 */
	public Task( String title, String body, String userName ) {
		setUserEmail(userName);
		setTitle(title);
		setBody(body);
	}
	
	/**
	 * Getter to the task id
	 * @return _id
	 */
	@Id
	@GeneratedValue
	public int getId() {
		return _id;
	}
	
	/**
	 * Setter for the task id
	 * @param id
	 */
	public void setId(int id) {
		_id = id;
	}

	/**
	 * Getter to the task title
	 * @return _title
	 */
	public String getTitle() {
		return _title;
	}

	/**
	 * Setter for the task title
	 * @param title
	 */
	public void setTitle(String title) {
		_title = title;
	}

	/**
	 * Getter to the task body
	 * @return _body
	 */
	public String getBody() {
		return _body;
	}

	/**
	 * Setter for the task body
	 * @param body
	 */
	public void setBody(String body) {
		_body = body;
	}

	/**
	 * Getter to the task user email
	 * @return _userEmail
	 */
	public String getUserEmail() {
		return _userEmail;
	}

	/**
	 * Setter for the task user email
	 * @param userEmail
	 */
	public void setUserEmail(String userEmail) {
		_userEmail = userEmail;
	}

	/**
	 * class Task toString
	 */
	@Override
	public String toString() {
		return "Task [_id=" + _id + ", _title=" + _title + ", _body=" + _body + ", _userEmail=" + _userEmail + "]";
	}	
}