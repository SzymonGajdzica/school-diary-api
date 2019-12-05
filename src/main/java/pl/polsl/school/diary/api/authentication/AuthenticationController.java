package pl.polsl.school.diary.api.authentication;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.school.diary.api.base.Message;
import pl.polsl.school.diary.api.exception.NotAuthorizedActionException;
import pl.polsl.school.diary.api.exception.UsernameAlreadyUsedException;
import pl.polsl.school.diary.api.exception.WrongRequestBodyException;
import pl.polsl.school.diary.api.parent.Parent;
import pl.polsl.school.diary.api.parent.ParentRepository;
import pl.polsl.school.diary.api.role.Role;
import pl.polsl.school.diary.api.role.RoleRepository;
import pl.polsl.school.diary.api.student.Student;
import pl.polsl.school.diary.api.student.StudentRepository;
import pl.polsl.school.diary.api.teacher.Teacher;
import pl.polsl.school.diary.api.teacher.TeacherRepository;
import pl.polsl.school.diary.api.user.User;
import pl.polsl.school.diary.api.user.UserPost;
import pl.polsl.school.diary.api.user.UserRepository;

import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/authenticate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;
    private final AuthenticationManager authenticationManager;
    private final AuthenticationUtils tokenUtils;
    private final AuthenticationUserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleRepository roleRepository;
    private final ParentRepository parentRepository;
    private final StudentRepository studentRepository;

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Message registerUser(@RequestBody UserPost userPost) {
        User user = new User();
        user.setUsername(userPost.getUsername());
        if(userRepository.findByUsername(user.getUsername()).isPresent())
            throw new UsernameAlreadyUsedException(user.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userPost.getPassword()));
        user.setEmail(userPost.getEmail());
        user.setSurname(userPost.getSurname());
        user.setName(userPost.getName());
        Optional<Role> role = roleRepository.findByName(userPost.getRoleName());
        if(!role.isPresent())
            throw new WrongRequestBodyException("should have existing role");
        user.setRole(role.get());


        switch(userPost.getRoleName()) {
            case "Teacher":
                Teacher teacher = new Teacher(user);
                if(userPost.getIsHeadTeacher() == null)
                    throw new WrongRequestBodyException("should have isHeadTeacher");
                teacher.setIsHeadTeacher(userPost.getIsHeadTeacher());
                teacherRepository.save(teacher);
                break;

            case "Parent":
                Parent parent = new Parent(user);
                parentRepository.save(parent);
                break;

            case "Student":
                Student student = new Student(user);
                student.setHasAccount(false);
                studentRepository.save(student);
                break;

        }

        return new Message("User registered", "Now you can login with given username and password");
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public AuthenticationView createAuthenticationToken(@RequestBody AuthenticationPost authenticationPost) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationPost.getUsername(), authenticationPost.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationPost.getUsername());
            String token = tokenUtils.generateToken(userDetails);
            return new AuthenticationView(token, tokenUtils.getExpirationDateFromToken(token));
        }  catch (Exception e){
            throw new NotAuthorizedActionException("wrong credentials");
        }
    }



}