/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ListaCircular;

import clases.Nodo;

/**
 *
 * @author vanes
 */
public class BaseListaCircular {
    
   //declarar propiedades de la lista
    int tam = 0;
    public  Nodo inicio;
    public Nodo fin;
    
    //metodo para revisar si la lista tiene datos
    public boolean vacia(){
        return(tam == 0);//si es igual a cero 
        //nos indica que la lista esta vacia
    }
    
    //insertando al inicio 
    public void insertar_inicio(String nombre, String dire){
        
        Nodo nuevo = new Nodo(nombre,dire);
        //revisar si esta vacia
        if(vacia()){
            inicio = nuevo;
            fin = nuevo;
            
            //apuntadores
            inicio.siguiente = fin;
            fin.siguiente = inicio;
            inicio.anterior = fin;
            fin.anterior = inicio;
            tam++;//aumentando el tama;o de la lista
        }else{
            //nodo auxiliar
            Nodo aux = inicio;
            nuevo.siguiente = aux;
            aux.anterior = nuevo;
            //igualar nodo de inicio con el que acabo de crear
            inicio = nuevo;//nos queda el nodo nuevo al inicio
            tam++;
        }
        
    }
    
    //metodo para insertar al final de la lista
        public void inserta_final(String nombre, String dire){
            Nodo nuevo = new Nodo(nombre, dire);
            
            if(vacia()){
                inicio = nuevo;
                fin = nuevo;
                //apuntadores
                inicio.siguiente = fin;
                fin.siguiente = inicio;
                inicio.anterior = fin;
                fin.anterior = inicio;
                //aumentar tama;o
                tam++;
                       
            }else{
                Nodo aux = fin;
                
                aux.siguiente = nuevo;
                nuevo.anterior = aux;
                
                fin = nuevo;
                tam++;
            }
            
        }
        
        //metodo para mostrar tama;o de la lista
        
    
}
