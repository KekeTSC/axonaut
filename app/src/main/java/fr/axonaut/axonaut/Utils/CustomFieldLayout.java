package fr.axonaut.axonaut.Utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import fr.axonaut.axonaut.R;


public class CustomFieldLayout extends LinearLayout
{
    LayoutInflater inflater = null;
    TextView textViewCustomFieldName, textViewCustomFieldResult;

    public CustomFieldLayout(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        initViews();
    }
    public CustomFieldLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initViews();
    }
    public CustomFieldLayout(Context context)
    {
        super(context);
        initViews();
    }
    void initViews()
    {
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custom_field_layout, this, true);
        textViewCustomFieldName = findViewById(R.id.textViewCustomFieldName);
        textViewCustomFieldResult = findViewById(R.id.textViewCustomFieldResult);
    }

    public void setCustomFieldName(String text){
        textViewCustomFieldName.setText(text);
    }
    public void setCustomFieldResult(String text){
        textViewCustomFieldResult.setText(text);
    }
}