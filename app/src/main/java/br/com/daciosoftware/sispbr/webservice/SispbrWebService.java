package br.com.daciosoftware.sispbr.webservice;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.daciosoftware.sispbr.configuracoes.WebServiceDAO;
import br.com.daciosoftware.sispbr.internet.HttpConnection;
import br.com.daciosoftware.sispbr.pojo.OrdemServico;
import br.com.daciosoftware.sispbr.util.MyDateUtil;

/**
 * Created by fdacio on 14/10/16.
 */
public class SispbrWebService {

    private Context context;
    private String retornoJsonWebService;


    public SispbrWebService(Context context) {
        this.context = context;
    }

    private String getUrlRoot() {

        return new WebServiceDAO(context).getUrl();
    }

    private JSONObject getJSONObjectFromUrl(String url) throws IOException, JSONException {

        retornoJsonWebService = HttpConnection.getContentJSON(url);
        return new JSONObject(retornoJsonWebService);
    }

    private OrdemServico getOrdemServicoFromJSONObject(JSONObject jsonObject) throws JSONException, ParseException {

        int numero = jsonObject.getInt("Numero");
        Calendar data = MyDateUtil.dateTimeBrToCalendar(jsonObject.getString("Data"));
        String usuario = jsonObject.getString("Usuario");
        String status = jsonObject.getString("Status");
        String solucionada = jsonObject.getString("Solucionada");
        String local = jsonObject.getString("Local");
        String patrimonio = jsonObject.getString("Patrimonio");
        String defeito = jsonObject.getString("Defeito");
        String descricaoDefeito = jsonObject.getString("DescricaoDefeito");

        OrdemServico ordemServico = new OrdemServico();
        ordemServico.setNumero(numero);
        ordemServico.setDataAbertura(data);
        ordemServico.setUsuario(usuario);
        ordemServico.setStatus(status);
        ordemServico.setSolucionada(solucionada);
        ordemServico.setLocal(local);
        ordemServico.setPatrimonio(patrimonio);
        ordemServico.setDefeito(defeito);
        ordemServico.setDescricaoDefeito(descricaoDefeito);

        return ordemServico;

    }

    private List<OrdemServico> getListaFromArrayJson(String urlWebService)
            throws IOException, JSONException, ParseException {

        List<OrdemServico> listaOrdemServico = new ArrayList<>();

        JSONObject jsonObject = getJSONObjectFromUrl(urlWebService);

        if (jsonObject != null) {
            JSONArray jsonArray = jsonObject.optJSONArray("ordensServicos");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjectOs = jsonArray.getJSONObject(i);
                listaOrdemServico.add(getOrdemServicoFromJSONObject(jsonObjectOs));
            }
        }

        return listaOrdemServico;
    }

    /**
     * @param status Status da Ordem de Servico - 1 - Aberta; 2 - Em Serviço; 3 - Finalizada
     * @param numero Número da Ordem de Servico - Buscará as Ordens de Serviços com número maior que
     * @param data   Data de Abertura da Ordem de Serviço
     * @return Lista de Ordem de Serviço
     * @throws JSONException
     * @throws ParseException
     * @throws IOException
     */
    public List<OrdemServico> getOrdemServico(int status, int numero, Calendar data)
            throws JSONException, ParseException, IOException {

        String urlMetodo = String.format("/?class=OrdemServicoWS&method=getOrdemServico&status=%1$d&numero=%2$d&data=%3$s)",
                status,
                numero,
                MyDateUtil.calendarToDateBr(data));

        String urlWebService = getUrlRoot() + urlMetodo;

        //Log.i(Constantes.CATEGORIA, "URL de getOrdemServico:"+urlWebService);

        try {
            return getListaFromArrayJson(urlWebService);
        } catch (Exception e) {
            throw new RuntimeException(retornoJsonWebService);
        }

    }

    /**
     * @param data Data de Abertura da Ordem de Serviço
     * @return Lista de Ordem de Serviço
     * @throws JSONException
     * @throws ParseException
     * @throws IOException
     */
    public List<OrdemServico> getOrdemServico(Calendar data) throws JSONException, ParseException, IOException {

        String urlMetodo = String.format("/?class=OrdemServicoWS&method=getOrdemServico&data=%1$s)",
                MyDateUtil.calendarToDateBr(data));

        String urlWebService = getUrlRoot() + urlMetodo;

        try {
            return getListaFromArrayJson(urlWebService);
        } catch (Exception e) {
            throw new RuntimeException(retornoJsonWebService);
        }

    }
}