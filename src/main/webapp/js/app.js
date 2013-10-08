'use strict';

/* App Module */

/*var servicesModule = angular.module('esieAddress.services', ['ngResource']);
 var controller = angular.module('esieAddress.controllers',['ngResource'])*/

angular.module('windmeal.services', ['ngResource']);
angular.module('windmeal.controllers', ['ngResource']);
angular.module('windmeal.filters', ['ngResource']);
angular.module('windmeal.directives', ['ngResource']);

var windmealApp = angular.module('windmealApp', ['windmeal.services', 'windmeal.controllers', 'windmeal.filters', 'windmeal.directives']);

var path = "rest";

windmealApp.config(['$routeProvider', '$httpProvider', function ($routeProvider, $httpProvider) {
	$routeProvider.
		/* Related Contacts Pages */
		when('/', {templateUrl: 'partials/home.html', controller: 'HomeCtrl'}).

		otherwise({redirectTo: '/'});

}]);

