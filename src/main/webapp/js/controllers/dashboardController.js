'use strict';

/* User Controllers */

var module = angular.module('windmeal.controllers');

module.controller('DashboardController', function ($scope, $routeParams, Orders) {
	Orders.getFromProvider(
		{id: $routeParams.id},
		{},
		function(data) {
			console.log(data);
		},
		function(error) {

		}
	);
});
