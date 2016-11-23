package mobile.edu.finalpj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MailActivity extends AppCompatActivity {

    private EditText subjectView;
    private EditText sendToView;
    private EditText msgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mail_activity);

        msgView = (EditText) findViewById(R.id.msgText);
        sendToView = (EditText) findViewById(R.id.sendToText);
        subjectView = (EditText) findViewById(R.id.subjectText);

    }

    public void skipToNextModal(View view) {
        Log.v("test", "tes");
        Intent intent = new Intent(this, MovieActivity.class);
        startActivity(intent);
    }

    public void openMailModal(View view) {
        //reset validation errors
        sendToView.setError(null);

        boolean cancel = false;

        String subject = subjectView.getText().toString();
        String sendTo = sendToView.getText().toString();
        String msg = msgView.getText().toString();

        //validate for good email
        if (TextUtils.isEmpty(sendTo)) {
            sendToView.setError(getString(R.string.error_field_required));
            cancel = true;
        }
        if (validEmail(sendTo)) {
            sendToView.setError(getString(R.string.error_invalid_email));
            cancel = true;
        }

        //valid data can proceed request
        if (!cancel) {
            Intent mail = new Intent(Intent.ACTION_SEND);
            mail.putExtra(Intent.EXTRA_EMAIL, new String[]{sendTo});
            mail.putExtra(Intent.EXTRA_SUBJECT, subject);
            mail.putExtra(Intent.EXTRA_TEXT, msg);
            mail.setType("message/rfc822");
            startActivity(Intent.createChooser(mail, "Sending mail..."));
        }
    }

    private boolean validEmail(String sendTo) {
        //here goes some other validation logic
        return !sendTo.contains("@");
    }


}
