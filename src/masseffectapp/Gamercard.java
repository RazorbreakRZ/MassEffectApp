/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package masseffectapp;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author Razorbreak
 */
public class Gamercard extends Thread{
    private PlayerInformation player;
    private Config cfg;
    private int despIni = 0;//15px si usa cabecera origin
    BufferedImage gamercard = null;
    BufferedImage base = null;
    BufferedImage gold = null;
    BufferedImage silver = null;
    BufferedImage awardInactive = null;
    BufferedImage awardActive = null;
    BufferedImage avatar = null;
    BufferedImage flag = null;
    Font fuente;
    private JPanel window;
    
    public Gamercard(PlayerInformation player,Config cfg,JPanel preview){
        this.player = player;
        this.cfg = cfg;
        window = preview;
    }
    
    public void scanResources(){
        gamercard = new BufferedImage(200,150+despIni,BufferedImage.TRANSLUCENT);
        
        try {
            base = ImageIO.read(new File(cfg.resourcesFolder+"\\base.png"));
        } catch (IOException ex) {System.err.println(ex);}
            
        try {
            gold = ImageIO.read(new File(cfg.resourcesFolder+"\\gold.png"));
        } catch (IOException ex) {System.err.println(ex);}
        
        try {
            silver = ImageIO.read(new File(cfg.resourcesFolder+"\\silver.png"));
        } catch (IOException ex) {System.err.println(ex);}
        
        try {
            awardActive = ImageIO.read(new File(cfg.resourcesFolder+"\\awardactive.png"));
        } catch (IOException ex) {System.err.println(ex);}
        
        try {
            awardInactive = ImageIO.read(new File(cfg.resourcesFolder+"\\awardinactive.png"));
        } catch (IOException ex) {System.err.println(ex);}
        
        try {
            flag = ImageIO.read(new File(cfg.resourcesFolder+"\\flag.png"));
        } catch (IOException ex) {System.err.println(ex);}
        
        try {
            avatar = ImageIO.read(new File(cfg.resourcesFolder+"\\avatar.png"));
        } catch (IOException ex) {System.err.println(ex);}
        
        fuente = new Font(cfg.resourcesFolder+"\\font.ttf",Font.TRUETYPE_FONT,10);
        
    }
    
    public void generateGamercard(){
        Graphics g = gamercard.getGraphics();
        g.drawImage(base, 0, 0, null);
        g.setFont(fuente);
        g.drawImage(avatar,10,35+this.despIni,40,40,null);
        g.setColor(Color.orange);
        g.drawString(player.name, 82, 35+this.despIni+7);
        g.drawString(player.country, 98, 51+this.despIni+7);
        if(player.country.equals("Espana")){//Pinta la cedilla de la ñ
            int a=122,b=51+this.despIni,l=3;
            g.drawLine(a, b, a+l, b);
        }
        String ch = player.title;
        if(ch.length()>21){
            ch = ch.substring(0,18)+"...";
        }
        g.drawString(ch, 81, 67+this.despIni+7);
        ch = player.operation;
        if(ch.length()>21){
            ch = ch.substring(0,18)+"...";
        }
        g.drawString(ch, 61, 110+this.despIni+7);
        ch = player.lastChallenge;
        if(ch.length()>21){
            ch = ch.substring(0,18)+"...";
        }
        g.drawString(ch, 18, 134+this.despIni+7);
        g.setColor(Color.white);
        g.drawString(player.n7level, 34, 81+this.despIni+7);
        g.drawString(player.challenges, 110, 81+this.despIni+7);
        g.drawString(player.hours+"H", 27, 95+this.despIni+7);
        g.drawString(player.games, 84, 95+this.despIni+7);
        g.drawString(player.credits+"Cr.", 136, 95+this.despIni+7);
        
        g.drawImage(flag,98+7*player.country.length(),50+this.despIni,16,11,null);
        if(player.opUnlocked){
            g.drawImage(awardActive,152,109+this.despIni,38,40,null);
        }else{
            g.drawImage(awardInactive,152,109+this.despIni,38,40,null);
        }
        if(player.lastChUnlocked){
            if(player.lastChType){
                g.drawImage(gold,125,130+this.despIni,18,18,null);
            }else{
                g.drawImage(silver,125,130+this.despIni,18,18,null);
            }
        }
    }
    
    public void saveGamercard(){
        try {
            if(cfg.saveFolder!=null){
                File outputfile = new File(cfg.saveFolder+"\\"+player.name+".png");            
                ImageIO.write(gamercard, "png", outputfile);
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
    
    public void forceRedraw(){
        Graphics g = window.getGraphics();
        generateGamercard();
        g.drawImage(gamercard, 7, 15, null);
    }
    
    @Override
    public void run(){
        forceRedraw();
        saveGamercard();
    }    
}