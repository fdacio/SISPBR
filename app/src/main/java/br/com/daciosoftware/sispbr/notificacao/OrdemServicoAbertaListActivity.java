package br.com.daciosoftware.sispbr.notificacao;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

import br.com.daciosoftware.sispbr.OrdemServicoListAdapter;
import br.com.daciosoftware.sispbr.R;
import br.com.daciosoftware.sispbr.configuracoes.NotificacaoDAO;
import br.com.daciosoftware.sispbr.pojo.OrdemServico;
import br.com.daciosoftware.sispbr.util.DialogBox;
import br.com.daciosoftware.sispbr.webservice.SispbrWebService;

public class OrdemServicoAbertaListActivity extends AppCompatActivity {

    private static final int STATUS_ABERTA = 1;

    private List<OrdemServico> listaOrdemServico;
    private TextView textViewEmpty;
    private ListView listViewOrdemServico;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordem_servico_aberta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setLogo(R.mipmap.ic_launcher);
            toolbar.setTitle(getResources().getString(R.string.app_name));
            toolbar.setSubtitle(getResources().getString(R.string.title_activity_ordem_servico_aberta));
            setSupportActionBar(toolbar);
        }

        listViewOrdemServico = (ListView) findViewById(R.id.listViewOrdemServico);
        textViewEmpty = (TextView) findViewById(R.id.emptyElement);
        listViewOrdemServico.setEmptyView(findViewById(R.id.textViewEmpty));

        progressBar = (ProgressBar) findViewById(R.id.progressBar2);


        new ListaOrdemServicoTask().execute();

    }

    /*
    Classe interna responsável por carregar os ordens de serviços para activity
     */
    private class ListaOrdemServicoTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {

            Calendar dataAbertura = Calendar.getInstance();

            int numeroOS = new NotificacaoDAO(OrdemServicoAbertaListActivity.this).getUltimaOSListView();

            try {
                listaOrdemServico = new SispbrWebService(OrdemServicoAbertaListActivity.this).getOrdemServico(STATUS_ABERTA, numeroOS, dataAbertura);
            } catch (JSONException | ParseException | IOException | RuntimeException e) {
                return e.getMessage();
            }

            return "OK";
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            listViewOrdemServico.setAdapter(null);
            progressBar.setVisibility(View.VISIBLE);
            textViewEmpty.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onPostExecute(String retorno) {

            progressBar.setVisibility(View.INVISIBLE);

            if (retorno.equals("OK")) {
                listViewOrdemServico.setAdapter(new OrdemServicoListAdapter(OrdemServicoAbertaListActivity.this, listaOrdemServico));
                new NotificacaoDAO(OrdemServicoAbertaListActivity.this).saveUltimaOSListView(new NotificacaoDAO(OrdemServicoAbertaListActivity.this).getUltimaOS());
            } else {
                textViewEmpty.setVisibility(View.VISIBLE);
                new DialogBox(OrdemServicoAbertaListActivity.this, DialogBox.DialogBoxType.INFORMATION, OrdemServicoAbertaListActivity.this.getResources().getString(R.string.web_service), retorno).show();
            }
        }

    }
}
