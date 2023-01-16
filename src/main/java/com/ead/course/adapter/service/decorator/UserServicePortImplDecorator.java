package com.ead.course.adapter.service.decorator;

import com.ead.course.core.domain.UserDomain;
import com.ead.course.core.port.service.UserServicePort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@AllArgsConstructor
public class UserServicePortImplDecorator implements UserServicePort {

    private final UserServicePort userServicePort;

    @Transactional
    @Override
    public UserDomain save(final UserDomain domain) {
        return userServicePort.save(domain);
    }

    @Transactional
    @Override
    public void delete(final UUID id) {
        userServicePort.delete(id);
    }
}
