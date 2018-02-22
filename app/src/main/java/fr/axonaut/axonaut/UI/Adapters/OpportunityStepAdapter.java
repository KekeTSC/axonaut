package fr.axonaut.axonaut.UI.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.ArrayList;

import fr.axonaut.axonaut.Models.PipeStepWithOpportunityModel;
import fr.axonaut.axonaut.R;

public class OpportunityStepAdapter extends RecyclerView.Adapter<OpportunityStepAdapter.ViewHolder>{

    private final String TAG = String.format("Axonaut : %s", getClass().getSimpleName());

    private Context mContext;
    private int mPipePosition;
    private ArrayList<PipeStepWithOpportunityModel> mPipeStepWithOpportunityList = new ArrayList<>();

    public OpportunityStepAdapter(Context context, int pipePosition) {
        this.mContext = context;
        this.mPipePosition = pipePosition;
    }

    @Override
    public OpportunityStepAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.opportunity_state_item, parent, false);
        return new OpportunityStepAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(OpportunityStepAdapter.ViewHolder holder, int position) {
        holder.pipeStepNameText.setText(mPipeStepWithOpportunityList.get(position).getName());
        Drawable background = holder.pipeStepNameText.getBackground();
        if (background instanceof ShapeDrawable) {
            ((ShapeDrawable)background).getPaint().setColor(Color.parseColor("#" + mPipeStepWithOpportunityList.get(position).getColorHex()));
        } else if (background instanceof GradientDrawable) {
            ((GradientDrawable)background).setColor(Color.parseColor("#" + mPipeStepWithOpportunityList.get(position).getColorHex()));
        } else if (background instanceof ColorDrawable) {
            ((ColorDrawable)background).setColor(Color.parseColor("#" + mPipeStepWithOpportunityList.get(position).getColorHex()));
        }
        holder.recyclerView.setLayoutManager( new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        if (mPipeStepWithOpportunityList.get(position).getOpportunityList() != null) {
            holder.recyclerView.setAdapter(new OpportunityAdapter(mPipePosition, position,mPipeStepWithOpportunityList.get(position).getOpportunityList(), mContext));
        }else {
            mPipeStepWithOpportunityList.get(position).setOpportunityList(new ArrayList<>());
            holder.recyclerView.setAdapter(new OpportunityAdapter(mPipePosition, position,mPipeStepWithOpportunityList.get(position).getOpportunityList(), mContext));
        }
    }

    public void updateAdapter(ArrayList<PipeStepWithOpportunityModel> list, int pipePosition) {
        mPipeStepWithOpportunityList = list;
        mPipePosition = pipePosition;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return mPipeStepWithOpportunityList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;
        TextView pipeStepNameText;

        ViewHolder(View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.opportunityRecycler);
            pipeStepNameText = itemView.findViewById(R.id.pipeStepNameText);
        }
    }
}
