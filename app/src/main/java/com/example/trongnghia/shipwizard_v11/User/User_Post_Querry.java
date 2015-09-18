package com.example.trongnghia.shipwizard_v11.User;

import android.support.annotation.NonNull;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Trong Nghia on 9/18/2015.
 */
public class User_Post_Querry {

    public static String userID;
    public static String ads_type;
    public static String price;
    public static String title;
    public static String time;
    public static String location;
    public static String status;
    public List<ParseObject> postList;

    public User_Post_Querry(){
        this.userID = ParseUser.getCurrentUser().getObjectId();
        postList = new List<ParseObject>() {
            @Override
            public void add(int i, ParseObject parseObject) {
                postList.add(i, parseObject);
            }

            @Override
            public boolean add(ParseObject parseObject) {
                return false;
            }

            @Override
            public boolean addAll(int i, Collection<? extends ParseObject> collection) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends ParseObject> collection) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> collection) {
                return false;
            }

            @Override
            public ParseObject get(int i) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @NonNull
            @Override
            public Iterator<ParseObject> iterator() {
                return null;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @Override
            public ListIterator<ParseObject> listIterator() {
                return null;
            }

            @NonNull
            @Override
            public ListIterator<ParseObject> listIterator(int i) {
                return null;
            }

            @Override
            public ParseObject remove(int i) {
                return null;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> collection) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> collection) {
                return false;
            }

            @Override
            public ParseObject set(int i, ParseObject parseObject) {
                return null;
            }

            @Override
            public int size() {
                return 0;
            }

            @NonNull
            @Override
            public List<ParseObject> subList(int i, int i1) {
                return null;
            }

            @NonNull
            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @NonNull
            @Override
            public <T> T[] toArray(T[] ts) {
                return null;
            }
        };

    }

    public void getUserPost(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("OrderPost");
        query.whereEqualTo("UserID", this.userID);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objectList, ParseException e) {
                if (e == null) {
                    Log.d("score", "Retrieved " + objectList.size() + " scores");
                    getAds(objectList);
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
    }

    public void getAds(List<ParseObject> objectList){
        for (int i=0; i<objectList.size(); i++){
            postList.add(objectList.get(i));
        }
    }

}
