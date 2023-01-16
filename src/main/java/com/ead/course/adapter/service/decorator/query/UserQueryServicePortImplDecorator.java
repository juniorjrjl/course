package com.ead.course.adapter.service.decorator.query;

import com.ead.course.core.domain.PageInfo;
import com.ead.course.core.domain.UserDomain;
import com.ead.course.core.domain.UserFilterDomain;
import com.ead.course.core.port.service.query.UserQueryServicePort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
@AllArgsConstructor
public class UserQueryServicePortImplDecorator implements UserQueryServicePort {

    private final UserQueryServicePort userQueryServicePort;

    @Transactional
    @Override
    public List<UserDomain> findAll(final UserFilterDomain filterDomain, final PageInfo pageInfo) {
        return userQueryServicePort.findAll(filterDomain, pageInfo);
    }

    @Transactional
    @Override
    public UserDomain findById(final UUID id) {
        return userQueryServicePort.findById(id);
    }
}
