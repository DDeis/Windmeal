'use strict';

/* User Controllers */

var module = angular.module('windmeal.controllers');

module.controller('FoodProviderSettingsController', function ($scope, $routeParams, $location, FoodProviders, Tags) {

	$scope.fp = {};
	$scope.fp.address = {};
	$scope.fp.tags = [];

	$scope.allTags = Tags.getTags();
	$scope.tags = {};

	if ($routeParams.id != undefined) {
		FoodProviders.get(
			{id: $routeParams.id},
			{},
			function (data) {
				$scope.fp = data;
				console.log(data);
			},
			function (error) {
				console.log("Error " + error.status);
			}
		);
	}

	$scope.submitInfo = function () {
		console.log($scope.tags);

		angular.forEach($scope.tags, function(value, key) {
			if(value) {
				$scope.fp.tags.push(key);
			}
		});

		if($routeParams.id != undefined) {
			FoodProviders.update(
				{id: $routeParams.id},
				$scope.fp,
				function(data) {
					console.log("Provider successfully updated:", data);
					$location.path("/providers/"+$routeParams.id+"/settings");
				},
				function(error) {
					console.log("Failed to update Provider: Error "+error.status);
				});
		}
		else {
			FoodProviders.save(
				{},
				$scope.fp,
				function(data) {
					console.log("Provider successfully created:", data);
					$location.path("/users/"+$scope.user._id+"/settings")
				},
				function(error) {
					console.log("Failed to create Provider: Error", error.status);
				}
			);
		}
	}


});
