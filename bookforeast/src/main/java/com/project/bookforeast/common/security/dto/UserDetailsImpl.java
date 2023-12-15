package com.project.bookforeast.common.security.dto;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.project.bookforeast.user.dto.UserDTO;
import com.project.bookforeast.user.entity.User;

@Component
public class UserDetailsImpl implements UserDetails {

	private User user;
	
	public void setUser(User user) {
		this.user = user;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		String role = user.getRole();
		SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role);
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(simpleGrantedAuthority);
		return authorities;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}

}
