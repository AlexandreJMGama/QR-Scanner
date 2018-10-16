package br.edu.ead.ifrn.qrscanner;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView tvCodigo;
    Button btnScan;
    ListView lvLista;
    ControlScanDB controlScanDB;
    AlertDialog alertDialog;
//    ArrayAdapter<String> adapter;
    QRListAdapter adapter;
    List<QR> qrs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controlScanDB = new ControlScanDB(this);

        btnScan = (Button) findViewById(R.id.btnScan);
        tvCodigo = (TextView) findViewById(R.id.tvCodigo);
        lvLista = (ListView) findViewById(R.id.lvLista);

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Scan All Code Types");
                integrator.setCameraId(0);  // Use a specific camera of the device
                integrator.setBeepEnabled(true);
                integrator.setBarcodeImageEnabled(true);
                integrator.initiateScan();
            }
        });
//        Carrega lista simplis
//        List<String> itens = controlScanDB.carregaItens();
//        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, (List<String>) itens);

        qrs = controlScanDB.carregarLista();
        adapter = new QRListAdapter(this, qrs);

        lvLista.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                tvCodigo.setText("Cancelado!");
            } else {
                tvCodigo.setText(result.getContents());
                saveCode(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void saveCode(final String codigo){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final EditText editTitulo = new EditText(this);
        builder.setTitle("Salvar QR?")
                .setMessage("Titulo:")
                .setCancelable(false)
                .setView(editTitulo);

        builder.setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                controlScanDB.inserir(editTitulo.getText().toString(), codigo);
                qrs.add(new QR(qrs.size()+1, editTitulo.getText().toString(), codigo));
                adapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tvCodigo.setText("Somente escaneado \n"+codigo);
            }
        });

        alertDialog = builder.create();
        alertDialog.show();
    }
}
