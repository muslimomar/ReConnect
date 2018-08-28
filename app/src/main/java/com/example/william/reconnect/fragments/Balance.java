package com.example.william.reconnect.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.william.reconnect.R;
import com.example.william.reconnect.model.SilenceModel;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.realm.Realm;


public class Balance extends Fragment {
    @BindView(R.id.silence_time_spent_iv)
    TextView silenceTimeSpentIv;
    Unbinder unbinder;
    @BindView(R.id.silence_spent_iv)
    TextView silenceSpentIv;
    Realm realm;
    @BindView(R.id.total_meditate_time_tv)
    TextView totalMeditationTime;
    @BindView(R.id.total_time_values)
    TextView totalTimeValues;
    TextView mantraSpentIv;
    @BindView(R.id.crown_usage_tv)
    TextView crownUsageTv;
    TextView chakraSpentIv;
    SilenceModel person;
    @BindView(R.id.good_job)
    LinearLayout goodJob;
    @BindView(R.id.good_job_rv)
    RelativeLayout goodJobRv;
    @BindView(R.id.compliment_card_view)
    CardView complimentCardView;
    @BindView(R.id.crown_usage_pb)
    ProgressBar crownUsagePb;
    @BindView(R.id.third_eye_usage_pb)
    ProgressBar thirdEyeUsagePb;
    @BindView(R.id.throat_usage_pb)
    ProgressBar throatUsagePb;
    @BindView(R.id.heart_usage_pb)
    ProgressBar heartUsagePb;
    @BindView(R.id.solar_plexus_usage_pb)
    ProgressBar solarPlexusUsagePb;
    @BindView(R.id.sacral_usage_pb)
    ProgressBar sacralUsagePb;
    @BindView(R.id.root_usage_pb)
    ProgressBar rootUsagePb;
    @BindView(R.id.third_eye_usage_tv)
    TextView thirdEyeUsageTv;
    @BindView(R.id.throat_usage_tv)
    TextView throatUsageTv;
    @BindView(R.id.heart_usage_tv)
    TextView heartUsageTv;
    @BindView(R.id.solar_plexus_usage_tv)
    TextView solarPlexusUsageTv;
    @BindView(R.id.sacral_usage_tv)
    TextView sacralUsageTv;
    @BindView(R.id.root_usage_tv)
    TextView rootUsageTv;
    private long fullChakraTimeSpent;
    private long mantraFullTimeSpent;
    private long fullMusicTimeSpent;
    private long allTimeSpent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_balance, container, false);
        unbinder = ButterKnife.bind(this, view);
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        person = realm.where(SilenceModel.class).findFirst();
        goodJobCheck();
        realm.commitTransaction();
        getAllChakraTimeSpent();
        getMusicTimeData();
        getSilenceTimeData();
        getMantraTimeSpent();
        getCrownTimeSpent();
        getThirdEyeTimeSpent();
        getThroatTimeSpent();
        getHeartTimeSpent();
        getSolarTimeSpent();
        getSacralTimeSpent();
        getRootTimeSpent();
        allTimeSpent();
        return view;
    }

    private void allTimeSpent() {

        if (person != null) {
            allTimeSpent = fullChakraTimeSpent + fullMusicTimeSpent + mantraFullTimeSpent;
            long hours = allTimeSpent / 3600;
            long minutes = (allTimeSpent % 3600) / 60;
            long seconds = allTimeSpent % 60;
            totalTimeValues.setText(hours + " Hours " + minutes + " min " + seconds + " sec");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void goodJobCheck() {
        if (person == null) {
            complimentCardView.setVisibility(View.GONE);
        } else
            complimentCardView.setVisibility(View.VISIBLE);
    }

    // Get Silence Time Spent Fully working 27-05-2018
    public void getSilenceTimeData() {
        if (person != null) {
            // Get the timespent on Silence Day.
            Long data = person.getSilenceTimeSpent();
            // Convert from seconds to time format
            long hours = data / 3600;
            long minutes = (data % 3600) / 60;
            long seconds = data % 60;
            silenceSpentIv.setText(hours + " Hours " + minutes + " min " + seconds + " sec");
        }
    }

    // Get Silence Music Spent Fully working 27-05-2018
    public void getMusicTimeData() {
        if (person != null) {
            // Get the timespent on Silence Day.
            Long data = person.getMusicTimeSpent();
            // Convert from seconds to time format
            long hours = data / 3600;
            long minutes = (data % 3600) / 60;
            long seconds = data % 60;
            fullMusicTimeSpent = hours + minutes + seconds;
            //musicTxt.setText(hours + " Hours " + minutes + " min " + seconds + " sec");
        }
    }

    // Get Mantra TimeSpent Fully working 27-05-2018
    public void getMantraTimeSpent() {
        if (person != null) {
            // Get the timespent on Silence Day.
            long data = person.getMantraTimeSpent();
            // Convert from seconds to time format
            long hours = data / 3600;
            long minutes = (data % 3600) / 60;
            long seconds = data % 60;

            mantraFullTimeSpent = hours + minutes + seconds;
            //mantraSpentIv.setText(hours + " Hours " + minutes + " min " + seconds + " sec");
        }
    }

    public void getAllChakraTimeSpent() {
        if (person != null) {
            long crownTimeSpent = person.getCrownChakraTimeSpent();
            long thirdEyeTimeSpent = person.getThirdEyeChakraTimeSpent();
            long throatTimeSpent = person.getThroatChakraTimeSpent();
            long heartTimeSpent = person.getHeartChakraTimeSpent();
            long solarPlexusTimeSpent = person.getSolarPlexusChakraTimeSpent();
            long sacralTimeSpent = person.getSacralChakraTimespent();
            long rootTimeSpent = person.getRootChakraTimeSpent();
            fullChakraTimeSpent = crownTimeSpent + thirdEyeTimeSpent + throatTimeSpent + heartTimeSpent + solarPlexusTimeSpent + sacralTimeSpent + rootTimeSpent;

            long hours = fullChakraTimeSpent / 3600;
            long minutes = (fullChakraTimeSpent % 3600) / 60;
            long seconds = fullChakraTimeSpent % 60;
            //chakraSpentIv.setText(hours + " Hours " + minutes + " min " + seconds + " sec");
        }
    }

    public void getCrownTimeSpent() {
        if (person != null) {
            // Get the timespent on crown Chakra Day.
            Long data = person.getCrownChakraTimeSpent();
            if (person.getCrownChakraTimeSpent() != 0) {
                /* Calculate element percentage */
                double finalRWt = ((1.0f * data / fullChakraTimeSpent) * 100);
                DecimalFormat format = new DecimalFormat("0");

                //Toast.makeText(getActivity(), "is : " + format.format(finalRWt), Toast.LENGTH_SHORT).show();
                // Convert from seconds to time format
                long hours = data / 3600;
                long minutes = (data % 3600) / 60;
                long seconds = data % 60;
                crownUsageTv.setText(hours + " Hours " + minutes + " min " + seconds + " sec");
                crownUsagePb.setMax(100);
                crownUsagePb.setProgress(Integer.valueOf(format.format(finalRWt)));

            } else
                crownUsageTv.setText("0:00:00");
        }
    }

    public void getThirdEyeTimeSpent() {
        if (person != null) {
            // Get the timespent on crown Chakra Day.
            Long data = person.getThirdEyeChakraTimeSpent();
            if (person.getThirdEyeChakraTimeSpent() != 0) {
                /* Calculate element percentage */
                double finalRWt = ((1.0f * data / fullChakraTimeSpent) * 100);
                DecimalFormat format = new DecimalFormat("0");

                //Toast.makeText(getActivity(), "is : " + format.format(finalRWt), Toast.LENGTH_SHORT).show();
                // Convert from seconds to time format
                long hours = data / 3600;
                long minutes = (data % 3600) / 60;
                long seconds = data % 60;
                thirdEyeUsageTv.setText(hours + " Hours " + minutes + " min " + seconds + " sec");
                thirdEyeUsagePb.setMax(100);
                thirdEyeUsagePb.setProgress(Integer.valueOf(format.format(finalRWt)));

            } else
                thirdEyeUsageTv.setText("0:00:00");
        }
    }

    public void getThroatTimeSpent() {
        if (person != null) {
            // Get the timespent on crown Chakra Day.
            Long data = person.getThroatChakraTimeSpent();
            if (person.getThroatChakraTimeSpent() != 0) {
                /* Calculate element percentage */
                double finalRWt = ((1.0f * data / fullChakraTimeSpent) * 100);
                DecimalFormat format = new DecimalFormat("0");

                //Toast.makeText(getActivity(), "is : " + format.format(finalRWt), Toast.LENGTH_SHORT).show();
                // Convert from seconds to time format
                long hours = data / 3600;
                long minutes = (data % 3600) / 60;
                long seconds = data % 60;
                throatUsageTv.setText(hours + " Hours " + minutes + " min " + seconds + " sec");
                throatUsagePb.setMax(100);
                throatUsagePb.setProgress(Integer.valueOf(format.format(finalRWt)));
            } else throatUsageTv.setText("0:00:00");

        }
    }

    public void getHeartTimeSpent() {
        if (person != null) {
            // Get the timespent on crown Chakra Day.
            Long data = person.getHeartChakraTimeSpent();
            if (person.getHeartChakraTimeSpent() != 0) {
                /* Calculate element percentage */
                double finalRWt = ((1.0f * data / fullChakraTimeSpent) * 100);
                DecimalFormat format = new DecimalFormat("0");

                //Toast.makeText(getActivity(), "is : " + format.format(finalRWt), Toast.LENGTH_SHORT).show();
                // Convert from seconds to time format
                long hours = data / 3600;
                long minutes = (data % 3600) / 60;
                long seconds = data % 60;
                heartUsageTv.setText(hours + " Hours " + minutes + " min " + seconds + " sec");
                heartUsagePb.setMax(100);
                heartUsagePb.setProgress(Integer.valueOf(format.format(finalRWt)));
            } else heartUsageTv.setText("0:00:00");
        }
    }

    public void getSolarTimeSpent() {
        if (person != null) {
            // Get the timespent on crown Chakra Day.
            Long data = person.getSolarPlexusChakraTimeSpent();
            if (person.getSolarPlexusChakraTimeSpent() != 0) {
                /* Calculate element percentage */
                double finalRWt = ((1.0f * data / fullChakraTimeSpent) * 100);
                DecimalFormat format = new DecimalFormat("0");

                //Toast.makeText(getActivity(), "is : " + format.format(finalRWt), Toast.LENGTH_SHORT).show();
                // Convert from seconds to time format
                long hours = data / 3600;
                long minutes = (data % 3600) / 60;
                long seconds = data % 60;
                solarPlexusUsageTv.setText(hours + " Hours " + minutes + " min " + seconds + " sec");
                solarPlexusUsagePb.setMax(100);
                solarPlexusUsagePb.setProgress(Integer.valueOf(format.format(finalRWt)));
            } else
                solarPlexusUsageTv.setText("0:00:00");
        }
    }

    public void getSacralTimeSpent() {
        if (person != null) {
            // Get the timespent on crown Chakra Day.
            Long data = person.getSacralChakraTimespent();
            if (person.getSacralChakraTimespent() != 0) {
                /* Calculate element percentage */
                double finalRWt = ((1.0f * data / fullChakraTimeSpent) * 100);
                DecimalFormat format = new DecimalFormat("0");
                //Toast.makeText(getActivity(), "is : " + format.format(finalRWt), Toast.LENGTH_SHORT).show();
                // Convert from seconds to time format
                long hours = data / 3600;
                long minutes = (data % 3600) / 60;
                long seconds = data % 60;
                sacralUsageTv.setText(hours + " Hours " + minutes + " min " + seconds + " sec");
                sacralUsagePb.setMax(100);
                sacralUsagePb.setProgress(Integer.valueOf(format.format(finalRWt)));
            } else
                sacralUsageTv.setText("0:00:00");
        }
    }

    public void getRootTimeSpent() {
        if (person != null) {
            // Get the timespent on crown Chakra Day.
            long data = person.getRootChakraTimeSpent();
            if (person.getRootChakraTimeSpent() != 0) {
                /* Calculate element percentage */
                double finalRWt = ((1.0f * data / fullChakraTimeSpent) * 100);
                DecimalFormat format = new DecimalFormat("0");

                //Toast.makeText(getActivity(), "is : " + format.format(finalRWt), Toast.LENGTH_SHORT).show();
                // Convert from seconds to time format
                long hours = data / 3600;
                long minutes = (data % 3600) / 60;
                long seconds = data % 60;
                rootUsageTv.setText(hours + " Hours " + minutes + " min " + seconds + " sec");
                rootUsagePb.setMax(100);
                rootUsagePb.setProgress(Integer.valueOf(format.format(finalRWt)));
            } else
                rootUsageTv.setText("0:00:00");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}