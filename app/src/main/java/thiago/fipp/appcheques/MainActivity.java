package thiago.fipp.appcheques;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SeekBar sbJuros;
    private TextView tvJuros;
    private NumberPicker npDias, npMeses, npAnos;
    private EditText etValor;
    private Button btIncluir;
    private ListView listview;
    private List<Cheque> cheques;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cheques = new ArrayList<>();

        sbJuros = findViewById(R.id.sbJuros);
        tvJuros = findViewById(R.id.tvJuros);

        npDias = findViewById(R.id.npDias);
        npMeses = findViewById(R.id.npMeses);
        npAnos = findViewById(R.id.npAnos);

        iniciarDataDoCheque();

        etValor = findViewById(R.id.etValor);
        btIncluir = findViewById(R.id.btIncluir);
        listview = findViewById(R.id.listview);

        sbJuros.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                double pg = (progress + 1) * 0.1;
                tvJuros.setText(String.format("Juros: %10.1f", pg) + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btIncluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                montarLista();
            }
        });

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                cheques.remove(position);
                ((ChequeAdapter) listview.getAdapter()).notifyDataSetChanged();
                return true;
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, cheques.get(position).getTotalLiquido(cheques),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void iniciarDataDoCheque() {
        npDias.setMinValue(1);
        npDias.setMaxValue(31);
        npDias.setWrapSelectorWheel(true);

        final String[] meses = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
        npMeses.setMinValue(0);
        npMeses.setMaxValue(meses.length - 1);
        npMeses.setDisplayedValues(meses);
        npMeses.setWrapSelectorWheel(true);

        npAnos.setMinValue(2020);
        npAnos.setMaxValue(2030);
        npAnos.setWrapSelectorWheel(true);

        Date date = new Date();
        npDias.setValue(date.getDay());
        npMeses.setValue(date.getMonth());
        npAnos.setValue(date.getYear() + 1900);
    }

    private void montarLista() {
        double juros = (sbJuros.getProgress() + 1) * 0.1;
        int dia = npDias.getValue();
        int mes = npMeses.getValue();
        int ano = npAnos.getValue() - 1900;
        double valorCheque = Double.parseDouble(etValor.getText().toString());

        Date dataDe = new Date();
        Date dataAte = new Date(ano, mes, dia);

        long diferencaDias = (dataAte.getTime() - dataDe.getTime()) / (1000 * 60 * 60 * 24);
        double jurosPeriodo = ((juros / 30) * diferencaDias) * 0.01;
        double jurosCheque = valorCheque * jurosPeriodo;
        cheques.add(new Cheque(valorCheque, diferencaDias, jurosCheque));
        listview.setAdapter(new ChequeAdapter(this, R.layout.bloco_lista, cheques));

        iniciarDataDoCheque();
        etValor.setText("");
    }
}
