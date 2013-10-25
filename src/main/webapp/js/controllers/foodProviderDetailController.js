'use strict';

var module = angular.module('windmeal.controllers');

module.controller('FoodProviderDetailController', function ($scope, $routeParams, FoodProviders, Menus) {
	$scope.fp = {};

	$scope.menu = {};

	$scope.averageRating = 1;
	$scope.ratingValue = "";

	$scope.newComment = {};
	$scope.newComment.rate = 1;

	var getMenu = function(id) {
		Menus.get(
			{id: id},
			{},
			function(data) {
				$scope.menu = data;
				console.log(data);
			},
			function(error) {
				console.log("Error "+error.status);
			}
		);
	}

	var getAverageRating = function() {
		var total = 0;
		for(var i=0; i < $scope.fp.comments.length; i++) {
			total += $scope.fp.comments[i].rate;
		}
		$scope.averageRating = total / $scope.fp.comments.length;
	}

	if($routeParams.id != undefined) {
		FoodProviders.get(
			{id: $routeParams.id},
			{},
			function(data) {
				$scope.fp = data;
				console.log(data);
				getAverageRating();
				getMenu($scope.fp.menuId);
			},
			function(error) {
				console.log("Error "+error.status);
			}
		);
	}

	$scope.saveRating = function(rating) {
//		$scope.newComment = rating;
		console.log(rating);
	};

	$scope.submitComment = function() {
		console.log($scope.newComment);

	}

});
