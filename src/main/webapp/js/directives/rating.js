var module = angular.module('windmeal.directives');

module.directive('rating', function () {
	return {
		restrict: 'A',
		template: '<ul class="rating">' +
			'<li ng-repeat="star in stars" ng-click="toggle($index)">' +
			'<a class="glyphicon" ng-class="star"></a>' +
			'</li>' +
			'</ul>',
		scope: {
			ratingValue: '=',
			readonly: '@',
			onRatingSelected: '&'
		},
		link: function (scope, elem, attrs) {

			var updateStars = function () {
				scope.stars = [];
				for (var i = 0; i < 5; i++) {
					if(i < scope.ratingValue) {
						scope.stars.push("glyphicon-star");
					}
					else {
						scope.stars.push("glyphicon-star-empty");
					}
				}
			};

			scope.toggle = function (index) {
				if (scope.readonly && scope.readonly === 'true') {
					return;
				}
				scope.ratingValue = index + 1;
				scope.onRatingSelected({rating: index + 1});
			};

			scope.$watch('ratingValue', function (oldVal, newVal) {
				if (newVal) {
					updateStars();
				}
			});

		}
	};
});