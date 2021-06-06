package com.david.Projectbest.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.david.Projectbest.beans.Company;

public interface CompanyRepo extends JpaRepository<Company, Integer> {

	boolean existsByName(String name);

	boolean existsByEmail(String email);

	Optional<Company> findByEmailAndPassword(String email, String password);

	boolean existsByIdAndName(int id, String name);

}
