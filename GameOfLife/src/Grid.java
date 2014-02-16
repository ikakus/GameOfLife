import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ikakus on 2/9/14.
 */
public class Grid extends JPanel {

    JLabel view;
    BufferedImage surface;
    public List<Point> getmLiveCells() {
        return mLiveCells;
    }

    public void setmLiveCells(List<Point> mLiveCells) {
        this.mLiveCells = mLiveCells;
    }

    private List<Point> mLiveCells;

    private int mWidth = 600;
    private int mHeight = 400;
    private int i =0;

    public Grid() {

        mLiveCells = new ArrayList<Point>();
        surface = new BufferedImage(mWidth,mHeight,BufferedImage.TYPE_INT_RGB);
        view = new JLabel(new ImageIcon(surface));
        Graphics g = surface.getGraphics();
        g.setColor(Color.ORANGE);
        g.fillRect(0,0,mWidth,mHeight);
        g.setColor(Color.BLACK);
        g.dispose();

        ActionListener listener = new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                Test();
                RecalcGrid();
            }
        };

        Timer timer = new Timer(500, listener);
        timer.start();

    }

    private void Test()
    {
        fillCell(i,0);
         i++;
    }



    public void RecalcGrid()
    {
        int step = 10;
        int width = 800;
        int height = 500;

        Graphics g = surface.getGraphics();

        FillCells(g, step, width, height);
        DrawGrid(g, step, width, height);
        g.dispose();
        view.repaint();
    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int step = 10;
        int width = 800;
        int height = 500;

        FillCells(g, step, width, height);
        DrawGrid(g, step, width, height);
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
        for (int i = step; i <= width; i += step) {
            g.drawLine(i, 0, i, height + step);
        }

        for (int i = step; i <= height; i += step) {
            g.drawLine(0, i, width + step, i);
        }
    }


    public void fillCell(int x, int y) {
        mLiveCells.add(new Point(x, y));
        repaint();
    }

    private int GetCellNeighbors(Point point)
    {
        int nCount=0;



        return nCount;
    }

}