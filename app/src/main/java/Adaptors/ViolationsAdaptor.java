package Adaptors;

import com.example.myapplication.Payment;
import com.example.myapplication.R;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

import Models.Violations;

public class ViolationsAdaptor extends RecyclerView.Adapter<ViolationsAdaptor.ViewHolder> {

    ArrayList<Violations> violationModel;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    String userId = user.getUid();

    public ViolationsAdaptor(ArrayList<Violations> violations){
        this.violationModel = violations;
    }

    @NonNull
    @Override
    public ViolationsAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_violations, parent, false);

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViolationsAdaptor.ViewHolder holder, int position) {
        holder.ticketType.setText(violationModel.get(position).getType());
        holder.fees.setText(String.valueOf(violationModel.get(position).getFees()));
        holder.status.setText(violationModel.get(position).getStatus());


        Glide.with(holder.itemView.getContext())
                .load(R.drawable.money)
                .into(holder.fawryPic);

        Violations item = violationModel.get(position);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference violationsRef = database.getReference("users/" + userId + "/violations");

        if (item.getStatus().equals("UNPAID")) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    violationsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            List<DataSnapshot> violationList = new ArrayList<>();
                            for (DataSnapshot violationSnapshot : snapshot.getChildren()) {
                                violationList.add(violationSnapshot);
                            }

                            int position = holder.getAdapterPosition();
                            if (position != RecyclerView.NO_POSITION && position < violationList.size()) {
                                DataSnapshot violationSnapshot = violationList.get(position);
                                String violationId = violationSnapshot.getKey(); // Get the ID of the clicked violation
                                Log.d("print", violationId);
                                Intent intent = new Intent(v.getContext(), Payment.class);
                                intent.putExtra("ViolationID", violationId);
                                v.getContext().startActivity(intent);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            });
        }else{
            holder.itemView.setOnClickListener(null);
        }
    }

    @Override
    public int getItemCount() {
        return violationModel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView ticketType, fees, status, violationId;
        ImageView fawryPic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ticketType = itemView.findViewById(R.id.ticketType);
            fees = itemView.findViewById(R.id.fee);
            status = itemView.findViewById(R.id.status);
            fawryPic = itemView.findViewById(R.id.fawry_pic);
            violationId = itemView.findViewById(R.id.violationId);
        }
    }
}
