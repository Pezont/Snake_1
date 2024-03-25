

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIGHT = 600;
    static final int SCREEN_HIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = 14400;
    static final int DELAY = 70;
    final int[] x = new int[14400];
    final int[] y = new int[14400];
    int bodyParts = 5;
    int applesEasten;
    int applesX;
    int applesY;
    char diraktion = 'R';
    boolean run = false;
    Timer timer;
    Random random = new Random();
    Color colorBody = Color.CYAN;
    Color colorHead = Color.BLUE;
    Color colorApple = Color.RED;

    GamePanel() {
        this.setPreferredSize(new Dimension(600, 600));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new GamePanel.MyKeyAdapter());
        this.startGame();
    }

    public void startGame() {
        this.newApple();
        this.run = true;
        this.timer = new Timer(150, this);
        this.timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.draw(g);
    }

    public void draw(Graphics g) {
        if (this.run) {
            g.setColor(colorApple);
            g.fillOval(this.applesX, this.applesY, UNIT_SIZE, UNIT_SIZE);

            for(int i = 0; i < this.bodyParts; ++i) {
                if (i == 0) {
                    g.setColor(colorHead);
                    g.fillRect(this.x[i], this.y[i], UNIT_SIZE,UNIT_SIZE);
                } else {
                    g.setColor(colorBody);
                    g.fillRect(this.x[i], this.y[i], UNIT_SIZE,UNIT_SIZE);
                }
            }

            g.setColor(Color.RED);
            g.setFont(new Font("Calibri", 1, 40));
            FontMetrics metrics = this.getFontMetrics(g.getFont());
            g.drawString("SCORE:" + this.applesEasten, (600 - metrics.stringWidth("SCORE:" + this.applesEasten)) / 2, g.getFont().getSize());
        } else {
            this.gameOver(g);
        }

    }

    public void newApple() {
        int x = this.random.nextInt(24) * 25;
        int y = this.random.nextInt(24) * 25;
        this.applesX = x;
        this.applesY = y;
    }

    public void move() {
        for(int i = this.bodyParts; i > 0; --i) {
            this.x[i] = this.x[i - 1];
            this.y[i] = this.y[i - 1];
        }

        switch(this.diraktion) {
            case 'D':
                this.y[0] += 25;
                break;
            case 'L':
                this.x[0] -= 25;
                break;
            case 'R':
                this.x[0] += 25;
                break;
            case 'U':
                this.y[0] -= 25;
        }

    }

    public void checkApple() {
        if (this.x[0] == this.applesX && this.y[0] == this.applesY) {
            ++this.bodyParts;
            ++this.applesEasten;
            this.newApple();
        }

    }

    public void checkCollins() {
        for(int i = this.bodyParts; i > 0; --i) {
            if (this.x[0] == this.x[i] && this.y[0] == this.y[i]) {
                this.run = false;
            }

            if (this.x[0] < 0) {
                this.run = false;
            }

            if (this.x[0] > 600) {
                this.run = false;
            }

            if (this.y[0] < 0) {
                this.run = false;
            }

            if (this.y[0] > 600) {
                this.run = false;
            }

            if (!this.run) {
                this.timer.stop();
            }
        }

    }

    public void gameOver(Graphics g) {
        String st;
        Color color;
        if(this.applesEasten>=250){
            st = "GAME WIN";
            color = Color.green;
        }
        else{ st = "GAME OVER";
            color = Color.red;}

        g.setColor(color);
        g.setFont(new Font("Calibri", 1, 75));
        FontMetrics metrics0 = this.getFontMetrics(g.getFont());
        g.drawString(st, (600 - metrics0.stringWidth(st)) / 2, 300);

        g.setColor(Color.RED);
        g.setFont(new Font("Calibri", 1, 40));
        FontMetrics metrics = this.getFontMetrics(g.getFont());
        g.drawString("SCORE:" + this.applesEasten, (600 - metrics.stringWidth("SCORE:" + this.applesEasten)) / 2, g.getFont().getSize());
    }

    public void actionPerformed(ActionEvent e) {
        if (this.run) {
            this.move();
            this.checkApple();
            this.checkCollins();
        }

        this.repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        public MyKeyAdapter() {
        }

        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()) {
                case 37:
                    if (GamePanel.this.diraktion != 'R') {
                        GamePanel.this.diraktion = 'L';
                    }
                    break;
                case 38:
                    if (GamePanel.this.diraktion != 'D') {
                        GamePanel.this.diraktion = 'U';
                    }
                    break;
                case 39:
                    if (GamePanel.this.diraktion != 'L') {
                        GamePanel.this.diraktion = 'R';
                    }
                    break;
                case 40:
                    if (GamePanel.this.diraktion != 'U') {
                        GamePanel.this.diraktion = 'D';
                    }
            }

        }
    }
}
