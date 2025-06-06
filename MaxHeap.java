package aed;

import java.util.ArrayList;

public class MaxHeap<T extends Comparable<T>> {
    private ArrayList<T> _heap;

    public MaxHeap(){
        this._heap = new ArrayList<>();
    }

    public void insertar(T elem){
        this._heap.add(elem);
        this.siftUp(elem);
    }

    private void siftUp(T elem) {
        int i = this._heap.size() - 1;
        int indicePadre = (i - 1) / 2;
        while (i > 0 && elem.compareTo(this._heap.get(indicePadre)) > 0) {
            T padre = this._heap.get(indicePadre);
            this._heap.set(i, padre);
            this._heap.set(indicePadre, elem);
            i = indicePadre;
            indicePadre = (i - 1) / 2;
        }
    }

    private void siftDown(T elem){
        int i = 0;
        int indiceHijoIzq = 2*i + 1;
        int indiceHijoDer = 2*i + 2;
        T hijoIzq = this._heap.get(indiceHijoIzq);
        T hijoDer = this._heap.get(indiceHijoDer);

        int indiceHijoMax = indiceHijoIzq;
        if (indiceHijoDer < this._heap.size() && hijoDer.compareTo(hijoIzq) > 0) indiceHijoMax = indiceHijoDer;
        T hijoMax = this._heap.get(indiceHijoMax);
        
        while (i < this._heap.size() && hijoMax != null && hijoMax.compareTo(elem) > 0) {
            this._heap.set(indiceHijoMax, elem);
            this._heap.set(i, hijoMax);
            
            indiceHijoIzq = 2*i + 1;
            indiceHijoDer = 2*i + 2;
            hijoIzq = this._heap.get(indiceHijoIzq);
            hijoDer = this._heap.get(indiceHijoDer);

            indiceHijoMax = indiceHijoIzq;
            if (indiceHijoDer < this._heap.size() && hijoDer.compareTo(hijoIzq) > 0) indiceHijoMax = indiceHijoDer;
            hijoMax = this._heap.get(indiceHijoMax);

            i = indiceHijoMax;
        }
    }

    public T obtenerPrimero(){
        T primero = this._heap.get(0);
        T ultimo = this._heap.get(this._heap.size() - 1);
        this._heap.remove(ultimo);
        this._heap.set(0, ultimo);
        this.siftDown(this._heap.get(0));
        return primero;
    }

    public T verPrimero(){
        return this._heap.get(0);
    }
}