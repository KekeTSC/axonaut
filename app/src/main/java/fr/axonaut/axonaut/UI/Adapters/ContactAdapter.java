package fr.axonaut.axonaut.UI.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import fr.axonaut.axonaut.Models.ClientModel;
import fr.axonaut.axonaut.Models.CompanyForListModel;
import fr.axonaut.axonaut.R;

/**
 * Created by Kelian on 12/02/2018.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder>{

    private final String TAG = String.format("Axonaut : %s", getClass().getSimpleName());

    private Context mContext;
    private ArrayList<CompanyForListModel> mContactList = new ArrayList<>();

    public ContactAdapter(Context context, ArrayList<CompanyForListModel> contactList) {
        this.mContactList = contactList;
        this.mContext = context;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.contact_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textCompanyName.setText(mContactList.get(position).getName());
        holder.textEmployeesNumber.setText(String.valueOf(mContactList.get(position).getEmployees().size()));
    }

    public void updateAdapter(ArrayList<CompanyForListModel> newContactList){
        mContactList = newContactList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mContactList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textCompanyName, textEmployeesNumber;
        ViewHolder(View itemView) {
            super(itemView);
            textCompanyName = itemView.findViewById(R.id.textViewCompanyName);
            textEmployeesNumber = itemView.findViewById(R.id.textViewEmployeesNumber);

        }
    }
}