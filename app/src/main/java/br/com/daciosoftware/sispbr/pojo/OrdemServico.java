package br.com.daciosoftware.sispbr.pojo;

import java.util.Calendar;

/**
 * Created by fdacio on 27/01/17.
 */
public class OrdemServico {

    private int numero;
    private Calendar dataAbertura;
    private String usuario;
    private String status;
    private String solucionada;
    private String local;
    private String patrimonio;
    private String Defeito;
    private String DescricaoDefeito;

    public OrdemServico() {
    }


    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Calendar getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(Calendar dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSolucionada() {
        return solucionada;
    }

    public void setSolucionada(String solucionada) {
        this.solucionada = solucionada;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getPatrimonio() {
        return patrimonio;
    }

    public void setPatrimonio(String patrimonio) {
        this.patrimonio = patrimonio;
    }

    public String getDefeito() {
        return Defeito;
    }

    public void setDefeito(String defeito) {
        Defeito = defeito;
    }

    public String getDescricaoDefeito() {
        return DescricaoDefeito;
    }

    public void setDescricaoDefeito(String descricaoDefeito) {
        DescricaoDefeito = descricaoDefeito;
    }

    @Override
    public String toString() {
        return "OrdemServico{" +
                "numero=" + numero +
                ", dataAbertura=" + dataAbertura +
                ", usuario='" + usuario + '\'' +
                ", local='" + local + '\'' +
                ", patrimonio='" + patrimonio + '\'' +
                ", Defeito='" + Defeito + '\'' +
                ", DescricaoDefeito='" + DescricaoDefeito + '\'' +
                '}';
    }
}
