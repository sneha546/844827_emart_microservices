package com.abc.eMart.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.abc.eMart.dao.entity.BuyerSignupEntity;

/*@Repository is a mechanism for encapsulating storage and retrieval*/
@Repository
public interface BuyerDao extends JpaRepository<BuyerSignupEntity, Integer> {
	BuyerSignupEntity findByBuyerUsernameAndBuyerPassword(String username, String password);
}
