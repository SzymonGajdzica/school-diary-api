package pl.polsl.school.diary.api.note;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.polsl.school.diary.api.exception.EmptyRequestBodyException;
import pl.polsl.school.diary.api.exception.NotAuthorizedActionException;
import pl.polsl.school.diary.api.exception.NotFoundException;
import pl.polsl.school.diary.api.exception.WrongRequestBodyException;
import pl.polsl.school.diary.api.parent.Parent;
import pl.polsl.school.diary.api.student.Student;
import pl.polsl.school.diary.api.teacher.Teacher;
import pl.polsl.school.diary.api.token.TokenRepository;
import pl.polsl.school.diary.api.user.User;
import pl.polsl.school.diary.api.user.UserRepository;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping(value = "/notes")
@AllArgsConstructor
public class NoteController {

    private final TokenRepository tokenRepository;
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public NoteView addNote(@ApiIgnore @RequestHeader(value = "Authorization") String tokenHeader,
                              @RequestBody NotePost notePost) {
        User teacher = tokenRepository.getUserFromHeader(tokenHeader);
        if (!(teacher instanceof Teacher))
            throw new NotAuthorizedActionException("only teacher can add grades");
        Long studentId = notePost.getStudentId();
        String description = notePost.getDescription();
        String title = notePost.getTitle();
        if (studentId == null || description == null || description.isEmpty() || title == null || title.isEmpty())
            throw new EmptyRequestBodyException();
        User student = userRepository.findById(studentId).orElseThrow(() -> new NotFoundException(studentId));
        if (!(student instanceof Student))
            throw new WrongRequestBodyException("field studentId has to point on Student");
        Note note = new Note();
        note.setTitle(title);
        note.setDescription(description);
        note.setTeacher((Teacher) teacher);
        note.setStudent((Student) student);
        return new NoteView(noteRepository.save(note));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<NoteView> getNotes(@ApiIgnore @RequestHeader(value = "Authorization") String tokenHeader) {
        User user = tokenRepository.getUserFromHeader(tokenHeader);
        Set<Note> notes;
        if (user instanceof Parent)
            notes = noteRepository.findAllByStudentParentId(user.getId());
        else if (user instanceof Student)
            notes = noteRepository.findAllByStudentId(user.getId());
        else
            notes = noteRepository.findAllByTeacherId(user.getId());
        return notes.stream().map(NoteView::new).collect(Collectors.toSet());
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public NoteView modifyNote(@ApiIgnore @RequestHeader(value = "Authorization") String tokenHeader,
                                 @PathVariable Long id,
                                 @RequestBody NotePatch notePatch) {
        User teacher = tokenRepository.getUserFromHeader(tokenHeader);
        Note note = noteRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        if (!(teacher instanceof Teacher) || !((Teacher) teacher).getNotes().contains(note))
            throw new NotAuthorizedActionException("only teacher that created this note can modify it");
        if(notePatch.getTitle() != null)
            note.setTitle(notePatch.getTitle());
        if(notePatch.getDescription() != null)
            note.setDescription(notePatch.getDescription());
        note.setUpdateDate(new Date());
        return new NoteView(noteRepository.save(note));
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteNote(@ApiIgnore @RequestHeader(value = "Authorization") String tokenHeader,
                            @PathVariable Long id) {
        User teacher = tokenRepository.getUserFromHeader(tokenHeader);
        Note note = noteRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        if (!(teacher instanceof Teacher) || !((Teacher) teacher).getNotes().contains(note))
            throw new NotAuthorizedActionException("only teacher that created this note can delete it");
        noteRepository.delete(note);
    }

}
