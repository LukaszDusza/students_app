package pl.akademiakodu.blog.controllers;


import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import pl.akademiakodu.blog.Repositories.CoursesRepository;
import pl.akademiakodu.blog.Repositories.StudentRepository;
import pl.akademiakodu.blog.model.Courses;
import pl.akademiakodu.blog.model.Student;
import pl.akademiakodu.blog.model.StudentDetails;

import java.util.*;

@RestController
@RequestMapping("/api")
public class StudentController {

    @Autowired
    StudentRepository studentRepository;


    @GetMapping("/students/all")
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @PostMapping("/students/add")
    public Student addNewStudent(@ModelAttribute Student newStudent, @ModelAttribute StudentDetails studentDetails) {
        StudentDetails sd;
        sd = studentDetails;
        sd.setRegisterDate(new Date());
        Student result = new Student(
                newStudent.getName(),
                newStudent.getEmail(),
                studentDetails
        );
        return studentRepository.save(result);
    }

    @PutMapping("students/update/{id}")
    public String updateStudentById(@ModelAttribute Student student, @ModelAttribute StudentDetails studentDetails, @PathVariable Long id) {
        Optional<Student> resultOptional = studentRepository.findById(id);
        resultOptional.ifPresent(result -> {
            result = resultOptional.get();
            //    result.setEmail(student.getEmail());
            //    result.setStudentDetails(studentDetails);
            result.getStudentDetails().setPhoneNumber(studentDetails.getPhoneNumber());
            result.getStudentDetails().setUpdateDate(new Date());
            studentRepository.save(result);
        });
        return "Student form id: " + id + " updated!";
    }

   /* @PostMapping("/students/form/update/phone")
    public String updateStudentByForm(@RequestParam Long id, @RequestParam String phoneNumber) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        studentOptional.ifPresent(result -> {
            result = studentOptional.get();
            //    result.setEmail(student.getEmail());
            //    result.setStudentDetails(studentDetails);
            result.getStudentDetails().setPhoneNumber(phoneNumber);
            studentRepository.save(result);
        });
        return "Student form id: " + id + " updated!";

    }*/


    @DeleteMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable("id") Long id) {
        studentRepository.deleteById(id);
        return "student from id: " + id + " deleted!";
    }

    @Autowired
    CoursesRepository coursesRepository;

    @GetMapping("/students/{idStudent}/course/{idCourse}")
    public String addCourseToStudent(
            @PathVariable Long idStudent,
            @PathVariable Long idCourse,
            //   @ModelAttribute Student student,
            @ModelAttribute Courses courses) {
        Optional<Student> studentOptional = studentRepository.findById(idStudent);
        Optional<Courses> courseOptional = coursesRepository.findById(idCourse);
        studentOptional.ifPresent(result -> {
            //   Courses c = courseOptional.get();
            //   Student s = studentOptional.get();
            List<Courses> courseList = studentOptional.get().getCourses();
            //  List<Courses> newCourse = new ArrayList<>();
            courseList.add(courseOptional.get());
            //   newCourse.add(c);
            result.setCourses(courseList);
            //    result.setCourses(studentOptional.get().getCourses().add(courseOptional.get()));
            studentRepository.save(result);
        });
        return "Student id: " + idStudent + " has new Course: " + idCourse;
    }

}


