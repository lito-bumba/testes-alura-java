package br.com.alura.leilao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.alura.leilao.exception.LanceMenorQueUltimoLance;
import br.com.alura.leilao.exception.LanceSeguidoDoMesmoUsuario;
import br.com.alura.leilao.exception.UsuarioJaDeuCincoLances;
import br.com.alura.leilao.model.Lance;
import br.com.alura.leilao.model.Usuario;

public class Leilao implements Serializable {

    private final long id;
    private final String descricao;
    private final List<Lance> lances;

    public Leilao(String descricao) {
        this.id = 0L;
        this.descricao = descricao;
        this.lances = new ArrayList<>();
    }

    public void propoe(Lance lance) {
        valida(lance);
        lances.add(lance);
        Collections.sort(lances);
    }

    private void valida(Lance lance) {
        double valorLance = lance.getValor();
        if (lanceForMenorQueOUltimoLance(valorLance))
            throw new LanceMenorQueUltimoLance();
        if (temLances()) {
            br.com.alura.leilao.model.Usuario usuarioNovo = lance.getUsuario();
            if (usuarioForOMesmoDoUltimoLance(usuarioNovo))
                throw new LanceSeguidoDoMesmoUsuario();
            if (usuarioDeuCincoLances(usuarioNovo))
                throw new UsuarioJaDeuCincoLances();
        }
    }

    private boolean temLances() {
        return !lances.isEmpty();
    }

    private boolean usuarioDeuCincoLances(br.com.alura.leilao.model.Usuario usuarioNovo) {
        int lancesDoUsuario = 0;
        for (Lance l :
                lances) {
            br.com.alura.leilao.model.Usuario usuarioExistente = l.getUsuario();
            if (usuarioExistente.equals(usuarioNovo)) {
                lancesDoUsuario++;
                if (lancesDoUsuario == 5) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean usuarioForOMesmoDoUltimoLance(br.com.alura.leilao.model.Usuario usuarioNovo) {
        Usuario ultimoUsuario = lances.get(0).getUsuario();
        return usuarioNovo.equals(ultimoUsuario);
    }

    private boolean lanceForMenorQueOUltimoLance(double valorLance) {
        return getMaiorLance() > valorLance;
    }

    public double getMenorLance() {
        if (lances.isEmpty()) {
            return 0.0;
        }
        return lances.get(lances.size() - 1).getValor();
    }

    public double getMaiorLance() {
        if (lances.isEmpty()) {
            return 0.0;
        }
        return lances.get(0).getValor();
    }

    public String getDescricao() {
        return descricao;
    }

    public List<Lance> tresMaioresLances() {
        int quantidadeMaximaLances = lances.size();
        if (quantidadeMaximaLances > 3) {
            quantidadeMaximaLances = 3;
        }
        return lances.subList(0, quantidadeMaximaLances);
    }

    public Long getId() {
        return id;
    }

}