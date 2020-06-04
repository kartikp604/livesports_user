package com.example.lsdemo.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.lsdemo.CricketReportActivity;
import com.example.lsdemo.LiveScoCriActivity;
import com.example.lsdemo.OtherGameRegActivity;
import com.example.lsdemo.R;
import com.example.lsdemo.ui.gallery.GalleryFragment;
import com.example.lsdemo.ui.send.SendFragment;
import com.example.lsdemo.ui.slideshow.SlideshowFragment;
import com.google.android.material.navigation.NavigationView;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private CardView cardLiveScore,cardSchedule,cardRules,cardReport;

    private HomeViewModel homeViewModel;
    private FragmentManager manager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        //final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        //
       // NavigationView navigationView=root.findViewById(R.id.nav_view);
        manager= getFragmentManager();
        
      cardLiveScore=(CardView)root.findViewById(R.id.card_LiveScore_Cricket);
      cardRules=(CardView)root.findViewById(R.id.card_Rules);
      cardReport=(CardView)root.findViewById(R.id.card_Report);
      cardSchedule=(CardView)root.findViewById(R.id.card_schedule);

      cardLiveScore.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              startActivity(new Intent(getContext(), LiveScoCriActivity.class));

          }
      });
      cardSchedule.setOnClickListener(this);
      cardReport.setOnClickListener(this);
      cardRules.setOnClickListener(this);
        return root;


    }
    //

    //

    @Override
    public void onClick(View v) {
        if(v==cardRules){

            SlideshowFragment slideshowFragment=new SlideshowFragment();

            manager.beginTransaction().replace(R.id.nav_host_fragment,slideshowFragment,slideshowFragment.getTag()).commit();



        }
        if(v==cardSchedule){
            GalleryFragment galleryFragment=new GalleryFragment();
            manager.beginTransaction().replace(R.id.nav_host_fragment,galleryFragment,galleryFragment.getTag()).commit();
        }
        if(v==cardReport){
            startActivity(new Intent(getContext(), CricketReportActivity.class));
        }


//        if(v==cardLiveScore){
//
//        }
    }
}