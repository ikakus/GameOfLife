import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;


/**
 * Created by ikakus on 2/9/14.
 */
public class Grid extends JPanel {

    JLabel view;
    BufferedImage surface;


    private int mWindowWidth = 601;
    private int mWindowHeight = 501;
    private int mStep = 3;


    private int mMapWidth = mWindowWidth / mStep;
    private int mMapHeight = mWindowHeight / mStep;

    private int mMapGeneration1[][] = new int[mMapWidth][mMapHeight];
    private int mMapGeneration2[][]; //= new int[mMapWidth][mMapHeight];

    private boolean mLiveCells[][] = new boolean[mMapWidth][mMapHeight];

    public Grid() {

        surface = new BufferedImage(mWindowWidth, mWindowHeight, BufferedImage.TYPE_INT_RGB);
        view = new JLabel(new ImageIcon(surface));
        Graphics g = surface.getGraphics();
        g.setColor(Color.ORANGE);
        g.fillRect(0, 0, mWindowWidth, mWindowHeight);
        g.setColor(Color.BLACK);
        g.dispose();

        AddGlider();


        ReDrawGrid();
        ActionListener listener = new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                CheckRules();
                ReDrawGrid();
            }
        };

        Timer timer = new Timer(300, listener);
        timer.start();

    }


    private void AddBlinker() {
        AddCell(3, 1, mMapGeneration1);
        AddCell(3, 2, mMapGeneration1);
        AddCell(3, 3, mMapGeneration1);
    }

    private void AddGlider() {

        AddCell(2, 1, mMapGeneration1);
        AddCell(3, 2, mMapGeneration1);
        AddCell(1, 3, mMapGeneration1);
        AddCell(2, 3, mMapGeneration1);
        AddCell(3, 3, mMapGeneration1);
    }

    public void ReDrawGrid() {
        Graphics g = surface.getGraphics();
        g.setColor(Color.ORANGE);
        g.fillRect(0, 0, mWindowWidth, mWindowHeight);
        FillCells(g, mStep, mWindowWidth, mWindowHeight);
        DrawGrid(g, mStep, mWindowWidth, mWindowHeight);
        g.dispose();
        view.repaint();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        FillCells(g, mStep, mWindowWidth, mWindowHeight);
        DrawGrid(g, mStep, mWindowWidth, mWindowHeight);
    }

    private void FillCells(Graphics g, int step, int width, int height) {

        for (int i = 1; i < mMapWidth - 1; i++)
            for (int j = 1; j < mMapHeight - 1; j++) {

                if (mLiveCells[i][j] == true) {
                    int cellX = step + (i * step);
                    int cellY = step + (j * step);
                    g.setColor(Color.BLACK );
                    g.fillRect(cellX, cellY, step, step);
                }
            }

        g.setColor(Color.BLACK);
        g.drawRect(step, step, width, height);
    }

    private void DrawGrid(Graphics g, int step, int width, int height) {
        for (int i = 0; i <= width; i += step) {
            g.drawLine(i, 0, i, height + step);
        }

        for (int i = 0; i <= height; i += step) {
            g.drawLine(0, i, width + step, i);
        }
    }


    private void OutputMap() {
        for (int i = 1; i < mMapWidth - 1; i++) {
            System.out.println();
            for (int j = 1; j < mMapHeight - 1; j++) {
                System.out.print(Integer.toString(mMapGeneration1[i][j]));
            }
        }
        System.out.println();
    }

    private void CheckRules() {

        mMapGeneration2 = new int[mMapWidth][mMapHeight];
        for (int i = 1; i < mMapWidth - 1; i++)
            for (int j = 1; j < mMapHeight - 1; j++) {

                if (mMapGeneration1[i][j] == 3) {
                    AddCell(i, j, mMapGeneration2);
                }
                if (mMapGeneration1[i][j] == 2) {
                    if (ContainsCell(new Point(i, j)))
                        AddCell(i, j, mMapGeneration2);
                }
            }

        for (int i = 0; i < mMapWidth - 1; i++)
            for (int j = 0; j < mMapHeight - 1; j++) {
                if (mMapGeneration1[i][j] > 3 || mMapGeneration1[i][j] < 2)
                    if (ContainsCell(new Point(i, j))) {
                        // RemoveCell(i, j,mMapGeneration2);
                        mLiveCells[i][j] = false;
                    }
            }
        mMapGeneration1 = mMapGeneration2;
    }

    public void AddCell(int x, int y, int Map[][]) {
        Point p = new Point(x, y);
        mLiveCells[x][y] = true;
        AppendNeighborCells(p, Map);
        repaint();
    }

    public void RemoveCell(int x, int y, int Map[][]) {
        Point p = new Point(x, y);

        mLiveCells[x][y] = false;

        DecrementNeighborCells(p, Map);
        repaint();
    }

    private boolean ContainsCell(Point point) {

        if (mLiveCells[point.x][point.y] == true)
            return true;

        return false;
    }

    private void DecrementNeighborCells(Point point, int mMap[][]) {
        if (mMap[point.x - 1][point.y] != 0)
            mMap[point.x - 1][point.y]--;

        if (mMap[point.x - 1][point.y - 1] != 0)
            mMap[point.x - 1][point.y - 1]--;

        if (mMap[point.x - 1][point.y + 1] != 0)
            mMap[point.x - 1][point.y + 1]--;

        if (mMap[point.x + 1][point.y] != 0)
            mMap[point.x + 1][point.y]--;

        if (mMap[point.x + 1][point.y - 1] != 0)
            mMap[point.x + 1][point.y - 1]--;

        if (mMap[point.x + 1][point.y + 1] != 0)
            mMap[point.x + 1][point.y + 1]--;

        if (mMap[point.x][point.y - 1] != 0)
            mMap[point.x][point.y - 1]--;

        if (mMap[point.x][point.y + 1] != 0)
            mMap[point.x][point.y + 1]--;
    }

    private void AppendNeighborCells(Point point, int mMap[][]) {
        mMap[point.x - 1][point.y]++;
        mMap[point.x - 1][point.y - 1]++;
        mMap[point.x - 1][point.y + 1]++;
        mMap[point.x + 1][point.y]++;
        mMap[point.x + 1][point.y - 1]++;
        mMap[point.x + 1][point.y + 1]++;
        mMap[point.x][point.y - 1]++;
        mMap[point.x][point.y + 1]++;
    }

}