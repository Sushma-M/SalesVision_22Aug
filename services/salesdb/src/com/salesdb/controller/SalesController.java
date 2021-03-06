/*Copyright (c) 2015-2016 wavemaker-com All Rights Reserved.This software is the confidential and proprietary information of wavemaker-com You shall not disclose such Confidential Information and shall use it only in accordance with the terms of the source code license agreement you entered into with wavemaker-com*/

package com.salesdb.controller;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.wavemaker.runtime.data.exception.EntityNotFoundException;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.file.model.Downloadable;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.salesdb.Sales;
import com.salesdb.service.SalesService;
import com.wordnik.swagger.annotations.*;
import com.wavemaker.tools.api.core.annotations.WMAccessVisibility;
import com.wavemaker.tools.api.core.models.AccessSpecifier;

/**
 * Controller object for domain model class Sales.
 * @see Sales
 */
@RestController("salesdb.SalesController")
@RequestMapping("/salesdb/Sales")
@Api(description = "Exposes APIs to work with Sales resource.", value = "SalesController")
public class SalesController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SalesController.class);

    @Autowired
    @Qualifier("salesdb.SalesService")
    private SalesService salesService;

    /**
     * @deprecated Use {@link #findSales(String, Pageable)} instead.
     */
    @Deprecated
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ApiOperation(value = "Returns the list of Sales instances matching the search criteria.")
    public Page<Sales> findSales(Pageable pageable, @RequestBody QueryFilter[] queryFilters) {
        LOGGER.debug("Rendering Sales list");
        return salesService.findAll(queryFilters, pageable);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ApiOperation(value = "Returns the list of Sales instances matching the search criteria.")
    public Page<Sales> findSales(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        LOGGER.debug("Rendering Sales list");
        return salesService.findAll(query, pageable);
    }

    @RequestMapping(value = "/export/{exportType}", method = RequestMethod.GET, produces = "application/octet-stream")
    @ApiOperation(value = "Returns downloadable file for the data.")
    public Downloadable exportSales(@PathVariable("exportType") ExportType exportType, @ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query, Pageable pageable) {
        return salesService.export(exportType, query, pageable);
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    @ApiOperation(value = "Returns the total count of Sales instances.")
    public Long countSales(@ApiParam("conditions to filter the results") @RequestParam(value = "q", required = false) String query) {
        LOGGER.debug("counting Sales");
        return salesService.count(query);
    }

    /**
	 * This setter method should only be used by unit tests
	 *
	 * @param service SalesService instance
	 */
    protected void setSalesService(SalesService service) {
        this.salesService = service;
    }

    @RequestMapping(method = RequestMethod.POST)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    @ApiOperation(value = "Creates a new Sales instance.")
    public Sales createSales(@RequestBody Sales salesInstance) {
        LOGGER.debug("Create Sales with information: {}", salesInstance);
        salesInstance = salesService.create(salesInstance);
        LOGGER.debug("Created Sales with information: {}", salesInstance);
        return salesInstance;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    @ApiOperation(value = "Returns the Sales instance associated with the given id.")
    public Sales getSales(@PathVariable(value = "id") Integer id) throws EntityNotFoundException {
        LOGGER.debug("Getting Sales with id: {}", id);
        Sales foundSales = salesService.getById(id);
        LOGGER.debug("Sales details with id: {}", foundSales);
        return foundSales;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    @ApiOperation(value = "Updates the Sales instance associated with the given id.")
    public Sales editSales(@PathVariable(value = "id") Integer id, @RequestBody Sales salesInstance) throws EntityNotFoundException {
        LOGGER.debug("Editing Sales with id: {}", salesInstance.getId());
        salesInstance.setId(id);
        salesInstance = salesService.update(salesInstance);
        LOGGER.debug("Sales details with id: {}", salesInstance);
        return salesInstance;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @WMAccessVisibility(value = AccessSpecifier.APP_ONLY)
    @ApiOperation(value = "Deletes the Sales instance associated with the given id.")
    public boolean deleteSales(@PathVariable(value = "id") Integer id) throws EntityNotFoundException {
        LOGGER.debug("Deleting Sales with id: {}", id);
        Sales deletedSales = salesService.delete(id);
        return deletedSales != null;
    }
}
