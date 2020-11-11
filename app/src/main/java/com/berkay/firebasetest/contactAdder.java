package com.berkay.firebasetest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class contactAdder extends AppCompatActivity {
    EditText nameText,numberText,birthdayText,extraText;
    FirebaseUser firebaseUser;
    FirebaseFirestore firestore;
    String uid,id;
    ToggleButton birthdayToggleButton, day1ToggleButton,day2ToggleButton,day3ToggleButton,day4ToggleButton,day5ToggleButton,day6ToggleButton,day7ToggleButton,
            day8ToggleButton,day9ToggleButton,day10ToggleButton,day11ToggleButton,weeklyToggleButton,monthlyToggleButton;
    Button deleteButton, saveButton, updateButton;
    TextView day1NameText,day2NameText,day3NameText,day4NameText,day5NameText,day6NameText,day7NameText,day8NameText,day9NameText,day10NameText,day11NameText;
    String day1Name,day2Name,day3Name,day4Name,day5Name,day6Name,day7Name,day8Name,day9Name,day10Name,day11Name;
    final ToggleStatus birthdayStatus = new ToggleStatus();
    final ToggleStatus day1Status = new ToggleStatus();
    final ToggleStatus day2Status = new ToggleStatus();
    final ToggleStatus day3Status = new ToggleStatus();
    final ToggleStatus day4Status = new ToggleStatus();
    final ToggleStatus day5Status = new ToggleStatus();
    final ToggleStatus day6Status = new ToggleStatus();
    final ToggleStatus day7Status = new ToggleStatus();
    final ToggleStatus day8Status = new ToggleStatus();
    final ToggleStatus day9Status = new ToggleStatus();
    final ToggleStatus day10Status = new ToggleStatus();
    final ToggleStatus day11Status = new ToggleStatus();
    final ToggleStatus monthlyStatus = new ToggleStatus();
    final ToggleStatus weeklyStatus = new ToggleStatus();
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_adder);

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        nameText = findViewById(R.id.nameText);
        numberText = findViewById(R.id.numberText);
        birthdayText = findViewById(R.id.birthdayText);
        extraText = findViewById(R.id.extraText);
        birthdayToggleButton = findViewById(R.id.birthdayToggleButton);
        day1ToggleButton = findViewById(R.id.day1ToggleButton);
        day2ToggleButton = findViewById(R.id.day2ToggleButton);
        day3ToggleButton = findViewById(R.id.day3ToggleButton);
        day4ToggleButton = findViewById(R.id.day4ToggleButton);
        day5ToggleButton = findViewById(R.id.day5ToggleButton);
        day6ToggleButton = findViewById(R.id.day6ToggleButton);
        day7ToggleButton = findViewById(R.id.day7ToggleButton);
        day8ToggleButton = findViewById(R.id.day8ToggleButton);
        day9ToggleButton = findViewById(R.id.day9ToggleButton);
        day10ToggleButton = findViewById(R.id.day10ToggleButton);
        day11ToggleButton = findViewById(R.id.day11ToggleButton);
        monthlyToggleButton = findViewById(R.id.monthlyToggleButton);
        weeklyToggleButton = findViewById(R.id.weeklyToggleButton);

        deleteButton = findViewById(R.id.deleteButton);
        saveButton = findViewById(R.id.saveButton);
        updateButton = findViewById(R.id.updateButton);

        day1NameText = findViewById(R.id.day1NameText);
        day2NameText = findViewById(R.id.day2NameText);
        day3NameText = findViewById(R.id.day3NameText);
        day4NameText = findViewById(R.id.day4NameText);
        day5NameText = findViewById(R.id.day5NameText);
        day6NameText = findViewById(R.id.day6NameText);
        day7NameText = findViewById(R.id.day7NameText);
        day8NameText = findViewById(R.id.day8NameText);
        day9NameText = findViewById(R.id.day9NameText);
        day10NameText = findViewById(R.id.day10NameText);
        day11NameText = findViewById(R.id.day11NameText);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = firebaseUser.getUid();
        firestore = FirebaseFirestore.getInstance();

        getTemplatesData();
        callToggleButtons();

        Intent intent = getIntent();
        String info = intent.getStringExtra("info");
        if (info.matches("old")){

            int birthdayToggle = intent.getIntExtra("birthdayToggle", 0);
            int day1Toggle = intent.getIntExtra("day1Toggle", 0);
            int day2Toggle = intent.getIntExtra("day2Toggle", 0);
            int day3Toggle = intent.getIntExtra("day3Toggle", 0);
            int day4Toggle = intent.getIntExtra("day4Toggle", 0);
            int day5Toggle = intent.getIntExtra("day5Toggle", 0);
            int day6Toggle = intent.getIntExtra("day6Toggle", 0);
            int day7Toggle = intent.getIntExtra("day7Toggle", 0);
            int day8Toggle = intent.getIntExtra("day8Toggle", 0);
            int day9Toggle = intent.getIntExtra("day9Toggle", 0);
            int day10Toggle = intent.getIntExtra("day10Toggle", 0);
            int day11Toggle = intent.getIntExtra("day11Toggle", 0);
            int monthlyToggle = intent.getIntExtra("monthlyToggle",0);
            int weeklyToggle = intent.getIntExtra("weeklyToggle",0);

            String name = intent.getStringExtra("name");
            String number = intent.getStringExtra("number");
            String birthday = intent.getStringExtra("birthday");
            String extra = intent.getStringExtra("extra");
            id = intent.getStringExtra("id");

            boolean boolValueBirthday;
            boolean boolValueDay1;
            boolean boolValueDay2;
            boolean boolValueDay3;
            boolean boolValueDay4;
            boolean boolValueDay5;
            boolean boolValueDay6;
            boolean boolValueDay7;
            boolean boolValueDay8;
            boolean boolValueDay9;
            boolean boolValueDay10;
            boolean boolValueDay11;
            boolean boolValueWeekly;
            boolean boolValueMonthly;

            if (birthdayToggle == 1) {
                boolValueBirthday = true;
            } else {
                boolValueBirthday = false;
            }

            if (day1Toggle == 1) {
                boolValueDay1 = true;
            } else {
                boolValueDay1 = false;
            }

            if (day2Toggle == 1) {
                boolValueDay2 = true;
            } else {
                boolValueDay2 = false;
            }

            if (day3Toggle == 1) {
                boolValueDay3 = true;
            } else {
                boolValueDay3 = false;
            }

            if (day4Toggle == 1) {
                boolValueDay4 = true;
            } else {
                boolValueDay4 = false;
            }

            if (day5Toggle == 1) {
                boolValueDay5 = true;
            } else {
                boolValueDay5 = false;
            }

            if (day6Toggle == 1) {
                boolValueDay6 = true;
            } else {
                boolValueDay6 = false;
            }

            if (day7Toggle == 1) {
                boolValueDay7 = true;
            } else {
                boolValueDay7 = false;
            }

            if (day8Toggle == 1) {
                boolValueDay8 = true;
            } else {
                boolValueDay8 = false;
            }

            if (day9Toggle == 1) {
                boolValueDay9 = true;
            } else {
                boolValueDay9 = false;
            }

            if (day10Toggle == 1) {
                boolValueDay10 = true;
            } else {
                boolValueDay10 = false;
            }

            if (day11Toggle == 1) {
                boolValueDay11 = true;
            } else {
                boolValueDay11 = false;
            }

            if (monthlyToggle==1){
                boolValueMonthly = true;
            }else {
                boolValueMonthly = false;
            }

            if (weeklyToggle == 1){
                boolValueWeekly = true;
            }else {
                boolValueWeekly = false;
            }

            nameText.setText(name);
            numberText.setText(number);
            birthdayText.setText(birthday);
            extraText.setText(extra);

            deleteButton.setVisibility(View.VISIBLE);
            updateButton.setVisibility(View.VISIBLE);
            saveButton.setVisibility(View.INVISIBLE);

            birthdayToggleButton.setChecked(boolValueBirthday);
            day1ToggleButton.setChecked(boolValueDay1);
            day2ToggleButton.setChecked(boolValueDay2);
            day3ToggleButton.setChecked(boolValueDay3);
            day4ToggleButton.setChecked(boolValueDay4);
            day5ToggleButton.setChecked(boolValueDay5);
            day6ToggleButton.setChecked(boolValueDay6);
            day7ToggleButton.setChecked(boolValueDay7);
            day8ToggleButton.setChecked(boolValueDay8);
            day9ToggleButton.setChecked(boolValueDay9);
            day10ToggleButton.setChecked(boolValueDay10);
            day11ToggleButton.setChecked(boolValueDay11);
            weeklyToggleButton.setChecked(boolValueWeekly);
            monthlyToggleButton.setChecked(boolValueMonthly);

        }else {
            nameText.setText("");
            numberText.setText("");
            birthdayText.setText("");
            extraText.setText("");
            birthdayToggleButton.setChecked(false);
            day1ToggleButton.setChecked(false);
            day2ToggleButton.setChecked(false);
            day3ToggleButton.setChecked(false);
            day4ToggleButton.setChecked(false);
            day5ToggleButton.setChecked(false);
            day6ToggleButton.setChecked(false);
            day7ToggleButton.setChecked(false);
            day8ToggleButton.setChecked(false);
            day9ToggleButton.setChecked(false);
            day10ToggleButton.setChecked(false);
            day11ToggleButton.setChecked(false);
            monthlyToggleButton.setChecked(false);
            weeklyToggleButton.setChecked(false);
            deleteButton.setVisibility(View.INVISIBLE);
            updateButton.setVisibility(View.INVISIBLE);
            saveButton.setVisibility(View.VISIBLE);
        }
    }

    public void saveRecord (View view){
        String name = nameText.getText().toString();
        String number = numberText.getText().toString();
        String birthday = birthdayText.getText().toString();
        String extra = extraText.getText().toString();

        if (!checkNumberInput() | !checkNameInput()){
            return;
        }

        int contactBirthdayToggle = birthdayStatus.getBirthdayStatus();
        int contactDay1Toggle = day1Status.getDay1Status();
        int contactDay2Toggle = day2Status.getDay2Status();
        int contactDay3Toggle = day3Status.getDay3Status();
        int contactDay4Toggle = day4Status.getDay4Status();
        int contactDay5Toggle = day5Status.getDay5Status();
        int contactDay6Toggle = day6Status.getDay6Status();
        int contactDay7Toggle = day7Status.getDay7Status();
        int contactDay8Toggle = day8Status.getDay8Status();
        int contactDay9Toggle = day9Status.getDay9Status();
        int contactDay10Toggle = day10Status.getDay10Status();
        int contactDay11Toggle = day11Status.getDay11Status();
        int contactWeeklyToggle = weeklyStatus.getWeeklyStatus();
        int contactMonthlyToggle = monthlyStatus.getMonthlyStatus();
        int birthdaySmsStatus = 1;
        int day1SmsStatus=1;
        int day2SmsStatus=1;
        int day3SmsStatus=1;
        int day4SmsStatus=1;
        int day5SmsStatus=1;
        int day6SmsStatus=1;
        int day7SmsStatus=1;
        int day8SmsStatus=1;
        int day9SmsStatus=1;
        int day10SmsStatus=1;
        int day11SmsStatus=1;
        int monthlySmsStatus=1;
        int weeklySmsStatus=1;

        if (contactBirthdayToggle==1){
            if (!checkBirthdayInput()){
                return;
            }
        }

        HashMap<String, Object> uploadContact = new HashMap<>();
        uploadContact.put("ID",uid);
        uploadContact.put("Name",name);
        uploadContact.put("Number",number);
        uploadContact.put("Birthday",birthday);
        uploadContact.put("Extra",extra);
        uploadContact.put("BirthdayToggle",contactBirthdayToggle);
        uploadContact.put("Day1Toggle",contactDay1Toggle);
        uploadContact.put("Day2Toggle",contactDay2Toggle);
        uploadContact.put("Day3Toggle",contactDay3Toggle);
        uploadContact.put("Day4Toggle",contactDay4Toggle);
        uploadContact.put("Day5Toggle",contactDay5Toggle);
        uploadContact.put("Day6Toggle",contactDay6Toggle);
        uploadContact.put("Day7Toggle",contactDay7Toggle);
        uploadContact.put("Day8Toggle",contactDay8Toggle);
        uploadContact.put("Day9Toggle",contactDay9Toggle);
        uploadContact.put("Day10Toggle",contactDay10Toggle);
        uploadContact.put("Day11Toggle",contactDay11Toggle);
        uploadContact.put("MonthlyToggle",contactMonthlyToggle);
        uploadContact.put("WeeklyToggle",contactWeeklyToggle);
        uploadContact.put("BirthdayStatus",birthdaySmsStatus);
        uploadContact.put("Day1Status",day1SmsStatus);
        uploadContact.put("Day2Status",day2SmsStatus);
        uploadContact.put("Day3Status",day3SmsStatus);
        uploadContact.put("Day4Status",day4SmsStatus);
        uploadContact.put("Day5Status",day5SmsStatus);
        uploadContact.put("Day6Status",day6SmsStatus);
        uploadContact.put("Day7Status",day7SmsStatus);
        uploadContact.put("Day8Status",day8SmsStatus);
        uploadContact.put("Day9Status",day9SmsStatus);
        uploadContact.put("Day10Status",day10SmsStatus);
        uploadContact.put("Day11Status",day11SmsStatus);
        uploadContact.put("MonthlyStatus",monthlySmsStatus);
        uploadContact.put("WeeklyStatus",weeklySmsStatus);

        firestore.collection("Contacts").add(uploadContact).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                String id = documentReference.getId();
                System.out.println(id);

                Toast.makeText(getApplicationContext(),"Kişi eklendi",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(contactAdder.this,ContactsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(contactAdder.this,"Bir Sorun Oluştu",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void deleteRecord(View view){
        firestore.collection("Contacts").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(contactAdder.this,"Silme İşlemi Başarılı",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(contactAdder.this,ContactsActivity.class);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(contactAdder.this,"Silme İşlemi Başarısız",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateRecord(View view){
        String name = nameText.getText().toString();
        String number = numberText.getText().toString();
        String birthday = birthdayText.getText().toString();
        String extra = extraText.getText().toString();
        int contactBirthdayToggle = birthdayStatus.getBirthdayStatus();
        int contactDay1Toggle = day1Status.getDay1Status();
        int contactDay2Toggle = day2Status.getDay2Status();
        int contactDay3Toggle = day3Status.getDay3Status();
        int contactDay4Toggle = day4Status.getDay4Status();
        int contactDay5Toggle = day5Status.getDay5Status();
        int contactDay6Toggle = day6Status.getDay6Status();
        int contactDay7Toggle = day7Status.getDay7Status();
        int contactDay8Toggle = day8Status.getDay8Status();
        int contactDay9Toggle = day9Status.getDay9Status();
        int contactDay10Toggle = day10Status.getDay10Status();
        int contactDay11Toggle = day11Status.getDay11Status();
        int contactWeeklyToggle = weeklyStatus.getWeeklyStatus();
        int contactMonthlyToggle = monthlyStatus.getMonthlyStatus();

        if (!checkNumberInput() | !checkNameInput()){
            return;
        }

        if (contactBirthdayToggle==1){
            if (!checkBirthdayInput()){
                return;
            }
        }

        HashMap<String, Object> uploadContact = new HashMap<>();
        //uploadContact.put("ID",uid);
        uploadContact.put("Name",name);
        uploadContact.put("Number",number);
        uploadContact.put("Birthday",birthday);
        uploadContact.put("Extra",extra);
        uploadContact.put("BirthdayToggle",contactBirthdayToggle);
        uploadContact.put("Day1Toggle",contactDay1Toggle);
        uploadContact.put("Day2Toggle",contactDay2Toggle);
        uploadContact.put("Day3Toggle",contactDay3Toggle);
        uploadContact.put("Day4Toggle",contactDay4Toggle);
        uploadContact.put("Day5Toggle",contactDay5Toggle);
        uploadContact.put("Day6Toggle",contactDay6Toggle);
        uploadContact.put("Day7Toggle",contactDay7Toggle);
        uploadContact.put("Day8Toggle",contactDay8Toggle);
        uploadContact.put("Day9Toggle",contactDay9Toggle);
        uploadContact.put("Day10Toggle",contactDay10Toggle);
        uploadContact.put("Day11Toggle",contactDay11Toggle);
        uploadContact.put("MonthlyToggle",contactMonthlyToggle);
        uploadContact.put("WeeklyToggle",contactWeeklyToggle);

        firestore.collection("Contacts").document(id).update(uploadContact).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(contactAdder.this,"Güncelleme Başarılı",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(contactAdder.this,ContactsActivity.class);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(contactAdder.this,"Güncelleme Başarısız",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getTemplatesData(){
        firestore.collection("Templates").whereEqualTo("ID",uid).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
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
                    }

                    day1NameText.setText(day1Name);
                    day2NameText.setText(day2Name);
                    day3NameText.setText(day3Name);
                    day4NameText.setText(day4Name);
                    day5NameText.setText(day5Name);
                    day6NameText.setText(day6Name);
                    day7NameText.setText(day7Name);
                    day8NameText.setText(day8Name);
                    day9NameText.setText(day9Name);
                    day10NameText.setText(day10Name);
                    day11NameText.setText(day11Name);

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(contactAdder.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    public void callToggleButtons(){
        birthdayToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    birthdayStatus.setBirthdayStatus(1);
                } else {
                    birthdayStatus.setBirthdayStatus(0);
                }

            }
        });

        day1ToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    day1Status.setDay1Status(1);
                } else {
                    day1Status.setDay1Status(0);
                }
            }
        });

        day2ToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    day2Status.setDay2Status(1);
                } else {
                    day2Status.setDay2Status(0);
                }
            }
        });
        day3ToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    day3Status.setDay3Status(1);
                } else {
                    day3Status.setDay3Status(0);
                }
            }
        });
        day4ToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    day4Status.setDay4Status(1);
                } else {
                    day4Status.setDay4Status(0);
                }
            }
        });
        day5ToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    day5Status.setDay5Status(1);
                } else {
                    day5Status.setDay5Status(0);
                }
            }
        });
        day6ToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    day6Status.setDay6Status(1);
                } else {
                    day6Status.setDay6Status(0);
                }
            }
        });
        day7ToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    day7Status.setDay7Status(1);
                } else {
                    day7Status.setDay7Status(0);
                }
            }
        });

        day8ToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    day8Status.setDay8Status(1);
                } else {
                    day8Status.setDay8Status(0);
                }
            }
        });

        day9ToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    day9Status.setDay9Status(1);
                } else {
                    day9Status.setDay9Status(0);
                }
            }
        });

        day10ToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    day10Status.setDay10Status(1);
                } else {
                    day10Status.setDay10Status(0);
                }
            }
        });

        day11ToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    day11Status.setDay11Status(1);
                } else {
                    day11Status.setDay11Status(0);
                }
            }
        });
        monthlyToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    monthlyStatus.setMonthlyStatus(1);
                }else {
                    monthlyStatus.setMonthlyStatus(0);
                }
            }
        });

        weeklyToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    weeklyStatus.setWeeklyStatus(1);
                }else {
                    weeklyStatus.setWeeklyStatus(0);
                }
            }
        });


    }

    public boolean checkNameInput(){
        String name = nameText.getText().toString().trim();

        if (name.isEmpty()){
            Toast.makeText(contactAdder.this,"İsim Boş Bırakılamaz",Toast.LENGTH_SHORT).show();
            return false;
        }else if (name.length()>20){
            Toast.makeText(contactAdder.this,"İsim 20 Karakterden Büyük Olamaz",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }

    public boolean checkNumberInput(){
        String number = numberText.getText().toString().trim();

        if (number.isEmpty()){
            Toast.makeText(contactAdder.this,"Numara Boş Bırakılamaz",Toast.LENGTH_SHORT).show();
            return false;
        }else if (number.length()<11){
            Toast.makeText(contactAdder.this,"Eksik Numara",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }

    public boolean checkBirthdayInput(){
        String birthday = birthdayText.getText().toString().trim();

        if (birthday.isEmpty()){
            Toast.makeText(contactAdder.this,"ON Modda D.G. Tarihi Boş Bırakılamaz",Toast.LENGTH_SHORT).show();
            return false;
        }else if (birthday.length()<5){
            Toast.makeText(contactAdder.this,"Eksik Doğum Günü Tarihi",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }

}