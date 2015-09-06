package com.intbridge.assisttheneedy;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;

@EActivity(R.layout.activity_main)
public class MainActivity extends Activity {

    @ViewById
    EditText phonenumber;
    @ViewById
    EditText address;
    @ViewById
    EditText amountneeded;
    @ViewById
    EditText purpose;
    @ViewById
    EditText yourname;
    @ViewById
    EditText youremailaddress;
    @ViewById
    EditText yourphonenum;

    @ViewById
    TextView submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @AfterViews
    void autocompleteFromLocalStorage(){
        ParseQuery query = ParseQuery.getQuery("LocalSubmission");
        query.fromLocalDatastore();
        ParseObject parseObject;
        try {
            parseObject = query.getFirst();
        } catch (ParseException e) {
            Log.e("autocomplete","ParseException");
            return;
        }
        Log.e("autocomplete", "h1");
        if(parseObject==null) return;
        Log.e("autocomplete", "h2");

        String userName = parseObject.getString("yourName");
        String userEmailAddress = parseObject.getString("yourEmailAddress");
        String userPhoneNumber = parseObject.getString("yourPhoneNumber");

        if(userName!=null) yourname.setText(userName);
        if(userEmailAddress!=null) youremailaddress.setText(userEmailAddress);
        if(userPhoneNumber!=null) yourphonenum.setText(userPhoneNumber);
    }

    @Click
    void submitClicked(){

        boolean isSubmitted = uploadSubmissionToParse();

        if(isSubmitted){
            updateLocalSubmission();
            Toast.makeText(getApplicationContext(), "Successfully Submitted ", Toast.LENGTH_LONG).show();
        }

    }

    private boolean uploadSubmissionToParse(){
        ParseObject submission;
        submission = new ParseObject("Submission");
        String phoneNumberString = phonenumber.getText().toString();
        String addressString = address.getText().toString();
        String amountNeededString = amountneeded.getText().toString();
        String purposeString = purpose.getText().toString();
        String yourNameString = yourname.getText().toString();
        String yourEmailAddressString = youremailaddress.getText().toString();
        String yourPhoneNumberString = yourphonenum.getText().toString();
//        if(isEmpty(phonenumber)){
//            Toast.makeText(getApplicationContext(), "Please Enter Phone Number", Toast.LENGTH_LONG).show();
//            return false;
//        }
        submission.put("phoneNumber", phoneNumberString);
        if(isEmpty(address)){
            Toast.makeText(getApplicationContext(), "Please Enter Address", Toast.LENGTH_LONG).show();
            return false;
        }
        submission.put("address", addressString);
//        if(isEmpty(amountneeded)){
//            Toast.makeText(getApplicationContext(), "Please Enter Amount Needed", Toast.LENGTH_LONG).show();
//            return false;
//        }
        submission.put("amountNeeded",amountNeededString);
//        if(isEmpty(purpose)){
//            Toast.makeText(getApplicationContext(), "Please Enter Purpose", Toast.LENGTH_LONG).show();
//            return false;
//        }
        submission.put("purpose",purposeString);
        if(isEmpty(yourname)){
            Toast.makeText(getApplicationContext(), "Please Enter Your Name", Toast.LENGTH_LONG).show();
            return false;
        }
        submission.put("yourName",yourNameString);
        if(isEmpty(youremailaddress)){
            Toast.makeText(getApplicationContext(), "Please Enter Your Email", Toast.LENGTH_LONG).show();
            return false;
        }
        submission.put("yourEmailAddress",yourEmailAddressString);
        if(isEmpty(yourphonenum)){
            Toast.makeText(getApplicationContext(), "Please Enter Your Phone Number", Toast.LENGTH_LONG).show();
            return false;
        }
        submission.put("yourPhoneNumber",yourPhoneNumberString);

        // if it is same as the last post
        ParseQuery query = ParseQuery.getQuery("LocalSubmission");
        query.fromLocalDatastore();
        ParseObject localStorage;
        try {
            localStorage = query.getFirst();
            if(localStorage==null) return true;
            Log.e("check", "afterTry");
            int count = 0;
            if(localStorage.get("phoneNumber").equals(phoneNumberString)) count++;
            if(localStorage.get("address").equals(addressString)) count++;
            if(localStorage.get("amountNeeded").equals(amountNeededString)) count++;
            if(localStorage.get("purpose").equals(purposeString)) count++;
            if(count==4) return false;
        } catch (ParseException e) {
            Log.e("check","ParseException");
        }

        submission.saveEventually();
        return true;
    }

    private void updateLocalSubmission(){
        String phoneNumberString = phonenumber.getText().toString();
        String addressString = address.getText().toString();
        String amountNeededString = amountneeded.getText().toString();
        String purposeString = purpose.getText().toString();
        String yourNameString = yourname.getText().toString();
        String yourEmailAddressString = youremailaddress.getText().toString();
        String yourPhoneNumberString = yourphonenum.getText().toString();
        // store to local
        ParseQuery query = ParseQuery.getQuery("LocalSubmission");
        query.fromLocalDatastore();
        ParseObject localStorage;
        try {
            localStorage = query.getFirst();
            localStorage.put("phoneNumber", phoneNumberString);
            localStorage.put("address", addressString);
            localStorage.put("amountNeeded",amountNeededString);
            localStorage.put("purpose",purposeString);
            localStorage.put("yourName",yourNameString);
            localStorage.put("yourEmailAddress",yourEmailAddressString);
            localStorage.put("yourPhoneNumber",yourPhoneNumberString);
        } catch (ParseException e) {
            Log.e("update","ParseException");
            localStorage = new ParseObject("LocalSubmission");
            localStorage.put("phoneNumber", phoneNumberString);
            localStorage.put("address", addressString);
            localStorage.put("amountNeeded",amountNeededString);
            localStorage.put("purpose",purposeString);
            localStorage.put("yourName",yourNameString);
            localStorage.put("yourEmailAddress", yourEmailAddressString);
            localStorage.put("yourPhoneNumber",yourPhoneNumberString);
            localStorage.pinInBackground();
        }
        Log.e("update","afterTry");


    }

    //If function return false means edittext is not empty and return true means edittext is empty...
    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

}
