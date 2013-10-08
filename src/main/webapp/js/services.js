'use strict';

/* Services */

/* User services */
var module = angular.module('windmeal.services');
var path = "rest";

module.factory('Login', function ($resource) {
	return $resource(path+'/login/', {}, {
		get: {method: 'GET'},
		save: {method: 'POST'}
	});
});

module.factory('Signup', function ($resource) {
	return $resource(path+'/users/', {}, {
		save: {method: 'POST'}
	});
});

module.factory('Logout', function ($resource) {
	return $resource(path+'/logout/', {}, {
		save: {method: 'POST'}
	});
});