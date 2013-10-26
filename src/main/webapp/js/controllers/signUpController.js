'use strict';

/* User Controllers */

var module = angular.module('windmeal.controllers');

module.controller('SignUpController', function ($scope, $routeParams, $location, $timeout, Users) {

	console.log("In signUp");

	$scope.newUser = {};
	var coordinates = {};

	if ($routeParams.id) {
		Users.get(
			{id: $routeParams.id},
			{},
			function (data) {
				console.log("Fetched User:", data);
				$scope.newUser = data;
			},
			function (error) {
				console.log("Error while fetching user", $routeParams.id, "Error", error.status);
			}
		);
	}

	$scope.signUp = function () {
		console.log($scope.newUser);

		var address = $scope.newUser.address.street
			+ " " + $scope.newUser.address.postalCode
			+ " " + $scope.newUser.address.city;

		console.log("Fetching coordinates for:", address);
		var geocoder = new google.maps.Geocoder();

		geocoder.geocode(
			{address: address},
			function (results, status) {
				if (status == google.maps.GeocoderStatus.OK) {
					coordinates.lat = results[0].geometry.location.lb;
					coordinates.lng = results[0].geometry.location.mb;

					$scope.newUser.address.location = coordinates;
					console.log("Coordinates:", coordinates);

					save();
				} else {
					console.log("Geocode was not successful for the following reason:", status);
				}
			}
		);
	};

	function save() {
		if ($routeParams.id) {
			Users.update(
				$scope.newUser
				, function (data) {
					console.log("Modified user:", data);

					$scope.newUser = {};

					$location.path("/users/"+$routeParams.id+"/settings");
				}
				, function (error) {
					console.log("Error " + error.status);
				}
			);
		}
		else {
			Users.save(
				$scope.newUser
				, function (data) {
					console.log("Successfully signed up");
					console.log("Signed up user:", data);

					$scope.login.email = $scope.newUser.email;
					$scope.login.password = $scope.newUser.password;

					$scope.newUser = {};

					$scope.$emit('event:loginRequest');

					if ($scope.previousRoute && $scope.previousRoute != "/signup") {
						$location.path($scope.previousRoute);
					}
					else {
						$location.path("/");
					}
				}
				, function (error) {
					console.log("Error " + error.status);
				}
			);
		}
	}
});
