package br.com.daciosoftware.sispbr.notificacao;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import br.com.daciosoftware.sispbr.R;

/**
 * Created by fdacio on 24/01/17.
 */
public class OrdemServicoNotificacao {

    private NotificationManager notificationManager;
    private Context context;
    private Class<?> activity;

    /**
     * @param context Context
     */
    public OrdemServicoNotificacao(Context context) {
        this.context = context;
        this.activity = OrdemServicoAbertaListActivity.class;
    }

    private NotificationCompat.Builder getNotificacaoPadrao() {

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        PendingIntent pendingIntent = PendingIntent.getActivity(this.context, 0, new Intent(context, activity), 0);
        long[] vibrate = new long[]{100, 250, 100, 500};

        return new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(this.context.getResources(), R.mipmap.ic_launcher))
                .setVibrate(vibrate)
                .setSound(soundUri)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
    }

    private NotificationCompat.Builder getNotificationBuilder(String mensagemBarraStatus, String titulo) {
        return getNotificacaoPadrao()
                .setTicker(mensagemBarraStatus + " " + titulo)
                .setContentTitle(mensagemBarraStatus)
                .setContentText(titulo);
    }

    /**
     * @param idNotificacao       in
     * @param mensagemBarraStatus String
     * @param titulo              String
     */
    public void notificar(int idNotificacao, String mensagemBarraStatus, String titulo) {
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(idNotificacao, getNotificationBuilder(mensagemBarraStatus, titulo).build());
    }

}
