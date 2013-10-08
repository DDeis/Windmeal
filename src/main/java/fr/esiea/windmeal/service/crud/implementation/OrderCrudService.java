package fr.esiea.windmeal.service.crud.implementation;

import fr.esiea.windmeal.dao.ICrudDao;
import fr.esiea.windmeal.dao.exception.DaoException;
import fr.esiea.windmeal.model.Order;
import fr.esiea.windmeal.service.crud.ICrudService;
import fr.esiea.windmeal.service.exception.InvalidIdException;
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
public class OrderCrudService implements ICrudService<Order> {
	@Autowired
	@Qualifier("orderDao")
	private ICrudDao<Order> dao;

	@Override
	public Iterable<Order> getAll() throws DaoException {
		return dao.getAll();
	}

	@Override
	public void remove(String idOrder) throws DaoException {
		dao.remove(idOrder);
	}

	@Override
	public void save(Order order) throws DaoException {
		dao.save(order);
	}

	@Override
	public void insert(Order order) throws DaoException {
		dao.insert(order);
	}

	@Override
	public Order getOne(String orderId) throws InvalidIdException, DaoException {
		Order order = dao.getOne(orderId);
		if (null == order)
			throw new InvalidIdException();
		return order;
	}
}
