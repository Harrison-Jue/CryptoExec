package wit.cryptoexec.backend.database;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

import wit.cryptoexec.backend.api.callbacks.ApiDetailsHandler;
import wit.cryptoexec.backend.api.callbacks.ApiExchangesHandler;
import wit.cryptoexec.backend.api.callbacks.ApiKeyHandler;
import wit.cryptoexec.backend.api.callbacks.ApiSecretHandler;

/**
 * Created by jueh on 3/5/2018.
 */

public class ApiDetailsDatabase {
    private FirebaseDatabase database;
    private DatabaseReference reference;

    private String ERROR = "ERROR";

    public ApiDetailsDatabase() {
        database = FirebaseDatabase.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        String referenceString = "users/" + uid + "/apiDetails";
        reference = database.getReference(referenceString);
    }


    //Assumes 1 exchange per account (Stretch goal to store multiple of the same exchange...
    public boolean saveApiDetails(String exchangeService, String apiKey, String apiSecret) throws Throwable {
        final TaskCompletionSource<Boolean> tcs = new TaskCompletionSource<>();
        tcs.setResult(new Boolean(true));

        reference.child(exchangeService).child("apiKey").setValue(apiKey).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.v("SUCCESS", "Added API Key to database");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.v(ERROR, "Could not add API Key to database: " + e.getMessage());
                tcs.setResult(new Boolean(false));
            }
        });

        if(tcs.getTask().getResult().equals(new Boolean(false))) {
            return false;
        }

        reference.child(exchangeService).child("apiSecret").setValue(apiSecret).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.v("SUCCESS", "Added API Secret to database");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.v(ERROR, "Could not add API Secret to database: " + e.getMessage());
                tcs.setResult(new Boolean(false));
            }
        });

        if(tcs.getTask().getResult().equals(new Boolean(false))) {
            return false;
        } else {
            return true;
        }
    }

    public void deleteApiDetails(String exchangeService) {
        reference.child(exchangeService).removeValue();
    }

    public void getApiKey(final String exchangeService, final ApiKeyHandler callback) throws Throwable {
        reference.child(exchangeService).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                callback.onSuccess(dataSnapshot.child("apiKey").getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.v(ERROR, "Could not find API Key from Exchange Service \"" + exchangeService + "\": " + databaseError.getMessage());
            }
        });
    }

    public void getApiSecret(final String exchangeService, final ApiSecretHandler callback) throws Throwable {
        reference.child(exchangeService).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                callback.onSuccess(dataSnapshot.child("apiSecret").getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.v(ERROR, "Could not find API Secret from Exchange Service \"" + exchangeService + "\": " + databaseError.getMessage());
            }
        });
    }

    public void getApiDetails(final String exchangeService, final ApiDetailsHandler callback) throws Throwable {
        reference.child(exchangeService).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                callback.onSuccess(dataSnapshot.child("apiKey").getValue(String.class), dataSnapshot.child("apiSecret").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.v(ERROR, "Could not find API Key/Secret from Exchange Service \"" + exchangeService + "\": " + databaseError.getMessage());
            }
        });
    }

    public void getExchanges(final ApiExchangesHandler callback) throws Throwable {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> exchanges = new ArrayList<>();
                for(DataSnapshot child : dataSnapshot.getChildren()) {
                    exchanges.add(child.getKey());
                }
                callback.onSucess(exchanges);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.v(ERROR, "Could not find Exchanges for user \"" + FirebaseAuth.getInstance().getCurrentUser().getUid().toString() + "\": " + databaseError.getMessage());
            }
        });
    }
}