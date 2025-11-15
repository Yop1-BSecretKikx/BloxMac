package src.Utils.Controleur;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.RandomAccess;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.text.StyledEditorKit.BoldAction;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import src.View;
import src.Utils.PathFinder;
import src.Utils.RWF;
import src.Utils.Bundle.Pages.Fastflag;
import src.Utils.Bundle.Pages.Popup1;

import java.awt.datatransfer.StringSelection;
public class ActionHandle {


    //Only Hudge Function
    public static Boolean FindAndRemouveSpecificFflag(String Fflag, String Value)
    {
        try
        {
            String ClientSettingContent = Files.readString(Paths.get(PathFinder.FindPathClientSetting()));
            ClientSettingContent = ClientSettingContent.replaceAll("\""+ Fflag + "\"\\s*:\\s*\"[^\"]*\",?", ""); // regex made by ai
            ClientSettingContent = ClientSettingContent.replaceAll(",\\s*}", "\n}"); // regex made by ai
            Files.writeString(Paths.get(PathFinder.FindPathClientSetting()), ClientSettingContent, StandardOpenOption.TRUNCATE_EXISTING);
            return (true);
        }
        catch(IOException e)
        {
            return (false);
        }
        
    }

    public static Boolean AddUserFFlags(String FFlags,String Value)
    {
        //"FFlagDebugGraphicsDisableDirect3D11": false,

        //4 nov : 2:28 -> Should Add the User FFlag into Client Setting properly;
        String ClientSettingPath = PathFinder.FindPathClientSetting();
        String UserFFlagsToSyntax = "";
        if(!(FFlags.isEmpty() && Value.isEmpty()))
        {
             UserFFlagsToSyntax = "    \"" + FFlags + "\"" + ": " + "\"" +Value + "\"" + "\n";
        }
        else
        {
            return (false);
        }

        File OpenClientSettings = new File(ClientSettingPath);
        int line = 0;
        try
        {
            String ClientSettingContent = Files.readString(Path.of(ClientSettingPath));
            line = ClientSettingContent.split("\r\n|\r|\n").length;
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
            return false;
        }

        //add "," ??

        if(!(FFlags.isEmpty() && Value.isEmpty()))
        {
            try {
                String path = ClientSettingPath;
                RandomAccessFile file = new RandomAccessFile(path, "rw");

                long pos = file.length() - 1;

                while (pos >= 0) {
                    file.seek(pos);
                    int b = file.read();
                    if ((char) b == '}') {
                        long insertPos = pos - 1;
                        while (insertPos >= 0)
                        {
                            file.seek(insertPos);
                            int bb = file.read();
                            char ch2 = (char) bb;
                            if (!Character.isWhitespace(ch2) && ch2 != '{') break;
                                insertPos--;
                        }
                        file.seek(insertPos + 1);
                        byte[] rest = new byte[(int)(file.length() - insertPos - 1)];
                        file.readFully(rest);

                    
                        file.seek(insertPos + 1);
                        file.write(",".getBytes("UTF-8"));
                        file.write(rest);
                        break;
            }
            pos--;
            }

            file.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
        }

        try
        {
            if(!(FFlags.isEmpty() && Value.isEmpty()))
            {
                System.out.println(line);
                RandomAccessFile ClientSettings = new RandomAccessFile(ClientSettingPath, "rw");
                long pos = OpenClientSettings.length() - 2;
                ClientSettings.seek(pos);
                ClientSettings.writeBytes(UserFFlagsToSyntax);
                ClientSettings.close();

                FileWriter CleaningClientSetting = new FileWriter(ClientSettingPath,true);
                CleaningClientSetting.append("\n}");

                CleaningClientSetting.close();
            }
        }
        catch(IOException e)
        {
                System.out.println(e.getMessage());
        }


        //cleaning json

        try
        {
            FileReader Reader = new FileReader(ClientSettingPath);
            int firstchar = Reader.read();
            if(firstchar != -1)
            {
                if((char)(firstchar) == ',')
                {
                    RandomAccessFile Clientsettings = new RandomAccessFile(ClientSettingPath, "rw");
                    Clientsettings.seek(0);
                    Clientsettings.write(' ');

                    Clientsettings.close();
                }
            }
            Reader.close();
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
        }

        System.out.println("[+] " + ClientSettingPath);
        System.out.println(UserFFlagsToSyntax);

        return (true);
    }

    public static Boolean ExportFFlag()
    {
        String ClientSettingContent = "";
        try
        {
            FileReader reader = new FileReader(PathFinder.FindPathClientSetting());
            
            int c;
            while((c = reader.read()) != -1)
            {
                ClientSettingContent += (char)c;
            }

            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            StringSelection selection = new StringSelection(ClientSettingContent);
            clipboard.setContents(selection, null);

            reader.close();
            return true;
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
            return false;
        }

    }

    public static Boolean OpenRoblox()
    {
        try
        {
            String[] commande = {"open", "-a","Roblox"};
            Runtime.getRuntime().exec(commande);
            return(true);
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
            return (false);
        }
    }

    public static Boolean FFlagImportSection(JPanel CurrentPanel)
    {
        JFileChooser FileChooser = new JFileChooser();
        FileChooser.setDialogTitle("BloxMac File Finder");

        int resultat = FileChooser.showOpenDialog(CurrentPanel);
        if(resultat == JFileChooser.CANCEL_OPTION)
        {
            return (false);
        }
        if (resultat == JFileChooser.APPROVE_OPTION) {
            File file = FileChooser.getSelectedFile();
            Fastflag.ChoseFileDescriptor[0] = file.getName();
            Fastflag.ChoseFileDescriptor[1] = file.getPath();
            Fastflag.ChoseFileDescriptor[2] = file.toString();

             //Clear ClientSetting And Replace Content By Chose File Content
            RWF rwf = new RWF();
            Fastflag.ChoseFileDescriptor[3] = rwf.ReadFile(Fastflag.ChoseFileDescriptor[2]);
            rwf.WriteFile(PathFinder.FindPathClientSetting(), Fastflag.ChoseFileDescriptor[3],false);
            return (true);
        }
        else if(resultat == JFileChooser.CANCEL_OPTION)
        {
            return (false);
        }

        return (true);
    }

    public static Boolean ImportModsIntoRoblox(JPanel CurrentPanel)
    {
        //finish 8 nov 4h30
        JFileChooser FileChooser = new JFileChooser();
        FileChooser.setDialogTitle("BloxMac File Finder");
        FileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int resultat = FileChooser.showOpenDialog(CurrentPanel);

        if(resultat == JFileChooser.CANCEL_OPTION)
        {
            FileChooser.cancelSelection();
            return (false);
        }
        else if(resultat == JFileChooser.APPROVE_OPTION)
        {
            File ModsFile = FileChooser.getSelectedFile();
            
            //todo End Mods Loader
            
        }
        return (true);
    }

    public static Boolean CreateNewInstance()
    {
        try
        {
            ProcessBuilder procBuilder = new ProcessBuilder("open", PathFinder.RobloxPath() + "/Contents/MacOS/RobloxPlayer");
            Process pr = procBuilder.start();
        }catch(IOException e)
        {
            return (false);
        }
        
        return (true);
    }

    public static Boolean KillAllInstance()
    {
        return (true);
    }

    public static Boolean SetFont(JPanel CurrentPanel)
    {

        String FontsExpectedName = "CustomFont.ttf";
        Path fontPath = Paths.get(PathFinder.RobloxPath() + "Contents/Resources/content/fonts/");
        String fontPathString = PathFinder.RobloxPath() + "Contents/Resources/content/fonts/";
        JFileChooser OpenFile = new JFileChooser();
        OpenFile.setDialogTitle("Import Font !");
        
        int resultat = OpenFile.showOpenDialog(CurrentPanel);

        if(resultat == OpenFile.CANCEL_OPTION)
        {
            return (false);
        }

        if(resultat == OpenFile.APPROVE_OPTION)
        {
            File FontFile = OpenFile.getSelectedFile();
            Path targetFile = fontPath.resolve(FontFile.getName());

            try
            {
                Files.copy(FontFile.toPath(), targetFile,StandardCopyOption.REPLACE_EXISTING);
                String SelectedFileName = FontFile.getName();
                File AddedFontFile = new File(fontPathString + SelectedFileName);
                Files.move(Paths.get(fontPathString + SelectedFileName), Paths.get(fontPathString + FontsExpectedName), StandardCopyOption.REPLACE_EXISTING);

                String[] ReplaceJsonFamilly = {
                    "{\"name\":\"Zekton\",\"faces\":[{\"name\":\"Regular\",\"weight\":400,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"}]}",
                    "{\"name\":\"Ubuntu\",\"faces\":[{\"name\":\"Light\",\"weight\":300,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Light Italic\",\"weight\":300,\"style\":\"italic\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Regular\",\"weight\":400,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Italic\",\"weight\":400,\"style\":\"italic\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Medium\",\"weight\":500,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Medium Italic\",\"weight\":500,\"style\":\"italic\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Bold\",\"weight\":700,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Bold Italic\",\"weight\":700,\"style\":\"italic\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"}]}",
                    "{\"name\":\"Titillium Web\",\"faces\":[{\"name\":\"Extra Light\",\"weight\":200,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Extra Light Italic\",\"weight\":200,\"style\":\"italic\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Light\",\"weight\":300,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Light Italic\",\"weight\":300,\"style\":\"italic\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Regular\",\"weight\":400,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Italic\",\"weight\":400,\"style\":\"italic\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Semi Bold\",\"weight\":600,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Semi Bold Italic\",\"weight\":600,\"style\":\"italic\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Bold\",\"weight\":700,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Bold Italic\",\"weight\":700,\"style\":\"italic\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Black\",\"weight\":900,\"style\":\"normal\",\"assetId\":\"rbxassetid://8075257018\"}]}",
                    "{\"name\":\"Special Elite\",\"faces\":[{\"name\":\"Regular\",\"weight\":400,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"}]}",
                    "{\"name\":\"Source Sans Pro\",\"faces\":[{\"name\":\"Extra Light\",\"weight\":200,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Extra Light Italic\",\"weight\":200,\"style\":\"italic\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Light\",\"weight\":300,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Light Italic\",\"weight\":300,\"style\":\"italic\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Regular\",\"weight\":400,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Italic\",\"weight\":400,\"style\":\"italic\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Semi Bold\",\"weight\":600,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Semi Bold Italic\",\"weight\":600,\"style\":\"italic\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Bold\",\"weight\":700,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Bold Italic\",\"weight\":700,\"style\":\"italic\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Black\",\"weight\":900,\"style\":\"normal\",\"assetId\":\"rbxassetid://8043677567\"},{\"name\":\"Black Italic\",\"weight\":900,\"style\":\"italic\",\"assetId\":\"rbxassetid://8075250899\"}]}",
                    "{\"name\":\"Sarpanch\",\"faces\":[{\"name\":\"Regular\",\"weight\":400,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Medium\",\"weight\":500,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Semi Bold\",\"weight\":600,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Bold\",\"weight\":700,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Extra Bold\",\"weight\":800,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Black\",\"weight\":900,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"}]}",
                    "{\"name\":\"Roman Antique\",\"faces\":[{\"name\":\"Regular\",\"weight\":400,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"}]}",
                    "{\"name\":\"Roboto Mono\",\"faces\":[{\"name\":\"Thin\",\"weight\":100,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Thin Italic\",\"weight\":100,\"style\":\"italic\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Extra Light\",\"weight\":200,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Extra Light Italic\",\"weight\":200,\"style\":\"italic\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Light\",\"weight\":300,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Light Italic\",\"weight\":300,\"style\":\"italic\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Regular\",\"weight\":400,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Italic\",\"weight\":400,\"style\":\"italic\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Medium\",\"weight\":500,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Medium Italic\",\"weight\":500,\"style\":\"italic\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Semi Bold\",\"weight\":600,\"style\":\"normal\",\"assetId\":\"rbxassetid://8075241600\"},{\"name\":\"Semi Bold Italic\",\"weight\":600,\"style\":\"italic\",\"assetId\":\"rbxassetid://8075242504\"},{\"name\":\"Bold\",\"weight\":700,\"style\":\"normal\",\"assetId\":\"rbxassetid://8075229982\"},{\"name\":\"Bold Italic\",\"weight\":700,\"style\":\"italic\",\"assetId\":\"rbxassetid://8075231017\"}]}",
                    "{\"name\":\"Roboto Condensed\",\"faces\":[{\"name\":\"Light\",\"weight\":300,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Light Italic\",\"weight\":300,\"style\":\"italic\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Regular\",\"weight\":400,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Italic\",\"weight\":400,\"style\":\"italic\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Bold\",\"weight\":700,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Bold Italic\",\"weight\":700,\"style\":\"italic\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"}]}",
                    "{\"name\":\"Roboto\",\"faces\":[{\"name\":\"Thin\",\"weight\":100,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Thin Italic\",\"weight\":100,\"style\":\"italic\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Light\",\"weight\":300,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Light Italic\",\"weight\":300,\"style\":\"italic\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Regular\",\"weight\":400,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Italic\",\"weight\":400,\"style\":\"italic\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Medium\",\"weight\":500,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Medium Italic\",\"weight\":500,\"style\":\"italic\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Bold\",\"weight\":700,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Bold Italic\",\"weight\":700,\"style\":\"italic\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Black\",\"weight\":900,\"style\":\"normal\",\"assetId\":\"rbxassetid://8075213353\"},{\"name\":\"Black Italic\",\"weight\":900,\"style\":\"italic\",\"assetId\":\"rbxassetid://8075214707\"}]}",
                    "{\"name\":\"Press Start 2P\",\"faces\":[{\"name\":\"Regular\",\"weight\":400,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"}]}",
                    "{\"name\":\"Permanent Marker\",\"faces\":[{\"name\":\"Regular\",\"weight\":400,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"}]}",
                    "{\"name\":\"Patrick Hand\",\"faces\":[{\"name\":\"Regular\",\"weight\":400,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"}]}",
                    "{\"name\":\"Oswald\",\"faces\":[{\"name\":\"Extra Light\",\"weight\":200,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Light\",\"weight\":300,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Regular\",\"weight\":400,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Medium\",\"weight\":500,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Semi Bold\",\"weight\":600,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Bold\",\"weight\":700,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"}]}",
                    "{\"name\":\"Nunito\",\"faces\":[{\"name\":\"Extra Light\",\"weight\":200,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Extra Light Italic\",\"weight\":200,\"style\":\"italic\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Light\",\"weight\":300,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Light Italic\",\"weight\":300,\"style\":\"italic\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Regular\",\"weight\":400,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Italic\",\"weight\":400,\"style\":\"italic\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Semi Bold\",\"weight\":600,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Semi Bold Italic\",\"weight\":600,\"style\":\"italic\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Bold\",\"weight\":700,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Bold Italic\",\"weight\":700,\"style\":\"italic\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Extra Bold\",\"weight\":800,\"style\":\"normal\",\"assetId\":\"rbxassetid://8075191639\"},{\"name\":\"Extra Bold Italic\",\"weight\":800,\"style\":\"italic\",\"assetId\":\"rbxassetid://8075193085\"},{\"name\":\"Black\",\"weight\":900,\"style\":\"normal\",\"assetId\":\"rbxassetid://8075186391\"},{\"name\":\"Black Italic\",\"weight\":900,\"style\":\"italic\",\"assetId\":\"rbxassetid://8075188191\"}]}",
                    "{\"name\":\"Montserrat\",\"faces\":[{\"name\":\"Thin\",\"weight\":100,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Thin Italic\",\"weight\":100,\"style\":\"italic\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Extra Light\",\"weight\":200,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Extra Light Italic\",\"weight\":200,\"style\":\"italic\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Light\",\"weight\":300,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Light Italic\",\"weight\":300,\"style\":\"italic\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Regular\",\"weight\":400,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Italic\",\"weight\":400,\"style\":\"italic\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Medium\",\"weight\":500,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Medium Italic\",\"weight\":500,\"style\":\"italic\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Semi Bold\",\"weight\":600,\"style\":\"normal\",\"assetId\":\"rbxassetid://11598119872\"},{\"name\":\"Semi Bold Italic\",\"weight\":600,\"style\":\"italic\",\"assetId\":\"rbxassetid://11598117539\"},{\"name\":\"Bold\",\"weight\":700,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/Montserrat-Bold.ttf\"},{\"name\":\"Bold Italic\",\"weight\":700,\"style\":\"italic\",\"assetId\":\"rbxassetid://11598117769\"},{\"name\":\"Extra Bold\",\"weight\":800,\"style\":\"normal\",\"assetId\":\"rbxassetid://11598118540\"},{\"name\":\"Extra Bold Italic\",\"weight\":800,\"style\":\"italic\",\"assetId\":\"rbxassetid://11598118921\"},{\"name\":\"Black\",\"weight\":900,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/Montserrat-Black.ttf\"},{\"name\":\"Black Italic\",\"weight\":900,\"style\":\"italic\",\"assetId\":\"rbxassetid://11598119154\"}]}",
                    "{\"name\":\"Michroma\",\"faces\":[{\"name\":\"Regular\",\"weight\":400,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"}]}",
                    "{\"name\":\"Merriweather\",\"faces\":[{\"name\":\"Light\",\"weight\":300,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Light Italic\",\"weight\":300,\"style\":\"italic\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Regular\",\"weight\":400,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Italic\",\"weight\":400,\"style\":\"italic\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Bold\",\"weight\":700,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Bold Italic\",\"weight\":700,\"style\":\"italic\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Black\",\"weight\":900,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Black Italic\",\"weight\":900,\"style\":\"italic\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"}]}",
                    "{\"name\":\"Luckiest Guy\",\"faces\":[{\"name\":\"Regular\",\"weight\":400,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"}]}",
                    "{\"name\":\"Arimo (Legacy)\",\"faces\":[{\"name\":\"Regular\",\"weight\":400,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Bold\",\"weight\":700,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"}]}",
                    "{\"name\":\"Arimo (Legacy)\",\"faces\":[{\"name\":\"Regular\",\"weight\":400,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Bold\",\"weight\":700,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"}]}",
                    "{\"name\":\"Arimo (Legacy)\",\"faces\":[{\"name\":\"Regular\",\"weight\":400,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Bold\",\"weight\":700,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"}]}",
                    "{\"name\":\"Kalam\",\"faces\":[{\"name\":\"Light\",\"weight\":300,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Regular\",\"weight\":400,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Bold\",\"weight\":700,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"}]}",
                    "{\"name\":\"Jura\",\"faces\":[{\"name\":\"Light\",\"weight\":300,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Regular\",\"weight\":400,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Medium\",\"weight\":500,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Semi Bold\",\"weight\":600,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Bold\",\"weight\":700,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"}]}",
                    "{\"name\":\"Josefin Sans\",\"faces\":[{\"name\":\"Thin\",\"weight\":100,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Thin Italic\",\"weight\":100,\"style\":\"italic\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Extra Light\",\"weight\":200,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Extra Light Italic\",\"weight\":200,\"style\":\"italic\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Light\",\"weight\":300,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Light Italic\",\"weight\":300,\"style\":\"italic\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Regular\",\"weight\":400,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Italic\",\"weight\":400,\"style\":\"italic\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Medium\",\"weight\":500,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Medium Italic\",\"weight\":500,\"style\":\"italic\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Semi Bold\",\"weight\":600,\"style\":\"normal\",\"assetId\":\"rbxassetid://8075166660\"},{\"name\":\"Semi Bold Italic\",\"weight\":600,\"style\":\"italic\",\"assetId\":\"rbxassetid://8075167921\"},{\"name\":\"Bold\",\"weight\":700,\"style\":\"normal\",\"assetId\":\"rbxassetid://8075153954\"},{\"name\":\"Bold Italic\",\"weight\":700,\"style\":\"italic\",\"assetId\":\"rbxassetid://8075155824\"}]}",
                    "{\"name\":\"Indie Flower\",\"faces\":[{\"name\":\"Regular\",\"weight\":400,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"}]}",
                    "{\"name\":\"Inconsolata\",\"faces\":[{\"name\":\"Extra Light\",\"weight\":200,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Light\",\"weight\":300,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Regular\",\"weight\":400,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Medium\",\"weight\":500,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Semi Bold\",\"weight\":600,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Bold\",\"weight\":700,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Extra Bold\",\"weight\":800,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Black\",\"weight\":900,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"}]}",
                    "{\"name\":\"Highway Gothic\",\"faces\":[{\"name\":\"Regular\",\"weight\":400,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"}]}",
                    "{\"name\":\"Guru\",\"faces\":[{\"name\":\"Regular\",\"weight\":400,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"}]}",
                    "{\"name\":\"Grenze Gotisch\",\"faces\":[{\"name\":\"Thin\",\"weight\":100,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Extra Light\",\"weight\":200,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Light\",\"weight\":300,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Regular\",\"weight\":400,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Medium\",\"weight\":500,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Semi Bold\",\"weight\":600,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Bold\",\"weight\":700,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Extra Bold\",\"weight\":800,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Black\",\"weight\":900,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"}]}",
                    "{\"name\":\"Fredoka One\",\"faces\":[{\"name\":\"Regular\",\"weight\":400,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"}]}",
                    "{\"name\":\"Fondamento\",\"faces\":[{\"name\":\"Regular\",\"weight\":400,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Italic\",\"weight\":400,\"style\":\"italic\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"}]}",
                    "{\"name\":\"Denk One\",\"faces\":[{\"name\":\"Regular\",\"weight\":400,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"}]}",
                    "{\"name\":\"Creepster\",\"faces\":[{\"name\":\"Regular\",\"weight\":400,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"}]}",
                    "{\"name\":\"Comic Neue Angular\",\"faces\":[{\"name\":\"Bold\",\"weight\":400,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"}]}",
                    "{\"name\":\"Builder Sans\",\"faces\":[{\"name\":\"Thin\",\"weight\":100,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Light\",\"weight\":300,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Regular\",\"weight\":400,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Medium\",\"weight\":500,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Semi Bold\",\"weight\":600,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Bold\",\"weight\":700,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Extra Bold\",\"weight\":800,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"}]}",
                    "{\"name\":\"Builder Mono\",\"faces\":[{\"name\":\"Light\",\"weight\":300,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Regular\",\"weight\":400,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Bold\",\"weight\":700,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"}]}",
                    "{\"name\":\"Builder Extended\",\"faces\":[{\"name\":\"Light\",\"weight\":300,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Regular\",\"weight\":400,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Semi Bold\",\"weight\":600,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Bold\",\"weight\":700,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Extra Bold\",\"weight\":800,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"}]}",
                    "{\"name\":\"Bangers\",\"faces\":[{\"name\":\"Regular\",\"weight\":400,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"}]}",
                    "{\"name\":\"Balthazar\",\"faces\":[{\"name\":\"Regular\",\"weight\":400,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"}]}",
                    "{\"name\":\"Arimo\",\"faces\":[{\"name\":\"Regular\",\"weight\":400,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Italic\",\"weight\":400,\"style\":\"italic\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Medium\",\"weight\":500,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Medium Italic\",\"weight\":500,\"style\":\"italic\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Semi Bold\",\"weight\":600,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Semi Bold Italic\",\"weight\":600,\"style\":\"italic\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Bold\",\"weight\":700,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Bold Italic\",\"weight\":700,\"style\":\"italic\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"}]}",
                    "{\"name\":\"Amatic SC\",\"faces\":[{\"name\":\"Regular\",\"weight\":400,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"},{\"name\":\"Bold\",\"weight\":700,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"}]}",
                    "{\"name\":\"Accanthis ADF Std\",\"faces\":[{\"name\":\"Regular\",\"weight\":400,\"style\":\"normal\",\"assetId\":\"rbxasset://fonts/CustomFont.ttf\"}]}",
                };
            
                File OpenFamilly = new File(fontPathString + "families");
                String[] files = OpenFamilly.list();
                int i = 0;
                for(String filename : files)
                {
                    File openttf = new File(fontPathString + "families/" + filename);
                    try
                    {
                        FileWriter writer = new FileWriter(openttf);
                        writer.write(ReplaceJsonFamilly[i]);
                        writer.close();
                    }
                    catch(IOException e)
                    {
                        e.getStackTrace();
                    }
                    i++;
                }
            
            }
            catch(IOException e)
            {
                e.getStackTrace();
            }
        }
        return (true);
    }

    public static void Notification(String Message) throws IOException
    {
        
    }
}
