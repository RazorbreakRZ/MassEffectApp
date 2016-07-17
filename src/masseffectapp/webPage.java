/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package masseffectapp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import static org.apache.http.HttpHeaders.USER_AGENT;

/**
 *
 * @author Razorbreak
 */
public class webPage {
    private String url="";
    
    webPage(String link){
        this.url=link;
    }
    
    String getResponse(){
        String html="***RESPONSE***\n";
        String urla="http://social.bioware.com/language.php";
        String urlb="http://social.bioware.com/n7hq/agegate?from=agegate.php";
        String urlc="http://razorbreak.no-ip.org/insert.php";
        url = urlc;
        try {
            System.out.println("CONNECTING...");
            
            //POST
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            //add request header
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Accept-Language", "es-E,en;q=0.5");

            String urlParameters = "'Nombre'=Java-Bot&'Cantidad'=1&'diceType'=8";

            // Send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Post parameters : " + urlParameters);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
            }
            in.close();

            //print result
            html += response.toString();
            
            
            System.out.println(html);
        } catch (Exception ex) {
            System.out.println("ERROR(1): "+ex);
        }
        return html;
    }
    
    void saveToFile(String content){
        System.out.println("WRITING...");
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(System.getProperty("user.home")+"\\Desktop\\me3-source.html"));
            bw.write(content);
            bw.close();
        } catch (IOException ex) {
            System.err.println("ERROR(2): "+ex);
        }
    }
    
}
