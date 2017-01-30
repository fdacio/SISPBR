package br.com.daciosoftware.sispbr.configuracoes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import br.com.daciosoftware.sispbr.R;
import br.com.daciosoftware.sispbr.notificacao.AgendaServicoNotificacao;
import br.com.daciosoftware.sispbr.util.DialogNumber;
import br.com.daciosoftware.sispbr.util.NumberPickerDialog;

public class NotificacaoEditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacao_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Switch switch1 = (Switch) findViewById(R.id.switch1);
        boolean isNotificar = new NotificacaoDAO(this).isNotificar();
        switch1.setChecked(isNotificar);

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                               @Override
                                               public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                                   new NotificacaoDAO(buttonView.getContext()).saveIsNotificar(isChecked);

                                                   if (isChecked) {
                                                       new AgendaServicoNotificacao().agendar(NotificacaoEditActivity.this);
                                                   } else {
                                                       new AgendaServicoNotificacao().cancelarAgendamento(NotificacaoEditActivity.this);
                                                   }

                                               }
                                           }
        );

        final Button buttonIntervalo = (Button) findViewById(R.id.buttonIntervalo);
        buttonIntervalo.setText(String.format(getResources().getString(R.string.label_notificacao3_edit), new NotificacaoDAO(this).getIntevalo()));


        buttonIntervalo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogNumber dialogNumber = new DialogNumber((Button) view,
                        R.layout.dialog_number_picker,
                        new NumberPickerDialog.OnNumberSetListener() {
                            @Override
                            public void onNumberSet(int number) {
                                new NotificacaoDAO(NotificacaoEditActivity.this).saveIntervalo(number);
                                new AgendaServicoNotificacao().agendar(NotificacaoEditActivity.this);

                                buttonIntervalo.setText(String.format(getResources().getString(R.string.label_notificacao3_edit), number));
                            }
                        }
                );

                dialogNumber.setTitle(getResources().getString(R.string.label_notificacao2_edit));
                dialogNumber.setStartValue(new NotificacaoDAO(NotificacaoEditActivity.this).getIntevalo());
                dialogNumber.setMinValue(5);
                dialogNumber.setMaxValue(120);
                dialogNumber.show();


            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
