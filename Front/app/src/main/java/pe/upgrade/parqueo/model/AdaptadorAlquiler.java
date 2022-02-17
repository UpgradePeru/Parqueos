package pe.upgrade.parqueo.model;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pe.upgrade.parqueo.MisAlquileresActivity;
import pe.upgrade.canchas.R;
import pe.upgrade.parqueo.model.api.AbstractAPIListener;
import pe.upgrade.parqueo.model.api.Alquiler;
import pe.upgrade.parqueo.model.api.Resp;

public class AdaptadorAlquiler extends RecyclerView.Adapter<AdaptadorAlquiler.MyViewHolder> {
    private Context context;
    private Application application;
    private List<Alquiler> listaAlquileres = new ArrayList<Alquiler>();

    public AdaptadorAlquiler(Context context, Application application, List<Alquiler> listaAlquileres) {
        this.context = context;
        this.application = application;
        this.listaAlquileres = listaAlquileres;
    }

    @NonNull
    @Override
    public AdaptadorAlquiler.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fila_mis_alquileres, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorAlquiler.MyViewHolder holder, int position) {
        holder.filaTitulo.setText(listaAlquileres.get(position).getCancha().getName() + "");
        holder.filaLugar.setText(listaAlquileres.get(position).getCancha().getAddress() + "");
        holder.filaPrecio.setText(listaAlquileres.get(position).getHora().getName() + "");
        holder.filaCantidad.setText(listaAlquileres.get(position).getCancha().getPrice() + "");


        holder.btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder ventana = new AlertDialog.Builder(context);
                ventana.setTitle("Confirmar!");
                ventana.setMessage("Desea eliminar el alquiler: " + listaAlquileres.get(position).getFecha());  //----------
                ventana.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final Model model = Model.getInstance(application);
                        model.deleteAlquiler(listaAlquileres.get(position).getId(), new AbstractAPIListener() {
                            @Override
                            public void onAlquilerDeleted(Resp resp) {
                                if (resp.getCode() > 0) {
                                    AlertDialog.Builder v2 = new AlertDialog.Builder(context);
                                    v2.setTitle("Información!");
                                    v2.setMessage(resp.getMsg());
                                    v2.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Intent intent = new Intent(context,MisAlquileresActivity.class);
                                            context.startActivity(intent);
                                        }
                                    });
                                    v2.create().show();
                                }
                                else {
                                    Toast.makeText(context, "Ocurrió un error", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                });
                ventana.setNegativeButton("NO", null);
                ventana.create().show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return listaAlquileres.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView filaTitulo, filaLugar, filaFecha, filaPrecio,filaCantidad;


        ImageButton btnEliminar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            filaTitulo = itemView.findViewById(R.id.filaTitulo);
            filaLugar = itemView.findViewById(R.id.filaLugar);
            //filaFecha = itemView.findViewById(R.id.filaFecha);
            filaPrecio = itemView.findViewById(R.id.filaPrecio);
            filaCantidad = itemView.findViewById(R.id.filaCantidad);


            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }
}
