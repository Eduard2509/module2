package com.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Telephone extends DigitalDevice {

    private String model;

    public Telephone(String series, String model, String screenType, double price) {
        super(series, screenType, price);
        this.model = model;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Telephone{");
        sb.append("id='").append(id).append('\'');
        sb.append(", series='").append(series).append('\'');
        sb.append(", screenType='").append(screenType).append('\'');
        sb.append(", price=").append(price);
        sb.append(", model='").append(model).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
