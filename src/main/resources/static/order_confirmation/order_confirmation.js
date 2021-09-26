angular.module('app').controller('orderConfirmationController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:8189/summer';

    $scope.cartContentRequest = function () {
        $http({
            url: contextPath + '/api/v1/cart',
            method: 'GET',
            params: {
                cartName: $localStorage.guestCartUuid
            }
        }).then(function (response) {
            $scope.cart = response.data;
        });
    };

    $scope.createOrder = function () {
        $http({
            url: contextPath + '/api/v1/orders',
            method: 'POST',
            params: {
                phone: $scope.order_info.phone,
                address: $scope.order_info.address
            }
        }).then(function successCallback(response) {
            alert('Заказ создан');
            $scope.loadCart();
        }, function errorCallback(response) {
            alert(response.data.messages);
        });
    }
});