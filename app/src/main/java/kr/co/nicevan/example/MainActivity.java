package kr.co.nicevan.example;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;

public class MainActivity extends Activity {

    int SEND_REQUEST_CODE = 1;
    int SEND_REQUEST_CHKVALID = 2;
    int SEND_REQUEST_CHKCARDBIN = 3;
    int SEND_REQUEST_CHKCASHIC = 4;
    int SEND_REQUEST_CHKMEMBERSHIP = 5;
    int SEND_REQUEST_NORMAL = 6;
    char fs = 0x1C;
    EditText etSenddata, etRecvdata, etReturnvalue, etMoney, etTax, etBongsa, etHalbu, etAgreenum, etAgreedate, etApprtid, etCashnum, etMyunse, etTxtnum, etTxt, etDevicegb, etFiller, etApprtp, etWcc, etSigndata, etStoamt;
    Button btSend, btReqCard, btReqCupCard, btReqCash, btReqCardCnl, btReqCupCardCnl, btReqCashCnl, btReqCardFb, btReqCashT, btReqCashP, btReqCardCnlFb, btReqCashCnlT, btReqCashCnlP, btReset, btMcnl, btcupMcnl, btchkValid, btReqPoint, btReqPointCnl, btReqMem, btReqMemCnl, btReqNoCardCnl, btReqNoCashCnl, btReqCheck, btChkCardBin, btChkCashIc, btChkMemberhip, btRestart, btReaderreset, btReqbalance, btGetappr, btDcc, btCashicappr, btCashiccnl, btCashicbal, btCashicapprresult, btReqnocashiccnl, btShutdown, btPayproappr, btPayprocnl, btReqcashno, btGetreaderinfo, btReqbarcode, btSetenv, btChkcardinmp, btReqPermission, btReqselectbtn, btReqniceprint, btReqTitcomm, btReqCmd, btReqsigndata, btReqNoCardPartCnl; // OSM20231228 : 신용 무카드 부분취소
    String strRecv01, strRecv02, strRecv03, strRecv04, strRecv05, strRecv06, strRecv07, strRecv08, strRecv09, strRecv10, strRecv11, strRecv12, strRecv13, strRecv14, strRecv15, strRecv16, strRecv17, strRecv18, strRecv19, strRecv20, strRecv21, strRecv22, strRecv23, strRecv24, strRecv25, strRecv26, strRecv27, strRecv28, strRecv29, strRecv30;
    CheckBox cbSignuse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etSenddata = (EditText) findViewById(R.id.etsenddata); //요청전문
        etRecvdata = (EditText) findViewById(R.id.etrecvdata); //응답전문
        etReturnvalue = (EditText) findViewById(R.id.etreturnvalue); //리턴값
        etMoney = (EditText) findViewById(R.id.etmoney); //거래금액
        etTax = (EditText) findViewById(R.id.ettax); //부가세
        etBongsa = (EditText) findViewById(R.id.etbongsa); //봉사료
        etHalbu = (EditText) findViewById(R.id.ethalbu); //할부
        etAgreenum = (EditText) findViewById(R.id.etagreenum); //승인번호
        etAgreedate = (EditText) findViewById(R.id.etagreedate); //승인날짜
        etApprtid = (EditText) findViewById(R.id.etapprtid); //다중사업자승인CATID
        etCashnum = (EditText) findViewById(R.id.etcashnum); //현금영수증 POS입력시 식별번호
        etMyunse = (EditText) findViewById(R.id.etmyunse); //면세금액
        etTxtnum = (EditText) findViewById(R.id.ettxtno); //전문관리번호
        etTxt = (EditText) findViewById(R.id.ettxt); //전문TEXT
        etDevicegb = (EditText) findViewById(R.id.etdevicegb); //기종구분
        etFiller = (EditText) findViewById(R.id.etfiller); //필러
        etApprtp = (EditText) findViewById(R.id.etapprtp); //거래유형
        etWcc = (EditText) findViewById(R.id.etwcc); //WCC
        etSigndata = (EditText) findViewById(R.id.etsigndata); //서명데이터
        cbSignuse = (CheckBox) findViewById(R.id.cbSignUse); //서명데이터 사용여부
        etStoamt = (EditText) findViewById(R.id.etstoamt);   //OSM20231228 : 주민번호

        // 서명 요청 API : OSM20231115
        btReqsigndata = (Button)findViewById(R.id.btreqsigndata);
        btReqsigndata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String senddata = "REQSIGNDATA";

                etSenddata.setText(senddata);

                Intent sendIntent = new Intent();
                sendIntent.setAction("NICEVCAT"); //setAction에 함수명
                sendIntent.putExtra("NVCATSENDDATA", senddata); //NVCATSENDDATA에 요청전문
                sendIntent.setType("text/plain"); //setType은 text/plain 필수
                startActivityForResult(sendIntent, SEND_REQUEST_NORMAL);
            }
        });

        btReqCmd = (Button)findViewById(R.id.btreqcmd);
        btReqCmd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String senddata = "REQCMD";

                Intent sendIntent = new Intent();
                sendIntent.setAction("NICEVCAT"); //setAction에 함수명
                sendIntent.putExtra("NVCATSENDDATA", senddata); //NVCATSENDDATA에 요청전문
                sendIntent.putExtra("COMMAND", 49); //COMMAND
                sendIntent.putExtra("SENDDATA", ""); //SENDDATA
                sendIntent.setType("text/plain"); //setType은 text/plain 필수
                startActivityForResult(sendIntent, SEND_REQUEST_NORMAL);
            }
        });

        btReqTitcomm = (Button)findViewById(R.id.btreqtitcomm);
        btReqTitcomm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String senddata = "TITCOMM";

                etSenddata.setText(senddata);

                Intent sendIntent = new Intent();
                sendIntent.setAction("NICEVCAT"); //setAction에 함수명
                sendIntent.putExtra("NVCATSENDDATA", senddata); //NVCATSENDDATA에 요청전문
                sendIntent.setType("text/plain"); //setType은 text/plain 필수
                sendIntent.putExtra("SENDDATA", etFiller.getText().toString());
                startActivityForResult(sendIntent, SEND_REQUEST_NORMAL);
            }
        });

        //TEST11
        btReqniceprint = (Button)findViewById(R.id.btreqniceprint);
        btReqniceprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                byte bAlimentLeft[] = {(byte)0x1B, (byte)0x61, (byte)0x30}; //왼쪽정렬
                byte bAlimentCenter[] = {(byte)0x1B, (byte)0x61, (byte)0x31}; //가운데정렬
                byte bAlimentRight[] = {(byte)0x1B, (byte)0x61, (byte)0x32}; //오른쪽정렬

                byte bFontA[] = {(byte)0x1B, (byte)0x4D, (byte)0x30}; //폰트A
                byte bFontB[] = {(byte)0x1B, (byte)0x4D, (byte)0x31}; //폰트B

                byte bSize0[] = {(byte)0x1D, (byte)0x21, (byte)0x00}; //사이즈 OFF
                byte bSizeH[] = {(byte)0x1D, (byte)0x21, (byte)0x01}; //세로 크게
                byte bSizeW[] = {(byte)0x1D, (byte)0x21, (byte)0x10}; //가로 크게

                byte bMode0[] = {(byte)0x1B, (byte)0x45, (byte)0x00}; //강조 OFF
                byte bMode1[] = {(byte)0x1B, (byte)0x45, (byte)0x01}; //강조 ON

                byte bUnderline0[] = {(byte)0x1B, (byte)0x2D, (byte)0x30}; //언더라인 OFF
                byte bUnderline1[] = {(byte)0x1B, (byte)0x2D, (byte)0x31}; //언더라인 1
                byte bUnderline2[] = {(byte)0x1B, (byte)0x2D, (byte)0x32}; //언더라인 1

                byte bBgO[] = {(byte)0x1D, (byte)0x42, (byte)0x00}; //음영 OFF
                byte bBgX[] = {(byte)0x1D, (byte)0x42, (byte)0x01}; //음영 ON

                byte bCRLF[] = {(byte)0x0D, (byte)0x0A}; //FEED
                byte bReset[] = {(byte)0x1B, (byte)0x40}; //프린터 초기화
                byte bSatus[] = {(byte)0x1C, (byte)0x6D}; //프린터 상태
                byte bSign[] = {(byte)0x1D, (byte)0x6C}; //나이스 서명 사용 불가

                String sPrintData = "";
                sPrintData += new String(bReset); //프린터 초기화 후 시작
                sPrintData += new String(bAlimentLeft) + new String(bFontA) + new String(bSize0) + new String(bMode0) + new String(bUnderline0) + new String(bBgO) + "상호 : 나이스정보통신(주)" + new String(bCRLF);
                sPrintData += new String(bAlimentLeft) + new String(bFontA) + new String(bSize0) + new String(bMode0) + new String(bUnderline0) + new String(bBgO) + "대표자 : 김용국" + new String(bCRLF);
                sPrintData += new String(bAlimentLeft) + new String(bFontA) + new String(bSize0) + new String(bMode0) + new String(bUnderline0) + new String(bBgO) + "사업자번호 : 220-81-15770" + new String(bCRLF);
                sPrintData += new String(bAlimentLeft) + new String(bFontA) + new String(bSize0) + new String(bMode0) + new String(bUnderline0) + new String(bBgO) + "TEL : 02-2187-2700" + new String(bCRLF);
                sPrintData += new String(bAlimentLeft) + new String(bFontA) + new String(bSize0) + new String(bMode0) + new String(bUnderline0) + new String(bBgO) + "주소 : 서울시 영등포구 은행로 17" + new String(bCRLF);
                sPrintData += new String(bAlimentLeft) + new String(bFontA) + new String(bSize0) + new String(bMode0) + new String(bUnderline0) + new String(bBgO) + "하나체크카드 매출표 IC 승인" + new String(bCRLF);
                sPrintData += new String(bAlimentLeft) + new String(bFontA) + new String(bSize0) + new String(bMode0) + new String(bUnderline0) + new String(bBgO) + "CATID : 23933****1" + new String(bCRLF);
                sPrintData += new String(bAlimentLeft) + new String(bFontA) + new String(bSize0) + new String(bMode0) + new String(bUnderline0) + new String(bBgO) + "일련번호 : 0005" + new String(bCRLF);
                sPrintData += new String(bAlimentLeft) + new String(bFontA) + new String(bSize0) + new String(bMode0) + new String(bUnderline0) + new String(bBgO) + "9440-81**-****-3288" + new String(bCRLF);
                sPrintData += new String(bAlimentLeft) + new String(bFontA) + new String(bSize0) + new String(bMode0) + new String(bUnderline0) + new String(bBgO) + "거래일시 : 23/07/28 19:54:41" + new String(bCRLF);
                sPrintData += new String(bAlimentLeft) + new String(bFontA) + new String(bSize0) + new String(bMode0) + new String(bUnderline0) + new String(bBgO) + "승인번호 : 19540041" + new String(bCRLF);
                sPrintData += new String(bAlimentLeft) + new String(bFontA) + new String(bSize0) + new String(bMode0) + new String(bUnderline0) + new String(bBgO) + "가맹점번호 : 8102776786" + new String(bCRLF);
                sPrintData += new String(bAlimentLeft) + new String(bFontA) + new String(bSize0) + new String(bMode0) + new String(bUnderline0) + new String(bBgO) + "매입사명 : 하나카드" + new String(bCRLF);
                sPrintData += new String(bAlimentLeft) + new String(bFontA) + new String(bSize0) + new String(bMode0) + new String(bUnderline0) + new String(bBgO) + "할부 : 일시불" + new String(bCRLF);
                sPrintData += new String(bAlimentLeft) + new String(bFontA) + new String(bSize0) + new String(bMode0) + new String(bUnderline0) + new String(bBgO) + "과세 : 993 원" + new String(bCRLF);
                sPrintData += new String(bAlimentLeft) + new String(bFontA) + new String(bSize0) + new String(bMode0) + new String(bUnderline0) + new String(bBgO) + "부가세 : 91 원" + new String(bCRLF);
                sPrintData += new String(bAlimentLeft) + new String(bFontA) + new String(bSize0) + new String(bMode0) + new String(bUnderline0) + new String(bBgO) + "총합계 : 1004 원" + new String(bCRLF);
                sPrintData += new String(bAlimentLeft) + new String(bFontA) + new String(bSize0) + new String(bMode0) + new String(bUnderline0) + new String(bBgO) + "고유번호 : 1954-1234-9999" + new String(bCRLF);
                sPrintData += new String(bAlimentLeft) + new String(bFontA) + new String(bSize0) + new String(bMode0) + new String(bUnderline0) + new String(bBgO) + "감사합니다!!" + new String(bCRLF);
                sPrintData += new String(bCRLF) + new String(bCRLF) + new String(bCRLF); //FEED 추가

                String senddata = "NICEPRINT";

                etSenddata.setText(senddata);

                Intent sendIntent = new Intent();
                sendIntent.setAction("NICEVCAT"); //setAction에 함수명
                sendIntent.putExtra("NVCATSENDDATA", senddata); //NVCATSENDDATA에 요청전문
                sendIntent.setType("text/plain"); //setType은 text/plain 필수
                sendIntent.putExtra("TYPE", "1");
                sendIntent.putExtra("SENDDATA", sPrintData);
                startActivityForResult(sendIntent, SEND_REQUEST_NORMAL);
            }
        });

        btReqselectbtn = (Button)findViewById(R.id.btreqselectbtn);
        btReqselectbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String senddata = "REQSELECTBTN";

                etSenddata.setText(senddata);

                Intent sendIntent = new Intent();
                sendIntent.setAction("NICEVCAT"); //setAction에 함수명
                sendIntent.putExtra("NVCATSENDDATA", "REQSELECTBTN"); //NVCATSENDDATA에 요청전문
                sendIntent.setType("text/plain"); //setType은 text/plain 필수
                sendIntent.putExtra("TYPE", "1");
                sendIntent.putExtra("SENDDATA", "동의하시겠습니까?                       ");
                startActivityForResult(sendIntent, SEND_REQUEST_NORMAL);
            }
        });

        btReqPermission = (Button)findViewById(R.id.btreqpermission);
        btReqPermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String senddata = "REQPERMISSION";

                etSenddata.setText(senddata);

                Intent sendIntent = new Intent();
                sendIntent.setAction("NICEVCAT"); //setAction에 함수명
                sendIntent.putExtra("NVCATSENDDATA", senddata); //NVCATSENDDATA에 요청전문
                sendIntent.setType("text/plain"); //setType은 text/plain 필수
                startActivityForResult(sendIntent, SEND_REQUEST_NORMAL);
            }
        });

        btChkcardinmp = (Button)findViewById(R.id.btchkcardinmp);
        btChkcardinmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String senddata = "CHKCARDINMP";

                etSenddata.setText(senddata);

                Intent sendIntent = new Intent();
                sendIntent.setAction("NICEVCAT"); //setAction에 함수명
                sendIntent.putExtra("NVCATSENDDATA", senddata); //NVCATSENDDATA에 요청전문
                sendIntent.setType("text/plain"); //setType은 text/plain 필수
                startActivityForResult(sendIntent, SEND_REQUEST_NORMAL);
            }
        });

        btSetenv = (Button)findViewById(R.id.btsetenv);
        btSetenv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String senddata = "SETENVSTRING";
                Intent sendIntent = new Intent();
                sendIntent.setAction("NICEVCAT"); //setAction에 함수명
                sendIntent.putExtra("NVCATSENDDATA", senddata); //NVCATSENDDATA에 요청전문
                sendIntent.setType("text/plain"); //setType은 text/plain 필수
                sendIntent.putExtra("KEY", "Serverport");
                sendIntent.putExtra("VALUE", "9701");
                startActivityForResult(sendIntent, SEND_REQUEST_NORMAL);

                senddata = "SETENVINT";
                sendIntent.setAction("NICEVCAT"); //setAction에 함수명
                sendIntent.putExtra("NVCATSENDDATA", senddata); //NVCATSENDDATA에 요청전문
                sendIntent.setType("text/plain"); //setType은 text/plain 필수
                sendIntent.putExtra("KEY", "Serverip");
                sendIntent.putExtra("VALUE", 0);
                startActivityForResult(sendIntent, SEND_REQUEST_NORMAL);

                senddata = "SETENVBOOL";
                sendIntent.setAction("NICEVCAT"); //setAction에 함수명
                sendIntent.putExtra("NVCATSENDDATA", senddata); //NVCATSENDDATA에 요청전문
                sendIntent.setType("text/plain"); //setType은 text/plain 필수
                sendIntent.putExtra("KEY", "DualScreenuse");
                sendIntent.putExtra("VALUE", true);
                startActivityForResult(sendIntent, SEND_REQUEST_NORMAL);
            }
        });

        btGetreaderinfo = (Button) findViewById(R.id.btgetreaderinfo); //리더기 정보 가져오기
        btGetreaderinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String senddata = "GETREADERINFO";

                etSenddata.setText(senddata);

                Intent sendIntent = new Intent();
                sendIntent.setAction("NICEVCAT"); //setAction에 함수명
                sendIntent.putExtra("NVCATSENDDATA", senddata); //NVCATSENDDATA에 요청전문
                sendIntent.setType("text/plain"); //setType은 text/plain 필수
                startActivityForResult(sendIntent, SEND_REQUEST_NORMAL);
            }
        });

        btReqbarcode = (Button) findViewById(R.id.btreqbarcode); //바코드 요청
        btReqbarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String senddata = "REQBARCODE";

                etSenddata.setText(senddata);

                Intent sendIntent = new Intent();
                sendIntent.setAction("NICEVCAT"); //setAction에 함수명
                sendIntent.putExtra("NVCATSENDDATA", senddata); //NVCATSENDDATA에 요청전문
                sendIntent.setType("text/plain"); //setType은 text/plain 필수
                startActivityForResult(sendIntent, SEND_REQUEST_NORMAL);
            }
        });

        btReqcashno = (Button) findViewById(R.id.btreqcashno); //고객식별번호 요청
        btReqcashno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String senddata = "REQCASHNO";

                etSenddata.setText(senddata);

                Intent sendIntent = new Intent();
                sendIntent.setAction("NICEVCAT"); //setAction에 함수명
                sendIntent.putExtra("NVCATSENDDATA", senddata); //NVCATSENDDATA에 요청전문
                sendIntent.setType("text/plain"); //setType은 text/plain 필수
                startActivityForResult(sendIntent, SEND_REQUEST_NORMAL);
            }
        });

        btMcnl = (Button) findViewById(R.id.btmangcnl);
        btMcnl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String senddata = "";
                if(cbSignuse.isChecked())
                    senddata = "0421" + fs + "10" + fs + "I" + fs + etMoney.getText().toString() + fs + etTax.getText().toString() + fs + etBongsa.getText().toString() + fs + etHalbu.getText().toString() + fs + fs + fs + etApprtid.getText().toString() + fs + fs + fs + fs + etMyunse.getText().toString() + fs + fs + fs + etTxtnum.getText().toString() + fs + etFiller.getText().toString() + fs + fs + etTxt.getText().toString() + fs + etDevicegb.getText().toString() + fs + fs + fs + fs + etSigndata.getText().toString() + fs + fs + fs + fs + fs + fs;
                else
                    senddata = "0421" + fs + "10" + fs + "I" + fs + etMoney.getText().toString() + fs + etTax.getText().toString() + fs + etBongsa.getText().toString() + fs + etHalbu.getText().toString() + fs + fs + fs + etApprtid.getText().toString() + fs + fs + fs + fs + etMyunse.getText().toString() + fs + fs + fs + etTxtnum.getText().toString() + fs + etFiller.getText().toString() + fs + fs + etTxt.getText().toString() + fs + etDevicegb.getText().toString() + fs + fs + fs + fs + fs + fs + fs + fs + fs + fs;
                send(senddata);
            }
        });

        btcupMcnl = (Button) findViewById(R.id.btmangcupcnl);
        btcupMcnl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String senddata = "";
                if(cbSignuse.isChecked())
                    senddata = "0421" + fs + "UP" + fs + "I" + fs + etMoney.getText().toString() + fs + etTax.getText().toString() + fs + etBongsa.getText().toString() + fs + etHalbu.getText().toString() + fs + fs + fs + etApprtid.getText().toString() + fs + fs + fs + fs + etMyunse.getText().toString() + fs + fs + fs + etTxtnum.getText().toString() + fs + etFiller.getText().toString() + fs + fs + etTxt.getText().toString() + fs + etDevicegb.getText().toString() + fs + fs + fs + fs + etSigndata.getText().toString() + fs + fs + fs + fs + fs + fs;
                else
                    senddata = "0421" + fs + "UP" + fs + "I" + fs + etMoney.getText().toString() + fs + etTax.getText().toString() + fs + etBongsa.getText().toString() + fs + etHalbu.getText().toString() + fs + fs + fs + etApprtid.getText().toString() + fs + fs + fs + fs + etMyunse.getText().toString() + fs + fs + fs + etTxtnum.getText().toString() + fs + etFiller.getText().toString() + fs + fs + etTxt.getText().toString() + fs + etDevicegb.getText().toString() + fs + fs + fs + fs + fs + fs + fs + fs + fs + fs;
                send(senddata);
            }
        });

        btReset = (Button) findViewById(R.id.btreset);
        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etSenddata.setText("");
                etRecvdata.setText("");
                etReturnvalue.setText("");
                etMoney.setText("");
                etTax.setText("");
                etBongsa.setText("");
                etHalbu.setText("");
                etAgreenum.setText("");
                etAgreedate.setText("");
                etApprtid.setText("");
                etCashnum.setText("");
                etMyunse.setText("");
                etTxtnum.setText("");
                etTxt.setText("");
                etDevicegb.setText("");
                etFiller.setText("");
                etApprtp.setText("");
            }
        });

        btSend = (Button) findViewById(R.id.btsend);
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etSenddata.getText().length() < 1) {
                    Toast.makeText(getApplicationContext(), "요청전문을 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    send(etSenddata.getText().toString());
                }
            }
        });

        btReqCard = (Button) findViewById(R.id.btreqcard); //신용승인(IC)
        btReqCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String senddata = "";
                if(cbSignuse.isChecked())
                    senddata = "0200" + fs + "10" + fs + "I" + fs + etMoney.getText().toString() + fs + etTax.getText().toString() + fs + etBongsa.getText().toString() + fs + etHalbu.getText().toString() + fs + fs + fs + etApprtid.getText().toString() + fs + fs + fs + fs + etMyunse.getText().toString() + fs + fs + fs + etTxtnum.getText().toString() + fs + etFiller.getText().toString() + fs + fs + etTxt.getText().toString() + fs + etDevicegb.getText().toString() + fs + fs + fs + fs + etSigndata.getText().toString() + fs + fs + fs + fs + fs + fs;
                else
                    senddata = "0200" + fs + "10" + fs + "I" + fs + etMoney.getText().toString() + fs + etTax.getText().toString() + fs + etBongsa.getText().toString() + fs + etHalbu.getText().toString() + fs + fs + fs + etApprtid.getText().toString() + fs + fs + fs + fs + etMyunse.getText().toString() + fs + fs + fs + etTxtnum.getText().toString() + fs + etFiller.getText().toString() + fs + fs + etTxt.getText().toString() + fs + etDevicegb.getText().toString() + fs + fs + fs + fs + fs + fs + fs + fs + fs + fs;
                send(senddata);
            }
        });

        btReqCupCard = (Button) findViewById(R.id.btreqcupcard); //은련승인(IC)
        btReqCupCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String senddata = "";
                if(cbSignuse.isChecked())
                    senddata = "0200" + fs + "UP" + fs + "I" + fs + etMoney.getText().toString() + fs + etTax.getText().toString() + fs + etBongsa.getText().toString() + fs + etHalbu.getText().toString() + fs + fs + fs + etApprtid.getText().toString() + fs + fs + fs + fs + etMyunse.getText().toString() + fs + fs + fs + etTxtnum.getText().toString() + fs + etFiller.getText().toString() + fs + fs + etTxt.getText().toString() + fs + etDevicegb.getText().toString() + fs + fs + fs + fs + etSigndata.getText().toString() + fs + fs + fs + fs + fs + fs;
                else
                    senddata = "0200" + fs + "UP" + fs + "I" + fs + etMoney.getText().toString() + fs + etTax.getText().toString() + fs + etBongsa.getText().toString() + fs + etHalbu.getText().toString() + fs + fs + fs + etApprtid.getText().toString() + fs + fs + fs + fs + etMyunse.getText().toString() + fs + fs + fs + etTxtnum.getText().toString() + fs + etFiller.getText().toString() + fs + fs + etTxt.getText().toString() + fs + etDevicegb.getText().toString() + fs + fs + fs + fs + fs + fs + fs + fs + fs + fs;
                send(senddata);
            }
        });

        btReqCash = (Button) findViewById(R.id.btreqcash); //현금승인(IC)
        btReqCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String senddata = "0200" + fs + "21" + fs + "I" + fs + etMoney.getText().toString() + fs + etTax.getText().toString() + fs + etBongsa.getText().toString() + fs + etHalbu.getText().toString() + fs + fs + fs + etApprtid.getText().toString() + fs + fs + fs + fs + etMyunse.getText().toString() + fs + fs + fs + etTxtnum.getText().toString() + fs + etFiller.getText().toString() + fs + fs + etTxt.getText().toString() + fs + etDevicegb.getText().toString() + fs + fs + fs + fs + fs + fs + fs + fs + fs + fs;
                send(senddata);
            }
        });

        btReqCardCnl = (Button) findViewById(R.id.btreqcardcnl); //신용취소(IC)
        btReqCardCnl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String senddata = "";
                if(cbSignuse.isChecked())
                    senddata = "0420" + fs + "10" + fs + "I" + fs + etMoney.getText().toString() + fs + etTax.getText().toString() + fs + etBongsa.getText().toString() + fs + etHalbu.getText().toString() + fs + etAgreenum.getText().toString() + fs + etAgreedate.getText().toString() + fs + etApprtid.getText().toString() + fs + fs + fs + fs + etMyunse.getText().toString() + fs + fs + fs + etTxtnum.getText().toString() + fs + etFiller.getText().toString() + fs + fs + etTxt.getText().toString() + fs + etDevicegb.getText().toString() + fs + fs + fs + fs + etSigndata.getText().toString() + fs + fs + fs + fs + fs + fs;
                else
                    senddata = "0420" + fs + "10" + fs + "I" + fs + etMoney.getText().toString() + fs + etTax.getText().toString() + fs + etBongsa.getText().toString() + fs + etHalbu.getText().toString() + fs + etAgreenum.getText().toString() + fs + etAgreedate.getText().toString() + fs + etApprtid.getText().toString() + fs + fs + fs + fs + etMyunse.getText().toString() + fs + fs + fs + etTxtnum.getText().toString() + fs + etFiller.getText().toString() + fs + fs + etTxt.getText().toString() + fs + etDevicegb.getText().toString() + fs + fs + fs + fs + fs + fs + fs + fs + fs + fs;
                send(senddata);
            }
        });

        btReqCupCardCnl = (Button) findViewById(R.id.btreqcupcardcnl); //은련취소(IC)
        btReqCupCardCnl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String senddata = "";
                if(cbSignuse.isChecked())
                    senddata = "0420" + fs + "UP" + fs + "I" + fs + etMoney.getText().toString() + fs + etTax.getText().toString() + fs + etBongsa.getText().toString() + fs + etHalbu.getText().toString() + fs + etAgreenum.getText().toString() + fs + etAgreedate.getText().toString() + fs + etApprtid.getText().toString() + fs + fs + fs + fs + etMyunse.getText().toString() + fs + fs + fs + etTxtnum.getText().toString() + fs + etFiller.getText().toString() + fs + fs + etTxt.getText().toString() + fs + etDevicegb.getText().toString() + fs + fs + fs + fs + etSigndata.getText().toString() + fs + fs + fs + fs + fs + fs;
                else
                    senddata = "0420" + fs + "UP" + fs + "I" + fs + etMoney.getText().toString() + fs + etTax.getText().toString() + fs + etBongsa.getText().toString() + fs + etHalbu.getText().toString() + fs + etAgreenum.getText().toString() + fs + etAgreedate.getText().toString() + fs + etApprtid.getText().toString() + fs + fs + fs + fs + etMyunse.getText().toString() + fs + fs + fs + etTxtnum.getText().toString() + fs + etFiller.getText().toString() + fs + fs + etTxt.getText().toString() + fs + etDevicegb.getText().toString() + fs + fs + fs + fs + fs + fs + fs + fs + fs + fs;
                send(senddata);
            }
        });

        btReqCashCnl = (Button) findViewById(R.id.btreqcashcnl); //현금취소(IC)
        btReqCashCnl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String senddata = "0420" + fs + "21" + fs + "I" + fs + etMoney.getText().toString() + fs + etTax.getText().toString() + fs + etBongsa.getText().toString() + fs + etHalbu.getText().toString() + fs + etAgreenum.getText().toString() + fs + etAgreedate.getText().toString() + fs + etApprtid.getText().toString() + fs + fs + fs + fs + etMyunse.getText().toString() + fs + fs + fs + etTxtnum.getText().toString() + fs + etFiller.getText().toString() + fs + fs + etTxt.getText().toString() + fs + etDevicegb.getText().toString() + fs + fs + fs + fs + fs + fs + fs + fs + fs + fs;
                send(senddata);
            }
        });

        btReqCardFb = (Button) findViewById(R.id.btreqcardfb); //신용승인(FB)
        btReqCardFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String senddata = "";
                if(cbSignuse.isChecked())
                    senddata = "0200" + fs + "10" + fs + "F" + fs + etMoney.getText().toString() + fs + etTax.getText().toString() + fs + etBongsa.getText().toString() + fs + etHalbu.getText().toString() + fs + fs + fs + etApprtid.getText().toString() + fs + fs + fs + fs + etMyunse.getText().toString() + fs + fs + fs + etTxtnum.getText().toString() + fs + etFiller.getText().toString() + fs + fs + etTxt.getText().toString() + fs + etDevicegb.getText().toString() + fs + fs + fs + fs + etSigndata.getText().toString() + fs + fs + fs + fs + fs + fs;
                else
                    senddata = "0200" + fs + "10" + fs + "F" + fs + etMoney.getText().toString() + fs + etTax.getText().toString() + fs + etBongsa.getText().toString() + fs + etHalbu.getText().toString() + fs + fs + fs + etApprtid.getText().toString() + fs + fs + fs + fs + etMyunse.getText().toString() + fs + fs + fs + etTxtnum.getText().toString() + fs + etFiller.getText().toString() + fs + fs + etTxt.getText().toString() + fs + etDevicegb.getText().toString() + fs + fs + fs + fs + fs + fs + fs + fs + fs + fs;
                send(senddata);
            }
        });

        btReqCashT = (Button) findViewById(R.id.btreqcashT); //현금승인(터치)
        btReqCashT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String senddata = "0200" + fs + "21" + fs + "T" + fs + etMoney.getText().toString() + fs + etTax.getText().toString() + fs + etBongsa.getText().toString() + fs + etHalbu.getText().toString() + fs + fs + fs + etApprtid.getText().toString() + fs + fs + fs + fs + etMyunse.getText().toString() + fs + fs + fs + etTxtnum.getText().toString() + fs + etFiller.getText().toString() + fs + fs + etTxt.getText().toString() + fs + etDevicegb.getText().toString() + fs + fs + fs + fs + fs + fs + fs + fs + fs + fs;
                send(senddata);
            }
        });

        btReqCashP = (Button) findViewById(R.id.btreqcashP); //현금승인(POS입력)
        btReqCashP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String senddata = "0200" + fs + "21" + fs + "P" + fs + etMoney.getText().toString() + fs + etTax.getText().toString() + fs + etBongsa.getText().toString() + fs + etHalbu.getText().toString() + fs + fs + fs + etApprtid.getText().toString() + fs + fs + fs + etCashnum.getText().toString() + fs + etMyunse.getText().toString() + fs + fs + fs + etTxtnum.getText().toString() + fs + etFiller.getText().toString() + fs + fs + etTxt.getText().toString() + fs + etDevicegb.getText().toString() + fs + fs + fs + fs + fs + fs + fs + fs + fs + fs;
                send(senddata);
            }
        });

        btReqCardCnlFb = (Button) findViewById(R.id.btreqcardcnlfb); //신용취소(FB)
        btReqCardCnlFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String senddata = "";
                if(cbSignuse.isChecked())
                    senddata = "0420" + fs + "10" + fs + "F" + fs + etMoney.getText().toString() + fs + etTax.getText().toString() + fs + etBongsa.getText().toString() + fs + etHalbu.getText().toString() + fs + etAgreenum.getText().toString() + fs + etAgreedate.getText().toString() + fs + etApprtid.getText().toString() + fs + fs + fs + fs + etMyunse.getText().toString() + fs + fs + fs + etTxtnum.getText().toString() + fs + etFiller.getText().toString() + fs + fs + etTxt.getText().toString() + fs + etDevicegb.getText().toString() + fs + fs + fs + fs + etSigndata.getText().toString() + fs + fs + fs + fs + fs + fs;
                else
                    senddata = "0420" + fs + "10" + fs + "F" + fs + etMoney.getText().toString() + fs + etTax.getText().toString() + fs + etBongsa.getText().toString() + fs + etHalbu.getText().toString() + fs + etAgreenum.getText().toString() + fs + etAgreedate.getText().toString() + fs + etApprtid.getText().toString() + fs + fs + fs + fs + etMyunse.getText().toString() + fs + fs + fs + etTxtnum.getText().toString() + fs + etFiller.getText().toString() + fs + fs + etTxt.getText().toString() + fs + etDevicegb.getText().toString() + fs + fs + fs + fs + fs + fs + fs + fs + fs + fs;
                send(senddata);
            }
        });

        btReqCashCnlT = (Button) findViewById(R.id.btreqcashcnlT); //현금취소(터치)
        btReqCashCnlT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String senddata = "0420" + fs + "21" + fs + "T" + fs + etMoney.getText().toString() + fs + etTax.getText().toString() + fs + etBongsa.getText().toString() + fs + etHalbu.getText().toString() + fs + etAgreenum.getText().toString() + fs + etAgreedate.getText().toString() + fs + etApprtid.getText().toString() + fs + fs + fs + fs + etMyunse.getText().toString() + fs + fs + fs + etTxtnum.getText().toString() + fs + etFiller.getText().toString() + fs + fs + etTxt.getText().toString() + fs + etDevicegb.getText().toString() + fs + fs + fs + fs + fs + fs + fs + fs + fs + fs;
                send(senddata);
            }
        });

        btReqCashCnlP = (Button) findViewById(R.id.btreqcashcnlP); //현금취소(POS입력)
        btReqCashCnlP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String senddata = "0420" + fs + "21" + fs + "P" + fs + etMoney.getText().toString() + fs + etTax.getText().toString() + fs + etBongsa.getText().toString() + fs + etHalbu.getText().toString() + fs + etAgreenum.getText().toString() + fs + etAgreedate.getText().toString() + fs + etApprtid.getText().toString() + fs + fs + fs + etCashnum.getText().toString() + fs + etMyunse.getText().toString() + fs + fs + fs + etTxtnum.getText().toString() + fs + etFiller.getText().toString() + fs + fs + etTxt.getText().toString() + fs + etDevicegb.getText().toString() + fs + fs + fs + fs + fs + fs + fs + fs + fs + fs;
                send(senddata);
            }
        });

        btchkValid = (Button) findViewById(R.id.btchkvalid);
        btchkValid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String senddata = "CHKVALID";

                etSenddata.setText(senddata);

                Intent sendIntent = new Intent();
                sendIntent.setAction("NICEVCAT"); //setAction에 함수명
                sendIntent.putExtra("NVCATSENDDATA", senddata); //NVCATSENDDATA에 요청전문
                sendIntent.setType("text/plain"); //setType은 text/plain 필수
                startActivityForResult(sendIntent, SEND_REQUEST_CHKVALID);
            }
        });

        btReqPoint = (Button) findViewById(R.id.btreqpoint); //POINT승인
        btReqPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String senddata = "0300" + fs + etApprtp.getText().toString() + fs + etWcc.getText().toString() + fs + etMoney.getText().toString() + fs + etTax.getText().toString() + fs + etBongsa.getText().toString() + fs + etHalbu.getText().toString() + fs + fs + fs + etApprtid.getText().toString() + fs + fs + fs + etCashnum.getText().toString() + fs + etMyunse.getText().toString() + fs + fs + fs + etTxtnum.getText().toString() + fs + etFiller.getText().toString() + fs + fs + etTxt.getText().toString() + fs + etDevicegb.getText().toString() + fs + fs + fs + fs + fs + fs + fs + fs + fs + fs;
                send(senddata);
            }
        });

        btReqPointCnl = (Button) findViewById(R.id.btreqpointcnl); //POINT취소
        btReqPointCnl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String senddata = "0520" + fs + etApprtp.getText().toString() + fs + etWcc.getText().toString() + fs + etMoney.getText().toString() + fs + etTax.getText().toString() + fs + etBongsa.getText().toString() + fs + etHalbu.getText().toString() + fs + etAgreenum.getText().toString() + fs + etAgreedate.getText().toString() + fs + etApprtid.getText().toString() + fs + fs + fs + etCashnum.getText().toString() + fs + etMyunse.getText().toString() + fs + fs + fs + etTxtnum.getText().toString() + fs + etFiller.getText().toString() + fs + fs + etTxt.getText().toString() + fs + etDevicegb.getText().toString() + fs + fs + fs + fs + fs + fs + fs + fs + fs + fs;
                send(senddata);
            }
        });

        btReqMem = (Button) findViewById(R.id.btreqmem); //MEM승인
        btReqMem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String senddata = "0320" + fs + etApprtp.getText().toString() + fs + etWcc.getText().toString() + fs + etMoney.getText().toString() + fs + etTax.getText().toString() + fs + etBongsa.getText().toString() + fs + etHalbu.getText().toString() + fs + fs + fs + etApprtid.getText().toString() + fs + fs + fs + etCashnum.getText().toString() + fs + fs + fs + fs + etTxtnum.getText().toString() + fs + etFiller.getText().toString() + fs + fs + etTxt.getText().toString() + fs + etDevicegb.getText().toString() + fs + etMyunse.getText().toString() + fs + fs + fs + fs + fs + fs + fs + fs + fs;
                String senddata = "0320" + fs + "67" + fs + "P" + fs + etMoney.getText().toString() + fs + "00" + fs + "00" + fs + "9999" + fs + fs + fs + "" + fs + fs + fs + "0100540855" + fs + fs + fs + fs + etTxtnum.getText().toString() + fs + etFiller.getText().toString() + fs + fs + etTxt.getText().toString() + fs + etDevicegb.getText().toString() + fs + "0000000010                      " + fs + fs + fs + fs + fs + fs + fs + fs + fs;
                send(senddata);
            }
        });

        btReqMemCnl = (Button) findViewById(R.id.btreqmemcnl); //MEM취소
        btReqMemCnl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String senddata = "0540" + fs + etApprtp.getText().toString() + fs + etWcc.getText().toString() + fs + etMoney.getText().toString() + fs + etTax.getText().toString() + fs + etBongsa.getText().toString() + fs + etHalbu.getText().toString() + fs + etAgreenum.getText().toString() + fs + etAgreedate.getText().toString() + fs + etApprtid.getText().toString() + fs + fs + fs + etCashnum.getText().toString() + fs + fs + fs + fs + etTxtnum.getText().toString() + fs + etFiller.getText().toString() + fs + fs + etTxt.getText().toString() + fs + etDevicegb.getText().toString() + fs + etMyunse.getText().toString() + fs + fs + fs + fs + fs + fs + fs + fs + fs;
                send(senddata);
            }
        });

        btReqNoCardCnl = (Button) findViewById(R.id.btreqnocardcnl); //신용무카드취소
        btReqNoCardCnl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String senddata = "0420" + fs + "10" + fs + "N" + fs + etMoney.getText().toString() + fs + etTax.getText().toString() + fs + etBongsa.getText().toString() + fs + etHalbu.getText().toString() + fs + etAgreenum.getText().toString() + fs + etAgreedate.getText().toString() + fs + etApprtid.getText().toString() + fs + fs + fs + etCashnum.getText().toString() + fs + etMyunse.getText().toString() + fs + fs + fs + etTxtnum.getText().toString() + fs + etFiller.getText().toString() + fs + fs + etTxt.getText().toString() + fs + etDevicegb.getText().toString() + fs + fs + fs + fs + fs + fs + fs + fs + fs + fs;
                send(senddata);
            }
        });

        btReqNoCardPartCnl = (Button) findViewById(R.id.btreqnocardpartcnl); //OSM20231228 : 신용 무카드 부분취소
        btReqNoCardPartCnl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //거래일련번호/원거래금액("P" + 금액(12))/전문TEXT("PCL") 셋팅 필요
                String senddata = "0520" + fs + "10" + fs + "N" + fs + etMoney.getText().toString() + fs + etTax.getText().toString() + fs + etBongsa.getText().toString() + fs + etHalbu.getText().toString() + fs + etAgreenum.getText().toString() + fs + etAgreedate.getText().toString() + fs + etApprtid.getText().toString() + fs + fs + fs + etCashnum.getText().toString() + fs + etMyunse.getText().toString() + fs + fs + fs + etTxtnum.getText().toString() + fs + etFiller.getText().toString() + fs + etStoamt.getText().toString() + fs + "PCL" + fs + etDevicegb.getText().toString() + fs + fs + fs + fs + fs + fs + fs + fs + fs + fs;
                send(senddata);
            }
        });

        btReqNoCardCnl = (Button) findViewById(R.id.btreqnocashcnl); //현금무카드취소
        btReqNoCardCnl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String senddata = "0420" + fs + "21" + fs + "N" + fs + etMoney.getText().toString() + fs + etTax.getText().toString() + fs + etBongsa.getText().toString() + fs + etHalbu.getText().toString() + fs + etAgreenum.getText().toString() + fs + etAgreedate.getText().toString() + fs + etApprtid.getText().toString() + fs + fs + fs + fs + etMyunse.getText().toString() + fs + fs + fs + etTxtnum.getText().toString() + fs + etFiller.getText().toString() + fs + fs + etTxt.getText().toString() + fs + etDevicegb.getText().toString() + fs + fs + fs + fs + fs + fs + fs + fs + fs + fs;
                send(senddata);
            }
        });

        btReqCheck = (Button) findViewById(R.id.btreqcheck);
        btReqCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String senddata = "0200" + fs + "20" + fs + "K" + fs + "00" + fs + "13" + fs + "12345678010001" + fs + "200214" + fs + "             " + fs + "000100000" + fs + "123456" + fs + fs + fs + fs + fs + fs + fs + fs + fs + fs + fs + fs + fs + fs + fs + fs + fs + fs + fs + fs + fs;
                send(senddata);
            }
        });

        btChkCardBin = (Button) findViewById(R.id.btchkcardbin);
        btChkCardBin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String senddata = "CHKCARDBIN";

                etSenddata.setText(senddata);

                Intent sendIntent = new Intent();
                sendIntent.setAction("NICEVCAT"); //setAction에 함수명
                sendIntent.putExtra("NVCATSENDDATA", senddata); //NVCATSENDDATA에 요청전문
                sendIntent.setType("text/plain"); //setType은 text/plain 필수
                startActivityForResult(sendIntent, SEND_REQUEST_CHKCARDBIN);
            }
        });

        btChkCashIc = (Button) findViewById(R.id.btchkcashic);
        btChkCashIc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String senddata = "CHKCASHIC";

                etSenddata.setText(senddata);

                Intent sendIntent = new Intent();
                sendIntent.setAction("NICEVCAT"); //setAction에 함수명
                sendIntent.putExtra("NVCATSENDDATA", senddata); //NVCATSENDDATA에 요청전문
                sendIntent.setType("text/plain"); //setType은 text/plain 필수
                startActivityForResult(sendIntent, SEND_REQUEST_CHKCASHIC);
            }
        });

        btChkMemberhip = (Button)findViewById(R.id.btchkmembership);
        btChkMemberhip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String senddata = "CHKMEMBERSHIP";

                etSenddata.setText(senddata);

                Intent sendIntent = new Intent();
                sendIntent.setAction("NICEVCAT"); //setAction에 함수명
                sendIntent.putExtra("NVCATSENDDATA", senddata); //NVCATSENDDATA에 요청전문
                sendIntent.setType("text/plain"); //setType은 text/plain 필수
                startActivityForResult(sendIntent, SEND_REQUEST_CHKMEMBERSHIP);
            }
        });

        btRestart = (Button)findViewById(R.id.btrestart);
        btRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String senddata = "RESTART";

                etSenddata.setText(senddata);

                Intent sendIntent = new Intent();
                sendIntent.setAction("NICEVCAT"); //setAction에 함수명
                sendIntent.putExtra("NVCATSENDDATA", senddata); //NVCATSENDDATA에 요청전문
                sendIntent.setType("text/plain"); //setType은 text/plain 필수
                startActivityForResult(sendIntent, SEND_REQUEST_NORMAL);
            }
        });


        btReqbalance = (Button)findViewById(R.id.btreqbalance);
        btReqbalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // RF 잔액조회 (OSM20230713)
                String senddata = "REQBALANCE";

                etSenddata.setText(senddata);

                Intent sendIntent = new Intent();
                sendIntent.setAction("NICEVCAT"); //setAction에 함수명
                sendIntent.putExtra("NVCATSENDDATA", senddata); //NVCATSENDDATA에 요청전문
                sendIntent.setType("text/plain"); //setType은 text/plain 필수
                startActivityForResult(sendIntent, SEND_REQUEST_NORMAL);
            }
        });


        btReaderreset = (Button)findViewById(R.id.btreaderreset);
        btReaderreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String senddata = "READERRESET";

                etSenddata.setText(senddata);

                Intent sendIntent = new Intent();
                sendIntent.setAction("NICEVCAT"); //setAction에 함수명
                sendIntent.putExtra("NVCATSENDDATA", senddata); //NVCATSENDDATA에 요청전문
                sendIntent.setType("text/plain"); //setType은 text/plain 필수
                startActivityForResult(sendIntent, SEND_REQUEST_NORMAL);
            }
        });

        /* NVCAT 강제 종료 (OSM20230712) */
        btShutdown = (Button)findViewById(R.id.btshutdown);
        btShutdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String senddata = "SHUTDOWN";

                etSenddata.setText(senddata);

                Intent sendIntent = new Intent();
                sendIntent.setAction("NICEVCAT"); //setAction에 함수명
                sendIntent.putExtra("NVCATSENDDATA", senddata); //NVCATSENDDATA에 요청전문
                sendIntent.setType("text/plain"); //setType은 text/plain 필수 (text 고정)
                startActivityForResult(sendIntent, SEND_REQUEST_NORMAL);
            }
        });

        btGetappr = (Button)findViewById(R.id.btgetappr);
        btGetappr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String senddata = "GETAPPR";

                etSenddata.setText(senddata);

                Intent sendIntent = new Intent();
                sendIntent.setAction("NICEVCAT"); //setAction에 함수명
                sendIntent.putExtra("NVCATSENDDATA", senddata); //NVCATSENDDATA에 요청전문
                sendIntent.setType("text/plain"); //setType은 text/plain 필수
                startActivityForResult(sendIntent, SEND_REQUEST_NORMAL);
            }
        });

        btDcc = (Button)findViewById(R.id.btdcc);
        btDcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //DCC승리
                String senddata = "";
                if(cbSignuse.isChecked())
                    senddata = "0200" + fs + "DC" + fs + "I" + fs + etMoney.getText().toString() + fs + etTax.getText().toString() + fs + etBongsa.getText().toString() + fs + etHalbu.getText().toString() + fs + fs + fs + etApprtid.getText().toString() + fs + "410" + fs + etMoney.getText().toString() + fs + "0" + fs + etMyunse.getText().toString() + fs + fs + fs + etTxtnum.getText().toString() + fs + etFiller.getText().toString() + fs + fs + etTxt.getText().toString() + fs + etDevicegb.getText().toString() + fs + fs + fs + fs + etSigndata.getText().toString() + fs + fs + fs + fs + fs + fs;
                else
                    senddata = "0200" + fs + "DC" + fs + "I" + fs + etMoney.getText().toString() + fs + etTax.getText().toString() + fs + etBongsa.getText().toString() + fs + etHalbu.getText().toString() + fs + fs + fs + etApprtid.getText().toString() + fs + "410" + fs + etMoney.getText().toString() + fs + "0" + fs + etMyunse.getText().toString() + fs + fs + fs + etTxtnum.getText().toString() + fs + etFiller.getText().toString() + fs + fs + etTxt.getText().toString() + fs + etDevicegb.getText().toString() + fs + fs + fs + fs + fs + fs + fs + fs + fs + fs;
                send(senddata);
            }
        });

        btCashicappr = (Button)findViewById(R.id.btcashicappr);
        btCashicappr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //현금IC승인
                String senddata = "0200" + fs + "I1" + fs + fs + etMoney.getText().toString() + fs + etTax.getText().toString() + fs + etBongsa.getText().toString() + fs + "00" + fs + fs + fs + etApprtid.getText().toString() + fs + fs + fs + fs + fs + fs + fs + etTxtnum.getText().toString() + fs + etFiller.getText().toString() + fs + fs + fs + fs + fs + fs + fs + fs + fs + fs + fs + fs + fs;
                send(senddata);
            }
        });

        btCashiccnl = (Button)findViewById(R.id.btcashiccnl);
        btCashiccnl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //현금IC취소
                String senddata = "0420" + fs + "I4" + fs + fs + etMoney.getText().toString() + fs + etTax.getText().toString() + fs + etBongsa.getText().toString() + fs + "00" + fs + etAgreenum.getText().toString() + fs + etAgreedate.getText().toString() + fs + etApprtid.getText().toString() + fs + fs + fs + fs + fs + fs + fs + etTxtnum.getText().toString() + fs + etFiller.getText().toString() + fs + fs + fs + fs + fs + fs + fs + fs + fs + fs + fs + fs + fs;
                send(senddata);
            }
        });

        btCashicbal = (Button)findViewById(R.id.btcashicbal);
        btCashicbal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //현금IC잔액조회
                //간소화
                String senddata = "0200" + fs + "I3" + fs + fs + "1" + fs + fs + fs + "00" + fs + fs + fs + fs + fs + fs + fs + fs + fs + fs + fs + etFiller.getText().toString() + fs + fs + fs +fs + fs + fs + fs + fs + fs + fs + fs + fs + fs;
                send(senddata);
            }
        });

        btCashicapprresult = (Button)findViewById(R.id.btcashicapprresult);
        btCashicapprresult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //현금IC승인결과조회
                String senddata = "0200" + fs + "I2" + fs + fs + etMoney.getText().toString() + fs + etTax.getText().toString() + fs + etBongsa.getText().toString() + fs + "00" + fs + etAgreenum.getText().toString() + fs + etAgreedate.getText().toString() + fs + etApprtid.getText().toString() + fs + fs + fs + fs + fs + fs + fs + etTxtnum.getText().toString() + fs + etFiller.getText().toString() + fs + fs + fs + fs + fs + fs + fs + fs + fs + fs + fs + fs + fs;
                send(senddata);
            }
        });

        btReqnocashiccnl = (Button)findViewById(R.id.btreqnocashiccnl);
        btReqnocashiccnl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //현금IC취소결과조회
                String senddata = "0420" + fs + "I4" + fs + fs + etMoney.getText().toString() + fs + etTax.getText().toString() + fs + etBongsa.getText().toString() + fs + "02" + fs + etAgreenum.getText().toString() + fs + etAgreedate.getText().toString() + fs + etApprtid.getText().toString() + fs + fs + fs + fs + fs + fs + fs + etTxtnum.getText().toString() + fs + etFiller.getText().toString() + fs + fs + fs + fs + fs + fs + fs + fs + fs + fs + fs + fs + fs;
                send(senddata);
            }
        });



        btPayproappr = (Button)findViewById(R.id.btpayproappr);
        btPayproappr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //PAYPRO 승인
                String senddata = "";
                if(cbSignuse.isChecked())
                    senddata = "0300" + fs + etApprtp.getText().toString() + fs + "L" + fs + etMoney.getText().toString() + fs + etTax.getText().toString() + fs + etBongsa.getText().toString() + fs + etHalbu.getText().toString() + fs + fs + fs + etApprtid.getText().toString() + fs + fs + fs + etCashnum.getText().toString() + fs + etMyunse.getText().toString() + fs + fs + fs + etTxtnum.getText().toString() + fs + etFiller.getText().toString() + fs + fs + "PRO" + fs + etDevicegb.getText().toString() + fs + fs + fs + fs + etSigndata.getText().toString() + fs + fs + fs + fs + fs + fs;
                else
                    senddata = "0300" + fs + etApprtp.getText().toString() + fs + "L" + fs + etMoney.getText().toString() + fs + etTax.getText().toString() + fs + etBongsa.getText().toString() + fs + etHalbu.getText().toString() + fs + fs + fs + etApprtid.getText().toString() + fs + fs + fs + etCashnum.getText().toString() + fs + etMyunse.getText().toString() + fs + fs + fs + etTxtnum.getText().toString() + fs + etFiller.getText().toString() + fs + fs + "PRO" + fs + etDevicegb.getText().toString() + fs + fs + fs + fs + fs + fs + fs + fs + fs + fs;
                send(senddata);
            }
        });



        btPayprocnl = (Button)findViewById(R.id.btpayprocnl);
        btPayprocnl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //PAYPRO 취소
                String senddata = "";
                if(cbSignuse.isChecked())
                    senddata = "0520" + fs + etApprtp.getText().toString() + fs + "L" + fs + etMoney.getText().toString() + fs + etTax.getText().toString() + fs + etBongsa.getText().toString() + fs + etHalbu.getText().toString() + fs + etAgreenum.getText().toString() + fs + etAgreedate.getText().toString() + fs + etApprtid.getText().toString() + fs + fs + fs + etCashnum.getText().toString() + fs + etMyunse.getText().toString() + fs + fs + fs + etTxtnum.getText().toString() + fs + etFiller.getText().toString() + fs + fs + "PRO" + fs + etDevicegb.getText().toString() + fs + fs + fs + fs + etSigndata.getText().toString() + fs + fs + fs + fs + fs + fs;
                else
                    senddata = "0520" + fs + etApprtp.getText().toString() + fs + "L" + fs + etMoney.getText().toString() + fs + etTax.getText().toString() + fs + etBongsa.getText().toString() + fs + etHalbu.getText().toString() + fs + etAgreenum.getText().toString() + fs + etAgreedate.getText().toString() + fs + etApprtid.getText().toString() + fs + fs + fs + etCashnum.getText().toString() + fs + etMyunse.getText().toString() + fs + fs + fs + etTxtnum.getText().toString() + fs + etFiller.getText().toString() + fs + fs + "PRO" + fs + etDevicegb.getText().toString() + fs + fs + fs + fs + fs + fs + fs + fs + fs + fs;
                send(senddata);
            }
        });
    }

    private void send(String senddata) {
        etSenddata.setText(senddata);

        Intent sendIntent = new Intent();
        sendIntent.setAction("NICEVCAT"); //setAction에 함수명
        sendIntent.putExtra("NVCATSENDDATA", senddata); //NVCATSENDDATA에 요청전문
        sendIntent.setType("text/plain"); //setType은 text/plain 필수
        startActivityForResult(sendIntent, SEND_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SEND_REQUEST_CODE) { //SEND_REQUEST_CODE에 대한 응답
            if (resultCode == RESULT_OK) {
                etReturnvalue.setText("" + data.getIntExtra("NVCATRETURNCODE", -99)); //리턴값
                etRecvdata.setText(data.getStringExtra("NVCATRECVDATA")); //응답전문
                RecvFS(data.getStringExtra("NVCATRECVDATA"));
                Toast.makeText(getApplicationContext(), "결제 완료", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                etReturnvalue.setText("" + data.getIntExtra("NVCATRETURNCODE", -99));
                etRecvdata.setText(data.getStringExtra("NVCATRECVDATA"));
                Toast.makeText(getApplicationContext(), "결제 실패", Toast.LENGTH_SHORT).show();
            }
        }
        else if (requestCode == SEND_REQUEST_CHKVALID) { //SEND_REQUEST_CHKVALID에 대한 응답
            if (resultCode == RESULT_OK) {
                etReturnvalue.setText("" + data.getIntExtra("NVCATRETURNCODE", -99)); //리턴값
                etRecvdata.setText(data.getStringExtra("NVCATRECVDATA")); //응답전문
                Toast.makeText(getApplicationContext(), "상호인증및무결성점검 성공", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                etReturnvalue.setText("" + data.getIntExtra("NVCATRETURNCODE", -99));
                etRecvdata.setText(data.getStringExtra("NVCATRECVDATA"));
                Toast.makeText(getApplicationContext(), "상호인증및무결성점검 실패", Toast.LENGTH_SHORT).show();
            }
        }
        else if (requestCode == SEND_REQUEST_CHKCARDBIN) { //SEND_REQUEST_CHKCARDBIN에 대한 응답
            if (resultCode == RESULT_OK) {
                etReturnvalue.setText("" + data.getIntExtra("NVCATRETURNCODE", -99)); //리턴값
                etRecvdata.setText(data.getStringExtra("NVCATRECVDATA")); //응답전문
                Toast.makeText(getApplicationContext(), "카드BIN 요청 성공", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                etReturnvalue.setText("" + data.getIntExtra("NVCATRETURNCODE", -99));
                etRecvdata.setText(data.getStringExtra("NVCATRECVDATA"));
                Toast.makeText(getApplicationContext(), "카드BIN 요청 실패", Toast.LENGTH_SHORT).show();
            }
        }
        else if (requestCode == SEND_REQUEST_CHKCASHIC) { //SEND_REQUEST_CHKCASHIC에 대한 응답
            if (resultCode == RESULT_OK) {
                etReturnvalue.setText("" + data.getIntExtra("NVCATRETURNCODE", -99)); //리턴값
                etRecvdata.setText(data.getStringExtra("NVCATRECVDATA")); //응답전문
                Toast.makeText(getApplicationContext(), "현금IC카드여부 성공", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                etReturnvalue.setText("" + data.getIntExtra("NVCATRETURNCODE", -99));
                etRecvdata.setText(data.getStringExtra("NVCATRECVDATA"));
                Toast.makeText(getApplicationContext(), "현금IC카드여부 실패", Toast.LENGTH_SHORT).show();
            }
        }
        else if (requestCode == SEND_REQUEST_CHKMEMBERSHIP) { //SEND_REQUEST_CHKMEMBERSHIP에 대한 응답
            if (resultCode == RESULT_OK) {
                etReturnvalue.setText("" + data.getIntExtra("NVCATRETURNCODE", -99)); //리턴값
                etRecvdata.setText(data.getStringExtra("NVCATRECVDATA")); //응답전문
                Toast.makeText(getApplicationContext(), "멤버쉽카드리딩 성공", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                etReturnvalue.setText("" + data.getIntExtra("NVCATRETURNCODE", -99));
                etRecvdata.setText(data.getStringExtra("NVCATRECVDATA"));
                Toast.makeText(getApplicationContext(), "멤버쉽카드리딩 실패", Toast.LENGTH_SHORT).show();
            }
        }
        else if (requestCode == SEND_REQUEST_NORMAL) { //SEND_REQUEST_NORMAL에 대한 응답
            if (resultCode == RESULT_OK) {
                etReturnvalue.setText("" + data.getIntExtra("NVCATRETURNCODE", -99)); //리턴값
                etRecvdata.setText(data.getStringExtra("NVCATRECVDATA")); //응답전문
                Toast.makeText(getApplicationContext(), "요청 성공", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                etReturnvalue.setText("" + data.getIntExtra("NVCATRETURNCODE", -99));
                etRecvdata.setText(data.getStringExtra("NVCATRECVDATA"));
                Toast.makeText(getApplicationContext(), "요청 실패", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void RecvFS(String recvdata) {
        int i, j = 0, k = 0;
        for (i = 0; i < recvdata.length(); i++) {
            if (recvdata.substring(i, i + 1).equals(String.valueOf(fs))) {
                k++;

                switch (k) {
                    case 1: //거래구분
                        strRecv01 = recvdata.substring(j, i);
                        j = i + 1;
                        break;
                    case 2: //거래유형
                        strRecv02 = recvdata.substring(j, i);
                        j = i + 1;
                        break;
                    case 3: //응답코드
                        strRecv03 = recvdata.substring(j, i);
                        j = i + 1;
                        break;
                    case 4: //거래금액
                        strRecv04 = recvdata.substring(j, i);
                        j = i + 1;
                        break;
                    case 5: //부가세
                        strRecv05 = recvdata.substring(j, i);
                        j = i + 1;
                        break;
                    case 6: //봉사료
                        strRecv06 = recvdata.substring(j, i);
                        j = i + 1;
                        break;
                    case 7: //할부
                        strRecv07 = recvdata.substring(j, i);
                        j = i + 1;
                        break;
                    case 8: //승인번호
                        strRecv08 = recvdata.substring(j, i);
                        etAgreenum.setText(strRecv08.replaceAll(" " , ""));
                        j = i + 1;
                        break;
                    case 9: //승인일자
                        strRecv09 = recvdata.substring(j, i);
                        if(strRecv09.replaceAll(" ", "").length() > 6)
                            etAgreedate.setText(strRecv09.replaceAll(" ", "").substring(0, 6));
                        else
                            etAgreenum.setText("");
                        j = i + 1;
                        break;
                    case 10: //발급사코드
                        strRecv10 = recvdata.substring(j, i);
                        j = i + 1;
                        break;
                    case 11: //발급사명
                        strRecv11 = recvdata.substring(j, i);
                        j = i + 1;
                        break;
                    case 12: //매입사코드
                        strRecv12 = recvdata.substring(j, i);
                        j = i + 1;
                        break;
                    case 13: //매입사명
                        strRecv13 = recvdata.substring(j, i);
                        j = i + 1;
                        break;
                    case 14: //가맹점번호
                        strRecv14 = recvdata.substring(j, i);
                        j = i + 1;
                        break;
                    case 15: //승인CATID
                        strRecv15 = recvdata.substring(j, i);
                        j = i + 1;
                        break;
                    case 16: //잔액
                        strRecv16 = recvdata.substring(j, i);
                        j = i + 1;
                        break;
                    case 17: //응답메시지
                        strRecv17 = recvdata.substring(j, i);
                        j = i + 1;
                        break;
                    case 18: //카드BIN
                        strRecv18 = recvdata.substring(j, i);
                        j = i + 1;
                        break;
                    case 19: //카드구분
                        strRecv19 = recvdata.substring(j, i);
                        j = i + 1;
                        break;
                    case 20: //전문관리번호
                        strRecv20 = recvdata.substring(j, i);
                        j = i + 1;
                        break;
                    case 21: //거래일련번호
                        strRecv21 = recvdata.substring(j, i);
                        etCashnum.setText(strRecv21);
                        j = i + 1;
                        break;
                    case 22: //발생포인트(할인금액)
                        strRecv22 = recvdata.substring(j, i);
                        j = i + 1;
                        break;
                    case 23: //가용포인트(지불금액)
                        strRecv23 = recvdata.substring(j, i);
                        j = i + 1;
                        break;
                    case 24: //누적포인트(잔액한도)
                        strRecv24 = recvdata.substring(j, i);
                        j = i + 1;
                        break;
                    case 25: //캐시백가맹점
                        strRecv25 = recvdata.substring(j, i);
                        j = i + 1;
                        break;
                    case 26: //캐시백승인번호
                        strRecv26 = recvdata.substring(j, i);
                        j = i + 1;
                        break;
                    case 27:
                        strRecv27 = recvdata.substring(j, i);
                        j = i + 1;
                        break;
                    case 28:
                        strRecv28 = recvdata.substring(j, i);
                        j = i + 1;
                        break;
                    case 29:
                        strRecv29 = recvdata.substring(j, i);
                        j = i + 1;
                        break;
                    case 30:
                        strRecv30 = recvdata.substring(j, i);
                        j = i + 1;
                        break;
                }
            }
        }
    }
}
