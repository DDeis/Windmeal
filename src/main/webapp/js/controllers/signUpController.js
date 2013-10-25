'use strict';

/* User Controllers */

var module = angular.module('windmeal.controllers');

module.controller('SignUpController', function ($scope, $location, $timeout, Signup) {

	console.log("In signUp");

	$scope.newUser = {};

	$scope.signUp = function () {
		console.log($scope.newUser);
		Signup.save(
			$scope.newUser
			, function (data) {
				console.log(data);
				console.log("Successfully signed up");

				$scope.login.email = $scope.newUser.email;
				$scope.login.password = $scope.newUser.password;

				$scope.$emit('event:loginRequest');

				console.log($scope.previousRoute)
				if ($scope.previousRoute) {
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
	};
});
