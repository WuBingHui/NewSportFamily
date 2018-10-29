package future3pay.newsportfamily.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import future3pay.newsportfamily.API.SportTypeAPI;
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

                        GameResultAPI.GameResul(Index.WeakIndex.get().GameType.get(GameResultType.getSelectedItemPosition()),SwitchDate());

                    }
                });




    }


}
