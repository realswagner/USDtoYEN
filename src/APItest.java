//import java.net.URL;
//import java.net.URI;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class APItest {
    public static void main(String[] args) {
        boolean dateValid = false;
        System.out.println("**************************************");
        System.out.println("Please enter date in yyyy-mm-dd format");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        while(!dateValid) {
            Scanner scan = new Scanner(System.in);
            String date = scan.nextLine();
            try {
                LocalDate parsedDate = LocalDate.parse(date, formatter);
                if (parsedDate.isAfter(LocalDate.now()) ||
                        parsedDate.isBefore(LocalDate.of(1960, 1, 1))) {
                    System.out.println("Please enter valid date between 01/01/1960 and now");
                } else if(date.equalsIgnoreCase("N")){ break;} else {
                    System.out.println("Date entered is valid " + parsedDate);
                    dateValid = true;

//                    JSONObject dayRate = getRate(date);
////                    assert dayRate != null;
//                    double rate = (double) dayRate.get("rate");
                    double rate = getRate(date);
                    System.out.println("Rate for:" + date + " is: " + rate);

                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format try again.");
            }
        }
    }
    private static double getRate(String date){
        String urlString = "https://kekkai-api.redume.su/api/getRate/?from_currency=USD&conv_currency=RUB&date="
                + date;
        try{
            HttpURLConnection apiConnection = fetchApiResponse(urlString);

            if(apiConnection.getResponseCode() != 200){
                System.out.println("Error: Could not connect to API");
                return 0;
            }
            //read response and convert string type
            String jsonResponse = readApiResponse(apiConnection);

            //parses string into json object
            JSONParser parser = new JSONParser();
            JSONObject resultsJsonObj = (JSONObject) parser.parse(jsonResponse);
            return (double) resultsJsonObj.get("rate");

//            JSONArray conversionData = (JSONArray) resultsJsonObj.get("rate");
//            return (JSONObject) conversionData.get(0);
//            double rate = (double) resultsJsonObj.get("rate");

        } catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }



    private static HttpURLConnection fetchApiResponse(String urlString) {
        try{
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            return conn;
        } catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }
    private static String readApiResponse(HttpURLConnection apiConnection){
        try {
            //create stringbuilder to store json data

            StringBuilder resultJson = new StringBuilder();
            //scanner reads input stream of http connection
            Scanner responseScanner = new Scanner(apiConnection.getInputStream());

            //loop through scanner to append to stringbuilder
            while (responseScanner.hasNext()) {
                resultJson.append(responseScanner.nextLine());
            }
            //close scanner to release resources associated
            responseScanner.close();

            //return json data as a string
            return resultJson.toString();
        } catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

}
