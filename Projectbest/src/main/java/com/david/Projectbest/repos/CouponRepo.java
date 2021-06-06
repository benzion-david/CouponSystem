package com.david.Projectbest.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.david.Projectbest.beans.Coupon;

public interface CouponRepo extends JpaRepository<Coupon, Integer> {

	@Modifying
	@Transactional
	@Query(value = "delete from coupons where company_id = ?", nativeQuery = true)
	public void deleteCouponByCompanyId(int id);

	boolean existsByTitle(String title);

	public boolean existsByTitleAndCompanyId(String title, int companyId);

}
