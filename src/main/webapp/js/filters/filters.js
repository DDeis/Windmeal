var module = angular.module('windmeal.filters');

module.filter('typeFilter', function () {
	return function (items, options) {

		matches = [];

		angular.forEach(options, function (value, key) {
			if (value) {
				if (key == "all") {
					matches = items;
					return;
				}
				for(var i=0; i < items.length; i++){
					if(items[i].tags) {
						for(var j=0; j < items[i].tags.length; j++){
							if(items[i].tags[j] == key) {
								matches[matches.length] = items[i];
								break;
							}
						}
					}
				}
			}
		});

		return matches;
	};
});

module.filter('tableSort', function() {
	return function(input) {
		if(input == undefined) {
			return;
		}
		return input.sort();
	}
});