package future3pay.newsportfamily.Fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cy.cyrvadapter.adapter.RVAdapter;
import com.cy.cyrvadapter.refreshrv.BaseRefreshLayout;
import com.cy.cyrvadapter.refreshrv.VerticalRefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import future3pay.newsportfamily.API.GameChampionInfoAPI;
import future3pay.newsportfamily.API.GameResultAPI;
import future3pay.newsportfamily.API.SportTypeAPI;
import future3pay.newsportfamily.Bean.GameChampionInfoBean;
import future3pay.newsportfamily.Bean.GameResultBean;
import future3pay.newsportfamily.FormaData;
import future3pay.newsportfamily.Index;
import future3pay.newsportfamily.R;
import future3pay.newsportfamily.UIkit.Loading;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameResultFragment extends Fragment {


    public static WeakReference<GameResultFragment> WeakGameResult;

    public List<GameResultBean> GameResulList;
    public List<GameResultBean>  GameResulCount;
    public RVAdapter<GameResultBean> GameResultAdapter;

    public VerticalRefreshLayout GameResultRV;

    public Spinner GameResultType,GameResultCategory,GameResultDate;

    public TextView NoGame;

    public GameResultFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_game_result, container, false);

        WeakGameResult = new WeakReference<>(this);

        NoGame= view.findViewById(R.id.NoGame);

        GameResultRV= view.findViewById(R.id.GameResultRV);

        GameResultType= view.findViewById(R.id.GameResultType);

        GameResultCategory= view.findViewById(R.id.GameResultCategory);

        GameResultDate= view.findViewById(R.id.GameResultDate);



        NoGame.setVisibility(View.GONE);

        GameResulList = new ArrayList<>();

        GameResulCount = new ArrayList<>();

        //SportTypeAPI.SportType();//取球種Api










        return  view;
    }



    public String SwitchDate(){
            String time ="A";
        switch (GameResultDate.getSelectedItem().toString()){
            case "1天":
                time ="A";
            break;
            case "3天":
                time ="B";
                break;
            case "1週":
                time ="C";
                break;

            case "3週":
                time ="D";
                break;
            case "1月":
                time ="E";
                break;
            case "2月":
                time ="F";
                break;
            case "3月":
                time ="G";
                break;

        }
        return time;
    }



    public AdapterView.OnItemSelectedListener TypeSelect = new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        GameResultRV.setVisibility(View.GONE);
        GameResultAPI.GameResul(Index.WeakIndex.get().GameType.get(GameResultFragment.WeakGameResult.get().GameResultType.getSelectedItemPosition()),GameResultFragment.WeakGameResult.get().SwitchDate());

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
};

    public AdapterView.OnItemSelectedListener DateSelect = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            GameResultRV.setVisibility(View.GONE);

            GameResultAPI.GameResul(Index.WeakIndex.get().GameType.get(GameResultFragment.WeakGameResult.get().GameResultType.getSelectedItemPosition()),GameResultFragment.WeakGameResult.get().SwitchDate());


        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    public AdapterView.OnItemSelectedListener CategorySelect = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            GetGameResultInfo();

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }

    };




    public void GetGameResultInfo(){

        GameResulCount.clear();

        for(int i = 0;i<GameResulList.size();i++){
            if(GameResultCategory.getSelectedItem().equals( GameResulList.get(i).getCategory())){
                GameResulCount.add(new GameResultBean(
                        GameResulList.get(i).getBetsType(),
                        GameResulList.get(i).getGameStartTime(),
                        GameResulList.get(i).getCategory(),
                        GameResulList.get(i).getTi(),
                        GameResulList.get(i).getAwayTeam(),
                        GameResulList.get(i).getHomeTeam(),
                        GameResulList.get(i).getAwayScore(),
                        GameResulList.get(i).getHomeScore(),
                        GameResulList.get(i).getAwayPeriod(),
                        GameResulList.get(i).getHomePeriod(),
                        GameResulList.get(i).getFinialResult(),
                        GameResulList.get(i).getNi(),
                        GameResulList.get(i).getCode(),
                        GameResulList.get(i).getType(),
                        GameResulList.get(i).getStatus()
                ));
            }


        }

        GameResultAdapter = new RVAdapter<GameResultBean>(GameResulCount) {
            @Override
            public void bindDataToView(RVViewHolder holder, int position, GameResultBean bean, boolean isSelected) {


                if(bean.getBetsType().equals("normal")){

                    holder.setText(R.id.GameCode, bean.getCode());
                    holder.setText(R.id.GameCategory, bean.getCategory());
                    holder.setText(R.id.GameStartTime, FormaData.formatData("MM月dd日 HH:mm", Long.parseLong(bean.getGameStartTime())));
                    holder.setText(R.id.AwayNameBig, bean.getAwayTeam());
                    holder.setText(R.id.HomeNameBig, bean.getHomeTeam());
                    holder.setText(R.id.AwaySum, bean.getAwayScore());
                    holder.setText(R.id.HomeSum, bean.getHomeScore());
                    holder.setText(R.id.AwayNameSmall, bean.getAwayTeam());
                    holder.setText(R.id.HomeNameSmall, bean.getHomeTeam());
                    holder.setText(R.id.Sum, Integer.valueOf(bean.getAwayScore())+Integer.valueOf(bean.getHomeScore()));
                    if(Integer.valueOf(bean.getAwayScore()) > Integer.valueOf(bean.getHomeScore())){

                        holder.setText(R.id.WhoWin, "客勝"+String.valueOf(Integer.valueOf(bean.getAwayScore()) - Integer.valueOf(bean.getHomeScore()))+"分");
                        holder.setTextColor(R.id.AwayNameBig,Color.parseColor("#218838"));
                        holder.setTextColor(R.id.AwaySum,Color.parseColor("#218838"));
                        holder.setTextColor(R.id.HomeNameBig,Color.parseColor("#000000"));
                        holder.setTextColor(R.id.HomeSum,Color.parseColor("#000000"));

                    }else{

                        if(Integer.valueOf(bean.getAwayScore()) < Integer.valueOf(bean.getHomeScore())){
                            holder.setText(R.id.WhoWin, "主勝"+String.valueOf(Integer.valueOf(bean.getHomeScore()) - Integer.valueOf(bean.getAwayScore()))+"分");
                            holder.setTextColor(R.id.HomeNameBig,Color.parseColor("#218838"));
                            holder.setTextColor(R.id.HomeSum,Color.parseColor("#218838"));
                            holder.setTextColor(R.id.AwayNameBig,Color.parseColor("#000000"));
                            holder.setTextColor(R.id.AwaySum,Color.parseColor("#000000"));
                        }else{

                            holder.setText(R.id.WhoWin, "和局");
                            holder.setTextColor(R.id.HomeNameBig,Color.parseColor("#000000"));
                            holder.setTextColor(R.id.HomeSum,Color.parseColor("#000000"));
                            holder.setTextColor(R.id.AwayNameBig,Color.parseColor("#000000"));
                            holder.setTextColor(R.id.AwaySum,Color.parseColor("#000000"));

                        }
                    }

                    try {

                        JSONObject AwayScore = new JSONObject(bean.getAwayPeriod());
                        JSONObject HomeScore = new JSONObject(bean.getHomePeriod());


                        SetScoreColor(holder,R.id.AwayOne,R.id.HomeOne,Integer.parseInt(AwayScore.getString("1")),Integer.parseInt(HomeScore.getString("1")));
                        SetScoreColor(holder,R.id.AwayTwo,R.id.HomeTwo,Integer.parseInt(AwayScore.getString("2")),Integer.parseInt(HomeScore.getString("2")));
                        SetScoreColor(holder,R.id.AwayThree,R.id.HomeThree,Integer.parseInt(AwayScore.getString("3")),Integer.parseInt(HomeScore.getString("3")));
                        SetScoreColor(holder,R.id.AwayFour,R.id.HomeFour,Integer.parseInt(AwayScore.getString("4")),Integer.parseInt(HomeScore.getString("4")));
                        SetScoreColor(holder,R.id.AwayFive,R.id.HomeFive,Integer.parseInt(AwayScore.getString("5")),Integer.parseInt(HomeScore.getString("5")));
                        SetScoreColor(holder,R.id.AwaySix,R.id.HomeSix,Integer.parseInt(AwayScore.getString("6")),Integer.parseInt(HomeScore.getString("6")));
                        SetScoreColor(holder,R.id.AwaySeven,R.id.HomeSeven,Integer.parseInt(AwayScore.getString("7")),Integer.parseInt(HomeScore.getString("7")));
                        SetScoreColor(holder,R.id.AwayEight,R.id.HomeEight,Integer.parseInt(AwayScore.getString("8")),Integer.parseInt(HomeScore.getString("8")));
                        SetScoreColor(holder,R.id.AwayNine,R.id.HomeNine,Integer.parseInt(AwayScore.getString("9")),Integer.parseInt(HomeScore.getString("9")));
                        SetScoreColor(holder,R.id.AwayOT,R.id.HomeOT,Integer.parseInt(AwayScore.getString("10")),Integer.parseInt(HomeScore.getString("10")));

                        SetScoreShow(holder,R.id.AwayOne,R.id.HomeOne,R.id.One,Integer.parseInt(AwayScore.getString("1")),Integer.parseInt(HomeScore.getString("1")));
                        SetScoreShow(holder,R.id.AwayTwo,R.id.HomeTwo,R.id.Two,Integer.parseInt(AwayScore.getString("2")),Integer.parseInt(HomeScore.getString("2")));
                        SetScoreShow(holder,R.id.AwayThree,R.id.HomeThree,R.id.Three,Integer.parseInt(AwayScore.getString("3")),Integer.parseInt(HomeScore.getString("3")));
                        SetScoreShow(holder,R.id.AwayFour,R.id.HomeFour,R.id.Four,Integer.parseInt(AwayScore.getString("4")),Integer.parseInt(HomeScore.getString("4")));
                        SetScoreShow(holder,R.id.AwayFive,R.id.HomeFive,R.id.Five,Integer.parseInt(AwayScore.getString("5")),Integer.parseInt(HomeScore.getString("5")));
                        SetScoreShow(holder,R.id.AwaySix,R.id.HomeSix,R.id.Six,Integer.parseInt(AwayScore.getString("6")),Integer.parseInt(HomeScore.getString("6")));
                        SetScoreShow(holder,R.id.AwaySeven,R.id.HomeSeven,R.id.Seven,Integer.parseInt(AwayScore.getString("7")),Integer.parseInt(HomeScore.getString("7")));
                        SetScoreShow(holder,R.id.AwayEight,R.id.HomeEight,R.id.Eight,Integer.parseInt(AwayScore.getString("8")),Integer.parseInt(HomeScore.getString("8")));
                        SetScoreShow(holder,R.id.AwayNine,R.id.HomeNine,R.id.Nine,Integer.parseInt(AwayScore.getString("9")),Integer.parseInt(HomeScore.getString("9")));
                        SetScoreShow(holder,R.id.AwayOT,R.id.HomeOT,R.id.OT,Integer.parseInt(AwayScore.getString("10")),Integer.parseInt(HomeScore.getString("10")));


                            holder.setText(R.id.AwayOne, AwayScore.getString("1"));
                            holder.setText(R.id.AwayTwo, AwayScore.getString("2"));
                            holder.setText(R.id.AwayThree,AwayScore.getString("3"));
                            holder.setText(R.id.AwayFour,AwayScore.getString("4"));
                            holder.setText(R.id.AwayFive, AwayScore.getString("5"));
                            holder.setText(R.id.AwaySix, AwayScore.getString("6"));
                            holder.setText(R.id.AwaySeven, AwayScore.getString("7"));
                            holder.setText(R.id.AwayEight, AwayScore.getString("8"));
                            holder.setText(R.id.AwayNine, AwayScore.getString("9"));
                            holder.setText(R.id.AwayOT, AwayScore.getString("10"));

                            holder.setText(R.id.HomeOne, HomeScore.getString("1"));
                            holder.setText(R.id.HomeTwo,  HomeScore.getString("2"));
                            holder.setText(R.id.HomeThree,  HomeScore.getString("3"));
                            holder.setText(R.id.HomeFour,  HomeScore.getString("4"));
                            holder.setText(R.id.HomeFive,  HomeScore.getString("5"));
                            holder.setText(R.id.HomeSix,  HomeScore.getString("6"));
                            holder.setText(R.id.HomeSeven,  HomeScore.getString("7"));
                            holder.setText(R.id.HomeEight,  HomeScore.getString("8"));
                            holder.setText(R.id.HomeNine,  HomeScore.getString("9"));
                            holder.setText(R.id.HomeOT,  HomeScore.getString("10"));

                    } catch (JSONException e) {

                        e.printStackTrace();

                    }




                }else{

                    if(bean.getBetsType().equals("champion")){
                        holder.setText(R.id.GameCode, bean.getCode());
                        holder.setText(R.id.GameCategory, bean.getCategory());
                        holder.setText(R.id.GameStartTime, FormaData.formatData("MM月dd日 HH:mm", Long.parseLong(bean.getGameStartTime())));

                        try {

                            JSONArray content = new JSONArray(bean.getFinialResult());

                            holder.setText(R.id.ChampionTitle,content.getJSONObject(0).getString("title"));
                            holder.setText(R.id.ChampionResult,content.getJSONObject(0).getString("option"));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }




            }






            @Override
            public int getItemLayoutID(int position, GameResultBean bean) {



                if(bean.getBetsType().equals("normal")){

                    return R.layout.game_result_normal_card;

                }else{

                    return R.layout.game_result_champion_card;

                }


            }


            @Override
            public void onItemClick(int position, GameResultBean bean) {
                //Toast.makeText(Index.WeakIndex.get(),bean.getCategory(),Toast.LENGTH_SHORT).show();

            }

        };

        GameResultRV.setAdapter(WeakGameResult.get().getContext(),GameResultAdapter,  getResources().getColor(R.color.topcolor),
                new BaseRefreshLayout.OnCYRefreshListener() {
                    @Override
                    public void onRefresh() {

                        GameResultAdapter.notifyDataSetChanged();
                        GameResultRV.finishRefreshing();
                    }
                });




    }

        //設置比分的顏色
        private void SetScoreColor(RVAdapter.RVViewHolder holder, int AwayView, int HomeView, int away, int home) {

            if (away == home && away > 0 && home > 0) {
                holder.setTextColor(AwayView,Color.parseColor("#f44336"));
                holder.setTextColor(HomeView,Color.parseColor("#f44336"));
            } else {
                if (away > home && away > 0 && home == 0) {
                    holder.setTextColor(AwayView,Color.parseColor("#f44336"));
                    holder.setTextColor(HomeView,Color.parseColor("#000000"));
                } else {
                    if (home > away && away == 0 && home > 0) {
                        holder.setTextColor(AwayView,Color.parseColor("#000000"));
                        holder.setTextColor(HomeView,Color.parseColor("#f44336"));
                    } else {
                        if (away == 0 && home == 0) {
                            holder.setTextColor(AwayView,Color.parseColor("#000000"));
                            holder.setTextColor(HomeView,Color.parseColor("#000000"));
                        } else {
                            if (away > home) {
                                holder.setTextColor(AwayView,Color.parseColor("#4caf50"));
                                holder.setTextColor(HomeView,Color.parseColor("#f44336"));
                            } else {
                                holder.setTextColor(AwayView,Color.parseColor("#f44336"));
                                holder.setTextColor(HomeView,Color.parseColor("#4caf50"));
                            }
                        }

                    }
                }
            }

        }

    //設置比分出現多少局
    private void SetScoreShow(RVAdapter.RVViewHolder holder,int Board, int AwayView, int HomeView, int away, int home) {

    if(away == -1 && home == -1){
        holder.itemView.findViewById(Board).setVisibility(View.GONE);
        holder.itemView.findViewById(AwayView).setVisibility(View.GONE);
        holder.itemView.findViewById(HomeView).setVisibility(View.GONE);
    }else{
        holder.itemView.findViewById(Board).setVisibility(View.VISIBLE);
        holder.itemView.findViewById(AwayView).setVisibility(View.VISIBLE);
        holder.itemView.findViewById(HomeView).setVisibility(View.VISIBLE);
    }


    }




}

