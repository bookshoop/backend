package com.project.bookforeast.common.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {

	private String socialId;
	private String socialProvider;
	private Collection<? extends GrantedAuthority> authorities;
	
	
	 public CustomUserDetails(String socialId, String socialProvider, Collection<? extends GrantedAuthority> authorities) {
        this.socialId = socialId;
        this.socialProvider = socialProvider;
        // ROLE_USER 권한을 가지도록 설정
        this.authorities = authorities;
    }
	
	 
	public String getSocialId() {
        return socialId;
    }

    public String getSocialProvider() {
        return socialProvider;
    }
	    
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
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
