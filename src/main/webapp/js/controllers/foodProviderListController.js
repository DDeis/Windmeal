'use strict';

/* User Controllers */

var module = angular.module('windmeal.controllers');

module.controller('FoodProviderListController', function ($scope, FoodProviders) {
	$scope.fps = [];

	FoodProviders.query(
		{},
		{},
		function(data) {
			$scope.fps = data;
			console.log(data);
		},
		function(error) {
			console.log(error.status);
		}
	);
});
