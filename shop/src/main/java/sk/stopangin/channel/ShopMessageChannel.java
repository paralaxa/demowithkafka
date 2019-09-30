package sk.stopangin.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface ShopMessageChannel {

    String ORDER_CREATED_INPUT = "orderCreated";

    String ORDER_IN_STORE_OUTPUT = "orderInStore";

    String ORDER_NOT_IN_STORE_OUTPUT = "orderNotInStore";



    @Input(ORDER_CREATED_INPUT)
    SubscribableChannel orderCreated();

    @Output(ORDER_IN_STORE_OUTPUT)
    MessageChannel orderInStore();

    @Output(ORDER_NOT_IN_STORE_OUTPUT)
    MessageChannel orderNotInStore();
}
