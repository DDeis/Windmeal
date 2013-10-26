'use strict';

var module = angular.module('windmeal.controllers');

module.controller('OrderController', function ($scope, $routeParams, FoodProviders, Menus, Orders) {

	$scope.view = 1;

	$scope.fp = {};

	$scope.menu = {};
	$scope.delivery = {};
	$scope.payment = {};

	$scope.payment.card = "xxxx-xxxx-xxxx-xxx";
	$scope.payment.cvv = "123";
	$scope.payment.expiration = "10/2014";

	if($routeParams.id != undefined) {
		FoodProviders.get(
			{id: $routeParams.id},
			{},
			function(data) {
				$scope.fp = data;
				console.log(data);
				getMenu($scope.fp.menuId);
			},
			function(error) {
				console.log("Error while fetching provider:", error.status);
			}
		);
	};

	 function getMenu(id) {
		Menus.get(
			{id: id},
			{},
			function(data) {
				$scope.menu = data;
				console.log(data);
			},
			function(error) {
				console.log("Error while fetching menu:", error.status);
			}
		);
	}


	$scope.addToCart = function(menuItem) {
		if(menuItem.nb == undefined) {
			menuItem.nb = 1;
		}
		else {
			menuItem.nb++;
		}
	};

	$scope.total = function() {
		var total = 0;

		angular.forEach($scope.menu.meals, function(item) {
			if(item.nb != undefined){
				total += item.nb * item.price;
			}
		});

		return total;
	};

	$scope.submitOrder = function() {
		var order = {};
		order.foodProviderId = $routeParams.id;
		order.meals = [];

		for(var i=0; i<$scope.menu.meals.length; i++) {
			if($scope.menu.meals[i].nb) {
				order.meals.push(
					{
						mealId: $scope.menu.meals[i]._id,
						number: $scope.menu.meals[i].nb
					}
				);
			}
		}

		console.log(order);

		Orders.save(
			{},
			order,
			function(data) {

			},
			function(error) {
				console.log("Error while submitting the order : "+error.status);
			}
		);
	}

	$scope.deleteAllItems = function() {
		for(var i=0; i<$scope.menu.meals.length; i++) {
			$scope.menu.meals[i].nb = 0;
		}
	}

	$scope.deleteItem = function(menuItem) {
		menuItem.nb = 0;
	}

	$scope.removeItem = function(menuItem) {
		menuItem.nb--;
	}
});
