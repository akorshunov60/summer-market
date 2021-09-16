package ru.geekbrains.summer;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.geekbrains.summer.utils.JsonUtils;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class FullServerRunTest {

    @Test
    public void fullRestTest() throws JsonProcessingException {

        List<?> products = Collections.singletonList(JsonUtils
                .convertObjectToJson("/api/v1/products", List.class));
        assertThat(products).isNotNull();
        assertThat(products).isNotEmpty();
    }
}
