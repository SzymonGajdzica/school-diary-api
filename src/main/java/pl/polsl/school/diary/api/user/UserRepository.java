package pl.polsl.school.diary.api.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;
import java.util.Set;

@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String userName);

    @Query(value = "select id from users where dtype in ('Teacher', 'Parent') and id in :ids", nativeQuery = true)
    Set<Long> findIdsByIdIsIn(@Param("ids") Set<Long> ids);

    Set<User> findAllByRoleIdIsIn(Set<Long> ids);

}

