import gui.*;
import javax.swing.*;

public class Main {
    // 登入視窗
    static LoginFrame loginFrame;
    // 投票視窗
    static VoteFrame voteFrame;
    // 查看資料視窗
    static ViewDataFrame viewDataFrame;
    // 目前視窗
    static JFrame curFrame;

    public static void main(String[] args) {
        /**
         * 初始顯示登入視窗
         */
        showLoginFrame();
    }

    /**
     * 顯示登入視窗
     */
    public static void showLoginFrame() {
        // 銷毀投票視窗
        if (voteFrame != null) {
            voteFrame.dispose();
        }
        // 銷毀查看資料視窗
        if (viewDataFrame != null) {
            viewDataFrame.dispose();
        }
        // 建立登入視窗
        loginFrame = new LoginFrame();
        // 當按下登入按鈕
        loginFrame.onLogin(e -> {
            if (e.account.equals("S987654321") && e.password.equals("321")) {
                JOptionPane.showMessageDialog(curFrame, "帳號密碼錯誤", "錯誤", JOptionPane.WARNING_MESSAGE);
                return;
            }
            showVoteFrame(e.account, e.password);
        });
        // 當按下查看資料按鈕
        loginFrame.onViewData(e -> {
            if (!e.account.equals("A13572468") || !e.password.equals("56781234")) {
                JOptionPane.showMessageDialog(curFrame, "帳號密碼錯誤", "錯誤", JOptionPane.WARNING_MESSAGE);
                return;
            }
            showViewDataFrame();
        });
        // 顯示登入視窗
        loginFrame.setVisible(true);
        // 切換到登入視窗
        curFrame = loginFrame;
    }

    /**
     * 顯示投票視窗
     */
    public static void showVoteFrame(String username, String password) {
        // 銷毀登入視窗
        if (loginFrame != null) {
            loginFrame.dispose();
        }
        // 顯示投票視窗
        voteFrame = new VoteFrame(username, password);
        // 當按下登出按鈕
        voteFrame.onSingOut(e -> {
            System.out.println(username + ": 登出");
            showLoginFrame();
        });
        voteFrame.setVisible(true);
        // 切換到投票視窗
        curFrame = voteFrame;
    }

    /**
     * 顯示查看資料視窗
     */
    public static void showViewDataFrame() {
        // 銷毀登入視窗
        if (loginFrame != null) {
            loginFrame.dispose();
        }
        // 顯示查看資料視窗
        viewDataFrame = new ViewDataFrame();
        // 當按下登出按鈕
        viewDataFrame.onSingOut(e -> {
            System.out.println("登出");
            showLoginFrame();
        });
        viewDataFrame.setVisible(true);
        // 切換到查看資料視窗
        curFrame = viewDataFrame;
    }
}
