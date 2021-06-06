package com.david.Projectbest.dbUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.david.Projectbest.repos.CompanyRepo;
import com.david.Projectbest.repos.CouponRepo;
import com.david.Projectbest.repos.CustomerRepo;

@Component
public class DatabaseReport {
	@Autowired
	CompanyRepo companyRepo;

	@Autowired
	CouponRepo couponRepo;

	@Autowired
	CustomerRepo customerRepo;

	public void dbReport() {

		int numCoupons = (int) couponRepo.count();

		int numCustomer = (int) customerRepo.count();

		int numCompanies = (int) companyRepo.count();

		System.out.println("Coupons : " + numCoupons);
		System.out.println("Customers : " + numCustomer);
		System.out.println("Companies: " + numCompanies);

	}

	public void fullPrintdbReport() {

		if (couponRepo.count() > 0) {

			System.out.println();
			System.out.println("Coupons : ");

			couponRepo.findAll().forEach(System.out::println);
		}

		if (companyRepo.count() > 0) {

			System.out.println();
			System.out.println("Companies : ");

			companyRepo.findAll().forEach(System.out::println);
		}

		if (customerRepo.count() > 0) {
			System.out.println();
			System.out.println("Customers : ");

			customerRepo.findAll().forEach(System.out::println);
		}

		System.out.println();
		System.out.println("-------------");

		dbReport();

	}

}
