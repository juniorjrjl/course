package com.ead.course.consumer;

import com.ead.course.dto.UserEventDTO;
import com.ead.course.enumeration.ActionType;
import com.ead.course.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static com.ead.course.enumeration.ActionType.CREATE;
import static org.springframework.amqp.core.ExchangeTypes.FANOUT;

@Component
@AllArgsConstructor
public class UserConsumer {

    private final UserService userService;

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "${ead.broker.queue.name}", durable = "true"),
                    exchange = @Exchange(value = "${ead.broker.exchange.user-event-exchange}", type = FANOUT, ignoreDeclarationExceptions = "true"))
    )
    public void listenUserEvent(@Payload final UserEventDTO dto){
        var model = dto.toModel();
        switch (ActionType.valueOf(dto.actionType())){
            case CREATE:
            case UPDATE:
                userService.save(model);
                break;
            case DELETE:
                userService.delete(dto.id());
                break;
        }
    }



}
