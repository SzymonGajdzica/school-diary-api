package pl.polsl.school.diary.api.authentication;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.polsl.school.diary.api.exception.NotAuthorizedActionException;
import pl.polsl.school.diary.api.exception.NotImplementedException;
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
import pl.polsl.school.diary.api.user.UserView;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/authenticate")
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

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserView registerUser(@RequestBody UserPost userPost) {
        User user = new User();
        user.setUsername(userPost.getUsername());
        if(userRepository.findByUsername(user.getUsername()).isPresent())
            throw new UsernameAlreadyUsedException(user.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userPost.getPassword()));
        user.setEmail(userPost.getEmail());
        user.setSurname(userPost.getSurname());
        user.setName(userPost.getName());
        Role role = roleRepository
                .findById(userPost.getRoleId())
                .orElseThrow(() -> new WrongRequestBodyException("should have existing role"));

        user.setRole(role);

        switch(user.getRole().getName()) {
            case "Teacher":
                Teacher teacher = new Teacher(user);
                if(userPost.getIsHeadTeacher() == null)
                    throw new WrongRequestBodyException("when registering teacher, field isHeadTeacher is required");
                teacher.setIsHeadTeacher(userPost.getIsHeadTeacher());
                return new UserView(teacherRepository.save(teacher));
            case "Parent":
                Parent parent = new Parent(user);
                return new UserView(parentRepository.save(parent));
            case "Student":
                Student student = new Student(user);
                student.setHasAccount(false);
                return new UserView(studentRepository.save(student));
            default:
                throw new NotImplementedException("registering user with " + user.getRole().getName() + " role");
        }
    }

    @CrossOrigin
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



    /*@CrossOrigin
    @RequestMapping(value= "/login", method=RequestMethod.OPTIONS)
    public String corsHeaders(HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.addHeader("Access-Control-Allow-Headers", "origin, content-type, accept, x-requested-with");
        response.addHeader("Access-Control-Max-Age", "3600");
        return "";
    }*/



}