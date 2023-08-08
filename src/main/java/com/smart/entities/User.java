package com.smart.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name="USER")
public class User implements UserDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	 
	private String name;
	@Column(unique = true)
	private String email;
	private String passward;
	private boolean enabled;
	private String imageUrl;
	@Column(length=500)
	private String about;
	
	@ElementCollection
	private List<String> role;
	
	public void addRole(String newRoleValue) {
	    if (role == null) {
	        role = new ArrayList<>();
	    }
	    role.add(newRoleValue);
	    
	}


	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	private List<Contacts> contacts = new ArrayList<>();
	
	public User() { }
	
	
	
	public User(String email, String passward, Collection<? extends GrantedAuthority> authorities) {
	    super();
	    this.email = email;
	    this.passward = passward;
	    this.authorities = authorities;
	    this.role = new ArrayList<>();
	    for (GrantedAuthority authority : authorities) {
	        this.role.add(authority.getAuthority());
	    }
	}

	
	@Transient
	private Collection<? extends GrantedAuthority> authorities;

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public String getPassward() {
		return passward;
	}

	public void setPassward(String passward) {
		this.passward = passward;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}


	public List<Contacts> getContacts() {
		return contacts;
	}


	public void setContacts(List<Contacts> contacts) {
		this.contacts = contacts;
	}
	
	
	public List<String> getRoles() {
		return role;
	}

	public void setRoles(List<String> role) {
		this.role = role;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", passward=" + passward + ", role=" + role
				+ ", enabled=" + enabled + ", imageUrl=" + imageUrl + ", about=" + about + ", contacts=" + contacts
				+ "]";
	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}


	@Override
	public String getPassword() {
		return this.passward;
	}


	@Override
	public String getUsername() {
		return this.email;
	}


	@Override
	public boolean isAccountNonExpired() {
		return true;
	}


	@Override
	public boolean isAccountNonLocked() {
		return true;
	}


	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}


}
