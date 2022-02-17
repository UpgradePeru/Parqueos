package pe.upgrade.parqueo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Application;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import pe.upgrade.canchas.R;
import pe.upgrade.parqueo.model.Model;
import pe.upgrade.parqueo.model.api.AbstractAPIListener;
import pe.upgrade.parqueo.model.api.Cancha;
import pe.upgrade.parqueo.model.api.Distrito;
import pe.upgrade.parqueo.model.api.User;

public class MainStdActivity extends AppCompatActivity {

    Application application = this.getApplication();
    Model model = Model.getInstance(application);
    List<Distrito> distritos = model.getDistritos();
    //final List<Cancha> canchas = model.getCanchas();

    TextInputLayout txtDistrito;
    TextInputLayout txtFecha;
    EditText editFecha;
    DatePickerDialog.OnDateSetListener setListener;

    AutoCompleteTextView autoCompleteDistritos;
    AutoCompleteTextView autoCompleteFechas;

    int distritoId;
    String fecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_std);

        setTitle("BUSCAR ESTACIONAMIENTO");

        txtDistrito = findViewById(R.id.txtDistrito);
        autoCompleteDistritos = findViewById(R.id.autoCompleteDistritos);

        txtFecha = findViewById(R.id.txtFecha);
        autoCompleteFechas = findViewById(R.id.autoCompleteFechas);
        //editFecha = findViewById(R.id.editFecha);

        ///recyclerCanchasDist = findViewById(R.id.recyclerCanchasDist);

        txtDistrito.setStartIconTintList(ContextCompat.getColorStateList(getApplicationContext(), R.drawable.starticon_selector));
        txtFecha.setStartIconTintList(ContextCompat.getColorStateList(getApplicationContext(), R.drawable.starticon_selector));

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        autoCompleteFechas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        MainStdActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListener, year, month, day);
                datePickerDialog. getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = (dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + (month < 10 ? "0" + month : month) + "/" + year;
                autoCompleteFechas.setText(date);
            }
        };

        //--
        Format formatter;
        Date date = new Date();
        formatter = new SimpleDateFormat("dd/MM/yyyy");
        autoCompleteFechas.setText(formatter.format(date));
        //--
        final Model model = Model.getInstance(MainStdActivity.this.getApplication());
        model.loadDistritos(true, new AbstractAPIListener() {
            @Override
            public void onDistritosLoaded(List<Distrito> loadedDistritos) {
                distritos.clear();
                distritos.addAll(loadedDistritos);
                ArrayList<String> dist =  new ArrayList<String>();
                String actual = "";
                for (int i = 0; i < distritos.size(); i++) {
                    dist.add(distritos.get(i).getName());
                }
                mostrarDistritos(dist);
            }
        });

        Button btnBuscar = findViewById(R.id.btnBuscar);

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MainActivity.this, "parqueos:" + canchas.size(), Toast.LENGTH_SHORT).show();
                distritoId = 0;
                if (validateDistrito() && validateFecha()) {
                    String distritoTexto = autoCompleteDistritos.getText().toString().trim();
                    for (int i = 0; i < distritos.size(); i++) {
                        if (distritoTexto.toLowerCase().equals(distritos.get(i).getName().trim().toLowerCase())) {
                            distritoId = distritos.get(i).getId();
                        }
                    }

                    fecha = autoCompleteFechas.getText().toString().trim();
                    //mostrarCanchas(); //listar en el mismo activity
                    listarCanchas(); //listar en listarActivity
                }
            }
        });

        TextView txtIniciar = findViewById(R.id.txtIniciar);
        txtIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainStdActivity.this,LoginActivity.class);
                startActivity(intent);
                //onBackPressed();
            }
        });

        TextView txtRegistrar = findViewById(R.id.txtRegistrate);
        txtRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainStdActivity.this,RegisterActivity.class);
                startActivity(intent);
                //onBackPressed();
            }
        });

    }

    private boolean validateDistrito() {
        String input = txtDistrito.getEditText().getText().toString().trim();
        if (input.isEmpty()) {
            txtDistrito.setError("Requerido *");
            return false;
        }
        else {
            txtDistrito.setError(null);
            return true;
        }
    }

    private boolean validateFecha() {
        String input = txtFecha.getEditText().getText().toString().trim();
        if (input.isEmpty()) {
            txtFecha.setError("Requerido *");
            return false;
        }
        else {
            txtFecha.setError(null);
            return true;
        }
    }

    protected void mostrarDistritos (ArrayList distr) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.dropdown_item, distr);
        autoCompleteDistritos.setAdapter(arrayAdapter);
    }
    protected void mostrarFechas (ArrayList fecs) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.dropdown_item, fecs);
        autoCompleteDistritos.setAdapter(arrayAdapter);
    }

    private void listarCanchas() {
        Intent intent = new Intent(MainStdActivity.this, ListarStdActivity.class);
        intent.putExtra("distritoId", distritoId + "");
        intent.putExtra("fecha", fecha + "");
        startActivity(intent);
    }
}