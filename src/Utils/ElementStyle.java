package src.Utils;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.text.StyledEditorKit.BoldAction;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import src.View;
import java.awt.*;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.Timer;
public class ElementStyle {

    public static Boolean OnButton = false;


    public static int Px = 50;
    public static int LineYp = 80;
    public static int yP = 85;
    public static int yB = 90;
    public static int BYp = 84;

    public static void SetSectionChooser(JPanel CurrentPanel,JComponent button,String SectionContent,Boolean DoLine,Boolean DoInIncPos,Boolean ForceStartFrom,int y,int y1,int RaduW,int RaduH)
    {
        
        if(ForceStartFrom == true)
        {
            yP = y;
            yB = y1;
        }

        if(DoInIncPos == true)
        {
            if(ForceStartFrom == true)
            {
                yP = y;
                yB = y1;
                LineYp = 175;
            }
            else
            {
                yP = 85;
                yB = 90;
                LineYp = 80;
            }
            Px = 50;
            
            BYp = 84;
        }

        JLabel SectionC = new JLabel(SectionContent);
        SectionC.setBounds(20, yP, 490, 50);
        button.setBounds(440, yB, 180, 39);

        ElementStyle.BasicTextFont(SectionC);
        if(button instanceof JButton)
        {
            ElementStyle.SetButtonStyle((JButton)button,RaduW,RaduH);
        }
        if(button instanceof JCheckBox)
        {
            //Style for checkbox
            ElementStyle.SetCheckBoxStyle((JCheckBox)button);
        }
        if(button instanceof JSlider)
        {
            //style slider
        }
        
        if(DoLine == true)
        {
            ElementStyle.SetLine(CurrentPanel, LineYp);
        }

        Px += 34;
        LineYp += 60;
        BYp += 61;
        yP += 60;
        yB += 60;
        CurrentPanel.add(SectionC);
        CurrentPanel.add(button);
    }

    //It was ai idea i did on from scratch my self but it was laggy
    public static void HoverColorAnimation(JComponent button, Color from, Color to, int delay, float durationMs)
    {

        if(button instanceof JButton)
        {
            Timer old = (Timer) button.getClientProperty("hoverTimer");
        if (old != null && old.isRunning()) old.stop();

        long start = System.currentTimeMillis();
        Timer timer = new Timer(delay, null);

        timer.addActionListener(e ->
        {
            float t = (System.currentTimeMillis() - start) / durationMs;
            if (t > 1f)
            {
                t = 1f;
                timer.stop();
            }

            int r = (int)(from.getRed()   + t * (to.getRed()   - from.getRed()));
            int g = (int)(from.getGreen() + t * (to.getGreen() - from.getGreen()));
            int b = (int)(from.getBlue()  + t * (to.getBlue()  - from.getBlue()));

            button.setBackground(new Color(r, g, b));
        });

        button.putClientProperty("hoverTimer", timer);
        timer.start();
        }
        if(button instanceof JMenuItem)
        {
            Timer old = (Timer) button.getClientProperty("hoverTimer");
        if (old != null && old.isRunning()) old.stop();

        long start = System.currentTimeMillis();
        Timer timer = new Timer(delay, null);

        timer.addActionListener(e ->
        {
            float t = (System.currentTimeMillis() - start) / durationMs;
            if (t > 1f)
            {
                t = 1f;
                timer.stop();
            }

            int r = (int)(from.getRed()   + t * (to.getRed()   - from.getRed()));
            int g = (int)(from.getGreen() + t * (to.getGreen() - from.getGreen()));
            int b = (int)(from.getBlue()  + t * (to.getBlue()  - from.getBlue()));

            button.setBackground(new Color(r, g, b));
        });

        button.putClientProperty("hoverTimer", timer);
        timer.start();
        }
}

    public static void ButtonHoverColorChanger(JButton button,Color color,Color HoverTextColor,Color ExiteColor)
    {
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override

            public void mouseEntered(java.awt.event.MouseEvent evt)
            {
                button.setForeground(HoverTextColor);
                HoverColorAnimation(button, color, color.darker(),2,400f);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt)
            {
                button.setForeground(ExiteColor);
                HoverColorAnimation(button, color.darker(), color,1,400f);
            
            }
        });
    }

    public static void HoverAddUserflag(JButton button,Color color)
    {
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt)
            {
                button.setForeground(View.ButtonHoverTextColor);
                HoverColorAnimation(button, color, color.darker(), 2, 400f);
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt)
            {
                button.setForeground(View.ButtonHoverTextColor);
                HoverColorAnimation(button, color.darker(), View.DarkMainColor, 2, 200f);
                //button.setBackground(View.DarkMainColor);
            }

        });
    }

    public static void PageTitleSet(JLabel PageTitle,int y)
    {
        PageTitle.setBounds(20,y,600,100);
        PageTitle.setForeground(View.ButtonHoverTextColor);
        PageTitle.setFont(View.TitleFont);
    }

    public static void HoverImportFFlag(JButton button,Color color)
    {
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt)
            {
                button.setForeground(View.ButtonHoverTextColor);
                HoverColorAnimation(button, color, color.darker(), 2, 400f);
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt)
            {
                button.setForeground(View.ButtonHoverTextColor);
                HoverColorAnimation(button, color.darker(), View.MoreClairance, 2, 400f);
            }

        });
    }

    public static void BasicTextFont(JLabel label)
    {
        label.setForeground(Color.WHITE);
        label.setFont(View.BasicTextFont);
    }

    public static void BasicTextFontSized(JLabel label)
    {
        label.setForeground(Color.WHITE);
        label.setFont(View.BasicTextFontSized);    
    }

    public static void SetButtonStyle(JButton button,int RadiusW,int RadiusH)
    {
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setBackground(View.MoreClairance);
        button.setForeground(View.ButtonHoverTextColor);
        button.setFont(View.BasicTextFont);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.setBorder(new LineBorder(Color.WHITE,30,true));


        button.setUI(new javax.swing.plaf.basic.BasicButtonUI()
        {
            public void paint(Graphics g, JComponent c)
            {
                g.setColor(c.getBackground());
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), RadiusW, RadiusH);
                super.paint(g, c);
            }
        });


        ElementStyle.HoverImportFFlag(button, View.ButtonHoverTextColor);
    }

    public static void SetButtonStyleInsideMiniLightPanel(JButton button)
    {
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setBackground(View.DarkMainColor);
        button.setForeground(View.ButtonHoverTextColor);
        button.setFont(View.ButtonFont);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));


        button.setUI(new javax.swing.plaf.basic.BasicButtonUI()
        {
            public void paint(Graphics g, JComponent c)
            {
                g.setColor(c.getBackground());
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 30, 30);
                super.paint(g, c);
            }
        });

    }

    public static void SetLine(JPanel CurrentPannel,int y)
    {
        JPanel line = new JPanel();

        line.setBounds(22,y,600,2);
        line.setBackground(View.MoreClairance);
        CurrentPannel.add(line);
    }

    public static void SetSectionStyle(JLabel label)
    {
        label.setFont(View.SectionFont);
        label.setForeground(Color.WHITE);
    }

    public static void SetCheckBoxStyle(JComponent checkbox)
    {
        checkbox.setForeground(View.ButtonHoverTextColor);
        checkbox.setFont(View.BasicTextFont);
    }

    public static void SetPlaceHolder(JTextField field,String Placeholder)
    {
        field.setText(Placeholder);
        field.setForeground(Color.GRAY);
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(Placeholder)) {
                    field.setText("");
                    field.setForeground(View.ButtonHoverTextColor);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(Placeholder);
                    field.setForeground(Color.GRAY);
                }
            }
        });
    }
}

