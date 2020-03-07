package com.abc.eMart.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abc.eMart.controller.BuyerController;
import com.abc.eMart.dao.BuyerDao;
import com.abc.eMart.dao.entity.BillDetails;
import com.abc.eMart.dao.entity.BillEntity;
import com.abc.eMart.dao.entity.BuyerSignupEntity;
import com.abc.eMart.dao.entity.CategoryEntity;
import com.abc.eMart.dao.entity.ItemEntity;
import com.abc.eMart.dao.entity.SellerSignupEntity;
import com.abc.eMart.dao.entity.SubCategoryEntity;
import com.abc.eMart.model.BillDetailsPojo;
import com.abc.eMart.model.BillPojo;
import com.abc.eMart.model.BuyerSignupPojo;
import com.abc.eMart.model.CategoryPojo;
import com.abc.eMart.model.ItemPojo;
import com.abc.eMart.model.SellerSignupPojo;
import com.abc.eMart.model.SubCategoryPojo;

@Service
public class BuyerServiceImpl implements BuyerService {

	static Logger LOG = Logger.getLogger(BuyerServiceImpl.class.getClass());

	@Autowired
	BuyerDao buyerDao;

	@Override
	/*validateBuyer() validates username and password then returns buyerPojo */
	public BuyerSignupPojo validateBuyer(BuyerSignupPojo buyerPojo) {

		LOG.info("entered validateBuyer()");

		System.out.println("username:" + buyerPojo.getBuyerUsername());
		System.out.println("password:" + buyerPojo.getBuyerPassword());
		BuyerSignupEntity buyerEntity = buyerDao.findByBuyerUsernameAndBuyerPassword(buyerPojo.getBuyerUsername(),
				buyerPojo.getBuyerPassword());
		System.out.println(buyerEntity);

		if (buyerEntity != null) {
			Set<BillEntity> allBillsEntity = buyerEntity.getAllBills();

			Set<BillPojo> allBillPojo = new HashSet<BillPojo>();

			for (BillEntity billEntity : allBillsEntity) {
				Set<BillDetails> allBillDetailsEntity = billEntity.getAllBillDetails();
				Set<BillDetailsPojo> allBillDetailsPojo = new HashSet<BillDetailsPojo>();

				for (BillDetails billDetailsEntity : allBillDetailsEntity) {

					ItemEntity itemEntity = billDetailsEntity.getItem();
					SubCategoryEntity subCategoryEntity = itemEntity.getSubcategory();
					CategoryEntity categoryEntity = subCategoryEntity.getCategory();
					SellerSignupEntity sellerEntity = itemEntity.getSellersignup();

					SellerSignupPojo sellerPojo = new SellerSignupPojo(sellerEntity.getSellerId(),
							sellerEntity.getSellerUsername(), sellerEntity.getSellerPassword(),
							sellerEntity.getSellerCompany(), sellerEntity.getSellerBrief(), sellerEntity.getSellerGst(),
							sellerEntity.getSellerAddress(), sellerEntity.getSellerEmail(),
							sellerEntity.getSellerContact(), sellerEntity.getSellerWebsite());
					
					
					CategoryPojo categoryPojo = new CategoryPojo(categoryEntity.getCategoryId(),
							categoryEntity.getCategoryName(), categoryEntity.getCategoryBrief());
					
					
					SubCategoryPojo subCategoryPojo = new SubCategoryPojo(subCategoryEntity.getSubCategoryId(),
							subCategoryEntity.getSubCategoryName(), subCategoryEntity.getSubCategoryBrief(),
							subCategoryEntity.getSubCategoryGst(), categoryPojo);
					
					
					ItemPojo itemPojo = new ItemPojo(itemEntity.getItemId(), itemEntity.getItemName(),
							itemEntity.getItemImage(), itemEntity.getItemPrice(), itemEntity.getItemStock(),
							itemEntity.getItemDescription(), itemEntity.getItemRemarks(), subCategoryPojo, sellerPojo);
					

					BillDetailsPojo billDetailsPojo = new BillDetailsPojo(billDetailsEntity.getBillDetailsId(), null,
							itemPojo);
					allBillDetailsPojo.add(billDetailsPojo);
				}
				BillPojo billPojo = new BillPojo(

						billEntity.getBillId(), billEntity.getBillType(), billEntity.getBillDate(),
						billEntity.getBillRemarks(), billEntity.getBillAmount(), null, allBillDetailsPojo);
				allBillPojo.add(billPojo);
			}
			buyerPojo = new BuyerSignupPojo(buyerEntity.getBuyerId(), buyerEntity.getBuyerUsername(),
					buyerEntity.getBuyerPassword(), buyerEntity.getBuyerEmail(), buyerEntity.getBuyerMobile(),
					buyerEntity.getBuyerDate(), allBillPojo);
		}
		LOG.info("exited validateBuyer()");
		return buyerPojo;
	}
	
	

	@Override
	/*getBuyer() stores entity into pojo and gets buyer details */
	public BuyerSignupPojo getBuyer(Integer buyerId) {

		LOG.info("entered getBuyer()");

		BuyerSignupPojo buyerSignupPojo = new BuyerSignupPojo();
		Optional<BuyerSignupEntity> optional = buyerDao.findById(buyerId);
		System.out.println(optional);

		if (optional.isPresent()) {
			BuyerSignupEntity buyerSignupEntity = optional.get();
			Set<BillEntity> allBillsEntity = buyerSignupEntity.getAllBills();

			Set<BillPojo> allBillPojo = new HashSet<BillPojo>();

			for (BillEntity billEntity : allBillsEntity) {
				Set<BillDetails> allBillDetailsEntity = billEntity.getAllBillDetails();
				Set<BillDetailsPojo> allBillDetailsPojo = new HashSet<BillDetailsPojo>();

				for (BillDetails billDetailsEntity : allBillDetailsEntity) {

					ItemEntity itemEntity = billDetailsEntity.getItem();
					SubCategoryEntity subCategoryEntity = itemEntity.getSubcategory();
					CategoryEntity categoryEntity = subCategoryEntity.getCategory();
					SellerSignupEntity sellerEntity = itemEntity.getSellersignup();

					SellerSignupPojo sellerPojo = new SellerSignupPojo(sellerEntity.getSellerId(),
							sellerEntity.getSellerUsername(), sellerEntity.getSellerPassword(),
							sellerEntity.getSellerCompany(), sellerEntity.getSellerBrief(), sellerEntity.getSellerGst(),
							sellerEntity.getSellerAddress(), sellerEntity.getSellerEmail(),
							sellerEntity.getSellerContact(), sellerEntity.getSellerWebsite());
					
					
					CategoryPojo categoryPojo = new CategoryPojo(categoryEntity.getCategoryId(),
							categoryEntity.getCategoryName(), categoryEntity.getCategoryBrief());

					SubCategoryPojo subCategoryPojo = new SubCategoryPojo(subCategoryEntity.getSubCategoryId(),
							subCategoryEntity.getSubCategoryName(), subCategoryEntity.getSubCategoryBrief(),
							subCategoryEntity.getSubCategoryGst(), categoryPojo);

					ItemPojo itemPojo = new ItemPojo(itemEntity.getItemId(), itemEntity.getItemName(),
							itemEntity.getItemImage(), itemEntity.getItemPrice(), itemEntity.getItemStock(),
							itemEntity.getItemDescription(), itemEntity.getItemRemarks(), subCategoryPojo, sellerPojo);
					
					
					BillDetailsPojo billDetailsPojo = new BillDetailsPojo(billDetailsEntity.getBillDetailsId(), null,
							itemPojo);
					allBillDetailsPojo.add(billDetailsPojo);
				}
				BillPojo billPojo = new BillPojo(

						billEntity.getBillId(), billEntity.getBillType(), billEntity.getBillDate(),
						billEntity.getBillRemarks(), billEntity.getBillAmount(), null, allBillDetailsPojo);
				allBillPojo.add(billPojo);
			}
			buyerSignupPojo = new BuyerSignupPojo(buyerSignupEntity.getBuyerId(), buyerSignupEntity.getBuyerUsername(),
					buyerSignupEntity.getBuyerPassword(), buyerSignupEntity.getBuyerEmail(),
					buyerSignupEntity.getBuyerMobile(), buyerSignupEntity.getBuyerDate(), allBillPojo);
		}
		LOG.info("exited getBuyer()");
		return buyerSignupPojo;
	}
}
