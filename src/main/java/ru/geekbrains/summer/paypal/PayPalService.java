package ru.geekbrains.summer.paypal;

import com.paypal.orders.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.summer.exceptions.ResourceNotFoundException;
import ru.geekbrains.summer.services.OrderService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PayPalService {

    private final OrderService orderService;

    @Transactional
    public OrderRequest createOrderRequest(Long orderId) {

        ru.geekbrains.summer.model.Order order = orderService
                .findOrderById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.checkoutPaymentIntent("CAPTURE");

        ApplicationContext applicationContext = new ApplicationContext()
                .brandName("Summer Market")
                .landingPage("BILLING")
                .shippingPreference("SET_PROVIDED_ADDRESS");
        orderRequest.applicationContext(applicationContext);

        List<PurchaseUnitRequest> purchaseUnitRequests = new ArrayList<>();
        PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest()
                .referenceId(orderId.toString())
                .description("Summer Market Invoice")
                .amountWithBreakdown(new AmountWithBreakdown()
                                .currencyCode("RUB")
                                .value(order.getPrice().toString()).amountBreakdown(
                                new AmountBreakdown().itemTotal(new Money()
                                        .currencyCode("RUB")
                                        .value(order.getPrice().toString())
                                )))
                .items(order.getItems().stream()
                        .map(orderItem -> new Item()
                                .name(orderItem.getProductEntity().getTitle())
                                .unitAmount(new Money().currencyCode("RUB").value(orderItem.getPrice().toString()))
                                .quantity(String.valueOf(orderItem.getQuantity()))).collect(Collectors.toList())
                )
                .shippingDetail(new ShippingDetail()
                        .name(new Name().fullName(order.getUser().getUsername()))
                        .addressPortable(new AddressPortable()
                                        .addressLine1("Lesnaya St")
                                        .addressLine2("Bld. 5")
                                .adminArea2("Togliatti")
                                        .adminArea1("Samara region")
                                        .postalCode("445011")
                                        .countryCode("RU")
                        ));
        purchaseUnitRequests.add(purchaseUnitRequest);
        orderRequest.purchaseUnits(purchaseUnitRequests);
        return orderRequest;
    }
}
