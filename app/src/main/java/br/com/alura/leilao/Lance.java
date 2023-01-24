package br.com.alura.leilao;

import android.support.annotation.NonNull;

import java.io.Serializable;

import br.com.alura.leilao.model.Usuario;

public class Lance implements Serializable, Comparable {

    private final br.com.alura.leilao.model.Usuario usuario;
    private final double valor;

    public Lance(br.com.alura.leilao.model.Usuario usuario, double valor) {
        this.usuario = usuario;
        this.valor = valor;
    }

    public double getValor() {
        return valor;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        Lance lance = (Lance) o;
        return Double.compare(lance.getValor(), valor);
    }

    public Usuario getUsuario() {
        return usuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lance lance = (Lance) o;

        if (Double.compare(lance.valor, valor) != 0) return false;
        return usuario.equals(lance.usuario);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = usuario.hashCode();
        temp = Double.doubleToLongBits(valor);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
