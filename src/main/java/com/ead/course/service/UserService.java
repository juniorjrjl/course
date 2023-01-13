package com.ead.course.service;

import com.ead.course.model.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;

public interface UserService {


    UserModel save(final UserModel model);

    void delete(final UUID id);

}
