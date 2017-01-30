package br.com.daciosoftware.sispbr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.daciosoftware.sispbr.pojo.OrdemServico;
import br.com.daciosoftware.sispbr.util.MyDateUtil;

/**
 * Created by fdacio on 26/01/17.
 */
public class OrdemServicoListAdapter extends BaseAdapter {

    private Context context;
    private List<OrdemServico> lista;

    public OrdemServicoListAdapter(Context context, List<OrdemServico> lista) {
        this.context = context;
        this.lista = lista;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public OrdemServico getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        ViewHolder viewHolder;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_ordem_servico_adapter, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textViewNumero = (TextView) view.findViewById(R.id.textViewNumero);
            viewHolder.textViewData = (TextView) view.findViewById(R.id.textViewData);
            viewHolder.textViewStatus = (TextView) view.findViewById(R.id.textViewStatus);
            viewHolder.textViewUsuario = (TextView) view.findViewById(R.id.textViewUsuario);
            viewHolder.textViewLocal = (TextView) view.findViewById(R.id.textViewLocal);
            viewHolder.textViewPatrimonio = (TextView) view.findViewById(R.id.textViewPatrimonio);
            viewHolder.textViewDefeito = (TextView) view.findViewById(R.id.textViewDefeito);
            viewHolder.textViewDescricaoDefeito = (TextView) view.findViewById(R.id.textViewDescricaoDefeito);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        OrdemServico ordemServico = getItem(position);

        viewHolder.textViewNumero.setText(String.valueOf(ordemServico.getNumero()));
        viewHolder.textViewData.setText(MyDateUtil.calendarToDateTimeBr(ordemServico.getDataAbertura()));
        String status = (ordemServico.getStatus().equals("Finalizada")) ? ordemServico.getStatus() + String.format(view.getResources().getString(R.string.label_solucionada), ordemServico.getSolucionada()) : ordemServico.getStatus();
        viewHolder.textViewStatus.setText(status);
        viewHolder.textViewUsuario.setText(ordemServico.getUsuario());
        viewHolder.textViewLocal.setText(ordemServico.getLocal());
        viewHolder.textViewPatrimonio.setText(ordemServico.getPatrimonio());
        viewHolder.textViewDefeito.setText(ordemServico.getDefeito());
        viewHolder.textViewDescricaoDefeito.setText(ordemServico.getDescricaoDefeito());

        return view;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    private static class ViewHolder {
        TextView textViewNumero;
        TextView textViewData;
        TextView textViewStatus;
        TextView textViewUsuario;
        TextView textViewLocal;
        TextView textViewPatrimonio;
        TextView textViewDefeito;
        TextView textViewDescricaoDefeito;
    }


}
