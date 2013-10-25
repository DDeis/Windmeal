'use strict';

/* User Controllers */

var module = angular.module('windmeal.controllers');

module.controller('FoodProviderListController', function ($scope, FoodProviders, FoodProviderResult,Tags) {
	$scope.fps = FoodProviderResult.getFoodProviderResult();
	$scope.allTags = Tags.getTags();
});
