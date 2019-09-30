package sk.stopangin.service;

import sk.stopangin.model.Order;

public interface OrderService {

    Order create(Order order);

     Order cancel(String id);

    Order process(String id);


}
