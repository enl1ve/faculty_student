package ua.faculty.faculty_student.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.faculty.faculty_student.Entity.Users;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    List<Users> findAllByUsernameAndPassword(String username, String password);
    List<Users> findAllByUsername(String username);

    Users findByUsernameAndPassword(String username, String password);

    Users findByUsername(String username);

}
