package fr.esiea.windmeal.controller.provider.search.elastisearch;


import fr.esiea.windmeal.dao.exception.DaoException;
import fr.esiea.windmeal.model.FoodProvider;
import fr.esiea.windmeal.service.exception.ServiceException;
import fr.esiea.windmeal.service.search.elasticsearch.ISearchProviderService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
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
 * WITH THE SOFTWARE OR THE USE OR OTHER DELINGS IN THE SOFTWARE.
 */
@Controller
@RequestMapping("/search/providers/request")
public class ProviderSearchCtrl {

    private static final Logger LOGGER = Logger.getLogger(ProviderSearchCtrl.class);
    @Autowired
    @Qualifier("elasticSearchService")
    private ISearchProviderService searchProvider;

    //curl -v -XGET -H "Content-Type:application/json" 'http://localhost:8080/windmeal/rest/search/providers/request' -d '{"request":"restaurant"}'
    @RequestMapping(method = RequestMethod.GET, params = "request")
    @ResponseBody
    public Iterable<FoodProvider> SearchProviderNearLocation(@RequestParam("request") String searchedTerms) throws ServiceException, DaoException {
        LOGGER.info("The user is querying " + searchedTerms);
        return searchProvider.search(searchedTerms);

    }

}


