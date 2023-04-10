package edu.northeastern.myapplication.dao;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.Objects;

import edu.northeastern.myapplication.entity.Comment;
import edu.northeastern.myapplication.entity.Tip;

public class CommentsDao {
    private final String PATH_TIPS = "comments";
    private DatabaseReference databaseReference;

    /**
     * No argument constructor for the class.
     */
    public CommentsDao() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    /**
     * Creates a tip in the Firebase Realtime Database.
     *
     * @param tipId the tip id
     * @param commentList    the tip object
     * @return a task
     */
    public Task<Void> createComments(String tipId, List<Comment> commentList) {
        Objects.requireNonNull(tipId);
        Objects.requireNonNull(commentList);
        return databaseReference.child(PATH_TIPS).child(tipId).setValue(commentList);
    }

    public Task<Void> updateComments(String tipId, List<Comment> commentList) {
        Objects.requireNonNull(tipId);
        Objects.requireNonNull(commentList);
        return databaseReference.child(PATH_TIPS).child(tipId).setValue(commentList);
    }
}
