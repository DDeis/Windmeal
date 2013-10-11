'use strict';

/* User Controllers */

var module = angular.module('windmeal.controllers');

module.controller('FoodProviderSettingsController', function ($scope, $routeParams, FoodProviders) {
	$scope.fp = {};
	$scope.fp.address = {};

	$scope.new = true;

	if($routeParams.id != undefined) {
		$scope.new = false;
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
