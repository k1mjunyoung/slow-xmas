package slowxmas.Service;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import slowxmas.Entity.User;
import slowxmas.Repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserSerivce {
    static final String REST_API_KEY = "55ab9b90f6fe66c4c4543273c83d5145";

    static final String REDIRECT_URL = "http://localhost:8080/login/kakao/callback";

    static final String ACCESS_TOKEN_REQUEST_URL = "https://kauth.kakao.com/oauth/token";

    static final String USER_INFO_REQUEST_URL = "https://kapi.kakao.com/v2/user/me";

    private final UserRepository userRepository;

    public String getKakaoAccessToken(String code) {
        String accessToken = "";
        String refreshToken = "";
        String requestUrl = ACCESS_TOKEN_REQUEST_URL;

        try {
            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();

            sb.append("grant_type=authorization_code");
            sb.append("&client_id=" + REST_API_KEY);
            sb.append("&redirect_uri=" + REDIRECT_URL);
            sb.append("&code=" + code);

            bw.write(sb.toString());
            bw.flush();

            int responseCode = conn.getResponseCode();
            System.out.println("[getKakaoAccessToken] responseCode = " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("[getKakaoAccessToken] responseBody: " + result);

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            accessToken = element.getAsJsonObject().get("access_token").getAsString();
            refreshToken = element.getAsJsonObject().get("refresh_token").getAsString();

            System.out.println("[getKakaoAccessToken] accessToken = " + accessToken);
            System.out.println("[getKakaoAccessToken] refreshToken = " + refreshToken);

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return accessToken;
    }

    public User getKakaoUserInfo(String token) {
        String requestUrl = USER_INFO_REQUEST_URL;

        User user = new User();

        try {
            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization" , "Bearer " + token);

            int responseCode = conn.getResponseCode();
            System.out.println("[getKakaoUserInfo] responseCode = " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("[getKakaoUserInfo] response body: " + result);

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            Long id = element.getAsJsonObject().get("id").getAsLong();
            String nickname = element.getAsJsonObject().get("properties").getAsJsonObject().get("nickname").getAsString();

            System.out.println("[getKakaoUserInfo] id = " + id);
            System.out.println("[getKakaoUserInfo] nickname = " + nickname);

            if (this.userRepository.findById(id).isEmpty()) {
                saveKakaoUser(id, nickname);
            }

            user = loadKakaoUser(id);

            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return user;
    }

    public void saveKakaoUser(Long id, String nickname) {
        User user = new User();

        user.setKakaoId(id);
        user.setNickname(nickname);
        this.userRepository.save(user);
    }

    public User loadKakaoUser(Long id) {
        User user = this.userRepository.findByKakaoId(id);

        return user;
    }
}
