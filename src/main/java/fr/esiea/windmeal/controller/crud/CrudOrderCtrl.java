package fr.esiea.windmeal.controller.crud;

import fr.esiea.windmeal.dao.exception.DaoException;
import fr.esiea.windmeal.model.Order;
import fr.esiea.windmeal.service.crud.ICrudOrderService;
import fr.esiea.windmeal.service.crud.ICrudService;
import fr.esiea.windmeal.service.crud.implementation.OrderCrudService;
import fr.esiea.windmeal.service.exception.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Copyright (c) 2013 ESIEA M. Labusquiere D. Déïs
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
@Controller
@RequestMapping("/orders")
public class  CrudOrderCtrl {
	private final static Logger LOGGER = Logger.getLogger(CrudOrderCtrl.class);

    @Autowired
	@Qualifier("orderValidationDecorator")
	private ICrudService<Order> crudValidationService;

    @Autowired
	private ICrudOrderService orderService;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Iterable<Order> getAll() throws ServiceException, DaoException, IOException {

		LOGGER.info("[Controller] Querying Order list");
		return crudValidationService.getAll();
	}

	@RequestMapping(value = "/{id}/provider", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
    @Secured("ROLE_USER")
	public Iterable<Order> getAllFromProvider(@PathVariable("id") String providerId) throws ServiceException, DaoException, IOException {

		LOGGER.info("[Controller] Querying Order for provider : "+providerId);
		return orderService.getAllFromProvider(providerId);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Order getById(@PathVariable("id") String orderId) throws ServiceException, DaoException {

		LOGGER.info("[Controller] Querying Order with id : \"" + orderId + "\"");
		return crudValidationService.getOne(orderId);
	}

    @Secured("ROLE_USER")
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
	@ResponseStatus(HttpStatus.CREATED)
	public void create(@RequestBody Order order) throws ServiceException, DaoException {

		LOGGER.info("[Controller] Querying to create new order : " + order.toString() + "\"");
		crudValidationService.insert(order);
	}

    @Secured("ROLE_USER")
	@RequestMapping(value = "{id}", method = RequestMethod.PUT, consumes = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public void edit(@PathVariable String id, @RequestBody Order order) throws ServiceException, DaoException {

		LOGGER.info("[Controller] Querying to edit Order : \"" + order.toString() + "\"");
		crudValidationService.save(order);
	}

    @Secured("ROLE_USER")
	@RequestMapping(value = "/{idOrder}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable String idOrder) throws ServiceException, DaoException {

		LOGGER.info("[Controller] Querying to delete Order with id : \"" + idOrder + "\"");
		crudValidationService.remove(idOrder);
	}
}
