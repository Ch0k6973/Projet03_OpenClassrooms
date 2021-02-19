package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;
import com.openclassrooms.entrevoisins.utils.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NeighbourDetailsActivity extends AppCompatActivity {

    @BindView(R.id.avatarDetail)
    ImageView avatarDetail;
    @BindView(R.id.textNameOnPicture)
    TextView textName2;
    @BindView(R.id.favButton)
    FloatingActionButton favButton;
    @BindView(R.id.textName)
    TextView textName;
    @BindView(R.id.textAddress)
    TextView textAddress;
    @BindView(R.id.textPhoneNumber)
    TextView textNumber;
    @BindView(R.id.textSocial)
    TextView textSocial;
    @BindView(R.id.textAboutTitle)
    TextView textAboutTitle;
    @BindView(R.id.textAboutMe)
    TextView textAbout;

    private NeighbourApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neighbour_details);
        ButterKnife.bind(this);
        mApiService = DI.getNeighbourApiService();
        Neighbour neighbour = getIntent().getParcelableExtra(Constant.NEIGHBOUR_KEY);
        Glide.with(this).load(neighbour.getAvatarUrl()).into(avatarDetail);
        textName2.setText(neighbour.getName());
        textName.setText(neighbour.getName());
        textAddress.setText(neighbour.getAddress());
        textNumber.setText(neighbour.getPhoneNumber());
        textSocial.setText("www.facebook.fr/" + neighbour.getName().toLowerCase());
        textAbout.setText(neighbour.getAboutMe());
        isFavoris(neighbour);

        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mApiService.updateFavorites(neighbour);
                isFavoris(neighbour);
            }
        });
    }

    public void isFavoris(Neighbour neighbour) {
        if(neighbour.isFavoris())
            favButton.setImageResource(R.drawable.ic_star_white_24dp);
        else
            favButton.setImageResource(R.drawable.ic_star_border_white_24dp);
    }

}