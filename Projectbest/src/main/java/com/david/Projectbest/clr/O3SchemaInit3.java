package com.david.Projectbest.clr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.david.Projectbest.beans.Company;
import com.david.Projectbest.beans.Customer;
import com.david.Projectbest.dbUtils.DatabaseReport;
import com.david.Projectbest.exceptions.CouponSystemException;
import com.david.Projectbest.facades.AdminService;
import com.david.Projectbest.loginSecurity.LoginManager;
import com.david.Projectbest.repos.CompanyRepo;
import com.david.Projectbest.repos.CouponRepo;
import com.david.Projectbest.repos.CustomerRepo;
import com.david.Projectbest.utils.BentziUtils;

@Component
@Order(1)
public class O3SchemaInit3 implements CommandLineRunner {

	@Autowired
	DatabaseReport dbr;

	@Autowired
	CompanyRepo companyRepo;

	@Autowired
	CouponRepo couponRepo;

	@Autowired
	CustomerRepo customerRepo;

	@Autowired
	AdminService adminService;

	@Autowired
	LoginManager loginManager;

	@Override
	public void run(String... args) {

		BentziUtils.testingHeader("Admin gets all companies");

		adminService.getAllCompanies().forEach(System.out::println);

		BentziUtils.testingHeader("Admin gets all customers");

		adminService.getAllCustomers().forEach(System.out::println);

		BentziUtils.testingHeader("Admin gets one company");

		try {
			System.out.println(adminService.getOneCompany(13));
		} catch (CouponSystemException e) {

			System.out.println(e.getMessage());
		}

		BentziUtils.testingHeader("Admin tries to get company which is already deleted ");

		try {
			System.out.println(adminService.getOneCompany(1));
		} catch (CouponSystemException e) {

			System.out.println(e.getMessage());
		}

		BentziUtils.testingHeader("Admin gets one customer");

		try {
			System.out.println(adminService.getSingleCustomer(1));
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}

		BentziUtils.testingHeader("Admin updates company password");

		Company companyToUpdate = null;

		try {
			companyToUpdate = companyRepo.findById(8)
					.orElseThrow(() -> new CouponSystemException("Sorry, company does not exist"));
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}

		BentziUtils.seperator();
		companyToUpdate.setPassword("This is a new password");

		System.out.println("New password should be : This is a new password");

		BentziUtils.testingHeader("Admin adds company babylon to system ");

		dbr.dbReport();

		try {
			adminService
					.addCompany(Company.builder().email("Babylon@baby.com").name("Babylon").password("1234").build());
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}
		BentziUtils.seperator();
		dbr.dbReport();

		BentziUtils.testingHeader("Admin adds customer kobi");

		dbr.dbReport();

		try {
			adminService.addCustomer(Customer.builder().email("Babylon@baby.com").firstName("Babylon").password("1234")
					.lastName("Shasha").build());
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}

		dbr.dbReport();

		BentziUtils.testingHeader("Customer kobi logged in");

		BentziUtils.testingHeader("One kobi purchases one coupon");

		BentziUtils.testingHeader("Each customer purchases 3 coupons");

		BentziUtils.testingHeader("Company babylon logges in");

		BentziUtils.testingHeader("Company babylon adds one coupon");

		BentziUtils.testingHeader("Company babylon adds 5 coupons ");

		BentziUtils.testingHeader("Admin deletes one customer");

		BentziUtils.testingHeader("Admin deletes one company");

	}
}
