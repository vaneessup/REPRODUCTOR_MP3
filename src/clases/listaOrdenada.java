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
public class listaOrdenada extends lista{
    public listaOrdenada(){
        super();
    }
    
   public listaOrdenada insertar(String entrada, String dire){
        Nodo nuevo; 
        nuevo = new Nodo(entrada, dire);
        if (vacia()) {
            primero = nuevo;
            ultimo = nuevo;
        }
        if(primero == null){
            primero = nuevo;
        }else if(entrada.compareTo(primero.getDato())>0){
            //nuevo.setEnlace(primero);
            //primero = nuevo;
            nuevo.anterior = ultimo;
            ultimo.siguiente = nuevo;
            ultimo = nuevo;
            Nodo anterior, p;
            anterior = p = primero;
            nuevo.setEnlace(anterior.getEnlace());
            while ((p.getEnlace() != null) && (entrada.compareTo(p.getDato()))>0)
                {
                    anterior = p;
                    p = p.getEnlace();
                }
                if (entrada.compareTo(p.getDato())>0)
                {//se inserta despues del ultimo nodo
                    anterior = p;
                }
                nuevo.setEnlace(anterior.getEnlace());
                anterior.setEnlace(nuevo);
        }
        tam++;
        return this;
    }
        
   
    //METODOS PENDIENTES DE LA LISTA ORDENADA
    //eliminar
    //buscarLista
    //
   
   public boolean buscar(String nombre, String direc){
        Nodo aux = primero;

        while (aux != null) {
            if (aux.dato.equals(nombre) && aux.direccion.equals(direc)) {
                return true;
            }
            aux = aux.siguiente;
        }
        return false;
    }
   public Nodo get_cancion(int index){
        if (index < 0 || index >= tam) {
            return null;
        }
        
        int n = 0;
        Nodo aux = primero;
        while (n != index) {            
            aux = aux.siguiente;
            n++;
        }
        
        return aux;
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
   public boolean vacia() {
        return primero == null;
    }

    public void clear() {
        while (!vacia()) {
            borrar(primero);
        }
    }
   public void borrar(Nodo b) {
        if (b == primero) {
            if (tam == 1) {
                primero = null;
                tam--;
                return;
            }
            primero.siguiente.anterior = null;
            primero = primero.siguiente;
            tam--;
            return;
        }
        tam--;
        if (b == ultimo) {
            ultimo.anterior.siguiente = null;
            ultimo = ultimo.anterior;
            return;
        }
        b.siguiente.anterior = b.anterior;
        b.siguiente.anterior.siguiente = b.siguiente;
    }
    
    
}
