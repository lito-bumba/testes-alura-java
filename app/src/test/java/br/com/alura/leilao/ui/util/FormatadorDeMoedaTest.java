package br.com.alura.leilao.ui.util;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

public class FormatadorDeMoedaTest {

    @Test
    public void formata() {
        FormatadorDeMoeda formatadorDeMoeda = new FormatadorDeMoeda();
        String moedaFormatada = formatadorDeMoeda.formata(200.0);
        assertThat(moedaFormatada, is(equalTo("R$ 200,00")));
    }
}