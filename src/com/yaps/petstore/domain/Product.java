/*
 * Created on 5 nov. 2005
 *
 * Product.java
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
public final class Product implements Serializable {
	
	////////////////////PRIVATE ATTRIBUTES///////////////////

	private String _productId;
	private String _name;
	private String _description;
	private Category _category;
	
	private static final String PERSISTENT_NAME_LAYER_PRODUCT ="persistentProduct.ser";
	private final transient HashmapAccessor _dao = new HashmapAccessor();
	
	///////////////////CONSTRUCTORS/////////////////
	/**
	 * @param productId
	 */
	public Product(final String productId) {
		
		_productId = productId;
	}

	/**
	 * @param productId
	 * @param name
	 * @param description
	 * @param category
	 */
	public Product(final String productId, final String name, final String description, Category category) {
		
		_productId = productId;
		_name = name;
		_description = description;
		_category = category;
	}

	/**
	 * 
	 */
	public Product() {
		
		
	}
	////////////////////PRIVATE METHODS////////////////////
	/**
	    * This method checks that the identifier is valid.
	    *
	    * @param id identifier to check
	    * @throws CheckException if the id is invalid
	    */
	
 private static void checkId(final String id)throws CheckException {
	if(id == null || "".equals(id))
		throw new CheckException("Invalid product id");
	
}
 /**
  * This method checks the integrity of the object data.
  *
  * @throws CheckException if data is invalide
  */
private  void checkData() throws CheckException {
	checkId(_productId);
	if (_name == null || "".equals(_name))
		throw new CheckException("Invalid product name");
	if(_description == null ||"".equals(_description))
		throw new CheckException("Invalid product description");
	if(_category == null)
		throw new CheckException("Invalid product category");
	if(_category.getId() == null || "".equals(_category.getId()))	
		throw new CheckException("Invalid product category id");
	
}
//////////////////PUBLIC METHODS//////////////////
	/**
	 * remove data in hashmap
	 */
	public void remove() throws RemoveException, CheckException {
		// check data integrity
		checkId(_productId);
		try {
			_dao.remove(_productId,PERSISTENT_NAME_LAYER_PRODUCT);
			
		} catch(ObjectNotFoundException e)
		{
			throw new RemoveException("Cannot remove the product. Product not found");
		}
		
	}

	/**
	 * create data and put in hashmap
	 */
	public void create() throws CreateException, CheckException{
		// check data integrity
		checkData();
		
		_dao.insert(this,_productId, PERSISTENT_NAME_LAYER_PRODUCT);
		
	}

	/**
	 * find in hashmap
	 * @param productId
	 */
	public void find(final String productId)throws FinderException, CheckException {
		// check data integrity
		
		checkId(productId);
		
		Product temp = (Product)_dao.select(productId,PERSISTENT_NAME_LAYER_PRODUCT);
		_productId = temp._productId;
		_name = temp._name;
		_description =temp._description;
		_category = temp._category;
		
	}
	/**
	 * update data in hashmap
	 */
	public void update()throws UpdateException, CheckException {
		// Check data integrity
		
		checkData();
		
		try {
			_dao.update(this, _productId, PERSISTENT_NAME_LAYER_PRODUCT);
		} catch (ObjectNotFoundException e)
		{
			throw new UpdateException("Cannot update product.Product not found");
		} catch(DuplicateKeyException e)		
		{
			throw new UpdateException("Cannot update product.Product already exists");
		}
		
	}
	/**
	 * find data in hashmap
	 * @return a colection
	 */
	public Collection findAll() throws ObjectNotFoundException {
		
		return _dao.selectAll(PERSISTENT_NAME_LAYER_PRODUCT);
	}
	/**
	 * Accessor set
	 * @param name
	 */
	public void setName(final String name) {
		_name = name;
		
	}
	/**
	 * Accesor get
	 * @return _name
	 */
	public String getName() {
		
		return _name;
	}
	/**
	 * Accessor set
	 * @param description
	 */
	public void setDescription(final String description) {
		_description = description;
		
	}

	/**
	 * Accessor get
	 * @return _description
	 */
	public String getDescription() {
		
		return _description;
	}

	/**
	 * Accessor set
	 * @param category
	 */
	public void setCategory(final Category category) {
		_category = category;
		
	}
	/**
	 * Accesor get
	 * @return
	 */
	public Category getCategory() {
		
		return _category;
	}
	/**
	 * Accessor set
	 * @param id
	 */
	public void setId(final String id){
		_productId = id;
	}

	/**
	 * Accessor get
	 * @return id
	 */
		
public String getId() {
	return _productId;
}
	

}
