/*
 * Created on 5 nov. 2005
 *
 * Item JAVA file
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.yaps.petstore.domain;

import java.io.Serializable;
import java.util.Collection;

import com.yaps.petstore.exception.*;
import com.yaps.petstore.persistence.HashmapAccessor;

/**
 * @author Veronique
 *
 * 
 */
public final class Item implements Serializable {
	////////////////////PRIVATE ATTRIBUTES///////////////////
	private String _itemid;
	private String _name;
	private Product _product;
	private double _unitCost;
	
	private static final String PERSISTENT_LAYER_NAME_ITEM = "persistentItem.ser";
	private final transient HashmapAccessor _dao = new HashmapAccessor();
	
	///////////////////CONSTRUCTORS////////////////

	/**
	 * @param itemid
	 */
	public Item(String itemid) {
		
		_itemid = itemid;
	}

	/**
	 * 
	 */
	public Item() {
		
		
	}
		

	/**
	 * @param itemid
	 * @param name
	 * @param unitCost
	 * @param product
	 */
	public Item(String itemid, String name, double unitCost, Product product) {
		
		_itemid = itemid;
		_name = name;
		_unitCost = unitCost;
		_product = product;
	}
	////////////////////PRIVATE METHODS//////////////////
	/**
	    * This method checks that the identifier is valid.
	    *
	    * @param id identifier to check
	    * @throws CheckException if the id is invalid
	    */
	
	private static void checkId(final String id) throws CheckException {
		if (id == null || "".equals(id))
			throw new CheckException("Invalid item id");
	}
	/**
     * This method checks the integrity of the object data.
     *
     * @throws CheckException if data is invalide
     */
	private void checkData() throws CheckException {
		checkId(_itemid);
		if (_name == null || "".equals(_name))
			throw new CheckException("Invalid item name");
		if ( _unitCost == 0)
			throw new CheckException("Invalid item unit cost");
		if (_product == null || _product.getId() == null || "".equals(_product.getId()))
			throw new CheckException("Invalid item product"); 	
	}
	////////////////////PUBLI METHODS///////////////////
	/**
	 * remove data in hashmap
	 */
	public void remove() throws RemoveException, CheckException {
		// check integrity
		checkId(_itemid);
		try {
			_dao.remove(_itemid, PERSISTENT_LAYER_NAME_ITEM);
		} catch (ObjectNotFoundException e) {
			throw new RemoveException("Cannot remove item. Item not found");
		}
		
	}

	/**
	 * find in hashmap
	 * @param itemid
	 */
	public void find(final String itemid) throws FinderException, CheckException {
		
		// Check intergrity
		checkId(itemid);
		
			final Item temp = (Item)_dao.select(itemid,PERSISTENT_LAYER_NAME_ITEM);
			
		   //Set data to current object
			_itemid = temp.getId();
			_name = temp.getName();
			_unitCost = temp.getUnitCost();
			_product = temp.getProduct();
		
	}
	/**
	 * update hasmap
	 */
	public void update() throws  UpdateException , CheckException {
		// Check integrity
		checkData();
		try {
			_dao.update(this, _itemid, PERSISTENT_LAYER_NAME_ITEM);
			
		} catch (ObjectNotFoundException e)
		{
			throw new UpdateException("Cannot update ietm. Item not found");
		} catch (DuplicateKeyException e)
		{
			throw new UpdateException("Cnnot update item. Item already exists");
		}
						
	}
	/**
	 * create data and put in hashmap
	 */
	public void create() throws CreateException, CheckException {
		// check integrity
		checkData();				
		_dao.insert(this,_itemid,PERSISTENT_LAYER_NAME_ITEM);				
	}

	/**
	 * find all data in hashmap
	 * @return a colection
	 */
	public Collection findAll() throws ObjectNotFoundException {
		
		return _dao.selectAll(PERSISTENT_LAYER_NAME_ITEM);
	}
	/**
	 * Accessor set
	 * @param product
	 */
	public void setProduct(Product product) {
		_product = product;
		
	}
	/**
	 * Accessor get
	 * @return _product
	 */
	public Product getProduct() {
		
		return _product;
	}
	/**
	 * Accessor set
	 * @param unitCost
	 */
	public void setUnitCost(final double unitCost) {
		_unitCost = unitCost;		
	}
	/**
	 * Accessor get
	 * @return _unitCost
	 */
	public double getUnitCost() {		
		return _unitCost;
	}
	/**
	 * Accessor set
	 * @param name
	 */
	public void setName(final String name) {
		_name = name;		
	}

	/**
	 * Accessor get
	 * @return _name
	 */
	public String getName() {		
		return _name;
	}
	/**
	 * Accessor set
	 * @param id
	 */
	public void setid(String id){
		_itemid = id;
	}
	/**
	 * Accessor get
	 * @return _itemid
	 */
public String getId()
{
	return _itemid;
}
		

}
