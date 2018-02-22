package fr.axonaut.axonaut.UI.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

import fr.axonaut.axonaut.Controllers.ApiCallsController;
import fr.axonaut.axonaut.Controllers.DetailController;
import fr.axonaut.axonaut.Models.OpportunityModel;
import fr.axonaut.axonaut.Models.PagerModel;
import fr.axonaut.axonaut.R;
import fr.axonaut.axonaut.UI.Activities.DetailActivity;

public class OpportunityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final String TAG = String.format("Axonaut : %s", getClass().getSimpleName());

    private Context mContext;
    private ArrayList<OpportunityModel> mOpportunityList = new ArrayList<>();
    private ApiCallsController mApiCallsController;
    private int mPipePosition, mPipeStepPosition;

    public OpportunityAdapter(int pipePosition, int pipeStepPosition,ArrayList<OpportunityModel> mOpportunityList, Context context) {
        this.mPipePosition = pipePosition;
        this.mPipeStepPosition = pipeStepPosition;
        this.mOpportunityList = mOpportunityList;
        this.mContext = context;
        notifyDataSetChanged();
        mApiCallsController = ApiCallsController.getInstance();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder;
        switch (viewType){
            case 0:
                viewHolder = new ViewHolder(inflater.inflate(R.layout.opportunity_item, parent, false));
                break;
            case 1:
                viewHolder = new AddOpportunityViewHolder(inflater.inflate(R.layout.add_opportunity_item, parent, false));
                break;
            default: viewHolder = new ViewHolder(inflater.inflate(R.layout.opportunity_item, parent, false));
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case 0:
                ViewHolder viewHolder = (ViewHolder) holder;
                viewHolder.opportunityNameText.setText(mOpportunityList.get(position).getName());
                viewHolder.opportunityAmountText.setText(String.valueOf(mOpportunityList.get(position).getAmount()));
                viewHolder.opportunityCompanyAmount.setText(mOpportunityList.get(position).getCompany().getName());
                viewHolder.itemView.setOnClickListener(view -> {
                    DetailController.getInstance().setOpportunityModel(mOpportunityList.get(position));
                    Intent intent = new Intent(mContext, DetailActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                });
                break;

            case 1:
                AddOpportunityViewHolder addOpportunityViewHolder = (AddOpportunityViewHolder) holder;
                addOpportunityViewHolder.itemView.setOnClickListener(view -> {

                    Dialog dialog = new Dialog(mContext);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.pager_layout);

                    List<PagerModel> pagerModels = new ArrayList<>();
                    pagerModels.add(new PagerModel("1", "First", R.layout.alert_dialog_create_opportunity_1));
                    pagerModels.add(new PagerModel("2", "Second", R.layout.alert_dialog_create_opportunity_2));

                    DialogPagerAdapter adapter = new DialogPagerAdapter(mContext, pagerModels, mPipeStepPosition, mPipePosition);

                    ViewPager pager = dialog.findViewById(R.id.pager);

                    pager.setAdapter(adapter);

                    CirclePageIndicator pageIndicator = dialog.findViewById(R.id.page_indicator);
                    pageIndicator.setFillColor(Color.parseColor("#000000"));
                    pageIndicator.setPageColor(Color.parseColor("#FF0000"));
                    pageIndicator.setViewPager(pager);
                    pageIndicator.setCurrentItem(0);
                    Button backButton = dialog.findViewById(R.id.backButton);
                    Button nextButton = dialog.findViewById(R.id.nextButton);

                    pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                        }

                        @Override
                        public void onPageSelected(int position) {
                            if (position == 0){
                                backButton.setText("Retour");
                            } else {
                                backButton.setText("Précédent");
                            }
                            if (pager.getCurrentItem() == pagerModels.size() - 1){
                                nextButton.setText("Ok");
                            } else {
                                nextButton.setText("Suivant");
                            }

                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {

                        }
                    });


                    backButton.setOnClickListener(view1 -> {
                        if (pager.getCurrentItem() == 0 ){
                            Toast.makeText(mContext, "Retour !", Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        } else {
                            pager.setCurrentItem(pager.getCurrentItem() - 1);
                        }
                    });


                    nextButton.setOnClickListener(view12 -> {
                        if (pager.getCurrentItem() == pagerModels.size() - 1 ){
                            Toast.makeText(mContext, "REUSSIS !", Toast.LENGTH_SHORT).show();
                            adapter.pushOpportunity(mContext, "");
                            dialog.cancel();
                        } else {
                            pager.setCurrentItem(pager.getCurrentItem() + 1);
                        }
                    });

                    dialog.show();

                });
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position != mOpportunityList.size()) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public int getItemCount() {
        return mOpportunityList.size() + 1;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView opportunityNameText, opportunityAmountText, opportunityCompanyAmount;
        ViewHolder(View itemView) {
            super(itemView);
            opportunityNameText = itemView.findViewById(R.id.opportunityNameText);
            opportunityAmountText = itemView.findViewById(R.id.opportunityAmountText);
            opportunityCompanyAmount = itemView.findViewById(R.id.opportunityCompanyAmount);
        }
    }
    class AddOpportunityViewHolder extends RecyclerView.ViewHolder {
        AddOpportunityViewHolder(View itemView) {
            super(itemView);
        }
    }
}

