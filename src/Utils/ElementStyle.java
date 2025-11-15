package src.Utils;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import src.View;
import java.awt.*;

public class ElementStyle {

    public static void ButtonHoverColorChanger(JButton button,Color color,Color HoverTextColor,Color ExiteColor)
    {
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override

            public void mouseEntered(java.awt.event.MouseEvent evt)
            {
                button.setForeground(HoverTextColor);
                button.setBackground(color.darker());
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt)
            {
                 button.setForeground(ExiteColor);
                button.setBackground(color);
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
                button.setBackground(color.darker());
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt)
            {
                 button.setForeground(View.ButtonHoverTextColor);
                button.setBackground(View.DarkMainColor);
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
                button.setBackground(color.darker());
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt)
            {
                 button.setForeground(View.ButtonHoverTextColor);
                button.setBackground(View.MoreClairance);
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

    public static void SetButtonStyle(JButton button)
    {
        button.setContentAreaFilled(true);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setBackground(View.MoreClairance);
        button.setForeground(View.ButtonHoverTextColor);
        button.setFont(View.BasicTextFont);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        ElementStyle.HoverImportFFlag(button, View.ButtonHoverTextColor);
    }

    public static void SetButtonStyleInsideMiniLightPanel(JButton button)
    {
        button.setContentAreaFilled(true);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setBackground(View.DarkMainColor);
        button.setForeground(View.ButtonHoverTextColor);
        button.setFont(View.ButtonFont);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public static void SetLine(JPanel CurrentPannel,int y)
    {
        JPanel line = new JPanel();

        line.setBounds(22,y,600,2);
        line.setBackground(View.MoreClairance);
        CurrentPannel.add(line);
    }
}
