package com.david.Projectbest.loginSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.david.Projectbest.exceptions.CouponSystemException;
import com.david.Projectbest.facades.AdminService;
import com.david.Projectbest.facades.CompanyService;
import com.david.Projectbest.facades.CustomerService;
import com.david.Projectbest.facades.service.ClientService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LoginManager {

	private final ApplicationContext ctx;
	private final AdminService adminService; // only 1 admin
	@Autowired
	private CompanyService companyService; // ...many companies
	@Autowired
	private CustomerService customerService; // ...many customers

	//
	//

	// L55 14:57
	// Factory method
	// מקבל טייפים, מחזיר אובייקט מאותחל
	public ClientService login(String email, String password, ClientType clientType) throws CouponSystemException {

		switch (clientType) {
		case ADMIN:
			if (((ClientService) adminService).clientLogin(email, password)) {
				System.out.println("Successful Admin login");
				return (ClientService) adminService;

			}
			break;

		case COMPANY:
			companyService = ctx.getBean(CompanyService.class);
			if (((ClientService) companyService).clientLogin(email, password)) {
				System.out.println("Successful Company login");
				// return (ClientService) companyService;
				return (ClientService) companyService;
			}
			break;

		case CUSTOMER:
			customerService = ctx.getBean(CustomerService.class);
			if (((ClientService) customerService).clientLogin(email, password)) {
				System.out.println("Successful Customer login");
				return (ClientService) customerService;
			}
			break;

		default:
			break;
		// I disagreed with this...
		// throw new CouponSystemException("Unauthorized. Sorry
		// invalid email and/or password");
		}
		throw new CouponSystemException("Unauthorised.  Sorry, invalid email and/or password");
		// KS did "return null;" here.
	}
}
