package fr.esiea.windmeal.fill.database;

import fr.esiea.windmeal.dao.ICrudDao;
import fr.esiea.windmeal.dao.exception.DaoException;
import fr.esiea.windmeal.fill.database.mock.SecurityMockService;
import fr.esiea.windmeal.model.*;
import fr.esiea.windmeal.model.enumeration.Tag;
import fr.esiea.windmeal.model.security.Profile;
import fr.esiea.windmeal.service.crud.ICrudService;
import fr.esiea.windmeal.service.exception.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static fr.esiea.windmeal.fill.database.helper.CsvHelper.readContactCSV;
import static fr.esiea.windmeal.fill.database.helper.FillHelper.*;

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
 */@RunWith(SpringJUnit4ClassRunner.class)
   @ContextConfiguration(
           locations = {
                   "classpath*:spring/application-context.xml",
           })
public class OwnerAndProviderImportationTest {

    @Autowired
    ApplicationContext applicationContext;

    private static ICrudService<User> userService;
    private static ICrudService<FoodProvider> providerService;
    private static ICrudService<Menu> menuService;

    @Test
    public void fillDbElasticsearch() throws Exception {

        //By using service u are sure than logic rules are applyed
        userService = (ICrudService<User>) applicationContext.getBean("userCrudService");
        providerService = (ICrudService<FoodProvider>) applicationContext.getBean("providerCrudService");
        menuService = (ICrudService<Menu>) applicationContext.getBean("menuCrudService");
        //Mock the security context
        SecurityMockService mockService = new SecurityMockService();
        userService.setSecurityService(mockService);
        menuService.setSecurityService(mockService);
        providerService.setSecurityService(mockService);

        ClassPathResource cpr = new ClassPathResource("data/OwnerAndProvider.csv");
        List<Map<String, String>> usersList = readContactCSV(SolveSpaceErrors(cpr.getURL().getPath()));

        User owner;
        FoodProvider foodProvider;
        Menu menu;
        Set<Tag> tags;
        Set<Comment> comments;

        for(Map<String,String> dataMap :usersList)  {
            owner = getUser(dataMap.get("email"), dataMap.get("password"), Profile.PROVIDER);

            menu=getMenu(getMealsFromMap(dataMap));
            tags=getTagsFromMap(dataMap);
            comments=getCommentsFromMap(dataMap); // Care the method add user to the database
            Address address = getAddress(dataMap.get("street"),dataMap.get("city"),dataMap.get("postcode"));
            foodProvider = getFoodProvider(owner,menu,tags,dataMap.get("description"),comments, address,dataMap.get("name"),dataMap.get("phone"));

            userService.save(owner);
            menuService.save(menu);
            providerService.save(foodProvider);

        }
    }

    private static Set<Comment> getCommentsFromMap(Map<String,String> dataMap) throws ServiceException, DaoException {
        int acc =1;

        Set<Comment> comments = new HashSet<Comment>();
        while(dataMap.containsKey("commentemail" + acc))  {
            User user = getUser(dataMap.get("commentemail"+acc), dataMap.get("commentpassword"+acc), Profile.USER);
            userService.save(user);
            Comment comment = getComment(user,dataMap.get("commenttext"+acc), Integer.valueOf(dataMap.get("commentrate" + acc)));
            comments.add(comment);
            acc++;

        }
        return comments;
    }

    private static Set<Tag> getTagsFromMap(Map<String, String> dataMap) {
        int acc =1;

        Set<Tag> tags = new HashSet<Tag>();
        while(dataMap.containsKey("tag" + acc))  {
            tags.add(Tag.valueOf(dataMap.get("tag"+acc)));
            acc++;

        }
        return tags;
    }

    private static Meal[] getMealsFromMap(Map<String, String> dataMap) {
        int acc =1;

        Set<Meal> menu = new HashSet<Meal>();
        while(dataMap.containsKey("mealname" + acc))  {
            Meal meal = createMeal(dataMap.get("mealname" + acc), dataMap.get("mealdescription" + acc), Double.valueOf(dataMap.get("mealprice" + acc)));
            menu.add(meal);
            acc++;

        }

        Meal[] mealArray = new Meal[menu.size()];
        acc = 0;
        for(Meal meal:menu) {
            mealArray[acc] = meal;
            acc++;
        }
        return mealArray;
    }

    private static String SolveSpaceErrors(String path) {
        //fast fix to solve the macintosh%20hd issue
        //Should be better handled
        return path.replace("%20", " ");
    }
}
