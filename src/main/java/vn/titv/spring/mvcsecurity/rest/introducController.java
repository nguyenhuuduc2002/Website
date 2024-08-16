package vn.titv.spring.mvcsecurity.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class introducController {

    @GetMapping("/intro")
    public String showIntroduc(){
        return "introduction/intro";
    }

}
