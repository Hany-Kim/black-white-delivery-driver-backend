package com.sparta.blackwhitedeliverydriver.controller;

import com.sparta.blackwhitedeliverydriver.dto.BasketListGetResponseDto;
import com.sparta.blackwhitedeliverydriver.dto.BasketRemoveRequestDto;
import com.sparta.blackwhitedeliverydriver.dto.BasketAddRequestDto;
import com.sparta.blackwhitedeliverydriver.dto.BasketResponseDto;
import com.sparta.blackwhitedeliverydriver.service.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/basket")
@RequiredArgsConstructor
public class BasketController {

    private final BasketService basketService;
    @PostMapping
    public BasketResponseDto addProductToBasket(BasketAddRequestDto request){
        return basketService.addProductToBasket(request);
    }

    @PutMapping
    public BasketResponseDto removeProductFromBasket(BasketRemoveRequestDto request){
        return basketService.removeProductFromBasket(request);
    }

    @GetMapping
    public BasketListGetResponseDto getBaskets(){
        return basketService.getBaskets(1L);
    }
}
