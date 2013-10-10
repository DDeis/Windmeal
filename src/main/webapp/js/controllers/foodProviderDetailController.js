'use strict';

/* User Controllers */

var module = angular.module('windmeal.controllers');

module.controller('FoodProviderDetailController', function ($scope, $routeParams, FoodProviders) {
	$scope.fp = {};

	if($routeParams.id != undefined) {
		FoodProviders.get(
			{id: $routeParams.id},
			{},
			function(data) {
				$scope.fp = data;
				console.log(data);
			},
			function(error) {
				console.log("Error "+error.status);
			}
		);
	}


});
