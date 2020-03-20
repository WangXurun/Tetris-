package tetris;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.CardLayout;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import java.io.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.swing.JRadioButton;
import java.awt.event.*;

public class Tetris extends JFrame {
    Timer timer;
    CardLayout card = new CardLayout();
    JPanel p1 = new JPanel();
    JPanel p2 = new JPanel();
    JPanel p3 = new JPanel();
    JPanel p4 = new JPanel();
    JPanel p = new JPanel(card);
    Panel2 p21 = new Panel2();
    Panel3 next_panel = new Panel3();
    JLabel final_score = new JLabel("");
    JLabel score_board = new JLabel("");
    ButtonGroup bg1 = new ButtonGroup();
    ButtonGroup bg2 = new ButtonGroup();
    ButtonGroup bg3 = new ButtonGroup();
    JRadioButton rb_music1 = new JRadioButton("\u97F3\u4E501");
    JRadioButton rb_music2 = new JRadioButton("\u97F3\u4E502");
    JRadioButton rb_music3 = new JRadioButton("\u97F3\u4E503");
    JRadioButton rb_music_on = new JRadioButton("\u5F00\u542F");
    JRadioButton rb_music_clo = new JRadioButton("\u5173\u95ED");
    JRadioButton rb_lev1 = new JRadioButton("\u7B80\u5355");
    JRadioButton rb_lev2 = new JRadioButton("\u666E\u901A");
    JRadioButton rb_lev3 = new JRadioButton("\u56F0\u96BE");
    Clip clip;
    String str1 = "src/main/resources/1.wav";
    String str2 = "src/main/resources/2.wav";
    String str3 = "src/main/resources/3.wav";
    String str = str1;
    String str_icon = "src/main/resources/star.gif";
    static String path = "src/main/resources/settings.txt";
    String[] s_str;
    boolean game_on = false;
    int level = 1000;
    public int score = 0;
    public final int width = 11;// 11
    public final int hight = 16;// 16
    private int x, y, t, tn, s;
    private int len = 50;// 50
    int map[][] = new int[hight + 6][width + 5];// 0-墙；1-正下落；2-已经放下的；3-空
    private final int shapes[][][] = new int[][][] {
            { { 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0 },
                    { 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0 },
                    { 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0 } },
            { { 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 },
                    { 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                    { 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 } },
            { { 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
                    { 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                    { 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 } },
            { { 0, 1, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0 }, { 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                    { 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
                    { 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 } },
            { { 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                    { 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                    { 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } },
            { { 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0 }, { 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                    { 1, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 },
                    { 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 } },
            { { 0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0 },
                    { 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
                    { 0, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 } } };

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Tetris frame = new Tetris();
                    frame.setVisible(true);
                    frame.setResizable(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void makemap() {
        for (int i = 0; i <= hight; i++) {
            for (int j = 0; j < width; j++) {
                if (j == 0 || j == width - 1) {
                    map[i][0] = 0;
                    map[i][width - 1] = 0;
                } else if (i == hight) {
                    map[hight][j] = 0;
                } else {
                    map[i][j] = 3;
                }
            }
        }
        // p21.requestFocus();
    }

    public void nextblock() {
        if (game_on) {
            s = 0;
            t = tn;
            tn = (int) (Math.random() * 7);
            next_panel.repaint();
            x = width / 2;
            y = 0;
            // p21.requestFocus();
        }
    }

    public boolean dtb() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if ((map[y + i + 1][x + j] == 0 || map[y + i + 1][x + j] == 2) && shapes[t][s][i * 4 + j] == 1) {
                    {
                        for (int m = 0; m < 4; m++) {
                            for (int n = 0; n < 4; n++) {
                                if (shapes[t][s][m * 4 + n] == 1) {
                                    map[y + m][x + n] = 2;
                                }
                            }
                        }
                        del();
                        // p21.requestFocus();
                        return true;
                    }
                }
            }
        }
        // p21.requestFocus();
        return false;
    }

    public int game_over() {
        for (int i = 0; i < width; i++) {
            if (map[0][i] == 2) {
                game_on = false;
                timer.stop();
                card.next(p);
                final_score.setText(score + "");
                // p21.requestFocus();
                return 1;
            }
        }
        // p21.requestFocus();
        return 0;
    }

    public int check_m(int s, int t, int x, int y) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (x + j < 0) {
                    continue;
                }
                if (map[y + i][x + j] == 0 && shapes[t][s][i * 4 + j] == 1) {
                    // p21.requestFocus();
                    return 0;// 碰上墙了
                } else if (map[y + i][x + j] == 2 && shapes[t][s][i * 4 + j] == 1) {
                    // p21.requestFocus();
                    return 2;// 碰上已经落下的块
                }
            }
        }
        // p21.requestFocus();
        return 1;// 没问题
    }

    public void del() {
        boolean flag;
        for (int i = 1; i <= hight; i++) {
            flag = false;
            for (int j = 1; j < width - 1; j++) {
                if (map[i][j] != 2) {
                    flag = true;
                }
            }
            if (!flag) {
                int weight = 30000 / level;
                for (int j = i; j > 0; j--) {
                    for (int k = 1; k < width - 1; k++) {
                        map[j][k] = map[j - 1][k];
                    }
                }
                score += weight;
                score_board.setText(score + "");
                p21.repaint();
            }
        }
        // p21.requestFocus();
    }

    public Tetris() {
        p21.addKeyListener(p21);
        setTitle("TETRIS");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 862);
        p.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(p);
        p.add(p1, "Start menu");
        p1.setLayout(null);
        p2.setLayout(null);

        JButton button = new JButton("\u5F00\u59CB\u6E38\u620F");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                start(0);
            }
        });
        button.setFont(new Font("华文行楷", Font.BOLD, 23));
        button.setBounds(323, 226, 133, 63);
        p1.add(button);

        JButton button_1 = new JButton("\u9000\u51FA");
        button_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        button_1.setFont(new Font("华文行楷", Font.BOLD, 26));
        button_1.setBounds(323, 511, 133, 63);
        p1.add(button_1);

        JButton btnNewButton = new JButton("\u9009\u9879");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                card.last(p);
            }
        });
        btnNewButton.setFont(new Font("华文行楷", Font.BOLD, 26));
        btnNewButton.setBounds(323, 366, 133, 63);
        p1.add(btnNewButton);

        JLabel lblWelcomToTetris = new JLabel("welcom to tetris");
        lblWelcomToTetris.setForeground(Color.WHITE);
        lblWelcomToTetris.setFont(new Font("Bodoni MT Poster Compressed", Font.BOLD | Font.ITALIC, 36));
        lblWelcomToTetris.setBounds(297, 67, 296, 84);
        p1.add(lblWelcomToTetris);

        JLabel label_1 = new JLabel("");
        label_1.setBounds(0, 0, 772, 805);
        p1.add(label_1);
        label_1.setFont(new Font("华文行楷", Font.PLAIN, 15));
        label_1.setIcon(new ImageIcon(str_icon));

        p.add(p2, "tetris.Tetris");

        p2.add(p21);
        JLabel bac_label = new JLabel("");
        bac_label.setBounds(0, 0, 772, 805);
        bac_label.setIcon(new ImageIcon(str_icon));
        p21.add(bac_label);
        JPanel p22 = new JPanel();
        p22.setBounds(550, 0, 222, 800);
        p2.add(p22);
        p22.setLayout(null);

        JLabel label = new JLabel("\u5F97\u5206");
        label.setForeground(Color.WHITE);
        label.setFont(new Font("华文行楷", Font.BOLD, 26));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBounds(14, 315, 194, 52);
        p22.add(label);
        score_board.setHorizontalAlignment(SwingConstants.CENTER);

        score_board.setForeground(Color.WHITE);
        score_board.setFont(new Font("华文行楷", Font.BOLD, 26));
        score_board.setBounds(24, 368, 173, 52);
        p22.add(score_board);

        JButton b_pause = new JButton("\u91CD\u65B0\u5F00\u59CB");
        b_pause.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                start(2);
            }
        });
        b_pause.setForeground(Color.BLACK);
        b_pause.setFont(new Font("华文行楷", Font.BOLD, 26));
        b_pause.setBounds(44, 455, 149, 44);
        p22.add(b_pause);

        JButton b_back = new JButton("\u8FD4\u56DE\u4E3B\u83DC\u5355");
        b_back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                card.previous(p);
                game_on = false;
                timer.stop();
            }
        });
        b_back.setForeground(Color.BLACK);
        b_back.setFont(new Font("华文行楷", Font.BOLD, 20));
        b_back.setBounds(44, 560, 149, 44);
        p22.add(b_back);

        p22.add(next_panel);
        JLabel b_lLabel2 = new JLabel("");
        b_lLabel2.setBounds(0, 0, 222, 320);
        b_lLabel2.setIcon(new ImageIcon(str_icon));
        next_panel.add(b_lLabel2);

        JLabel lblwads = new JLabel("<html><body>" + "说明：" + "<br>" + "'W'或↑旋转；" + "<br>" + "'A'或←向左移动；" + "<br>"
                + "'D'或→向右移动；" + "<br>" + "'S'或↓向下移动" + "<body></html>");
        lblwads.setFont(new Font("华文楷体", Font.BOLD, 20));
        lblwads.setForeground(Color.WHITE);
        lblwads.setBounds(14, 627, 194, 160);
        p22.add(lblwads);

        JLabel bac_label2 = new JLabel("");
        bac_label2.setForeground(Color.WHITE);
        bac_label2.setBounds(0, 310, 222, 490);
        bac_label2.setIcon(new ImageIcon(str_icon));
        p22.add(bac_label2);
        p.add(p3, "Game over!");
        p3.setLayout(null);

        JButton btm = new JButton("\u56DE\u4E3B\u83DC\u5355");
        btm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                card.first(p);
            }
        });
        btm.setFont(new Font("华文行楷", Font.BOLD, 26));
        btm.setForeground(Color.BLACK);
        btm.setBounds(270, 533, 214, 69);
        p3.add(btm);

        JButton nextgame_btn = new JButton("\u518D\u6765\u4E00\u5C40");
        nextgame_btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                start(1);
            }
        });
        nextgame_btn.setForeground(Color.BLACK);
        nextgame_btn.setFont(new Font("华文行楷", Font.BOLD, 26));
        nextgame_btn.setBounds(270, 339, 214, 69);
        p3.add(nextgame_btn);

        JLabel label_2 = new JLabel("\u60A8\u7684\u6700\u7EC8\u5F97\u5206\u662F\uFF1A");
        label_2.setForeground(Color.WHITE);
        label_2.setFont(new Font("华文行楷", Font.BOLD, 26));
        label_2.setHorizontalAlignment(SwingConstants.CENTER);
        label_2.setBounds(204, 44, 343, 84);
        p3.add(label_2);

        final_score.setForeground(Color.WHITE);
        final_score.setFont(new Font("华文行楷", Font.BOLD, 26));
        final_score.setHorizontalAlignment(SwingConstants.CENTER);
        final_score.setBounds(274, 121, 181, 52);
        p3.add(final_score);

        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(0, 0, 772, 805);
        lblNewLabel.setIcon(new ImageIcon(str_icon));
        p3.add(lblNewLabel);
        p.add(p4, "Options");
        p4.setLayout(null);

        JButton btnBack = new JButton("\u4FDD\u5B58\u5E76\u8FD4\u56DE");
        btnBack.setFont(new Font("华文行楷", Font.BOLD, 26));
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int a = 1, b = 0, c = 2;
                if (rb_music1.isSelected()) {
                    a = 1;
                } else if (rb_music2.isSelected()) {
                    a = 2;
                } else if (rb_music3.isSelected()) {
                    a = 3;
                }
                if (rb_music_on.isSelected()) {
                    b = 0;
                } else if (rb_music_clo.isSelected()) {
                    b = 1;
                }
                if (rb_lev1.isSelected()) {
                    c = 1;
                } else if (rb_lev2.isSelected()) {
                    c = 2;
                } else if (rb_lev3.isSelected()) {
                    c = 3;
                }
                try {
                    PrintWriter out = new PrintWriter(new FileWriter("C:\\Users\\ASUS\\Desktop\\settings.txt"));
                    out.write(a + "\n" + b + "\n" + c);
                    out.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                card.first(p);
            }
        });
        btnBack.setBounds(300, 581, 185, 74);
        p4.add(btnBack);

        rb_music1.setBackground(Color.BLACK);
        rb_music1.setFont(new Font("华文行楷", Font.BOLD, 26));
        rb_music1.setForeground(Color.WHITE);
        rb_music1.setBounds(94, 138, 157, 27);
        p4.add(rb_music1);

        rb_music2.setBackground(Color.BLACK);
        rb_music2.setForeground(Color.WHITE);
        rb_music2.setFont(new Font("华文行楷", Font.BOLD, 26));
        rb_music2.setBounds(306, 138, 157, 27);
        p4.add(rb_music2);

        rb_music3.setBackground(Color.BLACK);
        rb_music3.setFont(new Font("华文行楷", Font.BOLD, 26));
        rb_music3.setForeground(Color.WHITE);
        rb_music3.setBounds(507, 138, 157, 27);
        p4.add(rb_music3);

        bg1.add(rb_music1);
        bg1.add(rb_music2);
        bg1.add(rb_music3);

        JLabel label_3 = new JLabel("\u80CC\u666F\u97F3\u4E50\u9009\u62E9");
        label_3.setBackground(Color.LIGHT_GRAY);
        label_3.setFont(new Font("华文行楷", Font.BOLD, 26));
        label_3.setForeground(Color.WHITE);
        label_3.setHorizontalAlignment(SwingConstants.CENTER);
        label_3.setBounds(251, 47, 212, 38);
        p4.add(label_3);

        JLabel label_4 = new JLabel("\u80CC\u666F\u97F3\u4E50\u5F00\u542F\uFF1F");
        label_4.setFont(new Font("华文行楷", Font.BOLD, 26));
        label_4.setForeground(Color.WHITE);
        label_4.setBounds(271, 231, 197, 46);
        p4.add(label_4);

        rb_music_on.setBackground(Color.BLACK);
        rb_music_on.setForeground(Color.WHITE);
        rb_music_on.setFont(new Font("华文行楷", Font.BOLD, 26));
        rb_music_on.setBounds(159, 311, 157, 27);
        p4.add(rb_music_on);

        rb_music_clo.setForeground(Color.WHITE);
        rb_music_clo.setBackground(Color.BLACK);
        rb_music_clo.setFont(new Font("华文行楷", Font.BOLD, 26));
        rb_music_clo.setBounds(445, 311, 157, 27);
        p4.add(rb_music_clo);

        bg2.add(rb_music_clo);
        bg2.add(rb_music_on);

        JLabel label_5 = new JLabel("\u6E38\u620F\u96BE\u5EA6");
        label_5.setFont(new Font("华文行楷", Font.BOLD, 26));
        label_5.setHorizontalAlignment(SwingConstants.CENTER);
        label_5.setForeground(Color.WHITE);
        label_5.setBounds(271, 398, 197, 46);
        p4.add(label_5);

        rb_lev1.setForeground(Color.WHITE);
        rb_lev1.setBackground(Color.BLACK);
        rb_lev1.setFont(new Font("华文行楷", Font.BOLD, 26));
        rb_lev1.setBounds(94, 482, 157, 27);
        p4.add(rb_lev1);

        rb_lev2.setForeground(Color.WHITE);
        rb_lev2.setFont(new Font("华文行楷", Font.BOLD, 26));
        rb_lev2.setBackground(Color.BLACK);
        rb_lev2.setBounds(311, 482, 157, 27);
        p4.add(rb_lev2);

        rb_lev3.setForeground(Color.WHITE);
        rb_lev3.setBackground(Color.BLACK);
        rb_lev3.setFont(new Font("华文行楷", Font.BOLD, 26));
        rb_lev3.setBounds(507, 482, 157, 27);
        p4.add(rb_lev3);

        bg3.add(rb_lev1);
        bg3.add(rb_lev2);
        bg3.add(rb_lev3);

        JLabel lblnote = new JLabel(
                "\u6CE8\u610F\uFF1A\u9000\u51FA\u6E38\u620F\u540E\u518D\u6B21\u6253\u5F00\u65F6\u4F1A\u5E94\u7528\u672C\u6B21\u4FEE\u6539\u7684\u8BBE\u7F6E");
        lblnote.setForeground(Color.WHITE);
        lblnote.setFont(new Font("华文楷体", Font.BOLD, 26));
        lblnote.setHorizontalAlignment(SwingConstants.CENTER);
        lblnote.setBounds(14, 668, 744, 46);
        p4.add(lblnote);

        JLabel b_label = new JLabel("");
        b_label.setBounds(0, 0, 772, 816);
        b_label.setIcon(new ImageIcon(str_icon));
        p4.add(b_label);

        String fstr = readFile();
        if (fstr == null || fstr.split("\n").length < 2) {
            rb_music1.setSelected(true);
            rb_music_on.setSelected(true);
            rb_lev2.setSelected(true);
        } else {
            s_str = fstr.split("\n");
            switch (Integer.parseInt(s_str[1].trim())) {
                case 1:
                    rb_music1.setSelected(true);
                    break;
                case 2:
                    rb_music2.setSelected(true);
                    break;
                case 3:
                    rb_music3.setSelected(true);
                    break;
            }
            switch (Integer.parseInt(s_str[2].trim())) {
                case 0: {
                    rb_music_on.setSelected(true);
                    break;
                }
                case 1:
                    rb_music_clo.setSelected(true);
                    break;
            }
            switch (Integer.parseInt(s_str[3].trim())) {
                case 1:
                    rb_lev1.setSelected(true);
                    break;
                case 2:
                    rb_lev2.setSelected(true);
                    break;
                case 3:
                    rb_lev3.setSelected(true);
                    break;
            }
        }
        if (rb_music1.isSelected()) {
            str = str1;
        } else if (rb_music2.isSelected()) {
            str = str2;
        } else if (rb_music3.isSelected()) {
            str = str3;
        }
        if (rb_music_on.isSelected()) {
            music(str);
            clip.loop(clip.LOOP_CONTINUOUSLY);
        }
        if (rb_lev1.isSelected()) {
            level = 1500;
        } else if (rb_lev1.isSelected()) {
            level = 1000;
        } else if (rb_lev3.isSelected()) {
            level = 500;
        }
        rb_music1.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (rb_music1.isSelected()) {
                    str = str1;
                    if (clip.isActive()) {
                        clip.stop();
                        music(str);
                        clip.loop(clip.LOOP_CONTINUOUSLY);
                    }
                }
            }
        });
        rb_music2.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (rb_music2.isSelected()) {
                    str = str2;
                    if (clip.isActive()) {
                        clip.stop();
                        music(str);
                        clip.loop(clip.LOOP_CONTINUOUSLY);
                    }
                }

            }
        });
        rb_music3.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (rb_music3.isSelected()) {
                    str = str3;
                    if (clip.isActive()) {
                        clip.stop();
                        music(str);
                        clip.loop(clip.LOOP_CONTINUOUSLY);
                    }
                }
            }
        });

        rb_music_on.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (rb_music_on.isSelected()) {
                    music(str);
                    clip.loop(clip.LOOP_CONTINUOUSLY);
                }
            }
        });
        rb_music_clo.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (rb_music_clo.isSelected()) {
                    if (clip != null)
                        clip.stop();
                }
            }
        });

        rb_lev1.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                level = 1500;
            }
        });
        rb_lev2.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                level = 1000;
            }
        });
        rb_lev3.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                level = 500;
            }
        });
    }

    public void start(int key) {
        score = 0;
        score_board.setText(score + "");
        p21.requestFocus();
        if (key == 0) {
            card.next(p);
        } else if (key == 1) {
            card.previous(p);
        }
        game_on = true;
        tn = (int) (Math.random() * 7);
        makemap();
        nextblock();
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
        timer = new Timer(level, new TimerListener());
        timer.start();
    }

    public String readFile() {
        File file = new File(path);
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));// 构造一个BufferedReader类来读取文件

            String s = null;
            while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
                result.append(System.lineSeparator() + s);
            }
            br.close();
        } catch (Exception e) {
            return null;
            // e.printStackTrace();
        }
        return result.toString();
    }

    public void music(String str) {
        if (!str.equals("none")) {
            try {
                File soundFile = new File(str);
                AudioInputStream sound = AudioSystem.getAudioInputStream(soundFile);
                DataLine.Info info = new DataLine.Info(Clip.class, sound.getFormat());
                clip = (Clip) AudioSystem.getLine(info);
                clip.open(sound);
                clip.addLineListener(new LineListener() {
                    public void update(LineEvent event) {
                        if (event.getType() == LineEvent.Type.STOP) {
                            event.getLine().close();
                            // System.exit(0);
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class TimerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (game_on) {
                p21.repaint();
                if (check_m(s, t, x, y + 1) == 1) {
                    y++;
                }
                if (dtb()) {
                    game_over();
                    nextblock();
                }
                p21.requestFocus();
            }
        }
    }

    class Panel3 extends JPanel {
        public Panel3() {
            setBounds(0, 0, 222, 320);
            setLayout(null);
        }

        public void paint(Graphics g) {
            super.paint(g);
            switch (tn) {
                case 0:
                    g.setColor(Color.blue);
                    break;
                case 1:
                    g.setColor(Color.orange);
                    break;
                case 2:
                    g.setColor(Color.pink);
                    break;
                case 3:
                    g.setColor(Color.yellow);
                    break;
                case 4:
                    g.setColor(Color.red);
                    break;
                case 5:
                    g.setColor(Color.cyan);
                    break;
                case 6:
                    g.setColor(Color.green);
                    break;
            }
            setForeground(Color.white);
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (shapes[tn][0][i * 4 + j] == 1) {
                        g.fillRect(20 + j * len, 20 + i * len, len, len);
                    }
                }
            }
        }
    }

    class Panel2 extends JPanel implements KeyListener {
        public Panel2() {
            setBounds(0, 0, 550, 800);
            setLayout(null);
        }

        public void keyTyped(KeyEvent e) {

        }

        public void keyReleased(KeyEvent e) {

        }

        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_S:
                case KeyEvent.VK_DOWN: {
                    // p21.repaint();
                    if (check_m(s, t, x, y + 1) == 1) {
                        y++;
                    }
                    if (dtb()) {
                        nextblock();
                    }
                    p21.repaint();
                    game_over();
                    // p21.requestFocus();
                    break;
                }
                case KeyEvent.VK_W:
                case KeyEvent.VK_UP: {
                    // p21.repaint();
                    if (check_m((s + 1) % 4, t, x, y) == 1) {
                        s = (s + 1) % 4;
                        p21.repaint();
                    }
                    // p21.requestFocus();
                    break;
                }
                case KeyEvent.VK_A:
                case KeyEvent.VK_LEFT: {
                    // p21.repaint();
                    if (check_m(s, t, x - 1, y) == 1) {
                        x--;
                        p21.repaint();
                    }
                    // p21.requestFocus();
                    break;
                }
                case KeyEvent.VK_D:
                case KeyEvent.VK_RIGHT: {
                    // p21.repaint();
                    if (check_m(s, t, x + 1, y) == 1) {
                        x++;
                        p21.repaint();
                    }
                    // p21.requestFocus();
                    break;
                }
                default:
                    break;
            }
        }

        public void paint(Graphics g) {
            super.paint(g);
            switch (t) {
                case 0:
                    g.setColor(Color.blue);
                    break;
                case 1:
                    g.setColor(Color.orange);
                    break;
                case 2:
                    g.setColor(Color.pink);
                    break;
                case 3:
                    g.setColor(Color.yellow);
                    break;
                case 4:
                    g.setColor(Color.red);
                    break;
                case 5:
                    g.setColor(Color.cyan);
                    break;
                case 6:
                    g.setColor(Color.green);
                    break;
            }
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (shapes[t][s][i * 4 + j] == 1) {
                        g.fillRect((x + j) * len, (y + i - 1) * len, len, len);
                    }
                }
            }
            for (int i = 1; i <= hight; i++) {
                for (int j = 0; j < width; j++) {
                    if (map[i][j] == 2 || map[i][j] == 0) {
                        g.setColor(new Color(135, 135, 135));
                        g.fillRect(j * len, (i - 1) * len, len, len);
                    }
                }
            }
            p21.requestFocus();
        }
    }
}
