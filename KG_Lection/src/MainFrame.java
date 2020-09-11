import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.List;

/*Класс главного окна добавил 1 кнопку и саму панель для отрисовки*/

public class MainFrame extends JFrame implements KeyListener, MouseListener {
    public OBJLoader objLoader = new OBJLoader();
    public Obj t = objLoader.loadObj("./obj/Box.obj");
    private Button button = new Button("Load File");
    private Vector2f startVector = new Vector2f(0,0);/*Для удобства добавил скролинг мышкой*/
    private Vector2f endVector = new Vector2f(0,0);
    private int deviationByX = 0;
    private int deviationByY = 0;


    public MainFrame() {
        super("Test");
        setMinimumSize(new Dimension(500, 500));
        setContentPane(new DrawPanel3D());
        add(button);
        setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);
        setVisible(true);
        JFileChooser fileChooserObjOpen = new JFileChooser();/*Окошко для загрузки внешних файлов*/
        fileChooserObjOpen.setCurrentDirectory(new File("./"));
        fileChooserObjOpen.addChoosableFileFilter(new FileNameExtensionFilter("Obj Files (*.obj)", "obj"));
        fileChooserObjOpen.setAcceptAllFileFilterUsed(false);
        fileChooserObjOpen.setDialogType(JFileChooser.SAVE_DIALOG);
        fileChooserObjOpen.setApproveButtonText("Load");
        button.addActionListener(e -> {
            if (fileChooserObjOpen.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    t = objLoader.loadObj(fileChooserObjOpen.getSelectedFile());
                    this.repaint();
                } catch (Exception exc) {
                    System.err.print("Ошибка загрузки файла!");
                }
            }
            button.setFocusable(false);/*Клавиатура не работает если нет фокуса на Frame*/
            this.setFocusable(true);
            deviationByX = 0;
            deviationByY = 0;
        });
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) { /*Пока просто заменил if else на switch case.*/
        switch (e.getKeyCode()) {        /*Но я ещё подумаю. Есть идея использовать мапы, но она до конца не продумана*/
            case KeyEvent.VK_W:
                for (Vector3f vector3f : t.getVertices()) {
                    RotateUtils.rotateX(vector3f, 6);
                    this.repaint();
                }
                break;
            case KeyEvent.VK_S:
                for (Vector3f vector3f : t.getVertices()) {
                    RotateUtils.rotateX(vector3f, -6);
                    this.repaint();
                }
                break;
            case KeyEvent.VK_D:
                for (Vector3f vector3f : t.getVertices()) {
                    RotateUtils.rotateY(vector3f, 6);
                    this.repaint();
                }
                break;
            case KeyEvent.VK_A:
                for (Vector3f vector3f : t.getVertices()) {
                    RotateUtils.rotateY(vector3f, -6);
                    this.repaint();
                }
                break;
            case KeyEvent.VK_Q:
                for (Vector3f vector3f : t.getVertices()) {
                    RotateUtils.rotateX(vector3f, 3);
                    RotateUtils.rotateY(vector3f, -3);
                    this.repaint();
                }
                break;
            case KeyEvent.VK_E:
                for (Vector3f vector3f : t.getVertices()) {
                    RotateUtils.rotateX(vector3f, 3);
                    RotateUtils.rotateY(vector3f, 3);
                    this.repaint();
                }
                break;
            case KeyEvent.VK_Z:
                for (Vector3f vector3f : t.getVertices()) {
                    RotateUtils.rotateZ(vector3f, 6);
                    this.repaint();
                }
                break;
            case KeyEvent.VK_X:
                for (Vector3f vector3f : t.getVertices()) {
                    RotateUtils.rotateZ(vector3f, -6);
                    this.repaint();
                }
                break;
            case KeyEvent.VK_O:
                for (Vector3f vector3f : t.getVertices()) {
                    ScaleUtils.scale(vector3f, 1.1);
                    this.repaint();
                }
                break;
            case KeyEvent.VK_I:
                for (Vector3f vector3f : t.getVertices()) {
                    ScaleUtils.scale(vector3f, 0.9);
                    this.repaint();
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {/*Сама реализация скролинга просто запоминаются 4 координаты и по ним считается отклонение*/
        startVector.x = e.getX();
        startVector.y = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        endVector.x = e.getX();
        endVector.y = e.getY();
        deviationByX += endVector.x - startVector.x;
        deviationByY += endVector.y - startVector.y;
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    class DrawPanel3D extends JPanel {
        @Override
        public void paint(Graphics graphics) {
            Graphics2D gr = (Graphics2D) graphics;
            drawObj(gr, t);
        }
    }

    /*Метод для отрисовки 3d объекта. Здесь мы проходимся по всем маскам  и соединяем вершины с помощью линий*/
    public void drawObj(Graphics2D gr, Obj obj){
        for (List<Vector2f> vector2fList : obj.getMask()) {
            for (int i = 0; i < vector2fList.size(); i++) {
                Vector3f end;
                Vector3f start;
                if (i == vector2fList.size() - 1) {
                    start = obj.getVertices().get((int) vector2fList.get(i).x - 1);
                    end = obj.getVertices().get((int) vector2fList.get(0).x - 1);
                } else {
                    start = obj.getVertices().get((int) vector2fList.get(i).x - 1);
                    end = obj.getVertices().get((int) vector2fList.get(i + 1).x - 1);
                }
                /*Точно так и не понял как это работает. Но формулу нашел в интернете и доказательство для неё
                  Она считает проекцию 3d объекта на 2d плоскость. Тоесть переводит 3d координаты в 2d.
                 */
                double tempZStart = (500 / (-500 + start.z));
                double tempZEnd = (500 / (-500 + end.z));
                double x2dStart = start.x * tempZStart;
                double y2dStart = start.y * tempZStart;
                double x2dEnd = end.x * tempZEnd;
                double y2dEnd = end.y * tempZEnd;
                /*И рисуем линию*/
                gr.drawLine((int) x2dStart + 250 + deviationByX, (int) y2dStart + 250 + deviationByY, (int) x2dEnd + 250 + deviationByX, (int) y2dEnd + 250 + deviationByY);
            }
        }
    }
}
