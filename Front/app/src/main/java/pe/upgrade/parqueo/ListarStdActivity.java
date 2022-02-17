package pe.upgrade.parqueo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import pe.upgrade.canchas.R;
import pe.upgrade.parqueo.model.BuscarAdaptadorParqueoStd;
import pe.upgrade.parqueo.model.Model;
import pe.upgrade.parqueo.model.api.AbstractAPIListener;
import pe.upgrade.parqueo.model.api.Cancha;

public class ListarStdActivity extends AppCompatActivity {

    Application application = this.getApplication();
    Model model = Model.getInstance(application);
    //final List<Distrito> distritos = model.getDistritos();
    final List<Cancha> canchas = model.getCanchas();

    RecyclerView recyclerCanchasDist;
    BuscarAdaptadorParqueoStd adaptador;

    int distritoId;
    String fecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_std);

        setTitle("BUSCAR ESTACIONAMIENTO");

        recyclerCanchasDist = findViewById(R.id.recyclerCanchasDist);

        distritoId = Integer.parseInt(getIntent().getStringExtra("distritoId"));
        fecha = getIntent().getStringExtra("fecha");

        //Toast.makeText(ListarActivity.this, "lista:" + distritoId + "-" + fecha, Toast.LENGTH_SHORT).show();
        mostrarCanchas();
    }

    private void mostrarCanchas() {
        //final Model model = Model.getInstance(ListarStdActivity.this.getApplication());
        model.loadCanchasDistrito(distritoId, new AbstractAPIListener() {
            @Override
            public void onCanchasDistritoLoaded(List<Cancha> loadedCanchas) {
                //Toast.makeText(MainActivity.this, "lista:" + loadedCanchas.size(), Toast.LENGTH_SHORT).show();
                canchas.clear();
                canchas.addAll(loadedCanchas);
                mostrar();
            }
        });
    }

    private void mostrar() {
        adaptador = new BuscarAdaptadorParqueoStd(this, application, canchas, fecha);
        recyclerCanchasDist.setAdapter(adaptador);
        recyclerCanchasDist.setLayoutManager(new LinearLayoutManager(this));
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
        final Model model = Model.getInstance(ListarStdActivity.this.getApplication());

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
                Intent i4 = new Intent(this, MainStdActivity.class);
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