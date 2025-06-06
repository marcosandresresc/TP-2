package aed;

public class Usuario implements Comparable<Usuario>  {
    private int _id;
    private int _saldo;

    public Usuario(int id, int saldo){
        this._id = id;
        this._saldo = saldo;
    }

    public int id(){
        return this._id;
    }

    public int saldo(){
        return this._saldo;
    }

    @Override
    public int compareTo(Usuario otro) {
        return Integer.compare(this._saldo, otro.saldo());
    }

    @Override
    public boolean equals(Object otro){
        if (this == otro) return true;
        if (otro == null || this.getClass() != otro.getClass()) return false;
        Usuario u = (Usuario) otro;
        return this._id == u.id();
    }

}