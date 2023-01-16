package com.ead.course.core.port.service;

import com.ead.course.core.domain.UserDomain;

import java.util.UUID;

public interface UserServicePort {

    UserDomain save(final UserDomain domain);

    void delete(final UUID id);

}
