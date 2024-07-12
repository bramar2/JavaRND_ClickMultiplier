package bramar;

import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main extends JPanel {
    private static Font font;
    private JButton jcomp1;
    private JButton jcomp2;
    private JLabel jcomp3;
    private JTextField jcomp4;
    private JLabel jcomp5;
    private JLabel jcomp6;
    private JLabel jcomp7;
    private JLabel jcomp8;
    private JLabel jcomp9;
    private JLabel jcomp10;
    private JLabel jcomp11;
    private boolean on = false;
    private boolean f3pressed = false;
    private Robot robot;
    private int multiplier;
    static {
        font = new JLabel().getFont();
        font = new Font("Arial", Font.BOLD, font.getSize() + 3);
    }
    {
        robot = new Robot();
    }
    public void onF3() {
        if(on) {
            multiplier = 1;
            on = false;
            setStatus(Color.red);
            return;
        }
        multiplier = Integer.parseInt(jcomp4.getText());
        int i = Integer.parseInt(jcomp4.getText());
        if(i <= 1) return;
        if(i > 50) return;
        on = true;
        setStatus(Color.green);
    }
    public Main() throws Exception {
        GlobalScreen.addNativeMouseListener(new NativeMouseListener() {
            @Override
            public void nativeMouseClicked(NativeMouseEvent nativeMouseEvent) {
                try {
                    if(nativeMouseEvent.getButton() == NativeMouseEvent.BUTTON1) {
                        if(on) {
                            GlobalScreen.unregisterNativeHook();
                            on = false;
                            for(int i = 1; i < multiplier; i++) {
                                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                                robot.delay(2);
                                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                                Thread.sleep(1);
                            }
                            GlobalScreen.registerNativeHook();
                            on = true;
                        }
                    }else if(nativeMouseEvent.getButton() == NativeMouseEvent.BUTTON2) {
                        if(on) {
                            GlobalScreen.unregisterNativeHook();
                            on = false;
                            for(int i = 1; i < multiplier; i++) {
                                robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
                                robot.delay(2);
                                robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
                            }
                            GlobalScreen.registerNativeHook();
                            on = true;
                        }
                    }
                }catch(Exception ignored) {}
            }

            @Override
            public void nativeMousePressed(NativeMouseEvent nativeMouseEvent) {
                // Unused
            }

            @Override
            public void nativeMouseReleased(NativeMouseEvent nativeMouseEvent) {
                // Unused
            }
        });
        GlobalScreen.addNativeKeyListener(new NativeKeyListener() {
            @Override
            public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {
                // Unused
            }

            @Override
            public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
                if(nativeKeyEvent.getKeyCode() == NativeKeyEvent.VC_F9) {
                    if(f3pressed) return;
                    f3pressed = true;
                    Main.this.onF3();
                }
            }

            @Override
            public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
                if(nativeKeyEvent.getKeyCode() == NativeKeyEvent.VC_F9) {
                    if(!f3pressed) return;
                    f3pressed = false;
                }
            }
        });
        //construct components
        jcomp1 = new JButton ("Toggle");
        jcomp1.setFont(font);
        jcomp2 = new JButton ("");
        jcomp3 = new JLabel ("Status:");
        jcomp3.setFont(font);
        jcomp4 = new JTextField (5);
        jcomp5 = new JLabel ("Multiplier");
        jcomp4.setFont(font);
        jcomp5.setFont(font);
        jcomp6 = new JLabel ("Will not turn on if:");
        jcomp7 = new JLabel ("- The multiplier is below or is equal to 1");
        jcomp8 = new JLabel ("- The multiplier is higher than 50");
        jcomp9 = new JLabel ("- The input is not a number");
        jcomp10 = new JLabel ("- An unexpected error occured");
        jcomp11 = new JLabel ("F9 to toggle");
        jcomp6.setFont(font);
        jcomp7.setFont(font);
        jcomp8.setFont(font);
        jcomp9.setFont(font);
        jcomp10.setFont(font);
        jcomp11.setFont(font);

        //adjust size and set layout
        setPreferredSize (new Dimension (667, 366));
        setLayout (null);

        //add components
        add (jcomp1);
        add (jcomp2);
        add (jcomp3);
        add (jcomp4);
        add (jcomp5);
        add (jcomp6);
        add (jcomp7);
        add (jcomp8);
        add (jcomp9);
        add (jcomp10);
        add (jcomp11);

        //set component bounds (only needed by Absolute Positioning)
        jcomp1.setBounds (240, 245, 130, 45);
        jcomp2.setBounds (320, 310, 50, 40);
        jcomp3.setBounds (260, 320, 100, 25);
        jcomp4.setBounds (510, 90, 100, 25);
        jcomp5.setBounds (530, 60, 100, 25);
        jcomp6.setBounds (15, 45, 345, 30);
        jcomp7.setBounds (15, 75, 345, 15);
        jcomp8.setBounds (15, 95, 345, 25);
        jcomp9.setBounds (15, 120, 345, 25);
        jcomp10.setBounds (15, 145, 345, 20);
        jcomp11.setBounds (25, 230, 160, 30);
        jcomp2.setBackground(Color.red);

        jcomp1.addActionListener(e -> {
            try {
                onF3();
            }catch(Exception ignored) {}
        });
    }
    private void setStatus(Color color) {
        jcomp2.setBackground(color);
    }

    public static void main(String[] args) throws Exception {
        GlobalScreen.registerNativeHook();
        LogManager.getLogManager().reset();
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        JFrame frame = new JFrame("Click Multiplier");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new Main());
        frame.pack();
        frame.setVisible(true);
    }
}
