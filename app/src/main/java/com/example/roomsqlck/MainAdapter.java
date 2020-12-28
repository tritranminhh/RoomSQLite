package com.example.roomsqlck;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private List<Person> dataList;
    private Activity context;
    private RoomDB database;

    public MainAdapter(List<Person> dataList,Activity context) {
        this.dataList = dataList;
        this.context=context;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_main,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, int position) {
        Person data=dataList.get(position);
        database= RoomDB.getInstance(context);
        holder.tv_name.setText(data.getPersonName());
        holder.tv_age.setText(String.valueOf(data.getTuoi()));
        holder.btnEdit.setOnClickListener(v -> {
            Person person=dataList.get(holder.getAdapterPosition());
            final int id=person.getId();
            String name=person.getPersonName();
            int tuoi=person.getTuoi();
            Dialog dialog=new Dialog(context);
            dialog.setContentView(R.layout.dialog_update);
            int width= WindowManager.LayoutParams.MATCH_PARENT;
            int height=WindowManager.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width,height);
            dialog.show();
            EditText update_name=dialog.findViewById(R.id.editTextNameUpdate);
            EditText update_tuoi=dialog.findViewById(R.id.editTextAgeUpdate);
            Button btnUpdate=dialog.findViewById(R.id.btnUpdate);
            update_name.setText(name);
            update_tuoi.setText(String.valueOf(tuoi));
            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                dialog.dismiss();
                String uName=update_name.getText().toString().trim();
                int uTuoi=Integer.parseInt((update_tuoi.getText().toString().trim()));
                database.personDAO().update(id,uName,uTuoi);
                    Toast.makeText(context, "update thanh cong", Toast.LENGTH_SHORT).show();
                    dataList.clear();
                    dataList.addAll(database.personDAO().getAll());
                    notifyDataSetChanged();
                }
            });

        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person person=dataList.get(holder.getAdapterPosition());
                database.personDAO().deletePerson(person);
                Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                dataList.clear();
                dataList.addAll(database.personDAO().getAll());
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
    TextView tv_name,tv_age;
    ImageView btnEdit,btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name=itemView.findViewById(R.id.txtViewTen);
            tv_age=itemView.findViewById(R.id.textViewAges);
            btnEdit=itemView.findViewById(R.id.btnEdit);
            btnDelete=itemView.findViewById(R.id.btnDelete);
        }
    }
}
