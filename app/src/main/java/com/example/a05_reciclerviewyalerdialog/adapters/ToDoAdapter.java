package com.example.a05_reciclerviewyalerdialog.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a05_reciclerviewyalerdialog.R;
import com.example.a05_reciclerviewyalerdialog.modelos.ToDo;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ToDoVh> {

    private List<ToDo> objects;
    private int resource;
    private Context context;

    public ToDoAdapter(List<ToDo> objects, int resource, Context context) {
        this.objects = objects;
        this.resource = resource;
        this.context = context;
    }

    /**
     * Algo, no me importa quien llamará a este método para crear una nueva fila
     * @param parent
     * @param viewType
     * @return un Objeto ViewHolder
    **/

    @NonNull
    @Override
    public ToDoVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View toDoView = LayoutInflater.from(context).inflate(resource, null);
        ViewGroup.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        toDoView.setLayoutParams(lp);
        return new ToDoVh(toDoView);
    }

    /**
     * A partir de un ViewHolder -> Asignar valores a los elementos
     * @param holder -> Fila a configurar
     * @param position -> Elemento de la lista a mostrar
     */

    @Override
    public void onBindViewHolder(@NonNull ToDoVh holder, int position) {
        ToDo toDo = objects.get(position);
        holder.lbTitulo.setText(toDo.getTitulo());
        holder.lbContenido.setText(toDo.getContenido());
        holder.lbFecha.setText(toDo.getFecha().toString());
        if (toDo.isCompletado()){
            holder.btnCompletado.setImageResource(android.R.drawable.checkbox_on_background);
        }else{
            holder.btnCompletado.setImageResource(android.R.drawable.checkbox_off_background);
        }
        holder.btnCompletado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmaCambioEstado("¿Estas seguro de que quieres cambiar el estado?",toDo).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    private AlertDialog confirmaCambioEstado(String mensaje, ToDo toDo){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(mensaje);
        builder.setCancelable(false);

        builder.setNegativeButton("NO", null);
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                 toDo.setCompletado(!toDo.isCompletado());
                 notifyDataSetChanged();
            }
        });
        return builder.create();
    }


    // Objeto que se mostrará cada vez que se tenag que mostrar un To-Do en el Recycler pero sólo se instancian los que caben en la pantalla + 1/2

    public class ToDoVh extends RecyclerView.ViewHolder {

        TextView lbTitulo, lbContenido, lbFecha;
        ImageButton btnCompletado;

        public ToDoVh(@NonNull View itemView) {
            super(itemView);
            lbTitulo = itemView.findViewById(R.id.lbTituloToDoModelView);
            lbContenido = itemView.findViewById(R.id.lbContenidoToDoModelView);
            lbFecha = itemView.findViewById(R.id.lbFechaToDoModelView);
            btnCompletado = itemView.findViewById(R.id.btnCompletadoToDoModelView);
        }
    }
}
