package com.codecool.shop.cart.implementation;

import com.codecool.shop.cart.LineItem;
import com.codecool.shop.cart.OrderInterface;
import com.codecool.shop.cart.ShippingOption;
import spark.Request;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by makaimark on 2016.11.15..
 */
public class Order implements OrderInterface {

    private static String[] fields = new String[]{"name", "email", "phone", "billingCity", "shippingCity"};
    private String status;
    private String name;
    private String email;
    private String phone;
    private String billingAddress;
    private String billingAddressLine1;
    private String billingAddressLine2;
    private String billingCity;
    private String billingCountry;
    private String billingPostalCode;
    private String shippingAddress;
    private String shippingAddressLine1;
    private String shippingAddressLine2;
    private String shippingCity;
    private String shippingCountry;
    private String shippingPostalCode;
    private ShippingOption shipping;
    private List<LineItem> listOfSelectedItems;

    public Integer getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(Integer paymentCode) {
        this.paymentCode = paymentCode;
    }

    private Integer paymentCode;

    private Order() {
        this.listOfSelectedItems = new ArrayList<>();
    }

    public static Order getOrder(Request req) {
        if (req.session().attribute("Cart") == null) {
            req.session().attribute("Cart", new Order());
        }
        return req.session().attribute("Cart");
    }

    public static void dropOrder(Request request) {
        request.session().attribute("Cart", null);
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    public void setCheckoutFields(Request req) throws NoSuchFieldException, IllegalAccessException {
        for (String field : fields) {
            Field f = this.getClass().getDeclaredField(field);
            f.set(this, req.queryParams(field));
        }
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getBillingCity() {
        return billingCity;
    }

    public String getShippingCity() {
        return shippingCity;
    }

    public ShippingOption getShipping() {
        return shipping;
    }

    public void setShipping(ShippingOption shipping) {
        this.shipping = shipping;
    }

    public List<LineItem> getListOfSelectedItems() {
        return this.listOfSelectedItems;
    }

    public void add(LineItem item) {
        LineItem product = this.find(item);
        if (product != null) {
            product.incQuantity(item.getQuantity());
        } else {
            this.listOfSelectedItems.add(item);
        }
    }

    public void edit(LineItem item) {
        LineItem product = this.find(item);
        if (item.getQuantity() != 0) {
            product.setQuantity(item.getQuantity());
        } else {
            this.listOfSelectedItems.remove(product);
        }
    }

    public LineItem find(LineItem item) {
        return this.listOfSelectedItems.stream().filter(i -> i.getProduct().getId() == item.getProduct().getId()).findFirst().orElse(null);
    }

    public int getTotalQuantity() {
        return this.listOfSelectedItems.stream().mapToInt(o -> o.getQuantity()).sum();
    }

    public Float getTotalPrice() {
        Float sum = (float) this.listOfSelectedItems.stream().mapToDouble(o -> o.getTotalPrice()).sum();
        return (shipping == null) ? sum : sum + shipping.getCost();
    }
}