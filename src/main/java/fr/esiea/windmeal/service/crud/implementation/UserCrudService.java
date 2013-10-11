package fr.esiea.windmeal.service.crud.implementation;

import fr.esiea.windmeal.dao.ICrudUserDao;
import fr.esiea.windmeal.dao.exception.DaoException;
import fr.esiea.windmeal.model.User;
import fr.esiea.windmeal.service.crud.ICrudUserService;
import fr.esiea.windmeal.service.exception.EmailAlreadyExist;
import fr.esiea.windmeal.service.exception.InvalidIdException;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserCrudService implements ICrudUserService {
    @Autowired
    private ICrudUserDao dao;

    @Override
    public Iterable<User> getAll() throws DaoException {
        return dao.getAll();
    }

    @Override
    public void remove(String idUser) throws DaoException {
        dao.remove(idUser);
    }

    @Override
    public void save(User user) throws DaoException {
        if(checkUniqueEmail(user.getEmail(),user.getId())  )
            dao.save(user);
    }

    @Override
    public void insert(User user) throws EmailAlreadyExist, DaoException {
        if(checkUniqueEmail(user.getEmail()))
            dao.insert(user);
        else
            throw new EmailAlreadyExist();
    }

    @Override
    public User getOne(String userId) throws InvalidIdException, DaoException {
        User user = dao.getOne(userId);
        if (null == user)
            throw new InvalidIdException();
        return user;
    }

    @Override
    public User getOneByMail(String email) throws InvalidIdException, DaoException {
        User user = dao.getOneByMail(email);
        if (null == user)
            throw new InvalidIdException();
        return user;
    }


    private boolean checkUniqueEmail(String email) throws DaoException {
        return dao.getOneByMail(email) == null;
    }


    private boolean checkUniqueEmail(String emailUserUpdated, String idUserUpdated) throws DaoException {
        User userUpdated = dao.getOneByMail(emailUserUpdated);
        if(userUpdated == null)
            return true; //the user update is own email
        else
            return userUpdated.getId().equals(idUserUpdated);
    }
}
