package fr.esiea.windmeal.test.integration.service.crud;

import fr.esiea.windmeal.model.*;
import fr.esiea.windmeal.service.crud.ICrudService;
import fr.esiea.windmeal.service.exception.InvalidIdException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;

import static junit.framework.Assert.assertEquals;

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
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations = {
				"classpath*:spring/application-context.xml",
				"classpath*:spring/mock-test-model.xml"
		})
public class CrudServiceTest {

	@Autowired
	private ApplicationContext appCont;
	@Autowired
	private User user;
	@Autowired
	private FoodProvider provider;
	@Autowired
	private Order order;
	@Autowired
	private Menu menu;

	@Before
	public void setUp() throws Exception {
		user.generateId();
		provider.generateId();
		order.generateId();
		menu.generateId();

		Meal meal = new Meal();
		meal.generateId();
		meal.setDescription("desc");

		HashSet<MealOrder> mealOrders = new HashSet<MealOrder>();

		MealOrder mealOrder = new MealOrder();
		meal.generateId();
		mealOrder.setNumber(3);
		mealOrders.add(mealOrder);

		order.setMeals(mealOrders);
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test(expected = InvalidIdException.class)
	public void menuCrudServiceTest() throws Exception {

		ICrudService<Menu> dao = (ICrudService<Menu>) appCont.getBean("menuCrudService");

		//Insert a new menu
		dao.insert(menu);
		Menu menuToCheck = dao.getOne(menu.getId());
		assertEquals(menu, menuToCheck);
		menu.setMeals(null);
		//Update the menu
		dao.save(menu);
		assertEquals(menu, menuToCheck);
		//Remove the menu
		dao.remove(menu.getId());
		menuToCheck = dao.getOne(menu.getId());
		assertEquals(null, menuToCheck);
	}

	@Test(expected = InvalidIdException.class)
	public void providerCrudServiceTest() throws Exception {

		ICrudService<FoodProvider> dao = (ICrudService<FoodProvider>) appCont.getBean("providerCrudService");

		//Insert a new FoodProvider
		dao.insert(provider);
		FoodProvider providerToCheck = dao.getOne(provider.getId());
		assertEquals(provider, providerToCheck);
		provider.setEmail(null);
		//Update the provider
		dao.save(provider);
		providerToCheck = dao.getOne(provider.getId());
		assertEquals(provider, providerToCheck);
		//Remove the provider
		dao.remove(provider.getId());
		providerToCheck = dao.getOne(provider.getId());
		assertEquals(null, providerToCheck);
	}

	@Test(expected = InvalidIdException.class)
	public void orderCrudServiceTest() throws Exception {

		ICrudService<Order> dao = (ICrudService<Order>) appCont.getBean("orderCrudService");
		System.out.println(order);
		//Insert a new order
		dao.insert(order);
		System.out.println(order);
		Order orderToCheck = dao.getOne(order.getId());
		assertEquals(order, orderToCheck);
		order.setMeals(new HashSet<MealOrder>());
		//Update the order
		dao.save(order);
		orderToCheck = dao.getOne(order.getId());
		assertEquals(order, orderToCheck);
		//Remove the order
		dao.remove(order.getId());
		orderToCheck = dao.getOne(order.getId());
		assertEquals(null, orderToCheck);
	}

	@Test(expected = InvalidIdException.class)
	public void userCrudServiceTest() throws Exception {

		ICrudService<User> dao = (ICrudService<User>) appCont.getBean("userCrudService");

		//Insert a new user
		dao.insert(user);
		User userToCheck = dao.getOne(user.getId());
		assertEquals(user, userToCheck);
		user.setEmail(null);
		//Update the user
		dao.save(user);
		userToCheck = dao.getOne(user.getId());
		assertEquals(user, userToCheck);
		//Remove the user
		dao.remove(user.getId());
		userToCheck = dao.getOne(user.getId());
	}
}
