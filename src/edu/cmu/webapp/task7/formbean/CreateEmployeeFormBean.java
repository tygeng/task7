package edu.cmu.webapp.task7.formbean;

import java.util.ArrayList;
import java.util.List;

public class CreateEmployeeFormBean extends MyFormBean {
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private String confirmPassword;
	private String action;

	@Override
	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<>();

		if (getUsername() == null || getUsername().length() == 0) {
			errors.add("User Name is required");
		}
		if (getFirstName() == null || getFirstName().length() == 0) {
			errors.add("First Name is required");
		}
		if (getLastName() == null || getLastName().length() == 0) {
			errors.add("Last Name is required");
		}
		if (getPassword() == null || getPassword().length() == 0) {
			errors.add("Password is required");
		}
		if (getConfirmPassword() == null || getConfirmPassword().length() == 0) {
			errors.add("Confirm password is required");
		}

		if (!getPassword().equals(getConfirmPassword())) {
			errors.add("Passwords are not the same");
		}
		if (getAction() == null) {
			errors.add("Button is required");
		}

		if (errors.size() > 0) {
			return errors;
		}

		if (getUsername().matches(".*[<>\"].*")) {
			errors.add("User Name may not contain angle brackets or quotes");
		}
		if (getFirstName().matches(".*[<>\"].*")) {
			errors.add("First Name may not contain angle brackets or quotes");
		}
		if (getLastName().matches(".*[<>\"].*")) {
			errors.add("Last Name may not contain angle brackets or quotes");
		}
		if (getPassword().matches(".*[<>\"].*")) {
			errors.add("Password may not contain angle brackets or quotes");
		}
		if (!getAction().equals("Create Employee")) {
			errors.add("Invalid button");
		}
		return errors;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
}
