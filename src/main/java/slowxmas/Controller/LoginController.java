package slowxmas.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import slowxmas.Entity.User;
import slowxmas.Service.UserSerivce;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final UserSerivce userSerivce;

    @GetMapping("/login/kakao/callback")
    public String kakaoCallback(Model model, @RequestParam String code) {
        String accessToken = this.userSerivce.getKakaoAccessToken(code);
        User user = this.userSerivce.getKakaoUserInfo(accessToken);

        model.addAttribute("user", user);

        return "/main";
    }
}
