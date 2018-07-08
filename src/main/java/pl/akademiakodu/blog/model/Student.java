package pl.akademiakodu.blog.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor(access = AccessLevel.PUBLIC)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name = "student")
public class Student {

    // @Getter
    // @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @Getter
    // @Setter
    @Column(name = "name")
    private String name;

    // @Getter
    // @Setter
    @Column(name = "email")
    private String email;

    //  @JsonManagedReference
    //  @Getter
    //  @Setter
    @ManyToMany
    @JoinTable(name = "course_student", joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    private List<Courses> courses;

    //  @Getter
    //  @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_details_id")
    private StudentDetails studentDetails;


    //===============gett sett const =========================


    public Student() {
    }

    public Student(String name, String email, StudentDetails studentDetails) {
        this.name = name;
        this.email = email;
        this.studentDetails = studentDetails;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public List<Courses> getCourses() {
        return courses;
    }

    public void setCourses(List<Courses> courses) {
        this.courses = courses;
    }

    public StudentDetails getStudentDetails() {
        return studentDetails;
    }

    public void setStudentDetails(StudentDetails studentDetails) {
        this.studentDetails = studentDetails;
    }


}
