package com.jwt.entity;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.jwt.enums.PERMISSIONS;
import com.jwt.enums.ROLES;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "name", nullable = true)
	private String name;

	@Column(name = "username", nullable = true, unique = true)
	private String username;

	@Column(name = "password", nullable = true)
	private String password;

	@Column(name = "roles", nullable = true)
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "roles", joinColumns = @JoinColumn(name = "user_id"))
	@Enumerated(EnumType.STRING)
	private Set<ROLES> roles;

	@Column(name = "permissions", nullable = true)
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "permissions", joinColumns = @JoinColumn(name = "user_id"))
	@Enumerated(EnumType.STRING)
	private Set<PERMISSIONS> permissions;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		Set<String> rolesStream = roles.stream()
				.collect(Collectors.mapping(role -> "ROLE_" + String.valueOf(role), Collectors.toSet()));

		return Stream.concat(rolesStream.stream(), permissions.stream()).collect(
				Collectors.mapping(role -> new SimpleGrantedAuthority(String.valueOf(role)), Collectors.toSet()));
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

	@Override
	public boolean isEnabled() {
		return true;
	}

}
