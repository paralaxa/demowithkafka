package sk.stopangin.service;

        import lombok.extern.slf4j.Slf4j;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.beans.factory.annotation.Qualifier;
        import org.springframework.cloud.stream.annotation.StreamListener;
        import org.springframework.messaging.MessageChannel;
        import org.springframework.messaging.support.MessageBuilder;
        import org.springframework.web.bind.annotation.PostMapping;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RestController;
        import sk.stopangin.channel.OrderMessageChannel;
        import sk.stopangin.model.Order;
        import sk.stopangin.repository.OrderRepository;

        import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("order")
public class OrderServiceImpl implements OrderService {

    @Autowired
    @Qualifier(OrderMessageChannel.ORDER_CREATED_OUTPUT)
    private MessageChannel orderCreatedChannel;

    private OrderRepository orderRepository;


    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    @PostMapping
    public Order create(Order order) {
        order.setState(Order.State.DRAFT);
        log.info("creating order:{}", order);
        orderRepository.save(order);
        orderCreatedChannel.send(MessageBuilder.withPayload(order).build());
        return order;
    }

    @StreamListener(value = OrderMessageChannel.ACCOUNT_ERROR_INPUT)
    public void cancelForAccount(Order order) {
        log.info("canceling order account not valid:{}", order);
        cancel(order.getId());
    }

    @StreamListener(value = OrderMessageChannel.ORDER_NOT_IN_STORE_INPUT)
    public void cancelForStore(Order order) {
        log.info("canceling order item not in store:{}", order);
        cancel(order.getId());
    }
    @Override
    public Order cancel(String id) {
        Order order = orderRepository.findById(id);
        Objects.requireNonNull(order);
        log.info("canceling order:{}", order);
        order.setState(Order.State.CANCELED);
        return order;
    }

    @StreamListener(value = OrderMessageChannel.ORDER_VALIDATED_INPUT)
    public void doProcess(Order order){
        process(order.getId());
    }

    @Override
    public Order process(String id) {
        Order order = orderRepository.findById(id);
        Objects.requireNonNull(order);
        log.info("processing order:{}", order);
        order.setState(Order.State.PROCESSED);
        return order;
    }
}
