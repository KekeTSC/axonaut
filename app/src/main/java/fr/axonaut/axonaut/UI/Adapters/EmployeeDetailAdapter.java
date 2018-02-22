package fr.axonaut.axonaut.UI.Adapters;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import fr.axonaut.axonaut.Models.EmployeeModel;
import fr.axonaut.axonaut.Models.PipeStepWithOpportunityModel;
import fr.axonaut.axonaut.R;

public class EmployeeDetailAdapter extends RecyclerView.Adapter<EmployeeDetailAdapter.ViewHolder> {

    private final String TAG = String.format("Axonaut : %s", getClass().getSimpleName());

    private Context mContext;
    private ArrayList<EmployeeModel> mEmployeeList = new ArrayList<>();

    public EmployeeDetailAdapter(Context context, ArrayList<EmployeeModel> employeeList) {
        this.mContext = context;
        this.mEmployeeList = employeeList;
    }

    @Override
    public EmployeeDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.employee_item, parent, false);
        return new EmployeeDetailAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(EmployeeDetailAdapter.ViewHolder holder, int position) {
        holder.textViewEmployeeName.setText(mEmployeeList.get(position).getFullname());
        if ((mEmployeeList.get(position).getCellphoneNumber() == null
                || mEmployeeList.get(position).getCellphoneNumber().equals("")) && mEmployeeList.get(position).getPhoneNumber() != null){
            holder.textViewEmployeePhoneNumber.setText(mEmployeeList.get(position).getPhoneNumber());
        } else if (mEmployeeList.get(position).getCellphoneNumber() != null){
            holder.textViewEmployeePhoneNumber.setText(mEmployeeList.get(position).getCellphoneNumber());
        } else if (mEmployeeList.get(position).getCellphoneNumber() == null && mEmployeeList.get(position).getPhoneNumber() == null) {
            holder.textViewEmployeePhoneNumber.setText("");
        }
        holder.textViewEmployeeEmail.setText(mEmployeeList.get(position).getEmail());

        holder.textViewEmployeeEmail.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/html");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[] {holder.textViewEmployeeEmail.getText().toString()});
            intent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
            intent.putExtra(Intent.EXTRA_TEXT, "I'm email body.");
            mContext.startActivity(Intent.createChooser(intent, "Send Email"));
        });

        holder.textViewEmployeePhoneNumber.setOnClickListener(view -> {
            String uri = "tel:" + holder.textViewEmployeePhoneNumber.getText().toString().trim();
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse(uri));
            mContext.startActivity(intent);
        });
    }

    public void updateAdapter(ArrayList<EmployeeModel> newList){
        mEmployeeList = newList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mEmployeeList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewEmployeeName, textViewEmployeePhoneNumber, textViewEmployeeEmail;

        ViewHolder(View itemView) {
            super(itemView);
            textViewEmployeeName = itemView.findViewById(R.id.textViewEmployeeName);
            textViewEmployeePhoneNumber = itemView.findViewById(R.id.textViewEmployeePhoneNumber);
            textViewEmployeeEmail = itemView.findViewById(R.id.textViewEmployeeEmail);
        }
    }
}
