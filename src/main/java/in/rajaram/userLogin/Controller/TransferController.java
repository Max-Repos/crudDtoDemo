package in.rajaram.userLogin.Controller;

import in.rajaram.userLogin.DTO.CourseResponse;
import in.rajaram.userLogin.DTO.TransferRequest;
import in.rajaram.userLogin.DTO.UserResponse;
import in.rajaram.userLogin.Repository.CourseRepository;
import in.rajaram.userLogin.Service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/users")
@RequiredArgsConstructor
public class TransferController {
    private final TransferService transferService;
    private final CourseRepository repository;

    @PostMapping(value = "/transfer")
    private ResponseEntity<String> transferAmount(@RequestBody TransferRequest request) {
        return ResponseEntity.ok().body(transferService.transfer(request));
    }

    @GetMapping("/coursesre")
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
}
