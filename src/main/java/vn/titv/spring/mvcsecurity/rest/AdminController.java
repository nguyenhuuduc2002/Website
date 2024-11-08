package vn.titv.spring.mvcsecurity.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.titv.spring.mvcsecurity.entity.Scores;
import vn.titv.spring.mvcsecurity.entity.Student;
import vn.titv.spring.mvcsecurity.entity.User;
import vn.titv.spring.mvcsecurity.service.ScoresService;
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
    @Autowired
    private ScoresService scoresService;

    @GetMapping("/home")
    public String home(Model model) {
        List<User> users = userService.findAll();

        // Đếm số lượng tài khoản chưa xác nhận
        long unverifiedCount = users.stream().filter(user -> !user.isEnabled()).count();

        // Thêm danh sách tài khoản và số lượng tài khoản chưa xác nhận vào model
        model.addAttribute("users", users);
        model.addAttribute("unverifiedCount", unverifiedCount);
        return "account/index";
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

            String email = user.getEmail();
            // Tìm student theo email
            Student student = studentService.findByEmail(email);

            // Xóa tất cả scores liên quan trước khi xóa student
            if (student != null) {
                scoresService.deleteScoresById(student.getId()); // Phương thức xóa điểm theo studentId
                studentService.deleteStudentByEmail(email); // Sau đó xóa student
            }

            // Xóa user theo username
            userService.deleteByUsername(username);


        }

        return "redirect:/admin/home";
    }


    @GetMapping("/schedule")
    public String schedule() {
        return "schedule/schedule_admin";
    }




}
