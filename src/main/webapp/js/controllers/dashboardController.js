'use strict';

/* User Controllers */

var module = angular.module('windmeal.controllers');

module.controller('DashboardController', function ($scope, $routeParams, Orders) {

	$scope.orders = [];

	Orders.getFromProvider(
		{id: $routeParams.id},
		{},
		function(data) {
			$scope.orders = data;
			console.log(data);
		},
		function(error) {

		}
	);
});
