'use strict';

/* User Controllers */

var module = angular.module('windmeal.controllers');

module.controller('AppController', function ($rootScope, $scope, $route, $location, Login, Logout) {

	$scope.login = {};
	$scope.user = {};

	//Permet de verifier après un rafraichissement si on est loggué
	ping();

	$scope.opts = {
		backdropFade: true,
		dialogFade: true
	};

	/**
	 * Ping server to figure out if user is already logged in.
	 */
	function ping() {
		//Default
		$scope.logged = false;

		Login.get(
			{}, // Params
			{}, // Post Data
			function (data, status) {
				$scope.user = data;
				console.log("user : ");
				console.log(data);
				$scope.$broadcast('event:loginConfirmed');
			}, function () {
				console.info("404 is a proof that the user is not authenticated");
			}

		);
	}

	/*
	 * Called when the authentication form is field
	 */
	$scope.connect = function () {
		$scope.$broadcast('event:loginRequest');
	};

	$scope.openAuthModal = function () {
		console.info("In open AuthModal");
		$scope.shouldOpenAuthModal = true;
		$('#authenticationModal').modal('show');
	};

	$scope.closeAuthModal = function () {
		console.info("In close AuthModal");
		$('#authenticationModal').modal('hide');
	};
	/*
	 * Permit to broadcast that a login  is required
	 */
	$scope.loginRequired = function () {
		console.info("Send event login request");
		$scope.$broadcast('event:loginRequired');
	};

	/*
	 * Set data when an user is connected
	 */
	function connected() {
		console.log("the user is connected");
		$scope.logged = true;
	}

	/**
	 * On 'logoutRequest' invoke logout on the server and broadcast 'event:loginRequired'.
	 */
	$scope.logout = function () {
		Logout.get({}, function () {
				$scope.logged = false;
				console.info("logout success");
			}
			, function () {
				console.info("logout error");
			})
	};

	/**
	 * Holds all the requests which failed due to 401 response.
	 */
	$scope.requests401 = [];
	$scope.$on('event:loginRequired', function () {
		if ($scope.shouldOpenAuthModal) {
			$('#loginInfo').show();
		}

		else {
			$scope.openAuthModal();
		}
	});

	/**
	 * On 'event:loginRequest' Ask the server with scope.login
	 */
	$scope.$on('event:loginRequest', function (event) {
		Login.save($scope.login, function (data) {
			if (data != null)
				$scope.$broadcast('event:loginConfirmed');
            //Here u can catch the data from the current user
		}, function (error) {
			console.log("Login error, if you are here the interceptor doesn't work");
		});
		$scope.login = {};

	});

	/**
	 * On 'event:loginConfirmed', resend all the 401 requests.
	 */
	$scope.$on('event:loginConfirmed', function () {
		console.info("in login confirmed");
		$scope.closeAuthModal();
		connected();
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

		//$scope.$broadcast('updateContactList');
		$route.reload();
		//Handle the fact the the side bar is not in this scope
	});

	/**
	 * On 'event:accessForbidden' pop up a modal
	 */
	$scope.$on('event:accessForbidden', function (event, Login) {
		$scope.shouldOpenAccessForbiddenModal = true;
		console.info("accessForbidden in appController")
	});

});
