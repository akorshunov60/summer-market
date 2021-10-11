angular.module('app').controller('ordersController', function ($scope, $http, $localStorage, $location) {
    const contextPath = 'http://localhost:8189/summer';

    $scope.loadOrders = function () {
        $http({
            url: contextPath + '/api/v1/orders',
            method: 'GET'
        }).then(function (response) {
            $scope.orders = response.data;
            console.log($scope.orders);
        });
    }

    $scope.pay = function (orderId) {
        $location.path('/create_invoice/' + orderId)
    }

    $scope.loadOrders();
});