import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class GameFrame extends JFrame {

    GameFrame(int agent) {

        this.add(new GamePanel(agent));

        this.setTitle("Gold Miner");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);


        this.setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("pickaxe.png")));

    }
}
