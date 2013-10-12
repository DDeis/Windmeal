package fr.esiea.windmeal.controller.provider;

import fr.esiea.windmeal.dao.exception.DaoException;
import fr.esiea.windmeal.model.FoodProvider;
import fr.esiea.windmeal.service.crud.ICrudProviderService;
import fr.esiea.windmeal.service.exception.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

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
public class ProviderCtrl {

    @Autowired
    ICrudProviderService providerService;
    private final static Logger LOGGER = Logger.getLogger(ProviderCtrl.class);

    @RequestMapping(value="user/{ownerId}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Iterable<String> getAll(@PathVariable String ownerId) throws ServiceException, DaoException, IOException {

        LOGGER.info("[Controller] Querying FoodProvider list from the user " + ownerId);

        List<String> foodProviderIds = new ArrayList<String>();
        Iterator<FoodProvider> iterator = providerService.getAllProviderFromUser(ownerId).iterator();
        while (iterator.hasNext()) {
            FoodProvider foodProvider =  iterator.next();
            foodProviderIds.add(foodProvider.getId());
        }

        return foodProviderIds;
    }

}