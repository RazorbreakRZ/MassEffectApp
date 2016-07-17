/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package masseffectapp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Razorbreak
 */
public class Config {
    String home;// C:\Users\[username]
    File folder;// C:\Users\[username]\.RRZ
    File resourcesFolder;// C:\Users\[username]\.RRZ\Resources
    File saveFolder=null;
    //String[] tokens={"saveFolder=","name=","country=","title=","n7level=","challenges=","hours=","games=","credits=","operation=",
    //                 "opUnlocked=","lastChallenge=","lastChType=","lastChUnlocked="};
    
    public Config(){
        home = System.getProperty("user.home");
        folder = new File(home+"\\.ME3App");
        resourcesFolder = new File(home+"\\.ME3App\\Resources");
    }
    
    public void loadConfiguration(PlayerInformation player){
        try {
            BufferedReader br = new BufferedReader(new FileReader(folder+"\\config.cfg"));
            String line;
            while((line = br.readLine())!=null){
                //System.out.println(line);
                if(line.contains("saveFolder=")){
                    this.saveFolder= new File(line.substring("saveFolder=".length()));
                }else if(line.contains("name=")){
                    player.name=line.substring("name=".length());
                }else if(line.contains("country=")){
                    player.country=line.substring("country=".length());
                }else if(line.contains("title=")){
                    player.title=line.substring("title=".length());
                }else if(line.contains("n7level=")){
                    player.n7level=line.substring("n7level=".length());
                }else if(line.contains("challenges=")){
                    player.challenges=line.substring("challenges=".length());
                }else if(line.contains("hours=")){
                    player.hours=line.substring("hours=".length());
                }else if(line.contains("games=")){
                    player.games=line.substring("games=".length());
                }else if(line.contains("credits=")){
                    player.credits=line.substring("credits=".length());
                }else if(line.contains("operation=")){
                    player.operation=line.substring("operation=".length());
                }else if(line.contains("opUnlocked=")){
                    player.opUnlocked=line.substring("opUnlocked=".length()).equals("true");
                }else if(line.contains("lastChallenge=")){
                    player.lastChallenge=line.substring("lastChallenge=".length());
                }else if(line.contains("lastChType=")){
                    player.lastChType=line.substring("lastChType=".length()).equals("true");
                }else if(line.contains("lastChUnlocked=")){
                    player.lastChUnlocked=line.substring("lastChUnlocked=".length()).equals("true");
                }
            }
            //player.printPlayerInfo();
            System.out.println("***Fin lectura config.cfg***\n\n");
            br.close();
        }catch(Exception e){
            //System.err.println(e);
            System.out.println("***Creando fichero de configuración config.cfg***");
            File f = new File(this.folder+"\\config.cfg");
            try {
                f.createNewFile();
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
    }
    
    public void saveConfiguration(PlayerInformation player){
        String configFile="[CONFIG]\n";
        if(saveFolder!=null && !saveFolder.getPath().equals("")){
            configFile+="saveFolder="+saveFolder.getPath()+"\n";
        }
                
        configFile+="[GAMERCARD]\n"+
                "name="+player.name+"\n"+
                "country="+player.country+"\n"+
                "title="+player.title+"\n"+
                "n7level="+player.n7level+"\n"+
                "challenges="+player.challenges+"\n"+
                "hours="+player.hours+"\n"+
                "games="+player.games+"\n"+
                "credits="+player.credits+"\n"+
                "operation="+player.operation+"\n"+
                "opUnlocked="+player.opUnlocked+"\n"+
                "lastChallenge="+player.lastChallenge+"\n"+
                "lastChType="+player.lastChType+"\n"+
                "lastChUnlocked="+player.lastChUnlocked;
        
        
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(folder+"\\config.cfg"));
            bw.write(configFile);
            bw.close();
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}
