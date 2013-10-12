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

package fr.esiea.windmeal.test.integration.fill.helper;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.GeocoderResult;
import fr.esiea.windmeal.model.*;
import fr.esiea.windmeal.model.enumeration.Tag;
import fr.esiea.windmeal.model.geospatiale.Location;
import fr.esiea.windmeal.model.security.Profile;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FillHelper {

    private static final Geocoder geocoder = new Geocoder();
    private static final Logger LOGGER = Logger.getLogger(FillHelper.class);
    /*
             * Should be transformed as builder
             */
    public static Set<Tag> getTags(Tag... tags) {
        HashSet<Tag> tagsSet = new HashSet<Tag>();

        for(Tag tag:tags)  {
            tagsSet.add(tag);
        }
        return tagsSet;
    }

    public static Menu getMenu(Meal... meals) {

        List list = new ArrayList<>();
        Menu menu = new Menu();
        for(Meal meal:meals) {
            meal.generateId();
            list.add(meal);
        }
        menu.setMeals(list);
        menu.generateId();
        return menu;
    }

    public static Meal createMeal(String name,String description, double price) {
        Meal meal = new Meal();
        meal.generateId();
        meal.setDescription(description);
        meal.setName(name);
        meal.setPrice(price);
        return meal;
    }

    public static Address getAddress(String street, String city,String postcode) {
        Address address = new Address();
        address.setStreet(street);
        address.setPostalCode(postcode);
        address.setCity(city);
        address.setLocation(getLocation(address));
        return address;
    }

    public static FoodProvider getFoodProvider(User owner, Menu menu, Set<Tag> tag, String description, Set<Comment> comments, Address address, String name, String phone) {
        FoodProvider provider = new FoodProvider();
        provider.generateId();
        provider.setTags(tag);
        provider.setEmail(owner.getEmail());
        provider.setDescription(description);
        provider.setComments(comments);
        provider.setAddress(address);
        provider.setMenuId(menu.getId());
        provider.setOwnerId(owner.getId());
        provider.setName(name);
        provider.setPhone(phone);
        return provider;
    }

    public static Set<Comment> getComments(Comment... comments) {
        HashSet<Comment> commentsSet = new HashSet<Comment>();

        for(Comment comment:comments)  {
            commentsSet.add(comment);
        }
        return commentsSet;
    }
    public static Comment getComment(User user, String text, int rate) {
        Comment comment = new Comment();
        comment.generateId();
        comment.setRate(rate);
        comment.setText(text);
        comment.setUserId(user.getId());
        return comment;
    }

    public static User getUser(String email, String pwd, Profile provider) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(pwd);
        user.setProfile(provider);
        user.generateId();
        return user;
    }

    private static Location getLocation(Address address)   {
        //Shitty code
        GeocoderRequest geocoderRequest = new GeocoderRequestBuilder().setAddress(address.getStreet() + ", " +address.getPostalCode() + ", " + address.getCity()).setLanguage("fr").getGeocoderRequest();
        GeocodeResponse geocoderResponse = geocoder.geocode(geocoderRequest);
        List<GeocoderResult> results = geocoderResponse.getResults();
        Location location = new Location();

        if(results.size () == 0)    {
            LOGGER.error("No Answer");
            return location;

        }

        if(results.size()>1)    {
            LOGGER.warn("we can not be sure of the address we take the first response");
        }

        GeocoderResult result = results.get(0);
        BigDecimal lat = result.getGeometry().getLocation().getLat();
        BigDecimal lng = result.getGeometry().getLocation().getLng();
        location.setLat(lat.doubleValue());
        location.setLng(lng.doubleValue());
        return location;
    }
}
