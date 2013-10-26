'use strict';

/* User Controllers */

var module = angular.module('windmeal.controllers');

module.controller('HomeController', function ($scope, $location, Search, FoodProviderResult) {

	$scope.request = "";

	$scope.search = function (type) {
		var params;
		if (type == "request") {
			if ($scope.request) {
				params = {};
				params.type = "request";
				params.request = $scope.request;
			}
		}
		if (type == "location") {
			if ($scope.user.address && $scope.user.address.location) {
				params = {};
				params.type = "location";
				params.longitude = $scope.user.address.location.lng;
				params.latitude = $scope.user.address.location.lat;
			}
		}

		if (params) {
			console.log("Searching:", params);

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
