package letscode.sarafan.controller;

import letscode.sarafan.domain.User;
import letscode.sarafan.repo.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@Controller
@RequestMapping("/")
public class MainController {
    private final MessageRepo messageRepo;

    @Value("${spring.profiles.active}")
    private String profile;

    @Autowired
    public MainController(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    @GetMapping
    public String main(Model model, @AuthenticationPrincipal User user) {
        HashMap<Object, Object> data = new HashMap<>();
        //то есть, если на фронте profile != null, пользователь авторизован
        if (user != null) {
            data.put("profile", user);
            data.put("messages", messageRepo.findAll());
        }

        //после, происходит сериализация в json(on front-side)
        model.addAttribute("frontendData", data);
        //йода-запись(const-значение находится в левой части выражения)
        //позволяет избежать nullExc при profile == null
        model.addAttribute("isDevMode", "dev".equals(profile));

        return "index";
    }
}
