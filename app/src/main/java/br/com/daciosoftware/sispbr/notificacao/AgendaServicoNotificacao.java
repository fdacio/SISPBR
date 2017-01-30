package br.com.daciosoftware.sispbr.notificacao;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import br.com.daciosoftware.sispbr.configuracoes.NotificacaoDAO;

/**
 * Created by fdacio on 24/01/17.
 */
public class AgendaServicoNotificacao {

    public static final int TIME_START = 15;

    public AgendaServicoNotificacao() {
    }

    private PendingIntent getIntentService(Context context) {

        //Intent intent = new Intent("SORTEIO_SERVICE");
        Intent intent = new Intent(context, OrdemServicoService.class);
        int requstCode = 0;
        int flags = 0;
        PendingIntent pendingIntent = PendingIntent.getService(context, requstCode, intent, flags);
        return pendingIntent;
    }

    public void agendar(Context context) {
        /*
        Prepara o tempo para o agendamento
         */
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, TIME_START);
        long tempo = calendar.getTimeInMillis();

        long intervalo = new NotificacaoDAO(context).getIntevalo() * 60 * 1000;
        //long intervalo = 60 * 1000;

         /*
         Realiza o agendamento
         */
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, tempo, intervalo, getIntentService(context));
    }

    public void cancelarAgendamento(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(getIntentService(context));
    }

}
