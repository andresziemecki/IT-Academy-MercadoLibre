import com.google.gson.Gson;
import static spark.Spark.*;
import com.google.gson.JsonElement;
import spark.Spark;

import java.io.BufferedReader;
import java.net.URL;
import java.net.URLConnection;


public class ItemRestFul {


    public static void main(String[] args) {
        Token tokenUser = new Token();
        final ItemServiceMapImpl service = new ItemServiceMapImpl();
        Spark.port(8080);
        ElasticSearchClass elast = new ElasticSearchClass();

        // Devuelve token
        post("/users", (req,res) -> {
            System.out.println("post users");
            res.type("application/json");
            User user = new Gson().fromJson(req.body(),User.class);
            System.out.println(user);

            String response = service.loginUser("http://localhost:8095/users", user);
            user.setToken(response);
            System.out.println(user);
            tokenUser.setPassword(user.getPassword());
            tokenUser.setUsername(user.getUsername());
            tokenUser.setToken(user.getToken());
            System.out.println("tokenUser - token: " + tokenUser.getToken() );
            System.out.println("tokenUser - nameuser: " + tokenUser.getUsername());
            System.out.println("tokenUser - pass: " + tokenUser.getPassword());
            return new Gson().toJson(new StandarResponse(StatusResponse.SUCCESS));
        });


        // Devuelve sitios
        get("/sites",(req,res) -> {

            String url = "http://localhost:8095/users/sites/"+tokenUser.getUsername()+"/"+tokenUser.getToken();
            System.out.println(url);
            res.type("application/json");
            Site[] response = service.getSites(url);
            return new Gson().toJson(new StandarResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(response)));
        });




        // Devuelve categorias
        get("/category",(req,res) -> {
            res.type("application/json");
            String url = "http://localhost:8095/users/sites/MLA/categories/"+tokenUser.getUsername()+"/"+tokenUser.getToken();
            System.out.println(url);
            Category[] response = service.getCategory(url);
            System.out.println(response);
            return new Gson().toJson(new StandarResponse(StatusResponse.SUCCESS, new Gson().toJsonTree(response)));
        });

        //Post Item
        post("/items", (req,res) -> {
            System.out.println("post item");
            res.type("application/json");
            Item item = new Gson().fromJson(req.body(),Item.class);
            elast.sendPost(item);
            return new Gson().toJson(new StandarResponse(StatusResponse.SUCCESS));
        });

        get("items/:user", (req,res) -> {
            System.out.println("Get all items from a user");
            res.type("application/json");
            User user = new Gson().fromJson(req.body(),User.class);
            String output = elast.getItemsFromUsername(user.getUsername());
            System.out.println(output);
            return new Gson().toJson(new StandarResponse(StatusResponse.SUCCESS));
        });

        get("/items", (req,res) -> {
            System.out.println("Get all items from all users");
            res.type("application/json");
            String output = elast.getAllItems();
            System.out.println(output);
            return new Gson().toJson(new StandarResponse(StatusResponse.SUCCESS));
        });

    }
}


