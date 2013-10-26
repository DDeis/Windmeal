'use strict';

/* User Controllers */

var module = angular.module('windmeal.controllers');

module.controller('AppController', function ($rootScope, $scope, $route, $location, Login, Logout) {

	$scope.login = {};
	$scope.user = {};

	$scope.requests401 = [];

	$('#loginInfo').hide();

	// Permet de verifier après un rafraichissement si on est loggué
	ping();

	/**
	 * Ping server to figure out if user is already logged in.
	 */
	function ping() {
		//Default

		Login.get(
			{}, // Post Data
			function (data) {
				$scope.user = data;
				console.log("User :", data);
				$scope.$broadcast('event:loginConfirmed');
			}, function (error) {
				console.info("Error " + error.status);
			}
		);
	}

	/**
	 * Set data when an user is connected
	 */
	$scope.fetchUserData = function () {

		Login.get(
			{}, // Post Data
			function (data) {
				$scope.user = data;
				$scope.logged = true;
				console.log("Fetched user: ", data);
			}, function (error) {
				console.log("Login failed : Error " + error.status);
				$scope.logged = false;
			}
		);
	};


	/**
	 * On 'event:loginConfirmed', resend all the 401 requests.
	 */
	$scope.$on('event:loginConfirmed', function () {
		console.info("in login confirmed");
		$('#authenticationModal').modal('hide');
		console.log("User is now connected");
		$scope.fetchUserData();
		var i, requests = $scope.requests401;
		console.info("request length " + requests.length);
		for (i = 0; i < requests.length; i++) {
			retry(requests[i]);
		}
		$scope.requests401 = [];

		function retry(req) {
			$http(req.config).then(function (response) {
				req.deferred.resolve(response);
			});
		}

		$route.reload();
	});

	/**
	 * On 'logoutRequest' invoke logout on the server and broadcast 'event:loginRequired'.
	 */
	$scope.logout = function () {
		Logout.get(
			{},
			function () {
				$scope.logged = false;
				console.info("Logout success");
			},
			function () {
				console.info("Logout error");
			}
		);
	};

	/**
	 * Called when the authentication form is field
	 */
	$scope.connect = function () {
		$scope.$broadcast('event:loginRequest');
	};

	/**
	 * On 'event:loginRequest' Ask the server with scope.login
	 */
	$scope.$on('event:loginRequest', function (event) {
		console.log("Login requested");
		Login.save($scope.login, function (data) {
			if (data != null)
				$scope.$broadcast('event:loginConfirmed');
		}, function (error) {
			console.log("Login error, if you are here the interceptor doesn't work");
		});
		$scope.login = {};
	});

	/**
	 * Permit to broadcast that a login  is required
	 */
	$scope.loginRequired = function () {
		console.info("Send event login request");
		$scope.$broadcast('event:loginRequired');
	};

	/**
	 * Holds all the requests which failed due to 401 response.
	 */
	$scope.$on('event:loginRequired', function () {
		if ($('#authenticationModal').is(":visible")) {
			$('#loginInfo').show();
		}
		else {
			$('#authenticationModal').modal('show');
		}
	});


	/**
	 * On 'event:accessForbidden' pop up a modal
	 */
	$scope.$on('event:accessForbidden', function (event) {
		console.info("accessForbidden in appController");
	});

	$scope.$on('$locationChangeSuccess', function (event, currentLocation, previousLocation) {
		var url = "http://localhost:8080/windmeal/#";
		$scope.previousRoute = previousLocation.substring(url.length, previousLocation.length);
	});

});
