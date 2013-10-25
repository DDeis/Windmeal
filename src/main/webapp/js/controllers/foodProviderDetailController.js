'use strict';

var module = angular.module('windmeal.controllers');

module.controller('FoodProviderDetailController', function ($scope, $routeParams, FoodProviders, Menus, Users, Comment) {
    $scope.fp = {};

    $scope.menu = {};

    $scope.averageRating = 1;
    $scope.ratingValue = "";

    $scope.users = {};

    $scope.newComment = {};
    $scope.newComment.rate = 1;


    var getMenu = function(id) {
        Menus.get(
            {id: id},
            {},
            function(data) {
                $scope.menu = data;
                console.log(data);
            },
            function(error) {
                console.log("Error "+error.status);
            }
        );
    }

    var getAverageRating = function() {
        var total = 0;
        for(var i=0; i < $scope.fp.comments.length; i++) {
            total += $scope.fp.comments[i].rate;
        }
        $scope.averageRating = total / $scope.fp.comments.length;
    }

    var getUsers = function() {
        for(var i= 0; i<$scope.fp.comments.length; i++) {
            Users.get(
                {id: $scope.fp.comments[i].userId},
                {},
                function(data) {
                    $scope.users[data._id] = data;
                },
                function(error) {}
            );
        }
    }

    if($routeParams.id != undefined) {
        FoodProviders.get(
            {id: $routeParams.id},
            {},
            function(data) {
                $scope.fp = data;
                getUsers();
                console.log(data);
                getAverageRating();
                getMenu($scope.fp.menuId);
            },
            function(error) {
                console.log("Error "+error.status);
            }
        );
    }

    $scope.submitComment = function() {
        console.log($scope.newComment);
        Comment.addComment({id:$scope.fp._id},$scope.newComment,function() {
                $scope.fp.comments.push($scope.newComment);
            },
            {

            }
        );
    }

});
