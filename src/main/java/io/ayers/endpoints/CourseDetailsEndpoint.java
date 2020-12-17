package io.ayers.endpoints;

import io.ayers.courses.*;
import io.ayers.exception.CourseNotFoundException;
import io.ayers.service.CourseDetailsService;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.stream.Collectors;

@Endpoint
public class CourseDetailsEndpoint {

    private final CourseDetailsService service;

    public CourseDetailsEndpoint(CourseDetailsService service) {
        this.service = service;
    }

    @PayloadRoot(namespace = "http://ayers.io/courses", localPart = "GetCourseDetailsRequest")
    @ResponsePayload
    public GetCourseDetailsResponse processCourseDetailsRequest(@RequestPayload GetCourseDetailsRequest request) {
        var response = new GetCourseDetailsResponse();
        var course = service.findById(request.getId());

        if (course == null)
            throw new CourseNotFoundException(String.format("Invalid Course Id: %d", request.getId()));

        var courseDetails = mapCourse(course);

        response.setCourseDetails(courseDetails);

        return response;
    }

    @PayloadRoot(namespace = "http://ayers.io/courses", localPart = "GetAllCourseDetailsRequest")
    @ResponsePayload
    public GetAllCourseDetailsResponse processAllCourseDetailsRequest(@RequestPayload GetAllCourseDetailsRequest request) {
        var response = new GetAllCourseDetailsResponse();
        var courseDetails = service
                .findAll()
                .stream()
                .map(this::mapCourse)
                .collect(Collectors.toList());

        response.getCourseDetails()
                .addAll(courseDetails);

        return response;
    }

    @PayloadRoot(namespace = "http://ayers.io/courses", localPart = "DeleteCourseDetailsRequest")
    @ResponsePayload
    public DeleteCourseDetailsResponse deleteCourseDetailsRequest(@RequestPayload DeleteCourseDetailsRequest request) {
        var response = new DeleteCourseDetailsResponse();
        var status = service.deleteById(request.getId());
        response.setStatus(status);
        return response;
    }

    private CourseDetails mapCourse(io.ayers.bean.Course course) {
        var courseDetails = new CourseDetails();
        courseDetails.setId(course.getId());
        courseDetails.setName(course.getName());
        courseDetails.setDescription(course.getDescription());
        return courseDetails;
    }
}
