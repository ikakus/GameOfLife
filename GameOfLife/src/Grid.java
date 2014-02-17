import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


/**
 * Created by ikakus on 2/9/14.
 */
public class Grid extends JPanel {

    JLabel view;
    BufferedImage surface;


    private ArrayList<Point> mLiveCells;

    private int mWindowWidth = 101;
    private int mWindowHeight = 101;
    private int mStep = 10;




    private int mMapWidth = mWindowWidth / mStep;
    private int mMapHeight = mWindowHeight / mStep;

    private int mMap[][] = new int[mMapWidth][mMapHeight];

    public Grid() {

        mLiveCells = new ArrayList<Point>();
        surface = new BufferedImage(mWindowWidth, mWindowHeight, BufferedImage.TYPE_INT_RGB);
        view = new JLabel(new ImageIcon(surface));
        Graphics g = surface.getGraphics();
        g.setColor(Color.ORANGE);
        g.fillRect(0, 0, mWindowWidth, mWindowHeight);
        g.setColor(Color.BLACK);
        g.dispose();

        AddCell(3, 3);
        AddCell(3, 4);
        AddCell(3, 5);


        ReDrawGrid();
        ActionListener listener = new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                Test();
                ReDrawGrid();
            }
        };

        Timer timer = new Timer(1000, listener);
        timer.start();

    }

    private void Test() {
        OutputMap();
        CheckRules();
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
        for (Point fillCell : mLiveCells) {
            int cellX = step + (fillCell.x * step);
            int cellY = step + (fillCell.y * step);
            g.setColor(Color.BLACK);
            g.fillRect(cellX, cellY, step, step);
        }
        g.setColor(Color.BLACK);
        g.drawRect(step, step, width, height);
    }

    private void DrawGrid(Graphics g, int step, int width, int height) {
        for (int i = 0; i <= width; i += step) {
            g.drawLine(i, 0, i, height + step);
        }

        for (int i = 0; i <= height; i += step){
            g.drawLine(0, i, width + step, i);
        }
    }


    private void OutputMap()
    {
        for (int i = 1; i < mMapWidth - 1; i++)
        {
            System.out.println();
            for (int j = 1; j < mMapHeight - 1; j++) {

                System.out.print(Integer.toString( mMap[i][j]));

            }
        }
        System.out.println();
    }
    private void CheckRules() {
       // mLiveCells = new ArrayList<Point>();

        for (int i = 1; i < mMapWidth - 1; i++)
            for (int j = 1; j < mMapHeight - 1; j++) {

                if(mMap[i][j] ==3){
                    if(!ContainsCell(new Point(i,j)))
                    AddCell(i,j);
                }else

                if (mMap[i][j] != 0 ) {

                    if(mMap[i][j]>3 || mMap[i][j]<2)
                    if (ContainsCell(new Point(i, j)))
                        RemoveCell(i, j);
                }

            }
    }

    public void AddCell(int x, int y) {
        Point p = new Point(x, y);
        mLiveCells.add(p);
        AppendNeighborCells(p);
        repaint();
    }

    public void RemoveCell(int x, int y) {
        Point p = new Point(x, y);

        for(int i =0; i< mLiveCells.size()-1; i++)
        {
            if(p.x==mLiveCells.get(i).x && p.y == mLiveCells.get(i).y)
            {
                mLiveCells.remove(i);
            }
        }

        DecrementNeighborCells(p);
        repaint();
    }

    private boolean ContainsCell(Point point) {

        for (Point p : mLiveCells) {
            if (point.x == p.x && point.y == p.y)
                return true;
        }

        return false;
    }

    private void DecrementNeighborCells(Point point) {
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

    private void AppendNeighborCells(Point point) {
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