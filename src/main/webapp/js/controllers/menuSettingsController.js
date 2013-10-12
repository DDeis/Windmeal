'use strict';

/* User Controllers */

var module = angular.module('windmeal.controllers');

module.controller('menuSettingsController', function ($scope, $routeParams, FoodProviders, Menus) {
	$scope.fp = {};
	$scope.menu = {};
	$scope.menu.meals = [];


	if($routeParams.id != undefined) {
		$scope.new = false;
		FoodProviders.get(
			{id: $routeParams.id},
			{},
			function(data) {
				$scope.fp = data;
				Menus.get(
					{id: $scope.fp.menuId},
					{},
					function(data) {
						$scope.menu = data;
						console.log("Menu :");
						console.log(data);
						console.log("Meals :");
						console.log($scope.menu.meals);
					},
					function(error) {
						console.log("Error "+error.status);
					}
				);
			},
			function(error) {
				console.log("Error "+error.status);
			}
		);
	}

	$scope.submitMenu = function() {
		console.log($scope.menu);
		if($routeParams.id != undefined) {
			Menus.update(
				{id: $scope.menu._id},
				$scope.menu,
				function(data) {
					console.log("Menu :");
					console.log(data);
				},
				function(error) {
					console.log("Error "+error.status);
				}
			);
		}
		else {
			Menus.update(
				{},
				$scope.menu,
				function(data) {
					console.log("Menu :");
					console.log(data);
				},
				function(error) {
					console.log("Error "+error.status);
				}
			);
		}
	}

	$scope.addMenuItem = function() {
		$scope.menu.meals[$scope.menu.meals.length] = {};
		console.log($scope.menu);
	}

	$scope.deleteMenuItem = function(index) {
		$scope.menu.meals.splice(index, 1);
	}
});
