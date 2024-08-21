package vn.titv.spring.mvcsecurity.rest;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.titv.spring.mvcsecurity.entity.Scores;
import vn.titv.spring.mvcsecurity.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import vn.titv.spring.mvcsecurity.entity.User;
import vn.titv.spring.mvcsecurity.service.ScoresService;
import vn.titv.spring.mvcsecurity.service.StudentService;
import vn.titv.spring.mvcsecurity.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {
    private StudentService studentService;
    private ScoresService scoresService;

    private UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    public StudentController(StudentService studentService, ScoresService scoresService, UserService userService) {
        this.studentService = studentService;
        this.scoresService = scoresService;
        this.userService = userService;
    }








    @GetMapping("/list")
    public String listAll(Model model){
        List<Student> students = studentService.getAllStudents();

        model.addAttribute("students", students);
        return "home/home";


    }




    @GetMapping("/create")
    public String create(Model model){
        Student student = new Student();
        model.addAttribute("student", student);
        return "create/create-form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("student") Student student){
        studentService.updateStudent(student);
        String email = student.getEmail();
        User user = userService.findByEmail(email);

        if (user != null) {
            // Update the user details based on the student details
            user.setFirstName(student.getFirstName());
            user.setLastName(student.getLastName());
            user.setBirthDay(student.getNgaySinh());
            user.setEmail(student.getEmail());
            user.setPhone(student.getSoDienThoai());
            user.setAddress(student.getDiachi());

            // Save the updated user
            userService.saveUser2(user);
        }
        return "redirect:/students/list";
    }

    @GetMapping("/update")
    public String update(@RequestParam("id") Integer id, Model model){
        Student student = studentService.getStudentById(id);
        model.addAttribute("student", student);
        return "update/update-form";
    }


    @GetMapping("/delete")
    public String delete(@RequestParam("id") Integer id, Model model){
        studentService.deleteStudentById(id);
        scoresService.deleteScoresById(id);
        return "redirect:/students/list";
    }




    @GetMapping("/schedule")
    public String showSchedule(Model model, Principal principal) {
        // Hiển thị thông tin người dùng
        String username = principal.getName();
        User users = userService.findByUsername(username);
        Student students = studentService.findByEmail(users.getEmail());
        String studentClass = students.getLop();
        if (studentClass != null){
            model.addAttribute("studentClass", studentClass);
            return "schedule/schedule_student";
        }else{
            return "schedule/no_schedule";
        }

    }

    @GetMapping("/showPasswordPage")
    public String showUpdatePasswordForm(Model model) {
        model.addAttribute("user", new User());
        return "student/change_password";
    }

    @PostMapping("/updatePassword")
    public String updatePassword(@RequestParam("currentPassword") String currentPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 Principal principal,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
        String username = principal.getName();
        User user = userService.findByUsername(username);

        // Kiểm tra mật khẩu hiện tại
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            redirectAttributes.addFlashAttribute("error", "Mật khẩu hiện tại không đúng!");
            return "redirect:/students/showPasswordPage";
        }

        // Kiểm tra mật khẩu mới và xác nhận
        if (!newPassword.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "Mật khẩu mới và xác nhận mật khẩu không khớp!");
            return "redirect:/students/showPasswordPage";
        }

        // Cập nhật mật khẩu
        userService.updatePassword(user.getUsername(), passwordEncoder.encode(newPassword));

        // Chuyển hướng đến trang chủ
        return "public/homepage";
    }





}
