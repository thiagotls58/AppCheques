package thiago.fipp.appcheques;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ChequeAdapter extends ArrayAdapter<Cheque> {
    private int layout;

    public ChequeAdapter(Context context, int resource, List<Cheque> cheques) {
        super(context, resource, cheques);
        layout = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)
                    getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(this.layout, parent, false);
        }

        TextView tvValorC = convertView.findViewById(R.id.tvValorC);
        TextView tvDiasC = convertView.findViewById(R.id.tvDiasC);
        TextView tvJurosC = convertView.findViewById(R.id.tvJurosC);

        Cheque cheque = this.getItem(position);
        tvValorC.setText(String.format("%10.2f", cheque.getValor()));
        tvDiasC.setText(String.format("%02d", cheque.getQtdDiasCobranca()));
        tvJurosC.setText(String.format("%10.2f", cheque.getJuros()));

        return convertView;
    }
}
