package com.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public abstract class DigitalDevice {

    protected String id;
    protected String series;
    protected String screenType;
    protected double price;

    protected DigitalDevice(String series, String screenType, double price) {
        this.id = UUID.randomUUID().toString();
        this.series = series;
        this.screenType = screenType;
        this.price = price;
    }

}
