package aed;

import java.util.*;

public class AVL<T extends Comparable<T>> {
    private Nodo _raiz;
    private int _cardinal;

    private class Nodo {
        T valor;
        Nodo padre;
        Nodo izq;
        Nodo der;
        int altura;

        Nodo(T v){
            valor = v;
            padre = null;
            izq = null;
            der = null;
            altura = 1;
        }
    }

    public AVL() {
        this._raiz = null;
        this._cardinal = 0;
    }

    public int cardinal(){
        return this._cardinal;
    }

    private int altura(Nodo n){
        if (n == null) {
            return 0;
        } else {
            return n.altura;
        }
    }

    private void actualizarAltura(Nodo n){
        n.altura = 1 + Math.max(this.altura(n.izq), this.altura(n.der));
    }

    private int FDB(Nodo n){
        return this.altura(n.izq) - this.altura(n.der);
    }

    /* Rotacion derecha
           z            y                      z             y  
          /            / \                    /             / \  
         y     ==>    x   z   Y si tengo     y      ==>    x   z 
        /                                   / \               /   
       x                                   x   T3            T3     
    
    Ejemplo
               30             20   
               /             /  \   
              20      ==>   10  30 
             /  \               /  
            10   25            25  
    */

    private Nodo rotacionDerecha(Nodo z){
        Nodo y = z.izq;
        Nodo T3 = y.der;

        y.der = z;
        z.izq = T3;

        if (T3 != null) T3.padre = z;
        y.padre = z.padre;
        z.padre = y;

        if (y.padre != null) {
            if (y.padre.izq == z) {
                y.padre.izq = y;
            } else {
                y.padre.der = y;
            }
        } else {
            this._raiz = y;
        }

        this.actualizarAltura(z);
        this.actualizarAltura(y);

        return y;
    }

    /* Rotacion Izquierda
         z              y                    z              y    
          \            / \                    \            / \   
           y    ==>   z   x    Y si tengo      y    ==>   z   x  
            \                                 / \          \     
             x                              T2   x          T2    
    Ejemplo
             30              40    
              \             / \   
               40    ==>   30   50  
              /  \           \ 
             45  50           45
    */

    public Nodo rotacionIzquierda(Nodo z){
        Nodo y = z.der;
        Nodo T2 = y.izq;

        y.izq = z;
        z.der = T2;

        if (T2 != null) T2.padre = z;
        y.padre = z.padre;  
        z.padre = y;

        if (y.padre != null) {
            if (y.padre.izq == z) {
                y.padre.izq = y;
            } else {
                y.padre.der = y;
            }
        } else {
            this._raiz = y;
        }
        
        this.actualizarAltura(z);
        this.actualizarAltura(y);

        return y;
    }

    private Nodo balancear(Nodo n) {
        this.actualizarAltura(n);
        int balance = this.FDB(n);

        //Caso LL: Inserté un hijo izquierdo en la rama izquierda
        if (balance > 1 && this.FDB(n.izq) >= 0) {
            return this.rotacionDerecha(n);
        }

        //Caso LR: Inserté un hijo izquierdo en la rama derecha
        if (balance > 1 && this.FDB(n.izq) < 0) {
            n.izq = this.rotacionIzquierda(n.izq);
            if (n.izq != null) n.izq.padre = n;
            return this.rotacionDerecha(n);
        }

        //Caso RR: Inserté un hijo derecho en la rama derecha
        if (balance < -1 && this.FDB(n.der) <= 0) {
            return this.rotacionIzquierda(n);
        }

        //Caso RL: Inserté un hijo derecho en la rama izquierda
        if (balance < -1 && this.FDB(n.der) > 0) {
            n.der = this.rotacionDerecha(n.der);
            if (n.der != null) n.der.padre = n;
            return this.rotacionIzquierda(n);
        }

        // Si está balanceado, devolver el mismo nodo
        return n;
    }

    private void balancearArbol(Nodo n){
        while (n != null) {
            n = balancear(n);
            n = n.padre;
        }
    }

    public boolean pertenece(T elem){
        Nodo actual = this._raiz;
        while (actual != null && !elem.equals(actual.valor)) {
            if (actual.valor.compareTo(elem) < 0) {
                actual = actual.der;
            } else {
                actual = actual.izq;
            }
        }
        return actual != null;
    }

    public void insertar(T elem){
        if (this._raiz == null) {
            this._raiz = new Nodo(elem);
        } else {
            Nodo actual = this._raiz;
            Nodo nuevo = new Nodo(elem);
            Nodo padre = null;
            while (actual != null && !actual.valor.equals(elem)) {
                padre = actual;
                if (actual.valor.compareTo(elem) < 0) {
                    actual = actual.der;
                } else {
                    actual = actual.izq;
                }
            }

            if (actual != null) return;

            nuevo.padre = padre;
            if (padre.valor.compareTo(elem) < 0) {
                padre.der = nuevo;
            } else {
                padre.izq = nuevo;
            }
            this._cardinal += 1;
            this.balancearArbol(padre);
        }
    }

    public void eliminar(T elem){
        Nodo actual = this._raiz;
        Nodo padre = null;
        while (actual != null && !actual.valor.equals(elem)) {
            padre = actual;
            if (actual.valor.compareTo(elem) < 0) {
                actual = actual.der;
            } else {
                actual = actual.izq;
            }
        }
        //Caso 1: Elem no pertenece a ABB.
        if (actual == null) return;
        
        //Caso 2: El nodo cuyo valor es elem no tiene hijos.
        if (actual.izq == null && actual.der == null) {
            if (padre == null) {
                this._raiz = null;                        
            } else if (padre.izq == actual) {
                padre.izq = null;
            } else {
                padre.der = null;
            }
            this._cardinal -= 1;
            this.balancearArbol(padre);
            return;
        }
        
        //Caso 3: El nodo cuyo valor es elem tiene 1 hijo.
        if (actual.izq == null || actual.der == null) {
            Nodo hijo = null;
            if (actual.izq == null && actual.der != null) {
                hijo = actual.der;
            } else if (actual.izq != null && actual.der == null) {
                hijo = actual.izq;
            }
            hijo.padre = padre;
            
            if (padre == null) {
                this._raiz = hijo;
            } else if (padre.izq == actual) {
                padre.izq = hijo;
            } else {
                padre.der = hijo;
            }
            this._cardinal -= 1;
            this.balancearArbol(padre);
            return;
        }

        //Caso 4: El nodo cuyo valor es elem tiene 2 hijos.
        if (actual.izq != null && actual.der != null) {
            Nodo predecesorInmediato = maximoNodo(actual.izq);
            this.eliminar(predecesorInmediato.valor);
            actual.valor = predecesorInmediato.valor;
            this.balancearArbol(padre);
            this._cardinal -= 1;
            return;
        }
    }

    private Nodo maximoNodo (Nodo n) {
        while (n != null && n.der != null) {
            n = n.der;
        }
        return n;
    }
}