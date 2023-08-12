package ua.faculty.faculty_student.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.faculty.faculty_student.Entity.Users;
import ua.faculty.faculty_student.Repository.UsersRepository;

@Service
public class UsersService {

  private final UsersRepository usersRepository;

  @Autowired
  public UsersService(UsersRepository usersRepository) {
    this.usersRepository = usersRepository;
  }

  public boolean getLogicByUser(String username) {
    return !usersRepository.findAllByUsername(username).isEmpty();
  }

  //Save new user to DB and encrypt pass
  public Users saveUserToDB(Users users) {
    users.setPassword(new BCryptPasswordEncoder().encode(users.getPassword()));

    return usersRepository.save(users);
  }
}
