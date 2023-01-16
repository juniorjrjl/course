package com.ead.course.core.service.query;

import com.ead.course.core.domain.UserFilterDomain;
import com.ead.course.core.domain.PageInfo;
import com.ead.course.core.domain.UserDomain;
import com.ead.course.core.exception.DomainNotFoundException;
import com.ead.course.core.port.persistence.UserPersistencePort;
import com.ead.course.core.port.service.query.UserQueryServicePort;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class UserQueryServicePortImpl implements UserQueryServicePort {

    private final UserPersistencePort userPersistencePort;

    @Override
    public List<UserDomain> findAll(final UserFilterDomain filterDomain, final PageInfo pageInfo) {
        return userPersistencePort.findAll(filterDomain, pageInfo);
    }

    @Override
    public UserDomain findById(final UUID id) {
        return userPersistencePort.findById(id).orElseThrow(() -> new DomainNotFoundException("User not found"));
    }

}
