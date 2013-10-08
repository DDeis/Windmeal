'use strict';

/* User Controllers */

var module = angular.module('windmeal.controllers');

module.controller('GeoController', function ($scope) {

	$scope.address = {};

	$scope.coordinates = {};

	$scope.getCoordinates = function () {
		var geocoder = new google.maps.Geocoder();

		geocoder.geocode(
			$scope.address,
			function (results, status) {
				if (status == google.maps.GeocoderStatus.OK) {
					$scope.coordinates.latitude = results[0].geometry.location.lb;
					$scope.coordinates.longitude = results[0].geometry.location.mb;

					console.log($scope.coordinates);
				} else {
					//TODO handle the error
					alert('Geocode was not successful for the following reason: ' + status);
				}
			}
		);
	}
});
