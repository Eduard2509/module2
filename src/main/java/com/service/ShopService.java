package com.service;

import com.exceptionIncorrectReadFile.ExceptionIncorrectReadFile;
import com.model.*;
import com.storage.StorageInvoices;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
public class ShopService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShopService.class);
    private static final Random RANDOM = new Random();
    private static final double LIMIT = RANDOM.nextDouble(1000, 1500);
    private static final Pattern PATTERN = Pattern.compile(
            "(^\\w+)\\;(\\w*(.\\d+)? ?\\w?-?(\\d{4})?)\\;(\\w*)\\;(\\d+|\\w+)\\;(\\w+( .{4}?)?)?\\;(\\w*)\\;(.*)");

    static final List<Telephone> phones = new ArrayList<>();
    static final List<Television> televisions = new ArrayList<>();
    private List<Telephone> randomListTelephones = new ArrayList<>();
    private List<Television> randomListTelevision = new ArrayList<>();
    public static final StorageInvoices storageInvoices = new StorageInvoices();

    private InputStream getFileFromResourceAsStream(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }
    }

    private List<DigitalDevice> getRandomListDevices() {
        List<DigitalDevice> randomDevices = new ArrayList<>();
        int randomIteration = RANDOM.nextInt(1, 5);
        for (int i = 0; i < randomIteration; i++) {
            int randomDigital = RANDOM.nextInt(0, 2);
            if (randomDigital < 1) {
                int randomIndex = RANDOM.nextInt(0, phones.size());
                Telephone telephone = phones.get(randomIndex);
                randomListTelephones.add(telephone);
                randomDevices.add(telephone);
            } else {
                int randomIndex = RANDOM.nextInt(0, televisions.size());
                Television television = televisions.get(randomIndex);
                randomListTelevision.add(television);
                randomDevices.add(television);
            }
        }
        return randomDevices;
    }

    private TypeInvoice getTypeInvoice() {
        double sum = 0;
        for (DigitalDevice device : getRandomListDevices()) {
            sum += device.getPrice();
        }
        if (sum > LIMIT) {
            return TypeInvoice.WHOLESALE;
        } else {
            return TypeInvoice.RETAIL;
        }
    }

    public double sumPricesInvoice(List<DigitalDevice> digitalDevices) {
        double sumPrices = 0;
        for (DigitalDevice digitalDevice : digitalDevices) {
            sumPrices += digitalDevice.getPrice();
        }
        return sumPrices;
    }

    private double getSumInvoice(Invoice invoice) {
        double sum = 0;
        List<DigitalDevice> digitalDevices = invoice.getDigitalDevices();
        for (DigitalDevice digitalDevice : digitalDevices) {
            sum += digitalDevice.getPrice();
        }
        return sum;
    }

    private final Comparator<Invoice> comparatorAge =
            (o1, o2) -> o2.getCustomer().getAge() - o1.getCustomer().getAge();

    private final Comparator<Invoice> comparatorSize =
            (o1, o2) -> o2.getDigitalDevices().size() - o1.getDigitalDevices().size();

    private final Comparator<Invoice> comparatorSumInvoice = (o1, o2) -> {
        double sumInvoiceO1 = getSumInvoice(o1);
        double sumInvoiceO2 = getSumInvoice(o2);
        return Double.compare(sumInvoiceO2, sumInvoiceO1);
    };

    final Comparator<Invoice> complexComparator = comparatorAge
            .thenComparing(comparatorSize)
            .thenComparing(comparatorSumInvoice);


    String head = "";

    public void readFileAndCreateInvoice() {
        ShopService shopService = new ShopService();
        final Map<String, Object> map = new HashMap<>();
        String fileName = "storage.csv";
        InputStream inputStream = shopService.getFileFromResourceAsStream(fileName);
        String line = null;
        try (final InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
            while ((line = bufferedReader.readLine()) != null) {
                getMap(line, map);
            }
        } catch (ExceptionIncorrectReadFile | IOException e) {
            LOGGER.info("Incorrect read line: " + line);
        }
    }

    private void getMap(final String line, final Map<String, Object> map) throws ExceptionIncorrectReadFile {
        final Matcher matcher = PATTERN.matcher(line);
        if (matcher.find()) {
            if (head.equals("")) {
                head = matcher.group();
            }
            List<String> headComponents = parseString(head);
            List<String> bodyComponents = parseString(line);
            for (int i = 0; i < headComponents.size(); i++) {
                map.put(headComponents.get(i), bodyComponents.get(i));
            }
            if (map.get("type").equals("Telephone")) {
                phones.add(new Telephone((String) map.getOrDefault("series", "series0"),
                        (String) map.getOrDefault("model", "model0"),
                        (String) map.getOrDefault("screen type", "screenType0"),
                        Double.parseDouble((String) map.getOrDefault("price", 0))));
            }
            if (map.get("type").equals("Television")) {
                televisions.add(new Television((String) map.getOrDefault("series", "series0"),
                        Double.parseDouble((String) map.getOrDefault("diagonal", 0)),
                        (String) map.getOrDefault("screen type", "screenType0"),
                        (String) map.getOrDefault("country", "country0"),
                        Double.parseDouble((String) map.getOrDefault("price", 0))));
            }
            if (map.get("type") == "" || map.get("series") == "" || map.get("model") == ""
                    || map.get("diagonal") == "" || map.get("screen type") == ""
                    || map.get("country") == "" || map.get("price") == "") {
                throw new ExceptionIncorrectReadFile("Incorrect read line" + line);
            }
        }
    }

    private List<String> parseString(String string) {
        return List.of(string.split(";"));
    }

    public void save(Invoice invoice) {
        if (invoice == null) {
            throw new IllegalArgumentException("Invoice must not be null");
        }
        storageInvoices.getStorageInvoices().add(invoice);
    }

    public Invoice buildInvoice() {
        PersonService personService = new PersonService();
        Invoice invoice = new Invoice(getRandomListDevices(), personService.getRandomCustomer(), getTypeInvoice());
        save(invoice);
        LOGGER.info("[time: " + invoice.getCreated() + "] "
                + "[user data: " + invoice.getCustomer() + "] " + "[invoice Data" + invoice.getDigitalDevices() + "]");
        return invoice;
    }

    public int getCountSoldTelephone() {
        int countSaleTelephones = 0;
        for (int i = 0; i < randomListTelephones.size(); i++) {
            countSaleTelephones++;
        }
        return countSaleTelephones;
    }

    public int getCountSoldTelevisions() {
        int countSaleTelevisions = 0;
        for (int i = 0; i < randomListTelevision.size(); i++) {
            countSaleTelevisions++;
        }
        return countSaleTelevisions;
    }

    public Invoice getCheapestInvoice(List<Invoice> invoices) {
        double cheapestInvoice = 0;
        Invoice returnInvoice = null;
        for (Invoice invoice : invoices) {
            double cost = sumPricesInvoice(invoice.getDigitalDevices());
            if (cheapestInvoice == 0) {
                cheapestInvoice = cost;
            }
            if (cost <= cheapestInvoice) {
                cheapestInvoice = cost;
                returnInvoice = invoice;
            }
        }
        return returnInvoice;
    }

    public double getSumAllInvoice(List<Invoice> invoices) {
        double sum = 0;
        List<Double> objectStream = invoices.stream()
                .map(Invoice::getDigitalDevices)
                .map(digitalDevice -> digitalDevice.stream()
                        .mapToDouble(DigitalDevice::getPrice)
                        .sum()).toList();
        for (Double price : objectStream) {
            sum += price;
        }
        return sum;
    }

    public int getCountRetailInvoice(List<Invoice> invoices) {
        Predicate<TypeInvoice> typeInvoicePredicate = typeInvoice -> typeInvoice.equals(TypeInvoice.RETAIL);
        int count = 0;
        List<TypeInvoice> typeInvoices = invoices.stream()
                .map(Invoice::getTypeInvoice)
                .filter(typeInvoicePredicate).toList();
        for (int i = 0; i < typeInvoices.size(); i++) {
            count++;
        }
        return count;
    }

    public List<Invoice> invoiceWithOneDigitalDevice(List<Invoice> invoices) {

        return invoices.stream()
                .filter(invoice -> invoice.getDigitalDevices().size() < 2)
                .toList();
    }


    final Comparator<Invoice> invoiceComparator = Comparator.comparing(Invoice::getCreated);

    public List<Invoice> firstThreeInvoices() {
        return storageInvoices.getStorageInvoices()
                .stream()
                .sorted(invoiceComparator).limit(3)
                .toList();
    }

    public List<Invoice> checkNotValidInvoices(List<Invoice> storageInvoices) {
        List<Invoice> notValidInvoices = new ArrayList<>();
        for (Invoice invoice : storageInvoices) {
            if (invoice.getCustomer().getAge() < 18) {
                invoice.setTypeInvoice(TypeInvoice.LOW_AGE);
                notValidInvoices.add(invoice);
            }
        }
        return notValidInvoices;
    }

    public List<Invoice> sortedAllInvoices() {
        return storageInvoices.getStorageInvoices().stream()
                .sorted(complexComparator)
                .toList();
    }
}
