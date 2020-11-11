package com.berkay.firebasetest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.HashMap;

public class TemplatesActivity extends AppCompatActivity {
    EditText nicknameTemplateEditText, birthdayTemplateEditText, day1TemplateEditText, day2TemplateEditText, day3TemplateEditText, day4TemplateEditText,monthlyTemplateEditText,
            day5TemplateEditText, day6TemplateEditText,day7TemplateEditText,day8TemplateEditText,day9TemplateEditText,day10TemplateEditText,day11TemplateEditText,weeklyTemplateEditText;
    EditText day1NameEditText,day2NameEditText,day3NameEditText,day4NameEditText,day5NameEditText,day6NameEditText,day7NameEditText,day8NameEditText,day9NameEditText,day10NameEditText,day11NameEditText;
    EditText day1DateEditText,day2DateEditText,day3DateEditText,day4DateEditText,day5DateEditText,day6DateEditText,day7DateEditText,day8DateEditText,day9DateEditText,day10DateEditText,day11DateEditText,monthlyDateEditText,weeklyDateEditText;
    Button updateButton;
    String uid,documentId;
    FirebaseFirestore firestore;
    FirebaseUser firebaseUser;
    String day1Date,day2Date,day3Date,day4Date,day5Date,day6Date,day7Date,day8Date,day9Date,day10Date,day11Date,monthlyDate,weeklyDate;
    String newDay1Date,newDay2Date,newDay3Date,newDay4Date,newDay5Date,newDay6Date,newDay7Date,newDay8Date,newDay9Date,newDay10Date,newDay11Date,newMonthlyDate,newWeeklyDate;
    ArrayList<String> idList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_templates);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.templates);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.settings:
                        Intent intent2 = new Intent(getApplicationContext(),SettingsActivity.class);
                        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent2);
                        finish();
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        Intent intent1 = new Intent(getApplicationContext(),MainActivity.class);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent1);
                        finish();
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.contacts:
                        Intent intent = new Intent(getApplicationContext(),ContactsActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.templates:
                        return true;
                }
                return false;
            }
        });

        idList = new ArrayList<>();

        day1NameEditText = findViewById(R.id.day1NameEditText);
        day2NameEditText = findViewById(R.id.day2NameEditText);
        day3NameEditText = findViewById(R.id.day3NameEditText);
        day4NameEditText = findViewById(R.id.day4NameEditText);
        day5NameEditText = findViewById(R.id.day5NameEditText);
        day6NameEditText = findViewById(R.id.day6NameEditText);
        day7NameEditText = findViewById(R.id.day7NameEditText);
        day8NameEditText = findViewById(R.id.day8NameEditText);
        day9NameEditText = findViewById(R.id.day9NameEditText);
        day10NameEditText = findViewById(R.id.day10NameEditText);
        day11NameEditText = findViewById(R.id.day11NameEditText);

        day1DateEditText = findViewById(R.id.day1DateEditText);
        day2DateEditText = findViewById(R.id.day2DateEditText);
        day3DateEditText = findViewById(R.id.day3DateEditText);
        day4DateEditText = findViewById(R.id.day4DateEditText);
        day5DateEditText = findViewById(R.id.day5DateEditText);
        day6DateEditText = findViewById(R.id.day6DateEditText);
        day7DateEditText = findViewById(R.id.day7DateEditText);
        day8DateEditText = findViewById(R.id.day8DateEditText);
        day9DateEditText = findViewById(R.id.day9DateEditText);
        day10DateEditText = findViewById(R.id.day10DateEditText);
        day11DateEditText = findViewById(R.id.day11DateEditText);
        monthlyDateEditText = findViewById(R.id.monthlyDateEditText);
        weeklyDateEditText = findViewById(R.id.weeklyDateEditText);

        nicknameTemplateEditText = findViewById(R.id.nicknameTemplateEditText);
        birthdayTemplateEditText = findViewById(R.id.birthdayTemplateEditText);
        day1TemplateEditText = findViewById(R.id.day1TemplateEditText);
        day2TemplateEditText = findViewById(R.id.day2TemplateEditText);
        day3TemplateEditText = findViewById(R.id.day3TemplateEditText);
        day4TemplateEditText = findViewById(R.id.day4TemplateEditText);
        day5TemplateEditText = findViewById(R.id.day5TemplateEditText);
        day6TemplateEditText = findViewById(R.id.day6TemplateEditText);
        day7TemplateEditText = findViewById(R.id.day7TemplateEditText);
        day8TemplateEditText = findViewById(R.id.day8TemplateEditText);
        day9TemplateEditText = findViewById(R.id.day9TemplateEditText);
        day10TemplateEditText = findViewById(R.id.day10TemplateEditText);
        day11TemplateEditText = findViewById(R.id.day11TemplateEditText);
        monthlyTemplateEditText = findViewById(R.id.monthlyTemplateEditText);
        weeklyTemplateEditText = findViewById(R.id.weeklyTemplateEditText);

        updateButton = findViewById(R.id.updateButton);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firestore = FirebaseFirestore.getInstance();
        uid = firebaseUser.getUid();
        getContactsData();
        getDataFromFirestore();

    }

    public void updateTemplate(View view) {
        String newNicknameTemplate = nicknameTemplateEditText.getText().toString();
        String newBirthdayTemplate = birthdayTemplateEditText.getText().toString();
        String newDay1Template = day1TemplateEditText.getText().toString();
        String newDay2Template = day2TemplateEditText.getText().toString();
        String newDay3Template = day3TemplateEditText.getText().toString();
        String newDay4Template = day4TemplateEditText.getText().toString();
        String newDay5Template = day5TemplateEditText.getText().toString();
        String newDay6Template = day6TemplateEditText.getText().toString();
        String newDay7Template = day7TemplateEditText.getText().toString();
        String newDay8Template = day8TemplateEditText.getText().toString();
        String newDay9Template = day9TemplateEditText.getText().toString();
        String newDay10Template = day10TemplateEditText.getText().toString();
        String newDay11Template = day11TemplateEditText.getText().toString();
        String newWeeklyTemplate = weeklyTemplateEditText.getText().toString();
        String newMonthlyTemplate = monthlyTemplateEditText.getText().toString();

        String newDay1Name = day1NameEditText.getText().toString();
        String newDay2Name = day2NameEditText.getText().toString();
        String newDay3Name = day3NameEditText.getText().toString();
        String newDay4Name = day4NameEditText.getText().toString();
        String newDay5Name = day5NameEditText.getText().toString();
        String newDay6Name = day6NameEditText.getText().toString();
        String newDay7Name = day7NameEditText.getText().toString();
        String newDay8Name = day8NameEditText.getText().toString();
        String newDay9Name = day9NameEditText.getText().toString();
        String newDay10Name = day10NameEditText.getText().toString();
        String newDay11Name = day11NameEditText.getText().toString();

        newDay1Date = day1DateEditText.getText().toString();
        newDay2Date = day2DateEditText.getText().toString();
        newDay3Date = day3DateEditText.getText().toString();
        newDay4Date = day4DateEditText.getText().toString();
        newDay5Date = day5DateEditText.getText().toString();
        newDay6Date = day6DateEditText.getText().toString();
        newDay7Date = day7DateEditText.getText().toString();
        newDay8Date = day8DateEditText.getText().toString();
        newDay9Date = day9DateEditText.getText().toString();
        newDay10Date = day10DateEditText.getText().toString();
        newDay11Date = day11DateEditText.getText().toString();
        newMonthlyDate = monthlyDateEditText.getText().toString();
        newWeeklyDate = weeklyDateEditText.getText().toString();

        HashMap<String, Object> updateDays = new HashMap<>();

        updateDays.put("BirthdayTemplate", newBirthdayTemplate);
        updateDays.put("NicknameTemplate", newNicknameTemplate);
        updateDays.put("Day1Template", newDay1Template);
        updateDays.put("Day2Template", newDay2Template);
        updateDays.put("Day3Template", newDay3Template);
        updateDays.put("Day4Template", newDay4Template);
        updateDays.put("Day5Template", newDay5Template);
        updateDays.put("Day6Template", newDay6Template);
        updateDays.put("Day7Template", newDay7Template);
        updateDays.put("Day8Template", newDay8Template);
        updateDays.put("Day9Template", newDay9Template);
        updateDays.put("Day10Template", newDay10Template);
        updateDays.put("Day11Template", newDay11Template);
        updateDays.put("WeeklyTemplate",newWeeklyTemplate);
        updateDays.put("MonthlyTemplate",newMonthlyTemplate);

        updateDays.put("Day1Name",newDay1Name);
        updateDays.put("Day2Name",newDay2Name);
        updateDays.put("Day3Name",newDay3Name);
        updateDays.put("Day4Name",newDay4Name);
        updateDays.put("Day5Name",newDay5Name);
        updateDays.put("Day6Name",newDay6Name);
        updateDays.put("Day7Name",newDay7Name);
        updateDays.put("Day8Name",newDay8Name);
        updateDays.put("Day9Name",newDay9Name);
        updateDays.put("Day10Name",newDay10Name);
        updateDays.put("Day11Name",newDay11Name);

        updateDays.put("Day1Date",newDay1Date);
        updateDays.put("Day2Date",newDay2Date);
        updateDays.put("Day3Date",newDay3Date);
        updateDays.put("Day4Date",newDay4Date);
        updateDays.put("Day5Date",newDay5Date);
        updateDays.put("Day6Date",newDay6Date);
        updateDays.put("Day7Date",newDay7Date);
        updateDays.put("Day8Date",newDay8Date);
        updateDays.put("Day9Date",newDay9Date);
        updateDays.put("Day10Date",newDay10Date);
        updateDays.put("Day11Date",newDay11Date);
        updateDays.put("MonthlyDate",newMonthlyDate);
        updateDays.put("WeeklyDate",newWeeklyDate);

        updateMessageStatus();

        firestore.collection("Templates").document(documentId).update(updateDays).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(TemplatesActivity.this, "Taslak Güncelleme Başarılı", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(TemplatesActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(TemplatesActivity.this, "Taslak Güncelleme Başarısız", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void getDataFromFirestore() {

        firestore.collection("Templates").whereEqualTo("ID",uid).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    System.out.println("burdasın");
                    for (QueryDocumentSnapshot documentSnapshot: task.getResult()){
                        String nicknameTemplate = documentSnapshot.get("NicknameTemplate").toString();
                        String birthdayTemplate = documentSnapshot.get("BirthdayTemplate").toString();
                        String day1Template = documentSnapshot.get("Day1Template").toString();
                        String day2Template = documentSnapshot.get("Day2Template").toString();
                        String day3Template = documentSnapshot.get("Day3Template").toString();
                        String day4Template = documentSnapshot.get("Day4Template").toString();
                        String day5Template = documentSnapshot.get("Day5Template").toString();
                        String day6Template = documentSnapshot.get("Day6Template").toString();
                        String day7Template = documentSnapshot.get("Day7Template").toString();
                        String day8Template = documentSnapshot.get("Day8Template").toString();
                        String day9Template = documentSnapshot.get("Day9Template").toString();
                        String day10Template = documentSnapshot.get("Day10Template").toString();
                        String day11Template = documentSnapshot.get("Day11Template").toString();
                        String weeklyTemplate = documentSnapshot.get("WeeklyTemplate").toString();
                        String monthlyTemplate = documentSnapshot.get("MonthlyTemplate").toString();

                        String day1Name = documentSnapshot.get("Day1Name").toString();
                        String day2Name = documentSnapshot.get("Day2Name").toString();
                        String day3Name = documentSnapshot.get("Day3Name").toString();
                        String day4Name = documentSnapshot.get("Day4Name").toString();
                        String day5Name = documentSnapshot.get("Day5Name").toString();
                        String day6Name = documentSnapshot.get("Day6Name").toString();
                        String day7Name = documentSnapshot.get("Day7Name").toString();
                        String day8Name = documentSnapshot.get("Day8Name").toString();
                        String day9Name = documentSnapshot.get("Day9Name").toString();
                        String day10Name = documentSnapshot.get("Day10Name").toString();
                        String day11Name = documentSnapshot.get("Day11Name").toString();

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

                        nicknameTemplateEditText.setText(nicknameTemplate);
                        birthdayTemplateEditText.setText(birthdayTemplate);
                        day1TemplateEditText.setText(day1Template);
                        day2TemplateEditText.setText(day2Template);
                        day3TemplateEditText.setText(day3Template);
                        day4TemplateEditText.setText(day4Template);
                        day5TemplateEditText.setText(day5Template);
                        day6TemplateEditText.setText(day6Template);
                        day7TemplateEditText.setText(day7Template);
                        day8TemplateEditText.setText(day8Template);
                        day9TemplateEditText.setText(day9Template);
                        day10TemplateEditText.setText(day10Template);
                        day11TemplateEditText.setText(day11Template);
                        weeklyTemplateEditText.setText(weeklyTemplate);
                        monthlyTemplateEditText.setText(monthlyTemplate);

                        day1NameEditText.setText(day1Name);
                        day2NameEditText.setText(day2Name);
                        day3NameEditText.setText(day3Name);
                        day4NameEditText.setText(day4Name);
                        day5NameEditText.setText(day5Name);
                        day6NameEditText.setText(day6Name);
                        day7NameEditText.setText(day7Name);
                        day8NameEditText.setText(day8Name);
                        day9NameEditText.setText(day9Name);
                        day10NameEditText.setText(day10Name);
                        day11NameEditText.setText(day11Name);

                        day1DateEditText.setText(day1Date);
                        day2DateEditText.setText(day2Date);
                        day3DateEditText.setText(day3Date);
                        day4DateEditText.setText(day4Date);
                        day5DateEditText.setText(day5Date);
                        day6DateEditText.setText(day6Date);
                        day7DateEditText.setText(day7Date);
                        day8DateEditText.setText(day8Date);
                        day9DateEditText.setText(day9Date);
                        day10DateEditText.setText(day10Date);
                        day11DateEditText.setText(day11Date);
                        weeklyDateEditText.setText(weeklyDate);
                        monthlyDateEditText.setText(monthlyDate);
                    }

                }else {
                    Toast.makeText(TemplatesActivity.this,task.getException().toString(),Toast.LENGTH_SHORT);
                }
            }
        });

    }


    public void updateMessageStatus(){
        if (newWeeklyDate.matches(weeklyDate)){
        }else{
            bindDatabase("WeeklyStatus");
        }
        if (newMonthlyDate.matches(monthlyDate)){
        }else {
            bindDatabase("MonthlyStatus");
        }
        if (newDay1Date.matches(day1Date)){
        }else {
            bindDatabase("Day1Status");
        }

        if (newDay2Date.matches(day2Date)){
        }else {
            bindDatabase("Day2Status");
        }

        if (newDay3Date.matches(day3Date)){
        }else {
            bindDatabase("Day3Status");
        }

        if (newDay4Date.matches(day4Date)){
        }else {
            bindDatabase("Day4Status");
        }

        if (newDay5Date.matches(day5Date)){
        }else {
            bindDatabase("Day5Status");
        }

        if (newDay6Date.matches(day6Date)){
        }else {
            bindDatabase("Day6Status");
        }

        if (newDay7Date.matches(day7Date)){
        }else {
            bindDatabase("Day7Status");
        }

        if (newDay8Date.matches(day8Date)){
        }else {
            bindDatabase("Day8Status");
        }

        if (newDay9Date.matches(day9Date)){
        }else {
            bindDatabase("Day9Status");
        }

        if (newDay10Date.matches(day10Date)){
        }else {
            bindDatabase("Day10Status");
        }

        if (newDay11Date.matches(day11Date)){
        }else {
            bindDatabase("Day11Status");
        }
    }

    public void bindDatabase(String dayName){
        HashMap<String,Object> updateMessageStatus = new HashMap<>();
        int bir = 1;
        updateMessageStatus.put(dayName,bir);

        for (int i=0; i<idList.size(); i++){
            firestore.collection("Contacts").document(idList.get(i)).update(updateMessageStatus).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(TemplatesActivity.this,"Güncelleme Başarılı",Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(TemplatesActivity.this,"e.getLocalizedMessage()",Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void getContactsData(){
        firestore.collection("Contacts").whereEqualTo("ID",uid).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        idList.add(documentSnapshot.getId());

                    }
                    System.out.println(idList);
                }else {
                    Toast.makeText(TemplatesActivity.this,"Bir Hata Oluştu",Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(TemplatesActivity.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

}