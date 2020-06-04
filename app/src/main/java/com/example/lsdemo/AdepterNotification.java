package com.example.lsdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class AdepterNotification extends RecyclerView.Adapter<AdepterNotification.ViewHolder> {

    public List<ClassNotification>notificationList;
    public Context context;
    public FirebaseFirestore firestore;

    public RecyclerView recyclerView;
   // public NotificationActivity notificationActivity;




    public AdepterNotification(Context context, List<ClassNotification> requestNotificationList, RecyclerView notiRecView){

        this.context=context;
        this.recyclerView=notiRecView;
        this.notificationList=requestNotificationList;
    }
//    ItemTouchHelper.SimpleCallback simpleCallback =new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT) {
//        @Override
//        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
//            return false;
//        }
//
//        @Override
//        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//            notificationList.remove(viewHolder.getAdapterPosition());
//            notifyItemRemoved(viewHolder.getAdapterPosition());
//            // deletenotification(viewHolder.getAdapterPosition());
//
//        }
//    };



    @Override
    public AdepterNotification.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.notificationlayout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdepterNotification.ViewHolder holder, final int position) {

        holder.title.setText(notificationList.get(position).title);
        holder.body.setText(notificationList.get(position).message);

        final String docid=notificationList.get(position).notificationId;









    }



    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View view;
        public TextView title,body;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view=itemView;
            title=(TextView) view.findViewById(R.id.notiTitle);
            body=(TextView)view.findViewById(R.id.notiBody);




        }
    }
    public void deletenotification(String docref){
        String usermail= FirebaseAuth.getInstance().getCurrentUser().getEmail();

        firestore=FirebaseFirestore.getInstance();
        DocumentReference dr=firestore.collection("user/"+usermail+"Notification").document(docref);
        dr.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
            if(task.isSuccessful()){
                Toast.makeText(context, "Removed", Toast.LENGTH_SHORT).show();
            }
            }
        });




    }
}
