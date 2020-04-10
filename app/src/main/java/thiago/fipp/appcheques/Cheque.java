package thiago.fipp.appcheques;

import java.util.List;

public class Cheque {

    private double valor;
    private long qtdDiasCobranca;
    private double juros;

    public Cheque(double valor, long qtdDiasCobranca, double juros) {
        this.valor = valor;
        this.qtdDiasCobranca = qtdDiasCobranca;
        this.juros = juros;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public long getQtdDiasCobranca() {
        return qtdDiasCobranca;
    }

    public void setQtdDiasCobranca(long qtdDiasCobranca) {
        this.qtdDiasCobranca = qtdDiasCobranca;
    }

    public double getJuros() {
        return juros;
    }

    public void setJuros(double juros) {
        this.juros = juros;
    }

    public String getTotalLiquido(List<Cheque> cheques) {

        double total = 0;
        for (int i = 0; i < cheques.size(); i++) {
            total += cheques.get(i).valor + cheques.get(i).juros;
        }

        return String.format("Total Liquido: R$ %10.2f", total);
    }
}
