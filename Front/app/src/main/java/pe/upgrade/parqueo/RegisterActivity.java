package pe.upgrade.parqueo;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
import java.util.regex.Pattern;

import pe.upgrade.canchas.R;
import pe.upgrade.parqueo.model.Model;
import pe.upgrade.parqueo.model.api.AbstractAPIListener;
import pe.upgrade.parqueo.model.api.Resp;

public class RegisterActivity extends AppCompatActivity {

    TextInputLayout txtName;
    TextInputLayout txtEmail;
    TextInputLayout txtPassword;
    TextInputLayout txtPassword2;

    TextInputLayout txtNroDoc;
    TextInputLayout txtCelular;
    TextInputLayout txtLicencia;
    TextInputLayout txtPlaca;

    //subir imagen
    Button btnCamara, btnGaleria;
    ImageView iv;

    Bitmap bitmap;
    int PICK_IMAGE_REQUEST = 1;
    String UPLOAD_URL = "https://parqueoya.azurewebsites.net/app/subirplaca";

    String KEY_IMAGE = "foto";
    String KEY_NOMBRE = "nombre";
    //fin subir imagen

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        checkCameraPermission();
        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        txtPassword2 = findViewById(R.id.txtPassword2);

        txtNroDoc = findViewById(R.id.txtNroDoc);
        txtCelular = findViewById(R.id.txtCelular);
        txtLicencia = findViewById(R.id.txtLicencia);
        txtPlaca = findViewById(R.id.txtPlaca);

        Button btnRegister = findViewById(R.id.btnRegister);

        TextView txtIniciar = findViewById(R.id.txtIniciar);

        txtIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                //onBackPressed();
            }
        });

        //subir imagen
        btnCamara = findViewById(R.id.btnCamara);
        btnGaleria = findViewById(R.id.btnGaleria);

        //et = findViewById(R.id.editText);
        iv = findViewById(R.id.imagePlaca);

        btnCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCamera();
            }
        });
        btnGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGaleria();
            }
        });
        //fin subir imagen

        txtName.setStartIconTintList(ContextCompat.getColorStateList(getApplicationContext(), R.drawable.starticon_selector));
        txtEmail.setStartIconTintList(ContextCompat.getColorStateList(getApplicationContext(), R.drawable.starticon_selector));
        txtPassword.setStartIconTintList(ContextCompat.getColorStateList(getApplicationContext(), R.drawable.starticon_selector));
        txtPassword2.setStartIconTintList(ContextCompat.getColorStateList(getApplicationContext(), R.drawable.starticon_selector));

        txtNroDoc.setStartIconTintList(ContextCompat.getColorStateList(getApplicationContext(), R.drawable.starticon_selector));
        txtCelular.setStartIconTintList(ContextCompat.getColorStateList(getApplicationContext(), R.drawable.starticon_selector));
        txtLicencia.setStartIconTintList(ContextCompat.getColorStateList(getApplicationContext(), R.drawable.starticon_selector));
        txtPlaca.setStartIconTintList(ContextCompat.getColorStateList(getApplicationContext(), R.drawable.starticon_selector));
//-------
        txtName.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                validateName();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        txtEmail.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                validateEmail();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        txtPassword.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                validatePassword();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        txtPassword2.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                validatePassword2();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        txtNroDoc.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                validateNroDoc();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        txtCelular.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                validateCelular();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        txtLicencia.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                validateLicencia();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        txtPlaca.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                validatePlaca();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = txtName.getEditText().getText().toString();
                String email = txtEmail.getEditText().getText().toString();
                String password = txtPassword.getEditText().getText().toString();
                String password2 = txtPassword2.getEditText().getText().toString();

                String nroDoc = txtNroDoc.getEditText().getText().toString();
                String celular = txtCelular.getEditText().getText().toString();
                String licencia = txtLicencia.getEditText().getText().toString();
                String placa = txtPlaca.getEditText().getText().toString();

                //uploadImage();

                if (validateName() && validateNroDoc() && validateEmail() && validateCelular() && validatePassword() && validatePassword2() && validateLicencia() && validatePlaca()) {

                    final Model model = Model.getInstance(RegisterActivity.this.getApplication());
                    model.register(name, email, password, nroDoc, celular, licencia, placa, new AbstractAPIListener() {
                        @Override
                        public void onRegister(Resp resp) {
                            if (resp != null) {
                                model.setResp(resp);
                                Intent intent =  new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                                Toast.makeText(RegisterActivity.this, "Ok: " + resp.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(RegisterActivity.this, "Ocurrió un error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                //Toast.makeText(LoginActivity.this, "Email: " + email,Toast.LENGTH_SHORT).show();
            }
        });



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

    private boolean validateEmail() {
        String input = txtEmail.getEditText().getText().toString().trim();

        if (input.isEmpty()) {
            txtEmail.setError("Requerido *");
            return false;
        }
        else if (!validarEmail(input)) {
            txtEmail.setError("Email invalido *");
            return false;
        }
        else {
            txtEmail.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String input = txtPassword.getEditText().getText().toString().trim();

        if (input.isEmpty()) {
            txtPassword.setError("Requerido *");
            return false;
        }
        else if (input.length() < 8) {
            txtPassword.setError("No menor a 8 caracteres *");
            return false;
        }
        else {
            txtPassword.setError(null);
            return true;
        }
    }
    private boolean validatePassword2() {
        String input = txtPassword.getEditText().getText().toString().trim();
        String input2 = txtPassword2.getEditText().getText().toString().trim();

        if (input2.isEmpty()) {
            txtPassword2.setError("Requerido *");
            return false;
        }
        else if (input2.length() < 8) {
            txtPassword2.setError("No menor a 8 caracteres *");
            return false;
        }
        else if (!input2.equals(input)) {
            txtPassword2.setError("Contraseña diferente *");
            return false;
        }
        else {
            txtPassword2.setError(null);
            return true;
        }
    }

    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    private boolean validateNroDoc() {
        String input = txtNroDoc.getEditText().getText().toString().trim();

        if (input.isEmpty()) {
            txtNroDoc.setError("Requerido *");
            return false;
        }
        if (input.length() < 8) {
            txtNroDoc.setError("Requerido *");
            return false;
        }
        else {
            txtNroDoc.setError(null);
            return true;
        }
    }

    private boolean validateCelular() {
        String input = txtCelular.getEditText().getText().toString().trim();

        if (input.isEmpty()) {
            txtCelular.setError("Requerido *");
            return false;
        }
        else {
            txtCelular.setError(null);
            return true;
        }
    }

    private boolean validateLicencia() {
        String input = txtLicencia.getEditText().getText().toString().trim();

        if (input.isEmpty()) {
            txtLicencia.setError("Requerido *");
            return false;
        }
        else {
            txtLicencia.setError(null);
            return true;
        }
    }

    private boolean validatePlaca() {
        String input = txtPlaca.getEditText().getText().toString().trim();

        if (input.isEmpty()) {
            txtPlaca.setError("Requerido *");
            return false;
        }
        else {
            txtPlaca.setError(null);
            return true;
        }
    }

    //metodos de subir imagen
    public String getStringImagen(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public void uploadImage() {
        final ProgressDialog loading = ProgressDialog.show(this, "Subiendo...", "Espere por favor");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Toast.makeText(RegisterActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String imagen = getStringImagen(bitmap);
                //String nombre = et.getText().toString().trim();
                String nombre = txtNroDoc.getEditText().getText().toString().trim();

                Map<String, String> params = new Hashtable<String, String>();
                params.put(KEY_IMAGE, imagen);
                params.put(KEY_NOMBRE, nombre);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showGaleria() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT); //ACTION_GET_CONTENT
        //startActivityForResult(Intent.createChooser(intent, "Seleciona imagen"), PICK_IMAGE_REQUEST);
        //Intent intent = new Intent(this, SomeActivity.class);
        activityResultFileChooser.launch(Intent.createChooser(intent, "Seleciona imagen"));

    }
    private void checkCameraPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.CAMERA);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            Log.i("Mensaje", "No se tiene permiso para la camara!.");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 225);
        } else {
            Log.i("Mensaje", "Tienes permiso para usar la camara.");
        }
    }

    Uri cam_uri;
    private void showCamera() {
        //Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        //activityResultCamera.launch(intent);

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From Camera");
        cam_uri = this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, cam_uri);

        //startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_CODE); // OLD WAY
        activityResultCamera.launch(cameraIntent);                // VERY NEW WAY

    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (resultCode== Activity.RESULT_OK){
            Bundle ext = data.getExtras();
            bmp = (Bitmap)ext.get("data");
            imagen.setImageBitmap(bmp);
        }
    }*/
/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Cómo obtener el mapa de bits de la Galería
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Configuración del mapa de bits en ImageView
                iv.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }*/

    ActivityResultLauncher<Intent> activityResultFileChooser = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(),
        new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK) {
                // There are no request codes
                Intent data = result.getData();

                //super.onActivityResult(requestCode, resultCode, data);

                if (data != null && data.getData() != null) {
                    Uri filePath = data.getData();
                    try {
                        //Cómo obtener el mapa de bits de la Galería
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                        //Configuración del mapa de bits en ImageView
                        iv.setImageBitmap(bitmap);
                        txtPlaca.getEditText().setText(getStringImagen(bitmap));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    });

    ActivityResultLauncher<Intent> activityResultCamera = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        try {
                            //Cómo obtener el mapa de bits de la Galería
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), cam_uri);
                            //Configuración del mapa de bits en ImageView
                            iv.setImageBitmap(bitmap);
                            txtPlaca.getEditText().setText(getStringImagen(bitmap));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            });
    //fin metodos de subir imagen
}