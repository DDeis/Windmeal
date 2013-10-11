/*
 * Copyright (c) 2013. ESIEA M. Labusquiere & D. Deis
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package fr.esiea.windmeal.test.integration.fill;

import fr.esiea.windmeal.dao.ICrudDao;
import fr.esiea.windmeal.model.*;
import fr.esiea.windmeal.model.enumeration.Tag;
import fr.esiea.windmeal.model.exception.RestException;
import fr.esiea.windmeal.model.security.Profile;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static fr.esiea.windmeal.test.integration.fill.helper.FillHelper.*;
public class FillTestDB {

    public static void main(String[] args) throws RestException {
        ICrudDao<User> userDao;
        ICrudDao<FoodProvider> providerDao;
        ICrudDao<Menu> menuDao;


        User user = getUser("provider@gmail.com", "pwd", Profile.PROVIDER);
        User customer1 = getUser("customer1@gmail.com", "pwd", Profile.USER);
        User customer2 = getUser("customer2@gmail.com", "pwd", Profile.USER);
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath*:spring/application-context.xml");

        userDao = (ICrudDao<User>) applicationContext.getBean("userDao");
        providerDao = (ICrudDao<FoodProvider>) applicationContext.getBean("providerDao");
        menuDao = (ICrudDao<Menu>) applicationContext.getBean("menuDao");

        userDao.save(user);
        userDao.save(customer1);
        userDao.save(customer2);
        Meal meal1 = createMeal("scalopina limone", "viande au citron", 20);
        Meal meal2 = createMeal("pomodora spaghetti", "Italian pomodoro pasta", 15.5);
        Menu menu = getMenu(meal1, meal2);
        menuDao.save(menu);
        Comment comment1 = getComment(customer1, "Très bon restaurant", 4);
        Comment comment2 = getComment(customer2, "Très bon restaurant bis", 4);

        FoodProvider provider = getFoodProvider(user,menu,getTags(Tag.ITALIAN), "restaurant", getComments(comment1, comment2), getAddress("8", "rue de condee", "Paris","75006"), "Marco Polo", "0667021042");
        providerDao.save(provider);

    }

}
