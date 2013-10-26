'use strict';

/* User Controllers */

var module = angular.module('windmeal.controllers');

module.controller('menuSettingsController', function ($scope, $routeParams, $location, FoodProviders, Menus) {
	$scope.fp = {};
	$scope.menu = {};
	$scope.menu.meals = [];

	if ($routeParams.id != undefined) {
		$scope.new = false;
		FoodProviders.get(
			{id: $routeParams.id},
			{},
			function (data) {
				$scope.fp = data;
				if ($scope.fp.menuId) {
					Menus.get(
						{id: $scope.fp.menuId},
						{},
						function (data) {
							$scope.menu = data;
							console.log("Menu:", data);
							console.log("Meals:", $scope.menu.meals);
						},
						function (error) {
							console.log("Failed to fetch Menu: Error", error.status);
						}
					);
				}
			},
			function (error) {
				console.log("Failed to fetch Provider: Error", error.status);
			}
		);
	}

	$scope.submitMenu = function () {
		console.log($scope.menu);
		if ($scope.fp.menuId) {
			Menus.update(
				{id: $scope.menu._id},
				$scope.menu,
				function (data) {
					console.log("Menu :");
					console.log(data);
				},
				function (error) {
					console.log("Error " + error.status);
				}
			);
		}
		else {
			Menus.save(
				{},
				$scope.menu,
				function (data) {
					console.log("Menu :");
					console.log(data);
					$location.path("/providers/" + $scope.user._id + "/settings");
				},
				function (error) {
					console.log("Error " + error.status);
				}
			);
		}
	}

	$scope.addMenuItem = function () {
		$scope.menu.meals[$scope.menu.meals.length] = {};
		console.log($scope.menu);
	}

	$scope.deleteMenuItem = function (index) {
		$scope.menu.meals.splice(index, 1);
	}
});
