package pe.upgrade.parqueo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import pe.upgrade.canchas.R;
import pe.upgrade.parqueo.model.Model;
import pe.upgrade.parqueo.model.api.AbstractAPIListener;
import pe.upgrade.parqueo.model.api.Cancha;
import pe.upgrade.parqueo.model.api.Distrito;
import pe.upgrade.parqueo.model.api.Resp;

public class ParqueoActivity extends AppCompatActivity {

    Application application = this.getApplication();
    Model model = Model.getInstance(application);
    final List<Distrito> distritos = model.getDistritos();

    TextView lblTitFrm;

    TextInputLayout txtName;
    TextInputLayout txtDescription;
    TextInputLayout txtPrice;
    TextInputLayout txtContact;
    TextInputLayout txtAddress;
    TextInputLayout txtLatitude;
    TextInputLayout txtLongitude;
    TextInputLayout txtDistrito;
    Switch switchEnable;

    AutoCompleteTextView autoCompleteTextView;

    String name, description, address, contact;
    Double price, latitude, longitude;
    int distritoId, id;
    boolean enable;

    Boolean actualizar = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parqueo);

        lblTitFrm = findViewById(R.id.lblTitFrm);

        txtName = findViewById(R.id.txtName);
        txtDescription = findViewById(R.id.txtDescription);
        txtPrice = findViewById(R.id.txtPrice);
        txtContact = findViewById(R.id.txtContact);
        txtAddress = findViewById(R.id.txtAddress);
        txtLatitude = findViewById(R.id.txtLatitude);
        txtLongitude = findViewById(R.id.txtLongitude);
        txtDistrito = findViewById(R.id.txtDistrito);

        switchEnable = findViewById(R.id.switchEnable);
        enable = switchEnable.isEnabled();

        autoCompleteTextView = findViewById(R.id.autoCompleteDistritos);

        Button btnSave = findViewById(R.id.btnSave);

        txtName.setStartIconTintList(ContextCompat.getColorStateList(getApplicationContext(), R.drawable.starticon_selector));
        txtDescription.setStartIconTintList(ContextCompat.getColorStateList(getApplicationContext(), R.drawable.starticon_selector));
        txtPrice.setStartIconTintList(ContextCompat.getColorStateList(getApplicationContext(), R.drawable.starticon_selector));
        txtContact.setStartIconTintList(ContextCompat.getColorStateList(getApplicationContext(), R.drawable.starticon_selector));
        txtAddress.setStartIconTintList(ContextCompat.getColorStateList(getApplicationContext(), R.drawable.starticon_selector));
        txtLatitude.setStartIconTintList(ContextCompat.getColorStateList(getApplicationContext(), R.drawable.starticon_selector));
        txtLongitude.setStartIconTintList(ContextCompat.getColorStateList(getApplicationContext(), R.drawable.starticon_selector));
        txtDistrito.setStartIconTintList(ContextCompat.getColorStateList(getApplicationContext(), R.drawable.starticon_selector));

        agregarEventoKey();
        verificarEdicion();
        model.loadDistritos(false, new AbstractAPIListener() {
            @Override
            public void onDistritosLoaded(List<Distrito> loadedDistritos) {
                distritos.clear();
                distritos.addAll(loadedDistritos);
                //adapter.notifyDataSetChanged();
                //Toast.makeText(CanchaActivity.this, "Distritos: " + distritos.size(), Toast.LENGTH_SHORT).show();

                ArrayList<String> dist =  new ArrayList<String>();
                String actual = "";
                for (int i = 0; i < distritos.size(); i++) {
                    dist.add(distritos.get(i).getName());
                    if (distritoId == distritos.get(i).getId()) {
                        actual = distritos.get(i).getName().toString().trim();
                    }
                }
                mostrarDistritos(dist, actual);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                capturarDatos();

                if (!validateName() || !validateDescription() || !validateDistrito() || !validateAddress() || !validatePrice() || !validateLatitude() || !validateLongitude()) {
                    //error alguno
                }
                else {
                    final Model model = Model.getInstance(ParqueoActivity.this.getApplication());
                    if (actualizar) {
                        model.updateCancha(id, name, description, price, contact, address, latitude, longitude, distritoId, enable, new AbstractAPIListener() {
                            @Override
                            public void onCanchaUpdated(Resp resp) {

                                if (resp.getCode() > 0) {
                                    AlertDialog.Builder v2 = new AlertDialog.Builder(ParqueoActivity.this);
                                    v2.setTitle("Información!");
                                    v2.setMessage(resp.getMsg());
                                    v2.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Intent intent = new Intent(ParqueoActivity.this, ParqueosActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });
                                    v2.create().show();
                                }
                                else {
                                    Toast.makeText(ParqueoActivity.this, "Ocurrió un error", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                    }
                    else {
                        model.createCancha(name, description, price, contact, address, latitude, longitude, distritoId, enable, new AbstractAPIListener() {
                            @Override
                            public void onCanchaCreated(Cancha cancha) {
                                if (cancha != null) {
                                    model.setCancha(cancha);
                                    Intent intent =  new Intent(ParqueoActivity.this, ParqueosActivity.class);
                                    startActivity(intent);
                                    finish();
                                    Toast.makeText(ParqueoActivity.this, "Se agregó: " + cancha.getName(), Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(ParqueoActivity.this, "Ocurrió un error", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
                //Toast.makeText(LoginActivity.this, "Email: " + email,Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void verificarEdicion() {
        if(getIntent().hasExtra("id")) {
            id = Integer.parseInt(getIntent().getStringExtra("id"));
            name = getIntent().getStringExtra("name");
            description = getIntent().getStringExtra("description");
            price = Double.parseDouble(getIntent().getStringExtra("price"));
            contact = getIntent().getStringExtra("contact");
            address = getIntent().getStringExtra("address");
            latitude = Double.parseDouble(getIntent().getStringExtra("latitude"));
            longitude = Double.parseDouble(getIntent().getStringExtra("longitude"));
            distritoId = Integer.parseInt(getIntent().getStringExtra("distritoId"));
            enable = getIntent().getBooleanExtra("enable",true);

            actualizar = true;
            txtName.getEditText().setText(name);
            txtDescription.getEditText().setText(description);
            txtPrice.getEditText().setText(price + "");
            txtContact.getEditText().setText(contact);
            txtAddress.getEditText().setText(address);
            txtLatitude.getEditText().setText(latitude + "");
            txtLongitude.getEditText().setText(longitude + "");
            switchEnable.setChecked(enable);

            setTitle("Editar: " + name);
            lblTitFrm.setText("ACTUALIZAR ESTACIONAMIENTO");

        }
    }

    private void capturarDatos() {
        name = txtName.getEditText().getText().toString();
        description = txtDescription.getEditText().getText().toString();
        contact = txtContact.getEditText().getText().toString();
        address = txtAddress.getEditText().getText().toString();
        try {
            price = Double.parseDouble(txtPrice.getEditText().getText().toString().trim());
        } catch (Exception e){
            price = 0.00;
        }
        try {
            latitude = Double.parseDouble(txtLatitude.getEditText().getText().toString().trim());
        } catch (Exception e){
            latitude = 0.0;
        }
        try {
            longitude = Double.parseDouble(txtLongitude.getEditText().getText().toString().trim());
        } catch (Exception e){
            longitude = 0.0;
        }
        try {
            longitude = Double.parseDouble(txtLongitude.getEditText().getText().toString().trim());
        } catch (Exception e){
            longitude = 0.0;
        }

        distritoId = 0;

        enable = switchEnable.isChecked();

        String distritoTexto = autoCompleteTextView.getText().toString().trim();
        for (int i = 0; i < distritos.size(); i++) {
            if (distritoTexto.toLowerCase().equals(distritos.get(i).getName().trim().toLowerCase())) {
                distritoId = distritos.get(i).getId();
            }
        }

    }

    protected void mostrarDistritos (ArrayList distr, String actual) {
        autoCompleteTextView.setText(actual);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.dropdown_item, distr);
        //autoCompleteTextView = findViewById(R.id.autoCompleteDistritos);
        autoCompleteTextView.setAdapter(arrayAdapter);


    }

    private boolean validateName() {
        String input = txtName.getEditText().getText().toString().trim();
        if (input.isEmpty()) {
            txtName.setError("Requerido *");
            return false;
        }
        else {
            txtName.setError(null);
            return true;
        }
    }
    private boolean validateDescription() {
        String input = txtDescription.getEditText().getText().toString().trim();
        if (input.isEmpty()) {
            txtDescription.setError("Requerido *");
            return false;
        }
        else {
            txtDescription.setError(null);
            return true;
        }
    }

    private boolean validateContact() {
        String input = txtContact.getEditText().getText().toString().trim();
        if (input.isEmpty()) {
            txtContact.setError("Requerido *");
            return false;
        }
        else {
            txtContact.setError(null);
            return true;
        }
    }

    private boolean validateAddress() {
        String input = txtAddress.getEditText().getText().toString().trim();
        if (input.isEmpty()) {
            txtAddress.setError("Requerido *");
            return false;
        }
        else {
            txtAddress.setError(null);
            return true;
        }
    }

    private boolean validatePrice() {
        String input = txtPrice.getEditText().getText().toString().trim();
        try {
            price = Double.parseDouble(input);
        } catch (Exception e){
            price = 0.00;
        }
        if (input.isEmpty()) {
            txtPrice.setError("Requerido *");
            return false;
        }
        else if (price <= 0) {
            txtPrice.setError("No valido *");
            return false;
        }
        else {
            txtPrice.setError(null);
            return true;
        }
    }

    private boolean validateLatitude() {
        String input = txtLatitude.getEditText().getText().toString().trim();
        try {
            latitude = Double.parseDouble(input);
        } catch (Exception e){
            latitude = 0.00;
        }
        if (input.isEmpty()) {
            txtLatitude.setError("Requerido *");
            return false;
        }
        else if (latitude == 0) {
            txtLatitude.setError("No valido *");
            return false;
        }
        else {
            txtLatitude.setError(null);
            return true;
        }
    }
    private boolean validateLongitude() {
        String input = txtLongitude.getEditText().getText().toString().trim();
        try {
            longitude = Double.parseDouble(input);
        } catch (Exception e){
            longitude = 0.00;
        }
        if (input.isEmpty()) {
            txtLongitude.setError("Requerido *");
            return false;
        }
        else if (longitude == 0) {
            txtLongitude.setError("Requerido *");
            return false;
        }
        else {
            txtLongitude.setError(null);
            return true;
        }
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

    private void agregarEventoKey() {
        txtName.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { validateName(); }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        txtDescription.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { validateDescription(); }

            @Override
            public void afterTextChanged(Editable editable) { }
        });
        txtContact.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { validateContact(); }

            @Override
            public void afterTextChanged(Editable editable) { }
        });
        txtAddress.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { validateAddress(); }

            @Override
            public void afterTextChanged(Editable editable) { }
        });
        txtPrice.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { validatePrice(); }

            @Override
            public void afterTextChanged(Editable editable) { }
        });
        txtLatitude.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { validateLatitude(); }

            @Override
            public void afterTextChanged(Editable editable) { }
        });
        txtLongitude.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { validateLongitude(); }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        MenuItem mant_cancha = menu.findItem(R.id.mant_cancha);
        final Model model = Model.getInstance(ParqueoActivity.this.getApplication());

        if(model.getUser().getRole().trim().toLowerCase().contains("admin"))
        {
            mant_cancha.setVisible(true);
        }
        else
        {
            mant_cancha.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.camara:
                Intent i = new Intent(this, TomarFotoActivity.class);
                startActivity(i);
                return true;
            case R.id.mapa:
                Intent i2 = new Intent(this, VerMapaActivity.class);
                startActivity(i2);
                return true;
            case R.id.llamada:
                Intent i3 = new Intent(Intent.ACTION_DIAL);
                i3.setData(Uri.parse("tel:987-654-321"));
                startActivity(i3);
                return true;
            case R.id.main:
                Intent i4 = new Intent(this, MainActivity.class);
                startActivity(i4);
                return true;
            /*case R.id.AnularAlquiler:
                Intent i5 = new Intent(this, AnularalquilerActivity.class);
                startActivity(i5);
                return true;*/
            case R.id.misAlquileres:
                Intent i6 = new Intent(this, MisAlquileresActivity.class);
                startActivity(i6);
                return true;
            case R.id.mant_cancha:
                Intent i7 = new Intent(this, ParqueosActivity.class);
                startActivity(i7);
                return true;
            case R.id.salir:
                Intent i8 = new Intent(this, SplashActivity.class);
                startActivity(i8);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}