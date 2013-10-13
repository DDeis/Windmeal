'use strict';

/* User Controllers */

var module = angular.module('windmeal.controllers');

module.controller('FoodProviderListController', function ($scope, FoodProviders, Tags) {
	$scope.fps = [];

	$scope.allTags = [];
	$scope.tags = {};

	Tags.query(
		{},
		{},
		function(data) {
			console.log(data);
			$scope.allTags = data;
		},
		function(error) {
			console.log(error);
		}
	);

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
