/*
 * Created on 5 nov. 2005
 *
 * Category.java
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.yaps.petstore.domain;

import com.yaps.petstore.exception.*;
import com.yaps.petstore.persistence.HashmapAccessor;

import java.util.Collection;
import java.io.Serializable;

/**
 * @author Veronique
 *
 * 
 */
public final class Category implements Serializable {

	////////////////////PRIVATE ATTRIBUTES////////////////////
	
	private String _categoryId;
	private String _nameId;
	private String _description;
	
	private static final String PERSISTENT_LAYER_NAME_CATEGORY = "persistentCategory.ser";
    private final transient HashmapAccessor _dao = new HashmapAccessor();
    
    ////////////////////CONSTRUCTOR////////////////////       

	/**
	 * @param categoryId
	 * @param nameId
	 * @param description
	 */
	public Category(final String categoryId, final String nameId, final String description) {
		
		_categoryId = categoryId;
		_nameId = nameId;
		_description = description;
	}

	/**
	 * @param categoryId
	 */
	public Category(final String categoryId) {
		
		_categoryId = categoryId;
	}

	/**
	 * 
	 */
	public Category() {
		
		
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
            throw new CheckException("Invalid category id");
    }

	/**
     * This method checks the integrity of the object data.
     *
     * @throws CheckException if data is invalide
     */
    private void checkData() throws CheckException {
        checkId(_categoryId);
        if (_nameId == null || "".equals(_nameId))
            throw new CheckException("Invalid name");
        if (_description == null || "".equals(_description))
            throw new CheckException("Invalid description");
    }
////////////////////PUBLIC METHODS///////////////////
    /**
	 * find in hashmap
	 * @param categoryId
	 */
	public void find(final String categoryId) throws FinderException, CheckException {
		// Check data integrity
		checkId(categoryId);
		
		//Use DAO to access persistent layer
		final Category temp = (Category) _dao.select(categoryId,PERSISTENT_LAYER_NAME_CATEGORY);
		
		_categoryId = temp.getId();
		_nameId = temp.getName();
		_description = temp.getDescription();
	}
    
	/**
	 * update hashmap
	 */
	public void update() throws UpdateException, CheckException {
		// check data
		checkData();
		
		try {
			_dao.update(this,_categoryId,PERSISTENT_LAYER_NAME_CATEGORY);
			
		} catch(ObjectNotFoundException e) {
			throw new UpdateException("Cannot Update category. Category not found");			
		} catch (DuplicateKeyException e) {
			throw new UpdateException("Canno Update category. Category already exists");
		}
		
	}
	
	/**
	 * remove in hashmap
	 */
	public void remove() throws RemoveException, CheckException {
		// Check data integrity
		checkId(_categoryId);
		
		try {
			_dao.remove(_categoryId,PERSISTENT_LAYER_NAME_CATEGORY);
		} catch(ObjectNotFoundException e) {
			throw new RemoveException("Cannot remove category. Category not found");
		}
		
	}
	/**
	 * create and put in hashmap
	 */
	public void create() throws CreateException, CheckException {
		// Check data integrity
		checkData();
		// Use the DAO to access the persistent layer
		_dao.insert(this, _categoryId, PERSISTENT_LAYER_NAME_CATEGORY);
	}
	/**
	 * return hasmap content
	 * @return a collection of data
	 */
	public Collection findAll() throws ObjectNotFoundException {		
		return _dao.selectAll(PERSISTENT_LAYER_NAME_CATEGORY);
				
	}
	/**
	 * Accessor set
	 * @param nameId
	 */
	public void setName(final String nameId) {
		_nameId = nameId;		
	}
	/**
	 * Accessor get
	 * @return nameId
	 */
	public String getName() {
		
		return _nameId;
	}

	/**
	 * Accesssor set
	 * @param descriptionId
	 */
	public void setDescription(final String descriptionId) {
		_description = descriptionId;
		
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
	 * @param categoryId
	 */
	public void setId(final String categoryId) {
		_categoryId = categoryId;
		
	}
	/**
	 * Accessor get
	 * @return _categoryId
	 */
	public String getId() {
		return _categoryId;
	}
		
}
