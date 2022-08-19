package com.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Television extends DigitalDevice {

    private double diagonal;
    private String country;

    public Television(String series, Double diagonal, String screenType, String country, double price) {
        super(series, screenType, price);
        this.diagonal = diagonal;
        this.country = country;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Television{");
        sb.append("id='").append(id).append('\'');
        sb.append(", series='").append(series).append('\'');
        sb.append(", screenType='").append(screenType).append('\'');
        sb.append(", price=").append(price);
        sb.append(", diagonal=").append(diagonal);
        sb.append(", country='").append(country).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
