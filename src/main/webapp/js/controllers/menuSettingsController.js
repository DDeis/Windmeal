'use strict';

/* User Controllers */

var module = angular.module('windmeal.controllers');

module.controller('menuSettingsController', function ($scope, $routeParams, FoodProviders, Menus) {
	$scope.fp = {};
	$scope.menu = [];


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
						console.log(data);
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
	}

	$scope.addMenuItem = function() {
		$scope.menu[$scope.menu.length] = {};
	}

	$scope.deleteMenuItem = function(index) {
		$scope.menu.splice(index, 1);
	}
});
