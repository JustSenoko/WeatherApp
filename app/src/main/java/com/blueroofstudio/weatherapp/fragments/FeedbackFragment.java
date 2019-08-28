package com.blueroofstudio.weatherapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.blueroofstudio.weatherapp.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;


public class FeedbackFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);
        final TextInputEditText inputEditText = view.findViewById(R.id.feedback_text);
        Button btnSend = view.findViewById(R.id.button_send_feedback);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String feedback = Objects.requireNonNull(inputEditText.getText()).toString();
                if (feedback.isEmpty()) {
                    inputEditText.setError(getResources().getString(R.string.err_write_feedback));
                    return;
                }
                inputEditText.setError(null);
                inputEditText.setText("");
                Toast.makeText(getContext(), getResources().getString(R.string.feedback_sent), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
