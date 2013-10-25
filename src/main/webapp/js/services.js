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
		update: {method: 'PUT', params: {id: ':id'}},
		remove: {method: 'DELETE', params: {id: ':id'}}
	});
});

module.factory('Users', function ($resource) {
	return $resource(path + '/users/:id', {}, {
		query: {method: 'GET', isArray: true},
		get: {method: 'GET', params: {id: ':id'}},
		save: {method: 'POST'},
		update: {method: 'PUT', params: {id: ':id'}},
		remove: {method: 'DELETE', params: {id: ':id'}}
	});
});

module.factory('Menus', function ($resource) {
	return $resource(path + '/menus/:id', {}, {
		query: {method: 'GET', isArray: true},
		get: {method: 'GET', params: {id: ':id'}},
		save: {method: 'POST'},
		update: {method: 'PUT', params: {id: ':id'}},
		remove: {method: 'DELETE', params: {id: ':id'}}
	});
});

module.factory('Orders', function ($resource) {
	return $resource(path + '/orders/:id', {}, {
		query: {method: 'GET', isArray: true},
		get: {method: 'GET', params: {id: ':id'}},
		save: {method: 'POST'},
		update: {method: 'PUT', params: {id: ':id'}},
		remove: {method: 'DELETE', params: {id: ':id'}}
	});
});

module.factory('Comments', function ($resource) {
	return $resource(path + '/comments/:id/', {}, {
		query: {method: 'GET', isArray: true},
		get: {method: 'GET', params: {id: ':id'}},
		save: {method: 'POST'},
		update: {method: 'PUT', params: {id: ':id'}},
		remove: {method: 'DELETE', params: {id: ':id'}}
	});
});

module.factory('Tags', function ($resource) {
	return $resource(path + '/util/tags', {}, {
		query: {method: 'GET'}
	});
});

module.factory('UtilProvider', function ($resource) {
	return $resource(path + '/providers/user/:id', {}, {
		query: {method: 'GET', isArray:true, params:{id: 'id'}}
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