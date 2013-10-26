'use strict';

/* Services */

var module = angular.module('windmeal.services');
var path = "rest";

/*
 * Service that retain the foodProvider Result
 */
module.service('FoodProviderResult', function () {
	var data = [];

	return {
		getFoodProviderResult: function () {
			return this.data;
		},
		setFoodProviderResult: function (data) {
			this.data = data;
		}
	};
});

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
	return $resource(path + '/orders/:id/:provider', {}, {
		getFromProvider: {method: 'GET', isArray: true, params: {id: ':id', provider: 'provider'}},
		get: {method: 'GET', params: {id: ':id'}},
		save: {method: 'POST'},
		update: {method: 'PUT', params: {id: ':id'}},
		remove: {method: 'DELETE', params: {id: ':id'}}
	});
});

module.factory('Tags', function ($resource) {
	return $resource(path + '/util/tags', {}, {
		getTags: {method: 'GET'}
	});
});

module.factory('UtilProvider', function ($resource) {
	return $resource(path + '/providers/user/:id', {}, {
		query: {method: 'GET', isArray: true, params: {id: 'id'}}
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

module.factory('Comment', function ($resource) {
	return $resource(path + '/comment/provider/:id', {}, {
		addComment: {method: 'POST'}
	});
});

module.factory('Search', function ($resource) {
	return $resource(path + '/search/providers/:type', {}, {
		search: {method: 'GET', isArray: true, params: {type: 'type'}}
	});
});