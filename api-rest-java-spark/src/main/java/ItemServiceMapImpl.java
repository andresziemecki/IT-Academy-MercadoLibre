import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.*;
import org.json.JSONObject;

public class ItemServiceMapImpl {

    public static String loginUser(String urlPost, User user) {

        try {
            URL url = new URL(urlPost);
            try {
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setDoOutput(true);
                con.setDoInput(true);
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("Accept", "application/json");
                con.setRequestMethod("POST");
                Gson gson = new Gson();
                JsonElement jsonInputString = gson.toJsonTree(user);
                OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
                wr.write(jsonInputString.toString());
                wr.flush();
                StringBuilder sb = new StringBuilder();
                int HttpResult = con.getResponseCode();

                if (HttpResult == HttpURLConnection.HTTP_OK) {

                    BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));

                    Gson gsonSite = new Gson();
                    Token userr = gsonSite.fromJson(br, Token.class);
                    return userr.getToken();

                } else {
                    System.out.println(con.getResponseMessage());
                }
            } catch (IOException exception) {
                System.out.println(exception.getMessage());
            }
        } catch (MalformedURLException exception) {
            System.out.println(exception.getMessage());
        }
        return (null);
    }

    public static Site[] getSites(String urlget) {

        try {
            URL url = new URL(urlget);
            try {
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setDoOutput(true);
                con.setDoInput(true);
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("Accept", "application/json");
                con.setRequestMethod("GET");

                StringBuilder sb = new StringBuilder();
                int HttpResult = con.getResponseCode();
                System.out.println("hola");
                System.out.println(HttpResult + " - " + HttpURLConnection.HTTP_OK);
                if (HttpResult == HttpURLConnection.HTTP_OK) {
                    System.out.println("chau");
                    BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
                    System.out.println("aa");
                    Gson gsonSite = new Gson();
                    Site[] sites = gsonSite.fromJson(br, Site[].class);
                    System.out.println("hola:"+sites);
                    return sites;

                } else {
                    System.out.println(con.getResponseMessage());
                }
            } catch (IOException exception) {
                System.out.println(exception.getMessage());
            }
        } catch (MalformedURLException exception) {
            System.out.println(exception.getMessage());
        }
        return (null);
    }
    public static Category[] getCategory(String urlget) {

        try {
            URL url = new URL(urlget);
            try {
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setDoOutput(true);
                con.setDoInput(true);
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("Accept", "application/json");
                con.setRequestMethod("GET");

                StringBuilder sb = new StringBuilder();
                int HttpResult = con.getResponseCode();
                System.out.println("hola");
                System.out.println(HttpResult + " - " + HttpURLConnection.HTTP_OK);
                if (HttpResult == HttpURLConnection.HTTP_OK) {
                    System.out.println("chau");
                    BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
                    System.out.println("aa");
                    Gson gsonSite = new Gson();
                    Category[] categories = gsonSite.fromJson(br, Category[].class);
                    System.out.println("hola:"+categories);
                    return categories;

                } else {
                    System.out.println(con.getResponseMessage());
                }
            } catch (IOException exception) {
                System.out.println(exception.getMessage());
            }
        } catch (MalformedURLException exception) {
            System.out.println(exception.getMessage());
        }
        return (null);
    }
}
