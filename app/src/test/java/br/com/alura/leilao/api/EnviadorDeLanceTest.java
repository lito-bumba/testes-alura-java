package br.com.alura.leilao.api;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import br.com.alura.leilao.api.retrofit.client.RespostaListener;
import br.com.alura.leilao.api.retrofit.client.LeilaoWebClient;
import br.com.alura.leilao.api.retrofit.client.RespostaListener;
import br.com.alura.leilao.model.LeilaoTest;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.model.Lance;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.ui.dialog.AvisoDialogManager;
import br.com.alura.leilao.exception.UsuarioJaDeuCincoLances;
import br.com.alura.leilao.exception.LanceMenorQueUltimoLance;
import br.com.alura.leilao.exception.LanceSeguidoDoMesmoUsuario;

import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class EnviadorDeLanceTest {

    @Mock
    private LeilaoWebClient client;
    @Mock
    private EnviadorDeLance.LanceProcessadoListener listener;
    @Mock
    private AvisoDialogManager manager;
    @Mock
    private Leilao leilao;

    @Test
    public void deve_MostrarMensagemDeFalha_QuandoLanceForMenorQueUltimoLance() {
        EnviadorDeLance enviador = new EnviadorDeLance(client, listener, manager);
        doThrow(LanceMenorQueUltimoLance.class).when(leilao).propoe(any(Lance.class));

        enviador.envia(leilao, new Lance(new Usuario("Fran"), 100.0));

        verify(manager).mostraAvisoLanceMenorQueUltimoLance();
    }

    @Test
    public void deve_MostrarMensagemDeFalha_QuandoUsuarioComCincoDerNovoLance() {
        EnviadorDeLance enviador = new EnviadorDeLance(client, listener, manager);
        doThrow(UsuarioJaDeuCincoLances.class).when(leilao).propoe(any(Lance.class));

        enviador.envia(leilao, new Lance(new Usuario("Alex"), 200.0));

        verify(manager).mostraAvisoUsuarioJaDeuCincoLances();
    }

    @Test
    public void deve_MostrarMensagemDeFalha_QuandoOUsuarioDoUltimoLanceDerNovoLance() {
        EnviadorDeLance enviador = new EnviadorDeLance(client, listener, manager);
        doThrow(LanceSeguidoDoMesmoUsuario.class).when(leilao).propoe(any(Lance.class));

        enviador.envia(leilao, new Lance(new Usuario("Alex"), 200.0));

        verify(manager).mostraAvisoLanceSeguidoDoMesmoUsuario();
    }

    @Test
    public void deve_MostrarMensagemDeFalha_QuandoFalharEnvioDeLanceParaAPI(){
        EnviadorDeLance enviador = new EnviadorDeLance(client, listener, manager);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                RespostaListener<Void> argument = invocation.getArgument(2);
                argument.falha("");
                return null;
            }
        }).when(client).propoe(any(Lance.class), anyLong(), any(RespostaListener.class));

        enviador.envia(leilao, new Lance(new Usuario("Alex"), 200.0));

        verify(manager).mostraToastFalhaNoEnvio();
        verify(listener, never()).processado(leilao);
    }

    @Test
    public void deve_MostrarMensagemDeFalha_QuandoEnviarLanceParaAPIComSucesso(){
        EnviadorDeLance enviador = new EnviadorDeLance(client, listener, manager);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                RespostaListener<Void> argument = invocation.getArgument(2);
                argument.sucesso(any(Void.class));
                return null;
            }
        }).when(client).propoe(any(Lance.class), anyLong(), any(RespostaListener.class));

        enviador.envia(leilao, new Lance(new Usuario("Alex"), 200.0));

        verify(listener).processado(leilao);
        verify(manager, never()).mostraToastFalhaNoEnvio();
    }

}