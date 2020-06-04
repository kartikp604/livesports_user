package com.example.lsdemo.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.lsdemo.CricketScheduleActivity;
import com.example.lsdemo.R;

public class GalleryFragment extends Fragment  implements View.OnClickListener{

    private GalleryViewModel galleryViewModel;
    private Button buttonCricketSchedule,buttonVolleyBallSchedule,buttonKabaddiSchedule;

 //Schedule
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_schedule, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        galleryViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });


        //starting

        buttonCricketSchedule=(Button) root.findViewById(R.id.button_Schedule_cricket);
        buttonKabaddiSchedule=(Button)root.findViewById(R.id.button_Schedule_Kabaddi);
        buttonVolleyBallSchedule=(Button)root.findViewById(R.id.button_Schedule_VolleyBall);

        buttonCricketSchedule.setOnClickListener(this);
        buttonKabaddiSchedule.setOnClickListener(this);
        buttonVolleyBallSchedule.setOnClickListener(this);



        return root;
    }




    @Override
    public void onClick(View v) {
        if(v==buttonCricketSchedule)
        { Intent i =new Intent(getContext(), CricketScheduleActivity.class);

            Bundle bundle=new Bundle();
            bundle.putString("collref","CricketSchedule");

            i.putExtras(bundle);
            startActivity(i);
        }
        if(v==buttonKabaddiSchedule){

            Intent i =new Intent(getContext(), CricketScheduleActivity.class);

            Bundle bundle=new Bundle();
            bundle.putString("collref","KabaddiSchedule");

            i.putExtras(bundle);
            startActivity(i);

        }
        if (v==buttonVolleyBallSchedule){
            Intent i =new Intent(getContext(), CricketScheduleActivity.class);

            Bundle bundle=new Bundle();
            bundle.putString("collref","VolleyBallSchedule");

            i.putExtras(bundle);
            startActivity(i);


        }
    }
}

