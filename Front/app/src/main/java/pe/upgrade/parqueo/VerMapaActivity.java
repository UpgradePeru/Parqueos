package pe.upgrade.parqueo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import pe.upgrade.canchas.R;
import pe.upgrade.parqueo.model.Model;

public class VerMapaActivity extends AppCompatActivity implements OnMapReadyCallback {

    SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_mapa);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.setTrafficEnabled(true);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(-12.1135659, -77.0094867))
                .title("Cancha La 10 (Principal)")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(-12.1132931, -77.009873))
                .title("Cementerio Municipal de Surquillo"));

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(-12.1133623, -77.0092979))
                .title("Estadio Municipal"));

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-12.1135659, -77.0094867), 15));

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
        final Model model = Model.getInstance(VerMapaActivity.this.getApplication());

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