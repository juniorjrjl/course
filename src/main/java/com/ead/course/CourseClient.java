package com.ead.course;

import com.ead.course.dto.ResponsePageDTO;
import com.ead.course.dto.UserDTO;
import com.ead.course.service.UtilsService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

import static org.springframework.http.HttpMethod.GET;

@Component
@AllArgsConstructor
@Log4j2
public class CourseClient {

    private final RestTemplate restTemplate;
    private final UtilsService utilsService;

    public Page<UserDTO> getAllUsersByCourse(final Pageable pageable, final UUID courseId) {
        ResponseEntity<ResponsePageDTO<UserDTO>> result = null;
        var url = utilsService.createUrl(pageable, courseId);
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
}
