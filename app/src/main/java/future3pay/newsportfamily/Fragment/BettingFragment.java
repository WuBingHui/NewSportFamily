package future3pay.newsportfamily.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cy.cyrvadapter.adapter.RVAdapter;
import com.cy.cyrvadapter.refreshrv.BaseRefreshLayout;
import com.cy.cyrvadapter.refreshrv.VerticalRefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import future3pay.newsportfamily.API.GameChampionInfoAPI;
import future3pay.newsportfamily.API.GameNormalInfoAPI;
import future3pay.newsportfamily.Bean.GameChampionInfoBean;
import future3pay.newsportfamily.Bean.GameChampionInfoDetailBean;
import future3pay.newsportfamily.Bean.GameNormalInfoBean;
import future3pay.newsportfamily.Bean.GameNormalInfoDetailBean;
import future3pay.newsportfamily.Index;
import future3pay.newsportfamily.R;
import future3pay.newsportfamily.UIkit.ChampionGameOddDialog;
import future3pay.newsportfamily.UIkit.Loading;
import future3pay.newsportfamily.UIkit.NormalGameOddDialog;


/**
 * A simple {@link Fragment} subclass.
 */
public class BettingFragment extends Fragment {

    public static WeakReference<BettingFragment> WeakBettingFragment;

    public RVAdapter<GameNormalInfoBean> GameNormalAdapter;
    public RVAdapter<GameChampionInfoBean> GameChampionAdapter;
    public RVAdapter<GameNormalInfoDetailBean> GameNormalDetailAdapter;
    public RVAdapter<GameChampionInfoDetailBean> GameChampionDetailAdapter;

    public VerticalRefreshLayout BettingRV;
    public VerticalRefreshLayout BettingDetailRV;

    public  List<GameNormalInfoBean> GameNormalInfoList;
    public  List<GameChampionInfoBean> GameChampionInfoList;
    public  List<GameNormalInfoDetailBean> GameNormalInfoDetailList;
    public  List<GameChampionInfoDetailBean> GameChampionInfoDetailList;

    public TextView NoGame;

    public BettingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        // Fragment剛被建立時執行此方法
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Fragment即將在螢幕上顯示時執行此方法
        View view = inflater.inflate(R.layout.fragment_betting, container, false);
        WeakBettingFragment = new WeakReference<>(this);
        BettingRV= view.findViewById(R.id.BettingRV);
        BettingDetailRV= view.findViewById(R.id.BettingDetailRV);
        NoGame= view.findViewById(R.id.NoGame);

        BettingDetailRV.setVisibility(View.GONE);

        NoGame.setVisibility(View.GONE);

        Loading.start(Index.WeakIndex.get());

        GameNormalInfoList = new ArrayList<>();

        GameChampionInfoList = new ArrayList<>();

        GameNormalInfoDetailList = new ArrayList<>();

        GameChampionInfoDetailList = new ArrayList<>();

        switch (Index.WeakIndex.get().SportRoot){

            case "0":
                GetNormalBetting();
                GameNormalInfoAPI.GameInfo();
                break;
            case "1":
                GetChampionBetting();
                GameChampionInfoAPI.GameInfo();
                break;

        }





        return  view;
    }




    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {

        // 在這個方法中取得並定義Fragment的介面元件
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public void onPause()
    {
        // 當Fragment要從螢幕消失時執行此方法
        super.onPause();
    }



    //一般賽事
    public void GetNormalBetting(){

        GameNormalAdapter = new RVAdapter<GameNormalInfoBean>(GameNormalInfoList) {
            @Override
            public void bindDataToView(RVViewHolder holder, int position, GameNormalInfoBean bean, boolean isSelected) {

                holder.setText(R.id.SportCategory, bean.getCategory());
                holder.setText(R.id.BettingGameCount, bean.getGameLeagth());

            }


            @Override
            public int getItemLayoutID(int position, GameNormalInfoBean bean) {
                return R.layout.betting_item;

            }


            @Override
            public void onItemClick(int position, GameNormalInfoBean bean) {

                Index.WeakIndex.get().bottomNavigation.setVisibility(View.GONE);
                Index.WeakIndex.get().actionbar_textview.setText(bean.getCategory());
                Index.WeakIndex.get().back.setVisibility(View.VISIBLE);
                Index.WeakIndex.get().menu.setVisibility(View.GONE);
                BettingRV.setVisibility(View.GONE);
                BettingDetailRV.setVisibility(View.VISIBLE);


                GetNormalBettingDetail(bean.getGame());



            }

        };

        BettingRV.setAdapter(WeakBettingFragment.get().getContext(),GameNormalAdapter,  getResources().getColor(R.color.topcolor),
                new BaseRefreshLayout.OnCYRefreshListener() {
                    @Override
                    public void onRefresh() {

                        GameNormalInfoAPI.GameInfo();

                    }
                });



    }



    //冠軍賽事
    public void GetChampionBetting(){



        GameChampionAdapter = new RVAdapter<GameChampionInfoBean>(GameChampionInfoList) {
            @Override
            public void bindDataToView(RVViewHolder holder, int position, GameChampionInfoBean bean, boolean isSelected) {

                holder.setText(R.id.SportCategory, bean.getCategory());
                holder.setText(R.id.BettingGameCount, bean.getGameLegth());


            }


            @Override
            public int getItemLayoutID(int position, GameChampionInfoBean bean) {
                return R.layout.betting_item;

            }


            @Override
            public void onItemClick(int position, GameChampionInfoBean bean) {
                Index.WeakIndex.get().bottomNavigation.setVisibility(View.GONE);
                Index.WeakIndex.get().actionbar_textview.setText(bean.getCategory());
                Index.WeakIndex.get().back.setVisibility(View.VISIBLE);
                Index.WeakIndex.get().menu.setVisibility(View.GONE);

                BettingRV.setVisibility(View.GONE);
                BettingDetailRV.setVisibility(View.VISIBLE);

                GetChampionBettingDetail(bean.getGameInfo());

            }

        };

        BettingRV.setAdapter(WeakBettingFragment.get().getContext(),GameChampionAdapter,  getResources().getColor(R.color.topcolor),
                new BaseRefreshLayout.OnCYRefreshListener() {
                    @Override
                    public void onRefresh() {

                        GameChampionInfoAPI.GameInfo();

                    }
                });

    }



    //一般賽事-聯賽內容
    public void GetNormalBettingDetail(String detail){

        try {
            JSONArray content = new JSONArray(detail);
            GameNormalInfoDetailList.clear();
            for(int i = 0 ; i < content.length();i++){

                GameNormalInfoDetailList.add(new GameNormalInfoDetailBean(
                        content.getJSONObject(i).getString("gameStartTime"),
                        content.getJSONObject(i).getString("category"),
                        content.getJSONObject(i).getString("awayTeam"),
                        content.getJSONObject(i).getString("homeTeam"),
                        content.getJSONObject(i).getString("code"),
                        content.getJSONObject(i).getString("ni"),
                        content.getJSONObject(i).getString("si"),
                        content.getJSONObject(i).getString("ti"),
                        content.getJSONObject(i).getString("id"),
                        content.getJSONObject(i).getString("lv"),
                        content.getJSONObject(i).getString("mins"),
                        content.getJSONObject(i).getString("status"),
                        content.getJSONObject(i).getString("diffTime"),
                        content.getJSONObject(i).getString("bets")));
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }



        GameNormalDetailAdapter = new RVAdapter<GameNormalInfoDetailBean>(GameNormalInfoDetailList) {
            @Override
            public void bindDataToView(RVViewHolder holder, int position, GameNormalInfoDetailBean bean, boolean isSelected) {

                holder.setText(R.id.StartDate, bean.getGameStartTime());
                holder.setText(R.id.Code, bean.getCode());
                holder.setText(R.id.Mins, bean.getMins());
                holder.setText(R.id.Away, bean.getAwayTeam());
                holder.setText(R.id.Home, bean.getHomeTeam());
                switch (bean.getMins()){
                    case "1":
                        holder.setText(R.id.MinsType, "單場").setBackgroundResource(R.id.MinsType,R.drawable.corners_7);
                        break;
                    case "2":
                        holder.setText(R.id.MinsType, "一般賽事").setBackgroundResource(R.id.MinsType,R.drawable.corners_6);
                        break;
                    case "3":
                        holder.setText(R.id.MinsType, "一般賽事").setBackgroundResource(R.id.MinsType,R.drawable.corners_6);
                        break;

                }

            }


            @Override
            public int getItemLayoutID(int position, GameNormalInfoDetailBean bean) {

                return R.layout.betting_normal_detail_item;

            }


            @Override
            public void onItemClick(int position, GameNormalInfoDetailBean bean) {


                NormalGameOddDialog.NormalGameOdd(bean.getBets(),bean.getCode(),bean.getMins(),bean.getAwayTeam(),bean.getHomeTeam(),bean.getNi(),bean.getCategory(),bean.getGameStartTime());


            }

        };

        BettingDetailRV.setAdapter(WeakBettingFragment.get().getContext(),GameNormalDetailAdapter,  getResources().getColor(R.color.topcolor),
                new BaseRefreshLayout.OnCYRefreshListener() {
                    @Override
                    public void onRefresh() {

                        GameNormalDetailAdapter.notifyDataSetChanged();
                        BettingDetailRV.finishRefreshing();

                    }

                });



    }


    //冠軍賽事-聯賽內容
    public void GetChampionBettingDetail(String detail){

        try {
            JSONArray content = new JSONArray(detail);
            GameChampionInfoDetailList.clear();
            for(int i = 0 ; i < content.length();i++){

                GameChampionInfoDetailList.add(new GameChampionInfoDetailBean(
                        content.getJSONObject(i).getString("category"),
                        content.getJSONObject(i).getString("betsTitle"),
                        content.getJSONObject(i).getString("codes"),
                        content.getJSONObject(i).getString("mins"),
                        content.getJSONObject(i).getString("gameStartTime"),
                        content.getJSONObject(i).getString("code"))
                );
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }



        GameChampionDetailAdapter = new RVAdapter<GameChampionInfoDetailBean>(GameChampionInfoDetailList) {
            @Override
            public void bindDataToView(RVViewHolder holder, int position, GameChampionInfoDetailBean bean, boolean isSelected) {

                holder.setText(R.id.BetsTitle, bean.getBetsTitle());
                holder.setText(R.id.Mins, bean.getMins());
                holder.setText(R.id.StartDate, bean.getGameStartTime());
                holder.setText(R.id.MinsType, bean.getCode()).setBackgroundResource(R.id.MinsType,R.drawable.corners_8);

            }


            @Override
            public int getItemLayoutID(int position, GameChampionInfoDetailBean bean) {
                return R.layout.betting_champion_detail_item;

            }


            @Override
            public void onItemClick(int position, GameChampionInfoDetailBean bean) {

                ChampionGameOddDialog.ChampionGameOdd(bean.getCodes(),bean.getCode(),bean.getBetsTitle(),bean.getMins(),bean.getCategory(),bean.getGameStartTime());

            }

        };

        BettingDetailRV.setAdapter(WeakBettingFragment.get().getContext(),GameChampionDetailAdapter,  getResources().getColor(R.color.topcolor),
                new BaseRefreshLayout.OnCYRefreshListener() {
                    @Override
                    public void onRefresh() {

                        GameChampionDetailAdapter.notifyDataSetChanged();
                        BettingDetailRV.finishRefreshing();

                    }
                });



    }




}




