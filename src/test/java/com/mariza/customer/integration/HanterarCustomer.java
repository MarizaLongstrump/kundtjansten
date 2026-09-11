package com.mariza.customer.integration;


import com.mariza.customer.entity.Customer;
import com.mariza.customer.repository.CustomerRepository;
import jakarta.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
//@Transactional
@TestPropertySource(properties = "spring.datasource.url=jdbc:mysql://localhost:3306/kundjanstentest")
@AutoConfigureMockMvc

 class HanterarCustomer {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void postTest() throws Exception {

        postCustomer();

        mockMvc.perform(get("/api/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id")
                        .value(1));

    }



    void postCustomer() throws Exception {
        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"Pipi\"," +
                                "\"lastName\":\"Långstrump\"," +
                                "\"email\":\"pipi@hotmail.com\"," +
                                "\"address\":\"pipigatan,3\"," +
                                "\"phoneNumber\":\"047845123\"," +
                                "\"city\":\"Stockholm\"," +
                                "\"password\":\"pipi123!\"}")
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk());
    }
    @Test
    void putCostomer() throws Exception{
            postCustomer();
            mockMvc.perform(put("/api/customers/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"address\":\"pipigatan,4\"," +
                                    "\"phoneNumber\":\"04788888\"," +
                                    "\"city\":\"Stockholm\"}")
                            .characterEncoding(StandardCharsets.UTF_8))
                    .andExpect(status().isOk());
        }


    @BeforeEach
    void resetDatabase() {
        jdbcTemplate.execute("truncate table customer"); // truncate ta bort rad och
        // återställa auto increment
    }
    }




