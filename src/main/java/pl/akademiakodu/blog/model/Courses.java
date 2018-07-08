package pl.akademiakodu.blog.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
/*tzw. circular references - zapobiega towrzeniu petli obiektu w obiekcie*/
@Entity
@Table(name = "courses")
public class Courses {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*Różne mechanizmy generowania kluczy głównych w procesie mapowania modelu obiektowego narelacyjny*/
/*@Id oznacza adnotację dla klucza głównego.

Adnotacja @GeneratedValue (strategy=GenerationType.<...>)
oznacza strategię tworzenia wartości kluczy głównych, zależną od systemu baz danych
(javax.persistence.GeneratedValue, javax.persistence.GenerationType).

1. Domyślna strategia pozwala na przejęcie odpowiedzialności za
generowanie kluczy głównych przez TopLink, który posługuje się
pomocniczą tabelą przechowującą wartości potrzebne do generowania
kluczy.
@GeneratedValue(strategy=GenerationType.AUTO)

2. Wykorzystanie mechanizmu identyczności IDENTITY kolumny do generowania
wartości klucza głównego oznacza odpowiedzialność za generowanie
wartości kluczy głównych przez bazę danych*/

    @Column(name = "title")
    private String title;

    @Column(name = "level")
    private String level;


    @JsonBackReference // nie wyswietla pola student w json.
    @ManyToMany(cascade = {CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name = "course_student", joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    private List<Student> student;

    //   @JsonManagedReference
    @JsonBackReference // nie wyswietla pola instructor w json.
    @ManyToOne(cascade = {CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "course_description_id")
    private CourseDescription courseDescription;


    //===============gett sett const =========================


    public Courses() {
    }

    public Courses(String title, String level) {
        this.title = title;
        this.level = level;
    }

    public Courses(String title, String level, CourseDescription courseDescription) {
        this.title = title;
        this.level = level;
        this.courseDescription = courseDescription;
    }

    public Courses(String title, String level, List<Student> student, Instructor instructor, CourseDescription courseDescription) {
        this.title = title;
        this.level = level;
        this.student = student;
        this.instructor = instructor;
        this.courseDescription = courseDescription;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "Courses{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", level='" + level + '\'' +
                '}';
    }

    public List<Student> getStudent() {
        return student;
    }

    public void setStudent(List<Student> student) {
        this.student = student;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public CourseDescription getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(CourseDescription courseDescription) {
        this.courseDescription = courseDescription;
    }
}
