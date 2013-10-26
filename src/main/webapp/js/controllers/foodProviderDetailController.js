'use strict';

var module = angular.module('windmeal.controllers');

module.controller('FoodProviderDetailController', function ($scope, $routeParams, FoodProviders, Menus, Users, Comment) {
	$scope.fp = {};

	$scope.menu = {};

	$scope.averageRating = 1;
	$scope.ratingValue = "";

	$scope.users = {};

	$scope.newComment = {};
	$scope.newComment.rate = 1;

	var getMenu = function (id) {
		Menus.get(
			{id: id},
			{},
			function (data) {
				$scope.menu = data;
				console.log(data);
			},
			function (error) {
				console.log("Error " + error.status);
			}
		);
	}

	var getAverageRating = function () {
		var total = 0;
		for (var i = 0; i < $scope.fp.comments.length; i++) {
			if ($scope.fp.comments[i].rate) {
				total += $scope.fp.comments[i].rate;
			}
		}
		$scope.averageRating = Math.round(total / $scope.fp.comments.length);
	}

	var getUsers = function () {
		for (var i = 0; i < $scope.fp.comments.length; i++) {
			if ($scope.fp.comments[i].userId) {
				Users.get(
					{id: $scope.fp.comments[i].userId},
					{},
					function (data) {
						$scope.users[data._id] = data;
					},
					function (error) {
					}
				);
			}
		}
	}

	var loadFoodProvider = function () {
		if ($routeParams.id != undefined) {
			FoodProviders.get(
				{id: $routeParams.id},
				{},
				function (data) {
					console.log(data);
					$scope.fp = data;
					getUsers();
					getAverageRating();
					getMenu($scope.fp.menuId);
				},
				function (error) {
					console.log("Error " + error.status);
				}
			);
		}
	}

	$scope.submitComment = function () {
		$scope.newComment.userId = $scope.user._id;
		console.log($scope.newComment);
		if ($scope.newComment && $scope.newComment.userId && $scope.newComment.text) {
			Comment.addComment(
				{id: $scope.fp._id},
				$scope.newComment,
				function () {
					loadFoodProvider();

					$scope.newComment = {};
					$scope.newComment.rate = 1;
				},
				function () {

				}
			);
		}
	}

	loadFoodProvider();

});
