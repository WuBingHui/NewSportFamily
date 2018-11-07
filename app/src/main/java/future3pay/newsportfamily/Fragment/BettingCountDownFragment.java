package future3pay.newsportfamily.Fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cy.cyrvadapter.adapter.RVAdapter;
import com.cy.cyrvadapter.refreshrv.BaseRefreshLayout;
import com.cy.cyrvadapter.refreshrv.VerticalRefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;

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
import future3pay.newsportfamily.Bean.GameCountDownActiveBean;
import future3pay.newsportfamily.Bean.GameCountDownBean;
import future3pay.newsportfamily.FormaTimeData;
import future3pay.newsportfamily.Index;
import future3pay.newsportfamily.R;
import future3pay.newsportfamily.UIkit.Loading;

/**
 * A simple {@link Fragment} subclass.
 */
public class BettingCountDownFragment extends Fragment {

    public static WeakReference<BettingCountDownFragment> WeakBettingCountDown;

    public List<GameCountDownBean> GameCountDownList;

    public List<GameCountDownActiveBean> GameCountDownActiveList;

    public TextView CountDownAway, CountDownHome, DayTenDigits, DayDigits, HourTenDigits, HourDigits, MinTenDigits, MinDigits, SecTenDigits, SecDigits, NoCountDown;

    public ScheduledExecutorService scheduledThreadPool,scheduledThreadPool2;

    public ConstraintLayout CountdownNext;

    public RVAdapter<GameCountDownActiveBean> GameCountDownActiveAdapter;

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
        GameCountDownActiveList = new ArrayList<>();
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
        NoCountDown = view.findViewById(R.id.NoCountDown);

        CountdownNext = view.findViewById(R.id.CountdownNext);
        CountDownRV = view.findViewById(R.id.CountDownRV);

        NoCountDown.setVisibility(View.GONE);
        CountDownRV.setVisibility(View.GONE);




        StartActiveAPI();


        GameActive();
        GmaeNext();

        return view;

    }

    public void StartActiveAPI(){
        scheduledThreadPool2 = Executors.newScheduledThreadPool(5);
        scheduledThreadPool2.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {

                GameConutDownActiveAPI.GameConutDownActive();
            }

        }, 0, 2, TimeUnit.SECONDS);

    }



    //場中倒數計時
    public void GmaeNext() {

        if (GameCountDownList.size() > 0) {
            scheduledThreadPool = Executors.newScheduledThreadPool(5);
            scheduledThreadPool.scheduleAtFixedRate(new Runnable() {

                @Override
                public void run() {

                    long NowTimeStamp = (System.currentTimeMillis() / 1000);//獲取時間戳記
                    long GameTimeStamp = (Long.parseLong(GameCountDownList.get(0).getGameStartTime()) / 1000);
                    long SumSec = GameTimeStamp - NowTimeStamp;
                    final long day = SumSec / 60 / 60 / 24;
                    final long hour = (SumSec / 60 / 60) - (day * 24);
                    final long min = (SumSec / 60) % 60;
                    final long sec = SumSec - (((SumSec / 60 / 60) * 60 + min) * 60);
                    WeakBettingCountDown.get().getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if (day <= 0 && hour <= 0 && min <= 0 && sec <= 0) {
                                StartActiveAPI();//啟動正在進行的場中
                                scheduledThreadPool.shutdown();
                            }else{
                                if (!String.valueOf(DayTenDigits.getText()).equals(String.format("%02d", day).substring(0, 1))) {
                                    DayTenDigits.setText(String.format("%02d", day).substring(0, 1));
                                }
                                if (!String.valueOf(DayDigits.getText()).equals(String.format("%02d", day).substring(1))) {
                                    DayDigits.setText(String.format("%02d", day).substring(1));
                                }

                                if (!String.valueOf(HourTenDigits.getText()).equals(String.format("%02d", hour).substring(0, 1))) {
                                    HourTenDigits.setText(String.format("%02d", hour).substring(0, 1));
                                }
                                if (!String.valueOf(HourDigits.getText()).equals(String.format("%02d", hour).substring(1))) {
                                    HourDigits.setText(String.format("%02d", hour).substring(1));
                                }

                                if (!String.valueOf(MinTenDigits.getText()).equals(String.format("%02d", min).substring(0, 1))) {
                                    MinTenDigits.setText(String.format("%02d", min).substring(0, 1));
                                }
                                if (!String.valueOf(MinDigits.getText()).equals(String.format("%02d", min).substring(1))) {
                                    MinDigits.setText(String.format("%02d", min).substring(1));
                                }

                                if (!String.valueOf(SecTenDigits.getText()).equals(String.format("%02d", sec).substring(0, 1))) {
                                    SecTenDigits.setText(String.format("%02d", sec).substring(0, 1));
                                }
                                if (!String.valueOf(SecDigits.getText()).equals(String.format("%02d", sec).substring(1))) {
                                    SecDigits.setText(String.format("%02d", sec).substring(1));
                                }
                            }


                        }
                    });


                }

            }, 0, 1, TimeUnit.SECONDS);
        }

    }


    //正在進行的場中賽事
    public void GameActive() {

        GameCountDownActiveAdapter = new RVAdapter<GameCountDownActiveBean>(GameCountDownActiveList) {
            @Override
            public void bindDataToView(RVViewHolder holder, int position, GameCountDownActiveBean bean, boolean isSelected) {


                try {

                    JSONArray Result = new JSONArray(bean.getResult());


                    //Result.getJSONObject(position).getString("type");

                    // Result.getJSONObject(position).getString("status");

                    // Result.getJSONObject(position).getString("id");


                    holder.setText(R.id.GameCode, Result.getJSONObject(position).getString("code"));
                    holder.setText(R.id.GameCategory, Result.getJSONObject(position).getString("category"));
                    holder.setText(R.id.AwayNameBig, Result.getJSONObject(position).getString("away_team"));
                    holder.setText(R.id.HomeNameBig, Result.getJSONObject(position).getString("home_team"));
                    holder.setText(R.id.AwaySum, Result.getJSONObject(position).getString("away_score"));
                    holder.setText(R.id.HomeSum, Result.getJSONObject(position).getString("home_score"));
                    holder.setText(R.id.AwayNameSmall, Result.getJSONObject(position).getString("away_team"));
                    holder.setText(R.id.HomeNameSmall, Result.getJSONObject(position).getString("home_team"));
                    holder.setText(R.id.Sum, Integer.valueOf(Result.getJSONObject(position).getString("away_score")) + Integer.valueOf(Result.getJSONObject(position).getString("home_score")));
                    if (Integer.valueOf(Result.getJSONObject(position).getString("away_score")) > Integer.valueOf(Result.getJSONObject(position).getString("home_score"))) {

                        holder.setText(R.id.WhoWin, "客勝" + String.valueOf(Integer.valueOf(Result.getJSONObject(position).getString("away_score")) - Integer.valueOf(Result.getJSONObject(position).getString("home_score"))) + "分");
                        holder.setTextColor(R.id.AwayNameBig, Color.parseColor("#218838"));
                        holder.setTextColor(R.id.AwaySum, Color.parseColor("#218838"));
                        holder.setTextColor(R.id.HomeNameBig, Color.parseColor("#000000"));
                        holder.setTextColor(R.id.HomeSum, Color.parseColor("#000000"));

                    } else {

                        if (Integer.valueOf(Result.getJSONObject(position).getString("away_score")) < Integer.valueOf(Result.getJSONObject(position).getString("home_score"))) {
                            holder.setText(R.id.WhoWin, "主勝" + String.valueOf(Integer.valueOf(Result.getJSONObject(position).getString("home_score")) - Integer.valueOf(Result.getJSONObject(position).getString("away_score"))) + "分");
                            holder.setTextColor(R.id.HomeNameBig, Color.parseColor("#218838"));
                            holder.setTextColor(R.id.HomeSum, Color.parseColor("#218838"));
                            holder.setTextColor(R.id.AwayNameBig, Color.parseColor("#000000"));
                            holder.setTextColor(R.id.AwaySum, Color.parseColor("#000000"));
                        } else {

                            holder.setText(R.id.WhoWin, "和局");
                            holder.setTextColor(R.id.HomeNameBig, Color.parseColor("#000000"));
                            holder.setTextColor(R.id.HomeSum, Color.parseColor("#000000"));
                            holder.setTextColor(R.id.AwayNameBig, Color.parseColor("#000000"));
                            holder.setTextColor(R.id.AwaySum, Color.parseColor("#000000"));

                        }
                    }


                    SetScoreColor(holder, R.id.AwayOne, R.id.HomeOne, Integer.parseInt(Result.getJSONObject(position).getJSONObject("away_period").getString("1")), Integer.parseInt(Result.getJSONObject(position).getJSONObject("home_period").getString("1")));
                    SetScoreColor(holder, R.id.AwayTwo, R.id.HomeTwo, Integer.parseInt(Result.getJSONObject(position).getJSONObject("away_period").getString("2")), Integer.parseInt(Result.getJSONObject(position).getJSONObject("home_period").getString("2")));
                    SetScoreColor(holder, R.id.AwayThree, R.id.HomeThree, Integer.parseInt(Result.getJSONObject(position).getJSONObject("away_period").getString("3")), Integer.parseInt(Result.getJSONObject(position).getJSONObject("home_period").getString("3")));
                    SetScoreColor(holder, R.id.AwayFour, R.id.HomeFour, Integer.parseInt(Result.getJSONObject(position).getJSONObject("away_period").getString("4")), Integer.parseInt(Result.getJSONObject(position).getJSONObject("home_period").getString("4")));
                    SetScoreColor(holder, R.id.AwayFive, R.id.HomeFive, Integer.parseInt(Result.getJSONObject(position).getJSONObject("away_period").getString("5")), Integer.parseInt(Result.getJSONObject(position).getJSONObject("home_period").getString("5")));
                    SetScoreColor(holder, R.id.AwaySix, R.id.HomeSix, Integer.parseInt(Result.getJSONObject(position).getJSONObject("away_period").getString("6")), Integer.parseInt(Result.getJSONObject(position).getJSONObject("home_period").getString("6")));
                    SetScoreColor(holder, R.id.AwaySeven, R.id.HomeSeven, Integer.parseInt(Result.getJSONObject(position).getJSONObject("away_period").getString("7")), Integer.parseInt(Result.getJSONObject(position).getJSONObject("home_period").getString("7")));
                    SetScoreColor(holder, R.id.AwayEight, R.id.HomeEight, Integer.parseInt(Result.getJSONObject(position).getJSONObject("away_period").getString("8")), Integer.parseInt(Result.getJSONObject(position).getJSONObject("home_period").getString("8")));
                    SetScoreColor(holder, R.id.AwayNine, R.id.HomeNine, Integer.parseInt(Result.getJSONObject(position).getJSONObject("away_period").getString("9")), Integer.parseInt(Result.getJSONObject(position).getJSONObject("home_period").getString("9")));
                    SetScoreColor(holder, R.id.AwayOT, R.id.HomeOT, Integer.parseInt(Result.getJSONObject(position).getJSONObject("away_period").getString("10")), Integer.parseInt(Result.getJSONObject(position).getJSONObject("home_period").getString("10")));

                    SetScoreShow(holder, R.id.AwayOne, R.id.HomeOne, R.id.One, Integer.parseInt(Result.getJSONObject(position).getJSONObject("away_period").getString("1")), Integer.parseInt(Result.getJSONObject(position).getJSONObject("home_period").getString("1")));
                    SetScoreShow(holder, R.id.AwayTwo, R.id.HomeTwo, R.id.Two, Integer.parseInt(Result.getJSONObject(position).getJSONObject("away_period").getString("2")), Integer.parseInt(Result.getJSONObject(position).getJSONObject("home_period").getString("2")));
                    SetScoreShow(holder, R.id.AwayThree, R.id.HomeThree, R.id.Three, Integer.parseInt(Result.getJSONObject(position).getJSONObject("away_period").getString("3")), Integer.parseInt(Result.getJSONObject(position).getJSONObject("home_period").getString("3")));
                    SetScoreShow(holder, R.id.AwayFour, R.id.HomeFour, R.id.Four, Integer.parseInt(Result.getJSONObject(position).getJSONObject("away_period").getString("4")), Integer.parseInt(Result.getJSONObject(position).getJSONObject("home_period").getString("4")));
                    SetScoreShow(holder, R.id.AwayFive, R.id.HomeFive, R.id.Five, Integer.parseInt(Result.getJSONObject(position).getJSONObject("away_period").getString("5")), Integer.parseInt(Result.getJSONObject(position).getJSONObject("home_period").getString("5")));
                    SetScoreShow(holder, R.id.AwaySix, R.id.HomeSix, R.id.Six, Integer.parseInt(Result.getJSONObject(position).getJSONObject("away_period").getString("6")), Integer.parseInt(Result.getJSONObject(position).getJSONObject("home_period").getString("6")));
                    SetScoreShow(holder, R.id.AwaySeven, R.id.HomeSeven, R.id.Seven, Integer.parseInt(Result.getJSONObject(position).getJSONObject("away_period").getString("7")), Integer.parseInt(Result.getJSONObject(position).getJSONObject("home_period").getString("7")));
                    SetScoreShow(holder, R.id.AwayEight, R.id.HomeEight, R.id.Eight, Integer.parseInt(Result.getJSONObject(position).getJSONObject("away_period").getString("8")), Integer.parseInt(Result.getJSONObject(position).getJSONObject("home_period").getString("8")));
                    SetScoreShow(holder, R.id.AwayNine, R.id.HomeNine, R.id.Nine, Integer.parseInt(Result.getJSONObject(position).getJSONObject("away_period").getString("9")), Integer.parseInt(Result.getJSONObject(position).getJSONObject("home_period").getString("9")));
                    SetScoreShow(holder, R.id.AwayOT, R.id.HomeOT, R.id.OT, Integer.parseInt(Result.getJSONObject(position).getJSONObject("away_period").getString("10")), Integer.parseInt(Result.getJSONObject(position).getJSONObject("home_period").getString("10")));


                    holder.setText(R.id.AwayOne, Result.getJSONObject(position).getJSONObject("away_period").getString("1"));
                    holder.setText(R.id.AwayTwo, Result.getJSONObject(position).getJSONObject("away_period").getString("2"));
                    holder.setText(R.id.AwayThree, Result.getJSONObject(position).getJSONObject("away_period").getString("3"));
                    holder.setText(R.id.AwayFour, Result.getJSONObject(position).getJSONObject("away_period").getString("4"));
                    holder.setText(R.id.AwayFive, Result.getJSONObject(position).getJSONObject("away_period").getString("5"));
                    holder.setText(R.id.AwaySix, Result.getJSONObject(position).getJSONObject("away_period").getString("6"));
                    holder.setText(R.id.AwaySeven, Result.getJSONObject(position).getJSONObject("away_period").getString("7"));
                    holder.setText(R.id.AwayEight, Result.getJSONObject(position).getJSONObject("away_period").getString("8"));
                    holder.setText(R.id.AwayNine, Result.getJSONObject(position).getJSONObject("away_period").getString("9"));
                    holder.setText(R.id.AwayOT, Result.getJSONObject(position).getJSONObject("away_period").getString("10"));

                    holder.setText(R.id.HomeOne, Result.getJSONObject(position).getJSONObject("home_period").getString("1"));
                    holder.setText(R.id.HomeTwo, Result.getJSONObject(position).getJSONObject("home_period").getString("2"));
                    holder.setText(R.id.HomeThree, Result.getJSONObject(position).getJSONObject("home_period").getString("3"));
                    holder.setText(R.id.HomeFour, Result.getJSONObject(position).getJSONObject("home_period").getString("4"));
                    holder.setText(R.id.HomeFive, Result.getJSONObject(position).getJSONObject("home_period").getString("5"));
                    holder.setText(R.id.HomeSix, Result.getJSONObject(position).getJSONObject("home_period").getString("6"));
                    holder.setText(R.id.HomeSeven, Result.getJSONObject(position).getJSONObject("home_period").getString("7"));
                    holder.setText(R.id.HomeEight, Result.getJSONObject(position).getJSONObject("home_period").getString("8"));
                    holder.setText(R.id.HomeNine, Result.getJSONObject(position).getJSONObject("home_period").getString("9"));
                    holder.setText(R.id.HomeOT, Result.getJSONObject(position).getJSONObject("home_period").getString("10"));


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }


            @Override
            public int getItemLayoutID(int position, GameCountDownActiveBean bean) {
                return R.layout.game_result_normal_card;

            }


            @Override
            public void onItemClick(int position, GameCountDownActiveBean bean) {


            }

        };

        CountDownRV.setAdapter(WeakBettingCountDown.get().getActivity(), GameCountDownActiveAdapter, getResources().getColor(R.color.topcolor),
                new BaseRefreshLayout.OnCYRefreshListener() {
                    @Override
                    public void onRefresh() {

                        GameConutDownActiveAPI.GameConutDownActive();
                    }

                });


    }

    //設置比分的顏色
    private void SetScoreColor(RVAdapter.RVViewHolder holder, int AwayView, int HomeView, int away, int home) {

        if (away == home && away > 0 && home > 0) {
            holder.setTextColor(AwayView, Color.parseColor("#f44336"));
            holder.setTextColor(HomeView, Color.parseColor("#f44336"));
        } else {
            if (away > home && away > 0 && home == 0) {
                holder.setTextColor(AwayView, Color.parseColor("#f44336"));
                holder.setTextColor(HomeView, Color.parseColor("#000000"));
            } else {
                if (home > away && away == 0 && home > 0) {
                    holder.setTextColor(AwayView, Color.parseColor("#000000"));
                    holder.setTextColor(HomeView, Color.parseColor("#f44336"));
                } else {
                    if (away == 0 && home == 0) {
                        holder.setTextColor(AwayView, Color.parseColor("#000000"));
                        holder.setTextColor(HomeView, Color.parseColor("#000000"));
                    } else {
                        if (away > home) {
                            holder.setTextColor(AwayView, Color.parseColor("#4caf50"));
                            holder.setTextColor(HomeView, Color.parseColor("#f44336"));
                        } else {
                            holder.setTextColor(AwayView, Color.parseColor("#f44336"));
                            holder.setTextColor(HomeView, Color.parseColor("#4caf50"));
                        }
                    }

                }
            }
        }

    }

    //設置比分出現多少局
    private void SetScoreShow(RVAdapter.RVViewHolder holder, int Board, int AwayView, int HomeView, int away, int home) {

        if (away == -1 && home == -1) {
            holder.itemView.findViewById(Board).setVisibility(View.GONE);
            holder.itemView.findViewById(AwayView).setVisibility(View.GONE);
            holder.itemView.findViewById(HomeView).setVisibility(View.GONE);
        } else {
            holder.itemView.findViewById(Board).setVisibility(View.VISIBLE);
            holder.itemView.findViewById(AwayView).setVisibility(View.VISIBLE);
            holder.itemView.findViewById(HomeView).setVisibility(View.VISIBLE);
        }


    }

}
