package com.david.Projectbest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ProjectbestApplication {

	public static int numCompanies = 20;
	public static int numCustomers = 40;
	public static int numCoupons = 60;

	public static final int day = 1000 * 60 * 60 * 24;

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(ProjectbestApplication.class, args);

	}

}
