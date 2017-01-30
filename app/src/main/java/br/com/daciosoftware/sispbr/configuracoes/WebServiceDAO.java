package br.com.daciosoftware.sispbr.configuracoes;

import android.content.Context;
import android.content.SharedPreferences;

import br.com.daciosoftware.sispbr.util.Constantes;

/**
 * Created by fdacio on 01/09/16.
 */
public class WebServiceDAO {

    private Context context;


    public WebServiceDAO(Context context) {
        this.context = context;
    }


    public String getUrl() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constantes.SHARED_PREF, 0);
        return sharedPreferences.getString(Constantes.URL_WEB_SERVICE, Constantes.URL_WEB_SERVICE_DEFAULT);
    }


    public void save(String url) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constantes.SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constantes.URL_WEB_SERVICE, url);
        editor.apply();
    }


    public void saveDefaulValues() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constantes.SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constantes.URL_WEB_SERVICE, Constantes.URL_WEB_SERVICE_DEFAULT);
        editor.apply();
    }

}