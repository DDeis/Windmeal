'use strict';

/* App Module */

angular.module('windmeal.services', ['ngResource']);
angular.module('windmeal.controllers', ['ngResource']);
angular.module('windmeal.filters', ['ngResource']);
angular.module('windmeal.directives', ['ngResource']);
angular.module('windmeal.interceptors', ['ngResource']);

var windmealApp = angular.module('windmealApp', [
	'windmeal.controllers',
	'windmeal.directives',
	'windmeal.filters',
	'windmeal.services',
	'windmeal.interceptors'
]);

windmealApp.config(['$routeProvider', '$httpProvider', function ($routeProvider, $httpProvider) {
	$routeProvider.
		when('/', {templateUrl: 'partials/home.html', controller: 'HomeController'}).
		when('/geo', {templateUrl: 'partials/geo.html', controller: 'GeoController'}).
		when('/signup', {templateUrl: 'partials/signup.html', controller: 'SignUpController'}).

		when('/users/:id/settings', {templateUrl: 'partials/settings.html', controller: 'SettingsController'}).

		when('/providers', {templateUrl: 'partials/food-provider-list.html', controller: 'FoodProviderListController'}).
		when('/providers/new', {templateUrl: 'partials/food-provider-settings.html', controller: 'FoodProviderSettingsController'}).
		when('/providers/:id', {templateUrl: 'partials/food-provider-detail.html', controller: 'FoodProviderDetailController'}).
		when('/providers/:id/order', {templateUrl: 'partials/order.html', controller: 'OrderController'}).
		when('/providers/:id/dashboard', {templateUrl: 'partials/dashboard.html', controller: 'DashboardController'}).
		when('/providers/:id/settings/info', {templateUrl: 'partials/food-provider-settings.html', controller: 'FoodProviderSettingsController'}).
		when('/providers/:id/settings/menu', {templateUrl: 'partials/menu-settings.html', controller: 'menuSettingsController'}).

		when('/error', {templateUrl: 'partials/error.html', controller: 'ErrorController'}).
		when('/error/:status', {templateUrl: 'partials/error.html', controller: 'ErrorController'}).

		otherwise({redirectTo: '/'});

	$httpProvider.responseInterceptors.push('interceptor401_403');
}]);

