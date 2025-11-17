package src;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.StyledEditorKit.BoldAction;

import src.Update.UtilsFinder;
import src.Utils.PathFinder;
import src.Utils.RWF;
import src.Utils.Bundle.Pages.About;
import src.Utils.Bundle.Pages.BasicPannel;
import src.Utils.Bundle.Pages.Behavior;
import src.Utils.Bundle.Pages.Fastflag;
import src.Utils.Bundle.Pages.Popup1;
import src.Utils.Bundle.Pages.Popup1;
import src.Utils.Bundle.Pages.Style;
import src.Utils.Controleur.ActionHandle;
import src.Utils.Bundle.AddButtonSection;


import java.io.File;
import java.io.IOException;
import java.lang.Runtime.Version;
import java.sql.Connection;
import java.util.concurrent.CompletableFuture;
import java.awt.Color;
import java.awt.Font;
import java.io.Console;


public class View{

    public static String CurrentVersion = "v0.0.2";

    //Font && Colors && Style
    public static Color MainColor = Color.decode("#232432");
    public static Color DarkMainColor = Color.decode("#20202D");
    public static Color PanelBorderColor = Color.decode("#222230");
    public static Color MoreClairance = Color.decode("#33334D");
    public static Color ButtonHoverTextColor = Color.decode("#bab4f0");
    public static Font TitleFont = new Font("Arial", Font.BOLD, 30);
    public static Font ButtonFont = new Font("Arial", Font.BOLD, 25);
    public static Font BasicTextFont = new Font("Arial", Font.BOLD, 16);
    public static Font BasicTextFontSized = new Font("Arial", Font.BOLD, 19);
    public static Font SmallButtonText = new Font("Arial", Font.BOLD, 10);
    public static Font SmallTextFont = new Font("Arial",Font.ITALIC,10);
    public static Font SmallSmallTextFont = new Font("Arial",Font.ITALIC,4);
    //Logic && Action && Function Import;
    public static PathFinder ClientSettingPath = new PathFinder();
    public static void WindowBuilder()
    {
        UtilsFinder.incrementCounter();

        JFrame Window = new JFrame("BloxMac");

        if(UtilsFinder.checkVersion(CurrentVersion))
        {
            System.out.println("Up To date");
        }
        else
        {
            System.out.println("Download Latest Version");

            CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(15_000);
                System.exit(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
        });

        }

        Window.setLayout(null);
        Window.setSize(900, 590);
        Window.getContentPane().setBackground(MainColor);
        Window.setResizable(false);
        Window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel SidePannel = new JPanel();
        SidePannel.setBounds(250, 15, 642, 530);
        SidePannel.setBackground(DarkMainColor);
        SidePannel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        SidePannel.setLayout(null);

        BasicPannel.BasicSet(SidePannel);

        JLabel TitleLabel = new JLabel("<html>BloxMac <br><p>"+View.CurrentVersion+"</p></html>");
        TitleLabel.setBounds(60, -10, 150, 90);
        TitleLabel.setForeground(Color.WHITE);
        TitleLabel.setFont(TitleFont);

        JPanel ButtonSection = new JPanel();
        ButtonSection.setBounds(10, 75, 230, 470);
        ButtonSection.setBackground(DarkMainColor);
        ButtonSection.setLayout(null);
        ButtonSection.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        AddButtonSection.ButtonSectionSet(ButtonSection);


        //Component to Logic && Actions

        //Main Page
        AddButtonSection.AboutButton.addActionListener(e -> {
            About.Pannel1SetAbout(SidePannel);
        });

        AddButtonSection.FastFlagButton.addActionListener(e -> {
            Fastflag.Pannel1SetFastFlag(SidePannel);
        });

        AddButtonSection.StyleButton.addActionListener(e -> {
            Style.Pannel1SetStyle(SidePannel);
        });
       
        AddButtonSection.LuncherRoblox.addActionListener(e -> {
            ActionHandle.OpenRoblox(); 
            try
            {
                ActionHandle.Notification("test");
            }
            catch(IOException k)
            {

                k.getStackTrace();
            }
        });
        
        AddButtonSection.BahaviorButton.addActionListener(e -> {
            Behavior.Pannel1SetBehavior(SidePannel);
        });

        //Fastflag Page

        Fastflag.ImportFFlag.addActionListener(e -> {
            System.out.println("[+] Import FFlag Action");
            if(ActionHandle.FFlagImportSection(SidePannel))
            {
                Popup1.AddPopup(SidePannel, "Flags Added",34,Color.green);
            }
            else
            {
                Popup1.AddPopup(SidePannel, "Failed Import",130,Color.RED);
            }
        });

        Fastflag.ImportMods.addActionListener(e -> {
            ActionHandle.LoadMods(SidePannel);

        });
        
        Fastflag.RemouveUserFFlag.addActionListener(e -> {
            ActionHandle.FindAndRemouveSpecificFflag(Fastflag.UserFlags.getText(), Fastflag.UserFlagsValue.getText());
        });
        
        Fastflag.UserAddFlags.addActionListener(e -> {
            if(ActionHandle.AddUserFFlags(Fastflag.UserFlags.getText(), Fastflag.UserFlagsValue.getText()))
            {
                Popup1.AddPopup(SidePannel, "Flags Added Succesfully !", 85, Color.green);
            }
            else
            {
                Popup1.AddPopup(SidePannel, "Nothing commit",130,Color.RED);
            }
        });
        
        Fastflag.ResetMods.addActionListener(e -> {
                //Action Reset Mods todo 
        });

        //Style Page
        Style.ImportFont.addActionListener(e -> {
            ActionHandle.SetFont(SidePannel);
        });

        Style.ImportCursor.addActionListener(e -> {
            ActionHandle.SetCursor(SidePannel);
        });
        

        Window.add(TitleLabel);
        Window.add(SidePannel);
        Window.add(ButtonSection);
        Window.setVisible(true);
    }

    public static void main(String[] args)
    {

        //auto CreateFile if not existe
        File ClientSettingOpen = new File(PathFinder.FindPathClientSetting());
        if(!(ClientSettingOpen.exists()))
        {
              PathFinder.CreateClientSettingFull();
        }
        
        WindowBuilder();
        
        //System.out.print(RWIF.ReadFile(ClientSettingPath));
    }
}