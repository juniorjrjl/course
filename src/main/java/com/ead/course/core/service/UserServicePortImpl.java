package com.ead.course.core.service;

import com.ead.course.core.domain.UserDomain;
import com.ead.course.core.port.persistence.UserPersistencePort;
import com.ead.course.core.port.service.UserServicePort;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class UserServicePortImpl implements UserServicePort {

    private final UserPersistencePort userPersistencePort;

    @Override
    public UserDomain save(final UserDomain domain) {
        return userPersistencePort.save(domain);
    }

    @Override
    public void delete(final UUID id) {
        userPersistencePort.delete(id);
    }

}
