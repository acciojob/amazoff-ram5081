package com.driver;

public class Order {

    private String id;
    private final int deliveryTime;

    public Order(String id, String deliveryTime) {

        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
        int hr = Integer.valueOf(deliveryTime.substring(0,2));
        int min = Integer.valueOf(deliveryTime.substring(3));
        this.deliveryTime = hr * 60 + min;
    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {return deliveryTime;}
}
