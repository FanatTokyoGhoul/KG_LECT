import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.List;

/*Класс главного окна добавил 1 кнопку и саму панель для отрисовки*/

public class MainFrame extends JFrame implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
    public OBJLoader objLoader = new OBJLoader();
    public Obj t = objLoader.loadObj("./obj/Box.obj");
    private Button button = new Button("Load File");
    private Vector2f startVector = new Vector2f(0,0);/*Для удобства добавил скролинг мышкой*/
    private Vector2f endVector = new Vector2f(0,0);
    private int deviationByX = 0;
    private int deviationByY = 0;
    private int rotateX = 0;
    private int rotateY = 0;
    private boolean inversion = false;


    public MainFrame() {
        super("Test");
        setMinimumSize(new Dimension(500, 500));
        setContentPane(new DrawPanel3D());
        add(button);
        setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
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
                    RotateUtils.rotateX(t.getVertices(), 6);
                    this.repaint();
                break;
            case KeyEvent.VK_S:
                    RotateUtils.rotateX(t.getVertices(), -6);
                    this.repaint();
                break;
            case KeyEvent.VK_D:
                    RotateUtils.rotateY(t.getVertices(), 6);
                    this.repaint();
                break;
            case KeyEvent.VK_A:
                    RotateUtils.rotateY(t.getVertices(), -6);
                    this.repaint();
                break;
            case KeyEvent.VK_Q:
                    RotateUtils.rotateX(t.getVertices(), 3);
                    RotateUtils.rotateY(t.getVertices(), -3);
                    this.repaint();
                break;
            case KeyEvent.VK_E:
                    RotateUtils.rotateX(t.getVertices(), 3);
                    RotateUtils.rotateY(t.getVertices(), 3);
                    this.repaint();
                break;
            case KeyEvent.VK_Z:
                    RotateUtils.rotateZ(t.getVertices(), 6);
                    this.repaint();
                break;
            case KeyEvent.VK_X:
                    RotateUtils.rotateZ(t.getVertices(), -6);
                    this.repaint();
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
            case KeyEvent.VK_SPACE:
                inversion = true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_SPACE:
                inversion = false;
                break;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {/*Сама реализация скролинга сначала запоминаются 2 начальныекоординаты*/
        startVector.x = e.getX();
        startVector.y = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {/*А здесь идёт пересчет отклонения*/
        if(inversion) {
            endVector.x = e.getX();
            endVector.y = e.getY();
            deviationByX += endVector.x - startVector.x;
            deviationByY += endVector.y - startVector.y;
            repaint();
            startVector.x = e.getX();
            startVector.y = e.getY();
        }else {
            endVector.x = e.getX();
            endVector.y = e.getY();
            rotateX = (int) (endVector.x - startVector.x);
            rotateY = (int) (endVector.y - startVector.y);
            RotateUtils.rotateY(t.getVertices(),  rotateX);
            RotateUtils.rotateX(t.getVertices(), -1*rotateY);

            repaint();
            startVector.x = e.getX();
            startVector.y = e.getY();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    /*Увелечения размера с помощью колёсика мыши*/

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int rotation = -1 * e.getWheelRotation();
        for (Vector3f vector3f : t.getVertices()) {
            ScaleUtils.scale(vector3f, rotation * 0.1 + 1);
            this.repaint();
        }

    }

    class DrawPanel3D extends JPanel {
        @Override
        public void paint(Graphics graphics) {
            Graphics2D gr = (Graphics2D) graphics;
            drawObjTest(gr, t);
        }
    }

    public void drawObjTest(Graphics2D gr, Obj obj){
        for (List<Vector2f> vector2fList : obj.getMask()) {
            int[] arrayX = new int[vector2fList.size()];
            int[] arrayY = new int[vector2fList.size()];
            double[] arrayZ = new double[vector2fList.size()];
            for (int i = 0; i < vector2fList.size(); i++) {
                arrayX[i] = (int)(obj.getVertices().get((int) vector2fList.get(i).x - 1).x * (500/(-500 + obj.getVertices().get((int) vector2fList.get(i).x - 1).z)) + 250 + deviationByX);
                arrayY[i] = (int)(obj.getVertices().get((int) vector2fList.get(i).x - 1).y * (500/(-500 + obj.getVertices().get((int) vector2fList.get(i).x - 1).z)) + 250 + deviationByY);
                //arrayZ[i] = obj.getVertices().get((int) vector2fList.get(i).x - 1).z;
            }
            gr.fillPolygon(arrayX,arrayY,vector2fList.size());
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
