package com.example.SpringBoot.Entities;

import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "User")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@NotBlank(message = "* required field")
	@Size(min = 4, message = "Minimum 4 character required")
	private String name;

	@Column(unique = true)
	@NotBlank(message = "* required field")
	@Email(regexp = "^[a-zA-Z0-9]{2,}+@[a-zA-Z]{5,}+.[a-zA-Z]{2,}$", message = "Invalid email")
	private String email;

	@NotBlank(message = "* required field")
	@Size(min = 4, message = "Minimum 4 character required")
	private String password;

	@NotBlank(message = "* required field")
	@Pattern(regexp = "(^$|[0-9]{10})", message = "10 digits required")
	private String contact;

	@NotNull(message = "* required field")
	private String gender;

	@NotBlank(message = "* required field")
	private String city;

	@Transient
	public TreeSet<String> cities;

	public User() {
		cities = new TreeSet<String>();
		cities.add("Meerut");
		cities.add("Noida");
		cities.add("Ghaziabad");
		cities.add("Gurugram");
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", contact="
				+ contact + ", gender=" + gender + ", city=" + city + "]";
	}

}
