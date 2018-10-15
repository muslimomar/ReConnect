package com.devcora.android.reconnect.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.devcora.android.reconnect.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Instructions extends Fragment {

    @BindView(R.id.second_tv)
    TextView secondTv;
    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_instructions, container, false);
        unbinder = ButterKnife.bind(this, view);

        secondTv.setText(Html.fromHtml("<p><strong>Guidelines</strong></p>\n" +
                "<p>First create a time span for your hourly meditations. You will then receive a reminder each hour - on the hour. Then select how you would like your &lsquo;1 Minute&rsquo; meditations to be, from the following options:</p>\n" +
                "<p><strong>Option 1: Music Alone or Notification Alone</strong></p>\n" +
                "<p>You can opt for music alone to play during each minute - whilst you listen, close your eyes and focus within. Or you can choose a sound to notify you at the start and at the end of each minute, sitting silently without music. These are simple one-minute meditations without focusing on chakras or mantras (below).</p>\n" +
                "<p><strong>Option 2: Chakra or Body Focus (with music or Notification)</strong></p>\n" +
                "<p>You can opt to focus on a particular Chakra - chakra focused, or focus on this part of your body - body focused meditation. Again, you can opt for music during this time, or no music &ndash; just a notification sound at the beginning and the end. Note: chakras are centrally aligned within your body.</p>\n" +
                "<p><strong>Beginners</strong>: imagine the chakra colour within you or surrounding you; or simply bring your attention to this area of your body.</p>\n" +
                "<p><strong>Intermediate</strong>: visualise this living energy centre within your body; or focus on actual physical bodily sensations in this area.</p>\n" +
                "<p><strong>Advanced</strong>: see this chakra in line with the other chakras, spinning like a small coloured &lsquo;ceiling fan&rsquo; within the central circumference of your body; or focus on your body and your breathing, making a long sound on your &lsquo;out breath&rsquo; that intuitively corresponds.</p>\n" +
                "<p><strong>Option 3: Mantra or Affirmation Focus (with music or notification)</strong></p>\n" +
                "<p>These are used to re-programme the mind by affecting the unconscious. There are many ways to work with these. Discover which method works best for you.</p>\n" +
                "<ul>\n" +
                "<li><strong>Contemplate it</strong>: reflect on the meaning of the words, &lsquo;tuning in&rsquo; to particular words that resonate in that moment.</li>\n" +
                "<li><strong>Recite it</strong>: repeatedly say it out loud or inwardly, imagining that each time you say it, it becomes part of you. It is also very potent to whisper it &ndash; whispering forces the unconscious to &lsquo;listen-in&rsquo;, creating a different vibration.</li>\n" +
                "<li><strong>Visualise it</strong>: see it actually happening - like creating a movie, add all sorts of details and embellishments! Have fun with this. Make it big. Over exaggerate it.</li>\n" +
                "<li><strong>Breath it</strong>: inhale the words on every in-breath and use the out breath as you wish &ndash; release or relax.</li>\n" +
                "</ul>\n" +
                "<p><strong>Bonus Customisation</strong></p>\n" +
                "<p>You can also create your own mantra or affirmation according to what you are working on - strengthen something in you or manifest something in your life. Make sure your words are in the &lsquo;Here &amp; Now&rsquo; as if already happening. Make one up or input one that you already know.</p>"));

        secondTv.setTextIsSelectable(true);

        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
