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

import fr.esiea.windmeal.model.*;
import fr.esiea.windmeal.model.security.Profile;

import java.util.HashMap;
import java.util.Map;

public class FillTestDB {

    public static void main(String[] args) {
        User user = new User();
        user.setEmail("provider@gmail.com");
        user.setPassword("pwd");
        user.setProfile(Profile.PROVIDER);
        user.generateId();


        Menu menu = new Menu();

        Meal meal1 = createMeal("scalopina limone","viande au citron",20);
        Meal meal2 = createMeal("pomodora spaghetti","Italian pomodoro pasta",15.5);
        menu.setMeals(getMeals(meal1,meal2));

        FoodProvider provider = new FoodProvider();
        provider.setTags();
        provider.setEmail();
        provider.setDescription();
        provider.setComments();
        provider.setAddress(getAddress(null, null, null));
        provider.setMenuId(menu.getId());
        provider.setOwnerId(user.getId());
        provider.setName("Marco Polo");
        provider.setPhone("0667021042");
    }

    private static Map<String, Meal> getMeals(Meal... meals) {
        Map map = new HashMap();

        for(Meal meal:meals) {
            map.put(meal.getId(),meal);
        }

        return map;
    }

    private static Meal createMeal(String name,String description, double price) {
        Meal meal1 = new Meal();
        meal1.generateId();
        meal1.setDescription(description);
        meal1.setName(name);
        meal1.setPrice(price);
    }

    public static Address getAddress(String number, String street, String city) {
        Address address = new Address();
        address.setNumber(number);
        address.setStreet(street);
        address.setCity(city);
        return address;
    }
}
