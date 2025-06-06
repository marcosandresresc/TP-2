package aed;

import java.util.ArrayList;

public class Bloque {
    private int _id;
    private Transaccion[] _transacciones; // ordenadas por id
    //HeapMax<Transaccion> heapTXS;  
        //La transacción con mayor monto está en la raíz.
        //Si hay varias con el mismo monto, se elige la de mayor id.

    public Bloque (int id, Transaccion[] transaccions){
        this._id = id;
        this._transacciones = transaccions;
    }
}
