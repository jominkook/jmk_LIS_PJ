package com.yk.logistic.dto.item.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Getter
@Setter
public class UpdateItemReqDto {
    private String title;
    private String street;
    private String city;
    private String zipCode;
    private int price;
    private Long categoryId;
    private Double latitude;
    private Double longitude;
    private String description;
    private List<MultipartFile> images; // 여러 이미지

    public UpdateItemReqDto(String title, String street, String city, String zipCode,
                            int price, Long categoryId,
                            Double latitude, Double longitude, String description,
                            List<MultipartFile> images) {
        this.title = title;
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
        this.price = price;
        this.categoryId = categoryId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
        this.images = images;
    }
}