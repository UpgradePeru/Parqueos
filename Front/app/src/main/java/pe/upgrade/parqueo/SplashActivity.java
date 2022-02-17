package pe.upgrade.parqueo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import pe.upgrade.canchas.R;
import pe.upgrade.parqueo.model.Model;
import pe.upgrade.parqueo.model.api.AbstractAPIListener;
import pe.upgrade.parqueo.model.api.User;

public class SplashActivity extends Activity { //IMPORTANTE Activity para el splash

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        TimerTask tarea = new TimerTask() {
            @Override
            public void run() {
                //Intent intent = new Intent(SplashActivity.this, MainStdActivity.class);
                //startActivity(intent);
                //finish();
                final Model model = Model.getInstance(SplashActivity.this.getApplication());
                model.login("a@a.com", "a", new AbstractAPIListener() {
                    @Override
                    public void onLogin(User user) {
                        if (user != null) {
                            model.setUser(user);
                            Intent intent =  new Intent(SplashActivity.this, MainStdActivity.class);
                            startActivity(intent);
                            finish();
                            //Toast.makeText(SplashActivity.this, "Hola " + user.getName(), Toast.LENGTH_SHORT).show();
                        }
                        else {
                            //Toast.makeText(SplashActivity.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        };

        Timer tiempo = new Timer();
        tiempo.schedule(tarea, 0);
    }
}