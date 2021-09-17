# Урок 1. homeEx-1

## 1.На странице заказов отобразить список и количество продуктов в заказе

1. OrderRepository добавляем метод List<Order> findAllByUser(User user);
   
2. OrderService добавляем метод:
   @Transactional
   public List<OrderDto> findAllDtosByUser(User user) {...}
   
3. cart.js метод createOrder приводим к виду:
   
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
   }, function errorCallback (response) {
   alert(response.data.messages);
   });
   }
   
4. orders.html добавляем колонку для отображения списка продуктов
   и их количества в заказе:
   <td>
   <div ng-repeat="oi in o.items">
   <span>{{oi.productTitle + '*' + oi.quantity}}</span>
   <p></p>
   </div>
   </td>

## 2.Ругаться с backend'а если поля адрес или телефон заказа не заполнены

1. Класс MarketError изменения касаются конструктров для создания массива ошибок,
   отправляемых клиенту.
2. Класс IncorrectFieldFillingException для обработки ошибок
   при неправильном заполнении полей формы запроса.
3. В классе GlobalExceptionHandler создаем метод catchIncorrectFieldFillingException()
   для перехвата exception при неправильном заполнении полей формы запроса.
4. В классе OrderController в методе createOrder(Principal principal,
                                                @RequestParam String address,
                                                @RequestParam String phone)
   прописываем проверку правильности заполнения полей address и phone.
