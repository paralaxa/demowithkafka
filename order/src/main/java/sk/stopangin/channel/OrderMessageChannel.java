package sk.stopangin.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface OrderMessageChannel {

    String ORDER_CREATED_OUTPUT = "orderCreated";

    String ORDER_VALIDATED_INPUT = "orderValidated";

    String ACCOUNT_ERROR_INPUT = "accountError";

    String ORDER_NOT_IN_STORE_INPUT = "orderNotInStore";


    @Output(ORDER_CREATED_OUTPUT)
    MessageChannel orderCreated();

    @Input(ORDER_VALIDATED_INPUT)
    SubscribableChannel orderValidated();

    @Input(ACCOUNT_ERROR_INPUT)
    SubscribableChannel accountError();

    @Input(ORDER_NOT_IN_STORE_INPUT)
    SubscribableChannel orderNotInStore();
}
