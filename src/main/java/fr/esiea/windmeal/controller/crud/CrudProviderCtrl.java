package fr.esiea.windmeal.controller.crud;

import fr.esiea.windmeal.dao.exception.DaoException;
import fr.esiea.windmeal.model.FoodProvider;
import fr.esiea.windmeal.service.crud.ICrudService;
import fr.esiea.windmeal.service.exception.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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
@RequestMapping("/providers")
public class CrudProviderCtrl {
	private final static Logger LOGGER = Logger.getLogger(CrudProviderCtrl.class);
	@Autowired
	@Qualifier("providerValidationDecorator")
	ICrudService<FoodProvider> crudService;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Iterable<FoodProvider> getAll(HttpServletResponse servletResponse) throws ServiceException, DaoException, IOException {

		LOGGER.info("[Controller] Querying FoodProvider list");
		return crudService.getAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public FoodProvider getById(@PathVariable("id") String providerId) throws ServiceException, DaoException {

		LOGGER.info("[Controller] Querying FoodProvider with id : \"" + providerId + "\"");
		return crudService.getOne(providerId);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
	@ResponseStatus(HttpStatus.CREATED)
	public void create(@RequestBody FoodProvider provider) throws ServiceException, DaoException {

		LOGGER.info("[Controller] Querying to create new provider : " + provider.toString() + "\"");
		crudService.insert(provider);
	}

	@RequestMapping(value = "", method = RequestMethod.PUT, consumes = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public void edit(@RequestBody FoodProvider provider) throws ServiceException, DaoException {

		LOGGER.info("[Controller] Querying to edit FoodProvider : \"" + provider.toString() + "\"");
		crudService.save(provider);
	}

	@RequestMapping(value = "/{idFoodProvider}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable String idFoodProvider) throws ServiceException, DaoException {

		LOGGER.info("[Controller] Querying to delete FoodProvider with id : \"" + idFoodProvider + "\"");
		crudService.remove(idFoodProvider);
	}
}
