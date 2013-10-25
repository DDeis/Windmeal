'use strict';

/* User Controllers */

var module = angular.module('windmeal.controllers');

module.controller('FoodProviderListController', function ($scope, FoodProviders, FoodProviderResult, Tags) {
	$scope.fps = [];
	if(FoodProviderResult.getFoodProviderResult()) {
		$scope.fps = FoodProviderResult.getFoodProviderResult();
	}
	else {
		$scope.fps = FoodProviders.query();
	}
	$scope.allTags = Tags.getTags();
});
