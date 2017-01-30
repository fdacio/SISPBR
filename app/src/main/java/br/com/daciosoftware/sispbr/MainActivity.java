package br.com.daciosoftware.sispbr;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

import br.com.daciosoftware.sispbr.configuracoes.ConfiguracoesActivity;
import br.com.daciosoftware.sispbr.pojo.OrdemServico;
import br.com.daciosoftware.sispbr.util.DialogBox;
import br.com.daciosoftware.sispbr.util.DialogDate;
import br.com.daciosoftware.sispbr.util.MyDateUtil;
import br.com.daciosoftware.sispbr.webservice.SispbrWebService;

public class MainActivity extends AppCompatActivity {

    private ListView listViewOrdemServico;
    private TextView textViewEmpty;
    private Calendar dataAbertura;
    private List<OrdemServico> listaOrdemServico;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setLogo(R.mipmap.ic_launcher);
            toolbar.setTitle(getResources().getString(R.string.app_name));
            toolbar.setSubtitle(getResources().getString(R.string.label_ordens_servicos));
            setSupportActionBar(toolbar);
        }

        listViewOrdemServico = (ListView) findViewById(R.id.listViewOrdemServico);
        textViewEmpty = (TextView) findViewById(R.id.textViewEmpty);
        listViewOrdemServico.setEmptyView(findViewById(R.id.textViewEmpty));

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        Button buttonData = (Button) findViewById(R.id.buttonData);
        final DialogDate dialogDate = new DialogDate(buttonData, new DialogDate.OnAfterSelectDate() {
            @Override
            public void execute(Calendar dateSelected) {
                new ListaOrdemServicoTask().execute(dateSelected);
            }
        });

        buttonData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogDate.show();
            }
        });

        this.dataAbertura = dialogDate.getDate();
        buttonData.setText(MyDateUtil.calendarToDateBr(dataAbertura));
        new ListaOrdemServicoTask().execute(this.dataAbertura);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, ConfiguracoesActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
    Classe interna responsável por carregar os ordens de serviços para activity
     */
    private class ListaOrdemServicoTask extends AsyncTask<Calendar, Void, String> {

        @Override
        protected String doInBackground(Calendar... calendars) {

            Calendar dataAbertura = calendars[0];

            try {
                listaOrdemServico = new SispbrWebService(MainActivity.this).getOrdemServico(dataAbertura);
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
                listViewOrdemServico.setAdapter(new OrdemServicoListAdapter(MainActivity.this, listaOrdemServico));
            } else {
                textViewEmpty.setVisibility(View.VISIBLE);
                new DialogBox(MainActivity.this, DialogBox.DialogBoxType.INFORMATION, MainActivity.this.getResources().getString(R.string.web_service), retorno).show();
            }
        }

    }


}
