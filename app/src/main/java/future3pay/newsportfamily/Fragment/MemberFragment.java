package future3pay.newsportfamily.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;

import future3pay.newsportfamily.FacebookLogin;
import future3pay.newsportfamily.GoogleLogin;
import future3pay.newsportfamily.Activity.BettingRecordActivity;
import future3pay.newsportfamily.Activity.MakeUpPointRecordActivity;
import future3pay.newsportfamily.Activity.UseRecordActivity;
import future3pay.newsportfamily.Activity.UserInfoActivity;
import future3pay.newsportfamily.Activity.WithdrawalRecordActivity;
import future3pay.newsportfamily.Index;
import future3pay.newsportfamily.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MemberFragment extends Fragment {
    public static WeakReference<MemberFragment> WeakMemberFragment;
    public TextView FamilyPoint, CashPoint, InviteCode, WaitOrder, WaitBonus, TodayOrderCount;
    private Button Logout, UserInfo, BettingRecord, UseRecord, MakeUpPointRecord, WithdrawalRecord;

    public MemberFragment() {

        // Required empty public constructor


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_member, container, false);

        WeakMemberFragment = new WeakReference<>(this);

        FamilyPoint = view.findViewById(R.id.FamilyPoint);
        CashPoint = view.findViewById(R.id.CashPoint);
        InviteCode = view.findViewById(R.id.InviteCode);
        WaitOrder = view.findViewById(R.id.WaitOrder);
        WaitBonus = view.findViewById(R.id.WaitBonus);
        TodayOrderCount = view.findViewById(R.id.TodayOrderCount);

        Logout = view.findViewById(R.id.Logout);
        UserInfo = view.findViewById(R.id.UserInfo);
        BettingRecord = view.findViewById(R.id.BettingRecord);
        UseRecord = view.findViewById(R.id.UseRecord);
        MakeUpPointRecord = view.findViewById(R.id.MakeUpPointRecord);
        WithdrawalRecord = view.findViewById(R.id.WithdrawalRecord);


        Logout.setOnClickListener(logout);
        UserInfo.setOnClickListener(userInfo);
        BettingRecord.setOnClickListener(brttingRecord);
        UseRecord.setOnClickListener(useRecord);
        MakeUpPointRecord.setOnClickListener(makeUpPointRecord);
        WithdrawalRecord.setOnClickListener(withdrawalRecord);



        return view;
    }


    private Button.OnClickListener logout = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {

            GoogleLogin.signOut();
            GoogleLogin.revokeAccess();
            FacebookLogin.signOut();

            Index.WeakIndex.get().UserInfo.edit().clear().apply();

            Index.WeakIndex.get().bottomNavigation.setCurrentItem(0);

            Toast message = Toast.makeText(Index.WeakIndex.get(), "已登出", Toast.LENGTH_SHORT);
            message.setGravity(Gravity.CENTER, 0, 0);
            message.show();
        }

    };

    private Button.OnClickListener userInfo = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent =new Intent();
            intent.setClass(MemberFragment.WeakMemberFragment.get().getContext(),UserInfoActivity.class);
            startActivity(intent);
        }

    };

    private Button.OnClickListener brttingRecord = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent =new Intent();
            intent.setClass(MemberFragment.WeakMemberFragment.get().getContext(),BettingRecordActivity.class);
            startActivity(intent);
        }

    };

    private Button.OnClickListener useRecord = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent =new Intent();
            intent.setClass(MemberFragment.WeakMemberFragment.get().getContext(),UseRecordActivity.class);
            startActivity(intent);
        }

    };

    private Button.OnClickListener makeUpPointRecord = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent =new Intent();
            intent.setClass(MemberFragment.WeakMemberFragment.get().getContext(),MakeUpPointRecordActivity.class);
            startActivity(intent);
        }

    };

    private Button.OnClickListener withdrawalRecord = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent =new Intent();
            intent.setClass(MemberFragment.WeakMemberFragment.get().getContext(),WithdrawalRecordActivity.class);
            startActivity(intent);
        }

    };

}
