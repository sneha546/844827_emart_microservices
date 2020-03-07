package com.abc.eMart.service;

import java.util.List;

import com.abc.eMart.model.ItemPojo;


public interface ItemService {
	
	List<ItemPojo> getAllItems();
	ItemPojo getItem(Integer itemId);
	void deleteItems(Integer itemId);
	ItemPojo addItems(ItemPojo itemPojo);
	

}
