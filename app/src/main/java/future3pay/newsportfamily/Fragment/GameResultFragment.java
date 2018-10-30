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
import android.widget.Toast;

import com.cy.cyrvadapter.adapter.RVAdapter;
import com.cy.cyrvadapter.refreshrv.BaseRefreshLayout;
import com.cy.cyrvadapter.refreshrv.VerticalRefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    public GameResultFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_game_result, container, false);

        WeakGameResult = new WeakReference<>(this);

        GameResultRV= view.findViewById(R.id.GameResultRV);

        GameResultType= view.findViewById(R.id.GameResultType);

        GameResultCategory= view.findViewById(R.id.GameResultCategory);

        GameResultDate= view.findViewById(R.id.GameResultDate);

        GameResultType.setOnItemSelectedListener(TypeSelect);

        GameResultCategory.setOnItemSelectedListener(CategorySelect);

        GameResultDate.setOnItemSelectedListener(DateSelect);


        GameResulList = new ArrayList<>();
        GameResulCount = new ArrayList<>();
        SportTypeAPI.SportType();//取球種Api





        GetGameResultInfo();




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



private AdapterView.OnItemSelectedListener TypeSelect = new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        GameResultAPI.GameResul(Index.WeakIndex.get().GameType.get(GameResultFragment.WeakGameResult.get().GameResultType.getSelectedItemPosition()),GameResultFragment.WeakGameResult.get().SwitchDate());
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
};

    private AdapterView.OnItemSelectedListener DateSelect = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            GameResultAPI.GameResul(Index.WeakIndex.get().GameType.get(GameResultFragment.WeakGameResult.get().GameResultType.getSelectedItemPosition()),GameResultFragment.WeakGameResult.get().SwitchDate());
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    private AdapterView.OnItemSelectedListener CategorySelect = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            GetGameResultInfo();

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }

    };




    private void GetGameResultInfo(){



        GameResultAdapter = new RVAdapter<GameResultBean>(GameResulList) {
            @Override
            public void bindDataToView(RVViewHolder holder, int position, GameResultBean bean, boolean isSelected) {


                if(bean.getBetsType().equals("normal") && GameResultCategory.getSelectedItem().equals(bean.getCategory())){
                   // Log.d("aaaaaaaaaaaaa",bean.getCategory());
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

                    if(bean.getBetsType().equals("champion") && GameResultCategory.getSelectedItem().equals(bean.getCategory())){
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

                        GetGameResultInfo();
                        GameResultRV.finishRefreshing();
                    }
                });




    }





}
