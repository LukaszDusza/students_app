package pl.akademiakodu.blog.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.akademiakodu.blog.Repositories.CoursesRepository;
import pl.akademiakodu.blog.Repositories.InstructorRepository;
import pl.akademiakodu.blog.model.CourseDescription;
import pl.akademiakodu.blog.model.Courses;
import pl.akademiakodu.blog.model.Instructor;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CourseController {

    @Autowired
    CoursesRepository coursesRepository;

    @GetMapping("/courses/all")
    public List<Courses> getAllCourses() {
        return coursesRepository.findAll();
    }

    /*
    @PostMapping("courses/add")
    public Courses addNewCourse(@ModelAttribute Courses course) {
        Courses result = new Courses();
        result.setTitle(course.getTitle());
        result.setLevel(course.getLevel());
        return coursesRepository.save(result);
    }
    */

    @PostMapping("courses/add")
    public Courses addNewCourse(@ModelAttribute Courses course) {
        //  Courses result = new Courses();
        //  result.setTitle(course.getTitle());
        //  result.setLevel(course.getLevel());
        return coursesRepository.save(course);
    }

    @PostMapping("courses/add/desc")
    public Courses addNewCourseWithDescription(@ModelAttribute Courses course, @ModelAttribute CourseDescription courseDescription) {
        Courses result = new Courses(
                course.getTitle(),
                course.getLevel(),
                courseDescription
        );
        return coursesRepository.save(result);
    }

    @PutMapping("courses/update/{id}")
    public String updateCourse(@ModelAttribute Courses courses, @PathVariable Long id) {
        Optional<Courses> resultOptional = coursesRepository.findById(id);
        resultOptional.ifPresent(result -> {
            result.setLevel(courses.getLevel());
            result.setTitle(courses.getTitle());
            coursesRepository.save(result);
        });
        return "course id: " + id + " updated!";
    }

    @DeleteMapping("/courses/delete/{id}")
    public String deleteCourse(@PathVariable("id") Long id) {
        coursesRepository.deleteById(id);
        return "course from id: " + id + " deleted!";
    }

}
