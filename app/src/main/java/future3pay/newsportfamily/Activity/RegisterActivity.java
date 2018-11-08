package future3pay.newsportfamily.Activity;

import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.cy.dialog.BaseDialog;

import org.w3c.dom.Text;

import java.lang.ref.WeakReference;

import future3pay.newsportfamily.API.RegisterAPI;
import future3pay.newsportfamily.API.SmsAPI;
import future3pay.newsportfamily.R;
import future3pay.newsportfamily.UIkit.Loading;
import future3pay.newsportfamily.UIkit.ToastShow;
import future3pay.newsportfamily.VerifyData;

public class RegisterActivity extends AppCompatActivity {
    public EditText Account, PassWord, AgainPassWord, UserName, Email, BankAccount, InviteCode, Phone, VerifyCode;
    private TextView AccountNotice, PassWordNotice, AgainPassWordNotice, UserNameNotice, EmailNotice, BankAccountNotice, InviteCodeNotice, PhoneNotice, VerifyCodeNotice,PrivacyPolicy;
    private Spinner BankList;
    private Button SendSms, SendRegister;
    private CountDownTimer SendTimer;
    private CheckBox AgreeCheckBox;

    public static WeakReference<RegisterActivity> WeakRegisterActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        actionbar();

        WeakRegisterActivity = new WeakReference<>(this);



        ObjectCreate();

    }

    private void actionbar(){

        //獲取ActionBar對象
        ActionBar bar = getSupportActionBar();
        bar.setDisplayShowCustomEnabled(true);
        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.actionbar_login, null);
        TextView actionbar_textview = (TextView) v.findViewById(R.id.actionbar_textview);
        Button back = (Button) v.findViewById(R.id.Back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        bar.setCustomView(v, new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT));
    }



    private void ObjectCreate() {


        PassWordNotice = findViewById(R.id.PassWordNotice);
        AgainPassWordNotice = findViewById(R.id.AgainPassWordNotice);
        UserNameNotice = findViewById(R.id.UserNameNotice);
        EmailNotice = findViewById(R.id.EmailNotice);
        BankAccountNotice = findViewById(R.id.BankAccountNotice);
        PhoneNotice = findViewById(R.id.PhoneNotice);


        Account = findViewById(R.id.Account);
        PassWord = findViewById(R.id.PassWord);
        AgainPassWord = findViewById(R.id.AgainPassWord);
        UserName = findViewById(R.id.UserName);
        Email = findViewById(R.id.Email);
        BankAccount = findViewById(R.id.BankAccount);
        InviteCode = findViewById(R.id.InviteCode);
        Phone = findViewById(R.id.Phone);
        VerifyCode = findViewById(R.id.VerifyCode);

        PrivacyPolicy = findViewById(R.id.PrivacyPolicy);
        AgreeCheckBox = findViewById(R.id.AgreeCheckBox);



        BankList = findViewById(R.id.BankList);

        SendSms = findViewById(R.id.SendSms);
        SendRegister = findViewById(R.id.SendRegister);


        PassWord.setOnFocusChangeListener(PassWordTextWatcher);
        AgainPassWord.setOnFocusChangeListener(AgainPassWordWatcher);
        UserName.setOnFocusChangeListener(UserNameWatcher);
        Email.setOnFocusChangeListener(EmailWatcher);
        BankAccount.setOnFocusChangeListener(BankAccountWatcher);
        Phone.setOnFocusChangeListener(PhoneWatcher);

        SendSms.setOnClickListener(Sms);

        SendRegister.setOnClickListener(Register);

        PrivacyPolicy.setOnClickListener(Policy);

    }



    private Button.OnClickListener Register = new Button.OnClickListener() {

        @Override
        public void onClick(View view) {

            Loading.start(RegisterActivity.this);

            if (!Account.getText().toString().equals("")) {
                if (!PassWord.getText().toString().equals("")) {
                    if (!AgainPassWord.getText().toString().equals("")) {
                        if (!UserName.getText().toString().equals("")) {
                            if (!Email.getText().toString().equals("")) {
                                if (!BankAccount.getText().toString().equals("")) {
                                    if (!Phone.getText().toString().equals("")) {
                                        if (!VerifyCode.getText().toString().equals("")) {
                                            if(AgreeCheckBox.isChecked()){

                                                //打註冊api
                                                RegisterAPI.RegisterApi(Account.getText().toString(),PassWord.getText().toString(),AgainPassWord.getText().toString(),UserName.getText().toString(),Email.getText().toString(),Phone.getText().toString(),BankList.getSelectedItem().toString(),BankAccount.getText().toString(),VerifyCode.getText().toString(),InviteCode.getText().toString());

                                            }else{
                                                ToastShow.start(RegisterActivity.this,"尚未同意隱私權政策");
                                            }

                                        } else {

                                            ToastShow.start(RegisterActivity.this,"驗證碼不得空白");
                                            VerifyCode.requestFocus();
                                            Loading.diss();
                                        }
                                    } else {

                                        ToastShow.start(RegisterActivity.this,"手機號碼不得空白");
                                        Phone.requestFocus();
                                        Loading.diss();
                                    }
                                } else {

                                    ToastShow.start(RegisterActivity.this,"銀行帳號不得空白");
                                    BankAccount.requestFocus();
                                    Loading.diss();
                                }
                            } else {

                                ToastShow.start(RegisterActivity.this,"電子郵件不得空白");
                                Email.requestFocus();
                                Loading.diss();
                            }
                        } else {

                            ToastShow.start(RegisterActivity.this,"姓名不得空白");
                            UserName.requestFocus();
                            Loading.diss();
                        }
                    } else {
                        ToastShow.start(RegisterActivity.this,"確認密碼不得空白");
                        AgainPassWord.requestFocus();
                        Loading.diss();
                    }
                } else {
                    ToastShow.start(RegisterActivity.this,"密碼不得空白");
                    PassWord.requestFocus();
                    Loading.diss();
                }
            } else {
                ToastShow.start(RegisterActivity.this,"帳號不得空白");
                Account.requestFocus();
                Loading.diss();
            }

        }

    };

    private Button.OnClickListener Sms = new Button.OnClickListener() {

        @Override
        public void onClick(View view) {

            if (!Account.getText().toString().equals("")) {
                if (!PassWord.getText().toString().equals("")) {
                    if (!AgainPassWord.getText().toString().equals("")) {
                        if (!UserName.getText().toString().equals("")) {
                            if (!Email.getText().toString().equals("")) {
                                if (!BankAccount.getText().toString().equals("")) {
                                    if (!Phone.getText().toString().equals("")) {
                                                if (VerifyData.VerifyPhone(Phone.getText().toString())) {
                                                    SendSms.setEnabled(false);
                                                    Phone.setEnabled(false);

                                                    //打簡訊 api
                                                    SmsAPI.SmsApi(Phone.getText().toString());


                                                    SendTimer = new CountDownTimer(300000, 1000) {
                                                        int i = 300;

                                                        @Override
                                                        public void onFinish() {
                                                            //時間到的時候
                                                            SendSms.setEnabled(true);
                                                            Phone.setEnabled(true);
                                                            SendSms.setText("發送驗證碼");
                                                            SendTimer.cancel();
                                                        }

                                                        @Override
                                                        public void onTick(long millisUntilFinished) {

                                                            SendSms.setText(String.valueOf(i));
                                                            i--;
                                                        }
                                                    }.start();
                                                } else {
                                                    ToastShow.start(RegisterActivity.this,"手機格式不正確");
                                                }

                                    } else {

                                        ToastShow.start(RegisterActivity.this,"手機號碼不得空白");
                                        Phone.requestFocus();

                                    }
                                } else {

                                    ToastShow.start(RegisterActivity.this,"銀行帳號不得空白");
                                    BankAccount.requestFocus();

                                }
                            } else {

                                ToastShow.start(RegisterActivity.this,"電子郵件不得空白");
                                Email.requestFocus();

                            }
                        } else {

                            ToastShow.start(RegisterActivity.this,"姓名不得空白");
                            UserName.requestFocus();

                        }
                    } else {

                        ToastShow.start(RegisterActivity.this,"確認密碼不得空白");
                        AgainPassWord.requestFocus();

                    }
                } else {

                    ToastShow.start(RegisterActivity.this,"密碼不得空白");
                    PassWord.requestFocus();

                }
            } else {
                ToastShow.start(RegisterActivity.this,"帳號不得空白");
                Account.requestFocus();

            }

        }
    };
    private EditText.OnFocusChangeListener PassWordTextWatcher = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean b) {
            if (b) {
                // 得到焦點後處理的內容
            } else {
                // 失去焦點後處理的內容
                if (!VerifyData.VerifyLength(PassWord.getText().toString(), 6)) {
                    PassWordNotice.setText("密碼最少六位數");

                    PassWord.setText("");
                } else {
                    PassWordNotice.setText("OK!");
                }
            }

        }
    };

    private EditText.OnFocusChangeListener AgainPassWordWatcher = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean b) {
            if (b) {
                // 得到焦點後處理的內容
            } else {
                // 失去焦點後處理的內容
                if (!AgainPassWord.getText().toString().equals("")) {
                    if (!AgainPassWord.getText().toString().equals(PassWord.getText().toString())) {
                        AgainPassWordNotice.setText("確認密碼與密碼不一致");

                        AgainPassWord.setText("");
                    } else {
                        AgainPassWordNotice.setText("OK!");
                    }
                }
            }

        }
    };

    private EditText.OnFocusChangeListener UserNameWatcher = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean b) {
            if (b) {
                // 得到焦點後處理的內容
            } else {
                // 失去焦點後處理的內容
                if (!VerifyData.VerifyChiness(UserName.getText().toString())) {
                    UserNameNotice.setText("僅接受中文格式");

                    UserName.setText("");
                } else {
                    UserNameNotice.setText("OK!");
                }
            }

        }
    };


    private EditText.OnFocusChangeListener EmailWatcher = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean b) {
            if (b) {
                // 得到焦點後處理的內容
            } else {
                // 失去焦點後處理的內容
                if (!VerifyData.VerifyEmail(Email.getText().toString())) {
                    EmailNotice.setText("信箱格式錯誤");
                    Email.setText("");
                } else {
                    EmailNotice.setText("OK!");
                }
            }

        }
    };


    private EditText.OnFocusChangeListener BankAccountWatcher = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean b) {
            if (b) {
                // 得到焦點後處理的內容
            } else {
                // 失去焦點後處理的內容
                if (!VerifyData.VerifyLength(BankAccount.getText().toString(), 10)) {
                    BankAccountNotice.setText("銀行帳號格式不正確");
                    BankAccount.setText("");
                } else {
                    BankAccountNotice.setText("OK!");
                }
            }

        }
    };


    private EditText.OnFocusChangeListener PhoneWatcher = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean b) {
            if (b) {
                // 得到焦點後處理的內容
            } else {
                // 失去焦點後處理的內容
                if (!VerifyData.VerifyPhone(Phone.getText().toString())) {
                    PhoneNotice.setText("手機格式不正確");
                    Phone.setText("");
                } else {
                    PhoneNotice.setText("OK!");
                }
            }

        }
    };

    private TextView.OnClickListener Policy =new TextView.OnClickListener(){

        @Override
        public void onClick(View view) {

            BaseDialog  dialog = new BaseDialog(RegisterActivity.this);

            dialog.config(R.layout.privacy_policy, true).show();

         TextView PrivacyPolicyText = dialog.findViewById(R.id.PrivacyPolicyText);

            PrivacyPolicyText.setText("1.運動彩券網路投注站(以下簡稱本網)個人資料運用法定告知事項： 由於個人資料之蒐集，涉及 臺端的隱私權益，本網向 臺端蒐集個人資料時，依據個人資料保護法(以下稱個資法)第八條第一項規定，應明確告知 臺端下列事項：（一）非公務機關名稱（二）蒐集之目的（三）個人資料之類別（四）個人資料利用之期間、地區、對象及方式（五）當事人依個資法第三條規定得行使之權利及方式（六）當事人得自由選擇提供個人資料時，不提供將對其權益之影響。\n" +
                    "2.共通特定目的： 059金融服務業依法令規定及金融監理需要，所為之蒐集處理及利用、061金融監督、管理與檢查、063非公務機關依法定義務所進行個人資料之蒐集處理及利用、069契約、類似契約或其他法律關係管理之事務、090消費者、客戶管理與服務、091消費者保護、098商業與技術資訊、104帳務管理及債權交易業務、129會計與相關服務、135資(通)訊服務、136資(通)訊與資料庫管理、137資通安全與管理、148網路購物及其他電子商務服務、157調查、統計與研究分析、177其他金融管理業務、182其他諮詢與顧問服務。\n" +
                    "3.存匯業務： 036存款與匯款業務、067信用卡、現金卡、轉帳卡或電子票證業務、082借款戶與存款戶存借作業綜合管理、104帳務管理及債權交易業務、106授信業務、111票劵業務、112票據交換業務、160憑證業務管理、181其他經營合於營業登記項目或組織章程所定之業務。\n" +
                    "4.授信業務： 067信用卡、現金卡、轉帳卡或電子票證業務、082借款戶與存款戶存借作業綜合管理、088核貸與授信業務、106授信業務(含事後管理)、111票券業務、126債權整貼現及收買業務、154徵信、181其他經營合於營業登記項目或組織章程所定之業務。\n" +
                    "5.信用卡業務： 067信用卡(含附加功能服務)、現金卡、轉帳卡或電子票證業務、082借款戶與存款戶存借作業綜合管理、088核貸與授信業務、093財產保險、106授信業務、127募款(包含公益勸募)、154徵信、160憑證業務管理、181其他經營合於營業登記項目或組織章程所定之業務。\n" +
                    "6.電子支付機構相關業務： 022外匯業務、036存款與匯款業務、044投資管理、067信用卡、現金卡、轉帳卡或電子票證業務、068信託業務、082借款戶與存款戶存借作業綜合管理、088核貸與授信業務、094財產管理、106授信業務、112票據交換業務、154徵信、166證券、期貨、證券投資信託及顧問相關業務、181其他經營合於營業登記項目或組織章程所定之業務。\n" +
                    "7其他： 其他經營合於營業登記項目或組織章程所定之業務，或經中央主管機關核准辦理之其他有關業務（例如：保管箱業務、黃金存摺、電子金融業務、代理收付業務、共同行銷或合作推廣業務…等。\n" +
                    "8.姓名、身分證統一編號、性別、出生年月日、通訊方式及其他詳如相關業務申請書或契約書之內容，並以本網與客戶往來之相關業務、帳戶或服務及自客戶或第三人處（例如：財團法人金融聯合徵信中心）所實際蒐集之個人資料為準。\n" +
                    "9.個人資料利用之期間：特定目的存續期間，依相關法令所定（例如商業會計法等)或因執行業務所必須之保存期間或依個別契約就資料之保存所定之保存年限。 （以期限最長者為準）。\n" +
                    "個人資料利用之對象、地區及方式\n" +
                    "1.對象： 本網(含受本網委託處理事務之委外機構)、依法令規定利用之機構、其他業務相關之機構（例如：通匯行、財團法人金融聯合徵信中心、財團法人聯合信用卡處理中心、台灣票據交換所、財金資訊股份有限公司、信用保證機構、信用卡國際組織、收單機構暨特約商店等）、依法有權機關或金融監理機關及客戶所同意之對象（例如本網共同行銷或交互運用客戶資料之公司、與本網合作推廣業務之公司等）。\n" +
                    "2..地區： 前揭個人資料利用對象其國內及國外所在地。\n" +
                    "3..方式： 符合個人資料保護相關法令以自動化機器或其他非自動化之利用方式。\n" +
                    "個人資料當事人得行使之權利及方式\n" +
                    "依據個資法第三條規定， 臺端就本網保有 臺端之個人資料得行使下列權利：\n" +
                    "除有個資法第十條所規定之例外情形外，得向本網查詢、請求閱覽或請求製給複製本，惟本網依個資法第十四條規定得酌收必要成本費用。\n" +
                    "4.得向本網請求補充或更正，惟依個資法施行細則第十九條規定， 臺端應適當釋明其原因及事實。\n" +
                    "本網如有違反個資法規定蒐集、處理或利用 臺端的個人資料，依個資法第十一條第四項規定， 臺端得向本網請求停止蒐集。 \n" +
                    "5.依個資法第十一條第二項規定，個人資料正確性有爭議者，得向本網請求停止處理或利用 臺端的個人資料。惟依該項但書規定，本網因執行業務所必須並註明其爭議或經 臺端書面同意者，不在此限。\n" +
                    "6.依個資法第十一條第三項規定，個人資料蒐集之特定目的消失或期限屆滿時，得向本網請求刪除、停止處理或利用 臺端的個人資料。惟依該項但書規定，本網因執行業務所必須或經 臺端書面同意者，不在此限。\n" +
                    "其他\n" +
                    "1.臺端如欲行使上述個資法第三條規定之各項權利，有關如何行使之方式，得向本網客服詢問。\n" +
                    "2.臺端得自由選擇是否提供相關個人資料及類別，惟臺端所拒絕提供之個人資料及類別，如果是辦理業務審核或作業所需之資料，本網可能無法進行必要之業務審核或作業而無法提供 臺端相關服務或無法提供較佳之服務，敬請見諒。");


        }

    };



}
