package vn.titv.spring.mvcsecurity.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.titv.spring.mvcsecurity.dao.UserRepository;
import vn.titv.spring.mvcsecurity.entity.Student;
import vn.titv.spring.mvcsecurity.entity.User;
import vn.titv.spring.mvcsecurity.service.StudentService;
import vn.titv.spring.mvcsecurity.service.UserService;

import javax.management.relation.Role;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private StudentService studentService;

    @Autowired
    PasswordEncoder passwordEncoder;


    @GetMapping("/home")
    public String home(Model model, Principal principal) {
        // Hiển thị thông tin người dùng
        String username = principal.getName();
        User user = userService.findByUsername(username);
        model.addAttribute("user", user);
        return "account/information";
    }

    @GetMapping("/update")
    public String update(@RequestParam("id") Integer id, Model model){
        Student student = studentService.getStudentById(id);
        String username = student.getEmail();
        User user = userService.findByUsername(username);
        model.addAttribute("student", student);
        model.addAttribute("user", user);
        return "update/update-form";
    }
    @PostMapping("/update")
    public String updateProfile(@ModelAttribute User user, Principal principal) {
        // Lấy tên đăng nhập của người dùng hiện tại
        String username = principal.getName();

        // Tìm người dùng hiện tại trong cơ sở dữ liệu
        User currentUser = userService.findByUsername(username);


        // Cập nhật thông tin người dùng hiện tại với thông tin từ form
        if (currentUser != null) {
            currentUser.setFirstName(user.getFirstName());
            currentUser.setLastName(user.getLastName());
            currentUser.setBirthDay(user.getBirthDay());
            currentUser.setEmail(user.getEmail());
            currentUser.setPhone(user.getPhone());
            currentUser.setAddress(user.getAddress());

            // Lưu thông tin vào cơ sở dữ liệu
            userService.saveUser2(currentUser);



            if (currentUser.getRoles().contains("ROLE_STUDENT")) {

                Student student = studentService.findByEmail(user.getEmail()); // Tìm sinh viên theo email hoặc một thuộc tính duy nhất

                if (student != null) {
                    // Cập nhật thông tin của sinh viên đã tồn tại
                    student.setFirstName(user.getFirstName());
                    student.setLastName(user.getLastName());
                    student.setNgaySinh(user.getBirthDay());
                    student.setSoDienThoai(user.getPhone());
                    student.setDiachi(user.getAddress());
                    studentService.addStudent(student);
                } else {
                    // Tạo mới sinh viên nếu chưa tồn tại
                    student = new Student();
                    student.setFirstName(user.getFirstName());
                    student.setLastName(user.getLastName());
                    student.setEmail(user.getEmail());
                    student.setSoDienThoai(user.getPhone());
                    student.setDiachi(user.getAddress());
                    student.setNgaySinh(user.getBirthDay());
                    studentService.addStudent(student);
                }
            }
        }

        return "redirect:/user/home"; // Chuyển hướng về trang thông tin
    }

    @GetMapping("/create")
    public String create(Model model){
        User users = new User();
        model.addAttribute("users", users);
        return "student/create";
    }



    @PostMapping("/save")
    public String saveUser(@ModelAttribute User user, Model model) {
        if (userService.existsByUsername(user.getUsername())) {
            model.addAttribute("error", "Username đã tồn tại, vui lòng sử dụng Username khác!");
            return "student/create"; // Trả về trang đăng ký với thông báo lỗi
        }
        if (userService.existsByEmail(user.getEmail())) {
            model.addAttribute("error2", "Email đã tồn tại, vui lòng sử dụng Email khác!");
            return "student/create";
        }


        // Lưu thông tin user vào cơ sở dữ liệu
        userService.saveUser(user);

        User user1 = userService.findByEmail(user.getEmail());

        if (user1.getRoles().contains("ROLE_STUDENT")) {
            // Tạo và lưu thông tin student vào cơ sở dữ liệu
            Student student = new Student();
            student.setFirstName(user.getFirstName());
            student.setLastName(user.getLastName());
            student.setEmail(user.getEmail());
            student.setSoDienThoai(user.getPhone());
            student.setDiachi(user.getAddress());
            student.setNgaySinh(user.getBirthDay());

            studentService.addStudent(student);
            return "/admin/index"; // Điều hướng đến trang chính nếu thành công

        }
        return "/admin/index";
    }



    @GetMapping("/list")
    public String listAll(Model model){
        List<Student> students = studentService.getAllStudents();
        model.addAttribute("students", students);
        return "student/index";

    }

    @GetMapping("/accountTeacher")
    public String account(Model model){
        List<User> users = userService.findByRoles("ROLE_TEACHER");
        model.addAttribute("users",users);
        return "teacher/index";
    }

    @GetMapping("/scheduleTeacher")
    public String scheduleTeacher(){
        return "/schedule/schedule_teacher";

    }

    @GetMapping("/showPasswordPage")
    public String showUpdatePasswordForm(Model model) {
        model.addAttribute("user", new User());
        return "password/index";
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
            return "redirect:/user/showPasswordPage";
        }

        // Kiểm tra mật khẩu mới và xác nhận
        if (!newPassword.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "Mật khẩu mới và xác nhận mật khẩu không khớp!");
            return "redirect:/user/showPasswordPage";
        }

        // Cập nhật mật khẩu
        userService.updatePassword(user.getUsername(), passwordEncoder.encode(newPassword));

        // Chuyển hướng đến trang chủ
        return "admin/index";
    }

    @GetMapping("/filter")
    public String fileUsers(@RequestParam("role") String role , Model model){
       List<User> users = userService.findByRoles(role);
       model.addAttribute("users",users);
        return "account/index";
    }

    @GetMapping("/filterEnable")
    public String filterEnable(Model model){
        List<User> users = userService.findByEnabledFalse();
        long unverifiedCount = users.stream().filter(user -> !user.isEnabled()).count();
        model.addAttribute("users", users);
        model.addAttribute("unverifiedCount", unverifiedCount);
        return "account/index";
    }


}
