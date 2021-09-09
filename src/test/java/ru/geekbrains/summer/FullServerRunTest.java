package ru.geekbrains.summer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class FullServerRunTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void fullRestTest() {

//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

        Page<?> products = (Page<?>) restTemplate.getForObject("/api/v1/products", List.class);
        assertThat(products).isNotNull();
        assertThat(products).isNotEmpty();
    }
}
