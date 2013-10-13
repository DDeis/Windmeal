'use strict';

/* User Controllers */

var module = angular.module('windmeal.controllers');

module.controller('SettingsController', function ($scope, $routeParams, UtilProvider) {
	$scope.view = "";

	$scope.providers = [];


	UtilProvider.query(
		{id: $routeParams.id},
		{},
		function(data) {
			console.log(data);
			$scope.providers = data;
		},
		function(error) {
			console.log(error.status);
		}
	);
});
