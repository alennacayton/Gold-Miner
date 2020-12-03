import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLOutput;
import java.util.EventListener;


public class GameMenu extends JFrame implements MouseListener{

    private JPanel menuPanel;

    private JLabel chooseLabel;
    private JLabel goldminerLabel;

    private JButton smartButton;
    private JButton randomButton;


    GameMenu(){

        menuPanel = new JPanel();
        this.add(menuPanel);
        this.setTitle("Gold Miner");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();

        this.setBounds(100, 100, 600, 600);

     //   menuPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setLocationRelativeTo(null);

        this.setIconImage(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("pickaxe.png")));



        setContentPane(menuPanel);
        menuPanel.setLayout(null);




        menuPanel.setBackground(Color.black);
        menuPanel.setFocusable(true);


        goldminerLabel = new JLabel("GOLD MINER");
        goldminerLabel .setFont(new Font("Impact", Font.BOLD, 85));
        goldminerLabel .setForeground(Color.yellow);
        goldminerLabel .setBounds(80, 150, 450, 80);
        menuPanel.add(goldminerLabel);



        chooseLabel = new JLabel("Choose your agent");
        chooseLabel.setFont(new Font("Arial", Font.BOLD, 20));
        chooseLabel.setForeground(Color.white);
        chooseLabel.setBounds(200, 275, 211, 26);
        menuPanel.add(chooseLabel);

        smartButton = new JButton("SMART");
        smartButton.setBounds(125, 350, 150, 30);
        smartButton.setName("smart.jb");
        smartButton.setFont(new Font("Arial", Font.PLAIN, 25));
        menuPanel.add(smartButton, BorderLayout.BEFORE_FIRST_LINE);


        randomButton = new JButton("RANDOM");
        randomButton.setBounds(325, 350, 150, 30);
        randomButton.setName("random.jb");
        randomButton.setFont(new Font("Arial", Font.PLAIN, 25));
        menuPanel.add(randomButton, BorderLayout.EAST);

        this.setVisible(true);
        this.AddListener(this);


    }




    public void AddListener(EventListener e)
    {
        smartButton.addMouseListener((MouseListener) e);
        randomButton.addMouseListener((MouseListener) e);
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        Component i = (Component) e.getSource();
        switch(i.getName())
        {
            case "smart.jb":
                System.out.println("SMART HU");
                this.setVisible(false);
                GameFrame frame = new GameFrame();
                break;
            case "random.jb":
                System.out.println("RANDOM UH");
                break;



        }

    }




    // not needed

    @Override
    public void mouseClicked(MouseEvent e)
    {

    }

    @Override
    public  void mouseEntered(MouseEvent e)
    {

    }


    @Override
    public void mouseExited(MouseEvent e){

    }

    @Override
    public void mouseReleased(MouseEvent e)
    {

    }


}
