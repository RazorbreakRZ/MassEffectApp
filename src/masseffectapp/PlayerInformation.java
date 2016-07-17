/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package masseffectapp;

/**
 *
 * @author Razorbreak
 */
class PlayerInformation {
    String name="",country="",title="",operation="",lastChallenge="";
    String hours="0",challenges="0",n7level="0",games="0",credits="0";
    boolean opUnlocked=false,lastChUnlocked=false;
    boolean lastChType=false;//true=gold,false=silver
    
    public PlayerInformation(){}
    
    public void printPlayerInfo(){
        System.out.println("NAME: "+name);
        System.out.println("COUNTRY: "+country);
        System.out.println("TITLE: "+title);
        System.out.println("N7: "+n7level);
        System.out.println("POINTS CHALLENGES: "+challenges);
        System.out.println("HOURS: "+hours);
        System.out.println("GAMES: "+games);
        System.out.println("CREDITS: "+credits);
        System.out.println("OPERATION: "+operation+" - Unlocked? "+opUnlocked);
        System.out.println("LAST CHALLENGE: "+lastChallenge+" ("+lastChType+") - Unlocked? "+lastChUnlocked);
    }
    
    
}
