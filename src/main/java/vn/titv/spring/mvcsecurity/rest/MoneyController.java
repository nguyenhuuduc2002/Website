package vn.titv.spring.mvcsecurity.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.titv.spring.mvcsecurity.entity.Money;
import vn.titv.spring.mvcsecurity.entity.Student;
import vn.titv.spring.mvcsecurity.entity.User;
import vn.titv.spring.mvcsecurity.service.MoneyService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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

    @Autowired
    private JavaMailSender mailSender;


    @GetMapping("/add")
    public String addMoney(Model model) {
        Money money = new Money();
        model.addAttribute("money", money);
        return "money/create";
    }

    @PostMapping("/save")
    public String saveMoney(@ModelAttribute("money") Money money, Model model) {
        if (moneyService.existsByKhoiHoc(money.getKhoiHoc())) {
            model.addAttribute("error", "Bạn đã nhập học phí của khối này! .");
            return "money/create"; // Return to the form view with error message
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
            return "money/money-student";
        }else{
            return "money/no-money";
        }


    }

    @GetMapping("/update")
    public String show(@RequestParam("id") String khoiHoc, Model model){
        Money money = moneyService.findByKhoiHoc(khoiHoc);
        model.addAttribute("money", money);
        return "money/update";
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
        return "money/index";
    }

    @GetMapping("/pay")
    public String payMoney(){
        return "money/pay-money";
    }


    @GetMapping("/processPayment")
    public String processPayment(Principal principal, Model model) {
        String username = principal.getName();
        User user = userService.findByUsername(username);
        Student student = studentService.findByEmail(user.getEmail());

        sendPaymentConfirmationEmail(student.getEmail());
         model.addAttribute("successMessage","THANH TOÁN THÀNH CÔNG! ");
         return "money/pay-money";
    }


    private void sendPaymentConfirmationEmail(String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Thông Báo Thanh Toán");
        message.setText("Bạn đã thanh toán học phí thành công!");

        mailSender.send(message); // Gửi email
    }

}
