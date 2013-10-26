package fr.esiea.windmeal.service.search.geo.implementation;

import fr.esiea.windmeal.dao.IGeoProviderDao;
import fr.esiea.windmeal.dao.exception.DaoException;
import fr.esiea.windmeal.model.FoodProvider;
import fr.esiea.windmeal.model.geospatiale.Location;
import fr.esiea.windmeal.service.search.geo.IGeoProviderService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
@Service
public class GeoProviderService implements IGeoProviderService  {

    @Autowired
    IGeoProviderDao dao;

    @Value("${geo.search.max.distance}")
    public int maxDistance;

   private static final Logger LOGGER = Logger.getLogger(GeoProviderService.class);

    @Override
    public Iterable<FoodProvider> getProviderNear(Location location) throws DaoException {
        LOGGER.info("Research from ["+location.getLng()+", "+location.getLat()+"] with max distance " + maxDistance);
        return dao.getProviderNear(location,maxDistance);
    }
}
