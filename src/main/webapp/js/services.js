'use strict';

/* Services */

/* User services */
var module = angular.module('windmeal.services');

module.factory('Login', function ($resource) {
	return $resource('rest/login/', {}, {
		get: {method: 'GET'},
		save: {method: 'POST'}
	});
});

module.factory('Signup', function ($resource) {
	return $resource('rest/users/', {}, {
		save: {method: 'POST'}
	});
});

module.factory('Logout', function ($resource) {
	return $resource('rest/logout/', {}, {
		save: {method: 'POST'}
	});
});