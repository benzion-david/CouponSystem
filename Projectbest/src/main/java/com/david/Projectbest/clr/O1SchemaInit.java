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
import com.david.Projectbest.beans.Customer;
import com.david.Projectbest.dbUtils.DatabaseReport;
import com.david.Projectbest.repos.CompanyRepo;
import com.david.Projectbest.repos.CouponRepo;
import com.david.Projectbest.repos.CustomerRepo;
import com.david.Projectbest.utils.BentziUtils;

@Component
@Order(1)
public class O1SchemaInit implements CommandLineRunner {

	@Autowired
	DatabaseReport dbr;

	@Autowired
	CompanyRepo companyRepo;

	@Autowired
	CouponRepo couponRepo;

	@Autowired
	CustomerRepo customerRepo;

	@Override
	public void run(String... args) throws Exception {

		BentziUtils.testingHeader("Add companies with CompanyRepo");

		List<Company> companies = new ArrayList<Company>();

		for (int i = 0; i < ProjectbestApplication.numCompanies; i++) {
			Company company = new Company(BentziUtils.randomCompany(), "",

					BentziUtils.generateRandPass());

			company.setEmail("info@" + company.getName().toLowerCase() + ".com");

			if (!companyRepo.existsByEmail(company.getEmail())) {
				companies.add(company);
			}

			// companyRepo.save(company);

		}

		companyRepo.saveAll(companies);

		List<Company> allCompanies = companyRepo.findAll();

		List<Integer> allCompaniesIds = new ArrayList<Integer>();

		allCompanies.stream().forEach(c -> allCompaniesIds.add(c.getId()));

		System.out.println("There are now " + allCompanies.size() + " Companies in the db");

		BentziUtils.testingHeader("Add coupons with CouponRepo");

		List<Coupon> coupons = new ArrayList<Coupon>();

		for (int i = 0; i < ProjectbestApplication.numCoupons; i++) {

			int validCompanyId = allCompaniesIds.get((int) (Math.random() * allCompaniesIds.size()));

			int randomCategory = (int) (Math.random() * Category.values().length);
			Category category = Category.values()[randomCategory];

			String title = BentziUtils.generateCouponTitle();

			String description = "";

			Date startDate = new Date(
					(new Date().getTime() - ProjectbestApplication.day * BentziUtils.randIntBetween(1, 3)));
			Date endDate = new Date(
					(new Date().getTime() + ProjectbestApplication.day * BentziUtils.randIntBetween(-2, 2)));

			int amount = 50;

			double price = BentziUtils.randomNumFromList(1.1, 2.0, 2.5, 3.5);

			String image = "picture" + BentziUtils.randIntBetween(11, 50) + ".jpg";

			Coupon coupon = Coupon.builder().companyId(validCompanyId).category(category) //
					.title(title).description(description).startDate(startDate).endDate(endDate) //
					.amount(amount).price(price).image(image).build();

			coupons.add(coupon);

		}

		couponRepo.saveAll(coupons);

		dbr.dbReport();
		dbr.fullPrintdbReport();

		BentziUtils.testingHeader("Add customers with customerRepo");

		List<Customer> customers = new ArrayList<Customer>();

		for (int i = 0; i < ProjectbestApplication.numCustomers; i++) {

			Customer customer = Customer.builder().firstName(BentziUtils.generateName())
					.lastName(BentziUtils.generateABCletter()).password("1234").build();

			customer.setEmail(customer.getFirstName() + customer.getLastName() + "@Gmail.com");

			customers.add(customer);

		}

		customerRepo.saveAll(customers);

		dbr.fullPrintdbReport();

	}

}
