package sk.stopangin.shop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import sk.stopangin.channel.ShopMessageChannel;
import sk.stopangin.model.Order;

@Service
@Slf4j
public class ShopServiceImpl implements ShopService {


    @Autowired
    @Qualifier(ShopMessageChannel.ORDER_NOT_IN_STORE_OUTPUT)
    private MessageChannel orderNotInStore;

    @Autowired
    @Qualifier(ShopMessageChannel.ORDER_IN_STORE_OUTPUT)
    private MessageChannel orderInStoreChannel;



    @Override
    public boolean isItemInStore(String itemId) {
        return "itemId_123".equals(itemId);
    }


    @StreamListener(value = ShopMessageChannel.ORDER_CREATED_INPUT)
    public void orderCreated(Order order) {
        if (isItemInStore(order.getItem())) {
            log.info("Order is in store: {}",order);
            orderInStoreChannel.send(MessageBuilder.withPayload(order).build());
        } else {
            log.info("Order is NOT in store: {}",order);
            orderNotInStore.send(MessageBuilder.withPayload(order).build());
        }
    }
}
