'use strict';

/* User Controllers */

var module = angular.module('windmeal.controllers');

module.controller('SettingsController', function ($scope, $routeParams, $location, UtilProvider, FoodProviders, Users) {
	$scope.view = "";

	$scope.providers = [];

	$scope.delete = {};

	fetchProviders();

	function fetchProviders() {
		UtilProvider.query(
			{id: $routeParams.id},
			{},
			function (data) {
				console.log(data);
				$scope.providers = data;
			},
			function (error) {
				console.log(error.status);
			}
		);
	}

	$scope.delete = function (type, id) {
		$("#confirmModal").modal('show');
		$scope.delete.type = type;
		$scope.delete.id = id;
	}

	$scope.confirm = function () {
		$("#confirmModal").modal('hide');
		if ($scope.delete.type == "provider") {
			FoodProviders.remove(
				{id: $scope.delete.id},
				{},
				function () {
					console.log("Provider", $scope.delete.id, "deleted");
					fetchProviders();
				},
				function (error) {
					console.log("Error while deleting provider", $scope.delete.id, "Error", error.status);
				}
			);
		}
		if ($scope.delete.type == "user") {
			Users.remove(
				{id: $scope.delete.id},
				{},
				function () {
					console.log("User", $scope.delete.id, "deleted");
					$scope.logout();
					$location.path("/");
				},
				function (error) {
					console.log("Error while deleting user", $scope.delete.id, "Error", error.status);
				}
			);
		}
		$scope.delete = {};
	}

});
