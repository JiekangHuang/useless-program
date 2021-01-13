package gui;

import java.awt.*;
import javax.swing.*;
import java.util.concurrent.SubmissionPublisher;
import util.DefaultSubscriber;

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
    /**
     * 發布者
     */
    private SubmissionPublisher<LoginInfo> loginPublisher = new SubmissionPublisher<>();
    private SubmissionPublisher<LoginInfo> viewDataPublisher = new SubmissionPublisher<>();

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
        JLabel passwordLabel = new JLabel("解    鎖    碼：");
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
            this.handleClickLogin();
        });
        actionPanel.add(loginBtn);
        // 查看資料按鈕
        JButton viewDataBtn = new JButton("查看資料");
        viewDataBtn.setFont(FONT);
        viewDataBtn.addActionListener(e -> {
            this.handleClickViewData();
        });
        actionPanel.add(viewDataBtn);
        // 加入ContentPane
        this.getContentPane().add(actionPanel);
    }

    /**
     * 處理按下登入按鈕
     */
    private void handleClickLogin() {
        // 取得身分證號碼、解鎖碼
        String account = this.accountText.getText();
        String password = this.passwordText.getText();
        // 處理身分證號碼、解鎖碼
        account = account.trim();
        password = password.trim();
        // 檢查非空字串
        if (account.isEmpty() || password.isEmpty()) {
            return;
        }
        // 觸發事件
        this.loginPublisher.submit(new LoginInfo(account, password));
    }

    /**
     * 處理按下查看資料按鈕
     */
    private void handleClickViewData() {
        // 取得身分證號碼、解鎖碼
        String account = this.accountText.getText();
        String password = this.passwordText.getText();
        // 處理身分證號碼、解鎖碼
        account = account.trim();
        password = password.trim();
        // 檢查非空字串
        if (account.isEmpty() || password.isEmpty()) {
            // return;
        }
        // 觸發事件
        this.viewDataPublisher.submit(new LoginInfo(account, password));
    }

    /**
     * 註冊登入事件
     */
    public void onLogin(DefaultSubscriber.DataHandler<LoginInfo> handler) {
        this.loginPublisher.subscribe(new DefaultSubscriber<>(handler));
    }

    /**
     * 註冊查看資料事件
     */
    public void onViewData(DefaultSubscriber.DataHandler<LoginInfo> handler) {
        this.viewDataPublisher.subscribe(new DefaultSubscriber<>(handler));
    }

    /**
     * 登入資料
     */
    public class LoginInfo {
        public final String account;
        public final String password;

        public LoginInfo(String account, String password) {
            this.account = account;
            this.password = password;
        }
    }
}
