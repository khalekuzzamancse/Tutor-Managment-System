package com.example.blooddonation.firebasetemplate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FormFillUpInfo {
    CallbackStringList callback,callbackSubjectList;
    List<String> list;
    FirebaseFirestore db;
    private static final String collectionName = "DistrictList";
    private static final String classCollection = "Class";


    public FormFillUpInfo() {
        db = FirebaseFirestore.getInstance();
        list = new ArrayList<>();
    }

    OnCompleteListener<QuerySnapshot> callbackQS = new OnCompleteListener<QuerySnapshot>() {
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String info = document.getId();
                    //      info = document.toObject(String.class);
                    list.add(info);
                    //   Log.i("ReceivedData-FirebaseCustom", document.getId() + " => " + document.getData());
                }

                callback.receivedList(list);
                //Log.i("FETCHED_District", String.valueOf(districtList));
                //making the list empty or null so that existing data will remove

            }
        }
    };

    EventListener<DocumentSnapshot> callbackDocSnapShot = new EventListener<DocumentSnapshot>() {
        @Override
        public void onEvent(@Nullable DocumentSnapshot document, @Nullable FirebaseFirestoreException error) {

            if (document != null && document.exists()) {
                //  Log.i("Fetched-Doc ", String.valueOf(document.getData()));
                List<String> list = (List<String>) document.get("SubDistrict");
                callback.receivedList(list);
            }
        }
    };
    EventListener<DocumentSnapshot> subjectListSnap = new EventListener<DocumentSnapshot>() {
        @Override
        public void onEvent(@Nullable DocumentSnapshot document, @Nullable FirebaseFirestoreException error) {

            if (document != null && document.exists()) {
                //  Log.i("Fetched-Doc ", String.valueOf(document.getData()));
                List<String> list = (List<String>) document.get("subject");
                callbackSubjectList.receivedList(list);
            }
        }
    };

    public void getDistricts(CallbackStringList callback) {
        this.callback = callback;
        CollectionReference cref = db.collection(collectionName);
        Task<QuerySnapshot> snapshotTask = cref.get();
        snapshotTask.addOnCompleteListener(callbackQS);
    }

    public void getClassList(CallbackStringList callback) {
        this.callback = callback;
        CollectionReference cRef = db.collection(classCollection);
        Task<QuerySnapshot> snapshotTask = cRef.get();
        snapshotTask.addOnCompleteListener(callbackQS);
    }
    public void getSubjectList(String className,CallbackStringList callback)
    {
        this.callbackSubjectList = callback;
        DocumentReference docRef = db.collection(classCollection).document(className);
        docRef.addSnapshotListener(subjectListSnap);
    }



    public void getSubDistricts(String district, CallbackStringList callback) {
        this.callback = callback;
        DocumentReference docRef = db.collection(collectionName).document(district);
        docRef.addSnapshotListener(callbackDocSnapShot);
    }


}


