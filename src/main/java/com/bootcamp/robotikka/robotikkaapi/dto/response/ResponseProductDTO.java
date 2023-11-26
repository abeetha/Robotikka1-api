package com.bootcamp.robotikka.robotikkaapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseProductDTO {
    private String propertyId;
    private String displayName;
    private String description;
    private double unitPrice;
    private int qty;
    private double sellingPrice;
}
