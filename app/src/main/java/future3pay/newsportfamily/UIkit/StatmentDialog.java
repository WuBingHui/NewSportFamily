package future3pay.newsportfamily.UIkit;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cy.dialog.BaseDialog;

import future3pay.newsportfamily.Activity.CheckShopCarActivity;
import future3pay.newsportfamily.Index;
import future3pay.newsportfamily.R;

public class StatmentDialog {


    public static void  Statment(){

        final BaseDialog  dialog = new BaseDialog(Index.WeakIndex.get());

        dialog.config(R.layout.statment_and_remark, true);

        TextView Remark = dialog.findViewById(R.id.Remark);
        TextView Statment = dialog.findViewById(R.id.Statment);

        Button StatmentAgree = dialog.findViewById(R.id.StatmentAgree);
        Button StatmentCancel = dialog.findViewById(R.id.StatmentCancel);

        Remark.setText("1.本人知悉並同意於家族網站下注，若家族網站向台灣運彩官方下單成功，賠率是依據本人在家族網站下注時顯示的賠率為準(假設 : 下注時賠率1.75，下注後運彩官網下降至 1.65 若過盤，本網以 1.75計算，賠率上升亦同。)，且派彩點數歸本人，實體彩券歸家族網站所有，無法取回。\n" +
                "2.我同意若因運彩官網盤口變化，本網取消下注。\n" +
                "(因網站更新速度有差異，大小讓分等玩法若與運彩官網不同為避免爭議，本站一律取消該筆下注。)\n" +
                "3.我了解因運彩公司隨時會停止下注，故下注成功與否，依據本網投注資訊為依據。");

        Statment.setText("\t\t\t運動彩券線上投注服務會員同意條款\n" +
                "\n" +
                "\t\t\t『運動彩券線上投注服務』（以下簡稱本服務）由『家族投注網』（以下簡稱本站）提供。\n" +
                "\t\t\t依照發行機構規定，\n" +
                "\t\t\t當單注中獎倍數超過 200 倍者，應按給付全額扣除百分之二十中獎所得稅及千分之四印花稅（合計百分之二十點四）。\n" +
                "\n" +
                "\t\t\t在您完成投注後，本服務將自動比對該期運動彩券所公佈之賽事結果，並將您所中獎的金額撥入您於本站的現金點數戶頭中。\n" +
                "\t\t\t本服務所顯示之賠率為5分鐘內發行單位所公佈之賠率，由於部份賽事賠率變動較快，本服務所顯示之賠率並非最終投注完成之賠率，賽事賠率必須以投注完成後之顯示賠率為準。\n" +
                "\n" +
                "\t\t\t線上投注服務其部份流程需由人員進行操作，可能會有少部份機率導致您的投注單逾時處理或失敗，當造成失敗投注時，本服務將會自動退還該注單的投注費用（包含投注手續費）。但您不得對本公司提出因錯失當期投注而可能中獎的任何要求與賠償。\n" +
                "\n" +
                "\t\t\t若本服務發生以下錯誤時，一切以運動彩券發行機構所公佈之賽事賠率為處理依據：\n" +
                "\t\t\t(1) 當發生本服務自動更新之賽事資訊有誤，而造成獎金發放錯誤時，本公司有權追回錯誤發放的所有獎金。\n" +
                "\t\t\t(2) 當發現會員利用網路攻擊手法進行攻擊、破壞、資料竄改等相關行為，本站有權立即凍結該會員帳號及該戶頭中所有金額，並收集相關證據後提出法律訴訟。\n" +
                "\n" +
                "\t\t\t服務暫停或中斷：\n" +
                "\t\t\t(1) 當本服務進行『例行性』或『臨時性』維護時，本站將以網站公告或其他適當之方式告知會員。\n" +
                "\t\t\t(2) 在下列情形，本站將暫停或中斷本服務之全部或一部，且對使用者任何直接或間接之損害，均不負任何責任：\n" +
                "\t\t\t (2-1) 使用者有任何違反政府法令或本使用條款情形；\n" +
                "\t\t\t (2-2) 天災或其他不可抗力所致之服務停止或中斷；\n" +
                "\t\t\t (2-3) 任何不可歸責於本公司之事由所致之服務停止或中斷。\n" +
                "\t\t\t(3) 如因可歸責於本站之事由，致服務停止時，本站會視實際情況公告補償措施。\n" +
                "\n" +
                "\t\t\t終止服務：\n" +
                "\t\t\t(1) 基於本站的運作，本服務有可能停止提供服務之全部或一部份，使用者不可以因此而要求任何賠償或補償。\n" +
                "\t\t\t(2) 如果您違反了本服務條款，本站保留隨時暫時停止提供服務、或終止提供服務之權利，會員不可因此要求任何賠償或補償。\n" +
                "\n" +
                "\t\t\t商標聲明：\n" +
                "\t\t\t本站所引用的各項公益彩卷商標或名稱，其版權分別屬於各註冊公司所有，本服務引用純屬介紹之用途，並無任何侵害之意。\n" +
                "\t\t\t修改本使用條款的權利\n" +
                "\t\t\t本站保留隨時修改本服務條款內容，修改後的服務條款將公佈在本站上，不另外個別通知使用者。\n" +
                "\t\t\t準據法及管轄權\n" +
                "\t\t\t(1) 本約定條款解釋、補充及適用均以中華民國法令為準據法。\n" +
                "\t\t\t(2) 因本約定條款所發生之訴訟，以台灣台北地方法院為第一審管轄法院。\n" +
                "\t\t\t");

        StatmentAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent intent = new Intent();
                intent.putExtra("Amount",Index.WeakIndex.get().BettingSum.getText().toString());
                intent.putExtra("Payout",Index.WeakIndex.get().BettingWon.getText().toString());

                switch (Index.WeakIndex.get().Play){
                    case 0:
                        intent.putExtra("Combo","單場");
                        break;
                    case 1:
                        intent.putExtra("Combo","過關");
                        break;
                    case 2:
                        intent.putExtra("Combo","過關組合");
                        break;
                }

                intent.putExtra("Combination",Index.WeakIndex.get().combination);
                intent.putExtra("SingleAmount",String.valueOf(Integer.valueOf(Index.WeakIndex.get().BettingPayout.getText().toString())*10));
                intent.setClass(Index.WeakIndex.get(),CheckShopCarActivity.class);
                Index.WeakIndex.get().startActivity(intent);
            }
        });
                StatmentCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

        dialog.show();

    }

}
