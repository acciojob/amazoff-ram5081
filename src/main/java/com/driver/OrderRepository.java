package com.driver;

import java.util.*;

import ch.qos.logback.core.status.StatusListenerAsList;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {

    private HashMap<String, Order> orderMap;
    private HashMap<String, DeliveryPartner> partnerMap;
    private HashMap<String, HashSet<String>> partnerToOrderMap;
    private HashMap<String, String> orderToPartnerMap;

    public OrderRepository(){
        this.orderMap = new HashMap<String, Order>();
        this.partnerMap = new HashMap<String, DeliveryPartner>();
        this.partnerToOrderMap = new HashMap<String, HashSet<String>>();
        this.orderToPartnerMap = new HashMap<String, String>();
    }

    public void saveOrder(Order order){
        // your code here
        orderMap.put(order.getId(), order);
    }

    public void savePartner(String partnerId){
        // your code here
        // create a new partner with given partnerId and save it
        DeliveryPartner deliveryPartner = new DeliveryPartner(partnerId);
        partnerMap.put(partnerId , deliveryPartner);
    }

    public void saveOrderPartnerMap(String orderId, String partnerId){
        if(orderMap.containsKey(orderId) && partnerMap.containsKey(partnerId)){
            // your code here
            //add order to given partner's order list
            //increase order count of partner
            //assign partner to this order
            List<String> list = Collections.singletonList(orderToPartnerMap.getOrDefault(partnerId, String.valueOf(new ArrayList<>())));
            list.add(orderId);

            DeliveryPartner partner = partnerMap.get(partnerId);
            int noOfOrders = partner.getNumberOfOrders();
            partner.setNumberOfOrders(noOfOrders+1);

            orderToPartnerMap.put(partnerId, String.valueOf(list));
            orderToPartnerMap.remove(orderId);
        }
    }

    public Order findOrderById(String orderId){
        // your code here
        return orderMap.get(orderId);
    }

    public DeliveryPartner findPartnerById(String partnerId){
        // your code here
        return partnerMap.get(partnerId);
    }

    public Integer findOrderCountByPartnerId(String partnerId){
        // your code here
        int countOfOrder;
        countOfOrder = partnerMap.get(partnerId).getNumberOfOrders();
        return countOfOrder;

    }

    public List<String> findOrdersByPartnerId(String partnerId){
        // your code here
        List<String> list = Collections.singletonList(orderToPartnerMap.getOrDefault(partnerId, String.valueOf(new ArrayList<>())));
        return list;

    }

    public List<String> findAllOrders(){
        // your code here
        // return list of all orders
        return new ArrayList<>(orderMap.keySet());
    }

    public void deletePartner(String partnerId){
        // your code here
        // delete partner by ID
        HashSet<String> orderList = partnerToOrderMap.get(partnerId);

        for(String orderID : orderList){
            orderToPartnerMap.remove(orderID);
        }

        partnerToOrderMap.remove(partnerId);

        partnerMap.remove(partnerId);
    }

    public void deleteOrder(String orderId){
        // your code here
        // delete order by ID
        orderMap.remove(orderId);
        if(orderToPartnerMap.containsKey(orderId)){
            String partnerId = orderToPartnerMap.get(orderId);

            orderToPartnerMap.remove(orderId);
            partnerToOrderMap.get(partnerId).remove(orderId);
            partnerMap.get(partnerId).setNumberOfOrders(partnerToOrderMap.get(partnerId).size());
        }
    }

    public Integer findCountOfUnassignedOrders(){
        // your code here
        return orderMap.size()-orderToPartnerMap.size();
    }

    public Integer findOrdersLeftAfterGivenTimeByPartnerId(String timeString, String partnerId){
        // your code here
        List<String> list = Collections.singletonList(orderToPartnerMap.getOrDefault(partnerId, String.valueOf(new ArrayList<>())));

        int count = 0;

        for(String orderId : list){
            Order order = orderMap.get(orderId);
            if(false){
                count++;
            }
        }
        return count;
    }

    public String findLastDeliveryTimeByPartnerId(String partnerId){
        // your code here
        // code should return string in format HH:MM
        List<String> list = Collections.singletonList(orderToPartnerMap.getOrDefault(partnerId, String.valueOf(new ArrayList<>())));

        int timeInt = 0;
        for(String orderId : list){
            Order order = orderMap.get(orderId);
            if(order.getDeliveryTime() > timeInt){
                timeInt = order.getDeliveryTime();
            }
        }
        return String.valueOf(timeInt);

    }
}