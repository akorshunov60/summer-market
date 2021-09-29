package ru.geekbrains.summer;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.StringUtils;
import ru.geekbrains.summer.model.User;
import ru.geekbrains.summer.services.OrderService;
import ru.geekbrains.summer.services.UserService;

import java.util.Optional;

@AutoConfigureMockMvc
@FieldDefaults(level = AccessLevel.PRIVATE)
@SpringBootTest
public class OrderControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    OrderService orderService;

    @MockBean
    UserService userService;

    private final static String CLIENT = "Alex";
    private static User user;

    @BeforeAll
    public static void init() {

        user = new User();
        user.setId(1L);
        user.setEmail("alex@mail.ru");
        user.setUsername(CLIENT);
        user.setPassword("999");
    }

    @Test
    @WithMockUser(username = CLIENT, authorities = "USER")
    public void createOrder() throws Exception {

        String address = "Большая Андроньевская, 20";
        String phone = "9(999)999-99-99";
        Mockito.when(userService.findByUsername(CLIENT))
                .thenReturn(Optional.of(user));
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .param("address", address)
                .param("phone", phone)
        ).andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(orderService).createOrder(user, address, phone);

    }

    @ParameterizedTest
    @CsvSource({
            "'', '9(999)999-99-99'",
            "'Большая Андроньевская, 20', ''",
            "'', ''"
    })
    @WithMockUser(username = CLIENT, authorities = "USER")
    public void createOrder_badRequest(String address, String phone) throws Exception {

        String expectedEmptyAddressError = "Field 'address' can't be null";
        String expectedEmptyPhoneError = "Field 'phone' can't be null";
        Mockito.when(userService.findByUsername(CLIENT)).thenReturn(Optional.of(user));
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .param("address", address)
                .param("phone", phone))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
//        if (!StringUtils.hasText(address)) {
//            resultActions.andExpect(MockMvcResultMatchers.jsonPath("messages.[0]", is(expectedEmptyAddressError)));
//            if (!StringUtils.hasText(phone)) {
//                resultActions.andExpect(MockMvcResultMatchers.jsonPath("messages.[1]", is(expectedEmptyPhoneError)));
//            }
//        } else {
//            resultActions.andExpect(MockMvcResultMatchers.jsonPath("messages.[0]", is(expectedEmptyPhoneError)));
//        }
    }
}
