package vn.titv.spring.mvcsecurity.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.titv.spring.mvcsecurity.entity.User;
import vn.titv.spring.mvcsecurity.service.StudentService;
import vn.titv.spring.mvcsecurity.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private StudentService studentService;

    @GetMapping("/home")
    public String home(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "student/account";
    }

    @PostMapping("/update/{username}")
    public String updateUserEnabled(@PathVariable String username, @RequestParam boolean enabled) {
        User user = userService.findByUsername(username);
        if (user != null) {
            user.setEnabled(enabled);
            userService.saveUser2(user);
        }
        return "redirect:/admin/home";
    }

    @GetMapping("/delete/{username}")
    public String deleteUser(@PathVariable String username) {
        User user = userService.findByUsername(username);
        if (user != null) {
            userService.deleteByUsername(username);
            String email = user.getEmail();
            studentService.deleteStudentByEmail(email);
        }
        return "redirect:/admin/home";
    }

    @GetMapping("/schedule")
    public String schedule() {
        return "schedule/schedule_admin";
    }




}
