package pl.polsl.school.diary.api.user;

import org.springframework.data.rest.core.config.Projection;

/**
 * Not used now - may be useful in the future
 */
@Projection(types = { User.class })
public interface PSimpleUserView {
    Long getId();

    String getName();

    String getUsername();

    String getSurname();

    String getEmail();

}
