package fr.esiea.windmeal.fill.database;

import fr.esiea.windmeal.dao.exception.DaoException;
import fr.esiea.windmeal.model.User;
import fr.esiea.windmeal.model.security.Profile;
import fr.esiea.windmeal.service.crud.ICrudService;
import fr.esiea.windmeal.service.exception.ServiceException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static fr.esiea.windmeal.fill.database.helper.CsvHelper.readContactCSV;
import static fr.esiea.windmeal.fill.database.helper.FillHelper.getUser;

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
public class UserImportation {

    public static void main(String[] args) throws IOException, DaoException, ServiceException {

        ICrudService<User> userService;

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath*:spring/application-context.xml");
        //By using service u are sure than logic rules are applyed
        userService = (ICrudService<User>) applicationContext.getBean("userCrudService");
        ClassPathResource cpr = new ClassPathResource("data/users.csv");
        List<Map<String, String>> usersList = readContactCSV(SolveSpaceErrors(cpr.getURL().getPath()));

        User user;

        for(Map<String,String> userMap:usersList)  {
            user = getUser(userMap.get("email"),userMap.get("password"),Profile.valueOf(userMap.get("profile")));
            userService.insert(user);
        }
    }

    private static String SolveSpaceErrors(String path) {
        //fast fix to solve the macintosh%20hd issue
        //Should be better handled
        return path.replace("%20", " ");
    }
}
