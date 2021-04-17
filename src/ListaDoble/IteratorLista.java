/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ListaDoble;

import clases.Nodo;

/**
 *
 * @author vanes
 */
public class IteratorLista {
    
    private Nodo actual;
    
    public IteratorLista(clsListaDoble id){
        
        actual=id.cabeza;
    }
    
    public Nodo siguiente(){
        Nodo a;
        a = actual;
        
        if(actual != null){
            actual = actual.siguiente;
        }
        return a;
    }
    
}
