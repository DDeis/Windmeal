'use strict';

/* User Controllers */

var module = angular.module('windmeal.controllers');

module.controller('HomeController', function ($scope, $location, Search, FoodProviderResult) {

	$scope.request = "";

	$scope.search = function (type) {
		var params = {};
		if (type == "request") {
			if ($scope.request) {
				params = {type: "request", request: $scope.request}
			}
		}
		if (type == "location") {
			if ($scope.user.address && $scope.user.address.location) {
				params = {
					type: "location",
					longitude: $scope.user.address.location.lng,
					latitude: $scope.user.address.location.lat
				}
			}
		}
		console.log("Searching:", params);
		if (params) {
			Search.search(
				params,
				{},
				function (data) {
					FoodProviderResult.setFoodProviderResult(data);
					FoodProviderResult.setQuery($scope.request);
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
