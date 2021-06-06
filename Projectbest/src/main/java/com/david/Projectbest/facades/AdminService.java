package com.david.Projectbest.facades;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.david.Projectbest.beans.Company;
import com.david.Projectbest.beans.Customer;
import com.david.Projectbest.exceptions.CouponSystemException;
import com.david.Projectbest.facades.service.ClientService;
import com.david.Projectbest.repos.CompanyRepo;
import com.david.Projectbest.repos.CouponRepo;
import com.david.Projectbest.repos.CustomerRepo;

@Service
@Component
public class AdminService extends ClientService {

	@Autowired
	CompanyRepo companyRepo;

	@Autowired
	CustomerRepo customerRepo;

	@Autowired
	CouponRepo couponRepo;

	@Override
	public boolean clientLogin(String email, String password) throws CouponSystemException {

		if (!email.equals("admin@admin.com") && password.equals("admin")) {

			throw new CouponSystemException("Sorry incorrect login details");

		}

		System.out.println("Admin logged in successfully");
		return true;
	}

	public void addCompany(Company company) throws CouponSystemException {

		boolean k = companyRepo.existsByName(company.getName()) || companyRepo.existsByEmail(company.getEmail());

		if (k) {
			throw new CouponSystemException("Sorry this company name already exists");
		}

		companyRepo.save(company);

	}

	public void updateCompany(Company company) throws CouponSystemException {

		if (!companyRepo.existsByIdAndName(company.getId(), company.getName())) {
			throw new CouponSystemException("Sorry this company does not exist");
		}

		companyRepo.saveAndFlush(company);

	}

	public void deleteCompany(int id) {

		try {
			if (!companyRepo.existsById(id)) {
				throw new CouponSystemException("Sorry this id does not exist");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		couponRepo.deleteCouponByCompanyId(id);
		companyRepo.deleteById(id);

		// TODO ---- DELETE COMPANY COUPON PURCHASE

	}

	public List<Company> getAllCompanies() {

		return companyRepo.findAll();

	}

	public Company getOneCompany(int id) throws CouponSystemException {

		if (!companyRepo.existsById(id)) {
			throw new CouponSystemException("Sorry this id does not exist");
		}

		return companyRepo.getOne(id);
	}

	public void addCustomer(Customer customer) throws CouponSystemException {

		if (customerRepo.existsByEmail(customer.getEmail())) {
			throw new CouponSystemException("Sorry this email already exists");

		}

		customerRepo.save(customer);

	}

	public void updateCustomer() {

		// TODO
	}

	public void deleteCustomer(int id) throws CouponSystemException {

		if (!customerRepo.existsById(id)) {
			throw new CouponSystemException("Sorry this id does not exist");
		}

		customerRepo.deleteById(id);

		// TODO --- DELETE CUSTOMER PURCHASES

	}

	public List<Customer> getAllCustomers() {

		return customerRepo.findAll();
	}

	public Customer getSingleCustomer(int id) throws CouponSystemException {
		if (!customerRepo.existsById(id)) {
			throw new CouponSystemException("Sorry customer with this id does not exist " + id);
		}

		return customerRepo.getOne(id);
	}

}
