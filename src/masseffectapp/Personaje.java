/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package masseffectapp;

/**
 *
 * @author Razorbreak
 */
public class Personaje {
    private String nombre="null";
    private String clase="null";
    private String raza="null";
    private String[] poderes={"Unknown","Unknown","Unknown","Unknown","Unknown"};
    private String[] rangos={"0","0","0","0","0"};
    private boolean isValid=false;
    
    public Personaje(String name,String clase,String raza,String[] powers){
        nombre=name;
        this.clase=clase;
        this.raza=raza;
        if(powers.length==5){
            for(int i=0;i<5;i++){
                poderes[i]=powers[i];
            }
            isValid=true;
        }
    }
    
    public Personaje(String name,String clase,String raza,String[] powers,String[] ranks){
        nombre=name;
        this.clase=clase;
        this.raza=raza;
        if(powers.length==5 && ranks.length==5){
            isValid=true;
            String regex="[0-9](a|b)*";
            for(int i=0;i<5;i++){
                poderes[i]=powers[i];
                if(ranks[i].matches(regex)){
                    rangos[i]=ranks[i];
                }else{
                    isValid=false;
                }
            }
        }
    }
    
    public String getName(){
        return this.nombre;
    }
    
    public String getClase(){
        return this.clase;
    }
    
    public String getRaza(){
        return this.raza;
    }
    
    @Override
    public String toString(){
        String s="";
        s+="·"+this.nombre;
        s+=" ("+this.clase+")";
        return s;        
    }
    
}
