'use strict';

/* User Controllers */

var module = angular.module('windmeal.controllers');

module.controller('HomeController', function ($scope, $location, Search, FoodProviderResult) {

	$scope.request = "";

	$scope.search = function () {
		console.log("Searching:", $scope.request);
		Search.search(
			{type: "request", request: $scope.request},
			{},
			function (data) {
				FoodProviderResult.setFoodProviderResult(data);
				console.log("Found results:", data);
				$location.path("/providers");
			},
			function (error) {
				console.log("Encountered error while searching:", error.status);
			}
		);
	};

	$scope.near = function () {
		console.log("Searching near:", $scope.user.address);
		if ($scope.user.address && $scope.user.address.location) {
			console.log("Location", $scope.user.address.location);
			Search.search(
				{
					type: "location",
					longitude: $scope.user.address.location.lng,
					latitude: $scope.user.address.location.lat
				},
				$scope.user.address.location,
				function (data) {
					FoodProviderResult.setFoodProviderResult(data);
					console.log("Found results:", data);
					$location.path("/providers");
				},
				function (error) {
					console.log("Encountered error while searching:", error.status);
				}
			);
		}
	};
});
