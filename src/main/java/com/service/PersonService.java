package com.service;

import com.model.Customer;

import java.util.Random;

public class PersonService {

    private static final Random RANDOM = new Random();

    public Customer getRandomCustomer() {
        return new Customer(getRandomEmail(), getRandomAge());
    }

    private String getRandomEmail() {
        StringBuilder stringBuilder = new StringBuilder();
        int size = RANDOM.nextInt(3, 15);
        String[] chars = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
                "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v",
                "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        for (int i = 0; i < size; i++) {
            int randomChar = RANDOM.nextInt(0, 35);
            String oneChar = chars[randomChar];
            stringBuilder.append(oneChar);
        }
        String string = stringBuilder.toString();
        return string + "@gmail.com";
    }

    private int getRandomAge() {
        return RANDOM.nextInt(10, 70);
    }

}
