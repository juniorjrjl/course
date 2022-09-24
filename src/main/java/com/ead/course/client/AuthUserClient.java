package com.ead.course.client;

import com.ead.course.dto.ResponsePageDTO;
import com.ead.course.dto.UserCourseDTO;
import com.ead.course.dto.UserDTO;
import com.ead.course.service.UtilsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Component
@Log4j2
public class AuthUserClient {

    private final String requestUrlAuthUser;
    private final RestTemplate restTemplate;
    private final UtilsService utilsService;

    public AuthUserClient(@Value("${ead.api.url.auth-user}") final String requestUrlAuthUser,
                          final RestTemplate restTemplate,
                          final UtilsService utilsService) {
        this.requestUrlAuthUser = requestUrlAuthUser;
        this.restTemplate = restTemplate;
        this.utilsService = utilsService;
    }
    public Page<UserDTO> getAllUsersByCourse(final Pageable pageable, final UUID courseId) {
        ResponseEntity<ResponsePageDTO<UserDTO>> result = null;
        var url = requestUrlAuthUser + utilsService.createUrlGetAllUsersByCourses(pageable, courseId);
        log.debug("Request URL : {}", url);
        log.info("Request URL : {}", url);
        try{
            var responseType = new ParameterizedTypeReference<ResponsePageDTO<UserDTO>>() {};
            result = restTemplate.exchange(url, GET, null, responseType);
            log.debug("Response number of elements : {}", result.getBody().getSize());
        }catch (final HttpStatusCodeException ex){
            log.error("Error request / courses ", ex);
        }
        log.info("Ending request / users courseId : {}", courseId);
        return result.getBody();
    }

    public ResponseEntity<UserDTO> getUserById(final UUID userId){
        var url = requestUrlAuthUser + "/users/" + userId;
        return restTemplate.exchange(url, GET, null, UserDTO.class);
    }

    public void postSubscriptionUserInCourse(final UUID courseId, final UUID userId) {
        var url = requestUrlAuthUser + "/users/"+ userId + "/courses/subscription";
        var request = new UserCourseDTO(courseId);
        restTemplate.postForObject(url, request, String.class);
    }
}
