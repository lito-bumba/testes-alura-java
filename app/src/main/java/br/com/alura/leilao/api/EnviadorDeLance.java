package br.com.alura.leilao.api;


import android.content.Context;

import br.com.alura.leilao.api.retrofit.client.LeilaoWebClient;
import br.com.alura.leilao.api.retrofit.client.RespostaListener;
import br.com.alura.leilao.exception.LanceMenorQueUltimoLance;
import br.com.alura.leilao.exception.LanceSeguidoDoMesmoUsuario;
import br.com.alura.leilao.exception.UsuarioJaDeuCincoLances;
import br.com.alura.leilao.model.Lance;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.ui.dialog.AvisoDialogManager;

public class EnviadorDeLance {

    private final LeilaoWebClient client;
    private final LanceProcessadoListener listener;
    private final AvisoDialogManager manager;

    public EnviadorDeLance(LeilaoWebClient client,
                           LanceProcessadoListener listener,
                           AvisoDialogManager manager) {
        this.client = client;
        this.listener = listener;
        this.manager = manager;
    }

    public void envia(final Leilao leilao, Lance lance) {
        try {
            leilao.propoe(lance);
            client.propoe(lance, leilao.getId(), new RespostaListener<Void>() {
                @Override
                public void sucesso(Void resposta) {
                    listener.processado(leilao);
                }

                @Override
                public void falha(String mensagem) {
                    manager.mostraToastFalhaNoEnvio();
                }
            });
        } catch (LanceMenorQueUltimoLance exception) {
            manager.mostraAvisoLanceMenorQueUltimoLance();
        } catch (LanceSeguidoDoMesmoUsuario exception) {
            manager.mostraAvisoLanceSeguidoDoMesmoUsuario();
        } catch (UsuarioJaDeuCincoLances exception) {
            manager.mostraAvisoUsuarioJaDeuCincoLances();
        }
    }

    public interface LanceProcessadoListener {
        void processado(Leilao leilao);
    }

}
