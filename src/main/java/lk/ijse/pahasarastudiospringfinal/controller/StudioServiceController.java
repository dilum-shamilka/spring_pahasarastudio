package lk.ijse.pahasarastudiospringfinal.controller;

import lk.ijse.pahasarastudiospringfinal.service.StudioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/services")
public class StudioServiceController {
    private final StudioService studioService;

    public StudioServiceController(StudioService studioService) {
        this.studioService = studioService;
    }

    @GetMapping
    public String serviceList(Model model) {
        model.addAttribute("services", studioService.getAllServices());
        return "studio/services-config";
    }
}