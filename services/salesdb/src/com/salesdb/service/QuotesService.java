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

import com.salesdb.FollowUps;
import com.salesdb.Quotes;
import com.salesdb.Sales;

/**
 * Service object for domain model class Quotes.
 *
 * @see {@link Quotes}
 */
public interface QuotesService {

    /**
     * Creates a new Quotes.
     *
     * @param quotes The information of the created CompositeTable.
     * @return The created Quotes.
     */
	Quotes create(Quotes quotes);


	/**
	 * Finds Quotes by id.
	 *
	 * @param quotesId The id of the wanted Quotes.
	 * @return The found Quotes. If no Quotes is found, this method returns null.
	 */
	Quotes getById(Integer quotesId) throws EntityNotFoundException;

    /**
	 * Finds Quotes by unique key.
	 *
	 * @param Map of one of Unique key column fields vs values.
	 * @return The found Quotes. If no Quotes is found, this method returns null.
	 */
	Quotes getByUniqueKey(Map<String, Object> uniqueyKeyFieldsVsValues) throws EntityNotFoundException;

	/**
	 * Updates the information of a Quotes.
	 *
	 * @param quotes The information of the updated Quotes.
	 * @return The updated Quotes.
     *
	 * @throws EntityNotFoundException if no Quotes is found with given id.
	 */
	Quotes update(Quotes quotes) throws EntityNotFoundException;

    /**
	 * Deletes a Quotes.
	 *
	 * @param quotesId The id of the deleted Quotes.
	 * @return The deleted Quotes.
     *
	 * @throws EntityNotFoundException if no Quotes is found with the given id.
	 */
	Quotes delete(Integer quotesId) throws EntityNotFoundException;

	/**
	 * Finds all Quotes.
	 *
	 * @return A list of Quotes.
	 */
    @Deprecated
	Page<Quotes> findAll(QueryFilter[] queryFilters, Pageable pageable);

    /**
	 * Finds all Quotes.
	 * @return A list of Quotes.
	 */
    Page<Quotes> findAll(String query, Pageable pageable);

    Downloadable export(ExportType exportType, String query, Pageable pageable);

	/**
	 * Retrieve the count of the Quotes in the repository with matching query.
     *
     * @param query query to filter results.
	 * @return The count of the Quotes.
	 */
	long count(String query);

    Page<FollowUps> findAssociatedFollowUpses(Integer id, Pageable pageable);

    Page<Sales> findAssociatedSaleses(Integer id, Pageable pageable);

    Page<Quotes> findAssociatedValues(Object value, String entityName, String key,  Pageable pageable);

}

