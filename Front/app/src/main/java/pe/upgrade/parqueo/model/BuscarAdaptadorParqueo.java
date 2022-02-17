package pe.upgrade.parqueo.model;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pe.upgrade.canchas.R;
import pe.upgrade.parqueo.model.api.Cancha;

public class BuscarAdaptadorParqueo extends RecyclerView.Adapter<BuscarAdaptadorParqueo.MyViewHolder> {
    private Context context;
    private Application application;
    private List<Cancha> listaCanchas = new ArrayList<Cancha>();
    private String fechatxt;

    public BuscarAdaptadorParqueo(Context context, Application application, List<Cancha> listaCanchas, String fechatxt) {
        this.context = context;
        this.application = application;
        this.listaCanchas = listaCanchas;
        this.fechatxt = fechatxt;
    }

    @NonNull
    @Override
    public BuscarAdaptadorParqueo.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.buscar_fila_parqueo, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BuscarAdaptadorParqueo.MyViewHolder holder, int position) {
        holder.filaTitulo.setText(listaCanchas.get(position).getName() + "");
        holder.filaLugar.setText(listaCanchas.get(position).getAddress() + "");
        //holder.filaFecha.setText(listaCanchas.get(position).getFecha() + "");
        holder.filaPrecio.setText(listaCanchas.get(position).getPrice() + " / hora");
        //holder.filaCantidad.setText(listaCanchas.get(position).getCantidad() + "");

        holder.btnSeleccionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uri;
                uri = Uri.parse("geo:" + listaCanchas.get(position).getLatitude() + ", " + listaCanchas.get(position).getLongitude() + "?z=20");
                showMap(uri);

                /*Intent intent = new Intent(context, ParqueoHorasActivity.class);
                intent.putExtra("fecha", fechatxt);
                intent.putExtra("id", listaCanchas.get(position).getId() + "");
                intent.putExtra("name", listaCanchas.get(position).getName() + "");
                intent.putExtra("description", listaCanchas.get(position).getDescription() + "");
                intent.putExtra("price", listaCanchas.get(position).getPrice() + "");
                intent.putExtra("address", listaCanchas.get(position).getAddress() + "");
                intent.putExtra("latitude", listaCanchas.get(position).getLatitude() + "");
                intent.putExtra("longitude", listaCanchas.get(position).getLongitude() + "");
                intent.putExtra("distritoId", listaCanchas.get(position).getDistritoId() + "");

                context.startActivity(intent);*/
            }
        });

    }

    public void showMap(Uri geoLocation) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);
        //if (intent.resolveActivity(getPackageManager()) != null) { startActivity(intent); }
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return listaCanchas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView filaTitulo, filaLugar, filaPrecio;

        ImageButton btnSeleccionar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            filaTitulo = itemView.findViewById(R.id.filaTitulo);
            filaLugar = itemView.findViewById(R.id.filaLugar);
            //filaFecha = itemView.findViewById(R.id.filaFecha);
            filaPrecio = itemView.findViewById(R.id.filaPrecio);
            //filaCantidad = itemView.findViewById(R.id.filaCantidad);

            btnSeleccionar = itemView.findViewById(R.id.btnSeleccionar);
            //btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }
}
