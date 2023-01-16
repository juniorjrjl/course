package com.ead.course.core.port.publisher;

import com.ead.course.adapter.domain.NotificationCommandDTO;

public interface NotificationCommandPublisherPort {

    void publishNotificationCommand(final NotificationCommandDTO dto);

}
