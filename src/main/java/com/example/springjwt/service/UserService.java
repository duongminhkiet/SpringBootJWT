package com.example.springjwt.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springjwt.entities.Role;
import com.example.springjwt.entities.User;
import com.example.springjwt.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	public boolean checkPassword(final String passwordIn, final String passEncoded) {
		return passwordEncoder.matches(passwordIn, passEncoded);
	}

	@Autowired
	private UserRepository userRepository;

	public User findByUsername(String email) {
		return userRepository.findByUsername(email);
	}
	public List<User> findAll(){
		return userRepository.findAll();
	}
	public User findById(final int id) {
		return userRepository.findById(id);
	}
	public User add(final User user) {
		return userRepository.save(user);
	}
	public void delete(final int id) {
		userRepository.deleteById(id);
	}
	public boolean checkUserExistingInDB(User user) {
		User userFound = userRepository.findByUsername(user.getUsername());
		if (null != userFound) {
			return checkPassword(user.getPassword(), userFound.getPassword());
		}
		return false;
	}


	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}

		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		Set<Role> roles = user.getRoles();
		for (Role role : roles) {
			grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
		}

		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				grantedAuthorities);
	}
}