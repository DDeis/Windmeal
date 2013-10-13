'use strict';

/* User Controllers */

var module = angular.module('windmeal.controllers');

module.controller('OrderController', function ($scope, $routeParams, FoodProviders, Menus) {
	$scope.fp = {};

	$scope.menu = {};

	$scope.items = [];

	var getMenu = function(id) {
		Menus.get(
			{id: id},
			{},
			function(data) {
				$scope.menu = data;
				console.log(data);
			},
			function(error) {
				console.log("Error "+error.status);
			}
		);
	}

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
				console.log("Error "+error.status);
			}
		);
	};

	$scope.addToCart = function(menuItem) {
//		$scope.items[$scope.items.length] = menuItem;
		if(menuItem.nb == undefined) {
			menuItem.nb = 1;
		}
		else {
			menuItem.nb++;
		}
		console.log($scope.items);
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
});
