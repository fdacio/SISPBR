package br.com.daciosoftware.sispbr.notificacao;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import br.com.daciosoftware.sispbr.configuracoes.NotificacaoDAO;

/**
 * Created by fdacio on 20/01/17.
 */
public class AgendaServicoBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //Agendar o OrdemServicoService de busca de sorteio no webservice
        //15 segundos após o boot do sistema operacional,
        //agenda um alarme para a execução da classe OrdemServicoService
        if (new NotificacaoDAO(context).isNotificar()) {
            new AgendaServicoNotificacao().agendar(context);
        }

    }


}
