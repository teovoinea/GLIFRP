var testApp = angular.module('testApp', [])

testApp.controller('indexController', function ($scope, $http) {
    $scope.message = "This should work?"
    $scope.getInfo = function () {
        console.log('It got here')
        $http({
            'method': 'GET',
            'url': '/Home/GetInfo',
            'dataType': 'json'
        }).then(function successCallBack(response) {
            console.log(JSON.parse(response.data));
            $scope.response = JSON.parse(response.data);
        }, function errorCallBack(err) {
            console.log(err);
        });
    };
});
