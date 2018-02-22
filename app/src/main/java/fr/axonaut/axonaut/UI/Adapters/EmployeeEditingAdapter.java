package fr.axonaut.axonaut.UI.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import fr.axonaut.axonaut.Models.ClientEditingModel;
import fr.axonaut.axonaut.R;

public class EmployeeEditingAdapter extends RecyclerView.Adapter<EmployeeEditingAdapter.ViewHolder> {

    private final String TAG = String.format("Axonaut : %s", getClass().getSimpleName());

    private Context mContext;
    private ArrayList<ClientEditingModel> mList = new ArrayList<>();

    public EmployeeEditingAdapter(Context context, ArrayList<ClientEditingModel> employeeList) {
        this.mContext = context;
        this.mList = employeeList;
    }

    @Override
    public EmployeeEditingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.employee_item, parent, false);
        return new EmployeeEditingAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(EmployeeEditingAdapter.ViewHolder holder, int position) {
        if (mList.get(position).isSelected()){
            holder.textViewEmployeeName.setTextColor(mContext.getResources().getColor(android.R.color.holo_red_light));
        } else {
            holder.textViewEmployeeName.setTextColor(mContext.getResources().getColor(android.R.color.holo_blue_light));
        }
        holder.textViewEmployeeName.setText(mList.get(position).getClientModel().toString());
        if ((mList.get(position).getClientModel().getCellPhoneNumber() == null
                || mList.get(position).getClientModel().getCellPhoneNumber().equals("")) && mList.get(position).getClientModel().getPhoneNumber() != null){
            holder.textViewEmployeePhoneNumber.setText(mList.get(position).getClientModel().getPhoneNumber());
        } else if (mList.get(position).getClientModel().getCellPhoneNumber() != null){
            holder.textViewEmployeePhoneNumber.setText(mList.get(position).getClientModel().getCellPhoneNumber());
        }

        holder.textViewEmployeeEmail.setText(mList.get(position).getClientModel().getEmailAddress());
        holder.itemView.setOnClickListener(view -> {
            mList.get(position).setSelected(!mList.get(position).isSelected());
            notifyDataSetChanged();
        });
    }

    public void updateAdapter(ArrayList<ClientEditingModel> newList){
        mList = newList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public String getSelectedEmployeeIds() {
        StringBuilder resultString = new StringBuilder();
        for (int i = 0; i < mList.size() ; i++) {
            if (i!=0){
                resultString.append(",");
            }
            if (mList.get(i).isSelected()){
                resultString.append(mList.get(i).getClientModel().getId());
            }
        }
        return resultString.toString();
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
