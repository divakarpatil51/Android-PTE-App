package com.example.divakarpatil.pte.speaking.readaloud;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.divakarpatil.pte.R;
import com.example.divakarpatil.pte.utils.ParagraphResult;

import java.util.ArrayList;

/**
 * Recycler view fragment for Result View
 */
public class RecyclerViewFragment extends Fragment {

    private static final String PARAGRAPH_RESULT = "Paragraphs_Result";
    protected RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<ParagraphResult> paragraphResults;

    public RecyclerViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RecyclerViewFragment.
     */
    public static RecyclerViewFragment newInstance(ArrayList<ParagraphResult> paragraphResult) {
        RecyclerViewFragment fragment = new RecyclerViewFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(PARAGRAPH_RESULT, paragraphResult);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            paragraphResults = getArguments().getParcelableArrayList(PARAGRAPH_RESULT);
        }
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_recycler_view, container, false);

        mLayoutManager = new LinearLayoutManager(getActivity());
        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        ReadAloudResultAdapter readAloudResultAdapter = new ReadAloudResultAdapter(paragraphResults);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(readAloudResultAdapter);
        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
