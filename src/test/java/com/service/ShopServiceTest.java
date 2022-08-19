package com.service;

import com.model.*;
import com.storage.StorageInvoices;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class ShopServiceTest {
    private ShopService target;
    private Invoice invoice1;
    private Invoice invoice2;
    private StorageInvoices testStorage;


    private Invoice createTestInvoice1(){
        Customer customer = new Customer("test@gmail.com", 25);
        Telephone telephone = new Telephone("series", "model", "screen", 500);
        List<DigitalDevice> digitalDevices = new ArrayList<>();
        digitalDevices.add(telephone);
        return new Invoice(digitalDevices, customer, TypeInvoice.RETAIL);
    }

    private Invoice createTestInvoice2(){
        Customer customer = new Customer("test@gmail.com", 15);
        Telephone telephone = new Telephone("series", "model", "screen", 1000);
        Television television = new Television("series", 25.0,
                "screen", "Israel", 2000);
        List<DigitalDevice> digitalDevices = new ArrayList<DigitalDevice>();
        digitalDevices.add(telephone);
        digitalDevices.add(television);
        return new Invoice(digitalDevices, customer, TypeInvoice.WHOLESALE);
    }

    @BeforeEach
    void setUp() {
        testStorage = new StorageInvoices();
        target = new ShopService();
        target.readFileAndCreateInvoice();
        invoice1 = createTestInvoice1();
        invoice2 = createTestInvoice2();
        testStorage.getStorageInvoices().add(invoice1);
        testStorage.getStorageInvoices().add(invoice2);
    }

    @Test
    void getCheapestInvoice() {
        Invoice cheapestInvoice = target.getCheapestInvoice(testStorage.getStorageInvoices());
        Assertions.assertEquals(invoice1, cheapestInvoice);
    }

    @Test
    void getSumAllInvoice() {
        int priceInvoice1 = 0;
        int priceInvoice2 = 0;
        List<Double> collect = invoice1.getDigitalDevices().stream()
                .map(DigitalDevice::getPrice).toList();
        List<Double> doubles = invoice2.getDigitalDevices().stream()
                .map(DigitalDevice::getPrice).toList();
        for (Double price:collect){
            priceInvoice1 += price;
        }
        for (Double price:doubles){
            priceInvoice2 += price;
        }
        double sumTestInvoices = priceInvoice1 + priceInvoice2;

        double sumAllInvoice = target.getSumAllInvoice(testStorage.getStorageInvoices());
        Assertions.assertEquals(sumTestInvoices, sumAllInvoice);
    }

    @Test
    void getCountRetailInvoice() {
        Assertions.assertEquals(1, target.getCountRetailInvoice(testStorage.getStorageInvoices()));
    }

    @Test
    void invoiceWithOneDigitalDevice() {
        List<Invoice> invoices = new ArrayList<>();
        invoices.add(invoice1);
        Assertions.assertEquals(invoices, target.invoiceWithOneDigitalDevice(testStorage.getStorageInvoices()));

    }

    @Test
    void checkNotValidInvoices() {
        List<Invoice> invoices = new ArrayList<>();
        invoices.add(invoice2);
        Assertions.assertEquals(invoices, target.checkNotValidInvoices(testStorage.getStorageInvoices()));
    }

    @Test
    void getSumInvoice() {
        int priceInvoice2 = 0;
        List<Double> doubles = invoice2.getDigitalDevices().stream()
                .map(DigitalDevice::getPrice).toList();
        for (Double price:doubles){
            priceInvoice2 += price;
        }
        Assertions.assertEquals(priceInvoice2, target.sumPricesInvoice(invoice2.getDigitalDevices()));
    }

}