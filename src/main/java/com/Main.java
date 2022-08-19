package com;

import com.service.ShopService;
import com.service.ShowShopService;

public class Main {

    public static void main(String[] args) {

        ShopService shopService = new ShopService();
        ShowShopService showShopService = new ShowShopService();
        shopService.readFileAndCreateInvoice();
        for (int i = 0; i < 15; i++) {
            showShopService.showBuildInvoice();
        }
        System.out.println();
        System.out.println("Count sold digital devices: ");
        showShopService.showCountSoldDigitalDevices();
        System.out.println();
        showShopService.showSumAllInvoices();
        System.out.println();
        showShopService.showCountRetailInvoice();
        System.out.println();
        System.out.println("Cheapest invoice: ");
        showShopService.showCheapestInvoice();
        System.out.println();
        showShopService.showCountRetailInvoice();
        System.out.println();
        showShopService.showInvoiceWithOneDigitalDevice();
        System.out.println();
        showShopService.showFirstThreeInvoices();
        System.out.println();
        System.out.println("Check not valid invoices: ");
        showShopService.showNotValidInvoices();
        System.out.println();
        System.out.println("Sorted all invoices");
        showShopService.showSortedInvoices();
    }
}
