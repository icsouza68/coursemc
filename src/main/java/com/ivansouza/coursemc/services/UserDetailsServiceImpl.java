package com.ivansouza.coursemc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ivansouza.coursemc.domain.Cliente;
import com.ivansouza.coursemc.repositories.ClienteRepository;
import com.ivansouza.coursemc.security.UserSS;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private ClienteRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Cliente cli = repo.findByEMail(email);
		if (cli == null) {
			throw new UsernameNotFoundException(email);
		}
		return new UserSS(cli.getId(), cli.geteMail(), cli.getSenha(), cli.getPerfis());
	}

}
