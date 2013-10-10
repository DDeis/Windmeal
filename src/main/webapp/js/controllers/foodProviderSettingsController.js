'use strict';

/* User Controllers */

var module = angular.module('windmeal.controllers');

module.controller('FoodProviderSettingsController', function ($scope, $routeParams, FoodProviders) {
	$scope.fp = {};

	if($routeParams.id != undefined) {
		FoodProviders.get(
			{id: $routeParams.id},
			{},
			function(data) {
				$scope.fp = data;
			},
			function(error) {
				console.log("Error "+error.status);
			}
		);
	}
});
