package gui;

import java.awt.*;
import javax.swing.*;

public class LoginFrame extends JFrame {

    /**
     * 字體
     */
    static final Font FONT = new Font("微軟正黑體", Font.BOLD, 18);
    /**
     * 元件
     */
    private JTextField accountText;
    private JTextField passwordText;

    public LoginFrame() {
        // 設定視窗標題
        this.setTitle("投票系統");
        // 設定視窗寬度和高度
        this.setSize(300, 140);
        // 視窗顯示在中間
        this.setLocationRelativeTo(null);
        // 關閉視窗程式停止
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /**
         * 設定主要Layout
         */
        // 設定佈局方式
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        /**
         * 身分證號碼
         */
        // Panel
        JPanel accountPanel = new JPanel();
        accountPanel.setLayout(new BoxLayout(accountPanel, BoxLayout.X_AXIS));
        // 身分證號碼Label
        JLabel accountLabel = new JLabel("身分證號碼：");
        accountLabel.setFont(FONT);
        accountPanel.add(accountLabel);
        // 身分證號碼文字欄位
        this.accountText = new JTextField();
        this.accountText.setFont(FONT);
        accountPanel.add(this.accountText);
        // 加入ContentPane
        this.getContentPane().add(accountPanel);

        /**
         * 解鎖碼
         */
        // Panel
        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.X_AXIS));
        // 解鎖碼Label
        JLabel passwordLabel = new JLabel("解    鎖   碼：");
        passwordLabel.setFont(FONT);
        passwordPanel.add(passwordLabel);
        // 解鎖碼文字欄位
        this.passwordText = new JPasswordField();
        this.passwordText.setFont(FONT);
        passwordPanel.add(this.passwordText);
        // 加入ContentPane
        this.getContentPane().add(passwordPanel);

        /**
         * 按鈕
         */
        // Panel
        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.X_AXIS));
        // 登入按鈕
        JButton loginBtn = new JButton("登入");
        loginBtn.setFont(FONT);
        loginBtn.addActionListener(e -> {
            this.onLogin();
        });
        actionPanel.add(loginBtn);
        // 加入ContentPane
        this.getContentPane().add(actionPanel);
    }

    /**
     * 處理按下登入按鈕
     */
    public boolean onLogin() {
        // 取得身分證號碼、解鎖碼
        String account = this.accountText.getText();
        String password = this.passwordText.getText();
        // 處理身分證號碼、解鎖碼
        account = account.trim();
        password = password.trim();
        // 檢查非空字串
        if (account.isEmpty() || password.isEmpty()) {
            return false;
        }
        // 核對身分證號碼、解鎖碼
        if (!account.equals("S123456789") || !password.equals("789")) {
            return false;
        }
        return true;
    }
}
