package com.example.blooddonation.firebasetemplate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DatabaseFetch implements FirebaseCustom {
    FirebaseFirestore db;
    FirebaseAuth auth;
    FirebaseUser user;
    private String fieldName = "";
    private List<String> allDocumentNameList;
    CallbackDatabase doctListCallback;
    CallbackDocument callbackDoc;
    CallbackDocumentField callbackDocumentField;

    public DatabaseFetch() {
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
    }

    EventListener<DocumentSnapshot> callbackDocSnapShot = new EventListener<DocumentSnapshot>() {
        @Override
        public void onEvent(@Nullable DocumentSnapshot document, @Nullable FirebaseFirestoreException error) {

            if (document != null && document.exists()) {
                //  Log.i("Fetched-Doc ", String.valueOf(document.getData()));
                callbackDoc.getDoc((HashMap<String, Object>) document.getData());
            }
        }
    };
    EventListener<DocumentSnapshot> callbackDocFieldSnapShot = new EventListener<DocumentSnapshot>() {
        @Override
        public void onEvent(@Nullable DocumentSnapshot document, @Nullable FirebaseFirestoreException error) {

            if (document != null && document.exists()) {
                //  Log.i("Fetched-Doc ", String.valueOf(document.getData()));
                callbackDocumentField.getField(document.get(fieldName));
                fieldName = "";
            }
        }
    };

    OnCompleteListener<QuerySnapshot> callbackDistrict = new OnCompleteListener<QuerySnapshot>() {
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    allDocumentNameList.add(document.getId());
                    //Log.i("FETCHED_District", document.getId() + " => " + document.getData());
                }
                //Log.i("FETCHED_District", String.valueOf(districtList));
                doctListCallback.getList(allDocumentNameList);
                //making the list empty or null so that existing data will remove
                if (allDocumentNameList != null) {
                    allDocumentNameList.clear();
                }

            }
        }
    };


    public void getAllDocumentName(String collectionName, CallbackDatabase c) {
        allDocumentNameList = new ArrayList<>();
        fetchCollection(collectionName, callbackDistrict);
        doctListCallback = c;
    }

    public void getDocument(String collectionName, String documentName, CallbackDocument callbackDoc) {
        this.callbackDoc = callbackDoc;
        fetchDocument(collectionName, documentName, callbackDocSnapShot);
    }

    public void getDocumentField(String collectionName, String documentName, String fieldName, CallbackDocumentField callBackDocumentField) {
        this.fieldName = fieldName;
        this.callbackDocumentField = callBackDocumentField;
        fetchDocument(collectionName, documentName, callbackDocFieldSnapShot);
    }


    OnCompleteListener<Void> callbackAddDoc = task -> {
        if (!task.isSuccessful())
            ;//do something
        else
            ;//do something
    };

    public void addDocument(String collectionName, String documentName, HashMap<String, Object> doc) {
        CollectionReference cref = db.collection(collectionName);
        DocumentReference docRef = cref.document(documentName);
        Task<Void> snapshotTask = docRef.set(doc);
        snapshotTask.addOnCompleteListener(callbackAddDoc);
    }


    public void updateDocument(String collectionName, String documentName, HashMap<String, Object> updatedData) {
        CollectionReference cref = db.collection(collectionName);
        DocumentReference docRef = cref.document(documentName);
        Task<Void> snapshotTask = docRef.set(updatedData, SetOptions.merge());
        snapshotTask.addOnCompleteListener(callbackAddDoc);
    }

    public void updateArrayItem(String collectionName, String documentName, String arrayName, Object data) {
        DocumentReference doc = db.collection(collectionName).document(documentName);
        doc.update(arrayName, FieldValue.arrayUnion(data));
    }

    public void deleteArrayItem(String collectionName, String documentName, String arrayName, Object data) {
        DocumentReference doc = db.collection(collectionName).document(documentName);
        doc.update(arrayName, FieldValue.arrayRemove(data));
    }


    public void deleteDocument(String collectionName, String documentName) {
        CollectionReference cref = db.collection(collectionName);
        DocumentReference docRef = cref.document(documentName);
        Task<Void> snapshotTask = docRef.delete();
        snapshotTask.addOnCompleteListener(callbackAddDoc);
    }


    private void fetchDocument(String collection, String doc, EventListener<DocumentSnapshot> callbackDocSnapShot) {
        CollectionReference cref = db.collection(collection);
        DocumentReference docRef = cref.document(doc);
        docRef.addSnapshotListener(callbackDocSnapShot);
    }

    private void fetchCollection(String collection, OnCompleteListener<QuerySnapshot> callback) {
        CollectionReference cRef = db.collection(collection);
        Task<QuerySnapshot> snapshotTask = cRef.get();
        snapshotTask.addOnCompleteListener(callback);
    }

}