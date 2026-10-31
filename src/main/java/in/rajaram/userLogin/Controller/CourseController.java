package in.rajaram.userLogin.Controller;

import in.rajaram.userLogin.DTO.CourseResponse;
import in.rajaram.userLogin.DTO.UserResponse;
import in.rajaram.userLogin.Repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class CourseController {

    @Autowired
    private CourseRepository repository;

    @GetMapping("/allCourse")
    public ResponseEntity<List<CourseResponse>> getCourses() {

        List<CourseResponse> courses = repository.findAll()
                .stream()
                .map(course -> new CourseResponse(
                        course.getId(),
                        course.getName(),
                        course.getUsers()
                                .stream()
                                .map(user -> new UserResponse(
                                        user.getId(),
                                        user.getName(),
                                        user.getEmail()
                                ))
                                .toList()
                ))
                .toList();

        return ResponseEntity.ok(courses);
    }

    @GetMapping(value = "/courses")
    public ResponseEntity<CourseResponse> getCourseById(@RequestParam Long id) {

        return repository.findById(id)
                .map(course -> {
                    List<UserResponse> users = course.getUsers()
                            .stream()
                            .map(user -> new UserResponse(
                                    user.getId(),
                                    user.getName(),
                                    user.getEmail()
                            ))
                            .toList();

                    CourseResponse response = new CourseResponse(
                            course.getId(),
                            course.getName(),
                            users
                    );

                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
