package com.ead.course.core.port.service.query;

import com.ead.course.core.domain.UserFilterDomain;
import com.ead.course.core.domain.PageInfo;
import com.ead.course.core.domain.UserDomain;

import java.util.List;
import java.util.UUID;

public interface UserQueryServicePort {

    List<UserDomain> findAll(final UserFilterDomain filterDomain, final PageInfo pageInfo);

    UserDomain findById(final UUID id);

}
