package service;

public class LoginService {

    private static final String VALID_LOGIN_ID = "user";
    private static final String VALID_PASSWORD = "1234";

    public boolean validateLogin(String loginId, String password) {
        if (loginId == null || password == null) {
            return false;
        }

        return VALID_LOGIN_ID.equals(loginId.trim()) && VALID_PASSWORD.equals(password.trim());
    }
}
