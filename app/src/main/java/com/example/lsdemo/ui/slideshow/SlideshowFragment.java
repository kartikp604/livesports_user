package com.example.lsdemo.ui.slideshow;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.lsdemo.CricketRuleActivity;
import com.example.lsdemo.KabaddiRuleActivity;
import com.example.lsdemo.OtherGameRegActivity;
import com.example.lsdemo.R;
import com.example.lsdemo.VolleyBallRuleActivity;

public class SlideshowFragment extends Fragment implements View.OnClickListener {

    //Rules

    private SlideshowViewModel slideshowViewModel;
    private ImageView cricket_rule_imgview,volleyball_rule_imgview,kabaddi_rule_imgview,othergame_imageview;
    private VolleyBallRuleActivity volleyBallRuleActivity;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_rules, container, false);
        final TextView textView = root.findViewById(R.id.text_Rule);
        slideshowViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });




        cricket_rule_imgview=(ImageView) root.findViewById(R.id.image_rules_cricket);
        volleyball_rule_imgview=(ImageView) root.findViewById(R.id.image_rules_volleyball);
        kabaddi_rule_imgview=(ImageView) root.findViewById(R.id.image_rules_kabaddi);
        othergame_imageview=(ImageView) root.findViewById(R.id.image_rules_chess);


        cricket_rule_imgview.setOnClickListener(this);
        volleyball_rule_imgview.setOnClickListener(this);
        kabaddi_rule_imgview.setOnClickListener(this);
        othergame_imageview.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {


        if(v==cricket_rule_imgview)
        {
            startActivity(new Intent(getContext(), CricketRuleActivity.class));
        }
        if (v==volleyball_rule_imgview)
        {
            Intent i =new Intent(getContext(),VolleyBallRuleActivity.class);
            String game="VolleyBallRules";
            Bundle bundle=new Bundle();
            bundle.putString("game",game);
            i.putExtras(bundle);
            startActivity(i);



//            String game="VolleyBallRules";
//            VolleyBallRuleActivity volleyBallRuleActivity=new VolleyBallRuleActivity(game);
//           startActivity(new Intent(getContext(), VolleyBallRuleActivity.class));
        }
        if (v==kabaddi_rule_imgview)
        {
            Intent i =new Intent(getContext(),VolleyBallRuleActivity.class);
            String game="KabaddiRules";
            Bundle bundle=new Bundle();
            bundle.putString("game",game);
            i.putExtras(bundle);
            startActivity(i);
         // Toast.makeText(getContext(),"Under Construction",Toast.LENGTH_SHORT).show();
         //   startActivity(new Intent(getContext(), VolleyBallRuleActivity.class));
        }
        if(v==othergame_imageview){
            startActivity(new Intent(getContext(), OtherGameRegActivity.class));
        }
    }


}
