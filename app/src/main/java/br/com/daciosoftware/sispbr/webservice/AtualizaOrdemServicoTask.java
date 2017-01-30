package br.com.daciosoftware.sispbr.webservice;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import br.com.daciosoftware.sispbr.R;
import br.com.daciosoftware.sispbr.util.DialogBox;

/**
 * Created by Dácio Braga on 28/07/2016.
 */

public class AtualizaOrdemServicoTask extends AsyncTask<Void, String, String> {

    private Context context;

    private ProgressDialog progressDialog;


    public AtualizaOrdemServicoTask(Context context) {
        this.context = context;

    }


    @Override
    protected String doInBackground(Void... v) {

        SispbrWebService sorteioWebService = new SispbrWebService(context);


        return "OK";
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getResources().getString(R.string.msg_aguarde));
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(String retorno) {
        progressDialog.dismiss();
        if (!retorno.equals("OK")) {
            new DialogBox(context, DialogBox.DialogBoxType.INFORMATION, context.getResources().getString(R.string.msg_error), retorno).show();
        }
    }

}
