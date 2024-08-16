package vn.titv.spring.mvcsecurity.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.titv.spring.mvcsecurity.entity.User;
import vn.titv.spring.mvcsecurity.service.UserService;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping()
public class LoginController {

    private  User user;
    @Autowired
    private UserService userService;

    @GetMapping("/showLoginPage")
    public String showLoginPageStudent() {
        return "register_login/login";
    }





}
