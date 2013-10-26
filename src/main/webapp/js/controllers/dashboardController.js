'use strict';

/* User Controllers */

var module = angular.module('windmeal.controllers');

module.controller('DashboardController', function ($scope, $routeParams, FoodProviders, Orders, Menus) {

	$scope.orders = [];
	$scope.fp = {};
	$scope.menu = {};

	if ($routeParams.id != undefined) {
		fetchProviderData($routeParams.id);
		fetchOrders($routeParams.id);
	}

	function fetchProviderData(id) {
		FoodProviders.get(
			{id: id},
			{},
			function (data) {
				console.log("Fetched Provider: ", data);
				$scope.fp = data;
				fetchMenu($scope.fp.menuId);
			},
			function (error) {
				console.log("Error while fetching provider:", error.status);
			}
		);
	}

	function fetchOrders(id) {
		Orders.getFromProvider(
			{id: id},
			{},
			function (data) {
				$scope.orders = data;
				console.log("Fetched Orders:", data);
			},
			function (error) {

			}
		);
	}

	function fetchMenu(id) {
		Menus.get(
			{id: id},
			{},
			function (data) {
				for (var i = 0; i < data.meals.length; i++) {
					$scope.menu[data.meals[i]._id] = data.meals[i]
				}
				console.log("Fetched Menu: ", $scope.menu);
			},
			function (error) {
				console.log("Error while fetching menu:", error.status);
			}
		);
	}

	$scope.getTotalPrice = function (i) {
		var totalPrice = 0;

		for (var j = 0; j < $scope.orders[i].meals.length; j++) {
			if ($scope.menu[$scope.orders[i].meals[j].mealId]) {
				totalPrice += $scope.orders[i].meals[j].number * $scope.menu[$scope.orders[i].meals[j].mealId].price;
			}
		}

		return totalPrice;
	}

	$scope.getTotalMenuItems = function (i) {
		var totalItems = 0;

		for (var j = 0; j < $scope.orders[i].meals.length; j++) {
			totalItems += $scope.orders[i].meals[j].number;
		}

		return totalItems;
	}

	$scope.changeState = function(order) {
		order.state = true;

		Orders.update(
			{id: order._id},
			order,
			function () {
				console.log("State changed successfully");
			},
			function (error) {
				console.log("Failed to change state", error.status);
			}
		);
	}
});
