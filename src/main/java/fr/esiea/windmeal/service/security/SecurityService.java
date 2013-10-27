package fr.esiea.windmeal.service.security;

import fr.esiea.windmeal.controller.exception.security.NeedToBeAuthenticatedException;
import fr.esiea.windmeal.dao.ICrudDao;
import fr.esiea.windmeal.dao.exception.DaoException;
import fr.esiea.windmeal.model.User;
import fr.esiea.windmeal.service.exception.NotPermitException;
import fr.esiea.windmeal.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class SecurityService {

    @Autowired
    ICrudDao<User> userDao;

    public User getUserConnected() throws DaoException, NotPermitException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userAuthenticate =  userDao.getOne(authentication.getDetails().toString());

        if(null == userAuthenticate)
            throw new NotPermitException();

        return userAuthenticate;
    }


    public void isTheUserOwnTheModel(String id) throws ServiceException, DaoException {
        if(!getUserConnected().getId().equals(id))
            throw new NotPermitException();
    }

}
