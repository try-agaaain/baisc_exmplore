import java.lang.Thread;

public class UserOperation {
    public static void performOperation() {
        UserSession session = SessionManager.getSession();
        if (session != null) {
            System.out.println(Thread.currentThread().getName() + 
                               " - User " + session.getUsername() + 
                               " (ID: " + session.getUserId() + ") is performing an operation.");
        } else {
            System.out.println(Thread.currentThread().getName() + " - No user session found.");
        }
    }
}

