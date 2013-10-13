'use strict';

/* User Controllers */

var module = angular.module('windmeal.controllers');

module.controller('FoodProviderDetailController', function ($scope, $routeParams, FoodProviders, Menus) {
	$scope.fp = {};

	$scope.menu = {};

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
	}

});
