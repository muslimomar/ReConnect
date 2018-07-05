package com.example.william.reconnect.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.william.reconnect.R;
import com.example.william.reconnect.model.Chakra;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Credits extends Fragment {
    @BindView(R.id.first_tv)
    TextView firstTv;
    @BindView(R.id.second_tv)
    TextView secondTv;
    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_credits, container, false);
        unbinder = ButterKnife.bind(this, view);

        secondTv.setText(Html.fromHtml("1.\t<b>Notification Sounds</b>:<br/> &nbsp;&nbsp;&nbsp; Bell Tree<br/>&nbsp;&nbsp;&nbsp; Chinese Flute #1<br/>&nbsp;&nbsp;&nbsp; Chinese Flute #2<br/>&nbsp;&nbsp;&nbsp; Harp Sound<br/>&nbsp;&nbsp;&nbsp; Mermaid Singing<br/>&nbsp;&nbsp;&nbsp;&nbsp;<b>Licence: </b>Creative Common - Source:  freesound.org\t<b>&nbsp;&nbsp;&nbsp;&nbsp;felix.blume</b> - \t<b>womb_affliction</b><br/>"+
                "<br/>2.\t<b>Music</b>: <br/> &nbsp;&nbsp;&nbsp; Jason Shaw Acoustuc Meditation <br/>&nbsp;&nbsp;&nbsp; Kevin MacLeod - Sovereign Quarter<br/>&nbsp;&nbsp;&nbsp;&nbsp;Kevin MacLeod Dream Culture<br/>&nbsp;&nbsp;&nbsp; Kevin Macleod Bathed in The Light<br/>&nbsp;&nbsp;&nbsp; Kevin Macleod Windswept<br/>&nbsp;&nbsp;&nbsp; Kevin MacLeod Enchanted Journey<br/>&nbsp;&nbsp;&nbsp; Kevin MacLeod Smoother Moves<br/>&nbsp;&nbsp;&nbsp; Kevin MacLeod Meditation Impromptu<br/>&nbsp;&nbsp;&nbsp;&nbsp;Lee Rosevere Everywhere<br/>&nbsp;&nbsp;&nbsp; Lee Rosevere Betrayal<br/>&nbsp;&nbsp;&nbsp; Lee Rosevere We’ll figure it out together<br/>&nbsp;&nbsp;&nbsp;&nbsp;Lee Rosevere Not My Problem<br/>&nbsp;&nbsp;&nbsp; Ryan Andersen Day to Night <br/>&nbsp;&nbsp;&nbsp;&nbsp;<b>Licence: </b>Creative Common - Source:  freemusicarchive.org"
               ));

        secondTv.setTextIsSelectable(true);

        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
