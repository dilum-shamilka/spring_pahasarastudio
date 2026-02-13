package lk.ijse.pahasarastudiospringfinal.controller;

import lk.ijse.pahasarastudiospringfinal.dto.UserDTO;
import lk.ijse.pahasarastudiospringfinal.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() { return "auth/login"; }

    @GetMapping("/signup")
    public String signupPage(Model model) {
        model.addAttribute("user", new UserDTO());
        return "auth/signup";
    }

    @PostMapping("/signup")
    public String register(@ModelAttribute("user") UserDTO userDTO) {
        userService.saveUser(userDTO);
        return "redirect:/login?success";
    }
}