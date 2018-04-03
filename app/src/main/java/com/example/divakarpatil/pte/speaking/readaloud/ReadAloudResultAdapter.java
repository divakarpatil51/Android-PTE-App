package com.example.divakarpatil.pte.speaking.readaloud;

import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.divakarpatil.pte.R;
import com.example.divakarpatil.pte.utils.ParagraphResult;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Result Adapter
 * Created by divakar.patil on 26-03-2018.
 */

public class ReadAloudResultAdapter extends RecyclerView.Adapter<ReadAloudResultAdapter.ViewHolder> {

    private ArrayList<ParagraphResult> paragraphResults;

    ReadAloudResultAdapter(ArrayList<ParagraphResult> paragraphResults) {
        this.paragraphResults = paragraphResults;
    }

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
     */
    @NonNull
    @Override
    public ReadAloudResultAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_data_row_item, parent, false);
        return new ViewHolder(v);
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     */
    @Override
    public void onBindViewHolder(@NonNull final ReadAloudResultAdapter.ViewHolder holder, int position) {
        final ParagraphResult result = paragraphResults.get(position);
        holder.getAccuracyPercentageTV().setText(String.format(Locale.getDefault(), "%.2f",
                result.getAccuracyPercentage()));

        holder.getParaNumberTV().setText(String.format(Locale.getDefault(), "%d",
                result.getParagraphNumber()));

        holder.getRatingBar().setRating(result.getParagraphRating().floatValue());

        holder.getShowTextButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.getContext());

                AlertDialog dialog = builder.setMessage(Html.fromHtml(result.getParagraphRecording(), Html.TO_HTML_PARAGRAPH_LINES_CONSECUTIVE))
                        .setTitle(holder.getContext().getString(R.string.accuracy) + ": " + result.getAccuracyPercentage())
                        .create();
                dialog.show();
            }
        });
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return paragraphResults.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView paraNumberTV, accuracyPercentageTV;
        private RatingBar ratingBar;
        private Button showTextButton;
        private Context context;

        ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            paraNumberTV = itemView.findViewById(R.id.paragraphNumberTextView);
            accuracyPercentageTV = itemView.findViewById(R.id.paragraphAccuracyValueTextView);
            ratingBar = itemView.findViewById(R.id.paragraphRatingBar);
            showTextButton = itemView.findViewById(R.id.showRecordTextButton);
        }

        RatingBar getRatingBar() {
            return ratingBar;
        }

        TextView getParaNumberTV() {
            return paraNumberTV;
        }

        TextView getAccuracyPercentageTV() {
            return accuracyPercentageTV;
        }

        Button getShowTextButton() {
            return showTextButton;
        }

        Context getContext() {
            return context;
        }

    }
}
