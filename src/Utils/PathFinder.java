package src.Utils;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PathFinder {

    //Auto Create ClientSetting if Not Find
    public static void CreateClientSettingFull()
    {
        try
        {
            //CreateClient Folder
            String ClientFolder = "/Applications/Roblox.app/Contents/MacOS/ClientSettings/";
            File SetClientFolder = new File(ClientFolder);
            SetClientFolder.mkdirs();
            String ClientSettingFile = "/Applications/Roblox.app/Contents/MacOS/ClientSettings/ClientAppSettings.json";
            File ClientSettings = new File(ClientSettingFile);
            ClientSettings.createNewFile();

            FileWriter ClientSettingsWrite = new FileWriter(PathFinder.FindPathClientSetting(),false);
            ClientSettingsWrite.write("{\n\n}");
            ClientSettingsWrite.close();
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static String RobloxPath()
    {
        return "/Applications/Roblox.app/";
    }

    public static String FindPathClientSetting()
    {
        //eww but soon auto find it !!!!
        return "/Applications/Roblox.app/Contents/MacOS/ClientSettings/ClientAppSettings.json";
    }

    public String FindPathRobloxFont()
    {
        return "-";
    }
}
