package com.codecool.shop.controller;

import com.codecool.shop.cart.implementation.Order;
import com.codecool.shop.dao.implementation.jdbc.ProductCategoryDaoJDBC;
import com.codecool.shop.model.Product;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class CategoryController extends AbstractController{

    public static ModelAndView renderProducts(Request req, Response res) throws IOException, URISyntaxException {
        UserController.isLoggedIn(req);
        params.put("isLoggedIn", UserController.isLoggedIn(req));
        int categoryId = Integer.parseInt(req.params(":id"));
        List<Product> products = ProductCategoryDaoJDBC.getInstance().find(categoryId).getProducts();
        return setParams(Order.getOrder(req), "/category/" + categoryId, products);
    }


}
