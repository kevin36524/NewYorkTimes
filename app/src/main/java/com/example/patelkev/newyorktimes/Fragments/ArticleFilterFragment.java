package com.example.patelkev.newyorktimes.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.patelkev.newyorktimes.Models.FilterModel;
import com.example.patelkev.newyorktimes.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArticleFilterFragment extends DialogFragment {

    public interface ArticleFilterFragmentDelegate {
        public void setFilterPref(FilterModel filterPref);
    }

    EditText etDate;
    CheckBox cbArts;
    CheckBox cbForeign;
    CheckBox cbSports;

    RadioButton rbOldest;
    RadioButton rbNewest;
    RadioGroup rbSort;

    Button saveButton;
    Button resetButton;

    FilterModel filterPref;

    public ArticleFilterFragmentDelegate delegate;

    public ArticleFilterFragment() {
        // Required empty public constructor
    }

    public static ArticleFilterFragment newInstance(FilterModel filterPreferences, ArticleFilterFragmentDelegate delegate) {
        ArticleFilterFragment frag = new ArticleFilterFragment();
        frag.delegate = delegate;
        frag.filterPref = filterPreferences;
        Bundle args = new Bundle();
        args.putSerializable("filterPref", filterPreferences);
        frag.setArguments(args);
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_article_filter, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etDate = (EditText) view.findViewById(R.id.etDate);

        cbArts = (CheckBox) view.findViewById(R.id.cbarts);
        cbForeign = (CheckBox)view.findViewById(R.id.cbforeign);
        cbSports = (CheckBox)view.findViewById(R.id.cbsports);

        rbNewest = (RadioButton) view.findViewById(R.id.rbNewest);
        rbOldest = (RadioButton) view.findViewById(R.id.rbOldest);

        cbArts.setChecked(filterPref.getArts());
        cbSports.setChecked(filterPref.getSports());
        cbForeign.setChecked(filterPref.getForeign());

        rbNewest.setActivated(filterPref.getNewest());
        rbOldest.setActivated(filterPref.getOldest());

        saveButton = (Button) view.findViewById(R.id.btnSave);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilterModel filterPref = new FilterModel();

                filterPref.setSports(cbSports.isChecked());
                filterPref.setArts(cbArts.isChecked());
                filterPref.setForeign(cbArts.isChecked());

                filterPref.setNewest(rbNewest.isActivated());
                filterPref.setOldest(rbOldest.isActivated());

                delegate.setFilterPref(filterPref);
            }
        });

    }
}
