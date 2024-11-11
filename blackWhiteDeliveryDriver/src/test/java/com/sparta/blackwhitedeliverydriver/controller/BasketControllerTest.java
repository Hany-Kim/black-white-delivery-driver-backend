package com.sparta.blackwhitedeliverydriver.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.blackwhitedeliverydriver.dto.BasketGetResponseDto;
import com.sparta.blackwhitedeliverydriver.dto.BasketAddRequestDto;
import com.sparta.blackwhitedeliverydriver.dto.BasketResponseDto;
import com.sparta.blackwhitedeliverydriver.dto.BasketUpdateRequestDto;
import com.sparta.blackwhitedeliverydriver.service.BasketService;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(BasketController.class)
class BasketControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    BasketService basketService;

    @Autowired
    ObjectMapper mapper;

    private static final String BASE_URL = "/api/v1";

    @Test
    @DisplayName("장바구니 담기")
    void addProductToBasket() throws Exception {
        //given
        String productId = "f47ac10b-58cc-4372-a567-0e02b2c3d479";
        String basketId = "e623f3c2-4b79-4f3a-b876-9d1b5d47a283";
        int quantity = 2;

        //when
        when(basketService.addProductToBasket(any())).thenReturn(BasketResponseDto.builder()
                .basketId(UUID.fromString(basketId))
                .build());

        //then
        String body = mapper.writeValueAsString(BasketAddRequestDto.builder()
                .productId(UUID.fromString(productId))
                .quantity(quantity)
                .build());
        mvc.perform(post(BASE_URL + "/basket")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.basketId").exists());
    }

    @Test
    @DisplayName("장바구니 빼기")
    void removeProductFromBasket_success() throws Exception {
        //given
        String basketId = "e623f3c2-4b79-4f3a-b876-9d1b5d47a283";

        //when
        when(basketService.removeProductFromBasket(any())).thenReturn(BasketResponseDto.builder()
                .basketId(UUID.fromString(basketId))
                .build());

        //then
        mvc.perform(delete(BASE_URL + "/basket/%s", basketId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.basketId").exists());
    }

    @Test
    @DisplayName("장바구니 빼기 실패 : 파라미터가 없는 경우")
    void removeProductFromBasket_fail() throws Exception {
        //given
        String basketId = "e623f3c2-4b79-4f3a-b876-9d1b5d47a283";

        //when
        when(basketService.removeProductFromBasket(any())).thenReturn(BasketResponseDto.builder()
                .basketId(UUID.fromString(basketId))
                .build());

        //then
        mvc.perform(delete(BASE_URL + "/basket")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("장바구니 조회")
    void getBaskets() throws Exception {
        //given
        String basketId = "e623f3c2-4b79-4f3a-b876-9d1b5d47a283";
        String productId = "f47ac10b-58cc-4372-a567-0e02b2c3d479";
        Integer quantity = 2;
        //when
        when(basketService.getBaskets(any())).thenReturn(List.of(BasketGetResponseDto.builder()
                .basketId(UUID.fromString(basketId))
                .productId(UUID.fromString(productId))
                .quantity(quantity)
                .build()));

        //then
        mvc.perform(get(BASE_URL + "/basket"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("장바구니 수정")
    void updateBasket() throws Exception {
        //given
        String basketId = "e623f3c2-4b79-4f3a-b876-9d1b5d47a283";
        int quantity = 2;

        //when
        when(basketService.updateBasket(any())).thenReturn(BasketResponseDto.builder()
                .basketId(UUID.fromString(basketId))
                .build());

        //then
        String body = mapper.writeValueAsString(BasketUpdateRequestDto.builder()
                .basketId(UUID.fromString(basketId))
                .quantity(quantity)
                .build());

        mvc.perform(put(BASE_URL + "/basket")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.basketId").exists());
    }
}