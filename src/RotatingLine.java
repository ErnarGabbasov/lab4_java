import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

// Класс, который создает и отображает окно
public class RotatingLine extends JFrame {

    public RotatingLine() {
        setTitle("Rotating Line");
        setSize(350, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        LinePanel linePanel = new LinePanel();
        add(linePanel);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new RotatingLine();
            }
        });
    }
}

// Панель, на которой отображается вращающаяся линия 
// Панель содержит линию и таймер, который каждые 10 миллисекунд обновляет угол вращения линии и перерисовывает панель
class LinePanel extends JPanel {

    private Line line;
    private Timer timer;

    public LinePanel() {
        line = new Line(100, 100, 200, 200);
        setBackground(Color.WHITE);

        timer = new Timer(10, new ActionListener() {
            int angle = 0;
            // Таймер каждые 10 миллисекунд вызывает метод actionPerformed, 
            // который увеличивает угол вращения линии на 1 градус и вызывает метод repaint для перерисовки панели
            public void actionPerformed(ActionEvent e) {
                angle = (angle + 1) % 360;
                line.setAngle(angle);
                repaint();
            }
        });
        timer.start();
    }
    // В методе paintComponent панели вызывается метод draw линии для ее отображения
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        line.draw(g);
    }
}

// Класс, который представляет линию и содержит методы для изменения угла вращения линии и ее отображения
class Line {

    private int x1, y1, x2, y2;
    private int angle;

    public Line(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    // В методе draw линии вычисляются новые координаты концов линии, используя текущий угол вращения и длину линии
    public void draw(Graphics g) {
        int x = (x1 + x2) / 2;
        int y = (y1 + y2) / 2;
        int dx = x2 - x1;
        int dy = y2 - y1;
        int len = (int) Math.sqrt(dx * dx + dy * dy);
        double rad = Math.toRadians(angle);
        int nx1 = (int) (x - len * Math.sin(rad));
        int nx2 = (int) (x + len * Math.sin(rad));
        int ny1 = (int) (y + len * Math.cos(rad));
        int ny2 = (int) (y - len * Math.cos(rad));
        g.setColor(getColor());
        g.drawLine(nx1, ny1, nx2, ny2); // Линия отображается на панели с помощью метода drawLine
    }

    // Цвет линии вычисляется на основе текущего угла вращения с помощью метода getColor
    private Color getColor() {
        int hue = (int) (angle / 360f * 255f);
        return Color.getHSBColor((float) hue / 255f, 1f, 1f);
    }
}
