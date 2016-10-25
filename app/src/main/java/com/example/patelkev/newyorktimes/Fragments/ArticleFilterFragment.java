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
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.patelkev.newyorktimes.Models.FilterModel;
import com.example.patelkev.newyorktimes.R;

import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArticleFilterFragment extends DialogFragment {

    public interface ArticleFilterFragmentDelegate {
        public void setFilterPref(FilterModel filterPref);
    }

    TextView tvDate;
    DatePicker datePicker;
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
    public void onViewCreated(View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvDate = (TextView) view.findViewById(R.id.tvDate);
        datePicker = (DatePicker) view.findViewById(R.id.datePicker);

        cbArts = (CheckBox) view.findViewById(R.id.cbarts);
        cbForeign = (CheckBox)view.findViewById(R.id.cbforeign);
        cbSports = (CheckBox)view.findViewById(R.id.cbsports);

        rbNewest = (RadioButton) view.findViewById(R.id.rbNewest);
        rbOldest = (RadioButton) view.findViewById(R.id.rbOldest);

        cbArts.setChecked(filterPref.getArts());
        cbSports.setChecked(filterPref.getSports());
        cbForeign.setChecked(filterPref.getForeign());

        rbNewest.setChecked(filterPref.getNewest());
        rbOldest.setChecked(filterPref.getOldest());

        saveButton = (Button) view.findViewById(R.id.btnSave);
        resetButton = (Button) view.findViewById(R.id.btnReset);

        tvDate.setText(filterPref.getDate(true));
        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.setVisibility(View.VISIBLE);
                resetButton.setVisibility(View.INVISIBLE);
                saveButton.setVisibility(View.INVISIBLE);
                datePicker.bringToFront();
            }
        });

        Date beginDate = filterPref.getBeginDate();
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(beginDate.getTime());

        datePicker.init(calendar.get(calendar.YEAR), calendar.get(calendar.MONTH), calendar.get(calendar.DATE), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(year, monthOfYear, dayOfMonth);
                filterPref.setBeginDate(calendar.getTime());
                tvDate.setText(filterPref.getDate(true));
                datePicker.setVisibility(View.INVISIBLE);
                saveButton.setVisibility(View.VISIBLE);
                resetButton.setVisibility(View.VISIBLE);
            }
        });

        final ArticleFilterFragment fragmentReference = this;
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterPref.setSports(cbSports.isChecked());
                filterPref.setArts(cbArts.isChecked());
                filterPref.setForeign(cbArts.isChecked());

                filterPref.setNewest(rbNewest.isChecked());
                filterPref.setOldest(rbOldest.isChecked());

                delegate.setFilterPref(filterPref);
                fragmentReference.dismiss();
            }
        });

    }
}
