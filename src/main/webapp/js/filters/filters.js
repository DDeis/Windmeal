var module = angular.module('windmeal.filters');

module.filter('typeFilter', function () {
	return function (items, options) {

		matches = [];

		console.log("items");
		console.log(items);
		console.log("options");
		console.log(options);

		angular.forEach(options, function (value, key) {
			if (value) {
				if (key == "all") {
					console.log("all");
					matches = items;
					return;
				}
				for(var i=0; i < items.length; i++){
					for(var j=0; j < items[i].tags.length; j++){
						if(items[i].tags[j] == key) {
							matches[matches.length] = items[i];
							break;
						}
					}
				}
			}
		});

		return matches;
	};
});