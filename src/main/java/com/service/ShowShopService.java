package com.service;

import com.model.Customer;
import com.model.Invoice;

import java.util.List;

public class ShowShopService {

    private static final ShopService SHOP_SERVICE = new ShopService();

    public void showBuildInvoice() {
        Invoice invoice = SHOP_SERVICE.buildInvoice();
        System.out.println(invoice);
    }

    public void showCountSoldDigitalDevices() {
        int countSaleTelephones = SHOP_SERVICE.getCountSoldTelephone();
        int countSaleTelevisions = SHOP_SERVICE.getCountSoldTelevisions();
        System.out.println("Telephones sales: " + countSaleTelephones + " Television sales: "
                + countSaleTelevisions);
    }

    public void showCheapestInvoice() {
        Invoice cheapestInvoice = SHOP_SERVICE.getCheapestInvoice(SHOP_SERVICE.storageInvoices.getStorageInvoices());
        double sum = SHOP_SERVICE.sumPricesInvoice(cheapestInvoice.getDigitalDevices());
        Customer customer = cheapestInvoice.getCustomer();
        System.out.println("Sum cheapest Invoice: " + sum + " Customer:" + customer);
    }

    public void showSumAllInvoices() {
        double sumAllInvoice = SHOP_SERVICE.getSumAllInvoice(SHOP_SERVICE.storageInvoices.getStorageInvoices());
        System.out.println("Sum all invoices: " + sumAllInvoice);
    }

    public void showCountRetailInvoice() {
        System.out.println("Count retail invoices: " +
                SHOP_SERVICE.getCountRetailInvoice(SHOP_SERVICE.storageInvoices.getStorageInvoices()));
    }

    public void showInvoiceWithOneDigitalDevice() {
        List<Invoice> invoices = SHOP_SERVICE.invoiceWithOneDigitalDevice(SHOP_SERVICE.storageInvoices.getStorageInvoices());
        System.out.println("Invoice with one digital device:");
        for (Invoice invoice : invoices) {
            System.out.println(invoice);
        }
    }

    public void showFirstThreeInvoices() {
        List<Invoice> invoices = SHOP_SERVICE.firstThreeInvoices();
        System.out.println("First Tree invoices");
        for (Invoice invoice : invoices) {
            System.out.println(invoice);
        }
    }

    public void showNotValidInvoices() {
        List<Invoice> notValidInvoices = SHOP_SERVICE
                .checkNotValidInvoices(SHOP_SERVICE.storageInvoices.getStorageInvoices());
        for (Invoice invoice : notValidInvoices) {
            System.out.println(invoice);
        }
    }

    public void showSortedInvoices() {
        List<Invoice> sortedAllInvoices = SHOP_SERVICE.sortedAllInvoices();
        for (Invoice invoice : sortedAllInvoices) {
            System.out.println(invoice);
        }
    }
}
