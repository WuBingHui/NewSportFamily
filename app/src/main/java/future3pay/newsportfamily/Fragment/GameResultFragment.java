package future3pay.newsportfamily.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.cy.cyrvadapter.adapter.RVAdapter;
import com.cy.cyrvadapter.refreshrv.BaseRefreshLayout;
import com.cy.cyrvadapter.refreshrv.VerticalRefreshLayout;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import future3pay.newsportfamily.API.GameChampionInfoAPI;
import future3pay.newsportfamily.API.GameResultAPI;
import future3pay.newsportfamily.Bean.GameChampionInfoBean;
import future3pay.newsportfamily.Bean.GameResultBean;
import future3pay.newsportfamily.Index;
import future3pay.newsportfamily.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameResultFragment extends Fragment {



    public static WeakReference<GameResultFragment> WeakGameResult;

    public List<GameResultBean> GameResulList;

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

        GameResulList = new ArrayList<>();

        ArrayAdapter<String> List = new ArrayAdapter<>(WeakGameResult.get().getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                Index.WeakIndex.get().GameType);

        GameResultType.setAdapter(List);


        GameResultAPI.GameResul();

        GetGameResultInfo();




        return  view;
    }

    private void GetGameResultInfo(){


        GameResultAdapter = new RVAdapter<GameResultBean>(GameResulList) {
            @Override
            public void bindDataToView(RVViewHolder holder, int position, GameResultBean bean, boolean isSelected) {

                holder.setText(R.id.SportCategory, bean.getCategory());


            }


            @Override
            public int getItemLayoutID(int position, GameResultBean bean) {
                return R.layout.betting_item;

            }


            @Override
            public void onItemClick(int position, GameResultBean bean) {
                Toast.makeText(Index.WeakIndex.get(),bean.getCategory(),Toast.LENGTH_SHORT).show();

            }

        };

        GameResultRV.setAdapter(WeakGameResult.get().getContext(),GameResultAdapter,  getResources().getColor(R.color.topcolor),
                new BaseRefreshLayout.OnCYRefreshListener() {
                    @Override
                    public void onRefresh() {

                        GameResultAPI.GameResul();

                    }
                });




    }


}
