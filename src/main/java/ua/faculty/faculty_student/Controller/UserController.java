package ua.faculty.faculty_student.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ua.faculty.faculty_student.Entity.Users;
import ua.faculty.faculty_student.Service.UsersDetailsService;
import ua.faculty.faculty_student.Service.UsersService;

@Controller
public class UserController {

  private final UsersService usersService;
  private final UsersDetailsService usersDetailsService;

  @Autowired
  public UserController(UsersService usersService, UsersDetailsService usersDetailsService) {
    this.usersService = usersService;
    this.usersDetailsService = usersDetailsService;
  }

  @GetMapping("/settings")
  public String getSettingsPage(Model model) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    Users user = (Users) usersDetailsService.loadUserByUsername(authentication.getName());

    model.addAttribute("users", user);

    return "settings";
  }

  @PostMapping("/settings")
  public String saveSettings(@ModelAttribute(value = "users") Users users,
      @RequestParam(required = false, value = "bio") String bio,
      @RequestParam(required = false, value = "fullName") String fullName) {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    Users user = (Users) usersDetailsService.loadUserByUsername(authentication.getName());

    usersService.updateUserInfo(user.getId(), fullName, bio);

    return "redirect:/";
  }

  @PostMapping("/settings_photo")
  public String savePhotoUser(
      @RequestParam(required = false, value = "avatar") MultipartFile file) {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    Users user = (Users) usersDetailsService.loadUserByUsername(authentication.getName());

    usersService.updatePhotoUser(user.getId(), file);

    return "redirect:/";
  }
}
