'use strict';

/* User Controllers */

var module = angular.module('windmeal.controllers');

module.controller('SignUpController', function ($scope,$location,Signup,Login) {

    console.log("In signUp");

	$scope.user = {};

	$scope.signUp = function () {
        Signup.save($scope.user
            , function(){
                console.log("Successfully signed up");
                $location.path("/");
            }
            , function(error){
                console.log("Error "+error.status);
            }
        );
	};
});
