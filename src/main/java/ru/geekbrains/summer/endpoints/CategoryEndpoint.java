package ru.geekbrains.summer.endpoints;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.geekbrains.summer.services.CategoryService;
import ru.geekbrains.summer.soap.categories.GetCategoryByTitleRequest;
import ru.geekbrains.summer.soap.categories.GetCategoryByTitleResponse;

@RequiredArgsConstructor
@Endpoint
public class CategoryEndpoint {

    private static final String NAMESPACE_URI = "http://www.geekbrains.ru/summer/ws/categories";
    private final CategoryService ctgService;

    /*
        Пример запроса: POST http://localhost:8189/summer/ws

        <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
          xmlns:f="http://www.geekbrains.ru/summer/ws/categories">
            <soapenv:Header/>
            <soapenv:Body>
                <f:getCategoryByTitleRequest>
                    <f:title>Food</f:title>
                </f:getCategoryByTitleRequest>
            </soapenv:Body>
        </soapenv:Envelope>
     */

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCategoryByTitleRequest")
    @ResponsePayload
    @Transactional
    public GetCategoryByTitleResponse getGroupByTitle(@RequestPayload GetCategoryByTitleRequest request) {
        GetCategoryByTitleResponse response = new GetCategoryByTitleResponse();
        response.setCategory(ctgService.getByTitle(request.getTitle()));
        return response;
    }
}
