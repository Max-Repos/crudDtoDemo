package in.rajaram.userLogin.Service;

import in.rajaram.userLogin.Entity.Course;
import in.rajaram.userLogin.Entity.User;
import in.rajaram.userLogin.Repository.CourseRepository;
import in.rajaram.userLogin.Repository.UserDetailRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailService {

    @Value("${app.env}")
    private String env;

    @Autowired
    private UserDetailRepository repository;

    @Autowired
    private CourseRepository courseRepository;

    @Transactional
    public User findUserById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    @Transactional
    public User createUser(User user) {

        if (user.getCourses() != null) {
            List<Course> existingCourses = new ArrayList<>();
            Set<Course> uniqueValues = new HashSet<>();
            for (Course course : user.getCourses()) {
                Course existingCourse = courseRepository.findByNameIgnoreCase(course.getName())
                        .orElseGet(() -> courseRepository.save(course));
                if (uniqueValues.add(existingCourse)) {
                    existingCourses.add(existingCourse);
                }
            }

            user.setCourses(new ArrayList<>(existingCourses));
        }

        return repository.save(user);
    }

    @Transactional
    public List<User> findAllUsers() {
        List<User> users = repository.findAll();
        return users.stream()
                .map(val -> {
                    val.setName(val.getName() + env);
                    return val;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public User updateUser(Long id, User user) {

        User existingUser = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setUserAddress(user.getUserAddress());

        return repository.save(existingUser);
    }

    @Transactional
    public void deleteUser(Long id) {
        repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        repository.deleteById(id);

    }
}
