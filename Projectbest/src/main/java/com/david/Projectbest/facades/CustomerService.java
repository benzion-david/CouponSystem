package com.david.Projectbest.facades;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.david.Projectbest.beans.Category;
import com.david.Projectbest.beans.Coupon;
import com.david.Projectbest.beans.Customer;
import com.david.Projectbest.exceptions.CouponSystemException;
import com.david.Projectbest.facades.service.ClientService;
import com.david.Projectbest.repos.CompanyRepo;
import com.david.Projectbest.repos.CouponRepo;
import com.david.Projectbest.repos.CustomerRepo;

@Component
@Service
public class CustomerService extends ClientService {

	@Autowired
	CompanyRepo companyRepo;

	@Autowired
	CustomerRepo customerRepo;

	@Autowired
	CouponRepo couponRepo;

	private int customerid;

	@Override
	public boolean clientLogin(String email, String password) throws CouponSystemException {
		Customer customer = customerRepo.findByEmailAndPassword(email, password)//
				.orElseThrow(() -> new CouponSystemException("Sorry incorrect login details"));

		this.customerid = customer.getId();

		System.out.println("Logged in as" + customer.getFirstName() + "; With id : " + customer.getId());

		return true;
	}

	public void purchaseCoupon(Coupon coupon) {

	}

	public List<Coupon> getCustomerCoupons() {
		return null;
	}

	public List<Coupon> getCustomerCoupons(Category category) {
		return null;
	}

	public List<Coupon> getCompanyCoupons(double maxPrice) {
		return null;
	}

	public Customer getCustomerDetails() {
		return null;
	}

}
