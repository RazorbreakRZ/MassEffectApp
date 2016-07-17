/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package masseffectapp;

/**
 *
 * @author Razorbreak
 */
public class Arma {
    private String nombre="null";
    private String tipo="none";    
    
    public Arma(String name,String type){
        nombre=name;
        tipo=type;
    }
    
    public String getName(){
        return this.nombre;
    }
    
    public String getTipo(){
        return this.tipo;
    }
    
    @Override
    public String toString(){
        String s="";
        s+="·"+this.nombre;
        s+=" ("+this.tipo+")";
        return s;        
    }
    
}
