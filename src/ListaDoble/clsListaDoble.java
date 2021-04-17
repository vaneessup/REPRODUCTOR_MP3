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
public class clsListaDoble extends model{
    
    //Definir
    protected Nodo cabeza; //nodo que se llama primero
    
    
    public clsListaDoble insertarCabeza(String entrada, String dire){
        
        Nodo nuevo;
        nuevo = new Nodo(entrada, dire);
        nuevo.siguiente = cabeza;
        if(cabeza != null){
            cabeza.anterior = nuevo;
        }
        cabeza = nuevo;
        return this;
    }
    
    public clsListaDoble insertaDespues(Nodo atras, String entrada, String dire){
        Nodo nuevo;
        nuevo = new Nodo(entrada,dire);
        nuevo.siguiente = atras.siguiente;
        
        if(atras.siguiente != null){
            atras.siguiente.anterior = nuevo;
        }
        atras.siguiente = nuevo;
        nuevo.anterior = atras;
        return this;
    }
    
     //Nueva Inserción por la cabeza
    public clsListaDoble inserta(String entrada, String dire){
        
        
        Nodo nuevo = new Nodo(entrada, dire);
        if(vacia()){
            //insertaCabeza(entrada, dire);
            primero = nuevo; //si esta vacio sera la cabeza
            ultimo = nuevo;
        }else {
            nuevo.anterior = ultimo;
            ultimo.siguiente = nuevo;
            ultimo = nuevo;
            
        }
        tam++;
        return this;
    }
            
    
    public void borrar(Nodo song){
        Nodo actual;
        boolean encontrado = false;
        actual = cabeza;
        
        //bucle de busqueda
        while(actual != null && !encontrado){
            actual = actual.siguiente;
        
        //enlace de nodo anterior con el siguiente
        
        if(actual != null){
            //distinguir entre nodo cabecero y el resto de la lista
            if(actual == cabeza){
                cabeza = actual.siguiente;
                if(actual.siguiente != null){
                    actual.siguiente.anterior = null;
                }
            }else if(actual.siguiente != null){//no es el ultimo nodo
                actual.anterior.siguiente = actual.siguiente;
                actual.siguiente.anterior = actual.anterior;
            }else{//ultimo nodo
                actual.anterior.siguiente = null;
            }
            actual = null;
        }    
        
    }
 }
    public boolean vacia() {
        return primero == null;
    }

    public void clear() {
        while (!vacia()) {
            borrar(primero);
        }
    }
     
   public void visualizar(){
        Nodo n;
        n = cabeza;
        while(n!= null){
            System.out.println(n.nombre +"\n");
            n = n.siguiente;
        }
    }
    
    public Nodo cancion(int song){
        if (song < 0 || song >= tam) {
            return null;
        }
        
        int n = 0;
        Nodo aux = primero;
        while (n != song) {            
            aux = aux.siguiente;
            n++;
        }
        
        return aux;
    }
   
    public boolean buscar(String nombre, String direc){
        Nodo aux = primero;

        while (aux != null) {
            if (aux.nombre.equals(nombre) && aux.direccion.equals(direc)) {
                return true;
            }
            aux = aux.siguiente;
        }
        return false;
    }
    public int contar(Nodo b) {
        Nodo aux = primero;
        int con = 0;

        while (aux != null) {
            if (aux == b) {
                return con;
            }
            aux = aux.siguiente;
            con++;
        }
        return -1;
    }
}
