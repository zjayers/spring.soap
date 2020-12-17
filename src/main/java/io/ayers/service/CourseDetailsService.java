package io.ayers.service;

import io.ayers.bean.Course;
import io.ayers.courses.Status;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseDetailsService {

    private static final List<Course> courses = new ArrayList<>();

    static {
        courses.add(new Course(1, "Spring", "Description"));
        courses.add(new Course(2, "Spring 2", "Description 2"));
        courses.add(new Course(3, "Spring 3", "Description 3"));
    }

    public Course findById(int id) {

        for (var course : courses) {
            if (course.getId() == id)
                return course;
        }

        return null;
    }

    public List<Course> findAll() {
        return courses;
    }

    public Status deleteById(int id) {

        var iterator = courses.iterator();

        while (iterator.hasNext()) {
            var course = iterator.next();

            if (course.getId() == id) {
                iterator.remove();
                return Status.SUCCESS;
            }
        }

        return Status.FAILURE;
    }
}
