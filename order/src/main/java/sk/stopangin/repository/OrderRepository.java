package sk.stopangin.repository;

import org.springframework.stereotype.Component;
import sk.stopangin.model.Order;

import java.util.HashMap;
import java.util.Map;

@Component
public class OrderRepository {
    private Map<String, Order> orders= new HashMap<>();

    public Order save(Order order){
        orders.put(order.getId(),order);
        return order;
    }

    public Order findById(String id){
        return orders.get(id);
    }
}
