package com.ead.course.service;

import com.ead.course.model.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;

public interface UserService {

    Page<UserModel> findAll(final Specification<UserModel> spec, final Pageable pageable);

    UserModel save(final UserModel model);

    void delete(final UUID id);

    Optional<UserModel> findById(final UUID id);
}
