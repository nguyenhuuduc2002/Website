package vn.titv.spring.mvcsecurity.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.titv.spring.mvcsecurity.entity.Money;
import vn.titv.spring.mvcsecurity.entity.Student;
import vn.titv.spring.mvcsecurity.entity.User;
import vn.titv.spring.mvcsecurity.service.MoneyService;
import vn.titv.spring.mvcsecurity.service.ScoresService;
import vn.titv.spring.mvcsecurity.service.StudentService;
import vn.titv.spring.mvcsecurity.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/money")
public class MoneyController {

    @Autowired
    private MoneyService moneyService;


    @Autowired
    private StudentService studentService;


    @Autowired
    private UserService userService;



    @GetMapping("/add")
    public String addMoney(Model model) {
        Money money = new Money();
        model.addAttribute("money", money);
        return "money/add_money";
    }

    @PostMapping("/save")
    public String saveMoney(@ModelAttribute("money") Money money, Model model) {
        if (moneyService.existsByKhoiHoc(money.getKhoiHoc())) {
            model.addAttribute("error", "Bạn đã nhập học phí của khối này! .");
            return "money/add_money"; // Return to the form view with error message
        }

        double tongTien = money.getTienHoc() +
                money.getTienBaoHiem() +
                money.getTienDongPhuc() +
                money.getTienBanTru() +
                money.getTienHocThem() +
                money.getTienQuy() +
                money.getTienSach() +
                money.getDichVuKhac();

        // Set the total amount in the money object
        money.setTongTien(tongTien);

        moneyService.updateMoney(money);
        return "redirect:/money/list"; // Redirect to the list after successful save
    }


    @GetMapping("/edit")
    public String editMoney(Principal principal,Money money, Model model){
        String username = principal.getName();
        User user = userService.findByUsername(username);
        Student student = studentService.findByEmail(user.getEmail());


        Money moneys = moneyService.findByKhoiHoc(student.getLop());
        if (moneys!=null) {
            model.addAttribute("money", moneys);
            return "money/show_money_student";
        }else{
            return "money/no_money";
        }


    }

    @GetMapping("/update")
    public String show(@RequestParam("id") String khoiHoc, Model model){
        Money money = moneyService.findByKhoiHoc(khoiHoc);
        model.addAttribute("money", money);
        return "money/edit_money";
    }

    @PostMapping("/update")
    public String updateMoney(@ModelAttribute Money money){
        double tongTien = money.getTienHoc() +
                money.getTienBaoHiem() +
                money.getTienDongPhuc() +
                money.getTienBanTru() +
                money.getTienHocThem() +
                money.getTienQuy() +
                money.getTienSach() +
                money.getDichVuKhac();

        // Set the total amount in the money object
        money.setTongTien(tongTien);

        moneyService.updateMoney(money);
        return "redirect:/money/list";
    }


    @GetMapping("/list")
    public String listAll(Model model){
        List<Money> moneyList = moneyService.getAllMoney();
        model.addAttribute("moneyList", moneyList);
        return "money/list_money";
    }

    @GetMapping("/pay")
    public String payMoney(){
        return "money/pay_money";
    }



}
