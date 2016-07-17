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
public class ListaPersonajes {
    private ArrayList<Personaje> listado=new ArrayList();
    private ArrayList<Personaje> filtrado=new ArrayList();
    
    public ListaPersonajes(){
        //Adeptos
        listado.add(new Personaje("Adepto Humano","Adepto","Humano",new String[]{"Singularidad","Alteración","Onda de choque","Entrenamiento de la alianza","Buena forma"}));
        listado.add(new Personaje("Adepta Humana","Adepto","Humano",new String[]{"Singularidad","Alteración","Onda de choque","Entrenamiento de la alianza","Buena forma"}));
        listado.add(new Personaje("Adepto Drell","Adepto","Drell",new String[]{"Asaltar","Atracción","Granada de racimo","Asesino Drell","Buena forma"}));
        listado.add(new Personaje("Adepta Asari","Adepto","Asari",new String[]{"Estasis","Alteración","Lanzamiento","Justiciera Asari","Buena forma"}));
        listado.add(new Personaje("Justiciera Asari","Adepto","Asari",new String[]{"Esfera biótica","Asaltar","Atracción","Justiciera Asari","Buena forma"}));
        listado.add(new Personaje("Adepto del proyecto Fénix","Adepto","Humano",new String[]{"Singularidad","Azote","Aplastar","Entrenamiento Fénix","Buena forma"}));
        listado.add(new Personaje("Furia N7","Adepto","Humano",new String[]{"Lanzamiento","Campo de aniquilación","Fuerza oscura","Furia N7","Buena forma"}));
        listado.add(new Personaje("Chamán Krogan","Adepto","Krogan",new String[]{"Barrera","Onda de choque","Alteración","Berseker Krogan","Rabia"}));
        listado.add(new Personaje("Acuchillador Batariano","Adepto","Batariano",new String[]{"Alteración","Granada de racimo","Azote","Refuerzo Batariano","Buena forma"}));
        listado.add(new Personaje("Adepto Volus","Adepto","Volus",new String[]{"Estasis","Orbes bióticos","Mejora de escudo","Entrenamiento Volus","Buena forma"}));
        listado.add(new Personaje("Recolector despertado","Adepto","Recolector",new String[]{"Esfera oscura","Enjambre de buscadores","Fuerza oscura","Ancestro vengativo","Guerrero antiguo"}));
        //Soldados
        listado.add(new Personaje("Soldado Humano","Soldado","Humano",new String[]{"Subida de adrenalina","Disparo de conmoción","Granada de fragmentación","Entrenamiento de la alianza","Buena forma"}));
        listado.add(new Personaje("Soldado Humana","Soldado","Humano",new String[]{"Subida de adrenalina","Disparo de conmoción","Granada de fragmentación","Entrenamiento de la alianza","Buena forma"}));
        listado.add(new Personaje("Soldado Turiano","Soldado","Turiano",new String[]{"Tirador","Disparo de conmoción","Mina de proximidad","Veterano Turiano","Buena forma"}));
        listado.add(new Personaje("Soldado Krogan","Soldado","Krogan",new String[]{"Carnicería","Fortificación","Granada inferno","Berseker Krogan","Rabia"}));
        listado.add(new Personaje("Battlefield 3","Soldado","Humano",new String[]{"Subida de adrenalina","Carnicería","Granada inferno","Entrenamiento de la alianza","Buena forma"}));
        listado.add(new Personaje("Soldado Batariano","Soldado","Batariano",new String[]{"Armadura de cuchillas","Cuchillas proyectiles","Granada inferno","Refuerzo Batariano","Buena forma"}));
        listado.add(new Personaje("Soldado Vorcha","Soldado","Vorcha",new String[]{"Sed de sangre","Lanzallamas","Carnicería","Resistencia Vorcha","Buena forma"}));
        listado.add(new Personaje("Destructor N7","Soldado","Humano",new String[]{"Modo Devastador","Lanzamisiles Hawk","Multigranada de fragmentación","Traje de combate T5-V","Sistemas internos del T5-V"}));
        listado.add(new Personaje("Estrago Turiano","Soldado","Turiano",new String[]{"Paquete de estimulantes","Descarga criogénica","Ataque estrago","Legión Armiger","Buena forma"}));
        listado.add(new Personaje("Tropa Geth","Soldado","Geth",new String[]{"Lanzallamas","Fortificación","Modo cazador","IA en red","Hardware avanzado"}));
        listado.add(new Personaje("Tirador Quariano","Soldado","Quariano",new String[]{"Tirador","Escaneo táctico","Sabotaje","Defensor Quariano","Buena forma"}));
        listado.add(new Personaje("Juggernaut Geth","Soldado","Geth",new String[]{"Escudo hexagonal","Pulso de asedio","Torreta Geth","Juggernaut Geth","Plataforma endurecida"}));
        //Ingenieros
        listado.add(new Personaje("Ingeniero Humano","Ingeniero","Humano",new String[]{"Sobrecarga","Incineración","Dron de combate","Entrenamiento de la alianza","Buena forma"}));
        listado.add(new Personaje("Ingeniera Humana","Ingeniero","Humano",new String[]{"Sobrecarga","Incineración","Dron de combate","Entrenamiento de la alianza","Buena forma"}));
        listado.add(new Personaje("Ingeniero Salariano","Ingeniero","Salariano",new String[]{"Incineración","Drenaje de energía","Señuelo","Operativo Salariano","Buena forma"}));
        listado.add(new Personaje("Ingeniera Quariana","Ingeniero","Quariano",new String[]{"Incineración","Descarga criogénica","Torreta vigía","Defensor Quariano","Buena forma"}));
        listado.add(new Personaje("Ingeniero Geth","Ingeniero","Geth",new String[]{"Torreta Geth","Modo cazador","Sobrecarga","IA en red","Hardware avanzado"}));
        listado.add(new Personaje("Ingeniero Quariano","Ingeniero","Quariano",new String[]{"Incineración","Escaneo táctico","Granada de descarga","Defensor Quariano","Buena forma"}));
        listado.add(new Personaje("Demoledor N7","Ingeniero","Humano",new String[]{"Granada rastreadora","Granada de descarga","Suministrar emisor","Demoledor N7","Buena forma"}));
        listado.add(new Personaje("Saboteador Turiano","Ingeniero","Turiano",new String[]{"Sabotaje","Torreta vigía","Granada rastreadora","Legión Armiger","Buena forma"}));
        listado.add(new Personaje("Cazador Vorcha","Ingeniero","Vorcha",new String[]{"Sed de sangre","Incineración","Red de sumisión","Resistencia Vorcha","Buena forma"}));
        listado.add(new Personaje("Mercenario de los Garras","Ingeniero","Humano",new String[]{"Mina trampa Caín","Flechas conmoción","Flechas perforantes","Mercenario de élite","Maestría con omniarco"}));
        listado.add(new Personaje("Ingeniero Volus","Ingeniero","Volus",new String[]{"Mina de proximidad","Recarga de escudo","Mina de reconocimiento","Entrenamiento Volus","Buena forma"}));
        //Centinelas
        listado.add(new Personaje("Centinela Humano","Centinela","Humano",new String[]{"Lanzamiento","Alteración","Armadura tecnológica","Entrenamiento de la alianza","Buena forma"}));
        listado.add(new Personaje("Centinela Humana","Centinela","Humano",new String[]{"Lanzamiento","Alteración","Armadura tecnológica","Entrenamiento de la alianza","Buena forma"}));
        listado.add(new Personaje("Centinela Turiano","Centinela","Turiano",new String[]{"Alteración","Sobrecarga","Armadura tecnológica","Veterano Turiano","Buena forma"}));
        listado.add(new Personaje("Centinela Krogan","Centinela","Krogan",new String[]{"Incineración","Granada de levitación","Armadura tecnológica","Berseker Krogan","Rabia"}));
        listado.add(new Personaje("Centinela Batariano","Centinela","Batariano",new String[]{"Armadura de cuchillas","Onda de choque","Red de sumisión","Refuerzo Batariano","Buena forma"}));
        listado.add(new Personaje("Centinela Vorcha","Centinela","Vorcha",new String[]{"Sed de sangre","Lanzallamas","Granada de racimo","Resistencia Vorcha","Buena forma"}));
        listado.add(new Personaje("Paladín N7","Centinela","Humano",new String[]{"Congelación instantánea","Incineración","Drenaje de energía","Paladín N7","Maestría con escudo"}));
        listado.add(new Personaje("Valquiria Asari","Centinela","Asari",new String[]{"Armadura tecnológica","Campo de aniquilación","Alteración","Valquiria Asari","Buena forma"}));
        listado.add(new Personaje("Mercenario Volus","Centinela","Volus",new String[]{"Señuelo","Dron de combate","Recarga de escudo","Entrenamiento Volus","Buena forma"}));
        listado.add(new Personaje("Jefe criminal Krogan","Centinela","Krogan",new String[]{"Armadura tecnológica","Martillo biótico","Martillo eléctrico","Jefe criminal Krogan","Rabia jefe criminal"}));
        //Infiltrados
        listado.add(new Personaje("Infiltrado Humano","Infiltrado","Humano",new String[]{"Granada adhesiva","Ocultación táctica","Descarga criogénica","Entrenamiento de la alianza","Buena forma"}));
        listado.add(new Personaje("Infiltrada Humana","Infiltrado","Humano",new String[]{"Granada adhesiva","Ocultación táctica","Descarga criogénica","Entrenamiento de la alianza","Buena forma"}));
        listado.add(new Personaje("Infiltrada Quariana","Infiltrado","Quariano",new String[]{"Granada adhesiva","Ocultación táctica","Sabotaje","Defensor Quariano","Buena forma"}));
        listado.add(new Personaje("Infiltrado Salariano","Infiltrado","Salariano",new String[]{"Drenaje de energía","Ocultación táctica","Mina de proximidad","Operativo Salariano","Buena forma"}));
        listado.add(new Personaje("Cazador Geth","Infiltrado","Geth",new String[]{"Ocultación táctica","Modo cazador","Mina de proximidad","IA en red","Hardware avanzado"}));
        listado.add(new Personaje("Infiltrado Quariano","Infiltrado","Quariano",new String[]{"Escaneo táctico","Ocultación táctica","Granada de descarga","Defensor Quariano","Buena forma"}));
        listado.add(new Personaje("Sombra N7","Infiltrado","Humano",new String[]{"Ocultación táctica","Ataque sombra","Corte eléctrico","Sombra N7","Maestría con espada"}));
        listado.add(new Personaje("Fantasma Turiano","Infiltrado","Turiano",new String[]{"Sobrecarga","Ocultación táctica","Paquetes estimulantes","Legión Armiger","Buena forma"}));
        listado.add(new Personaje("Cazadora Asari","Infiltrado","Asari",new String[]{"Ocultación táctica","Fuerza Oscura","Alteración","Cazadora Asari","Buena forma"}));
        listado.add(new Personaje("Asesino Drell","Infiltrado","Drell",new String[]{"Ocultación táctica","Granada rastreadora","Mina de reconocimiento","Asesino Drell","Buena forma"}));
        listado.add(new Personaje("Unidad de infiltración de la Alianza","Infiltrado","Sintético",new String[]{"Ocultación táctica","Matriz de reparación","Congelación instantánea","IA liberada","Módula de forma física"}));
        //Vanguardias
        listado.add(new Personaje("Vanguardia Humano","Vanguardia","Humano",new String[]{"Carga biótica","Onda de choque","Nova","Entrenamiento de la alianza","Buena forma"}));
        listado.add(new Personaje("Vanguardia Humana","Vanguardia","Humano",new String[]{"Carga biótica","Onda de choque","Nova","Entrenamiento de la alianza","Buena forma"}));
        listado.add(new Personaje("Vanguardia Asari","Vanguardia","Asari",new String[]{"Carga biótica","Estasis","Granada de levitación","Justiciera Asari","Buena forma"}));
        listado.add(new Personaje("Vanguardia Drell","Vanguardia","Drell",new String[]{"Carga biótica","Atracción","Granada de racimo","Asesino Drell","Buena forma"}));
        listado.add(new Personaje("Señor de la guerra Krogan","Vanguardia","Krogan",new String[]{"Carga biótica","Carnicería","Barrera","Señor de la guerra Krogan","Rabia"}));
        listado.add(new Personaje("Vanguardia del proyecto Fénix","Vanguardia","Humano",new String[]{"Carga biótica","Azote","Aplastar","Entrenamiento Fénix","Buena forma"}));
        listado.add(new Personaje("Asesino N7","Vanguardia","Humano",new String[]{"Disruptor de fase","Carga biótica","Corte biótico","Asesino N7","Buena forma"}));
        listado.add(new Personaje("Luchador Batariano","Vanguardia","Batariano",new String[]{"Carga biótica","Azote","Armadura de cuchillas","Refuerzo Batariano","Buena forma"}));
        listado.add(new Personaje("Protector Volus","Vanguardia","Volus",new String[]{"Carga biótica","Orbes bióticos","Recarga de escudo","Entrenamiento Volus","Buena forma"}));
        listado.add(new Personaje("Vanguardia de la Cábala","Vanguardia","Turiano",new String[]{"Golpe venenoso","Cuchillas belladona","Concentración biótica","Veterano Turiano","Guanteletes ponzoñosos"}));
    }
    
    public ArrayList<Personaje> searchByRazaClase(String raza,String clase){
        this.filtrado.clear();
        Personaje pj;
        for(int i=0;i<this.listado.size();i++){
            if(((pj=this.listado.get(i)).getRaza().equals(raza)||raza.equals("Todas"))&&(pj.getClase().equals(clase)||clase.equals("Todas"))){
                this.filtrado.add(pj);
            }
        }
        return this.filtrado;  
    }
    
    public void check(){
        ArrayList<String> A= new ArrayList();
        ArrayList<String> B= new ArrayList();
        Personaje pj;
        System.out.println("Comprobando clases y razas...");
        for(int i=0;i<this.listado.size();i++){
            pj= this.listado.get(i);
            if(!A.contains(pj.getClase())){
                A.add(pj.getClase());
            }
            if(!B.contains(pj.getRaza())){
                B.add(pj.getRaza());
            }
        }
        System.out.print("-Clases: ");
        for(int i=0;i<A.size();i++){
            if(i==0){System.out.print("[");}
            System.out.print(A.get(i));
            if(i==A.size()-1){System.out.print("]");}
            else{System.out.print(",");}
        }
        System.out.print("\n-Razas: ");
        for(int i=0;i<B.size();i++){
            if(i==0){System.out.print("[");}
            System.out.print(B.get(i));
            if(i==B.size()-1){System.out.print("]");}
            else{System.out.print(",");}
        }
        System.out.println("\nEn total se han encontrado "+A.size()+" clases y "+B.size()+" razas diferentes.");
    }
    
    @Override
    public String toString(){
        String s="Listado de personajes:\n"
                + "======================";
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
