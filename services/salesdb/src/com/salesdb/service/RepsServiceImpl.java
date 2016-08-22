/*Copyright (c) 2015-2016 wavemaker-com All Rights Reserved.This software is the confidential and proprietary information of wavemaker-com You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the source code license agreement you entered into with wavemaker-com*/

package com.salesdb.service;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wavemaker.runtime.data.dao.WMGenericDao;
import com.wavemaker.runtime.data.exception.EntityNotFoundException;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.file.model.Downloadable;

import com.salesdb.Quotes;
import com.salesdb.Reps;
import com.salesdb.Tasks;


/**
 * ServiceImpl object for domain model class Reps.
 *
 * @see Reps
 */
@Service("salesdb.RepsService")
public class RepsServiceImpl implements RepsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RepsServiceImpl.class);

    @Autowired
	@Qualifier("salesdb.QuotesService")
	private QuotesService quotesService;

    @Autowired
	@Qualifier("salesdb.TasksService")
	private TasksService tasksService;

    @Autowired
    @Qualifier("salesdb.RepsDao")
    private WMGenericDao<Reps, Integer> wmGenericDao;

    public void setWMGenericDao(WMGenericDao<Reps, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "salesdbTransactionManager")
    @Override
	public Reps create(Reps reps) {
        LOGGER.debug("Creating a new Reps with information: {}", reps);
        Reps repsCreated = this.wmGenericDao.create(reps);
        if(repsCreated.getTaskses() != null) {
            for(Tasks taskse : repsCreated.getTaskses()) {
                taskse.setReps(repsCreated);
                LOGGER.debug("Creating a new child Tasks with information: {}", taskse);
                tasksService.create(taskse);
            }
        }

        if(repsCreated.getQuoteses() != null) {
            for(Quotes quotese : repsCreated.getQuoteses()) {
                quotese.setReps(repsCreated);
                LOGGER.debug("Creating a new child Quotes with information: {}", quotese);
                quotesService.create(quotese);
            }
        }
        return repsCreated;
    }

	@Transactional(readOnly = true, value = "salesdbTransactionManager")
	@Override
	public Reps getById(Integer repsId) throws EntityNotFoundException {
        LOGGER.debug("Finding Reps by id: {}", repsId);
        Reps reps = this.wmGenericDao.findById(repsId);
        if (reps == null){
            LOGGER.debug("No Reps found with id: {}", repsId);
            throw new EntityNotFoundException(String.valueOf(repsId));
        }
        return reps;
    }

    @Transactional(readOnly = true, value = "salesdbTransactionManager")
	@Override
	public Reps getByUniqueKey(Map<String, Object> uniqueyKeyFieldsVsValues) throws EntityNotFoundException {
        LOGGER.debug("Finding Reps by unique keys: {}", uniqueyKeyFieldsVsValues);
        Reps reps = this.wmGenericDao.findByUniqueKey(uniqueyKeyFieldsVsValues);
        if (reps == null){
            LOGGER.debug("No Reps found with given unique key values: {}", uniqueyKeyFieldsVsValues);
            throw new EntityNotFoundException(String.valueOf(uniqueyKeyFieldsVsValues));
        }
        return reps;
    }

	@Transactional(rollbackFor = EntityNotFoundException.class, value = "salesdbTransactionManager")
	@Override
	public Reps update(Reps reps) throws EntityNotFoundException {
        LOGGER.debug("Updating Reps with information: {}", reps);
        this.wmGenericDao.update(reps);

        Integer repsId = reps.getId();

        return this.wmGenericDao.findById(repsId);
    }

    @Transactional(value = "salesdbTransactionManager")
	@Override
	public Reps delete(Integer repsId) throws EntityNotFoundException {
        LOGGER.debug("Deleting Reps with id: {}", repsId);
        Reps deleted = this.wmGenericDao.findById(repsId);
        if (deleted == null) {
            LOGGER.debug("No Reps found with id: {}", repsId);
            throw new EntityNotFoundException(String.valueOf(repsId));
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

	@Transactional(readOnly = true, value = "salesdbTransactionManager")
	@Override
	public Page<Reps> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all Reps");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "salesdbTransactionManager")
    @Override
    public Page<Reps> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all Reps");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "salesdbTransactionManager")
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service salesdb for table Reps to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

	@Transactional(readOnly = true, value = "salesdbTransactionManager")
	@Override
	public long count(String query) {
        return this.wmGenericDao.count(query);
    }

    @Override
    public Page<Tasks> findAssociatedTaskses(Integer id, Pageable pageable) {
        LOGGER.debug("Fetching all associated taskses");
        return tasksService.findAssociatedValues(id, "reps", "id", pageable);
    }

    @Override
    public Page<Quotes> findAssociatedQuoteses(Integer id, Pageable pageable) {
        LOGGER.debug("Fetching all associated quoteses");
        return quotesService.findAssociatedValues(id, "reps", "id", pageable);
    }

    @Transactional(readOnly = true, value = "salesdbTransactionManager")
    @SuppressWarnings("unchecked")
	@Override
    public Page<Reps> findAssociatedValues(Object value, String entityName, String key,  Pageable pageable) {
        LOGGER.debug("Fetching all associated");
        return this.wmGenericDao.getAssociatedObjects(value, entityName, key, pageable);
    }

    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service QuotesService instance
	 */
	protected void setQuotesService(QuotesService service) {
        this.quotesService = service;
    }

    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service TasksService instance
	 */
	protected void setTasksService(TasksService service) {
        this.tasksService = service;
    }

}

