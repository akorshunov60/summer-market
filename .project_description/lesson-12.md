# Урок 12. homeEx-12

# Домашнее задание:

 1. Добавьте к заказу подробный адрес (сущность), "по требованиям PayPal".
 2. Добавьте к заказам статусы и изменения этих статусов в зависимости от PayPal callback'ов.
 3. Не надо давать возможность оплачивать оплаченный заказ.
 4. Попробуйте защитить back/front от неправильных действий пользователя
 (пользователь сможет посмотреть/оплатить не свой заказ,
 попытаться оплатить оплаченный заказ в обход фронтовой кнопки "оплатить").

Что сделано:
   - PayPal сервис обновлен и работает корректно;
   - Добавил на фронт два контроллера order_confirmation.js и create_invoice.js
      для подтверждения заказа и формирования и оплаты инвойса;
   - Добавил в ордер поле status. При формировании нового заказа ему присваивается статус "подтвержден".
      Изменения статусов пока не успел доделать.
