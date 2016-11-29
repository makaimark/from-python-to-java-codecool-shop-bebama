package com.codecool.shop;
import com.codecool.shop.dao.*;
import com.codecool.shop.dao.implementation.*;
import com.codecool.shop.model.*;

/**
 * Created by cickib on 2016.11.28..
 */
public class DefaultStock {

    ProductDao productDataStore;
    ProductCategoryDao productCategoryDataStore;
    SupplierDao supplierDataStore;

    public DefaultStock(String dataStorage) {
//        implementation based on memory
        if (dataStorage.equals("memory")) {
            this.productDataStore = ProductDaoMem.getInstance();
            this.productCategoryDataStore = ProductCategoryDaoJDBC.getInstance();
            this.supplierDataStore = SupplierDaoMem.getInstance();
        }
//        implementation based on database
        else {
            this.productCategoryDataStore = ProductCategoryDaoJDBC.getInstance();
            this.supplierDataStore = SupplierDaoJDBC.getInstance();
            this.productDataStore = (ProductDao) ProductDaoJDBC.getInstance();
        }
    }


    public void populateData() {
        ProductDao productDataStore = getProductDataStore();
        ProductCategoryDao productCategoryDataStore = getProductCategoryDataStore();
        SupplierDao supplierDataStore = getSupplierDataStore();

        //setting up a new supplier
        Supplier amazon = new Supplier("Amazon", "Digital content and services");
        supplierDataStore.add(amazon);
        Supplier lenovo = new Supplier("Lenovo", "Computers");
        supplierDataStore.add(lenovo);
        Supplier apple = new Supplier("Apple", "Phones");
        supplierDataStore.add(apple);
        Supplier samsung = new Supplier("Samsung", "Computers");
        supplierDataStore.add(samsung);

        //setting up a new product category
        ProductCategory tablet = new ProductCategory("Tablet", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        productCategoryDataStore.add(tablet);
        ProductCategory phone = new ProductCategory("Phone", "Hardware", "Smart phones and mobile phones (also known as cell phones) with an advanced mobile operating system.");
        productCategoryDataStore.add(phone);
        ProductCategory laptop = new ProductCategory("Laptop", "Hardware", "Laptop. Just because.");
        productCategoryDataStore.add(laptop);
        ProductCategory accessories = new ProductCategory("Accessories", "Hardware", "Cell phones have become one of the most crucial personal technology purchases. Buy cell phone, iPhone case, or Bluetooth headset.");
        productCategoryDataStore.add(accessories);

        //setting up products and printing it
        productDataStore.add(new Product("Amazon Fire", 49.9f, "USD", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", tablet, amazon));
        productDataStore.add(new Product("Lenovo IdeaPad Miix 700", 479, "USD", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", tablet, lenovo));
        productDataStore.add(new Product("Amazon Fire HD 8", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", tablet, amazon));
        productDataStore.add(new Product("iPhone 10", 899, "USD", "iPhone. No more description needed.", phone, apple));
        productDataStore.add(new Product("Random laptop", 139, "USD", "Lorem ipsum blablabla.", laptop, samsung));
        productDataStore.add(new Product("Exploding phone", 79, "USD", "If you like to live dangerously, buy this one.", phone, samsung));
        productDataStore.add(new Product("AmazonPhone", 59, "USD", "Amazon's very own budget phone.", phone, amazon));
    }

    public ProductDao getProductDataStore() {
        return productDataStore;
    }

    public ProductCategoryDao getProductCategoryDataStore() {
        return productCategoryDataStore;
    }

    public SupplierDao getSupplierDataStore() {
        return supplierDataStore;
    }
}
