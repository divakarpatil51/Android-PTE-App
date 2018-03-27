package com.example.divakarpatil.pte.speaking;

import android.content.Context;
import android.net.Uri;
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
    //    protected LayoutManagerType mCurrentLayoutManagerType;
    protected RecyclerView mRecyclerView;
    protected RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<ParagraphResult> paragraphResults;
    private OnFragmentInteractionListener mListener;
    private ReadAloudResultAdapter readAloudResultAdapter;

    public RecyclerViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RecyclerViewFragment.
     */
    // TODO: Rename and change types and number of parameters
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_recycler_view, container, false);

        mLayoutManager = new LinearLayoutManager(getActivity());
        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        readAloudResultAdapter = new ReadAloudResultAdapter(paragraphResults);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(readAloudResultAdapter);
        return root;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
