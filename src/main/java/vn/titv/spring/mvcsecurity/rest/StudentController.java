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
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {
    private StudentService studentService;
    private ScoresService scoresService;

    private UserService userService;



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
        return "student/index";


    }



    @GetMapping("/listNoClass")
    public String listNoClass(Model model){
        List<Student> students = studentService.findStudentsByClass(null);
        model.addAttribute("students", students);
        return "class/studentNoClass";
    }


    @GetMapping("/create")
    public String create(Model model){
        Student student = new Student();
        model.addAttribute("student", student);
        return "student/create";
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

    @PostMapping("/save2")
    public String save2(@ModelAttribute("student") Student student){
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
        return "redirect:/students/listNoClass";
    }


    @GetMapping("/update")
    public String update(@RequestParam("id") Integer id, Model model){
        Student student = studentService.getStudentById(id);
        model.addAttribute("student", student);
        return "student/update";
    }


    @GetMapping("/update2")
    public String update2(@RequestParam("id") Integer id, Model model){
        Student student = studentService.getStudentById(id);
        model.addAttribute("student", student);
        return "class/update";
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

    @GetMapping("/searchID")
    public String searchID(@RequestParam(name = "id", required = false) Integer id, Model model,Student stu) {
        if (id != null ) {
            Student student = studentService.findStudentById(id);
            if(student!=null){
                model.addAttribute("students", Collections.singletonList(student));
            }else{
                model.addAttribute("error", "không tìm thấy ID này!");
            }
        } else {
            // Nếu không có ID, hiển thị tất cả học sinh
            List<Student> students = studentService.getAllStudents();
            model.addAttribute("students", students);
        }
        return "student/index"; // Điều chỉnh tên view nếu cần
    }







}
