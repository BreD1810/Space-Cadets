import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame implements ActionListener{

    private JButton radiusButton, offsetButton, loopButton, drawHypercycloid;
    private JPanel panel;
    private JTextField radiusEntry, offsetEntry, loopEntry;
    private Hypercycloid spyrograph = new Hypercycloid();

    public void createGUI()
    {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container window = getContentPane();
        window.setLayout(new FlowLayout());

        panel = new JPanel();
        panel.setPreferredSize(new Dimension(950, 900));
        panel.setBackground(Color.white);
        window.add(panel);

        radiusEntry = new JTextField(5);
        window.add(radiusEntry);

        radiusButton = new JButton("Set Radius");
        window.add(radiusButton);
        radiusButton.addActionListener(this);

        offsetEntry = new JTextField(5);
        window.add(offsetEntry);

        offsetButton = new JButton("Set Offset");
        window.add(offsetButton);
        offsetButton.addActionListener(this);

        loopEntry = new JTextField(5);
        window.add(loopEntry);

        loopButton = new JButton("Set Loop Number");
        window.add(loopButton);
        loopButton.addActionListener(this);

        drawHypercycloid = new JButton("Draw");
        window.add(drawHypercycloid);
        drawHypercycloid.addActionListener(this);
    }

    public void actionPerformed(ActionEvent event)
    {
        Object source = event.getSource();
        if (source == radiusButton)
        {
            if (spyrograph.getOffset() != 0)
            {
                if (spyrograph.getOffset()<= Float.valueOf(radiusEntry.getText()))
                {
                    spyrograph.setRadius(Float.valueOf(radiusEntry.getText()));
                }
                else{
                    JOptionPane.showMessageDialog(null, "Offset cannot be less than the radius");
                    radiusEntry.setText("");
                }
            }
            else
            {
                spyrograph.setRadius(Float.valueOf(radiusEntry.getText()));
            }
        }
        else if (source == offsetButton)
        {
            if (spyrograph.getRadius() != 0)
            {
                if (spyrograph.getRadius() >= Float.valueOf(offsetEntry.getText()))
                {
                    spyrograph.setOffset(Float.valueOf(offsetEntry.getText()));
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Offset cannot be less than the radius");
                    offsetEntry.setText("");
                }
            }
            else
            {
                spyrograph.setOffset(Float.valueOf(offsetEntry.getText()));
            }
        }
        else if (source == loopButton)
        {
            spyrograph.setLoopNumber(Integer.valueOf(loopEntry.getText()));
        }
        else if (source == drawHypercycloid)
        {
            drawHypercycloid();
        }
    }

    public void drawHypercycloid()
    {
        Graphics paper = panel.getGraphics();
        paper.setColor(Color.white);
        paper.fillRect(0, 0, 950, 900);
        paper.setColor(Color.black);
        double originalX, originalY, x, y;
        System.out.println("Radius: " + spyrograph.getRadius() + " Offset: " + spyrograph.getOffset() + " Loops: " + spyrograph.getLoopNumber());
        for(int t = 0; t < spyrograph.getLoopNumber(); t++)
        {
            originalX = (350+spyrograph.getRadius())*Math.cos(t) - (spyrograph.getRadius()+spyrograph.getOffset())*Math.cos((200+spyrograph.getRadius())/spyrograph.getRadius()*t);
            originalX += 475;
            originalY = (350+spyrograph.getRadius())*Math.sin(t) - (spyrograph.getRadius()+spyrograph.getOffset())*Math.sin((200+spyrograph.getRadius())/spyrograph.getRadius()*t);
            originalY += 450;
            x = (350+spyrograph.getRadius())*Math.cos(t+1) - (spyrograph.getRadius()+spyrograph.getOffset())*Math.cos((200+spyrograph.getRadius())/spyrograph.getRadius()*(t+1));
            x += 475;
            y = (350+spyrograph.getRadius())*Math.sin(t+1) - (spyrograph.getRadius()+spyrograph.getOffset())*Math.sin((200+spyrograph.getRadius())/spyrograph.getRadius()*(t+1));
            y += 450;
            paper.drawLine((int)originalX, (int)originalY, (int)x, (int)y);
        }
    }

    public static void main(String[] args)
    {
        Main frame = new Main();
        frame.setSize(1000, 1000);
        frame.createGUI();
        frame.setVisible(true);
    }

}
