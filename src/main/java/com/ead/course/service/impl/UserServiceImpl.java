package com.ead.course.service.impl;

import com.ead.course.model.UserModel;
import com.ead.course.repository.CourseRepository;
import com.ead.course.repository.UserRepository;
import com.ead.course.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    @Override
    public Page<UserModel> findAll(final Specification<UserModel> spec, final Pageable pageable) {
        return userRepository.findAll(spec, pageable);
    }

    @Override
    public UserModel save(final UserModel model) {
        return userRepository.save(model);
    }

    @Transactional
    @Override
    public void delete(final UUID id) {
        courseRepository.deleteCourseUserByUser(id);
        userRepository.deleteById(id);
    }

    @Override
    public Optional<UserModel> findById(final UUID id) {
        return userRepository.findById(id);
    }
}
