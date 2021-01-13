package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.SubmissionPublisher;
import util.DefaultSubscriber;

public class ViewDataFrame extends JFrame {

    /**
     * 字體
     */
    static final Font FONT_TITLE = new Font("微軟正黑體", Font.BOLD, 20);
    static final Font FONT_CONTENT = new Font("微軟正黑體", Font.PLAIN, 18);

    /**
     * 元件
     */
    private JLabel totalLabel = new JLabel(); // 總投票人數
    private JLabel redLabel = new JLabel(); // 紅黨投票人數
    private JLabel greenLabel = new JLabel(); // 綠黨投票人數
    private JLabel yellowLabel = new JLabel(); // 黃黨投票人數
    private JLabel p1Label = new JLabel(); // 陳投票人數
    private JLabel p2Label = new JLabel(); // 吳投票人數
    private JLabel p3Label = new JLabel(); // 柳投票人數
    /**
     * 發布者
     */
    private SubmissionPublisher<String> singOutPublisher = new SubmissionPublisher<>();
    /**
     * 資料
     */
    private String[][] data;

    public ViewDataFrame() {
        // 設定視窗標題
        this.setTitle("查看資料");
        // 設定視窗寬度和高度
        this.setSize(800, 550);
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
        // 讀取 Csv 資料
        this.renderData();

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(0, 10, 10, 10);
        c.gridwidth = 3;
        c.gridheight = 3;
        c.gridx = 0;
        c.gridy = 0;
        
        String[] columnNames = { "身分證字號", "密碼", "投黨", "投人" };

        JTable allData = new JTable(data, columnNames);
        allData.setBounds(0, 0, 200, 300);
        JScrollPane sp = new JScrollPane(allData);

        this.getContentPane().add(sp, c);

        /**
         * GridBag：統計資料（總投票人數）
         */
        // Grid參數設定
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(10, 10, 10, 10);
        c.gridwidth = 2;
        c.gridheight = 1;
        c.gridx = 3;
        c.gridy = 0;
        this.totalLabel.setFont(FONT_CONTENT);
        // 加入ContentPane
        this.getContentPane().add(totalLabel, c);

        /**
         * GridBag：統計資料（各黨票數）
         */
        // Grid參數設定
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(10, 10, 10, 10);
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 3;
        c.gridy = 1;
        // 創建統計資料（各黨票數）Panel（垂直排列的BoxLayout）
        JPanel partPanel = new JPanel();
        partPanel.setLayout(new BoxLayout(partPanel, BoxLayout.Y_AXIS));
        // 將標籤加入陣列
        JLabel[] partLabels = { this.redLabel, this.greenLabel, this.yellowLabel };
        // 逐個初設定並將Label加入Panel中
        for (JLabel label : partLabels) {
            label.setFont(FONT_CONTENT);
            partPanel.add(label);
        }
        // 加入ContentPane
        this.getContentPane().add(partPanel, c);

        /**
         * GridBag：統計資料（各人票數）
         */
        // Grid參數設定
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(10, 10, 10, 10);
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 4;
        c.gridy = 1;
        // 創建統計資料（各人票數）Panel（垂直排列的BoxLayout）
        JPanel personPanel = new JPanel();
        personPanel.setLayout(new BoxLayout(personPanel, BoxLayout.Y_AXIS));
        // 將標籤加入陣列
        JLabel[] personLabels = { this.p1Label, this.p2Label, this.p3Label };
        // 逐個初設定並將Label加入Panel中
        for (JLabel label : personLabels) {
            label.setFont(FONT_CONTENT);
            personPanel.add(label);
        }
        // 加入ContentPane
        this.getContentPane().add(personPanel, c);

        /**
         * GridBag：登出按鈕
         */
        // Grid參數設定
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(0, 10, 10, 10);
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridx = 1;
        c.gridy = 3;

        JButton singOutBtn = new JButton("登出");
        singOutBtn.setFont(FONT_TITLE);

        singOutBtn.addActionListener(e -> {
            this.singOutPublisher.submit("");
        });

        // 加入ContentPane
        this.getContentPane().add(singOutBtn, c);
    }

    /**
     * 註冊登出事件
     */
    public void onSingOut(DefaultSubscriber.DataHandler<String> handler) {
        this.singOutPublisher.subscribe(new DefaultSubscriber<>(handler));
    }

    /**
     * 資料處理
     */
    private void renderData() {
        /**
         * 讀取資料
         */
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream("data.csv"));
            BufferedReader reader = new BufferedReader(isr);
            String line = null;
            int line_count = 0, r_count = 0, g_count = 0, y_count = 0, p1_count = 0, p2_count = 0, p3_count = 0;
            List<String> list = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                // 投票資料
                list.add(line);
                line_count++;
                String item[] = line.split(",");
                if (item[2].equals("紅黨")) {
                    r_count++;
                } else if (item[2].equals("綠黨")) {
                    g_count++;
                } else if (item[2].equals("黃黨")) {
                    y_count++;
                }
                if (item[3].equals("陳xx")) {
                    p1_count++;
                } else if (item[3].equals("吳xx")) {
                    p2_count++;
                } else if (item[3].equals("柳xx")) {
                    p3_count++;
                }
            }
            this.data = new String[list.size()][4];
            for (int ii = 0; ii < list.size(); ii++) {
                this.data[ii] = list.get(ii).split(",");
            }
            reader.close();
            /**
             * 渲染資料
             */
            // 總投票人數
            this.totalLabel.setText("總投票人數：" + line_count);
            // 紅黨得票數
            this.redLabel.setText("紅黨得票數：" + r_count);
            // 綠黨得票數
            this.greenLabel.setText("綠黨得票數：" + g_count);
            // 黃黨得票數
            this.yellowLabel.setText("黃黨得票數：" + y_count);
            // 陳xx得票數
            this.p1Label.setText("陳xx得票數：" + p1_count);
            // 吳xx得票數
            this.p2Label.setText("吳xx得票數：" + p2_count);
            // 柳xx得票數
            this.p3Label.setText("柳xx得票數：" + p3_count);
            // System.out.println("Line count = " + line_count);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
