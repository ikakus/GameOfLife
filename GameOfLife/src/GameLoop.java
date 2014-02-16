import javax.swing.*;

/**
 * Created by ikakus on 2/9/14.
 */
public class GameLoop implements Runnable {

    public void  GameLoop(JFrame window)
    {

    }

    @Override
    public void run() {
        while(true)
        {
            System.out.println("Kookokoko");

            try
            {
            Thread.sleep(2000);
            }
            catch (InterruptedException inEx)
            {}
        }
    }
}
