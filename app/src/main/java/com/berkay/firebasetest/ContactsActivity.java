package com.berkay.firebasetest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
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
import com.wafflecopter.multicontactpicker.ContactResult;
import com.wafflecopter.multicontactpicker.LimitColumn;
import com.wafflecopter.multicontactpicker.MultiContactPicker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ContactsActivity extends AppCompatActivity implements DialogClass.DialogClassListener {
    ListView listView;
    FirebaseUser firebaseUser;
    String uid;
    FirebaseFirestore firestore;
    ArrayList<String> contactNameList,contactNumberList,contactBirthdayList,contactExtraList,idList;
    ArrayList<Integer> contactBirthdayToggleList,contactDay1ToggleList,contactDay2ToggleList,contactDay3ToggleList,contactDay4ToggleList,contactDay5ToggleList,contactDay6ToggleList,contactDay7ToggleList;
    ArrayList<Integer> contactDay8ToggleList,contactDay9ToggleList,contactDay10ToggleList,contactDay11ToggleList,contactMonthlyToggleList,contactWeeklyToggleList;
    private static final int CONTACT_PICKER_REQUEST =57 ;
    List<ContactResult> results = new ArrayList<>();
    myAdapter arrayAdapter;
    ArrayList<String> selectedMultipleItems;
    ArrayList<String> selectedMultipleId;
    int count = 0;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 2);
        } else {

        }

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-8899434918779848/1007572170");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.contacts);

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
                        Intent intent1 = new Intent(getApplicationContext(),MainActivity.class);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent1);
                        finish();

                        overridePendingTransition(0,0);
                        return true;

                    case R.id.contacts:
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

        contactNameList = new ArrayList<>();
        contactNumberList = new ArrayList<>();
        contactBirthdayList = new ArrayList<>();
        contactExtraList = new ArrayList<>();
        contactBirthdayToggleList = new ArrayList<>();
        contactDay1ToggleList = new ArrayList<>();
        contactDay2ToggleList = new ArrayList<>();
        contactDay3ToggleList = new ArrayList<>();
        contactDay4ToggleList = new ArrayList<>();
        contactDay5ToggleList = new ArrayList<>();
        contactDay6ToggleList = new ArrayList<>();
        contactDay7ToggleList = new ArrayList<>();
        contactDay8ToggleList = new ArrayList<>();
        contactDay9ToggleList = new ArrayList<>();
        contactDay10ToggleList = new ArrayList<>();
        contactDay11ToggleList = new ArrayList<>();
        contactMonthlyToggleList = new ArrayList<>();
        contactWeeklyToggleList = new ArrayList<>();
        idList = new ArrayList<>();
        selectedMultipleItems = new ArrayList<>();
        selectedMultipleId = new ArrayList<>();

        listView = findViewById(R.id.listView);
        firestore = FirebaseFirestore.getInstance();

        addFromContacts();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = firebaseUser.getUid();
        getDataFromFirestore();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), contactAdder.class);
                intent.putExtra("info", "old");

                intent.putExtra("name", contactNameList.get(position));
                intent.putExtra("number", contactNumberList.get(position));
                intent.putExtra("birthday", contactBirthdayList.get(position));
                intent.putExtra("extra", contactExtraList.get(position));
                intent.putExtra("birthdayToggle", contactBirthdayToggleList.get(position));
                intent.putExtra("day1Toggle", contactDay1ToggleList.get(position));
                intent.putExtra("day2Toggle", contactDay2ToggleList.get(position));
                intent.putExtra("day3Toggle", contactDay3ToggleList.get(position));
                intent.putExtra("day4Toggle", contactDay4ToggleList.get(position));
                intent.putExtra("day5Toggle", contactDay5ToggleList.get(position));
                intent.putExtra("day6Toggle", contactDay6ToggleList.get(position));
                intent.putExtra("day7Toggle", contactDay7ToggleList.get(position));
                intent.putExtra("day8Toggle", contactDay8ToggleList.get(position));
                intent.putExtra("day9Toggle", contactDay9ToggleList.get(position));
                intent.putExtra("day10Toggle", contactDay10ToggleList.get(position));
                intent.putExtra("day11Toggle", contactDay11ToggleList.get(position));
                intent.putExtra("monthlyToggle",contactMonthlyToggleList.get(position));
                intent.putExtra("weeklyToggle",contactWeeklyToggleList.get(position));
                intent.putExtra("id",idList.get(position));

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }

    private void getDataFromFirestore() {

        CollectionReference collectionReference = firestore.collection("Contacts");

        Query query = collectionReference.whereEqualTo("ID",uid).orderBy("Name", Query.Direction.ASCENDING);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){

                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                        contactNameList.add(documentSnapshot.get("Name").toString());
                        contactNumberList.add(documentSnapshot.get("Number").toString());
                        contactBirthdayList.add(documentSnapshot.get("Birthday").toString());
                        contactExtraList.add(documentSnapshot.get("Extra").toString());
                        contactBirthdayToggleList.add((Integer.parseInt(documentSnapshot.get("BirthdayToggle").toString())));
                        contactDay1ToggleList.add(Integer.parseInt(documentSnapshot.get("Day1Toggle").toString()));
                        contactDay2ToggleList.add(Integer.parseInt(documentSnapshot.get("Day2Toggle").toString()));
                        contactDay3ToggleList.add(Integer.parseInt(documentSnapshot.get("Day3Toggle").toString()));
                        contactDay4ToggleList.add(Integer.parseInt(documentSnapshot.get("Day4Toggle").toString()));
                        contactDay5ToggleList.add(Integer.parseInt(documentSnapshot.get("Day5Toggle").toString()));
                        contactDay6ToggleList.add(Integer.parseInt(documentSnapshot.get("Day6Toggle").toString()));
                        contactDay7ToggleList.add(Integer.parseInt(documentSnapshot.get("Day7Toggle").toString()));
                        contactDay8ToggleList.add(Integer.parseInt(documentSnapshot.get("Day8Toggle").toString()));
                        contactDay9ToggleList.add(Integer.parseInt(documentSnapshot.get("Day9Toggle").toString()));
                        contactDay10ToggleList.add(Integer.parseInt(documentSnapshot.get("Day10Toggle").toString()));
                        contactDay11ToggleList.add(Integer.parseInt(documentSnapshot.get("Day11Toggle").toString()));
                        contactMonthlyToggleList.add(Integer.parseInt(documentSnapshot.get("MonthlyToggle").toString()));
                        contactWeeklyToggleList.add(Integer.parseInt(documentSnapshot.get("WeeklyToggle").toString()));

                        idList.add(documentSnapshot.getId());
                    }
                    arrayAdapter = new myAdapter(getApplicationContext(),contactNameList);
                    listView.setAdapter(arrayAdapter);
                    arrayAdapter.notifyDataSetChanged();
                    listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
                    listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
                        @Override
                        public void onItemCheckedStateChanged(final ActionMode mode, int position, long id, boolean checked) {

                            if (checked==true){

                                selectedMultipleItems.add(contactNameList.get(position));
                                selectedMultipleId.add(idList.get(position));
                                count = count+1;
                            }else {

                                selectedMultipleItems.remove(contactNameList.get(position));
                                selectedMultipleId.remove(idList.get(position));
                                System.out.println(contactNameList.get(position));
                                count=count-1;
                            }
                            arrayAdapter.notifyDataSetChanged();
                            mode.setTitle(count +" Kişi Seçildi");
                        }

                        @Override
                        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                            MenuInflater menuInflater = mode.getMenuInflater();
                            menuInflater.inflate(R.menu.delete,menu);
                            return true;
                        }

                        @Override
                        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                            return false;
                        }

                        @Override
                        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                            switch (item.getItemId()){
                                case R.id.delete_contacts:
                                    for (int i =0; i< selectedMultipleId.size(); i++){
                                        firestore.collection("Contacts").document(selectedMultipleId.get(i)).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {

                                                Intent intent = new Intent(ContactsActivity.this,ContactsActivity.class);
                                                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                startActivity(intent);
                                                finish();



                                                arrayAdapter.notifyDataSetChanged();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(ContactsActivity.this,"Silme İşlemi Başarısız",Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                    }
                                    Toast.makeText(ContactsActivity.this,"Bekleyiniz "+count+" Kişi İçin Silme İşlemi Gerçekleşiyor",Toast.LENGTH_SHORT).show();
                                    count = 0;
                                    mode.finish();
                                    return true;

                                case R.id.update_contacts:
                                    DialogClass dialogClass = new DialogClass();
                                    dialogClass.setIdList(selectedMultipleId);
                                    dialogClass.show(getSupportFragmentManager(),"Dialog");
                                    System.out.println("buradasın");

                                    count=0;
                                    mode.finish();
                                    return true;

                                case R.id.selectAll:
                                    for (int i=0; i<contactNameList.size();i++){
                                        listView.setItemChecked(i,true);
                                    }
                                    return true;

                                default:
                                    return false;
                            }
                        }

                        @Override
                        public void onDestroyActionMode(ActionMode mode) {
                            count=0;
                        }
                    });
                }else {
                    System.out.println("hata mesajı aldın");
                }
            }
        });

    }

    public void goToContacts(View view){
        new MultiContactPicker.Builder(ContactsActivity.this) //Activity/fragment context
                //.theme(R.style.MyCustomPickerTheme) //Optional - default: MultiContactPicker.Azure
                .hideScrollbar(false) //Optional - default: false
                .showTrack(true) //Optional - default: true
                .searchIconColor(Color.WHITE) //Option - default: White
                .setChoiceMode(MultiContactPicker.CHOICE_MODE_MULTIPLE) //Optional - default: CHOICE_MODE_MULTIPLE
                .handleColor(ContextCompat.getColor(ContactsActivity.this, R.color.colorPrimary)) //Optional - default: Azure Blue
                .bubbleColor(ContextCompat.getColor(ContactsActivity.this, R.color.colorPrimary)) //Optional - default: Azure Blue
                .bubbleTextColor(Color.WHITE) //Optional - default: White
                .setTitleText("Kişi Seçin") //Optional - default: Select Contacts
                //.setSelectedContacts("10", "5" / myList) //Optional - will pre-select contacts of your choice. String... or List<ContactResult>
                .setLoadingType(MultiContactPicker.LOAD_ASYNC) //Optional - default LOAD_ASYNC (wait till all loaded vs stream results)
                .limitToColumn(LimitColumn.NONE) //Optional - default NONE (Include phone + email, limiting to one can improve loading time)
                .setActivityAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
                        android.R.anim.slide_in_left,
                        android.R.anim.slide_out_right) //Optional - default: No animation overrides

                .showPickerForResult(CONTACT_PICKER_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CONTACT_PICKER_REQUEST){
            if(resultCode == RESULT_OK) {
                results = MultiContactPicker.obtainResult(data);

                for (int i = 0; i<results.size(); i++){

                }
                List<ContactResult> results = MultiContactPicker.obtainResult(data);
                Log.d("MyTag", results.get(0).getDisplayName());
            } else if(resultCode == RESULT_CANCELED){
                System.out.println("User closed the picker without selecting items.");
            }
        }
    }

    public void addContact(View view) {
        Intent intent = new Intent(getApplicationContext(), contactAdder.class);
        intent.putExtra("info","new");
        startActivity(intent);
    }

    public void addFromContacts(){
        if (!results.isEmpty()) {

            for (int i = 0; i<results.size(); i++){
                String contactName = results.get(i).getDisplayName();
                String contactNumber = results.get(i).getPhoneNumbers().get(0).getNumber();

                String contactBirthday = "";
                String contactExtra = "";
                int contactBirthdayToggle = 0;
                int contactDay1Toggle = 0;
                int contactDay2Toggle = 0;
                int contactDay3Toggle = 0;
                int contactDay4Toggle = 0;
                int contactDay5Toggle = 0;
                int contactDay6Toggle = 0;
                int contactDay7Toggle = 0;
                int contactDay8Toggle = 0;
                int contactDay9Toggle = 0;
                int contactDay10Toggle = 0;
                int contactDay11Toggle = 0;
                int contactWeeklyToggle = 0;
                int contactMonthlyToggle = 0;
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
                int weeklySmsStatus=1;
                int monthlySmsStatus=1;

                HashMap<String, Object> uploadContact = new HashMap<>();
                uploadContact.put("ID",uid);
                uploadContact.put("Name",contactName);
                uploadContact.put("Number",contactNumber);
                uploadContact.put("Birthday",contactBirthday);
                uploadContact.put("Extra",contactExtra);

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
                uploadContact.put("WeeklyStatus",weeklySmsStatus);
                uploadContact.put("MonthlyStatus",monthlySmsStatus);

                firestore.collection("Contacts").add(uploadContact).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(ContactsActivity.this,"Kişi(ler) Ekleniyor Bekleyiniz",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),ContactsActivity.class);
                        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                });
            }arrayAdapter.notifyDataSetChanged();
        }results.clear();
    }

    @Override
    protected void onResume() {
        addFromContacts();
        super.onResume();
    }


    @Override
    public void trial(int day1Toggle) {
        System.out.println(day1Toggle);
    }

    class myAdapter extends ArrayAdapter<String>{
        Context context;
        ArrayList contactName;

        public myAdapter(Context c, ArrayList<String> name){

            super(c,R.layout.custom_layout,R.id.list_item,name);
            this.context=c;
            this.contactName=name;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            View custom = View.inflate(context,R.layout.custom_layout,null);


            TextView myContactName = (TextView) custom.findViewById(R.id.list_item);

            myContactName.setText(contactName.get(position).toString());
            return custom;
        }
    }
}