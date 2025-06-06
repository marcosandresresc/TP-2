package aed;

import java.util.ArrayList;

public class Berretacoin {
    private ArrayList _blockchain;
    private ArrayList _usuarios;
    private MaxHeap _transacciones;
    private AVL _tenedores;
    private ArrayList _creadores;
    private float _montoMedioUltimoBloque;

    public Berretacoin(int n_usuarios){
        this._blockchain = new ArrayList<>();
        this._tenedores = new AVL<>();
        this._creadores = new ArrayList<>(3000);
        this._montoMedioUltimoBloque = 0;
        this._usuarios = new ArrayList<>(n_usuarios + 1);
        for (int i = 0; i < this._usuarios.size(); i++) {
            this._usuarios.add(new Usuario(i,0));
        }
    }

    public void agregarBloque(Transaccion[] transacciones){
        Bloque bloque = new Bloque(this._blockchain.size(), transacciones);
        this._blockchain.add(bloque);
    }

    public Transaccion txMayorValorUltimoBloque(){
        throw new UnsupportedOperationException("Implementar!");
    }

    public Transaccion[] txUltimoBloque(){
        throw new UnsupportedOperationException("Implementar!");
    }

    public int maximoTenedor(){
        throw new UnsupportedOperationException("Implementar!");
    }

    public int montoMedioUltimoBloque(){
        return Math.round(this._montoMedioUltimoBloque);
    }

    public void hackearTx(){
        throw new UnsupportedOperationException("Implementar!");
    }
    
}