package vn.titv.spring.mvcsecurity.rest;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import vn.titv.spring.mvcsecurity.entity.User;
import vn.titv.spring.mvcsecurity.service.UserService;

import java.util.Arrays;
import java.util.List;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        List<String> roles = Arrays.asList("ROLE_TEACHER","ROLE_STUDENT");
        model.addAttribute("roles", roles);
        return "register_login/register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
        if (userService.isUsernameExist(user.getUsername())) {
            bindingResult.rejectValue("username", "error.user", "Username đã tồn tại, vui lòng sử dụng username khác");
        }

        // Check if there are any validation errors
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", Arrays.asList("ROLE_ADMIN", "ROLE_TEACHER", "ROLE_STUDENT")); // Re-populate roles
            return "register_login/register";
        }

        // Save user if no errors
        userService.saveUser(user);
        return "redirect:/register?success=true";
    }



}
