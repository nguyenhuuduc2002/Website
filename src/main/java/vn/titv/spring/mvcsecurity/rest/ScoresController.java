package vn.titv.spring.mvcsecurity.rest;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/scores")
public class ScoresController {

    private ScoresService scoresService;
    private StudentService studentService;

    private UserService userService;

    @Autowired
    public ScoresController(ScoresService scoresService, StudentService studentService,UserService userService) {
        this.scoresService = scoresService;
        this.studentService = studentService;
        this.userService = userService;
    }



    @GetMapping("/list")
    public String listAll(Model model) {
        List<Scores> scores = scoresService.getAllScores();
        for (Scores sco : scores) {
            double diemtb = Math.round(((sco.getDiemToan() + sco.getDiemLy() + sco.getDiemHoa() + sco.getDiemSinh() + sco.getDiemVan() + sco.getDiemSu() + sco.getDiemDia() + sco.getDiemAnh()) * 10.0 / 8.0)) / 10.0;
            sco.setDiemTrungBinh(diemtb);
        }

        model.addAttribute("scores", scores);
        return "scores/index";

    }

    @GetMapping("/listScore")
    public String listScoresByStudentId(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userService.findByUsername(username);

        Student student = studentService.findByEmail(user.getEmail());

        Scores scores = scoresService.getScoresByStudentId(student.getId());

        if (scores == null) {
            return "scores/scores-student/no_scores"; // Trang thông báo không có điểm
        } else {
            model.addAttribute("scores", scores);
            return "scores/scores-student/scores"; // Trang hiển thị điểm số
        }


    }



    @GetMapping("/create")
    public String create(Model model){
        Scores scores = new Scores();
        List<Student> students = studentService.getAllStudents(); // Lấy danh sách sinh viên từ service
        List<Long> usedIds = scoresService.getUsedStudentIds();
        model.addAttribute("scores", scores);
        model.addAttribute("students", students);
        model.addAttribute("usedIds", usedIds);
        return "scores/create";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("scores") Scores scores){
        double diemtb = Math.round(((scores.getDiemToan() + scores.getDiemLy() + scores.getDiemHoa() + scores.getDiemSinh() + scores.getDiemVan() + scores.getDiemSu() + scores.getDiemDia() + scores.getDiemAnh()) * 10.0 / 8.0)) / 10.0;
        if(diemtb>=8){
            scores.setHocLuc("giỏi");
        }else if(diemtb<8&&diemtb>=6.5){
            scores.setHocLuc("khá");
        }else if(diemtb<6.5&&diemtb>=5){
            scores.setHocLuc("trung bình");
        }else if(diemtb<5&&diemtb>=3.5){
            scores.setHocLuc("yếu");
        }else{
            scores.setHocLuc("kém");
        }
        scores.setDiemTrungBinh(diemtb);

        // Đặt id của Scores bằng id của Student
        if (scores.getStudent() != null) {
            scores.setId(scores.getStudent().getId());
        }

        scoresService.updateScores(scores);
        return "redirect:/scores/list";
    }

    @GetMapping("/update")
    public String update(@RequestParam("id") Integer id, Model model){
        Scores scores = scoresService.getScoresById(id);
        model.addAttribute("scores", scores);
        return "scores/update";
    }
    @GetMapping("/delete")
    public String delete(@RequestParam("id") Integer id, Model model){
        scoresService.deleteScoresById(id);
        return "redirect:/scores/list";
    }

    @GetMapping("/sortByAverage")
    public String sortScoresByAverage(Model model) {
        List<Scores> sortedScores = scoresService.getScoresSortedByAverage();
        model.addAttribute("scores", sortedScores);
        return "scores/index"; // Sử dụng lại trang hiển thị điểm số
    }


    @GetMapping("/searchID")
    public String searchID(@RequestParam(name = "id", required = false) Integer id, Model model) {
        if (id != null) {
            Scores scores = scoresService.getScoresByStudentId(id);
            if (scores != null) {
                model.addAttribute("scores", Collections.singletonList(scores));
            } else {
                model.addAttribute("errorMessage", "Không tìm thấy ID này!"); // Sử dụng "errorMessage" cho rõ ràng
            }
        } else {
            // Nếu không có ID, hiển thị tất cả học sinh
            List<Scores> scores = scoresService.getAllScores();
            model.addAttribute("scores", scores);
        }
        return "scores/index"; // Điều chỉnh tên view nếu cần
    }



}
