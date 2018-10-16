package br.edu.ead.ifrn.qrscanner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class QRListAdapter extends BaseAdapter {

    Context context;
    List<QR> qrs;

    public QRListAdapter(Context context, List<QR> qrs){
        this.context = context;
        this.qrs = qrs;
    }

    @Override
    public int getCount() {
        return qrs.size();
    }

    @Override
    public Object getItem(int position) {
        return qrs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        QR qr = qrs.get(position);

        View view = LayoutInflater.from(context).inflate(R.layout.minha_lista, null);
        TextView id = (TextView) view.findViewById(R.id.list_ID);
        TextView titulo = (TextView) view.findViewById(R.id.list_title);
        TextView codigo = (TextView) view.findViewById(R.id.list_codigo);

        id.setText(qr.id+".");
        titulo.setText(qr.titulo);
        codigo.setText(qr.codigo);

        return view;
    }
}
