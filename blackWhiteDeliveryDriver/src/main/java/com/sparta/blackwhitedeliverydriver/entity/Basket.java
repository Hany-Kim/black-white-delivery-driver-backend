package com.sparta.blackwhitedeliverydriver.entity;

import com.sparta.blackwhitedeliverydriver.dto.BasketResponse;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Basket {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID basketId;
    private Long userId; // 임시 컬럼
    private UUID productId; // 임시 컬럼
    private Integer quantity;

    public static Basket from(BasketResponse requestDto) {
        return Basket.builder()
                .userId(requestDto.getUserId())
                .productId(UUID.fromString(requestDto.getProductId()))
                .quantity(requestDto.getQuantity())
                .build();
    }

}
