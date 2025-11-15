package src.Utils;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class RWF {
    public String HomeName = System.getProperty("user.home");


    public Boolean IsClientSettingsCreated(String Path)
    {
        File openFile = new File(Path);

        if(!(openFile.isFile()))
        {
            System.out.println("File ClientSettings.json Do not existe");
            return (boolean)(false);
        }
        return (boolean)(true);
    }
    
    //WriteFile With Json Formater
    public Boolean WriteFile(String Path, String Content, Boolean Append)
    {
        if(!(IsClientSettingsCreated(Path)))
        {
            return (boolean)(false);
        }
        try
        {
            File openFile = new File(Path);
            FileWriter Writer = new FileWriter(openFile,Append);
            Writer.append(Content);
            Writer.close();
            return (Boolean)(true);
        }
        catch(IOException e)
        {
            return (Boolean)(false);
        }
    }

    public String ReadFile(String Path)
    {
        String FileContent = "";
        try
        {
            File openFile = new File(Path);
            if(openFile.canRead())
            {
                FileReader Read = new FileReader(openFile);
                int ch;
                while((ch = Read.read()) != -1)
                {
                    FileContent += (char)ch;
                }
                Read.close();
            }
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
        }
        return (FileContent);
    }
 

}
