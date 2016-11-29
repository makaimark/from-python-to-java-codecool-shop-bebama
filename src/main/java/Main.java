import com.codecool.shop.DefaultStock;
import com.codecool.shop.controller.CartController;
import com.codecool.shop.controller.CategoryController;
import com.codecool.shop.controller.ProductController;
import com.codecool.shop.controller.SupplierController;
import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.util.HashMap;

import static spark.Spark.*;
import static spark.debug.DebugScreen.enableDebugScreen;

public class Main {

    public static void main(String[] args) {

        // default server settings
        exception(Exception.class, (e, req, res) -> e.printStackTrace());
        staticFileLocation("/public");
        port(8888);

        // populate some data for the memory storage
        DefaultStock stock = new DefaultStock("");
        stock.populateData();

        // define routes
        get("/hello", (req, res) -> "Hello World");
        get("/category/:id", CategoryController::renderProducts, new ThymeleafTemplateEngine());
        get("/supplier/:id", SupplierController::renderProducts, new ThymeleafTemplateEngine());
        post("/additemtocart", CartController::addItemToCart);
        post("/editcart", CartController::editCart);
        get("/checkout", (req, res) -> new ThymeleafTemplateEngine().render(new ModelAndView(new HashMap<>(), "product/form")));
        post("/checkout", CartController::checkOut);
        get("/payment", (req, res) -> new ThymeleafTemplateEngine().render(new ModelAndView(new HashMap<>(), "product/payment")));
        get("/", ProductController::renderProducts, new ThymeleafTemplateEngine());

        // Add this line to your project to enable the debug screen
        enableDebugScreen();
    }

}