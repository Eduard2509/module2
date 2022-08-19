package com.storage;

import com.model.Invoice;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class StorageInvoices {

    private final List<Invoice> storageInvoices = new ArrayList<>();
}
