package fr.esiea.windmeal.dao.mongo;

import fr.esiea.windmeal.dao.ICrudProviderDao;
import fr.esiea.windmeal.dao.IGeoProviderDao;
import fr.esiea.windmeal.dao.exception.DaoException;
import fr.esiea.windmeal.model.FoodProvider;
import org.jongo.MongoCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

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
@Repository
public class ProviderDao implements ICrudProviderDao,IGeoProviderDao {
	@Autowired
	@Qualifier("providerCollection")
	MongoCollection collection;

	@Override
	public Iterable<FoodProvider> getAll() throws DaoException {

		Iterable<FoodProvider> providers = collection.find().as(FoodProvider.class);
		return providers;
	}

	@Override
	public FoodProvider getOne(String id) throws DaoException {
		FoodProvider provider = collection.findOne("{'_id':#}", id).as(FoodProvider.class);
		return provider;
	}

	@Override
	public void save(FoodProvider model) throws DaoException {
		collection.save(model);
	}

	@Override
	public void insert(FoodProvider model) throws DaoException {
		collection.save(model);
	}

	@Override
	public void remove(String id) throws DaoException {
		collection.remove("{'_id':#}", id);
	}

    @Override
    public Iterable<FoodProvider> getAllProviderFromUser(String ownerId) {
        return collection.find("{'ownerId':#}", ownerId).as(FoodProvider.class);
    }

    @Override
    public Iterable<FoodProvider> getProviderNear() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
