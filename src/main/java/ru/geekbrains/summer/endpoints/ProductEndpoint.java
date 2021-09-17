package ru.geekbrains.summer.endpoints;

import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.geekbrains.summer.services.ProductService;
import ru.geekbrains.summer.soap.products.GetAllProductsRequest;
import ru.geekbrains.summer.soap.products.GetAllProductsResponse;
import ru.geekbrains.summer.soap.products.GetProductByIdRequest;
import ru.geekbrains.summer.soap.products.GetProductByIdResponse;

@Endpoint
@RequiredArgsConstructor
public class ProductEndpoint {

    private static final String NAMESPACE_URI = "http://www.geekbrains.ru/summer/ws/products";
    private final ProductService prdService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductByIdRequest")
    @ResponsePayload
    public GetProductByIdResponse getProductById(@RequestPayload GetProductByIdRequest request) {
        GetProductByIdResponse response = new GetProductByIdResponse();
        response.setProduct(prdService.getById(request.getId()));
        return response;
    }

    /*
        Пример запроса: POST http://localhost:8189/summer/ws

        <soapenv:Envelope   xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                            xmlns:f="http://www.geekbrains.ru/summer/ws/products">
            <soapenv:Header/>
            <soapenv:Body>
                <f:getAllProductsRequest/>
            </soapenv:Body>
        </soapenv:Envelope>
     */

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllProductsRequest")
    @ResponsePayload
    public GetAllProductsResponse getAllProducts(@RequestPayload GetAllProductsRequest request) {
        GetAllProductsResponse response = new GetAllProductsResponse();
        prdService.getAllProducts().forEach(response.getProducts()::add);
        return response;
    }
}
