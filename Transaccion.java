package aed;

public class Transaccion implements Comparable<Transaccion> {
    private int _id;
    private int _idComprador;
    private int _idVendedor;
    private int _monto;

    public Transaccion(int id, int id_comprador, int id_vendedor, int monto) {
        this._id = id;
        this._idComprador = id_comprador;
        this._idVendedor = id_vendedor;
        this._monto = monto;
    }

    @Override
    public int compareTo(Transaccion otro) {
        if (this._monto != otro._monto) {
            return Integer.compare(this._monto, otro._monto);
        } else {
            return Integer.compare(this._id, otro._id);
        }
    }

    @Override
    public boolean equals(Object otro){
        if (this == otro) return true;
        if (otro == null || this.getClass() != otro.getClass()) return false;
        Transaccion t = (Transaccion) otro;
        return this._id == t.id();
    }

    public int id(){
        return this._id;
    }

    public int monto() {
        return this._monto;
    }

    public int id_comprador() {
        return this._idComprador;
    }
    
    public int id_vendedor() {
        return this._idVendedor;
    }
}