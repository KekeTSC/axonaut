package fr.axonaut.axonaut.Utils;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import fr.axonaut.axonaut.R;

public class ClearableEditText extends RelativeLayout
{
    LayoutInflater inflater = null;
    TextInputEditText edit_text;
    Button btn_clear;
    public ClearableEditText(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
// TODO Auto-generated constructor stub
        initViews();
    }
    public ClearableEditText(Context context, AttributeSet attrs)
    {
        super(context, attrs);
// TODO Auto-generated constructor stub
        initViews();
    }
    public ClearableEditText(Context context)
    {
        super(context);
// TODO Auto-generated constructor stub
        initViews();
    }
    void initViews()
    {
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.clearable_edit_text, this, true);
        edit_text = findViewById(R.id.clearable_edit);
        btn_clear = findViewById(R.id.clearable_button_clear);
        btn_clear.setVisibility(RelativeLayout.INVISIBLE);
        clearText();
        showHideClearButton();
    }
    void clearText()
    {
        btn_clear.setOnClickListener(v -> {
// TODO Auto-generated method stub
            edit_text.setText("");
        });
    }
    void showHideClearButton()
    {
        edit_text.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
// TODO Auto-generated method stub
                if (s.length() > 0)
                    btn_clear.setVisibility(RelativeLayout.VISIBLE);
                else
                    btn_clear.setVisibility(RelativeLayout.INVISIBLE);
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
// TODO Auto-generated method stub
            }
            @Override
            public void afterTextChanged(Editable s)
            {
// TODO Auto-generated method stub
            }
        });
        edit_text.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                if (edit_text.getText().length() > 0) {
                    btn_clear.setVisibility(RelativeLayout.VISIBLE);
                } else {
                    btn_clear.setVisibility(RelativeLayout.INVISIBLE);
                }
            } else {
                btn_clear.setVisibility(RelativeLayout.INVISIBLE);
            }
        });
    }
    public Editable getText()
    {
        return edit_text.getText();
    }

    public void setText(String text){
        edit_text.setText(text);
    }
}