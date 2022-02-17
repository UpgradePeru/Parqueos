package pe.upgrade.parqueo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

import pe.upgrade.canchas.R;
import pe.upgrade.parqueo.model.Model;
import pe.upgrade.parqueo.model.api.AbstractAPIListener;
import pe.upgrade.parqueo.model.api.User;

public class LoginActivity extends AppCompatActivity {

    TextInputLayout txtEmail;
    TextInputLayout txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        Button btnLogin = findViewById(R.id.btnLogin);

        TextView txtRegistrate = findViewById(R.id.txtRegistrate);

        txtRegistrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                //onBackPressed();
            }
        });

        txtEmail.setStartIconTintList(ContextCompat.getColorStateList(getApplicationContext(), R.drawable.starticon_selector));
        txtPassword.setStartIconTintList(ContextCompat.getColorStateList(getApplicationContext(), R.drawable.starticon_selector));

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

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = txtEmail.getEditText().getText().toString();
                String password = txtPassword.getEditText().getText().toString();

                if (validateEmail() && validatePassword()) {

                    final Model model = Model.getInstance(LoginActivity.this.getApplication());
                    model.login(email, password, new AbstractAPIListener() {
                        @Override
                        public void onLogin(User user) {
                            if (user != null) {
                                model.setUser(user);
                                Intent intent =  new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                                //Toast.makeText(LoginActivity.this, "Hola " + user.getName(), Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(LoginActivity.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                //Toast.makeText(LoginActivity.this, "Email: " + email,Toast.LENGTH_SHORT).show();
            }
        });
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
        else {
            txtPassword.setError(null);
            return true;
        }
    }

    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

}