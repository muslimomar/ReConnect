package com.example.william.reconnect.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.william.reconnect.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Instructions extends Fragment {

    @BindView(R.id.first_tv)
    TextView firstTv;
    @BindView(R.id.second_tv)
    TextView secondTv;
    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_instructions, container, false);
        unbinder = ButterKnife.bind(this, view);

        secondTv.setText(Html.fromHtml("1.\t<b>Music/Notification</b>: automatic music will play every hour - selected or random. When you hear the music, close your eyes and focus within. Or, you can choose a sound to notify you of the start and end of each minute.<br/>\n" +
                "2.\t<b>Chakra/Body</b> : focus on the chakra colour and it’s placement within your body. You can imagine it pulsating and can make a sound on breathing out. Note: they are centrally positioned within the body, along the spine.<br/>\n" +
                "•\t<b>Beginner</b>: imagine the chakra colour within you and surrounding you, or simply bring your attention to this area of your body.<br/>\n" +
                "•\t<b>Intermediate</b>: see the colour and placement of the chakra and imagine it pulsating, vibrating or gently spinning within you.<br/>\n" +
                "•\t<b>Advanced</b>: imagine the spinning chakra as part of your whole spine, linking to other chakras; as you breathe out, make a longish sound that resonates with this chakra.<br/>\n" +
                "3.\t<b>Mantra/Affirmation</b>: \n" +
                "Mantras and affirmations are used to re-programme the brain and affect the unconscious. Here are four ways to work with these: <br/>\n" +
                "•\t<b>Contemplate it</b>: reflect on the meaning and tune in to particular words to absorb them.<br/>\n" +
                "•\t<b>Recite it</b>: repeatedly say it out loud or inwardly, imagining that each time you say it, it gets embedded in you. It is very potent to whisper it – whispering forces the unconscious to tune-in, creating a different vibration.<br/>\n" +
                "•\t<b>Visualise it</b>: see it actually happening. Create a movie - add all sorts of detail and embellishment, make it big, over exaggerate.<br/>\n" +
                "•\t<b>Breath it</b>: inhale the words on every ‘in-breath’, and exhale with</b> happiness on the out-breath releasing any natural sounds.<br/>\n" +
                "<b>Create Your Own</b>\n" +
                "You can create your own mantra/affirmation to strengthen or manifest anything in you and your life, make sure your words are in the ‘here and now’ as if already happening; Or you can input one that you already know. <br/>\n" +
                "4.\t<b>Silence/Non-talking Day</b>\n" +
                "Talking burns energy and takes us away from ourselves, whilst being silent collects energy and is a step towards quietening the mind. \n" +
                "<br/>Choose a less busy day to practice Silence/Non-Talking. You can still do the usual things but without added talking. Let people know in advance: ask them not to start conversations and only communicate if necessary. You can show people your ‘Silence’ sign.\n" +
                "<br/>Ask people to join you. Doing this can enhance a relationship and in groups, collectively raises the energetic vibration. \n" +
                "<br/><br/>Benefits of Silence:<br/>\n" +
                "•\tHeightened awareness <br/>\n" +
                "•\tMore processing of thoughts & feelings<br/>\n" +
                "•\tEmotional balance and stability<br/>\n" +
                "•\tEnhanced stress tolerance<br/>\n" +
                "•\tGreater focused attention<br/>\n" +
                "•\tMind over matter: discipline & control<br/>\n" +
                "•\tReserve of vital energy (Chi/Ki)<br/>\n" +
                "•\tDeeper relational connections<br/>\n" +
                "•\tShines a light on one’s inner world\n" +
                "\n" +
                "\n"));

        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
