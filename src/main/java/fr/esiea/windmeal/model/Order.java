package fr.esiea.windmeal.model;

import org.joda.time.DateTime;

import java.util.Set;

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
public class Order extends Model {

	private String foodProviderId;
	private Set<MealOrder> meals;
	private DateTime orderDate;
	private boolean state;

	public String getFoodProviderId() {
		return foodProviderId;
	}

	public void setFoodProviderId(String foodProviderId) {
		this.foodProviderId = foodProviderId;
	}

	public Set<MealOrder> getMeals() {
		return meals;
	}

	public void setMeals(Set<MealOrder> meals) {
		this.meals = meals;
	}

	public DateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(DateTime orderDate) {
		this.orderDate = orderDate;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;

		Order order = (Order) o;

		if (state != order.state) return false;
		if (!foodProviderId.equals(order.foodProviderId)) return false;
		if (!meals.equals(order.meals)) return false;
		if (!orderDate.equals(order.orderDate)) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + foodProviderId.hashCode();
		result = 31 * result + meals.hashCode();
		result = 31 * result + orderDate.hashCode();
		result = 31 * result + (state ? 1 : 0);
		return result;
	}
}
