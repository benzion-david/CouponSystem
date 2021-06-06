package com.david.Projectbest.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.david.Projectbest.beans.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Integer> {

	boolean existsByEmail(String email);

	Optional<Customer> findByEmailAndPassword(String email, String password);

}
