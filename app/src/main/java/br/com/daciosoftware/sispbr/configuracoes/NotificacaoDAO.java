package br.com.daciosoftware.sispbr.configuracoes;

import android.content.Context;
import android.content.SharedPreferences;

import br.com.daciosoftware.sispbr.util.Constantes;

/**
 * Created by fdacio on 21/01/17.
 */
public class NotificacaoDAO {

    private Context context;

    public NotificacaoDAO(Context context) {
        this.context = context;
    }

    public boolean isNotificar() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constantes.SHARED_PREF, 0);
        return sharedPreferences.getBoolean(Constantes.NOTIFICAR_OS, Constantes.NOTIFICAR_OS_DEFAULT);

    }

    public int getIntevalo() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constantes.SHARED_PREF, 0);
        return sharedPreferences.getInt(Constantes.NOTIFICAR_INTERVALO, Constantes.NOTIFICAR_INTERVALO_DEFAULT);
    }


    public int getUltimaOS() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constantes.SHARED_PREF, 0);
        return sharedPreferences.getInt(Constantes.NOTIFICAR_ULTIMA_OS, Constantes.NOTIFICAR_ULTIMA_OS_DEFAULT);
    }

    public int getUltimaOSListView() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constantes.SHARED_PREF, 0);
        return sharedPreferences.getInt(Constantes.NOTIFICAR_ULTIMA_OS_LISTVIEW, Constantes.NOTIFICAR_ULTIMA_OS__LISTVIEW_DEFAULT);
    }

    public void saveIntervalo(int intervalo) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constantes.SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(Constantes.NOTIFICAR_INTERVALO, intervalo);
        editor.apply();
    }

    public void saveIsNotificar(boolean isNotificar) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constantes.SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(Constantes.NOTIFICAR_OS, isNotificar);
        editor.apply();
    }

    public void saveUltimaOS(int numero) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constantes.SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(Constantes.NOTIFICAR_ULTIMA_OS, numero);
        editor.apply();
    }

    public void saveUltimaOSListView(int numero) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constantes.SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(Constantes.NOTIFICAR_ULTIMA_OS_LISTVIEW, numero);
        editor.apply();
    }

    public void saveDefaulValues() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constantes.SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(Constantes.NOTIFICAR_OS, Constantes.NOTIFICAR_OS_DEFAULT);
        editor.putInt(Constantes.NOTIFICAR_INTERVALO, Constantes.NOTIFICAR_INTERVALO_DEFAULT);
        editor.apply();
    }


}
