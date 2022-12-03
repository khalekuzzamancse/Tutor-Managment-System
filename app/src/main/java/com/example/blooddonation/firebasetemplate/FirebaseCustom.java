package com.example.blooddonation.firebasetemplate;

import java.util.HashMap;

public interface FirebaseCustom {
    public void getAllDocumentName(String collectionName, CallbackDatabase c);

    public void getDocument(String collectionName, String documentName, CallbackDocument callbackDoc);

    public void getDocumentField(String collectionName, String documentName, String fieldName, CallBackDocumentField callbackDoc);

    public void addDocument(String collectionName, String documentName, HashMap<String, Object> doc);

    public void updateDocument(String collectionName, String documentName, HashMap<String, Object> updatedData);

    public void deleteDocument(String collectionName, String documentName);

    public void updateArrayItem(String collectionName, String documentName, String arrayName, Object data);
    public void deleteArrayItem(String collectionName, String documentName, String arrayName, Object data);
}
