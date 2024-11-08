package vn.titv.spring.mvcsecurity.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.titv.spring.mvcsecurity.entity.Scores;
import vn.titv.spring.mvcsecurity.entity.Student;
import vn.titv.spring.mvcsecurity.service.ScoresService;
import vn.titv.spring.mvcsecurity.service.StudentService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/home_class")
public class ClassController {

    private final StudentService studentService;
    private final ScoresService scoresService;

    @Autowired
    public ClassController(StudentService studentService, ScoresService scoresService) {
        this.studentService = studentService;
        this.scoresService = scoresService;
    }

    @GetMapping
    public String showClassPage(Model model) {
        return "class/index";
    }

    @GetMapping("/search")
    public String search(@RequestParam(name = "lop") String lop, Model model) {
        List<Student> students = studentService.findStudentsByClass(lop);
        Map<Integer, Scores> scores = scoresService.getScoresByStudentIds(students.stream().map(Student::getId).collect(Collectors.toList()));
        model.addAttribute("students", students);
        model.addAttribute("scores", scores);
        return "class/index";
    }


}
