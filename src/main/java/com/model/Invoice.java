package com.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Getter
@Setter
public class Invoice {
    Random random = new Random();

    private LocalDateTime created;
    private List<DigitalDevice> digitalDevices;
    private Customer customer;
    private TypeInvoice typeInvoice;

    public Invoice(List<DigitalDevice> digitalDevices, Customer customer, TypeInvoice typeInvoice) {
        this.digitalDevices = digitalDevices;
        this.customer = customer;
        this.typeInvoice = typeInvoice;
        this.created = LocalDateTime.now();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Invoice{");
        sb.append(", created=").append(created);
        sb.append(", digitalDevices=").append(digitalDevices);
        sb.append(", customer=").append(customer);
        sb.append(", typeInvoice=").append(typeInvoice);
        sb.append('}');
        return sb.toString();
    }
}
