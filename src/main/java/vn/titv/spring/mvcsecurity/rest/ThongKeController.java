package vn.titv.spring.mvcsecurity.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.titv.spring.mvcsecurity.dao.ScoresRepository;
import vn.titv.spring.mvcsecurity.entity.Scores;

import java.util.List;

@Controller
@RequestMapping("/thongke")
public class ThongKeController {

    @Autowired
    private ScoresRepository scoresRepository;


    @GetMapping("/thongke")
    public String thongKe(Model model){
        // Lấy danh sách điểm từ cơ sở dữ liệu
        List<Scores> scoresList = scoresRepository.findAll();

        //-----------------------------------------------------------------------------------
        double diemGioi = 0.0;
        int totalScoresOver8 = 0; // Số lượng điểm trên 8


// Đếm số lượng điểm trên 8
        for (Scores scores : scoresList) {
            if (scores.getDiemTrungBinh() >=8) totalScoresOver8++;
        }

// Tính điểm giỏi
        if (!scoresList.isEmpty()) {
            diemGioi = (double) totalScoresOver8 / scoresList.size() * 100;
            diemGioi = Math.round(diemGioi * 10.0) / 10.0;

        }


//-----------------------------------------------------------------------------------------------
        double diemKha = 0.0;
        int totalScores65den8 = 0; // Số lượng điểm trên 8


// Đếm số lượng điểm 6.5 đến 8
        for (Scores scores : scoresList) {
            if (scores.getDiemTrungBinh() >= 6.5 && scores.getDiemTrungBinh() <8) totalScores65den8++;

        }

// Tính điểm khá
        if (!scoresList.isEmpty()) {
            diemKha = (double) totalScores65den8 / scoresList.size() * 100;
            diemKha = Math.round(diemKha * 10.0) / 10.0;
        }


        //-----------------------------------------------------------------------------------------------
        double diemTB = 0.0;
        int totalScores5den65 = 0; // Số lượng điểm trên 8


// Đếm số lượng điểm 7 đến 8
        for (Scores scores : scoresList) {
            if (scores.getDiemTrungBinh() >= 5 && scores.getDiemTrungBinh() <6.5) totalScores5den65++;

        }

// Tính điểm tb
        if (!scoresList.isEmpty()) {
            diemTB = (double) totalScores5den65 / scoresList.size()* 100;
            diemTB = Math.round(diemTB * 10.0) / 10.0;
        }



        //-----------------------------------------------------------------------------------------------
        double diemYeu = 0.0;
        int totalScores35den5 = 0; // Số lượng điểm trên 8


// Đếm số lượng điểm 7 đến 8
        for (Scores scores : scoresList) {
            if (scores.getDiemTrungBinh()>= 3.5 && scores.getDiemTrungBinh() <5) totalScores35den5++;
        }

// Tính điểm yếu
        if (!scoresList.isEmpty()) {
            diemYeu = (double) totalScores35den5 / scoresList.size()  * 100;
            diemYeu = Math.round(diemYeu * 10.0) / 10.0;
        }

        //--------------------------------------------------------------------------------

        double diemKem = 0.0;
        int totalScores0den35 = 0; // Số lượng điểm trên 8


// Đếm số lượng điểm 7 đến 8
        for (Scores scores : scoresList) {
            if (scores.getDiemTrungBinh() <3.5) totalScores0den35++;

        }


        if (!scoresList.isEmpty()) {
            diemKem = (double) totalScores0den35 / scoresList.size()  * 100;
            diemKem = Math.round(diemKem * 10.0) / 10.0;
        }




        // Gửi kết quả trung bình cộng điểm đến view
        model.addAttribute("diemGioi", diemGioi);
        model.addAttribute("diemKha", diemKha);
        model.addAttribute("diemTB", diemTB);
        model.addAttribute("diemYeu", diemYeu);
        model.addAttribute("diemKem", diemKem);

        return "thongke/thongke";
    }
}
