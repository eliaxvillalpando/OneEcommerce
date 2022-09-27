package com.iudc.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.iudc.common.entity.Customer;
import com.iudc.customer.CustomerRepository;

public class CustomerUserDetailsService implements UserDetailsService {
	
	@Autowired private CustomerRepository repo;
	
        
        //Este metodo se incova por spring para autenticación
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Customer customer = repo.findByEmail(email);
		if (customer == null) 
			throw new UsernameNotFoundException("No se encontro el correo " + email);
		
		return new CustomerUserDetails(customer);
	}

}
