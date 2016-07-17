/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package masseffectapp;

import java.util.ArrayList;

/**
 *
 * @author Razorbreak
 */
public class ListaArmas {
    private ArrayList<Arma> listado=new ArrayList();
    private ArrayList<Arma> filtrado=new ArrayList();
    
    public ListaArmas(){
        listado.add(new Arma("Avenger M-8","Fusil de asalto"));
        listado.add(new Arma("Predator M-3","Pistola"));
        listado.add(new Arma("Katana M-23","Escopeta"));
        listado.add(new Arma("Shuriken M-4","Subfusil"));
        listado.add(new Arma("Mantis M-92","Rifle de francotirador"));
        listado.add(new Arma("Vindicator M-15","Fusil de asalto"));
        listado.add(new Arma("Mattock M-96","Fusil de asalto"));
        listado.add(new Arma("Phaeston","Fusil de asalto"));
        listado.add(new Arma("Phalanx M-5","Pistola"));
        listado.add(new Arma("Scimitar M-27","Escopeta"));
        listado.add(new Arma("Eviscerator M-22","Escopeta"));
        listado.add(new Arma("Tempest M-9","Subfusil"));
        listado.add(new Arma("Locust M-12","Subfusil"));
        listado.add(new Arma("Viper M-97","Rifle de francotirador"));
        listado.add(new Arma("Raptor M-13","Rifle de francotirador"));
        listado.add(new Arma("Incisor M-29","Rifle de francotirador"));
        listado.add(new Arma("Revenant M-76","Fusil de asalto"));
        listado.add(new Arma("Striker","Fusil de asalto"));
        listado.add(new Arma("Fusil de pulso Geth","Fusil de asalto"));
        listado.add(new Arma("Falcon M-37","Fusil de asalto"));
        listado.add(new Arma("Fusil de recolector","Fusil de asalto"));
        listado.add(new Arma("Argus M-55","Fusil de asalto"));
        listado.add(new Arma("Fusil antisintéticos ADAS","Fusil de asalto"));
        listado.add(new Arma("Pistola de arcos","Pistola"));
        listado.add(new Arma("M-11 Suppressor","Pistola"));
        listado.add(new Arma("Carnifex M-6","Pistola"));
        listado.add(new Arma("Executioner","Pistola"));
        listado.add(new Arma("Acólita","Pistola"));
        listado.add(new Arma("Raider AT-12","Escopeta"));
        listado.add(new Arma("Carabina Reegar","Escopeta"));
        listado.add(new Arma("Lanzapicos Graal","Escopeta"));
        listado.add(new Arma("Escopeta de plasma Geth","Escopeta"));
        listado.add(new Arma("Discípulo","Escopeta"));
        listado.add(new Arma("Claymore M-300","Escopeta"));
        listado.add(new Arma("N7 Piranha","Escopeta"));
        listado.add(new Arma("Hornet M-25","Subfusil"));
        listado.add(new Arma("Subfusil de plasmas Geth","Subfusil"));
        listado.add(new Arma("Widow M-98","Rifle de francotirador"));
        listado.add(new Arma("Krysae","Rifle de francotirador"));
        listado.add(new Arma("Rifle de francotirador recolector","Rifle de francotirador"));
        listado.add(new Arma("Lanzaarpones Kishock","Rifle de francotirador"));
        listado.add(new Arma("Valkyrie N7","Fusil de asalto"));
        listado.add(new Arma("Spitfire Geth","Fusil de asalto"));
        listado.add(new Arma("Sable M-99","Fusil de asalto"));
        listado.add(new Arma("Fusil de partículas","Fusil de asalto"));
        listado.add(new Arma("M-7 Lancer","Fusil de asalto"));
        listado.add(new Arma("N7 Typhoon","Fusil de asalto"));
        listado.add(new Arma("Harrier de Cerberus","Fusil de asalto"));
        listado.add(new Arma("Talon M-358","Pistola"));
        listado.add(new Arma("Scorpion","Pistola"));
        listado.add(new Arma("Paladin M-77","Pistola"));
        listado.add(new Arma("Eagle N7","Pistola"));
        listado.add(new Arma("M-11 Wraith","Escopeta"));
        listado.add(new Arma("Escopeta Venom","Escopeta"));
        listado.add(new Arma("Crusader N7","Escopeta"));
        listado.add(new Arma("Hurricane N7","Subfusil"));
        listado.add(new Arma("Subfusil recolector","Subfusil"));
        listado.add(new Arma("Castigadora Manada Sangrienta","Subfusil"));
        listado.add(new Arma("Valiant N7","Rifle de francotirador"));
        listado.add(new Arma("Jabalina","Rifle de francotirador"));
        listado.add(new Arma("Indra M-90","Rifle de francotirador"));
        listado.add(new Arma("Black Widow","Rifle de francotirador"));
    }
        
    public ArrayList<Arma> searchByTipo(String tipo){
        this.filtrado.clear();
        Arma wpn;
        for(int i=0;i<this.listado.size();i++){
            if((wpn=this.listado.get(i)).getTipo().equals(tipo)||tipo.equals("Todas")){
                this.filtrado.add(wpn);
            }
        }
        return this.filtrado;        
    }
    
    public void check(){
        ArrayList<String> A= new ArrayList();
        Arma wpn;
        System.out.println("Comprobando tipos...");
        for(int i=0;i<this.listado.size();i++){
            wpn= this.listado.get(i);
            if(!A.contains(wpn.getTipo())){
                A.add(wpn.getTipo());
            }
        }
        System.out.print("-Tipos: ");
        for(int i=0;i<A.size();i++){
            if(i==0){System.out.print("[");}
            System.out.print(A.get(i));
            if(i==A.size()-1){System.out.print("]");}
            else{System.out.print(",");}
        }
        System.out.println("\nEn total se han encontrado "+A.size()+" tipos de armas diferentes.");
    }
    
    @Override
    public String toString(){
        String s="Listado de armas:\n"
                + "=================";
        if(!this.filtrado.isEmpty()){
            for(int i=0;i<this.filtrado.size();i++){
                s+="\n"+this.filtrado.get(i).toString();
            }
        }else{
            s+="\n***Sin coincidencias***";
        }
        return s;        
    }
    
}
