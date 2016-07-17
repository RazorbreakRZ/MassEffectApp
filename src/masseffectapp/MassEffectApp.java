/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package masseffectapp;

/**
 *
 * @author Razorbreak
 */
public class MassEffectApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        webPage source = new webPage("http://social.bioware.com/n7hq/?name=RazorbreakRZ&platform=pc");
        source.saveToFile(source.getResponse());
    }
}
