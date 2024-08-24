package org.example;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CurrencyConverter {

    // Constants for API access and supported currencies
    private static final String API_TOKEN = "e8b268e076cd478ff1127904923ed21f"; // Replace with your API token
    private static final String API_BASE_URL = "https://api.currencylayer.com/convert";

    // Set of supported currencies
    private static final Set<String> SUPPORTED_CURRENCIES = new HashSet<>(Set.of(
            "USD", "EUR", "GBP", "JPY", "AUD", "CAD", "CHF", "CNY", "INR", "MXN",
            "SGD", "HKD", "NOK", "SEK", "NZD", "KRW", "TRY", "RUB", "ZAR", "BRL",
            "SAR", "AED", "DKK", "PLN", "HUF", "CZK", "ILS", "THB", "MYR", "PHP",
            "IDR", "CLP", "COP", "PEN", "ARS", "VND", "BHD", "QAR", "KWD", "JOD",
            "LYD", "TND", "OMR", "EGP", "PKR", "BDT", "LKR", "MDL", "KZT", "UAH",
            "RSD", "BGN", "HRK", "RON", "NAD", "MUR", "TZS", "MZN", "AOA", "GHS",
            "NGN", "XOF", "XAF", "XPF", "SYP", "SDG", "DJF", "ERN", "ETB", "SLL",
            "MNT", "KPW", "KGS", "KYD", "JMD", "BAM", "MRO", "KMF", "STN", "CUP",
            "CUC", "MOP", "NIO", "PAB", "PYG", "SCR", "TJS", "WST", "XCD", "ZWL",
            "AWG", "BBD", "BND", "BOV", "BWP", "CDF"
    ));

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            // Greet user and display available currencies
            System.out.println("Welcome to the Global Currency Exchange!");
            System.out.println("Available currencies: " + formatAvailableCurrencies());

            // Get user input for conversion
            System.out.print("Enter the currency code you want to convert from (e.g., INR): ");
            String fromCurrency = scanner.nextLine().toUpperCase();

            System.out.print("Enter the currency code you want to convert to (e.g., USD): ");
            String toCurrency = scanner.nextLine().toUpperCase();

            System.out.print("Enter the amount to convert: ");
            double amount = scanner.nextDouble();

            // Validate currency codes
            if (isSupportedCurrency(fromCurrency) && isSupportedCurrency(toCurrency)) {
                // Construct API request URL
                String requestUrl = buildApiUrl(fromCurrency, toCurrency, amount);

                // Fetch and process conversion result
                String jsonResponse = fetchApiResponse(requestUrl);
                processApiResponse(jsonResponse, amount, fromCurrency, toCurrency);
            } else {
                System.out.println("Invalid currency code. Please use supported currencies.");
            }

        } catch (IOException e) {
            System.err.println("Failed to connect to the API: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        } finally {
            System.out.println("Thank you for using the Currency Converter! Created by Sayan Chakraborty");
        }
    }

    // Build the API request URL
    private static String buildApiUrl(String from, String to, double amount) {
        return String.format("%s?access_key=%s&from=%s&to=%s&amount=%s",
                API_BASE_URL, API_TOKEN, from, to, amount);
    }

    // Check if the currency is supported
    private static boolean isSupportedCurrency(String currency) {
        return SUPPORTED_CURRENCIES.contains(currency);
    }

    // Fetch the API response
    private static String fetchApiResponse(String requestUrl) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(requestUrl).openConnection();
        connection.setRequestMethod("GET");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            return reader.readLine();
        }
    }

    // Process the API response and display the result
    private static void processApiResponse(String response, double amount, String fromCurrency, String toCurrency) {
        JSONObject jsonResponse = new JSONObject(response);
        if (jsonResponse.has("result")) {
            double convertedAmount = jsonResponse.getDouble("result");
            System.out.printf("%.2f %s is equivalent to %.2f %s%n", amount, fromCurrency, convertedAmount, toCurrency);
        } else {
            System.out.println("Conversion result not found in the API response.");
        }
    }

    // Format the available currencies for display
    private static String formatAvailableCurrencies() {
        List<String> sortedCurrencies = SUPPORTED_CURRENCIES.stream()
                .sorted()
                .collect(Collectors.toList());

        if (sortedCurrencies.size() <= 5) {
            return String.join(", ", sortedCurrencies);
        } else {
            return String.join(", ", sortedCurrencies.subList(0, 4)) + ", and " + (sortedCurrencies.size() - 4) + " more";
        }
    }
}
