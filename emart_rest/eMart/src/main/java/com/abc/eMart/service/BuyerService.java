package com.abc.eMart.service;

import com.abc.eMart.model.BuyerSignupPojo;

public interface BuyerService {
	BuyerSignupPojo validateBuyer(BuyerSignupPojo buyerpojo);
	BuyerSignupPojo getBuyer(Integer buyerId);
}
