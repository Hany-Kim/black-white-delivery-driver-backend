package com.sparta.blackwhitedeliverydriver.service;

import com.sparta.blackwhitedeliverydriver.dto.BasketListGetResponseDto;
import com.sparta.blackwhitedeliverydriver.dto.BasketRemoveRequestDto;
import com.sparta.blackwhitedeliverydriver.dto.BasketAddRequestDto;
import com.sparta.blackwhitedeliverydriver.dto.BasketResponseDto;
import com.sparta.blackwhitedeliverydriver.entity.Basket;
import com.sparta.blackwhitedeliverydriver.repository.BasketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BasketService {

    private final BasketRepository basketRepository;

    public BasketResponseDto addProductToBasket(BasketAddRequestDto request) {
        // 유저가 유효한지
        // 상품이 유효한지
        // 수량이 0개 이상인지 100개 미만인지
        // 같은 지점에서 담은 상품인지
        Basket basket = basketRepository.save(Basket.from(request));
        return BasketResponseDto.from(basket);
    }

    public BasketResponseDto removeProductFromBasket(BasketRemoveRequestDto request) {
        //유저 유효성 검사
        //장바구니 유효성 검사
        Basket basket = basketRepository.findById(request.getBasketId()).orElseThrow(() ->
                new IllegalArgumentException("장바구니가 존재하지 않습니다."));

        basketRepository.delete(basket);
        return BasketResponseDto.from(basket);
    }

    public BasketListGetResponseDto getBaskets (Long userId) {
        return null;
    }
}
