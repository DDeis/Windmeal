'use strict';

/* User Controllers */

var module = angular.module('windmeal.controllers');

module.controller('FoodProviderListController', function ($scope, FoodProviders, FoodProviderResult, Tags, Search) {
    $scope.fps = [];
    if($scope.user != undefined && $scope.user.address != undefined)    {
        $scope.address = $scope.user.address.street
            + " " + $scope.user.address.postalCode
            + " " + $scope.user.address.city;
    }
    $scope.request = "";

    var coordinates = {};

    if (FoodProviderResult.getFoodProviderResult()) {
        $scope.fps = FoodProviderResult.getFoodProviderResult();
    }
    else {
        $scope.fps = FoodProviders.query();
    }

    if (FoodProviderResult.getQuery()) {
        $scope.request = FoodProviderResult.getQuery();
    }

    $scope.allTags = Tags.getTags();

    $scope.searchAddress = function () {
        console.log("Fetching coordinates for:", $scope.address);
        var geocoder = new google.maps.Geocoder();

        geocoder.geocode(
            {address: $scope.address},
            function (results, status) {
                if (status == google.maps.GeocoderStatus.OK) {
                    coordinates.lat = results[0].geometry.location.lb;
                    coordinates.lng = results[0].geometry.location.mb;

                    console.log("Coordinates:", coordinates);

                    var params =
                    {
                        type: "location",
                        longitude: coordinates.lat,
                        latitude: coordinates.lat
                    };
                    performSearch(params);
                } else {
                    console.log("Geocode was not successful for the following reason:", status);
                }
            }
        );

    }

    $scope.search = function () {
        var params = {type: "request", request: $scope.request};
        performSearch(params);
    }

    function performSearch(params) {
        console.log("Searching:", params);
        Search.search(
            params,
            {},
            function (data) {
                console.log("Found results:", data);
                $scope.fps = data;
            },
            function (error) {
                console.log("Encountered error while searching:", error.status);
            }
        );
    }

});
