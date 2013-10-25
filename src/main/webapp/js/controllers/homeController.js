'use strict';

/* User Controllers */

var module = angular.module('windmeal.controllers');

module.controller('HomeController', function ($scope, $location, Search, FoodProviderResult) {

	$scope.request = "";

	$scope.search = function () {
		console.log("In search");
		Search.search({request: $scope.request}
			, function (data) {
				FoodProviderResult.setFoodProviderResult(data);
				console.log(data);
				$location.path("/providers");
				console.log("Successfully searched");
			}
			, function (error) {
				console.log("Error " + error.status);
			}
		);
	};
});
