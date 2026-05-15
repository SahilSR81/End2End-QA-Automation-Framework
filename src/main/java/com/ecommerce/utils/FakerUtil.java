package com.ecommerce.utils;

import com.github.javafaker.Faker;

public class FakerUtil {
    private static final Faker faker = new Faker();

    /**
     * Generates a random first name.
     * @return A randomly generated first name string.
     */
    public static String generateFirstName() {
        return faker.name().firstName();
    }

    /**
     * Generates a random last name.
     * @return A randomly generated last name string.
     */
    public static String generateLastName() {
        return faker.name().lastName();
    }

    /**
     * Generates a random, valid email address format.
     * @return A randomly generated email string.
     */
    public static String generateEmail() {
        return faker.internet().emailAddress();
    }

    /**
     * Generates a random strong password.
     * @return A randomly generated complex password.
     */
    public static String generatePassword() {
        return faker.internet().password(8, 20, true, true, true);
    }

    /**
     * Generates a random phone number format.
     * @return A randomly generated phone number string.
     */
    public static String generatePhoneNumber() {
        return faker.phoneNumber().cellPhone();
    }

    /**
     * Generates a random street address.
     * @return A randomly generated street address.
     */
    public static String generateAddress() {
        return faker.address().streetAddress();
    }

    /**
     * Generates a random city name.
     * @return A randomly generated city name.
     */
    public static String generateCity() {
        return faker.address().city();
    }

    /**
     * Generates a random zip code format.
     * @return A randomly generated zip code numeric format string.
     */
    public static String generateZipCode() {
        return faker.address().zipCode();
    }

    /**
     * Generates a random country name.
     * @return A randomly generated country name string.
     */
    public static String generateCountry() {
        return faker.address().country();
    }
}
