package com.hepsijet.kargo2.Xdock.Models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class City {
    private String cityName;
    private String zipCode;
}
