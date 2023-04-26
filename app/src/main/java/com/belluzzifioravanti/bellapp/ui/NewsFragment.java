package com.belluzzifioravanti.bellapp.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.belluzzifioravanti.bellapp.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.transition.MaterialFadeThrough;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class NewsFragment extends Fragment {
    private MaterialFadeThrough materialFadeThrough = new MaterialFadeThrough();
    private TextInputLayout nomeNews;
    private TextInputLayout cognomeNews;
    private TextInputLayout textNews;
    private TextInputEditText nome;
    private TextInputEditText cognome;
    private TextInputEditText news;
    private MaterialButton materialButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        materialFadeThrough.setDuration(500);
        setEnterTransition(materialFadeThrough);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nomeNews = getActivity().findViewById(R.id.nomeNews);
        cognomeNews = getActivity().findViewById(R.id.cognomeNews);
        textNews = getActivity().findViewById(R.id.textNews);
        materialButton = getActivity().findViewById(R.id.button_news);
        nome = getActivity().findViewById(R.id.nome);
        cognome = getActivity().findViewById(R.id.cognome);
        news = getActivity().findViewById(R.id.news);
        button();
        checkInput();
    }

    public void button() {
        materialButton.setOnClickListener(i -> {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycby2iUV8BcIIn2t7oC2FtKwwz9XZgKiObh9o42qRmFMJL2JYFF5fI_UaiKSLdbAKw0-QaQ/exec",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            nomeNews.clearFocus();
                            cognomeNews.clearFocus();
                            textNews.clearFocus();
                            nome.setText("");
                            cognome.setText("");
                            news.setText("");
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> parmas = new HashMap<>();
                    parmas.put("name", Objects.requireNonNull(nome.getText()).toString());
                    parmas.put("surname", Objects.requireNonNull(cognome.getText()).toString());
                    parmas.put("amount", Objects.requireNonNull(news.getText()).toString());
                    return parmas;
                }
            };
            RetryPolicy retryPolicy = new DefaultRetryPolicy(5000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            stringRequest.setRetryPolicy(retryPolicy);
            RequestQueue queue = Volley.newRequestQueue(requireContext());
            queue.add(stringRequest);
        });
    }

    private void checkInput() {
        TextWatcher tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String name = nome.getText().toString().trim();
                String surname = cognome.getText().toString().trim();
                String infos = news.getText().toString().trim();
                boolean enabled = (!name.isEmpty() && !surname.isEmpty() && !infos.isEmpty());
                materialButton.setEnabled(enabled);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        nome.addTextChangedListener(tw);
        cognome.addTextChangedListener(tw);
        news.addTextChangedListener(tw);
    }

    public boolean onKeyDown() {
        boolean prova = false;
        if (nomeNews.hasFocus()) {
            nomeNews.clearFocus();
            prova = true;
        } else {
            if (cognomeNews.hasFocus()) {
                cognomeNews.clearFocus();
                prova = true;
            } else {
                if (textNews.hasFocus()) {
                    textNews.clearFocus();
                    prova = true;
                }
            }
        }
        return prova;
    }
}