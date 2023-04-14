package edu.northeastern.myapplication.dao;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import edu.northeastern.myapplication.entity.Nanny;

public class NannyDao {
    private final String PATH_NANNIES = "nannies";
    private DatabaseReference databaseReference;

    /**
     * No argument constructor for the class.
     */
    public NannyDao() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    /**
     * Creates a nanny in the Firebase Realtime Database.
     *
     * @param userId the user id
     * @param nanny  the nanny object
     * @return a task
     */
    public Task<Void> create(String userId, Nanny nanny) {
        Objects.requireNonNull(userId);
        Objects.requireNonNull(nanny);
        return databaseReference.child(PATH_NANNIES).child(userId).setValue(nanny);
    }

    public Task<DataSnapshot> findNannyById(String userId) {
        Objects.requireNonNull(userId);
        return databaseReference.child(PATH_NANNIES).child(userId).get();
    }

    public Task<Void> update(String nannyId, Nanny nanny) {
        Objects.requireNonNull(nannyId);
        Objects.requireNonNull(nanny);
        return databaseReference.child(PATH_NANNIES).child(nannyId).setValue(nanny);
    }
}
