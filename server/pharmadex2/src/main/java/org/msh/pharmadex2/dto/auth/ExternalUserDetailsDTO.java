package org.msh.pharmadex2.dto.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.msh.pharmadex2.dto.form.AllowValidation;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * User details to display on screen and to use for Spring Security
 * @author alexk
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class ExternalUserDetailsDTO extends AllowValidation implements UserDetails {
	private static final long serialVersionUID = -6035017843014009134L;
	private String password="";
	private String userName="";
	private String name="";
	private String email="";
	private String state="";
	private boolean expired=false;
	private boolean locked=false;
	private boolean active=true;
	private List<UserRoleDto> granted = new ArrayList<>();

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public List<UserRoleDto> getGranted() {
		return granted;
	}

	public void setGranted(List<UserRoleDto> granted) {
		this.granted.clear();
		this.granted.addAll(granted);
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return getGranted();
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return !isExpired();
	}

	@Override
	public boolean isAccountNonLocked() {
		return ! isLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return isActive();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
