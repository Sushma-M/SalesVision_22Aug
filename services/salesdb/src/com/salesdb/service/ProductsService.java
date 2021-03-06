/*Copyright (c) 2015-2016 wavemaker-com All Rights Reserved.This software is the confidential and proprietary information of wavemaker-com You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the source code license agreement you entered into with wavemaker-com*/

package com.salesdb.service;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.wavemaker.runtime.data.exception.EntityNotFoundException;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.file.model.Downloadable;

import com.salesdb.Products;
import com.salesdb.Sales;

/**
 * Service object for domain model class Products.
 *
 * @see {@link Products}
 */
public interface ProductsService {

    /**
     * Creates a new Products.
     *
     * @param products The information of the created CompositeTable.
     * @return The created Products.
     */
	Products create(Products products);


	/**
	 * Finds Products by id.
	 *
	 * @param productsId The id of the wanted Products.
	 * @return The found Products. If no Products is found, this method returns null.
	 */
	Products getById(Integer productsId) throws EntityNotFoundException;

    /**
	 * Finds Products by unique key.
	 *
	 * @param Map of one of Unique key column fields vs values.
	 * @return The found Products. If no Products is found, this method returns null.
	 */
	Products getByUniqueKey(Map<String, Object> uniqueyKeyFieldsVsValues) throws EntityNotFoundException;

	/**
	 * Updates the information of a Products.
	 *
	 * @param products The information of the updated Products.
	 * @return The updated Products.
     *
	 * @throws EntityNotFoundException if no Products is found with given id.
	 */
	Products update(Products products) throws EntityNotFoundException;

    /**
	 * Deletes a Products.
	 *
	 * @param productsId The id of the deleted Products.
	 * @return The deleted Products.
     *
	 * @throws EntityNotFoundException if no Products is found with the given id.
	 */
	Products delete(Integer productsId) throws EntityNotFoundException;

	/**
	 * Finds all Products.
	 *
	 * @return A list of Products.
	 */
    @Deprecated
	Page<Products> findAll(QueryFilter[] queryFilters, Pageable pageable);

    /**
	 * Finds all Products.
	 * @return A list of Products.
	 */
    Page<Products> findAll(String query, Pageable pageable);

    Downloadable export(ExportType exportType, String query, Pageable pageable);

	/**
	 * Retrieve the count of the Products in the repository with matching query.
     *
     * @param query query to filter results.
	 * @return The count of the Products.
	 */
	long count(String query);

    Page<Sales> findAssociatedSaleses(Integer id, Pageable pageable);

    Page<Products> findAssociatedValues(Object value, String entityName, String key,  Pageable pageable);

}

