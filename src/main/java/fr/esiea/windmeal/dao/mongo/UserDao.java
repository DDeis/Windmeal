package fr.esiea.windmeal.dao.mongo;

import fr.esiea.windmeal.dao.ICrudUserDao;
import fr.esiea.windmeal.dao.exception.DaoException;
import fr.esiea.windmeal.model.User;
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
public class UserDao implements ICrudUserDao {

	@Autowired
	@Qualifier("userCollection")
	MongoCollection collection;

	@Override
	public Iterable<User> getAll() throws DaoException {
		Iterable<User> users = collection.find().as(User.class);
		return users;
	}

	@Override
	public User getOne(String id) throws DaoException {
		User user = collection.findOne("{'_id':#}", id).as(User.class);
		return user;
	}

	@Override
	public void save(User model) throws DaoException {
		collection.save(model);
	}

	@Override
	public void insert(User model) throws DaoException {
		collection.save(model);
	}

	@Override
	public void remove(String id) throws DaoException {
		collection.remove("{'_id':#}", id);
	}

	@Override
	public User getOneByMail(String email) throws DaoException {
		User user = collection.findOne("{'email':#}", email).as(User.class);
		return user;
	}
}
