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

	fetchProviderData();

	function fetchProviderData() {
		if ($routeParams.id != undefined) {
			FoodProviders.get(
				{id: $routeParams.id},
				{},
				function (data) {
					console.log("Fetched Provider: ", data);
					$scope.fp = data;
					fetchCommentUsers();
					getAverageRating();
					fetchMenu($scope.fp.menuId);
				},
				function (error) {
					console.log("Error while fetching provider:", error.status);
				}
			);
		}
	}

	function fetchMenu(id) {
		Menus.get(
			{id: id},
			{},
			function (data) {
				$scope.menu = data;
				console.log("Fetched Menu: ", data);
			},
			function (error) {
				console.log("Error while fetching menu:", error.status);
			}
		);
	}

	function getAverageRating() {
		var total = 0;
		for (var i = 0; i < $scope.fp.comments.length; i++) {
			if ($scope.fp.comments[i].rate) {
				total += $scope.fp.comments[i].rate;
			}
		}
		$scope.averageRating = Math.round(total / $scope.fp.comments.length);
	}

	function fetchCommentUsers() {
		for (var i = 0; i < $scope.fp.comments.length; i++) {
			if ($scope.fp.comments[i].userId) {
				Users.get(
					{id: $scope.fp.comments[i].userId},
					{},
					function (data) {
						$scope.users[data._id] = data;
					},
					function (error) {
						console.log("Error while fetching users:",  error.status);
					}
				);
			}
		}
	}

	$scope.submitComment = function () {
		$scope.newComment.userId = $scope.user._id;
		console.log("Posting comment: ", $scope.newComment);
		if ($scope.newComment && $scope.newComment.userId && $scope.newComment.text) {
			Comment.addComment(
				{id: $scope.fp._id},
				$scope.newComment,
				function () {
					fetchProviderData();
					$scope.newComment = {};
					$scope.newComment.rate = 1;
				},
				function (error) {
					console.log("Error while posting comment:", error.status);
				}
			);
		}
	};

});
