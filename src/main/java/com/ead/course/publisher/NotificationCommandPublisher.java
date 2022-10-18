package com.ead.course.publisher;

import com.ead.course.dto.NotificationCommandDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NotificationCommandPublisher {

    private final RabbitTemplate rabbitTemplate;

    private final String notificationCommandExchange;

    private final String notificationCommandKey;

    public NotificationCommandPublisher(final RabbitTemplate rabbitTemplate,
                                        @Value("${ead.broker.exchange.notification-command-exchange}") final String notificationCommandExchange,
                                        @Value("${ead.broker.key.notification-command-key}") final String notificationCommandKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.notificationCommandExchange = notificationCommandExchange;
        this.notificationCommandKey = notificationCommandKey;
    }

    public void publishNotificationCommand(final NotificationCommandDTO dto){
        rabbitTemplate.convertAndSend(notificationCommandExchange, notificationCommandKey, dto);
    }

}
