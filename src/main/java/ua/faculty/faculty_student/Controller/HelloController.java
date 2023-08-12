package ua.faculty.faculty_student.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

  @GetMapping("/")
  public String getHelloPage() {
    return "hello";
  }
}
