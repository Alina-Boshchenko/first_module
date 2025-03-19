package ru.boshchenko.object_mapper;

import jakarta.validation.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.boshchenko.object_mapper.controller.CustomerRestController;
import ru.boshchenko.object_mapper.model.Customer;
import ru.boshchenko.object_mapper.service.CustomerService;

import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerRestController.class)
public class CustomerRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private Validator validator;

    @Test
    @DisplayName("POST /create - Успешное создание клиента")
    void createCustomer() throws Exception {
        String data = """
                {
                     "first_name": "gazga",
                     "last_name": "fef",
                     "email": "fef@fef.ru",
                     "contact_number": "+79586394587",
                     "test": "frregreferf"
                 }""";
        Customer customer = new Customer();
        customer.setFirstName("gazga");
        customer.setLastName("fef");
        customer.setEmail("fef@fef.ru");
        customer.setContactNumber("+79586394587");
        UUID customerId = UUID.randomUUID();
        customer.setId(customerId);

        Mockito.when(validator.validate(any(Customer.class)))
                .thenReturn(Collections.emptySet());
        Mockito.when(customerService.create(any(Customer.class)))
                .thenReturn(customer);

        mockMvc.perform(post("/api/customer/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(data))
                .andExpect(status().isCreated())
                .andExpect(content().string("\"" + customerId.toString() + "\""));
    }
}
