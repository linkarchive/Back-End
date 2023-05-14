package project.linkarchive.backend.url.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.linkarchive.backend.url.domain.Url;

import java.util.List;

public interface UrlRepository extends JpaRepository<Url, Long> {

    @Query("SELECT u FROM Url u " +
            "WHERE u.user.id=:userId ")
    List<Url> getByUserId(Long userId);

}