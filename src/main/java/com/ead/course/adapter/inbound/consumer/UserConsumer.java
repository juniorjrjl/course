package com.ead.course.adapter.inbound.consumer;

import com.ead.course.adapter.dto.UserEventDTO;
import com.ead.course.adapter.mapper.UserMapper;
import com.ead.course.core.domain.enumeration.ActionType;

import com.ead.course.core.port.service.UserServicePort;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static org.springframework.amqp.core.ExchangeTypes.FANOUT;

@Component
@AllArgsConstructor
public class UserConsumer {

    private final UserServicePort userServicePort;
    private final UserMapper userMapper;

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "${ead.broker.queue.name}", durable = "true"),
                    exchange = @Exchange(value = "${ead.broker.exchange.user-event-exchange}", type = FANOUT, ignoreDeclarationExceptions = "true"))
    )
    public void listenUserEvent(@Payload final UserEventDTO dto){
        switch (ActionType.valueOf(dto.actionType())) {
            case CREATE, UPDATE -> userServicePort.save(userMapper.toDomain(dto));
            case DELETE -> userServicePort.delete(dto.id());
        }
    }



}
