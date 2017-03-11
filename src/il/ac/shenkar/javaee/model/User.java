package il.ac.shenkar.javaee.model;

import javax.persistence.*;

/**
 * class User to represent user with first name, last name, email and password
 */
@Entity
@Table(name="User",uniqueConstraints = {@UniqueConstraint(columnNames={"email"})})
public class User {
	
	@Column (name = "ID")
	private int _id;
	
	@Column (name = "FNAME")
	private String _fname;
	
	@Column (name = "LNAME")
	private String _lname;
	
	@Column (name = "EMAIL")
	private String _email;
	
	@Column (name = "PASSWORD")
	private String _password;
	
	/**
	 * Default C'tor
	 */
	public User() {}
	
	/**
	 * C'tor
	 * @param fname
	 * @param lname
	 * @param email
	 * @param password
	 */
	public User(String fname, String lname, String email, String password) {
		setFname(fname);
		setLname(lname);
		setEmail(email);
		setPassword(password);
	}
	
	/**
	 * Getter to the user id
	 * @return _id
	 */
	@Id
	@GeneratedValue
	public int getId() {
		return _id;
	}
	
	/**
	 * Setter for the user id
	 * @param id
	 */
	public void setId(int id) {
		_id = id;
	}

	/**
	 * Getter to the user first name
	 * @return _fname
	 */
	public String getFname() {
		return _fname;
	}

	/**
	 * Setter for the user first name
	 * @param fname
	 */
	public void setFname(String fname) {
		_fname = fname;
	}

	/**
	 * Getter to the user last name
	 * @return _lname
	 */
	public String getLname() {
		return _lname;
	}

	/**
	 * Setter for the user last name
	 * @param lname
	 */
	public void setLname(String lname) {
		_lname = lname;
	}

	/**
	 * Getter to the user email address
	 * @return _email
	 */
	public String getEmail() {
		return _email;
	}

	/**
	 * Setter for the user email address
	 * @param email
	 */
	public void setEmail(String email) {
		_email = email;
	}

	/**
	 * Getter to the user password
	 * @return _password
	 */
	public String getPassword() {
		return _password;
	}

	/**
	 * Setter for the user password
	 * @param password
	 */
	public void setPassword(String password) {
		_password = password;
	}

	/**
	 * class Task toString
	 */
	@Override
	public String toString() {
		return "User [_id=" + _id + ", _fname=" + _fname + ", _lname=" + _lname + ", _email=" + _email + ", _password="	+ _password + "]";
	}

	/**
	 * equals method
	 * @param obj
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (_email == null) {
			if (other._email != null)
				return false;
		} else if (!_email.equals(other._email))
			return false;
		if (_fname == null) {
			if (other._fname != null)
				return false;
		} else if (!_fname.equals(other._fname))
			return false;
		if (_lname == null) {
			if (other._lname != null)
				return false;
		} else if (!_lname.equals(other._lname))
			return false;
		if (_password == null) {
			if (other._password != null)
				return false;
		} else if (!_password.equals(other._password))
			return false;
		return true;
	}	
}