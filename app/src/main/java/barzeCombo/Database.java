package barzeCombo;

import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Database {
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    public Database() {
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();
    }

    public Task<Boolean> signIn(final String username, final String password) {
        final TaskCompletionSource<Boolean> tcs = new TaskCompletionSource<>();
        mDatabaseReference.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.getValue(User.class).getUsername().equals(username) &&
                            ds.getValue(User.class).getPassword().equals(password)) {
                        if(tcs.getTask().isComplete()){
                            return;
                        }else{
                            tcs.setResult(true);
                            return;

                        }

                    }
                }
                tcs.setResult(false);
                return;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                tcs.setResult(false);
            }
        });
        return tcs.getTask();
    }

    public boolean signUp(HashMap<String, Object> userInfo) {
        try {
            DatabaseReference newUser = mDatabaseReference.child("Users").push();
            newUser.setValue(userInfo);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean addBar(HashMap<String, Object> barInfo) {
        try {
            DatabaseReference newBar = mDatabaseReference.child("Bars").push();
            newBar.setValue(barInfo);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Task<String> getUserStatus(final String username) {
        final TaskCompletionSource<String> tcs = new TaskCompletionSource<>();
        mDatabaseReference.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.getValue(User.class).getUsername().equals(username)) {
                        tcs.setResult(ds.getValue(User.class).getStatus());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                tcs.setResult(null);
            }
        });
        return tcs.getTask();
    }

    public Task<Boolean> setUserStatus(String username, final String status) {
        final TaskCompletionSource<Boolean> tcs = new TaskCompletionSource<>();
        Query query = mDatabaseReference.child("Users").orderByChild("username").equalTo(username);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot nodeDataSnapshot = dataSnapshot.getChildren().iterator().next();
                String key = nodeDataSnapshot.getKey(); // this key is id of entry
                String path = "/" + dataSnapshot.getKey() + "/" + key;
                HashMap<String, Object> result = new HashMap<>();
                result.put("status", status);
                mDatabaseReference.child(path).updateChildren(result);
                tcs.setResult(true);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("Database", ">>> Error:" + "find onCancelled:" + databaseError);
                tcs.setResult(false);

            }
        });

        return tcs.getTask();
    }

    public Task<Integer> getUserPoints(final String username) {
        final TaskCompletionSource<Integer> tcs = new TaskCompletionSource<>();
        mDatabaseReference.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.getValue(User.class).getUsername().equals(username)) {
                        tcs.setResult(ds.getValue(User.class).getPoints());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                tcs.setResult(null);
            }
        });
        return tcs.getTask();
    }

    public Task<Boolean> addUserPoints(final String username, final int points) {
        Task<Integer> pointsTask = getUserPoints(username);
        final TaskCompletionSource<Boolean> tcs = new TaskCompletionSource<>();
        pointsTask.addOnCompleteListener(new OnCompleteListener<Integer>() {
            @Override
            public void onComplete(@NonNull Task<Integer> task) {
                final int total = points + task.getResult();
                Query query = mDatabaseReference.child("Users").orderByChild("username").equalTo(username);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        DataSnapshot nodeDataSnapshot = dataSnapshot.getChildren().iterator().next();
                        String key = nodeDataSnapshot.getKey();
                        String path = "/" + dataSnapshot.getKey() + "/" + key;
                        HashMap<String, Object> result = new HashMap<>();
                        result.put("points", total);
                        mDatabaseReference.child(path).updateChildren(result);
                        tcs.setResult(true);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.i("Database", ">>> Error:" + "find onCancelled:" + databaseError);
                        tcs.setResult(false);

                    }
                });
            }
        });
        return tcs.getTask();
    }

    public Task<String> ownsBar(final String username) {
        final TaskCompletionSource<String> tcs = new TaskCompletionSource<>();
        mDatabaseReference.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.getValue(User.class).getUsername() == null) {
                        tcs.setResult(null);
                    } else if(ds.getValue(User.class).getUsername().equals(username)) {
                        // null if user does not own bar
                        if(tcs.getTask().isComplete()){
                            return;
                        }else{
                            tcs.setResult(ds.getValue(User.class).getBar());
                        }

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // null if error
                tcs.setResult(null);
            }
        });
        return tcs.getTask();
    }

    public Task<Integer> getWaitTime(final String bar) {
        final TaskCompletionSource<Integer> tcs = new TaskCompletionSource<>();
        mDatabaseReference.child("Bars").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.getValue(Bar.class).getName().equals(bar)) {
                        if(tcs.getTask().isComplete()){
                            return;
                        }else{
                            tcs.setResult(ds.getValue(Bar.class).getWaitTime());
                        }

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                tcs.setResult(null);
            }
        });
        return tcs.getTask();
    }

    public Task<Boolean> setWaitTime(String bar, final int newWaitTime) {
        final TaskCompletionSource<Boolean> tcs = new TaskCompletionSource<>();
        Query query = mDatabaseReference.child("Bars").orderByChild("name").equalTo(bar);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot nodeDataSnapshot = dataSnapshot.getChildren().iterator().next();
                String key = nodeDataSnapshot.getKey(); // this key is id of entry
                String path = "/" + dataSnapshot.getKey() + "/" + key;
                HashMap<String, Object> result = new HashMap<>();
                result.put("waitTime", newWaitTime);
                mDatabaseReference.child(path).updateChildren(result);
                tcs.setResult(true);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("Database", ">>> Error:" + "find onCancelled:" + databaseError);
                tcs.setResult(false);

            }
        });
        return tcs.getTask();
    }

    public Task<Integer> getLowCover(final String bar) {
        final TaskCompletionSource<Integer> tcs = new TaskCompletionSource<>();
        mDatabaseReference.child("Bars").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.getValue(Bar.class).getName().equals(bar)) {
                        if(tcs.getTask().isComplete()){
                            return;
                        }else{
                            tcs.setResult(ds.getValue(Bar.class).getLowCover());
                        }

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                tcs.setResult(null);
            }
        });
        return tcs.getTask();
    }

    public Task<Boolean> setLowCover(String bar, final int lowCover) {
        final TaskCompletionSource<Boolean> tcs = new TaskCompletionSource<>();
        Query query = mDatabaseReference.child("Bars").orderByChild("name").equalTo(bar);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot nodeDataSnapshot = dataSnapshot.getChildren().iterator().next();
                String key = nodeDataSnapshot.getKey();
                String path = "/" + dataSnapshot.getKey() + "/" + key;
                HashMap<String, Object> result = new HashMap<>();
                result.put("lowCover", lowCover);
                mDatabaseReference.child(path).updateChildren(result);
                tcs.setResult(true);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("Database", ">>> Error:" + "find onCancelled:" + databaseError);
                tcs.setResult(false);

            }
        });
        return tcs.getTask();
    }

    public Task<Integer> getHighCover(final String bar) {
        final TaskCompletionSource<Integer> tcs = new TaskCompletionSource<>();
        mDatabaseReference.child("Bars").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.getValue(Bar.class).getName().equals(bar)) {
                        if(tcs.getTask().isComplete()){
                            return;
                        }else{
                            tcs.setResult(ds.getValue(Bar.class).getHighCover());
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                tcs.setResult(null);
            }
        });
        return tcs.getTask();
    }

    public Task<Boolean> setHighCover(String bar, final int highCover) {
        final TaskCompletionSource<Boolean> tcs = new TaskCompletionSource<>();
        Query query = mDatabaseReference.child("Bars").orderByChild("name").equalTo(bar);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot nodeDataSnapshot = dataSnapshot.getChildren().iterator().next();
                String key = nodeDataSnapshot.getKey();
                String path = "/" + dataSnapshot.getKey() + "/" + key;
                HashMap<String, Object> result = new HashMap<>();
                result.put("highCover", highCover);
                mDatabaseReference.child(path).updateChildren(result);
                tcs.setResult(true);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("Database", ">>> Error:" + "find onCancelled:" + databaseError);
                tcs.setResult(false);
            }
        });
        return tcs.getTask();
    }

    public Task<List<Float>> getBarLocation(final String bar) {
        final TaskCompletionSource<List<Float>> tcs = new TaskCompletionSource<>();
        if(tcs.getTask().isComplete()){
            return tcs.getTask();
        }
        mDatabaseReference.child("Bars").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.getValue(Bar.class).getName().equals(bar)) {
                        tcs.setResult(ds.getValue(Bar.class).getLocation());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                tcs.setResult(null);
            }
        });
        return tcs.getTask();
    }
    public Task<List<Bar>> getAllBars() {
        final TaskCompletionSource<List<Bar>> tcs = new TaskCompletionSource<>();

        mDatabaseReference.child("Bars").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Bar> barList = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    barList.add(ds.getValue(Bar.class));
                }
                if(tcs.getTask().isComplete()){
                    return ;
                }else{
                    tcs.setResult(barList);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                tcs.setResult(null);
            }
        });
        return tcs.getTask();
    }
    public Task<List<User>> getAllUsers() {
        final TaskCompletionSource<List<User>> tcs = new TaskCompletionSource<>();
        mDatabaseReference.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<User> userList = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    userList.add(ds.getValue(User.class));
                }
                tcs.setResult(userList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                tcs.setResult(null);
            }
        });
        return tcs.getTask();
    }
}