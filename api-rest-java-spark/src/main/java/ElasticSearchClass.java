import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.*;
​
public class ElasticSearchClass {
​
    private static String ElasticURL = "http://localhost:9200/items";
​
    public ElasticSearchClass(){}
​
    public void sendPost(Item item) throws Exception {
​
        URL obj = new URL(ElasticURL + "/_doc");
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
​
        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
​
        String urlParameters = "{ \"username\" :" + "\"" + item.getUser() + "\"" +
                ", \"site\" :" +  "\"" + item.getSite() + "\"" +
                ", \"category\" :" +  "\"" + item.getCategory().getName() + "\"" +
                ", \"name_item\" :" +  "\"" + item.getName() + "\"" +
                " }";
​
        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();
​
        int responseCode = con.getResponseCode();
​
        System.out.println("\nSending 'POST' request to URL : " + ElasticURL+ "/_doc");
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);
​
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
​
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
​
        //print result
        System.out.println(response.toString());
        return;
​
    }
    public String getItemsFromUsername(String username) throws Exception {
​
        URL obj = new URL(ElasticURL + "/_search?q=" + username);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
​
        // optional default is GET
        con.setRequestMethod("GET");
​
        //add request header
        con.setRequestProperty("Content-Type", "application/json");
​
        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + ElasticURL + "/_search?q=" + username);
        System.out.println("Response Code : " + responseCode);
​
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
​
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
​
        //print result
        System.out.println(response.toString());
        return response.toString();
​
    }
​
    public String getAllItems() throws Exception {
​
        URL obj = new URL(ElasticURL + "/_search?");
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
​
        // optional default is GET
        con.setRequestMethod("GET");
​
        //add request header
        con.setRequestProperty("Content-Type", "application/json");
​
        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + ElasticURL + "/_search?");
        System.out.println("Response Code : " + responseCode);
​
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
​
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
​
        //print result
        System.out.println(response.toString());
​
        return response.toString();
    }
​
}