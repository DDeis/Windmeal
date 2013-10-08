/*
 * Copyright (c) 2013 ESIEA M. Labusquiere D. Déïs
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

var module = angular.module('windmeal.interceptors');

module.factory('interceptor401_403', function ($rootScope, $q) {

	$rootScope.requests401 = [];

	function success(response) {
		return response;
	}

	function error(response) {
		console.log("In the interceptor");
		var status = response.status;
		if (status == 403) {
			console.info("403 detected an event accessForbidden is broadcast");
			$rootScope.$broadcast('event:accessForbidden');
			return;
		}

		if (status == 401) {
			var deferred = $q.defer();
			var req = {
				config: response.config,
				deferred: deferred
			};
			console.log("Pushed in request401 " + req)
			$rootScope.requests401.push(req);
			$rootScope.$broadcast('event:loginRequired');
			return deferred.promise;
		}
		// otherwise
		return $q.reject(response);

	}

	return function (promise) {
		return promise.then(success, error);
	}

});

