package br.com.alura.leilao.model;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.List;

public class LeilaoTest {

    // Criar Cenário do Teste
    public static final double DELTA = 0.0001;
    private final Leilao CONSOLE = new Leilao("Console");
    private final Usuario ALEX = new Usuario("Alex");

    @Test
    public void deve_DevolverDescricao_QuandoRecebeDescricao() {
        // Executar Ação Esperada
        String descricaoDevolvida = CONSOLE.getDescricao();

        // Testar Resultado Esperado
        assertEquals("Console", descricaoDevolvida);
    }

    @Test
    public void deve_DevolverMaiorLance_QuandoRecebeApenasUmLance() {
        CONSOLE.propoe(new Lance(ALEX, 200.0));
        double maiorLanceDevolvido = CONSOLE.getMaiorLance();
        assertEquals(200.0, maiorLanceDevolvido, DELTA);
    }

    @Test
    public void deve_DevolverMaiorLance_QuandoRecebeMaisDeUmLanceEmOrdemCrescente() {
        CONSOLE.propoe(new Lance(ALEX, 100.0));
        CONSOLE.propoe(new Lance(new Usuario("Fran"), 200.0));
        double lanceDevolvido = CONSOLE.getMaiorLance();
        assertEquals(200.0, lanceDevolvido, DELTA);
    }

    @Test
    public void deve_DevolverMaiorLance_QuandoRecebeMaisDeUmLanceEmOrdemDecrescente() {
        CONSOLE.propoe(new Lance(ALEX, 10000.0));
        CONSOLE.propoe(new Lance(new Usuario("Fran"), 9000.0));
        double lanceDevolvido = CONSOLE.getMaiorLance();
        assertEquals(10000.0, lanceDevolvido, DELTA);
    }

    @Test
    public void deve_DevolverMenorLance_QuandoRecebeApenasUmLance() {
        CONSOLE.propoe(new Lance(ALEX, 200.0));
        double lanceDevolvido = CONSOLE.getMenorLance();
        assertEquals(200.0, lanceDevolvido, DELTA);
    }

    @Test
    public void deve_DevolverMenorLance_QuandoRecebeMaisDeUmLanceEmOrdemCrescente() {
        CONSOLE.propoe(new Lance(ALEX, 100.0));
        CONSOLE.propoe(new Lance(new Usuario("Fran"), 200.0));
        double lanceDevolvido = CONSOLE.getMenorLance();
        assertEquals(100.0, lanceDevolvido, DELTA);
    }

    @Test
    public void deve_DevolverMenorLance_QuandoRecebeMaisDeUmLanceEmOrdemDecrescente() {
        CONSOLE.propoe(new Lance(ALEX, 10000.0));
        CONSOLE.propoe(new Lance(new Usuario("Fran"), 9000.0));
        double lanceDevolvido = CONSOLE.getMenorLance();
        assertEquals(9000.0, lanceDevolvido, DELTA);
    }

    @Test
    public void deve_DevolverTresMaioresLances_QuandoReceberExatosTresLances() {
        CONSOLE.propoe(new Lance(ALEX, 200.0));
        CONSOLE.propoe(new Lance(new Usuario("Fran"), 300.0));
        CONSOLE.propoe(new Lance(ALEX, 400.0));

        List<Lance> lancesDevolvidos = CONSOLE.tresMaioresLances();
        assertEquals(3, lancesDevolvidos.size());
        assertEquals(400.0, lancesDevolvidos.get(0).getValor(), DELTA);
        assertEquals(300.0, lancesDevolvidos.get(1).getValor(), DELTA);
        assertEquals(200.0, lancesDevolvidos.get(2).getValor(), DELTA);
    }

    @Test
    public void deve_DevolverTresMaioresLances_QuandoNaoRecebeLances() {
        List<Lance> lancesDevolvidos = CONSOLE.tresMaioresLances();
        assertEquals(0, lancesDevolvidos.size());
    }

    @Test
    public void deve_DevolverTresMaioresLances_QuandoRecebeApenasUmLance() {
        CONSOLE.propoe(new Lance(ALEX, 200.0));
        List<Lance> lancesDevolvidos = CONSOLE.tresMaioresLances();

        assertEquals(1, lancesDevolvidos.size());
        assertEquals(200.0, lancesDevolvidos.get(0).getValor(), DELTA);
    }

    @Test
    public void deve_DevolverTresMaioresLances_QuandoRecebeApenasDoisLances() {
        CONSOLE.propoe(new Lance(ALEX, 300.0));
        CONSOLE.propoe(new Lance(new Usuario("Fran"), 400.0));

        List<Lance> lancesDevolvidos = CONSOLE.tresMaioresLances();
        assertEquals(2, lancesDevolvidos.size());
        assertEquals(400.0, lancesDevolvidos.get(0).getValor(), DELTA);
        assertEquals(300.0, lancesDevolvidos.get(1).getValor(), DELTA);
    }

    @Test
    public void deve_DevolverTresMaioresLances_QuandoRecebeMaisDeTresLances() {
        final Usuario FRAN = new Usuario("Fran");
        CONSOLE.propoe(new Lance(ALEX, 300.0));
        CONSOLE.propoe(new Lance(FRAN, 400.0));
        CONSOLE.propoe(new Lance(ALEX, 500.0));
        CONSOLE.propoe(new Lance(FRAN, 600.0));

        List<Lance> lancesDevolvidos = CONSOLE.tresMaioresLances();
        assertEquals(3, lancesDevolvidos.size());
        assertEquals(600, lancesDevolvidos.get(0).getValor(), DELTA);
        assertEquals(500, lancesDevolvidos.get(1).getValor(), DELTA);
        assertEquals(400, lancesDevolvidos.get(2).getValor(), DELTA);

        CONSOLE.propoe(new Lance(ALEX, 700.0));
        final List<Lance> lancesDevolvidosMaisUm = CONSOLE.tresMaioresLances();
        assertEquals(3, lancesDevolvidosMaisUm.size());
        assertEquals(700.0, lancesDevolvidosMaisUm.get(0).getValor(), DELTA);
        assertEquals(600.0, lancesDevolvidosMaisUm.get(1).getValor(), DELTA);
        assertEquals(500.0, lancesDevolvidosMaisUm.get(2).getValor(), DELTA);
    }

}