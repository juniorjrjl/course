package com.ead.course.core.port.persistence;

import com.ead.course.core.domain.UserFilterDomain;
import com.ead.course.core.domain.PageInfo;
import com.ead.course.core.domain.UserDomain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserPersistencePort {

    UserDomain save(final UserDomain domain);

    void delete(final UUID id);

    List<UserDomain> findAll(final UserFilterDomain filterDomain, final PageInfo pageInfo);

    Optional<UserDomain> findById(final UUID id);

}
