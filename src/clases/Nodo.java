/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

/**
 *
 * @author vanes
 */
public class Nodo {
    public String dato, direccion;
    Nodo enlace;
    public Nodo siguiente, anterior;
    
     public Nodo(String nombre, String direccion) {
        this.dato = nombre;
        this.direccion = direccion;
    }
    
    public Nodo(String x){
        dato = x;
        enlace = null;
    }
    
    public Nodo(String x, Nodo n){
        dato = x;
        enlace = n;
    }
    
    public String getDato(){
        return dato;
       
    }
    
    public Nodo getEnlace(){
        return enlace;
    }
    public void setEnlace(Nodo enlace){
        this.enlace = enlace;
    }
}
