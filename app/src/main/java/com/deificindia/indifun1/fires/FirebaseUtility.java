package com.deificindia.indifun1.fires;

import androidx.annotation.NonNull;

import com.deificindia.indifun1.Utility.IndifunApplication;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

import static com.deificindia.indifun1.Utility.Logger.loge;

public class FirebaseUtility {

    public static final String COLLECTION_LIVE = "live_collection";

    public static void settings(FirebaseFirestore firebaseFirestore){
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
        firebaseFirestore.setFirestoreSettings(settings);
    }

    public static void addBroadcast(String uid,
                                    String title,
                                    long broadtype,
                                    long broad_id,
                                    String room_id,
                                    String rtcChannelName
                                    ){

        loge("FireUtils", "con");
        //String uid = IndifunApplication.getInstance().getuserid();
        FirebaseFirestore.getInstance().collection(COLLECTION_LIVE)
                .document(uid)
                .set(new AddBroad(uid,
                                title,
                                broadtype,
                                broad_id,
                                room_id,
                                rtcChannelName
                        ),
                        SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
        });
    }

    public static void updateIsbroadcasting(boolean isBroadcasting){
        String uid = IndifunApplication.getInstance().getCredential().getId();
        Map<String, Object> map = new HashMap<>();
        map.put("isbroadcasting",isBroadcasting);
        FirebaseFirestore.getInstance().collection(COLLECTION_LIVE)
                .document(uid)
                .update(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    static class AddBroad{
        String uid;
        String title;
        Long broadcast_type;
        long broadcast_id;
        String room_id;
        String rtcChannelName;

        public AddBroad() {}

        public AddBroad(String uid, String title, Long broadcast_type) {
            this.uid = uid;
            this.title = title;
            this.broadcast_type = broadcast_type;
        }

        public AddBroad(String uid, String title, long broadcast_type, long broadcast_id, String room_id, String rtcChannelName) {
            this.uid = uid;
            this.title = title;
            this.broadcast_type = broadcast_type;
            this.broadcast_id = broadcast_id;
            this.room_id = room_id;
            this.rtcChannelName = rtcChannelName;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Long getBroadcast_type() {
            return broadcast_type;
        }

        public void setBroadcast_type(Long broadcast_type) {
            this.broadcast_type = broadcast_type;
        }

        public long getBroadcast_id() {
            return broadcast_id;
        }

        public void setBroadcast_id(long broadcast_id) {
            this.broadcast_id = broadcast_id;
        }

        public String getRoom_id() {
            return room_id;
        }

        public void setRoom_id(String room_id) {
            this.room_id = room_id;
        }

        public String getRtcChannelName() {
            return rtcChannelName;
        }

        public void setRtcChannelName(String rtcChannelName) {
            this.rtcChannelName = rtcChannelName;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////

    public static void inviteUser(String remote_uid, Map<String,Object> map){
        loge("FireUtil invite", remote_uid);
        FirebaseFirestore.getInstance().collection(COLLECTION_LIVE)
                .document(remote_uid)
                .set(map,
                        SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public static void acceptingUpdate(String uid,  Map<String,Object> map){
        loge("FireUtil invite", uid);
        FirebaseFirestore.getInstance().collection(COLLECTION_LIVE)
                .document(uid)
                .set(map,
                        SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    ///trigger in on reject peer event for inviter
    ///trigger on reject click for invitee
    public static void rejectInvite(String uid){
        Map<String,Object> map =  new HashMap<>();
        map.put("pkstate", 0);

        FirebaseFirestore.getInstance().collection(COLLECTION_LIVE)
                .document(uid)
                .set(map, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }


}
