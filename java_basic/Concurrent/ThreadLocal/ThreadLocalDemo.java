import java.lang.Thread;

public class ThreadLocalDemo {
    public static void main(String[] args) {
        // 创建并启动三个线程,模拟三个不同的用户
        Thread user1 = new Thread(new UserSimulator("Alice", 1), "Thread-Alice");
        Thread user2 = new Thread(new UserSimulator("Bob", 2), "Thread-Bob");
        Thread user3 = new Thread(new UserSimulator("Charlie", 3), "Thread-Charlie");

        user1.start();
        user2.start();
        user3.start();
    }

    static class UserSimulator implements Runnable {
        private String username;
        private int userId;

        public UserSimulator(String username, int userId) {
            this.username = username;
            this.userId = userId;
        }

        @Override
        public void run() {
            // 设置用户会话
            SessionManager.setSession(new UserSession(username, userId));

            // 执行用户操作
            UserOperation.performOperation();

            // 清理会话
            SessionManager.clearSession();

            // 再次尝试执行操作,这次应该没有会话
            UserOperation.performOperation();
        }
    }
}
