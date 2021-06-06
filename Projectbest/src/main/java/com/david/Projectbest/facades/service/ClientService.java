package com.david.Projectbest.facades.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.david.Projectbest.exceptions.CouponSystemException;
import com.david.Projectbest.repos.CompanyRepo;
import com.david.Projectbest.repos.CouponRepo;
import com.david.Projectbest.repos.CustomerRepo;

@Service
public abstract class ClientService {

	@Autowired
	CompanyRepo companyRepo;

	@Autowired
	CustomerRepo customerRepo;

	@Autowired
	CouponRepo couponRepo;

	public abstract boolean clientLogin(String email, String password) throws CouponSystemException;

}
