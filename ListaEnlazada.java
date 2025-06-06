package aed;

import java.util.*;

public class ListaEnlazada<T> implements Secuencia<T> {
    private Nodo _primero;
    private Nodo _ultimo;

    private class Nodo {
        T valor;
        Nodo sig;
        Nodo ant;

        Nodo(T v){
            valor = v;
        }
    }

    public ListaEnlazada() {
        this._primero = null;
        this._ultimo = null;
    }

    public int longitud() {
        Iterador<T> it = this.iterador();
        int cantidad = 0;
        while (it.haySiguiente()) {
            it.siguiente();
            cantidad++;
        }
        return cantidad;
    }

    public void agregarAdelante(T elem) {
        Nodo nuevo = new Nodo(elem);
        if (this._primero == null) {
            nuevo.sig = null;
            nuevo.ant = null;
            this._primero = nuevo;
            this._ultimo = nuevo;
        } else {
            nuevo.ant = null;
            nuevo.sig = this._primero;
            this._primero.ant = nuevo;
            this._primero = nuevo;
        }
    }

    public void agregarAtras(T elem) {
        Nodo nuevo = new Nodo(elem);
        if (this._primero == null) {
            nuevo.sig = null;
            nuevo.ant = null;
            this._primero = nuevo;
            this._ultimo = nuevo;
        } else {
            nuevo.ant = this._ultimo;
            nuevo.sig = null;
            this._ultimo.sig = nuevo;
            this._ultimo = nuevo;
        }
    }

    public T obtener(int i) {
        Iterador<T> it = this.iterador();
        int indice = 0;
        while (it.haySiguiente() && indice != i) {
            it.siguiente();
            indice++;
        }
        return it.siguiente();
    }

    public Nodo obtenerUltimo(){
        return this._ultimo;
    }

    public void eliminar(int i) {
        if (this.longitud() == 1){
            this._primero = null;
            this._ultimo = null;
        } else if (i == 0) {
            this._primero = this._primero.sig;
            this._primero.ant = null;
        } else if (i == this.longitud() - 1){
            this._ultimo = this._ultimo.ant;
            this._ultimo.sig = null;
        } else {
            /*Desde el iterador puedo acceder al valor pero no al nodo en sí.
              Por eso lo hice sin el iterador en este caso. Si lo usara para 
              recorrer, igualmente necesitaría un nodo que lo acompañe y sería
              redundante.*/
            /*Y como no uso el iterador que arranca sí o sí desde el primero,
              por lo menos por como están pensados los tests, lo hice como para
              optimizarlo. Dependiendo de si el i está más cerca del primer o del
              último nodo, arranca desde donde esté más cerca.*/
            if (i <= this.longitud() - 1 / 2){
                Nodo n = this._primero;
                for(int j = 0; j < i;j++){
                    n = n.sig;
                }
                (n.ant).sig = n.sig;
                (n.sig).ant = n.ant;
            } else {
                Nodo n = this._ultimo;
                for(int j = this.longitud() - 1; j > i; j--){
                    n = n.ant;
                }
                (n.ant).sig = n.sig;
                (n.sig).ant = n.ant;
            }
        }
    }

    public void modificarPosicion(int indice, T elem) {
        /*Mismo caso, desde el iterador puedo solo acceder a valores.
          Solo lectura. Y tiene sentido sino agregaria cualidades que 
          exceden la "iteración".*/
        if (indice <= this.longitud() - 1 / 2){
            Nodo n = this._primero;
            for(int j = 0; j < indice;j++){
                n = n.sig;
            }
            n.valor = elem;
        } else {
            Nodo n = this._ultimo;
            for(int j = this.longitud() - 1; j > indice; j--){
                n = n.ant;
            }
            n.valor = elem;
        }
    }

    public ListaEnlazada(ListaEnlazada<T> lista) {
        Nodo p = new Nodo(lista._primero.valor);
        p.ant = lista._primero.ant;
        p.sig = lista._primero.sig;
        Nodo u = new Nodo(lista._ultimo.valor);
        u.ant = lista._ultimo.ant;
        u.sig = lista._ultimo.sig;
        this._primero = p;
        this._ultimo = u;
    }
    
    @Override
    public String toString() {
        Iterador<T> it = this.iterador();
        String res = "[";
        if (it.haySiguiente()) {
            res += it.siguiente();
        }
        while (it.haySiguiente()) {
            res += ", " + it.siguiente();
        }
        res += "]";
        return res;
    }
    
    private class ListaIterador implements Iterador<T> {
        private Nodo _puntero;
        private boolean _pasoUltimo;

        public ListaIterador(){
            this._puntero = _primero;
            this._pasoUltimo = false;
        }

        public boolean haySiguiente() {
            return this._puntero != null;
        }
        
        public boolean hayAnterior() {
            if (this._puntero == null) {
                return _ultimo != null;
            } else {
                return this._puntero.ant != null;
            }
        }

        public T siguiente() {
            T valor = this._puntero.valor;
            this._puntero = this._puntero.sig;
            if (this._puntero == null) {
                this._pasoUltimo = true;
            }
            return valor;
        }

        public T anterior() {
            if (this._puntero == null && this._pasoUltimo == true) {
                this._puntero = _ultimo;
                this._pasoUltimo = false;
            } else if (this._puntero != null && this._pasoUltimo == false) {                
               this._puntero = this._puntero.ant;
            } else {
                throw new NullPointerException("No hay elemento anterior");
            }
            return this._puntero.valor;
        }
    }

    public Iterador<T> iterador() {
	    return new ListaIterador();
    }

}