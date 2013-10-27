package fr.esiea.windmeal.service.crud.implementation;

import fr.esiea.windmeal.dao.ICrudDao;
import fr.esiea.windmeal.dao.ICrudProviderDao;
import fr.esiea.windmeal.dao.exception.DaoException;
import fr.esiea.windmeal.model.Comment;
import fr.esiea.windmeal.model.FoodProvider;
import fr.esiea.windmeal.model.Menu;
import fr.esiea.windmeal.service.crud.ICrudProviderService;
import fr.esiea.windmeal.service.crud.ICrudService;
import fr.esiea.windmeal.service.exception.InvalidIdException;
import fr.esiea.windmeal.service.exception.ServiceException;
import fr.esiea.windmeal.service.security.AbstractSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class ProviderCrudService extends AbstractSecurityService implements ICrudProviderService {
	@Autowired
	@Qualifier("elasticSearchIndexation")
	private ICrudProviderDao dao;
	@Autowired // Only to add a comment not elegant
	@Qualifier("providerValidationDecorator")
	private ICrudService<FoodProvider> validationService;
    @Autowired
    @Qualifier("menuDao")
    private ICrudDao<Menu> menuDao;

	@Override
	public Iterable<FoodProvider> getAll() throws DaoException {
		return dao.getAll();
	}

	@Override
	public void remove(String idFoodProvider) throws DaoException, ServiceException {
        isTheUserOwnTheModel(dao.getOne(idFoodProvider).getOwnerId());
		dao.remove(idFoodProvider);
	}

	@Override
	public void save(FoodProvider provider) throws DaoException, ServiceException {
        isTheUserOwnTheModel(provider.getOwnerId());
        dao.save(provider);
	}

	@Override
	public void insert(FoodProvider provider) throws DaoException, ServiceException {
        isTheUserOwnTheModel(provider.getOwnerId());
        Menu menu = new Menu();
        menu.generateId();
        provider.setMenuId(menu.getId());
        menuDao.insert(menu);
        dao.insert(provider);
	}

	@Override
	public FoodProvider getOne(String providerId) throws InvalidIdException, DaoException {
		FoodProvider provider = dao.getOne(providerId);
		if (null == provider)
			throw new InvalidIdException();
		return provider;
	}

	@Override
	public Iterable<FoodProvider> getAllProviderFromUser(String ownerId) throws DaoException {
		return dao.getAllProviderFromUser(ownerId);
	}

	@Override
	public void addComment(String providerId, Comment comment) throws DaoException, ServiceException {
		isTheUserOwnTheModel(comment.getUserId());
        FoodProvider one = dao.getOne(providerId);
		if (one == null) {
			throw new InvalidIdException();
		}
		comment.generateId();
		one.addComment(comment);
		validationService.save(one);
	}
}
