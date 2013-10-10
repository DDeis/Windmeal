'use strict';

/* Services */

/* User services */
var module = angular.module('windmeal.services');
var path = "rest";

module.factory('FoodProviders', function ($resource) {
	return $resource(path + '/providers/:id', {}, {
		query: {method: 'GET', isArray: true},
		get: {method: 'GET', params: {id: ':id'}},
		save: {method: 'POST'},
		update: {method: 'PUT'},
		remove: {method: 'DELETE', params: {id: ':id'}}
	});
});

module.factory('Users', function ($resource) {
	return $resource(path + '/addresses/:id', {}, {
		query: {method: 'GET', isArray: true},
		get: {method: 'GET', params: {id: ':id'}},
		save: {method: 'POST'},
		update: {method: 'PUT', params: {id: ':id'}},
		remove: {method: 'DELETE', params: {id: ':id'}}
	});
});

module.factory('Orders', function ($resource) {
	return $resource(path + '/addresses/:id', {}, {
		query: {method: 'GET', isArray: true},
		get: {method: 'GET', params: {id: ':id'}},
		save: {method: 'POST'},
		update: {method: 'PUT', params: {id: ':id'}},
		remove: {method: 'DELETE', params: {id: ':id'}}
	});
});

module.factory('Login', function ($resource) {
	return $resource(path + '/login/', {}, {
		get: {method: 'GET'},
		save: {method: 'POST'}
	});
});

module.factory('Signup', function ($resource) {
	return $resource(path + '/users/', {}, {
		save: {method: 'POST'}
	});
});

module.factory('Logout', function ($resource) {
	return $resource(path + '/logout/', {}, {
		save: {method: 'POST'}
	});
});