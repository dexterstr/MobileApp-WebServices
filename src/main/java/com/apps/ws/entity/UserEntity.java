package com.apps.ws.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class UserEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long id;
	
	@Column(nullable=false)
	private String userId;
	
	@Column(nullable=false,length=50)
	private String firstName;
	
	@Column(nullable=false,length=50)
	private String lastName;
	
	
	@Column(nullable=false,length=120)
	private String email;
	
	@Column(nullable=false)
	private String encryptedPassword;
	

	
	private String emailVerficationToken;

	@Column(nullable=false)
	private Boolean emailVerfificationStatus=false;
	
	@OneToMany(mappedBy="userDetails",cascade=CascadeType.ALL)
	//mention name of field 
	private List<AddressEntity> addresses;
	
	
	

	public List<AddressEntity> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<AddressEntity> addresses) {
		this.addresses = addresses;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	public String getEmailVerficationToken() {
		return emailVerficationToken;
	}

	public void setEmailVerficationToken(String emailVerficationToken) {
		this.emailVerficationToken = emailVerficationToken;
	}

	public Boolean getEmailVerfificationStatus() {
		return emailVerfificationStatus;
	}

	public void setEmailVerfificationStatus(Boolean emailVerfificationStatus) {
		this.emailVerfificationStatus = emailVerfificationStatus;
	}
	
	
}
