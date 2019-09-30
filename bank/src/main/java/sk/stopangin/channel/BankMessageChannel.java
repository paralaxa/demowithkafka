package sk.stopangin.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface BankMessageChannel {

    String ORDER_IN_STORE_INPUT = "orderInStore";

    String ORDER_VALIDATED_OUTPUT = "orderValidated";

    String ACCOUNT_ERROR_OUTPUT = "accountError";


    @Input(ORDER_IN_STORE_INPUT)
    SubscribableChannel orderInStore();

    @Output(ORDER_VALIDATED_OUTPUT)
    MessageChannel orderValidated();

    @Output(ACCOUNT_ERROR_OUTPUT)
    MessageChannel accountError();
}
