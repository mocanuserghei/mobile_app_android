package mobile.edu.finalpj.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.concurrent.Callable;

import mobile.edu.finalpj.R;

public class FacebookLoginExample extends AppCompatActivity {

    private CallbackManager facebookCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        facebookCallbackManager = CallbackManager.Factory.create();

        //treat the case then he is already logged in
        if (AccessToken.getCurrentAccessToken() != null){
            handleSignInResult(null);
            return;
        }

        setContentView(R.layout.activity_oauth);

        LoginButton facebookSignInButton = (LoginButton) findViewById(R.id.login_button);
        facebookSignInButton.registerCallback(facebookCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleSignInResult(new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        LoginManager.getInstance().logOut();
                        return null;
                    }
                });
            }

            @Override
            public void onCancel() {
//                do nothing
            }

            @Override
            public void onError(FacebookException error) {
//                doNothing
            }
        });

    }

    void handleSignInResult(Callable<Void> callable) {
        Intent intent = new Intent(this, MovieActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        facebookCallbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
