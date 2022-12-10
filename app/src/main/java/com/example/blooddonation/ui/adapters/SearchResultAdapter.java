package com.example.blooddonation.ui.adapters;

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

import com.example.blooddonation.R;
import com.example.blooddonation.firebasetemplate.DomainUserInfo;

import java.util.List;


public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.Viewholder> {
    List<DomainUserInfo> list;
    Context context;

    public SearchResultAdapter( Context context,List<DomainUserInfo> list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override


    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View view = LayoutInflater.from(context).
                inflate(R.layout.recycler_layout_search_result, parent, false);
        Viewholder vh = new Viewholder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int pos) {
        holder.nameTV.setText("Name : "+list.get(pos).name);
        holder.phoneTV.setText("Phone No : "+list.get(pos).phoneNo);
        holder.emailTV.setText("Email : "+list.get(pos).email);
        holder.classTV.setText("Class : "+list.get(pos).className);
        holder.subjectTV.setText("Subject : "+list.get(pos).subject);
        holder.districtTV.setText("District : "+list.get(pos).district);

        holder.callBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = holder.phoneTV.getText().toString().trim();
                number = number.substring(11);
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + number));
                context.startActivity(intent);
            }


        });

        holder.mailBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = holder.emailTV.getText().toString().trim();
                email = email.substring(7);
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", email, null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "This is my subject text");
                context.startActivity(Intent.createChooser(emailIntent, null));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public void setFilterList(List<DomainUserInfo> L) {
        this.list = L;
        notifyDataSetChanged();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView nameTV, emailTV, phoneTV, classTV, subjectTV, districtTV;
        ImageButton callBTN, mailBTN;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            nameTV = itemView.findViewById(R.id.name_TV);
            emailTV = itemView.findViewById(R.id.email_TV);
            phoneTV = itemView.findViewById(R.id.phone_TV);
            districtTV = itemView.findViewById(R.id.district_TV);
            classTV = itemView.findViewById(R.id.class_tv);
            subjectTV = itemView.findViewById(R.id.subject_tv);
            callBTN = itemView.findViewById(R.id.call_BTN);
            mailBTN = itemView.findViewById(R.id.mail_BTN);

        }
    }
}
