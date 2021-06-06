package com.david.Projectbest.clr;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.david.Projectbest.ProjectbestApplication;
import com.david.Projectbest.beans.Category;
import com.david.Projectbest.beans.Company;
import com.david.Projectbest.beans.Coupon;
import com.david.Projectbest.dbUtils.DatabaseReport;
import com.david.Projectbest.exceptions.CouponSystemException;
import com.david.Projectbest.facades.AdminService;
import com.david.Projectbest.facades.CompanyService;
import com.david.Projectbest.loginSecurity.ClientType;
import com.david.Projectbest.loginSecurity.LoginManager;
import com.david.Projectbest.repos.CompanyRepo;
import com.david.Projectbest.repos.CouponRepo;
import com.david.Projectbest.repos.CustomerRepo;
import com.david.Projectbest.utils.BentziUtils;

@Component
@Order(1)
public class O2SchemaInit2 implements CommandLineRunner {

	@Autowired
	DatabaseReport dbr;

	@Autowired
	CompanyRepo companyRepo;

	@Autowired
	CouponRepo couponRepo;

	@Autowired
	CustomerRepo customerRepo;

	@Autowired
	AdminService adminFacade;

	@Autowired
	LoginManager loginManager;

	@Override
	public void run(String... args) {

		BentziUtils.testingHeader("Admin log in ");

		AdminService adminService = null;

		try {
			adminService = (AdminService) loginManager.login("admin@admin.com", "admin", ClientType.ADMIN);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		BentziUtils.testingHeader("Admin deletes one company");// 5

		System.out.println("Before deletion : ");

		dbr.dbReport();

		adminFacade.deleteCompany(1);

		BentziUtils.seperator();

		dbr.dbReport();

		System.out.println("After deletion");

		BentziUtils.testingHeader("Admin deletes 5 companies");

		for (int i = 2; i < 7; i++) {
			adminFacade.deleteCompany(i);
		}

		dbr.dbReport();

		List<Integer> companyIds = new ArrayList<>();
		companyRepo.findAll().stream().forEach(c -> companyIds.add(c.getId()));

		BentziUtils.testingHeader("Company with Id=8 logged in");

		int id = 8;

//		for (int id : companyIds) {

		Company company = companyRepo.findById(id).get();

		CompanyService companyService = null;
		try {
			companyService = (CompanyService) loginManager.login(company.getEmail(), company.getPassword(),
					ClientType.COMPANY);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		BentziUtils.testingHeader("Company gets company details");

		System.out.println(companyService.getCompanyDetails());

		BentziUtils.testingHeader("Company with Id=8 tries to add a  coupon");

		Date startDate = new Date(
				(new Date().getTime() - ProjectbestApplication.day * BentziUtils.randIntBetween(1, 3)));
		Date endDate = new Date(
				(new Date().getTime() + ProjectbestApplication.day * BentziUtils.randIntBetween(-1, 3)));

		int randomCategory = (int) (Math.random() * Category.values().length);

		Coupon coupon = Coupon.builder().category(Category.values()[randomCategory])
				.title(BentziUtils.generateCouponTitle()).startDate(startDate).endDate(endDate).amount(50)
				.price(BentziUtils.randBetween(10, 50)).companyId(id).build();

		System.out.println("Before add coupon");

		dbr.dbReport();

		System.out.println("----------");

		try {
			companyService.addCoupon(coupon);
		} catch (CouponSystemException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}

		System.out.println("After add coupon");

		dbr.dbReport();

		// }----------------------------------------

		BentziUtils.testingHeader("Company 8 tries to  add 5 coupons");

		for (int i = 0; i < 5; i++) {

			startDate = new Date(
					(new Date().getTime() - ProjectbestApplication.day * BentziUtils.randIntBetween(1, 3)));
			endDate = new Date((new Date().getTime() + ProjectbestApplication.day * BentziUtils.randIntBetween(-1, 3)));

			randomCategory = (int) (Math.random() * Category.values().length);

			coupon = Coupon.builder().category(Category.values()[randomCategory])
					.title(BentziUtils.generateCouponTitle()).startDate(startDate).endDate(endDate).amount(50)
					.price(BentziUtils.randBetween(10, 50)).companyId(id).build();

			try {
				companyService.addCoupon(coupon);
			} catch (CouponSystemException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());

			}

		}

		dbr.dbReport();

		BentziUtils.testingHeader("Every company tries to add 5 coupons ");

		List<Company> allCompanies = companyRepo.findAll();
		List<Integer> allCompaniesIds = new ArrayList<Integer>();
		allCompanies.stream().forEach(c -> allCompaniesIds.add(c.getId()));

		int count = 0;

		for (int compId : allCompaniesIds) {

			for (int i = 0; i < 5; i++) {

				startDate = new Date(
						(new Date().getTime() - ProjectbestApplication.day * BentziUtils.randIntBetween(1, 3)));
				endDate = new Date(
						(new Date().getTime() + ProjectbestApplication.day * BentziUtils.randIntBetween(-1, 3)));

				randomCategory = (int) (Math.random() * Category.values().length);

				coupon = Coupon.builder().category(Category.values()[randomCategory])
						.title(BentziUtils.generateCouponTitle()).startDate(startDate).endDate(endDate).amount(50)
						.price(BentziUtils.randBetween(10, 50)).companyId(compId).build();

				try {
					companyService.addCoupon(coupon);
				} catch (CouponSystemException e) {
					count++;

					// System.out.println(e.getMessage());

				}

			}
		}

		System.out.println("Number of exceptions : " + count);

		dbr.dbReport();

	}
}
