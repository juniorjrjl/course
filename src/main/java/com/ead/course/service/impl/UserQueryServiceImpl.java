package com.ead.course.service.impl;

import com.ead.course.exception.DomainNotFoundException;
import com.ead.course.model.UserModel;
import com.ead.course.repository.UserRepository;
import com.ead.course.service.UserQueryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserQueryServiceImpl implements UserQueryService {

    private final UserRepository userRepository;

    @Override
    public Page<UserModel> findAll(final Specification<UserModel> spec, final Pageable pageable) {
        return userRepository.findAll(spec, pageable);
    }

    @Override
    public UserModel findById(final UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new DomainNotFoundException("User not found"));
    }
}
