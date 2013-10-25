'use strict';

/* User Controllers */

var module = angular.module('windmeal.controllers');

module.controller('FoodProviderSettingsController', function ($scope, $routeParams, FoodProviders, Tags) {


	$scope.fp = {};
	$scope.fp.address = {};

	$scope.allTags = Tags.getTags();
	$scope.tags = {};



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

	$scope.submitInfo = function() {
		console.log($scope.tags);
		console.log($scope.fp);
//		if($routeParams.id != undefined) {
//			FoodProviders.update(
//				{id: $routeParams.id},
//				$scope.fp,
//				function(data) {
//					console.log(data);
//				},
//				function(error) {
//					console.log("Error "+error.status);
//				});
//		}
//		else {
//			FoodProviders.save(
//				{},
//				$scope.fp,
//				function(data) {
//					console.log(data);
//				},
//				function(error) {
//					console.log("Error "+error.status);
//				});
//		}
	}

});
