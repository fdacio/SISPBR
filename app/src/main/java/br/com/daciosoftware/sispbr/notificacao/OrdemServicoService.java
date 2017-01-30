package br.com.daciosoftware.sispbr.notificacao;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import br.com.daciosoftware.sispbr.R;
import br.com.daciosoftware.sispbr.configuracoes.NotificacaoDAO;
import br.com.daciosoftware.sispbr.pojo.OrdemServico;
import br.com.daciosoftware.sispbr.webservice.SispbrWebService;

/**
 * Created by fdacio on 20/01/17.
 */
public class OrdemServicoService extends Service implements Runnable {

    private static final int STATUS_ABERTA = 1;
    private Context context;

    @Override
    public void onCreate() {
        this.context = this;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        new Thread(this).start();
        return super.onStartCommand(intent, flags, startId);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void run() {

        //Log.i(Constantes.CATEGORIA, "OrdemServicoService run(): "+ MyDateUtil.calendarToDateTimeBr(Calendar.getInstance()));

        List<OrdemServico> listaOrdemServico;

        int numeroOS = new NotificacaoDAO(context).getUltimaOS();
        Calendar dataAbertura = Calendar.getInstance();

        try {

            listaOrdemServico = new SispbrWebService(context).getOrdemServico(STATUS_ABERTA, numeroOS, dataAbertura);

        } catch (Exception e) {
            e.printStackTrace();
            stopSelf();
            return;
        }

        //Aqui enviar uma notificação para o usuario informado
        //que há Novo Sorteio a ser atualizado
        if (listaOrdemServico.size() > 0) {

            int ultimaOSAberta = listaOrdemServico.get(0).getNumero();
            int totalOSAbertas = listaOrdemServico.size();

            new NotificacaoDAO(context).saveUltimaOS(ultimaOSAberta);

            String mensagemBarraStatus = context.getResources().getString(R.string.app_name);

            String titulo = (totalOSAbertas > 1) ? String.format(Locale.getDefault(), "%1$d Novas Ordens de Serviços Abertas", totalOSAbertas) : String.format(Locale.getDefault(), "%1$d Nova Ordem de Serviço Aberta", totalOSAbertas);
            int idNotificacao = 10000 * ultimaOSAberta;

            new OrdemServicoNotificacao(context).notificar(idNotificacao, mensagemBarraStatus, titulo);

        }

        stopSelf();
    }


}
