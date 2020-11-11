package com.berkay.firebasetest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.HashMap;

public class DialogClass extends AppCompatDialogFragment {
    DialogClassListener listener;
    FirebaseUser firebaseUser;
    String uid;
    FirebaseFirestore firestore;
    public ArrayList<String> idList;
    ToggleButton day1ToggleButton,day2ToggleButton,day3ToggleButton,day4ToggleButton,day5ToggleButton,day6ToggleButton,day7ToggleButton,day8ToggleButton,day9ToggleButton,day10ToggleButton,day11ToggleButton,monthlyToggleButton,weeklyToggleButton;
    int day1Status=0,day2Status=0,day3Status=0,day4Status=0,day5Status=0,day6Status=0,day7Status=0,day8Status=0,day9Status=0,day10Status=0,day11Status=0,monthlyStatus=0,weeklyStatus=0;
    String day1Name,day2Name,day3Name,day4Name,day5Name,day6Name,day7Name,day8Name,day9Name,day10Name,day11Name;
    TextView day1Text,day2Text,day3Text,day4Text,day5Text,day6Text,day7Text,day8Text,day9Text,day10Text,day11Text;

    public void setIdList(ArrayList<String> idList) {
        this.idList = idList;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_layout,null);

        day1Text = view.findViewById(R.id.day1Text);
        day2Text = view.findViewById(R.id.day2Text);
        day3Text = view.findViewById(R.id.day3Text);
        day4Text = view.findViewById(R.id.day4Text);
        day5Text = view.findViewById(R.id.day5Text);
        day6Text = view.findViewById(R.id.day6Text);
        day7Text = view.findViewById(R.id.day7Text);
        day8Text = view.findViewById(R.id.day8Text);
        day9Text = view.findViewById(R.id.day9Text);
        day10Text = view.findViewById(R.id.day10Text);
        day11Text = view.findViewById(R.id.day11Text);

        day1ToggleButton = view.findViewById(R.id.day1ToggleButton);
        day2ToggleButton = view.findViewById(R.id.day2ToggleButton);
        day3ToggleButton = view.findViewById(R.id.day3ToggleButton);
        day4ToggleButton = view.findViewById(R.id.day4ToggleButton);
        day5ToggleButton = view.findViewById(R.id.day5ToggleButton);
        day6ToggleButton = view.findViewById(R.id.day6ToggleButton);
        day7ToggleButton = view.findViewById(R.id.day7ToggleButton);
        day8ToggleButton = view.findViewById(R.id.day8ToggleButton);
        day9ToggleButton = view.findViewById(R.id.day9ToggleButton);
        day10ToggleButton = view.findViewById(R.id.day10ToggleButton);
        day11ToggleButton = view.findViewById(R.id.day11ToggleButton);
        weeklyToggleButton = view.findViewById(R.id.weeklyToggleButton);
        monthlyToggleButton = view.findViewById(R.id.monthlyToggleButton);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = firebaseUser.getUid();
        firestore = FirebaseFirestore.getInstance();

        callToggleButtons();
        getTemplatesData();

        builder.setView(view).setTitle("Güncelle:").setNegativeButton("İptal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println(idList);
                HashMap<String, Object> updateContactToggle = new HashMap<>();
                updateContactToggle.put("Day1Toggle",day1Status);
                updateContactToggle.put("Day2Toggle",day2Status);
                updateContactToggle.put("Day3Toggle",day3Status);
                updateContactToggle.put("Day4Toggle",day4Status);
                updateContactToggle.put("Day5Toggle",day5Status);
                updateContactToggle.put("Day6Toggle",day6Status);
                updateContactToggle.put("Day7Toggle",day7Status);
                updateContactToggle.put("Day8Toggle",day8Status);
                updateContactToggle.put("Day9Toggle",day9Status);
                updateContactToggle.put("Day10Toggle",day10Status);
                updateContactToggle.put("Day11Toggle",day11Status);
                updateContactToggle.put("MonthlyToggle",monthlyStatus);
                updateContactToggle.put("WeeklyToggle",weeklyStatus);

                listener.trial(day1Status);

                for (int i =0; i<idList.size(); i++){
                    System.out.println(idList);
                    firestore.collection("Contacts").document(idList.get(i)).update(updateContactToggle).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(),"Güncelleme Başarısız",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                Intent intent = new Intent(getActivity(),ContactsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);



            }
        });

        //editTextUserName = view.findViewById(R.id.editUserName);
        //editTextUserPassword = view.findViewById(R.id.editUserPassword);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (DialogClassListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+"Must İmplement ExampleDialogListener");
        }
    }

    public interface  DialogClassListener{
        void trial(int day1Toggle);
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

                        day1Text.setText(day1Name);
                        day2Text.setText(day2Name);
                        day3Text.setText(day3Name);
                        day4Text.setText(day4Name);
                        day5Text.setText(day5Name);
                        day6Text.setText(day6Name);
                        day7Text.setText(day7Name);
                        day8Text.setText(day8Name);
                        day9Text.setText(day9Name);
                        day10Text.setText(day10Name);
                        day11Text.setText(day11Name);
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(),e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void callToggleButtons(){

        day1ToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    day1Status =1;
                }else {
                    day1Status=0;
                }
            }
        });

        day2ToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    day2Status =1;
                }else {
                    day2Status=0;
                }
            }
        });

        day3ToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    day3Status =1;
                }else {
                    day3Status=0;
                }
            }
        });

        day4ToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    day4Status =1;
                }else {
                    day4Status=0;
                }
            }
        });

        day5ToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    day5Status =1;
                }else {
                    day5Status=0;
                }
            }
        });

        day6ToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    day6Status =1;
                }else {
                    day6Status=0;
                }
            }
        });

        day7ToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    day7Status =1;
                }else {
                    day7Status=0;
                }
            }
        });

        day8ToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    day8Status =1;
                }else {
                    day8Status=0;
                }
            }
        });

        day9ToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    day9Status =1;
                }else {
                    day9Status=0;
                }
            }
        });

        day10ToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    day10Status =1;
                }else {
                    day10Status=0;
                }
            }
        });

        day11ToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    day11Status =1;
                }else {
                    day11Status=0;
                }
            }
        });

        monthlyToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    monthlyStatus =1;
                }else {
                    monthlyStatus =0;
                }
            }
        });

        weeklyToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    weeklyStatus =1;
                }else {
                    weeklyStatus=0;
                }
            }
        });
    }
}
