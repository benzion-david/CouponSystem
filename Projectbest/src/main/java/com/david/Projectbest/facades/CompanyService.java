package com.david.Projectbest.facades;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.david.Projectbest.beans.Category;
import com.david.Projectbest.beans.Company;
import com.david.Projectbest.beans.Coupon;
import com.david.Projectbest.exceptions.CouponSystemException;
import com.david.Projectbest.facades.service.ClientService;
import com.david.Projectbest.repos.CompanyRepo;
import com.david.Projectbest.repos.CouponRepo;
import com.david.Projectbest.repos.CustomerRepo;

@Component
@Service
public class CompanyService extends ClientService {

	@Autowired
	CompanyRepo companyRepo;

	@Autowired
	CustomerRepo customerRepo;

	@Autowired
	CouponRepo couponRepo;

	private int companyId;

	@Override
	public boolean clientLogin(String email, String password) throws CouponSystemException {
		Company company = companyRepo.findByEmailAndPassword(email, password)//
				.orElseThrow(() -> new CouponSystemException("Sorry incorrect login details"));

		this.companyId = company.getId();

		System.out.println("Company logged in as " + company.getName() + ";  With id :  " + company.getId());

		return true;
	}

	public void addCoupon(Coupon coupon) throws CouponSystemException {

		if (couponRepo.existsByTitleAndCompanyId(coupon.getTitle(), coupon.getCompanyId())) {
			throw new CouponSystemException("Sorry , coupon already exists with same title and company");
		}

		couponRepo.save(coupon);

	}

	public void updateCoupon(Coupon coupon) {

		// TODO

	}

	public void deleteCoupon(int id) {

	}

	public List<Coupon> getCompanyCoupons() {

		return null;
	}

	public List<Coupon> getCompanyCoupons(Category category) {

		return null;
	}

	public List<Coupon> getCompanyCoupons(double price) {
		return null;
	}

	public Company getCompanyDetails() {
		return companyRepo.findById(this.companyId).get();
	}

}
