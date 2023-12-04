package slowxmas.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import slowxmas.Service.UserSerivce;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final UserSerivce userSerivce;

    @GetMapping("/login/kakao/callback")
    public void kakaoCallback(@RequestParam String code) {
        String accessToken = this.userSerivce.getKakaoAccessToken(code);
        this.userSerivce.createKakaoUser(accessToken);
    }



}
