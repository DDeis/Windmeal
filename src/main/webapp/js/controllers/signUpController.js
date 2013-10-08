'use strict';

/* User Controllers */

var module = angular.module('windmeal.controllers');

module.controller('SignUpController', function ($scope,Signup) {

    console.log("In signUp");

	$scope.user = {};

	$scope.signUp = function () {
        Signup.save($scope.user
            , function(){
                console.log("Successfully signed up");
            }
            , function(error){
                console.log("Error "+error.status);
            }
        );
	};
});
