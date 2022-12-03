package com.example.blooddonation.firebasetemplate;

import android.util.Log;

import java.util.HashMap;
import java.util.List;

public class FirebaseTemplateDemo {
    //demo for get the name off all document name of a collection

    public void demoGetAllDocumentName() {
        FirebaseCustom firebaseCustom = new DatabaseFetch();
//        //collectionName=DistrictList
//        firebaseCustom.getAllDocumentName("DistrictList", new CallbackDatabase() {
//            @Override
//            public void getList(List<String> list) {
//                Log.i("Fetched-AllDocumentName" + " of -> " + "DistrictList", String.valueOf(list));
//            }
//        });
        //lambda-version for multiple statement
//        firebaseCustom.getAllDocumentName("DistrictList", list -> {
//            Log.i("Fetched-AllDocumentName" + " of -> " + "DistrictList", String.valueOf(list)));
//        });

        //fetching using lambda,removing { },because of single statement
        firebaseCustom.getAllDocumentName("DistrictList", list ->
                Log.i("Fetched-AllDocumentName", String.valueOf(list)));

    }

    public void demoGetDocument() {
        FirebaseCustom firebaseCustom = new DatabaseFetch();
        //collectionName=DistrictList ,documentId/Name=Dhaka
        firebaseCustom.getDocument("DistrictList", "Dhaka", doc ->
                Log.i("Fetched-Document", String.valueOf(doc)));
    }

    public void demoFetDocumentField() {
        FirebaseCustom firebaseCustom = new DatabaseFetch();
        //collectionName=DistrictList ,documentId/Name=Dhaka
        //documentName/Id=jahidrana190153cse@gmail.com ,fieldName=Name
        firebaseCustom.getDocumentField("UserInfo", "jahidrana190153cse@gmail.com", "Name", fieldValue ->
                Log.i("Fetched-Document ,jahidrana190153cse@gmail.com ,FieldValue ->", String.valueOf(fieldValue)));

    }

    public void demoAddingDocument() {

        FirebaseCustom firebaseCustom = new DatabaseFetch();
        HashMap<String, Object> Data = new HashMap<>();
        Data.put("Name", "Babul Hossain");
        Data.put("Father_Name", "Mokbul Hossain");
        Data.put("Mother_Name", "Rahima Begum");
        Data.put("Job_Post", "Manager");
        Data.put("Salary", 20000);
        //collectionName=UserDemo ,documentId/Name=user@123
        firebaseCustom.addDocument("UserDemo", "user@123", Data);
    }

    public void demoUpdateDocument() {
        FirebaseCustom firebaseCustom = new DatabaseFetch();
        HashMap<String, Object> updatedData = new HashMap<>();
        updatedData.put("Name", "Karim");
        updatedData.put("Job_Post", "Student");
        updatedData.put("Salary", 5000);
        //collectionName=UserDemo ,documentId/Name=user@123
        firebaseCustom.updateDocument("UserDemo", "user@123", updatedData);
    }

    public void demoUpdateArrayItem() {
        FirebaseCustom firebaseCustom = new DatabaseFetch();
        //collectionName=ClassInfo ,documentId/Name=one
        //field(array/list)Name=subjectList ,arrayNewElement=Math
        //Note:array element can be any type(primitive or complex) as defined in the database
        //where we use array filed data type string so we are going to pass a string("Math)
        firebaseCustom.updateArrayItem("ClassInfo", "one", "subject", "Math");
    }

    public void demoDeleteArrayItem() {
        FirebaseCustom firebaseCustom = new DatabaseFetch();
        //collectionName=ClassInfo ,documentId/Name=one
        //field(array/list)Name=subjectList ,arrayNewElement=Math
        //Note:array element can be any type(primitive or complex) as defined in the database
        //where we use array filed data type string so we are going to pass a string("Math)
        firebaseCustom.deleteArrayItem("ClassList", "One", "subject", "Math");
    }

    public void demoDeleteDocument() {
        FirebaseCustom firebaseCustom = new DatabaseFetch();
        //deleting a specific document
        //collectionName=ClassInfo ,documentId/Name=one
        firebaseCustom.deleteDocument("Khalek", "one");
    }
}


