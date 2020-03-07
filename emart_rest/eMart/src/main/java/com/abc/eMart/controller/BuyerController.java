package com.abc.eMart.controller;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abc.eMart.model.BuyerSignupPojo;
import com.abc.eMart.service.BuyerService;

@CrossOrigin
@RestController
/*
 * RequestMapping annotation is used to map web requests onto specific handler
 * classes and/or handler methods.
 */
@RequestMapping("emart")
public class BuyerController {

	static Logger Log = Logger.getLogger(BuyerController.class.getClass());

	/* @Autowired annotation injects object dependency implicitly */
	@Autowired
	BuyerService buyerService;

	/*@GetMapping annotated methods handle the HTTP GET requests matched with given URI expression.*/
	@GetMapping("validate")
	
	BuyerSignupPojo validateBuyer(@RequestHeader("Authorization") String data) {

		BasicConfigurator.configure();
		Log.info("Entered End Point\'emart/validate \' ");
		Log.info("Exited End Point\'emart/validate \' ");

		
		String token[] = data.split(":");
		BuyerSignupPojo buyerPojo = new BuyerSignupPojo();
		buyerPojo.setBuyerUsername(token[0]);
		buyerPojo.setBuyerPassword(token[1]);
		// BuyerSignupPojo validateBuyer(@RequestBody BuyerSignupPojo buyerPojo) {
		return buyerService.validateBuyer(buyerPojo);
	}

	
	@GetMapping("buyer/{buyerId}")
	
	BuyerSignupPojo getBuyer(@PathVariable("buyerId") Integer buyerId) {
		
		BasicConfigurator.configure();
		Log.info("Entered End Point\'emart/buyer/{buyerId} \' ");
		Log.info("Exited End Point\'emart/buyer/{buyerId} \' ");

		return buyerService.getBuyer(buyerId);
	}
}
