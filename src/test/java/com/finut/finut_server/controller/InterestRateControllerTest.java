package com.finut.finut_server.controller;

import com.finut.finut_server.service.InterestRateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class InterestRateControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @MockBean
    private InterestRateService interestRateService;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testGetInterestRates() throws Exception {
        String expectedResponse = "3.5";

        when(interestRateService.getInterestRatesToady()).thenReturn(expectedResponse);

        mockMvc.perform(get("/interest-rate/today"))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse));

        verify(interestRateService, times(1)).getInterestRatesToady();
    }



}
