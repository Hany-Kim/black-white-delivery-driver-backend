package com.sparta.blackwhitedeliverydriver.entity;

import com.sparta.blackwhitedeliverydriver.dto.AddressRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "p_address")
public class Address extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_username")
    private User user;

    @Column(nullable = false)
    private String zipNum;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String district;

    @Column(nullable = false)
    private String streetName;

    @Column(nullable = false)
    private String streetNum;

    @Column(nullable = false)
    private String detailAddr;

    @Builder
    public Address(String zipNum, String city, String district, String streetName,
                   String streetNum, String detailAddr, User user) {
        this.zipNum = zipNum;
        this.city = city;
        this.district = district;
        this.streetName = streetName;
        this.streetNum = streetNum;
        this.detailAddr = detailAddr;
        this.user = user;
    }

    // 정적 팩토리 메서드
    public static Address from(AddressRequestDto dto, User user) {
        return Address.builder()
                .zipNum(dto.getZipNum())
                .city(dto.getCity())
                .district(dto.getDistrict())
                .streetName(dto.getStreetName())
                .streetNum(dto.getStreetNum())
                .detailAddr(dto.getDetailAddr())
                .user(user)
                .build();
    }

    // 필드 값 업데이트를 위한 메서드
    public void update(AddressRequestDto dto) {
        this.zipNum = dto.getZipNum();
        this.city = dto.getCity();
        this.district = dto.getDistrict();
        this.streetName = dto.getStreetName();
        this.streetNum = dto.getStreetNum();
        this.detailAddr = dto.getDetailAddr();
    }
}
