package com.example.pojo;

public class Order
{
    String email;
    Product product;
    static Order instance;

   public Order(String email, Product product) {
        this.email = email;
        this.product = product;
    }

    public static Order getInstance()
    {
        if (instance == null)
        {
            instance = new Order(getInstance().email, getInstance().product);
        }
        return instance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
