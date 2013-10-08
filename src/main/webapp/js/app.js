'use strict';

/* App Module */


angular.module('windmeal.services', ['ngResource']);
angular.module('windmeal.controllers', ['ngResource']);
angular.module('windmeal.filters', ['ngResource']);
angular.module('windmeal.directives', ['ngResource']);

var windmealApp = angular.module('windmealApp', [
	'windmeal.controllers',
	'windmeal.directives',
	'windmeal.filters',
	'windmeal.services'
]);

windmealApp.config(
	['$routeProvider',
		function ($routeProvider) {
			$routeProvider.
				when('/', {templateUrl: 'partials/home.html', controller: 'HomeController'}).

				otherwise({redirectTo: '/'});
		}
	]
);

