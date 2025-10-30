package com.example.ruralconnect;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ruralconnect.R;
import com.example.ruralconnect.model.Complaint; // Make sure this import is correct
import java.util.List;

public class ComplaintsAdapter extends RecyclerView.Adapter<ComplaintsAdapter.ComplaintViewHolder> {

    private List<Complaint> complaintList;
    private OnItemClickListener listener; // For handling clicks

    // Interface for click events
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public ComplaintsAdapter(List<Complaint> complaintList) {
        this.complaintList = complaintList;
    }

    @NonNull
    @Override
    public ComplaintViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Make sure you have a layout file named 'list_item_complaint.xml'
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_complaint, parent, false);
        return new ComplaintViewHolder(view, listener); // Pass listener
    }

    @Override
    public void onBindViewHolder(@NonNull ComplaintViewHolder holder, int position) {
        Complaint complaint = complaintList.get(position);

        // --- THIS IS THE FIX ---
        // Use the new methods from your Complaint POJO
        holder.titleTextView.setText(complaint.getComplaintType()); // Use type as title
        holder.descriptionTextView.setText(complaint.getComplaintText()); // Use complaint text as description
        // --- END OF FIX ---

        // Show status only if it exists
        if (complaint.getStatus() != null) {
            holder.statusTextView.setText("Status: " + complaint.getStatus());
            holder.statusTextView.setVisibility(View.VISIBLE);
        } else {
            holder.statusTextView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return complaintList == null ? 0 : complaintList.size();
    }

    // Method to update the list when data is fetched from API
    public void updateComplaints(List<Complaint> newComplaints) {
        this.complaintList = newComplaints;
        notifyDataSetChanged(); // Refresh the entire list view
    }

    static class ComplaintViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView descriptionTextView;
        TextView statusTextView;

        public ComplaintViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            // Make sure these IDs match your 'list_item_complaint.xml'
            titleTextView = itemView.findViewById(R.id.complaintTitleTextView);
            descriptionTextView = itemView.findViewById(R.id.complaintDescriptionTextView);
            statusTextView = itemView.findViewById(R.id.complaintStatusTextView);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            });
        }
    }
}