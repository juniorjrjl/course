package com.ead.course.adapter.outbound.persistence;

import com.ead.course.adapter.mapper.UserMapper;
import com.ead.course.adapter.spec.UserSpec;
import com.ead.course.core.domain.PageInfo;
import com.ead.course.core.domain.UserDomain;
import com.ead.course.core.domain.UserFilterDomain;
import com.ead.course.core.port.persistence.UserPersistencePort;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component

@AllArgsConstructor
public class UserPersistencePortImpl implements UserPersistencePort {

    private final UserJpaRepository userJpaRepository;
    private final UserMapper userMapper;

    @Transactional
    @Override
    public UserDomain save(final UserDomain domain) {
        var entity = userJpaRepository.save(userMapper.toEntity(domain));
        return userMapper.toDomain(entity);
    }

    @Transactional
    @Override
    public void delete(final UUID id) {
        userJpaRepository.deleteById(id);
    }

    @Transactional
    @Override
    public List<UserDomain> findAll(final UserFilterDomain filterDomain, final PageInfo pageInfo) {
        var pageable = PageRequest.of(pageInfo.pageNumber(), pageInfo.pageSize());
        return userJpaRepository.findAll(UserSpec.findAllFilter(userMapper.toDTO(filterDomain)), pageable).getContent().stream().map(userMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Optional<UserDomain> findById(final UUID id) {
        return userJpaRepository.findById(id).map(userMapper::toDomain);
    }

}
