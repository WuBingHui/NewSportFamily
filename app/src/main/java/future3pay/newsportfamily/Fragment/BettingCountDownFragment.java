package future3pay.newsportfamily.Fragment;


import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cy.cyrvadapter.adapter.RVAdapter;
import com.cy.cyrvadapter.refreshrv.VerticalRefreshLayout;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import future3pay.newsportfamily.API.GameConutDownAPI;
import future3pay.newsportfamily.API.GameConutDownActiveAPI;
import future3pay.newsportfamily.Bean.GameChampionInfoDetailBean;
import future3pay.newsportfamily.Bean.GameCountDownBean;
import future3pay.newsportfamily.FormaTimeData;
import future3pay.newsportfamily.R;
import future3pay.newsportfamily.UIkit.Loading;

/**
 * A simple {@link Fragment} subclass.
 */
public class BettingCountDownFragment extends Fragment {
    public static WeakReference<BettingCountDownFragment> WeakBettingCountDown;
    public List<GameCountDownBean> GameCountDownList;
    public TextView CountDownAway, CountDownHome, DayTenDigits, DayDigits, HourTenDigits, HourDigits, MinTenDigits, MinDigits, SecTenDigits, SecDigits,NoCountDown;
    public  ScheduledExecutorService scheduledThreadPool;
    public ConstraintLayout CountdownNext;

    public RVAdapter<GameChampionInfoDetailBean> GameChampionDetailAdapter;
    public VerticalRefreshLayout CountDownRV;


    public BettingCountDownFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_betting_count_down, container, false);
        WeakBettingCountDown = new WeakReference<>(this);
        GameCountDownList = new ArrayList<>();

        CountDownAway = view.findViewById(R.id.CountDownAway);
        CountDownHome = view.findViewById(R.id.CountDownHome);
        DayTenDigits = view.findViewById(R.id.DayTenDigits);
        DayDigits = view.findViewById(R.id.DayDigits);
        HourTenDigits = view.findViewById(R.id.HourTenDigits);
        HourDigits = view.findViewById(R.id.HourDigits);
        MinTenDigits = view.findViewById(R.id.MinTenDigits);
        MinDigits = view.findViewById(R.id.MinDigits);
        SecTenDigits = view.findViewById(R.id.SecTenDigits);
        SecDigits = view.findViewById(R.id.SecDigits);
        NoCountDown= view.findViewById(R.id.NoCountDown);

        CountdownNext= view.findViewById(R.id.CountdownNext);
        CountDownRV= view.findViewById(R.id.CountDownRV);

        NoCountDown.setVisibility(View.GONE);
        CountDownRV.setVisibility(View.GONE);

        GameConutDownActiveAPI.GameConutDownActive();
        GmaeNext();
        return view;

    }


    public void GmaeNext(){


        if(GameCountDownList.size() > 0){
            scheduledThreadPool = Executors.newScheduledThreadPool(5);
            scheduledThreadPool.scheduleAtFixedRate(new Runnable() {

                @Override
                public void run() {

                    long NowTimeStamp = (System.currentTimeMillis()/1000);//獲取時間戳記
                    long GameTimeStamp = (Long.parseLong(GameCountDownList.get(0).getGameStartTime())/1000);
                    long SumSec =GameTimeStamp-NowTimeStamp;
                    final long day = SumSec / 60 / 60 / 24;
                    final long hour = (SumSec / 60 / 60) - (day * 24);
                    final long min = (SumSec / 60) % 60;
                    final long sec = SumSec - (((SumSec / 60 / 60) * 60 + min) * 60);
                    WeakBettingCountDown.get().getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            if(!String.valueOf(DayTenDigits.getText()).equals(String.format("%02d",day).substring(0,1))){
                                DayTenDigits.setText(String.format("%02d",day).substring(0,1));
                            }
                            if(!String.valueOf(DayDigits.getText()).equals(String.format("%02d",day).substring(1))){
                                DayDigits.setText(String.format("%02d",day).substring(1));
                            }

                            if(!String.valueOf(HourTenDigits.getText()).equals(String.format("%02d",hour).substring(0,1))){
                                HourTenDigits.setText(String.format("%02d",hour).substring(0,1));
                            }
                            if(!String.valueOf(HourDigits.getText()).equals(String.format("%02d",hour).substring(1))){
                                HourDigits.setText(String.format("%02d",hour).substring(1));
                            }

                            if(!String.valueOf(MinTenDigits.getText()).equals(String.format("%02d",min).substring(0,1))){
                                MinTenDigits.setText(String.format("%02d",min).substring(0,1));
                            }
                            if(!String.valueOf(MinDigits.getText()).equals(String.format("%02d",min).substring(1))){
                                MinDigits.setText(String.format("%02d",min).substring(1));
                            }

                            if(!String.valueOf(SecTenDigits.getText()).equals(String.format("%02d",sec).substring(0,1))){
                                SecTenDigits.setText(String.format("%02d",sec).substring(0,1));
                            }
                            if(!String.valueOf(SecDigits.getText()).equals(String.format("%02d",sec).substring(1))){
                                SecDigits.setText(String.format("%02d",sec).substring(1));
                            }
                        }
                    });


                }

            }, 0, 1, TimeUnit.SECONDS);
        }


    }



}
