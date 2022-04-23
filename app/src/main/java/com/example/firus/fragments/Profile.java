package com.example.firus.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firus.R;
import com.google.android.material.chip.Chip;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.net.UnknownServiceException;

public class Profile extends Fragment {
    TextView signup,txt_forgotpassword,usernmae,signout;
    EditText editemail,editpassword,editcnfpassword;
    Chip chip_login;
    ProgressBar progressBar;
    LinearLayout lt_out;
    CardView usercard;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        chip_login = view.findViewById(R.id.chip_login);
        signup = view.findViewById(R.id.signup);
        editemail = view.findViewById(R.id.editTextTextEmailAddress);
        editpassword = view.findViewById(R.id.editTextTextPassword);
        editcnfpassword = view.findViewById(R.id.editTextcnfpassword);
        txt_forgotpassword = view.findViewById(R.id.txt_forgotpassword);
        progressBar = view.findViewById(R.id.progressBar);
        lt_out = view.findViewById(R.id.lt_out);
        usernmae = view.findViewById(R.id.username);

        usercard = view.findViewById(R.id.usercardview);
        usercard.setVisibility(View.GONE);

        if(ParseUser.getCurrentUser()!=null){
            lt_out.setVisibility(View.GONE);
            usercard.setVisibility(View.VISIBLE);
            usernmae.setText(ParseUser.getCurrentUser().getUsername());
        }
        signout = view.findViewById(R.id.signout);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                FancyToast.makeText(getActivity(),"Successfully Logged out",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();
                lt_out.setVisibility(View.VISIBLE);
                usercard.setVisibility(View.GONE);
            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if(signup.getText().equals("Already have an Account, LOG IN")){
                    chip_login.setText("Log in");
                    editcnfpassword.setVisibility(View.GONE);
                    signup.setText("Don't have an Account, SIGN UP");
                }else {
                    chip_login.setText("Create Account");
                    editcnfpassword.setVisibility(View.VISIBLE);
                    signup.setText("Already have an Account, LOG IN");
                }

            }
        });

        chip_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chip_login.getText().toString().equals("Log in")){
                    chip_login.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                    if(!editemail.getText().toString().isEmpty() && !editpassword.getText().toString().isEmpty()){
                        ParseUser.logInInBackground(editemail.getText().toString(), editpassword.getText().toString(), new LogInCallback() {
                            @Override
                            public void done(ParseUser user, ParseException e) {
                                if(user != null){
                                    progressBar.setVisibility(View.INVISIBLE);
                                    chip_login.setVisibility(View.VISIBLE);
                                    FancyToast.makeText(getActivity(),"Logged in Successfully",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();
                                    lt_out.setVisibility(View.GONE);
                                    usercard.setVisibility(View.VISIBLE);
                                }else {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    chip_login.setVisibility(View.VISIBLE);
                                    FancyToast.makeText(getActivity(),e.getMessage(),FancyToast.LENGTH_SHORT,FancyToast.WARNING,false).show();
                                }
                            }
                        });
                    }else {
                        progressBar.setVisibility(View.INVISIBLE);
                        chip_login.setVisibility(View.VISIBLE);
                        FancyToast.makeText(getActivity(),"Email and Password cannot be Empty",FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show();
                    }
                    
                }
                else {
                    chip_login.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                    if(!editemail.getText().toString().isEmpty() && !editpassword.getText().toString().isEmpty() && !editcnfpassword.getText().toString().isEmpty()){
                        if(editpassword.getText().toString().equals(editcnfpassword.getText().toString())){
                           ParseUser parseUser = new ParseUser();
                           parseUser.setUsername(editemail.getText().toString());
                           parseUser.setEmail(editemail.getText().toString());
                           parseUser.setPassword(editpassword.getText().toString());
                           parseUser.signUpInBackground(new SignUpCallback() {
                               @Override
                               public void done(ParseException e) {
                                   if(e == null){
                                       progressBar.setVisibility(View.INVISIBLE);
                                       chip_login.setVisibility(View.VISIBLE);
                                       FancyToast.makeText(getActivity(),"Account Created Successfully",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();
                                       lt_out.setVisibility(View.GONE);
                                       usercard.setVisibility(View.VISIBLE);
                                   }else {
                                       chip_login.setVisibility(View.VISIBLE);
                                       progressBar.setVisibility(View.INVISIBLE);
                                       FancyToast.makeText(getActivity(),e.getMessage(),FancyToast.LENGTH_SHORT,FancyToast.WARNING,false).show();
                                   }
                               }
                           });

                        }else {
                            chip_login.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.INVISIBLE);
                            FancyToast.makeText(getActivity(),"Password and Confirm Password Doesn't match",FancyToast.LENGTH_SHORT,FancyToast.WARNING,false).show();
                        }
                    }
                    else {
                        FancyToast.makeText(getActivity(),"Fields Cannot be Empty",FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show();
                    }
                }
            }
        });


        return view;
    }
}