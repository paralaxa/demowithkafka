package sk.stopangin.bank.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import sk.stopangin.channel.BankMessageChannel;
import sk.stopangin.model.Order;

@Service
@Slf4j
public class BankServiceImpl implements BankService {
    @Autowired
    @Qualifier(BankMessageChannel.ACCOUNT_ERROR_OUTPUT)
    private MessageChannel accountErrorChannel;

    @Autowired
    @Qualifier(BankMessageChannel.ORDER_VALIDATED_OUTPUT)
    private MessageChannel orderValidChanel;

    @Override
    public boolean isAccountValid(String accountId) {
        return "accountId_123".equals(accountId);
    }

    @StreamListener(value = BankMessageChannel.ORDER_IN_STORE_INPUT)
    public void orderCreated(Order order) {
        if (isAccountValid(order.getCardId())) {
            log.info("account is valid for order:{}",order);
            orderValidChanel.send(MessageBuilder.withPayload(order).build());
        } else {
            log.info("account is NOT valid for order:{}",order);
            accountErrorChannel.send(MessageBuilder.withPayload(order).build());
        }
    }
}
