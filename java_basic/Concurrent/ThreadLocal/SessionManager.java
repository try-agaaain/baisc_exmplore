// import java.util.ThreadLocal;
import java.lang.ThreadLocal;

public class SessionManager {
    private static final ThreadLocal<UserSession> userSession = new ThreadLocal<>();

    public static void setSession(UserSession session) {
        userSession.set(session);
    }

    public static UserSession getSession() {
        return userSession.get();
    }

    public static void clearSession() {
        userSession.remove();
    }
}

