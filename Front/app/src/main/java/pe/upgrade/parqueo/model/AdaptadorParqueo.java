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

import pe.upgrade.parqueo.ParqueoActivity;
import pe.upgrade.parqueo.ParqueosActivity;
import pe.upgrade.canchas.R;
import pe.upgrade.parqueo.model.api.AbstractAPIListener;
import pe.upgrade.parqueo.model.api.Cancha;
import pe.upgrade.parqueo.model.api.Resp;

public class AdaptadorParqueo extends RecyclerView.Adapter<AdaptadorParqueo.MyViewHolder> {
    private Context context;
    private Application application;
    private List<Cancha> listaCanchas = new ArrayList<Cancha>();

    public AdaptadorParqueo(Context context, Application application, List<Cancha> listaCanchas) {
        this.context = context;
        this.application = application;
        this.listaCanchas = listaCanchas;
    }

    @NonNull
    @Override
    public AdaptadorParqueo.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fila_parqueo, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorParqueo.MyViewHolder holder, int position) {
        holder.filaTitulo.setText(listaCanchas.get(position).getName() + "");
        holder.filaLugar.setText(listaCanchas.get(position).getAddress() + "");
        //holder.filaFecha.setText(listaCanchas.get(position).getFecha() + "");
        holder.filaPrecio.setText(listaCanchas.get(position).getPrice() + " / hora");
        //holder.filaCantidad.setText(listaCanchas.get(position).getCantidad() + "");

        holder.txtEnable.setText(listaCanchas.get(position).isEnable() ? "" : "No habilitado");

        holder.btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, ParqueoActivity.class);
                intent.putExtra("id", listaCanchas.get(position).getId() + "");
                intent.putExtra("name", listaCanchas.get(position).getName() + "");
                intent.putExtra("description", listaCanchas.get(position).getDescription() + "");
                intent.putExtra("price", listaCanchas.get(position).getPrice() + "");
                intent.putExtra("contact", listaCanchas.get(position).getContact() + "");
                intent.putExtra("address", listaCanchas.get(position).getAddress() + "");
                intent.putExtra("latitude", listaCanchas.get(position).getLatitude() + "");
                intent.putExtra("longitude", listaCanchas.get(position).getLongitude() + "");
                intent.putExtra("distritoId", listaCanchas.get(position).getDistritoId() + "");
                intent.putExtra("enable", listaCanchas.get(position).isEnable());
                context.startActivity(intent);
            }
        });

        holder.btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder ventana = new AlertDialog.Builder(context);
                ventana.setTitle("Confirmar!");
                ventana.setMessage("Desea eliminar el parqueo: " + listaCanchas.get(position).getName());
                ventana.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final Model model = Model.getInstance(application);
                        model.deleteCancha(listaCanchas.get(position).getId(), new AbstractAPIListener() {
                            @Override
                            public void onCanchaDeleted(Resp resp) {
                                if (resp.getCode() > 0) {
                                    AlertDialog.Builder v2 = new AlertDialog.Builder(context);
                                    v2.setTitle("Información!");
                                    v2.setMessage(resp.getMsg());
                                    v2.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Intent intent = new Intent(context, ParqueosActivity.class);
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
        return listaCanchas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView filaTitulo, filaLugar, filaFecha, filaPrecio,filaCantidad, txtEnable;

        ImageButton btnEditar;
        ImageButton btnEliminar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            filaTitulo = itemView.findViewById(R.id.filaTitulo);
            filaLugar = itemView.findViewById(R.id.filaLugar);
            //filaFecha = itemView.findViewById(R.id.filaFecha);
            filaPrecio = itemView.findViewById(R.id.filaPrecio);
            //filaCantidad = itemView.findViewById(R.id.filaCantidad);
            txtEnable = itemView.findViewById(R.id.txtEnable);

            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }
}
