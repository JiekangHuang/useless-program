package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;
import java.util.concurrent.SubmissionPublisher;
import util.DefaultSubscriber;

public class VoteFrame extends JFrame {

    /**
     * 字體
     */
    static final Font FONT = new Font("微軟正黑體", Font.BOLD, 18);
    /**
     * 元件
     */
    private JRadioButton redRadio, greenRadio, yellowRadio;
    private JRadioButton p1, p2, p3;
    private ButtonGroup partyButtonGroup, personButtonGroup;
    /**
     * 發布者
     */
    private SubmissionPublisher<String> singOutPublisher = new SubmissionPublisher<>();
    /**
     * 資料
     */
    private String party = "", person = "";
    private String username, password;

    public VoteFrame(String username, String password) {
        this.username = username;
        this.password = password;
        // 設定視窗標題
        this.setTitle("投票系統");
        // 設定視窗寬度和高度
        this.setSize(350, 180);
        // 視窗顯示在中間
        this.setLocationRelativeTo(null);
        // 關閉視窗程式停止
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /**
         * 設定主要Layout
         */
        GridBagLayout gridBagLayout = new GridBagLayout();
        // 設定佈局方式
        this.getContentPane().setLayout(gridBagLayout);

        GridBagConstraints c = new GridBagConstraints();
        /**
         * GridBag：黨標題
         */
        // Grid參數設定
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 0;

        JLabel partyLabel = new JLabel("黨：");
        partyLabel.setFont(FONT);
        // 加入ContentPane
        this.getContentPane().add(partyLabel, c);

        /**
         * GridBag：黨選項
         */
        // Grid參數設定
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.gridx = 1;
        c.gridy = 0;

        redRadio = new JRadioButton("紅黨");
        greenRadio = new JRadioButton("綠黨");
        yellowRadio = new JRadioButton("黃黨");

        redRadio.setFont(FONT);
        greenRadio.setFont(FONT);
        yellowRadio.setFont(FONT);

        redRadio.addActionListener(e -> {
            this.selectParty(e);
        });

        greenRadio.addActionListener(e -> {
            this.selectParty(e);
        });

        yellowRadio.addActionListener(e -> {
            this.selectParty(e);
        });

        partyButtonGroup = new ButtonGroup();
        partyButtonGroup.add(redRadio);
        partyButtonGroup.add(greenRadio);
        partyButtonGroup.add(yellowRadio);
        // 加入ContentPane
        JPanel partyRadio = new JPanel();
        partyRadio.setLayout(new FlowLayout(FlowLayout.CENTER));

        partyRadio.add(redRadio);
        partyRadio.add(greenRadio);
        partyRadio.add(yellowRadio);

        this.getContentPane().add(partyRadio, c);

        /**
         * GridBag：人標題
         */
        // Grid參數設定
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 1;

        JLabel personLabel = new JLabel("人：");
        personLabel.setFont(FONT);
        // 加入ContentPane
        this.getContentPane().add(personLabel, c);

        /**
         * GridBag：人選項
         */
        // Grid參數設定
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.gridx = 1;
        c.gridy = 1;

        p1 = new JRadioButton("陳xx");
        p2 = new JRadioButton("吳xx");
        p3 = new JRadioButton("柳xx");

        p1.setFont(FONT);
        p2.setFont(FONT);
        p3.setFont(FONT);

        p1.addActionListener(e -> {
            this.selectPerson(e);
        });

        p2.addActionListener(e -> {
            this.selectPerson(e);
        });

        p3.addActionListener(e -> {
            this.selectPerson(e);
        });

        personButtonGroup = new ButtonGroup();
        personButtonGroup.add(p1);
        personButtonGroup.add(p2);
        personButtonGroup.add(p3);

        JPanel personRadio = new JPanel();
        personRadio.setLayout(new FlowLayout(FlowLayout.CENTER));

        personRadio.add(p1);
        personRadio.add(p2);
        personRadio.add(p3);

        // 加入ContentPane
        this.getContentPane().add(personRadio, c);

        /**
         * GridBag：送出按鈕
         */
        // Grid參數設定
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(0, 10, 10, 10);
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 2;

        JButton sendBtn = new JButton("送出");
        sendBtn.setFont(FONT);

        sendBtn.addActionListener(e -> {
            if (party.isEmpty()) {
                JOptionPane.showMessageDialog(this, "尚未選擇黨", "錯誤", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (person.isEmpty()) {
                JOptionPane.showMessageDialog(this, "尚未選擇人", "錯誤", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String data = this.username + "," + this.password + "," + party + "," + person;
            System.out.println(data);
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter("data.csv", true));
                bw.write(data);
                bw.newLine();
                bw.close();
                JOptionPane.showMessageDialog(this, "填寫完成", "成功", JOptionPane.NO_OPTION);
                this.singOutPublisher.submit("");
            } catch (FileNotFoundException err) {
                err.printStackTrace();
            } catch (IOException err) {
                err.printStackTrace();
            }
        });

        // 加入ContentPane
        this.getContentPane().add(sendBtn, c);

        /**
         * GridBag：清除按鈕
         */
        // Grid參數設定
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(0, 10, 10, 10);
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 1;
        c.gridy = 2;

        JButton clrBtn = new JButton("清除");
        clrBtn.setFont(FONT);

        clrBtn.addActionListener(e -> {
            party = person = "";
            partyButtonGroup.clearSelection();
            personButtonGroup.clearSelection();
        });

        // 加入ContentPane
        this.getContentPane().add(clrBtn, c);

        /**
         * GridBag：登出按鈕
         */
        // Grid參數設定
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(0, 10, 10, 10);
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 2;
        c.gridy = 2;

        JButton singOutBtn = new JButton("登出");
        singOutBtn.setFont(FONT);

        singOutBtn.addActionListener(e -> {
            this.singOutPublisher.submit("");
        });

        // 加入ContentPane
        this.getContentPane().add(singOutBtn, c);
    }

    private void selectParty(ActionEvent e) {
        party = ((JRadioButton) e.getSource()).getText();
    }

    private void selectPerson(ActionEvent e) {
        person = ((JRadioButton) e.getSource()).getText();
    }

    /**
     * 註冊登出事件
     */
    public void onSingOut(DefaultSubscriber.DataHandler<String> handler) {
        this.singOutPublisher.subscribe(new DefaultSubscriber<>(handler));
    }
}
