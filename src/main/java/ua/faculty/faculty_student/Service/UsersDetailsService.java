package ua.faculty.faculty_student.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.faculty.faculty_student.Repository.UsersRepository;

@Service
public class UsersDetailsService implements UserDetailsService {

  private final UsersRepository usersRepository;

  @Autowired
  public UsersDetailsService(UsersRepository usersRepository) {
    this.usersRepository = usersRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return usersRepository.findByUsername(username);
  }
}
