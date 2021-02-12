package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Intent;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddNeighbourActivity extends AppCompatActivity {

    @BindView(R.id.avatar)
    ImageView avatar;
    @BindView(R.id.nameLyt)
    TextInputLayout nameInput;
    @BindView(R.id.phoneNumberLyt)
    TextInputLayout phoneInput;
    @BindView(R.id.addressLyt)
    TextInputLayout addressInput;
    @BindView(R.id.aboutMeLyt)
    TextInputLayout aboutMeInput;
    @BindView(R.id.create)
    MaterialButton addButton;

    private NeighbourApiService mApiService;
    private String mNeighbourImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_neighbour);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mApiService = DI.getNeighbourApiService();
        init();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home : {
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void init() {
        mNeighbourImage = "data:image/jpeg;base64,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";
        Glide.with(this).load(mNeighbourImage).placeholder(R.drawable.ic_account)
                .apply(RequestOptions.circleCropTransform()).into(avatar);
        nameInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                addButton.setEnabled(s.length() > 0);
            }
        });

    }

    @OnClick(R.id.create)
    void addNeighbour() {
        Neighbour neighbour = new Neighbour(
                System.currentTimeMillis(),
                nameInput.getEditText().getText().toString(),
                mNeighbourImage,
                addressInput.getEditText().getText().toString(),
                phoneInput.getEditText().getText().toString(),
                aboutMeInput.getEditText().getText().toString()

        );
        mApiService.createNeighbour(neighbour);
        finish();
    }

    /**
     * Generate a random image. Useful to mock image picker
     * @return String
     */
    String randomImage() {
        return "https://i.pravatar.cc/150?u="+ System.currentTimeMillis();
    }

    /**
     * Used to navigate to this activity
     * @param activity
     */
    public static void navigate(FragmentActivity activity) {
        Intent intent = new Intent(activity, AddNeighbourActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }
}
