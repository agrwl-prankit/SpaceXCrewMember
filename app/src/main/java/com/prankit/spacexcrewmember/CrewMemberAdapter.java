package com.prankit.spacexcrewmember;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.prankit.spacexcrewmember.model.CrewMemberModel;

import java.util.List;

public class CrewMemberAdapter extends RecyclerView.Adapter<CrewMemberAdapter.ViewHolder>{

    private Context context;
    private List<CrewMemberModel> crewList;

    public CrewMemberAdapter(Context context, List<CrewMemberModel> crewList) {
        this.context = context;
        this.crewList = crewList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_memeber_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(crewList.get(position).getName());
        holder.agency.setText(crewList.get(position).getAgency());
        holder.status.setText(crewList.get(position).getStatus());
        holder.wiki.setText(crewList.get(position).getWikipedia());
        Glide.with(context).load(crewList.get(position).getImage()).placeholder(R.drawable.icon_no_profile).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return crewList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView name, agency, status, wiki;
        ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.member_name);
            agency = itemView.findViewById(R.id.member_agency);
            status = itemView.findViewById(R.id.member_status);
            wiki = itemView.findViewById(R.id.member_wiki);
            image = itemView.findViewById(R.id.member_pic);
        }
    }
}
