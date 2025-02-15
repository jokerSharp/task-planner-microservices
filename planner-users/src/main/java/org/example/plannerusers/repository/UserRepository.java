package org.example.plannerusers.repository;

import org.example.taskplannerentity.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    void deleteByEmail(String email);

    @Query("""
            SELECT u
            FROM User u where
            :email is not null or :email='' or lower(u.email) like lower(concat('%', :email, '%')) or
            :username is not null or :username='' or lower(u.username) like lower(concat('%', :username, '%'))
            """)
    Page<User> findByParams(@Param("email") String email,
                            @Param("username") String username,
                            Pageable pageable);
}
