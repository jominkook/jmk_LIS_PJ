package com.yk.logistic.domain.address;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.yk.logistic.config.serializer.AddressDeserializer;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@JsonDeserialize(using = AddressDeserializer.class)
public class Address {
    private String street;
    private String city;
    private String zipCode;

    public Address(String street, String city, String zipCode) {
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
    }
    
    @Override
    public String toString() {
        return street + ", " + city + ", " + zipCode;
    }
}