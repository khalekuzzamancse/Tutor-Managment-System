package com.example.blooddonation.firebasetemplate;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BloodInfo {
    FirebaseFirestore db;
    CallbackDomainList callbackCustom;
    List<DomainUserInfo> list;

    private static final String collectionName = "TutorUserInfo";

    //
//    LocalDate date = LocalDate.now();
//    int m = date.getMonth().getValue();
//    int d = date.getDayOfMonth();
//    int y = date.getYear();
//    int todays = m * 30 + y * 365 + d;
//            Log.i("NEXTDAY", String.valueOf(days-todays));


    //<-----------Methods----->
    //<-----------Methods----->
    //<-----------Methods----->
    public BloodInfo() {

        db = FirebaseFirestore.getInstance();
        list = new ArrayList<>();
    }

    OnCompleteListener<QuerySnapshot> callbackQS = new OnCompleteListener<QuerySnapshot>() {
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    DomainUserInfo info = new DomainUserInfo();
                    info = document.toObject(DomainUserInfo.class);
                    list.add(info);


                    //   Log.i("ReceivedData-FirebaseCustom", document.getId() + " => " + document.getData());
                }
                callbackCustom.receivedList(list);
                //Log.i("FETCHED_District", String.valueOf(districtList));
                //making the list empty or null so that existing data will remove

            }
        }
    };
//    public void getBloodGroupSubDisWiseList(String bloodGroup, String district, String subDistrict, CallbackDomainList callbackCustom) {
//        this.callbackCustom = callbackCustom;
//        CollectionReference cref = db.collection("UserInfo");
//        Task<QuerySnapshot> snapshotTask = cref.whereEqualTo("BloodGroup", bloodGroup)
//                .whereEqualTo("District", district)
//                .whereEqualTo("SubDistrict", subDistrict)
//                .get();
//        snapshotTask.addOnCompleteListener(callbackQS);
//    }
//
//    public void getBloodGroupDisWiseList(String bloodGroup, String district, Callback callbackCustom) {
//        this.callbackCustom = callbackCustom;
//        CollectionReference cref = db.collection("UserInfo");
//        Task<QuerySnapshot> snapshotTask = cref.whereEqualTo("BloodGroup", bloodGroup)
//                .whereEqualTo("District", district)
//                .get();
//        snapshotTask.addOnCompleteListener(callbackQS);
//    }
//
//    public void getBloodGroupWiseList(String bloodGroup, Callback callbackCustom) {
//        this.callbackCustom = callbackCustom;
//        CollectionReference cref = db.collection("UserInfo");
//        Task<QuerySnapshot> snapshotTask = cref.whereEqualTo("BloodGroup", bloodGroup)
//                .get();
//        snapshotTask.addOnCompleteListener(callbackQS);
//    }

    public void getTutors(CallbackDomainList callbackCustom) {
        this.callbackCustom = callbackCustom;
        CollectionReference cRef = db.collection(collectionName);
        Task<QuerySnapshot> snapshotTask = cRef.get();
        snapshotTask.addOnCompleteListener(callbackQS);
    }

//    public void getDistrictWiseDonorList(String district, Callback callbackCustom) {
//        this.callbackCustom = callbackCustom;
//        CollectionReference cref = db.collection("UserInfo");
//        Task<QuerySnapshot> snapshotTask = cref.whereEqualTo("isDonor", "true")
//                .whereEqualTo("District", district)
//                .get();
//        snapshotTask.addOnCompleteListener(callbackQS);
//    }


}