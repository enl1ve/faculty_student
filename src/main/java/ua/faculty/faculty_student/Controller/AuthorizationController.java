package ua.faculty.faculty_student.Controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ua.faculty.faculty_student.Entity.Users;
import ua.faculty.faculty_student.Service.UsersService;

@Controller
public class AuthorizationController {

  private final UsersService usersService;

  @Autowired
  public AuthorizationController(UsersService usersService) {
    this.usersService = usersService;
  }

  @GetMapping("/login")
  public String getPageLogin(Model model) {
    model.addAttribute("users", new Users());
    return "authorization/auth";
  }


  @PostMapping("/register")
  public String registerUser(@Valid Users users,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "authorization/auth";
    }

    /* Проверка пользователя в системе */
    if (usersService.getLogicByUser(users.getUsername())) {
      return "/authorization/auth";
    }

    usersService.saveUserToDB(users);

    return "redirect:/login";
  }
}
