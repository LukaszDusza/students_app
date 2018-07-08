package pl.akademiakodu.blog.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.akademiakodu.blog.Repositories.StudentRepository;
import pl.akademiakodu.blog.model.Student;

import java.util.Optional;

@Controller
public class ViewController {

@RequestMapping("/home")
 public String homePage(){
    return "home";
}

@RequestMapping("/update/student/phonenumber")
    public String updatePhoneNumber(){
    return "updateStudent";
}

@Autowired
StudentRepository studentRepository;

    @PostMapping("/students/form/update/phone")
    public String updateStudentByForm(@RequestParam Long id, @RequestParam String phoneNumber) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        studentOptional.ifPresent(result -> {
            result = studentOptional.get();
            //    result.setEmail(student.getEmail());
            //    result.setStudentDetails(studentDetails);
            result.getStudentDetails().setPhoneNumber(phoneNumber);
            studentRepository.save(result);
        });
        //   return "Student form id: " + id + " updated!";
        return "updateStudent";
    }

}
