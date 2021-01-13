package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.util.concurrent.SubmissionPublisher;
import util.DefaultSubscriber;

public class ViewDataFrame extends JFrame {

    /**
     * 字體
     */
    static final Font FONT = new Font("微軟正黑體", Font.BOLD, 18);
    static final Font FONT_CONTENT = new Font("微軟正黑體", Font.PLAIN, 18);

    /**
     * 元件
     */
    /**
     * 發布者
     */
    private SubmissionPublisher<String> singOutPublisher = new SubmissionPublisher<>();
    /**
     * 資料
     */
    private DefaultTableModel tableModelData;

    public ViewDataFrame() {
        // 設定視窗標題
        this.setTitle("查看資料");
        // 設定視窗寬度和高度
        this.setSize(600, 400);
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
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(0, 10, 10, 10);
        c.gridwidth = 3;
        c.gridheight = 3;
        c.gridx = 0;
        c.gridy = 0;
        // 玩家列表
        this.tableModelData = new NoEditableTableModel(new String[] { "玩家", "所在地區", "職業", "技能" });
        JTable tablePlayer = new JTable(this.tableModelData);
        tablePlayer.setFont(FONT_CONTENT);
        tablePlayer.setRowHeight(25);

        // this.getContentPane().add(tablePlayer, c);

        /**
         * GridBag：登出按鈕
         */
        // Grid參數設定
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(0, 10, 10, 10);
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 0;
        c.gridy = 0;

        JButton singOutBtn = new JButton("登出");
        singOutBtn.setFont(FONT);

        singOutBtn.addActionListener(e -> {
            this.singOutPublisher.submit("");
        });

        // 加入ContentPane
        this.getContentPane().add(singOutBtn, c);
    }

    /**
     * 不可編輯的TableModel
     */
    private class NoEditableTableModel extends DefaultTableModel {

        /**
         *
         */
        private static final long serialVersionUID = 1L;

        public NoEditableTableModel(Object[] columnNames) {
            super(new String[][] {}, columnNames);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }

    /**
     * 註冊登出事件
     */
    public void onSingOut(DefaultSubscriber.DataHandler<String> handler) {
        this.singOutPublisher.subscribe(new DefaultSubscriber<>(handler));
    }
}
