import gui.*;

public class Main {
    // 登入視窗
    static LoginFrame loginFrame;

    public static void main(String[] args) {
        /**
         * 登入視窗
         */
        // 建立登入視窗
        loginFrame = new LoginFrame();

        /**
         * 初始顯示登入視窗
         */
        showLoginFrame();
    }

    /**
     * 顯示登入視窗
     */
    public static void showLoginFrame() {
        // 顯示登入視窗
        loginFrame.setVisible(true);
    }
}
