package future3pay.newsportfamily.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cy.cyrvadapter.adapter.RVAdapter;
import com.cy.cyrvadapter.refreshrv.BaseRefreshLayout;
import com.cy.cyrvadapter.refreshrv.VerticalRefreshLayout;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import future3pay.newsportfamily.API.GameInfoAPI;
import future3pay.newsportfamily.Bean.GameInfoBean;
import future3pay.newsportfamily.Index;
import future3pay.newsportfamily.R;
import future3pay.newsportfamily.UIkit.Loading;


/**
 * A simple {@link Fragment} subclass.
 */
public class BettingFragment extends Fragment {
    public static WeakReference<BettingFragment> WeakBettingFragment;
    public RVAdapter<GameInfoBean> rvAdapter;
    public VerticalRefreshLayout BettingRV;
    public  List<GameInfoBean> GameInfoList;

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

        Loading.start(Index.WeakIndex.get());

        GameInfoList = new ArrayList<>();
        GameInfoAPI.GameInfo();
        GetBetting();




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




    private void GetBetting(){

        rvAdapter = new RVAdapter<GameInfoBean>(GameInfoList) {
            @Override
            public void bindDataToView(RVViewHolder holder, int position, GameInfoBean bean, boolean isSelected) {

                holder.setText(R.id.SportCategory, bean.getCategory());



            }


            @Override
            public int getItemLayoutID(int position, GameInfoBean bean) {
                return R.layout.betting_item;

            }


            @Override
            public void onItemClick(int position, GameInfoBean bean) {
                Toast.makeText(Index.WeakIndex.get(),bean.getCategory(),Toast.LENGTH_SHORT).show();

            }

        };

        BettingRV.setAdapter(WeakBettingFragment.get().getContext(),rvAdapter,  getResources().getColor(R.color.topcolor),
                new BaseRefreshLayout.OnCYRefreshListener() {
                    @Override
                    public void onRefresh() {

                        GameInfoAPI.GameInfo();

                    }
                });

    }


}




