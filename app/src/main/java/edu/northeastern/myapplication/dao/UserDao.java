package edu.northeastern.myapplication.dao;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import edu.northeastern.myapplication.entity.User;

public class UserDao {
    private final String PATH_USERS = "users";
    private final String PATH_CITY = "city";
    private final String PATH_USER_TOKEN = "userToken";
    private DatabaseReference databaseReference;

    /**
     * No argument constructor for the class.
     */
    public UserDao() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    /**
     * Creates a user in the Firebase Realtime Database.
     *
     * @param userId the user id
     * @param user   the user object
     * @return a task
     */
    public Task<Void> create(String userId, User user) {
        Objects.requireNonNull(userId);
        Objects.requireNonNull(user);
        return databaseReference.child(PATH_USERS).child(userId).setValue(user);
    }

    public Task<Void> updateUser(String userId, User user) {
        Objects.requireNonNull(userId);
        Objects.requireNonNull(user);
        return databaseReference.child(PATH_USERS).child(userId).setValue(user);
    }

    /**
     * Updates the user's city information.
     *
     * @param userId   the user id
     * @param cityName the city name
     * @return a task
     */
    public Task<Void> updateCity(String userId, String cityName) {
        Objects.requireNonNull(userId);
        Objects.requireNonNull(cityName);
        return databaseReference.child(PATH_USERS).child(userId).child(PATH_CITY).setValue(cityName);
    }

    /**
     * Updates the user's token.
     *
     * @param userId    the user's Id
     * @param userToken the new user token
     * @return a task
     */
    public Task<Void> updateUserToken(String userId, String userToken) {
        Objects.requireNonNull(userId);
        Objects.requireNonNull(userToken);
        return databaseReference.child(PATH_USERS).child(userId).child(PATH_USER_TOKEN).setValue(userToken);
    }

    /**
     * Finds a user by Id.
     *
     * @param userId the user's id
     * @return the DataSnapshot that contains the user
     */
    public Task<DataSnapshot> findUserById(String userId) {
        Objects.requireNonNull(userId);
        return databaseReference.child(PATH_USERS).child(userId).get();
    }
}
