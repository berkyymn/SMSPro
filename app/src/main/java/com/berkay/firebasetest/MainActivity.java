package com.berkay.firebasetest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.SmsManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.onesignal.OneSignal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    Button sendSmsButton;
    TextView day1CounterText,errorTextview;
    String uid,documentId;
    FirebaseFirestore firestore;
    FirebaseUser firebaseUser;
    String date,day1Date,day2Date,day3Date,day4Date,day5Date,day6Date,day7Date,day8Date,day9Date,day10Date,day11Date,monthlyDate,weeklyDate;
    int birthdayCounter,day1Counter,day2Counter,day3Counter,day4Counter,day5Counter,day6Counter,day7Counter,day8Counter,day9Counter,day10Counter,day11Counter,weeklyCounter,monthlyCounter;

    String nicknameTemplate,birthdayTemplate,day1Template,day2Template,day3Template,day4Template,weeklyTemplate,monthlyTemplate,
            day5Template,day6Template,day7Template,day8Template,day9Template,day10Template,day11Template,
    day1Name,day2Name,day3Name,day4Name,day5Name,day6Name,day7Name,day8Name,day9Name,day10Name,day11Name;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;

    ArrayList<String> idList;
    ArrayList<String> numberArray;
    ArrayList<String> nameArray;
    ArrayList<String> birthdayArray;
    ArrayList<Integer> birthdayToggleArray;
    ArrayList<Integer> day1ToggleArray;
    ArrayList<Integer> day2ToggleArray;
    ArrayList<Integer> day3ToggleArray;
    ArrayList<Integer> day4ToggleArray;
    ArrayList<Integer> day5ToggleArray;
    ArrayList<Integer> day6ToggleArray;
    ArrayList<Integer> day7ToggleArray;
    ArrayList<Integer> day8ToggleArray;
    ArrayList<Integer> day9ToggleArray;
    ArrayList<Integer> day10ToggleArray;
    ArrayList<Integer> day11ToggleArray;
    ArrayList<Integer> monthlyToggleArray;
    ArrayList<Integer> weeklyToggleArray;

    ArrayList<Integer> birthdayMessageStatusArray;
    ArrayList<Integer> day1MessageStatusArray;
    ArrayList<Integer> day2MessageStatusArray;
    ArrayList<Integer> day3MessageStatusArray;
    ArrayList<Integer> day4MessageStatusArray;
    ArrayList<Integer> day5MessageStatusArray;
    ArrayList<Integer> day6MessageStatusArray;
    ArrayList<Integer> day7MessageStatusArray;
    ArrayList<Integer> day8MessageStatusArray;
    ArrayList<Integer> day9MessageStatusArray;
    ArrayList<Integer> day10MessageStatusArray;
    ArrayList<Integer> day11MessageStatusArray;
    ArrayList<Integer> monthlyMessageStatusArray;
    ArrayList<Integer> weeklyMessageStatusArray;
    String newNicknameTemplate,newDay1Template;

    ArrayList<String> newArrayList;
    ArrayList<String> smsList;

    char[] dateChar;
    String dayCheck;
    int sum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // OneSignal Initialization
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        OneSignal.idsAvailable(new OneSignal.IdsAvailableHandler() {
            @Override
            public void idsAvailable(String userId, String registrationId) {
                
            }
        });

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-8899434918779848/4265356564");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firestore = FirebaseFirestore.getInstance();
        uid = firebaseUser.getUid();

        day1CounterText = findViewById(R.id.day1CounterText);
        errorTextview = findViewById(R.id.errorTextView);

        errorTextview.setVisibility(View.INVISIBLE);

        sendSmsButton = findViewById(R.id.sendSmsButton);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.settings:
                        Intent intent = new Intent(getApplicationContext(),SettingsActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        if (mInterstitialAd.isLoaded()){
                            mInterstitialAd.show();
                        }else {
                            System.out.println("ad is not loaded");
                        }
                        finish();
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.home:

                        return true;

                    case R.id.contacts:
                        Intent intent1 = new Intent(getApplicationContext(),ContactsActivity.class);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent1);
                        finish();
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.templates:
                        Intent intent2 = new Intent(getApplicationContext(),TemplatesActivity.class);
                        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent2);
                        finish();
                        overridePendingTransition(0,0);
                        return true;

                }

                return false;
            }
        });

        idList = new ArrayList<>();
        numberArray = new ArrayList<>();
        nameArray = new ArrayList<>();
        birthdayArray = new ArrayList<>();
        birthdayToggleArray = new ArrayList<>();
        day1ToggleArray = new ArrayList<>();
        day2ToggleArray = new ArrayList<>();
        day3ToggleArray = new ArrayList<>();
        day4ToggleArray = new ArrayList<>();
        day5ToggleArray = new ArrayList<>();
        day6ToggleArray = new ArrayList<>();
        day7ToggleArray = new ArrayList<>();
        day8ToggleArray = new ArrayList<>();
        day9ToggleArray = new ArrayList<>();
        day10ToggleArray = new ArrayList<>();
        day11ToggleArray = new ArrayList<>();
        monthlyToggleArray = new ArrayList<>();
        weeklyToggleArray = new ArrayList<>();

        birthdayMessageStatusArray = new ArrayList<>();
        day1MessageStatusArray = new ArrayList<>();
        day2MessageStatusArray = new ArrayList<>();
        day3MessageStatusArray = new ArrayList<>();
        day4MessageStatusArray = new ArrayList<>();
        day5MessageStatusArray = new ArrayList<>();
        day6MessageStatusArray = new ArrayList<>();
        day7MessageStatusArray = new ArrayList<>();
        day8MessageStatusArray = new ArrayList<>();
        day9MessageStatusArray = new ArrayList<>();
        day10MessageStatusArray = new ArrayList<>();
        day11MessageStatusArray = new ArrayList<>();
        monthlyMessageStatusArray = new ArrayList<>();
        weeklyMessageStatusArray = new ArrayList<>();

        newArrayList = new ArrayList<>();

        sum=0;
        birthdayCounter=0;
        day1Counter = 0;
        day2Counter = 0;
        day3Counter = 0;
        day4Counter = 0;
        day5Counter = 0;
        day6Counter = 0;
        day7Counter = 0;
        day8Counter = 0;
        day9Counter = 0;
        day10Counter = 0;
        day11Counter = 0;
        weeklyCounter =0;
        monthlyCounter = 0;

        firestore.collection("Templates").whereEqualTo("ID",uid).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    QuerySnapshot querySnapshot = task.getResult();
                    if (querySnapshot.isEmpty()){
                        initializeTemplates();
                    }else {

                    }
                }else {
                    Toast.makeText(MainActivity.this,"Bir Hata Oluştu",Toast.LENGTH_SHORT).show();
                }
            }
        });


        SimpleDateFormat simpleDateFormat;
        simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        date = simpleDateFormat.format(new Date());
        System.out.println(date);

        dateChar = date.toCharArray();
        dayCheck = String.valueOf(dateChar,0,5);



        // izin kontrolleri

        if (ContextCompat.checkSelfPermission(this,Manifest.permission.SEND_SMS)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},1);
        }else{

        }

        sendSmsButton.setClickable(false);
        sendSmsButton.setText("Veriler Alınırken Bekleyiniz");
        // taslakların async yüklenmesini engellemek
        getTemplatesData(new FirestoreCallback() {
            @Override
            public void onCallback(List<String> nickname) {
                newNicknameTemplate = nicknameTemplate;
                newDay1Template = day1Template;

            }
        });

        getContactData(new FirestoreCallback() {
            @Override
            public void onCallback(List<String> name) {

                new CountDownTimer(3000,1000){

                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        birthdayCounterMethod();

                        if (date.matches(weeklyDate)){
                            weeklyCounterMethod();
                        }
                        if (date.matches(monthlyDate)){
                            monthlyCounterMethod();
                        }
                        if (date.matches(day1Date)){
                            day1CounterMethod();
                        }
                        if (date.matches(day2Date)){
                            day2CounterMethod();
                        }
                        if (date.matches(day3Date)){
                            day3CounterMethod();
                        }
                        if (date.matches(day4Date)){
                            day4CounterMethod();
                        }
                        if (date.matches(day5Date)){
                            day5CounterMethod();
                        }
                        if (date.matches(day6Date)){
                            day6CounterMethod();
                        }
                        if (date.matches(day7Date)){
                            day7CounterMethod();
                        }
                        if (date.matches(day8Date)){
                            day8CounterMethod();
                        }
                        if (date.matches(day9Date)){
                            day9CounterMethod();
                        }
                        if (date.matches(day10Date)){
                            day10CounterMethod();
                        }
                        if (date.matches(day11Date)){
                            day11CounterMethod();
                        }

                        sum =weeklyCounter+monthlyCounter+birthdayCounter+day1Counter+day2Counter+day3Counter+day4Counter+day5Counter+day6Counter+day7Counter+day7Counter+day8Counter+day9Counter+day10Counter+day11Counter;
                        day1CounterText.setText("Gönderilecek "+day1Name+  " mesaj sayısı: "+day1Counter+"\n"+"Gönderilecek "+day2Name+ " mesaj sayısı: "+day2Counter+"\n"+
                                "Gönderilecek "+day3Name+  " mesaj sayısı: "+day3Counter+"\n"+"Gönderilecek "+day4Name+ " mesaj sayısı: "+day4Counter+"\n"+
                                "Gönderilecek "+day5Name+  " mesaj sayısı: "+day5Counter+"\n"+"Gönderilecek "+day6Name+ " mesaj sayısı: "+day6Counter+"\n"+
                                "Gönderilecek "+day7Name+  " mesaj sayısı: "+day7Counter+"\n"+"Gönderilecek "+day8Name+ " mesaj sayısı: "+day8Counter+"\n"+
                                "Gönderilecek "+day9Name+  " mesaj sayısı: "+day9Counter+"\n"+"Gönderilecek "+day10Name+ " mesaj sayısı: "+day10Counter+"\n"+
                                "Gönderilecek "+day11Name+  " mesaj sayısı: "+day11Counter+"\n"+"Gönderilecek doğum günü mesaj sayısı: "+birthdayCounter+"\n"+
                                "Gönderilecek aylık mesaj sayısı: "+monthlyCounter+"\n"+"Gönderilecek haftalık mesaj sayısı: "+weeklyCounter+"\n"
                                + "Toplam Mesaj Sayısı: "+sum);

                        sendSmsButton.setText("SMS GÖNDER");
                        sendSmsButton.setClickable(true);
                        sendSmsButton.setBackgroundColor(sendSmsButton.getContext().getResources().getColor(R.color.colorPrimary));
                        sendSmsButton.setTextColor(Color.parseColor("#ffffff"));

                    }
                }.start();
            }
        });
    }

    public void initializeTemplates() {
        String newNicknameTemplate = "Sms Pro Kullanıcısı";
        String newBirthdayTemplate = "Sayın xxx, Doğum Gününüzü Kutlarım.";
        String newDay1Template = "Sayın xxx, Sağlıklı ve Mutlu Bir Yıl Dilerim";
        String newDay2Template = "Sayın xxx, 23 Nisan Ulusal Egemenlik ve Çocuk Bayramınızı Kutlarım ";
        String newDay3Template = "Sayın xxx, 30 Ağustos Zafer Bayramınızı Kutlarım.";
        String newDay4Template = "Sayın xxx, 29 Ekim Cumhuriyet Bayramınızı Kutlarım.";
        String newDay5Template = "Sayın xxx, Ramazan Bayramınız Kutlu Olsun";
        String newDay6Template = "Sayın xxx, Kurban Bayramınız Kutlu Olsun";
        String newDay7Template = "Sayın xxx, 1 Mayıs Emek ve Dayanışma Gününüz Kutlu Olsun.";
        String newDay8Template = "Sayın xxx, 15 Temmuz Demokrasi ve Milli Birlik Günü.";
        String newDay9Template = "Sayın xxx, iyi günler. Örnek Mesaj";
        String newDay10Template = "Sayın xxx, iyi günler. Örnek Mesaj";
        String newDay11Template = "Sayın xxx, iyi günler. Örnek Mesaj";
        String newMonthlyTemplate = "Sayın xxx, iyi günler. Bu mesaj aylık olarak gönderilecektir.";
        String newWeeklyTemplate = "Sayın xxx, iyi günler. Bu mesaj haftalık olarak gönderilecektir.";

        String newDay1Date = "01.01.2021";
        String newDay2Date = "23.04.2021";
        String newDay3Date = "30.08.2021";
        String newDay4Date = "29.10.2021";
        String newDay5Date = "13.05.2021";
        String newDay6Date = "20.07.2021";
        String newDay7Date = "01.05.2021";
        String newDay8Date = "15.07.2021";
        String newDay9Date = "GG.AA.YYYY";
        String newDay10Date = "GG.AA.YYYY";
        String newDay11Date = "GG.AA.YYYY";
        String newWeeklyDate = "01.01.2010";
        String newMonthlyDate = "01.01.2010";

        String newDay1Name = "Yılbaşı";
        String newDay2Name = "23 Nisan";
        String newDay3Name = "30 Ağustos";
        String newDay4Name = "29 Ekim";
        String newDay5Name = "R. Bayramı";
        String newDay6Name = "K. Bayramı";
        String newDay7Name = "1 Mayıs";
        String newDay8Name = "15 Temmuz";
        String newDay9Name = "Gün İsmi";
        String newDay10Name = "Gün İsmi";
        String newDay11Name = "Gün İsmi";

        HashMap<String, Object> uploadContact = new HashMap<>();
        uploadContact.put("ID",uid);
        uploadContact.put("BirthdayTemplate", newBirthdayTemplate);
        uploadContact.put("NicknameTemplate", newNicknameTemplate);
        uploadContact.put("Day1Template", newDay1Template);
        uploadContact.put("Day2Template", newDay2Template);
        uploadContact.put("Day3Template", newDay3Template);
        uploadContact.put("Day4Template", newDay4Template);
        uploadContact.put("Day5Template", newDay5Template);
        uploadContact.put("Day6Template", newDay6Template);
        uploadContact.put("Day7Template", newDay7Template);
        uploadContact.put("Day8Template", newDay8Template);
        uploadContact.put("Day9Template", newDay9Template);
        uploadContact.put("Day10Template", newDay10Template);
        uploadContact.put("Day11Template", newDay11Template);
        uploadContact.put("WeeklyTemplate",newWeeklyTemplate);
        uploadContact.put("MonthlyTemplate",newMonthlyTemplate);

        uploadContact.put("Day1Name",newDay1Name);
        uploadContact.put("Day2Name",newDay2Name);
        uploadContact.put("Day3Name",newDay3Name);
        uploadContact.put("Day4Name",newDay4Name);
        uploadContact.put("Day5Name",newDay5Name);
        uploadContact.put("Day6Name",newDay6Name);
        uploadContact.put("Day7Name",newDay7Name);
        uploadContact.put("Day8Name",newDay8Name);
        uploadContact.put("Day9Name",newDay9Name);
        uploadContact.put("Day10Name",newDay10Name);
        uploadContact.put("Day11Name",newDay11Name);

        uploadContact.put("Day1Date",newDay1Date);
        uploadContact.put("Day2Date",newDay2Date);
        uploadContact.put("Day3Date",newDay3Date);
        uploadContact.put("Day4Date",newDay4Date);
        uploadContact.put("Day5Date",newDay5Date);
        uploadContact.put("Day6Date",newDay6Date);
        uploadContact.put("Day7Date",newDay7Date);
        uploadContact.put("Day8Date",newDay8Date);
        uploadContact.put("Day9Date",newDay9Date);
        uploadContact.put("Day10Date",newDay10Date);
        uploadContact.put("Day11Date",newDay11Date);
        uploadContact.put("MonthlyDate",newMonthlyDate);
        uploadContact.put("WeeklyDate",newWeeklyDate);


        firestore.collection("Templates").add(uploadContact).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void sendSMS(View view){

        if (ContextCompat.checkSelfPermission(this,Manifest.permission.SEND_SMS)!=PackageManager.PERMISSION_GRANTED){
            Toast.makeText(MainActivity.this,"Önce SMS İzni Vermeniz Gerekiyor",Toast.LENGTH_LONG).show();
            return;
        }

        if (sum!=0){
            sendSmsButton.setText("Bekleyiniz");
            sendSmsButton.setClickable(false);
            sendBirthdayMessage();
            if (date.matches(weeklyDate)){
                sendWeeklyMessage();
                weeklyDateChange();
            }else {
                bindDatabase("WeeklyStatus");
            }
            if (date.matches(monthlyDate)){
                sendMonthlyMessage();
                monthlyDateChange();
            }else {
                bindDatabase("MonthlyStatus");
            }
            if (date.matches(day1Date)){
                sendDay1Message();
            }
            if (date.matches(day2Date)){
                sendDay2Message();
            }
            if (date.matches(day3Date)){
                sendDay3Message();
            }
            if (date.matches(day4Date)){
                sendDay4Message();
            }
            if (date.matches(day5Date)){
                sendDay5Message();
            }
            if (date.matches(day6Date)){
                sendDay6Message();
            }
            if (date.matches(day7Date)){
                sendDay7Message();
            }
            if (date.matches(day8Date)){
                sendDay8Message();
            }
            if (date.matches(day9Date)){
                sendDay9Message();
            }
            if (date.matches(day10Date)){
                sendDay10Message();
            }
            if (date.matches(day11Date)){
                sendDay11Message();
            }


            Toast.makeText(MainActivity.this,"Mesajlar Gönderiliyor",Toast.LENGTH_SHORT).show();
            sendSmsButton.setClickable(true);
            errorTextview.setVisibility(View.VISIBLE);
        }else {
            Toast.makeText(MainActivity.this,"Bugün Gönderilecek Mesajınız Yok :(",Toast.LENGTH_SHORT).show();
            sendSmsButton.setClickable(true);
            return;
        }
    }


    private void getContactData(final FirestoreCallback firestoreCallback) {
        CollectionReference collectionReference = firestore.collection("Contacts");

        Query query = collectionReference.whereEqualTo("ID",uid).orderBy("Name", Query.Direction.ASCENDING);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){

                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        nameArray.add(documentSnapshot.get("Name").toString());
                        numberArray.add(documentSnapshot.get("Number").toString());
                        birthdayArray.add(documentSnapshot.get("Birthday").toString());

                        birthdayToggleArray.add((Integer.parseInt(documentSnapshot.get("BirthdayToggle").toString())));
                        day1ToggleArray.add(Integer.parseInt(documentSnapshot.get("Day1Toggle").toString()));
                        day2ToggleArray.add(Integer.parseInt(documentSnapshot.get("Day2Toggle").toString()));
                        day3ToggleArray.add(Integer.parseInt(documentSnapshot.get("Day3Toggle").toString()));
                        day4ToggleArray.add(Integer.parseInt(documentSnapshot.get("Day4Toggle").toString()));
                        day5ToggleArray.add(Integer.parseInt(documentSnapshot.get("Day5Toggle").toString()));
                        day6ToggleArray.add(Integer.parseInt(documentSnapshot.get("Day6Toggle").toString()));
                        day7ToggleArray.add(Integer.parseInt(documentSnapshot.get("Day7Toggle").toString()));
                        day8ToggleArray.add(Integer.parseInt(documentSnapshot.get("Day8Toggle").toString()));
                        day9ToggleArray.add(Integer.parseInt(documentSnapshot.get("Day9Toggle").toString()));
                        day10ToggleArray.add(Integer.parseInt(documentSnapshot.get("Day10Toggle").toString()));
                        day11ToggleArray.add(Integer.parseInt(documentSnapshot.get("Day11Toggle").toString()));
                        monthlyToggleArray.add(Integer.parseInt(documentSnapshot.get("MonthlyToggle").toString()));
                        weeklyToggleArray.add(Integer.parseInt(documentSnapshot.get("WeeklyToggle").toString()));

                        idList.add(documentSnapshot.getId());
                        birthdayMessageStatusArray.add(Integer.parseInt(documentSnapshot.get("BirthdayStatus").toString()));
                        day1MessageStatusArray.add(Integer.parseInt(documentSnapshot.get("Day1Status").toString()));
                        day2MessageStatusArray.add(Integer.parseInt(documentSnapshot.get("Day2Status").toString()));
                        day3MessageStatusArray.add(Integer.parseInt(documentSnapshot.get("Day3Status").toString()));
                        day4MessageStatusArray.add(Integer.parseInt(documentSnapshot.get("Day4Status").toString()));
                        day5MessageStatusArray.add(Integer.parseInt(documentSnapshot.get("Day5Status").toString()));
                        day6MessageStatusArray.add(Integer.parseInt(documentSnapshot.get("Day6Status").toString()));
                        day7MessageStatusArray.add(Integer.parseInt(documentSnapshot.get("Day7Status").toString()));
                        day8MessageStatusArray.add(Integer.parseInt(documentSnapshot.get("Day8Status").toString()));
                        day9MessageStatusArray.add(Integer.parseInt(documentSnapshot.get("Day9Status").toString()));
                        day10MessageStatusArray.add(Integer.parseInt(documentSnapshot.get("Day10Status").toString()));
                        day11MessageStatusArray.add(Integer.parseInt(documentSnapshot.get("Day11Status").toString()));
                        monthlyMessageStatusArray.add(Integer.parseInt(documentSnapshot.get("MonthlyStatus").toString()));
                        weeklyMessageStatusArray.add(Integer.parseInt(documentSnapshot.get("WeeklyStatus").toString()));
                    }
                    firestoreCallback.onCallback(nameArray);


                }else {
                    System.out.println("hata mesajı aldın");
                }
            }
        });

    }

    private interface  FirestoreCallback{
        void onCallback(List<String> name);
    }

    private void getTemplatesData(final FirestoreCallback firestoreCallback){
        firestore.collection("Templates").whereEqualTo("ID",uid).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        nicknameTemplate = documentSnapshot.get("NicknameTemplate").toString();
                        birthdayTemplate = documentSnapshot.get("BirthdayTemplate").toString();
                        day1Template = documentSnapshot.get("Day1Template").toString();
                        day2Template = documentSnapshot.get("Day2Template").toString();
                        day3Template = documentSnapshot.get("Day3Template").toString();
                        day4Template = documentSnapshot.get("Day4Template").toString();
                        day5Template = documentSnapshot.get("Day5Template").toString();
                        day6Template = documentSnapshot.get("Day6Template").toString();
                        day7Template = documentSnapshot.get("Day7Template").toString();
                        day8Template = documentSnapshot.get("Day8Template").toString();
                        day9Template = documentSnapshot.get("Day9Template").toString();
                        day10Template = documentSnapshot.get("Day10Template").toString();
                        day11Template = documentSnapshot.get("Day11Template").toString();
                        monthlyTemplate = documentSnapshot.get("MonthlyTemplate").toString();
                        weeklyTemplate = documentSnapshot.get("WeeklyTemplate").toString();

                        day1Name = documentSnapshot.get("Day1Name").toString();
                        day2Name = documentSnapshot.get("Day2Name").toString();
                        day3Name = documentSnapshot.get("Day3Name").toString();
                        day4Name = documentSnapshot.get("Day4Name").toString();
                        day5Name = documentSnapshot.get("Day5Name").toString();
                        day6Name = documentSnapshot.get("Day6Name").toString();
                        day7Name = documentSnapshot.get("Day7Name").toString();
                        day8Name = documentSnapshot.get("Day8Name").toString();
                        day9Name = documentSnapshot.get("Day9Name").toString();
                        day10Name = documentSnapshot.get("Day10Name").toString();
                        day11Name = documentSnapshot.get("Day11Name").toString();

                        day1Date = documentSnapshot.get("Day1Date").toString();
                        day2Date = documentSnapshot.get("Day2Date").toString();
                        day3Date = documentSnapshot.get("Day3Date").toString();
                        day4Date = documentSnapshot.get("Day4Date").toString();
                        day5Date = documentSnapshot.get("Day5Date").toString();
                        day6Date = documentSnapshot.get("Day6Date").toString();
                        day7Date = documentSnapshot.get("Day7Date").toString();
                        day8Date = documentSnapshot.get("Day8Date").toString();
                        day9Date = documentSnapshot.get("Day9Date").toString();
                        day10Date = documentSnapshot.get("Day10Date").toString();
                        day11Date = documentSnapshot.get("Day11Date").toString();
                        monthlyDate = documentSnapshot.get("MonthlyDate").toString();
                        weeklyDate = documentSnapshot.get("WeeklyDate").toString();

                        documentId = documentSnapshot.getId();
                    }
                    firestoreCallback.onCallback(Collections.singletonList(nicknameTemplate));

                }else {
                    Toast.makeText(MainActivity.this,task.getException().toString(),Toast.LENGTH_SHORT);
                }
            }
        });
    }

    public void sendBirthdayMessage(){
        if (nameArray.size()>0){
            if (birthdayArray.size()>0){
                for (int i = 0; i<birthdayArray.size(); i++){
                    if (birthdayToggleArray.get(i)==1){
                        if (birthdayMessageStatusArray.get(i)==1){

                            String birthdayContact = birthdayArray.get(i);
                            char[] birthdayChar = birthdayContact.toCharArray();
                            String birthdayCheck = String.valueOf(birthdayChar,0,5);

                            if (birthdayCheck.matches(dayCheck)){


                                String message = birthdayTemplate+"\n"+nicknameTemplate;
                                SmsManager smsManager = SmsManager.getDefault();
                                if (birthdayTemplate.contains("xxx")){
                                    String replacement = nameArray.get(i).toUpperCase();
                                    String replaceMessage = message.replace("xxx",replacement.toUpperCase());
                                    smsList = smsManager.divideMessage(replaceMessage);

                                }else {
                                    smsList = smsManager.divideMessage(message);
                                }

                                //System.out.println(smsList);
                                smsManager.sendMultipartTextMessage(numberArray.get(i),null,smsList,null,null);
                                numberArray.set(i,numberArray.get(i)+",");

                                System.out.println(numberArray);
                                updateBirthdayStatus(idList.get(i));
                            }
                        }
                    }
                }


            }
        }
    }
    public void sendWeeklyMessage(){
        if (nameArray.size()>0){
            for (int i = 0; i<nameArray.size(); i++){
                if (weeklyToggleArray.get(i)==1){
                    if (weeklyMessageStatusArray.get(i)==1){
                        ArrayList<String> smsList;
                        String message = weeklyTemplate+"\n"+nicknameTemplate;
                        SmsManager smsManager = SmsManager.getDefault();
                        if (weeklyTemplate.contains("xxx")){
                            String replacement = nameArray.get(i).toUpperCase();
                            String replaceMessage = message.replace("xxx",replacement.toUpperCase());
                            //SmsManager smsManager = SmsManager.getDefault();
                            smsList = smsManager.divideMessage(replaceMessage);

                        }else {
                            //SmsManager smsManager = SmsManager.getDefault();
                            smsList = smsManager.divideMessage(message);
                        }

                        //System.out.println(smsList);
                        smsManager.sendMultipartTextMessage(numberArray.get(i),null,smsList,null,null);

                        updateWeeklyStatus(idList.get(i));
                    }
                }
            }
        }
    }
    public void sendMonthlyMessage(){
        if (nameArray.size()>0){
            for (int i=0; i<nameArray.size(); i++){
                if (monthlyToggleArray.get(i)==1){
                    if (monthlyMessageStatusArray.get(i)==1){
                        ArrayList<String> smsList;
                        String message = monthlyTemplate+"\n"+nicknameTemplate;
                        SmsManager smsManager = SmsManager.getDefault();
                        if (monthlyTemplate.contains("xxx")){
                            String replacement = nameArray.get(i).toUpperCase();
                            String replaceMessage = message.replace("xxx",replacement.toUpperCase());

                            smsList = smsManager.divideMessage(replaceMessage);

                        }else {
                            smsList = smsManager.divideMessage(message);
                        }

                        //System.out.println(smsList);
                        smsManager.sendMultipartTextMessage(numberArray.get(i),null,smsList,null,null);
                        updateMonthlyStatus(idList.get(i));
                    }
                }
            }
        }
    }

    public void sendDay1Message(){
        if (nameArray.size()>0){
            for (int i =0; i<nameArray.size(); i++){
                if (day1ToggleArray.get(i)==1){
                    if (day1MessageStatusArray.get(i)==1){
                        updateDay1Status(idList.get(i));
                        ArrayList<String> smsList;
                        String message = day1Template+"\n"+nicknameTemplate;
                        SmsManager smsManager = SmsManager.getDefault();
                        if (day1Template.contains("xxx")){
                            String replacement = nameArray.get(i).toUpperCase();
                            String replaceMessage = message.replace("xxx",replacement.toUpperCase());

                            smsList = smsManager.divideMessage(replaceMessage);

                        }else {
                            smsList = smsManager.divideMessage(message);
                        }

                        //System.out.println(smsList);
                        smsManager.sendMultipartTextMessage(numberArray.get(i),null,smsList,null,null);
                    }
                }
            }
        }
    }

    public void birthdayCounterMethod(){
        if (nameArray.size()>0){
            if (birthdayArray.size()>0){
                for (int i = 0; i<birthdayArray.size(); i++){
                    if (birthdayToggleArray.get(i)==1){
                        if (birthdayMessageStatusArray.get(i)==1){

                            String birthdayContact = birthdayArray.get(i);
                            char[] birthdayChar = birthdayContact.toCharArray();
                            String birthdayCheck = String.valueOf(birthdayChar,0,5);

                            if (birthdayCheck.matches(dayCheck)){

                                birthdayCounter = birthdayCounter+1;
                            }
                        }
                    }
                }
            }
        }
    }

    public void weeklyCounterMethod(){
        if (nameArray.size()>0){
            for (int i=0; i<nameArray.size(); i++){
                if (weeklyToggleArray.get(i)==1){
                    if (weeklyMessageStatusArray.get(i) == 1){
                        weeklyCounter = weeklyCounter+1;
                    }
                }
            }
        }
    }

    public void monthlyCounterMethod(){
        if (nameArray.size()>0){
            for (int i = 0; i<nameArray.size(); i++){
                if (monthlyToggleArray.get(i)==1){
                    if (monthlyMessageStatusArray.get(i)==1){
                        monthlyCounter = monthlyCounter+1;
                    }
                }
            }
        }
    }


    public void day1CounterMethod(){

        if (nameArray.size()>0){
            for (int i=0; i<nameArray.size(); i++){
                if (day1ToggleArray.get(i)==1){
                    if (day1MessageStatusArray.get(i) == 1){
                        day1Counter = day1Counter+1;
                    }
                }
            }
        }
    }

    public void day2CounterMethod(){

        if (nameArray.size()>0){
            for (int i=0; i<nameArray.size(); i++){
                if (day2ToggleArray.get(i)==1){
                    if (day2MessageStatusArray.get(i) == 1){
                        day2Counter = day2Counter+1;
                    }
                }
            }
        }
    }

    public void day3CounterMethod(){

        if (nameArray.size()>0){
            for (int i=0; i<nameArray.size(); i++){
                if (day3ToggleArray.get(i)==1){
                    if (day3MessageStatusArray.get(i) == 1){
                        day3Counter = day3Counter+1;
                    }
                }
            }
        }
    }

    public void day4CounterMethod(){

        if (nameArray.size()>0){
            for (int i=0; i<nameArray.size(); i++){
                if (day4ToggleArray.get(i)==1){
                    if (day4MessageStatusArray.get(i) == 1){
                        day4Counter = day4Counter+1;
                    }
                }
            }
        }
    }

    public void day5CounterMethod(){

        if (nameArray.size()>0){
            for (int i=0; i<nameArray.size(); i++){
                if (day5ToggleArray.get(i)==1){
                    if (day5MessageStatusArray.get(i) == 1){
                        day5Counter = day5Counter+1;
                    }
                }
            }
        }
    }

    public void day6CounterMethod(){
            if (nameArray.size()>0){
                for (int i=0; i<nameArray.size(); i++){
                    if (day6ToggleArray.get(i)==1){
                        if (day6MessageStatusArray.get(i) == 1){
                            day6Counter = day6Counter+1;
                        }
                    }
                }
            }
    }

    public void day7CounterMethod(){
        if (nameArray.size()>0){
            for (int i=0; i<nameArray.size(); i++){
                if (day7ToggleArray.get(i)==1){
                    if (day7MessageStatusArray.get(i) == 1){
                        day7Counter = day7Counter+1;
                    }
                }
            }
        }
    }

    public void day8CounterMethod(){
        if (nameArray.size()>0){
            for (int i=0; i<nameArray.size(); i++){
                if (day8ToggleArray.get(i)==1){
                    if (day8MessageStatusArray.get(i) == 1){
                        day8Counter = day8Counter+1;
                    }
                }
            }
        }
    }

    public void day9CounterMethod(){
        if (nameArray.size()>0){
            for (int i=0; i<nameArray.size(); i++){
                if (day9ToggleArray.get(i)==1){
                    if (day9MessageStatusArray.get(i) == 1){
                        day9Counter = day9Counter+1;
                    }
                }
            }
        }
    }

    public void day10CounterMethod(){
        if (nameArray.size()>0){
            for (int i=0; i<nameArray.size(); i++){
                if (day10ToggleArray.get(i)==1){
                    if (day10MessageStatusArray.get(i) == 1){
                        day10Counter = day10Counter+1;
                    }
                }
            }
        }
    }

    public void day11CounterMethod(){
        if (nameArray.size()>0){
            for (int i=0; i<nameArray.size(); i++){
                if (day11ToggleArray.get(i)==1){
                    if (day11MessageStatusArray.get(i) == 1){
                        day11Counter = day11Counter+1;
                    }
                }
            }
        }
    }

    public void sendDay2Message(){
        if (nameArray.size()>0){
            for (int i =0; i<nameArray.size(); i++){
                if (day2ToggleArray.get(i)==1){
                    if (day2MessageStatusArray.get(i)==1){
                        ArrayList<String> smsList;
                        String message = day2Template+"\n"+nicknameTemplate;
                        SmsManager smsManager = SmsManager.getDefault();
                        if (day2Template.contains("xxx")){
                            String replacement = nameArray.get(i).toUpperCase();
                            String replaceMessage = message.replace("xxx",replacement.toUpperCase());
                            smsList = smsManager.divideMessage(replaceMessage);

                        }else {
                            smsList = smsManager.divideMessage(message);
                        }

                        //System.out.println(smsList);
                        smsManager.sendMultipartTextMessage(numberArray.get(i),null,smsList,null,null);
                        updateDay2Status(idList.get(i));
                    }
                }
            }
        }
    }

    public void sendDay3Message(){
        if (nameArray.size()>0){
            for (int i =0; i<nameArray.size(); i++){
                if (day3ToggleArray.get(i)==1){
                    if (day3MessageStatusArray.get(i)==1){
                        ArrayList<String> smsList;
                        String message = day3Template+"\n"+nicknameTemplate;
                        SmsManager smsManager = SmsManager.getDefault();
                        if (day3Template.contains("xxx")){
                            String replacement = nameArray.get(i).toUpperCase();
                            String replaceMessage = message.replace("xxx",replacement.toUpperCase());
                            smsList = smsManager.divideMessage(replaceMessage);

                        }else {
                            smsList = smsManager.divideMessage(message);
                        }

                        //System.out.println(smsList);
                        smsManager.sendMultipartTextMessage(numberArray.get(i),null,smsList,null,null);
                        updateDay3Status(idList.get(i));
                    }
                }
            }
        }
    }
    public void sendDay4Message(){
        if (nameArray.size()>0){
            for (int i =0; i<nameArray.size(); i++){
                if (day4ToggleArray.get(i)==1){
                    if (day4MessageStatusArray.get(i)==1){
                        ArrayList<String> smsList;
                        String message = day4Template+"\n"+nicknameTemplate;
                        SmsManager smsManager = SmsManager.getDefault();
                        if (day4Template.contains("xxx")){
                            String replacement = nameArray.get(i).toUpperCase();
                            String replaceMessage = message.replace("xxx",replacement.toUpperCase());
                            smsList = smsManager.divideMessage(replaceMessage);

                        }else {
                            smsList = smsManager.divideMessage(message);
                        }

                        //System.out.println(smsList);
                        smsManager.sendMultipartTextMessage(numberArray.get(i),null,smsList,null,null);
                        updateDay4Status(idList.get(i));
                    }
                }
            }
        }
    }
    public void sendDay5Message(){
        if (nameArray.size()>0){
            for (int i =0; i<nameArray.size(); i++){
                if (day5ToggleArray.get(i)==1){
                    if (day5MessageStatusArray.get(i)==1){
                        ArrayList<String> smsList;
                        String message = day5Template+"\n"+nicknameTemplate;
                        SmsManager smsManager = SmsManager.getDefault();
                        if (day5Template.contains("xxx")){
                            String replacement = nameArray.get(i).toUpperCase();
                            String replaceMessage = message.replace("xxx",replacement.toUpperCase());
                            smsList = smsManager.divideMessage(replaceMessage);

                        }else {
                            smsList = smsManager.divideMessage(message);
                        }

                        //System.out.println(smsList);
                        smsManager.sendMultipartTextMessage(numberArray.get(i),null,smsList,null,null);
                        updateDay5Status(idList.get(i));
                    }
                }
            }
        }
    }
    public void sendDay6Message(){
        if (nameArray.size()>0){
            for (int i =0; i<nameArray.size(); i++){
                if (day6ToggleArray.get(i)==1){
                    if (day6MessageStatusArray.get(i)==1){
                        ArrayList<String> smsList;
                        String message = day6Template+"\n"+nicknameTemplate;
                        SmsManager smsManager = SmsManager.getDefault();
                        if (day6Template.contains("xxx")){
                            String replacement = nameArray.get(i).toUpperCase();
                            String replaceMessage = message.replace("xxx",replacement.toUpperCase());
                            smsList = smsManager.divideMessage(replaceMessage);

                        }else {
                            smsList = smsManager.divideMessage(message);
                        }

                        //System.out.println(smsList);
                        smsManager.sendMultipartTextMessage(numberArray.get(i),null,smsList,null,null);
                        updateDay6Status(idList.get(i));
                    }
                }
            }
        }
    }
    public void sendDay7Message(){
        if (nameArray.size()>0){
            for (int i =0; i<nameArray.size(); i++){
                if (day7ToggleArray.get(i)==1){
                    if (day7MessageStatusArray.get(i)==1){
                        ArrayList<String> smsList;
                        String message = day7Template+"\n"+nicknameTemplate;
                        SmsManager smsManager = SmsManager.getDefault();
                        if (day7Template.contains("xxx")){
                            String replacement = nameArray.get(i).toUpperCase();
                            String replaceMessage = message.replace("xxx",replacement.toUpperCase());
                            smsList = smsManager.divideMessage(replaceMessage);

                        }else {
                            smsList = smsManager.divideMessage(message);
                        }

                        //System.out.println(smsList);
                        smsManager.sendMultipartTextMessage(numberArray.get(i),null,smsList,null,null);
                        updateDay7Status(idList.get(i));
                    }
                }
            }
        }
    }

    public void sendDay8Message(){
        if (nameArray.size()>0){
            for (int i =0; i<nameArray.size(); i++){
                if (day8ToggleArray.get(i)==1){
                    if (day8MessageStatusArray.get(i)==1){
                        ArrayList<String> smsList;
                        String message = day8Template+"\n"+nicknameTemplate;
                        SmsManager smsManager = SmsManager.getDefault();
                        if (day8Template.contains("xxx")){
                            String replacement = nameArray.get(i).toUpperCase();
                            String replaceMessage = message.replace("xxx",replacement.toUpperCase());
                            smsList = smsManager.divideMessage(replaceMessage);

                        }else {
                            smsList = smsManager.divideMessage(message);
                        }

                        //System.out.println(smsList);
                        smsManager.sendMultipartTextMessage(numberArray.get(i),null,smsList,null,null);
                        updateDay8Status(idList.get(i));
                    }
                }
            }
        }
    }

    public void sendDay9Message(){
        if (nameArray.size()>0){
            for (int i =0; i<nameArray.size(); i++){
                if (day9ToggleArray.get(i)==1){
                    if (day9MessageStatusArray.get(i)==1){
                        ArrayList<String> smsList;
                        String message = day9Template+"\n"+nicknameTemplate;
                        SmsManager smsManager = SmsManager.getDefault();
                        if (day9Template.contains("xxx")){
                            String replacement = nameArray.get(i).toUpperCase();
                            String replaceMessage = message.replace("xxx",replacement.toUpperCase());
                            smsList = smsManager.divideMessage(replaceMessage);

                        }else {
                            smsList = smsManager.divideMessage(message);
                        }

                        //System.out.println(smsList);
                        smsManager.sendMultipartTextMessage(numberArray.get(i),null,smsList,null,null);
                        updateDay9Status(idList.get(i));
                    }
                }
            }
        }
    }

    public void sendDay10Message(){
        if (nameArray.size()>0){
            for (int i =0; i<nameArray.size(); i++){
                if (day10ToggleArray.get(i)==1){
                    if (day10MessageStatusArray.get(i)==1){
                        ArrayList<String> smsList;
                        String message = day10Template+"\n"+nicknameTemplate;
                        SmsManager smsManager = SmsManager.getDefault();
                        if (day10Template.contains("xxx")){
                            String replacement = nameArray.get(i).toUpperCase();
                            String replaceMessage = message.replace("xxx",replacement.toUpperCase());
                            smsList = smsManager.divideMessage(replaceMessage);

                        }else {
                            smsList = smsManager.divideMessage(message);
                        }

                        //System.out.println(smsList);
                        smsManager.sendMultipartTextMessage(numberArray.get(i),null,smsList,null,null);
                        updateDay10Status(idList.get(i));
                    }
                }
            }
        }
    }

    public void sendDay11Message(){
        if (nameArray.size()>0){
            for (int i =0; i<nameArray.size(); i++){
                if (day11ToggleArray.get(i)==1){
                    if (day11MessageStatusArray.get(i)==1){
                        ArrayList<String> smsList;
                        String message = day11Template+"\n"+nicknameTemplate;
                        SmsManager smsManager = SmsManager.getDefault();
                        if (day11Template.contains("xxx")){
                            String replacement = nameArray.get(i).toUpperCase();
                            String replaceMessage = message.replace("xxx",replacement.toUpperCase());
                            smsList = smsManager.divideMessage(replaceMessage);

                        }else {
                            smsList = smsManager.divideMessage(message);
                        }

                        //System.out.println(smsList);
                        smsManager.sendMultipartTextMessage(numberArray.get(i),null,smsList,null,null);
                        updateDay11Status(idList.get(i));
                    }
                }
            }
        }
    }

    public void updateMonthlyStatus(String id1){
        HashMap<String,Object> updateStatus = new HashMap<>();

        updateStatus.put("MonthlyStatus",0);

        firestore.collection("Contacts").document(id1).update(updateStatus).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                System.out.println("monthly status güncellendi");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("bir hata var");
            }
        });
    }

    public void updateWeeklyStatus(String id1){
        HashMap<String,Object> updateStatus = new HashMap<>();

        updateStatus.put("WeeklyStatus",0);

        firestore.collection("Contacts").document(id1).update(updateStatus).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                System.out.println("monthly status güncellendi");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("bir hata var");
            }
        });
    }


    public void updateBirthdayStatus(String id1){
        HashMap<String,Object> updateStatus = new HashMap<>();

        updateStatus.put("BirthdayStatus",0);

        firestore.collection("Contacts").document(id1).update(updateStatus).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                System.out.println("Birthday status güncellendi");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("bir hata var");
            }
        });
    }

    public void updateDay1Status(String id1){
        HashMap<String,Object> updateStatus = new HashMap<>();

        updateStatus.put("Day1Status",0);

        firestore.collection("Contacts").document(id1).update(updateStatus).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                System.out.println("day1 status güncellendi");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("bir hata var");
            }
        });
    }

    public void updateDay2Status(String id1){
        HashMap<String,Object> updateStatus = new HashMap<>();

        updateStatus.put("Day2Status",0);

        firestore.collection("Contacts").document(id1).update(updateStatus).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                System.out.println("day2 status güncellendi");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("bir hata var");
            }
        });
    }
    public void updateDay3Status(String id1){
        HashMap<String,Object> updateStatus = new HashMap<>();

        updateStatus.put("Day3Status",0);

        firestore.collection("Contacts").document(id1).update(updateStatus).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                System.out.println("day3 status güncellendi");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("bir hata var");
            }
        });

    }
    public void updateDay4Status(String id1){
        HashMap<String,Object> updateStatus = new HashMap<>();

        updateStatus.put("Day4Status",0);

        firestore.collection("Contacts").document(id1).update(updateStatus).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                System.out.println("day4 status güncellendi");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("bir hata var");
            }
        });
    }
    public void updateDay5Status(String id1){
        HashMap<String,Object> updateStatus = new HashMap<>();

        updateStatus.put("Day5Status",0);

        firestore.collection("Contacts").document(id1).update(updateStatus).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                System.out.println("day5 status güncellendi");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("bir hata var");
            }
        });
    }
    public void updateDay6Status(String id1){
        HashMap<String,Object> updateStatus = new HashMap<>();

        updateStatus.put("Day6Status",0);

        firestore.collection("Contacts").document(id1).update(updateStatus).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                System.out.println("day6 status güncellendi");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("bir hata var");
            }
        });
    }
    public void updateDay7Status(String id1){
        HashMap<String,Object> updateStatus = new HashMap<>();

        updateStatus.put("Day7Status",0);

        firestore.collection("Contacts").document(id1).update(updateStatus).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                System.out.println("day7 status güncellendi");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("bir hata var");
            }
        });
    }

    public void updateDay8Status(String id1){
        HashMap<String,Object> updateStatus = new HashMap<>();

        updateStatus.put("Day8Status",0);

        firestore.collection("Contacts").document(id1).update(updateStatus).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                System.out.println("day8 status güncellendi");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("bir hata var");
            }
        });
    }

    public void updateDay9Status(String id1){
        HashMap<String,Object> updateStatus = new HashMap<>();

        updateStatus.put("Day9Status",0);

        firestore.collection("Contacts").document(id1).update(updateStatus).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                System.out.println("day9 status güncellendi");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("bir hata var");
            }
        });
    }

    public void updateDay10Status(String id1){
        HashMap<String,Object> updateStatus = new HashMap<>();

        updateStatus.put("Day10Status",0);

        firestore.collection("Contacts").document(id1).update(updateStatus).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                System.out.println("day10 status güncellendi");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("bir hata var");
            }
        });
    }

    public void updateDay11Status(String id1){
        HashMap<String,Object> updateStatus = new HashMap<>();

        updateStatus.put("Day11Status",0);

        firestore.collection("Contacts").document(id1).update(updateStatus).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                System.out.println("day11 status güncellendi");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("bir hata var");
            }
        });
    }

    public void bindDatabase(String dayName){
        HashMap<String,Object> updateMessageStatus = new HashMap<>();
        int bir = 1;
        updateMessageStatus.put(dayName,bir);

        for (int i=0; i<idList.size(); i++){
            firestore.collection("Contacts").document(idList.get(i)).update(updateMessageStatus).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }
    }

    public void weeklyDateChange(){
        try {
            char[] weeklyChar = weeklyDate.toCharArray();
            String weeklyMonthString = String.valueOf(weeklyChar,3,2);

            String weeklyDayString = String.valueOf(weeklyChar,0,2);

            String weeklyYearString = String.valueOf(weeklyChar,6,4);

            int weeklyDayInt = Integer.parseInt(weeklyDayString);
            int weeklyMonthInt = Integer.parseInt(weeklyMonthString);
            int weeklyYearInt = Integer.parseInt(weeklyYearString);

            String dayString;
            String monthString;
            String yearString;

            int[] months= {31,28,31,30,31,30,31,31,30,31,30,31};

            int a = months[Integer.parseInt(weeklyMonthString)-1];

            if (weeklyMonthInt==12){
                if (weeklyDayInt + 7 > a){
                    weeklyYearInt = weeklyYearInt+1;
                    weeklyMonthInt = 01;
                    weeklyDayInt = weeklyDayInt-31+7;
                }else {
                    weeklyDayInt = weeklyDayInt+7;
                }
            }else {
                if (a==31){
                    if (weeklyDayInt+7>a){
                        weeklyMonthInt = weeklyMonthInt+1;
                        weeklyDayInt = weeklyDayInt-a+7;
                    }else {
                        weeklyDayInt = weeklyDayInt+7;
                    }

                }else if (a==30){
                    if (weeklyDayInt + 7 > a){
                        weeklyMonthInt = weeklyMonthInt+1;
                        weeklyDayInt = weeklyDayInt-a+7;
                    }else {
                        weeklyDayInt = weeklyDayInt+7;
                    }

                }else if (a==28){
                    if (weeklyDayInt + 7 > a){
                        weeklyMonthInt = weeklyMonthInt+1;
                        weeklyDayInt = weeklyDayInt-a+7;
                    }else {
                        weeklyDayInt = weeklyDayInt+7;
                    }
                }
            }

            if (weeklyDayInt<10){
               dayString = "0"+weeklyDayInt;
            }else {
                dayString = String.valueOf(weeklyDayInt);
            }
            if (weeklyMonthInt<10){
                monthString = "0"+weeklyMonthInt;
            }else {
                monthString = String.valueOf(weeklyMonthInt);
            }
            yearString = String.valueOf(weeklyYearInt);

            String newWeeklyTemplateDate = dayString+"."+monthString+"."+yearString;

            firestore.collection("Templates").document(documentId).update("WeeklyDate",newWeeklyTemplateDate).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    System.out.println("güncelleme başarılı");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    System.out.println("işlem başarısız");
                }
            });


        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

    }

    public void monthlyDateChange(){
        try {
            char[] monthlyChar = monthlyDate.toCharArray();
            String monthlyMonthString = String.valueOf(monthlyChar,3,2);

            String monthlyDayString = String.valueOf(monthlyChar,0,2);

            String monthlyYearString = String.valueOf(monthlyChar,6,4);

            int monthlyDayInt = Integer.parseInt(monthlyDayString);
            int monthlyMonthInt = Integer.parseInt(monthlyMonthString);
            int monthlyYearInt = Integer.parseInt(monthlyYearString);

            String dayString;
            String monthString;
            String yearString;

            if (monthlyMonthInt==12){
                monthlyYearInt = monthlyYearInt+1;
                monthlyMonthInt = 1;
            }else {
                monthlyMonthInt = monthlyMonthInt+1;
            }

            if (monthlyDayInt<10){
                dayString = "0"+monthlyDayInt;
            }else {
                dayString = String.valueOf(monthlyDayInt);
            }
            if (monthlyMonthInt<10){
                monthString = "0"+monthlyMonthInt;
            }else {
                monthString = String.valueOf(monthlyMonthInt);
            }

            yearString = String.valueOf(monthlyYearInt);

            String newMonthlyTemplateDate = dayString+"."+monthString+"."+yearString;

            firestore.collection("Templates").document(documentId).update("MonthlyDate",newMonthlyTemplateDate).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    System.out.println("güncelleme başarılı");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    System.out.println("işlem başarısız");
                }
            });
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
