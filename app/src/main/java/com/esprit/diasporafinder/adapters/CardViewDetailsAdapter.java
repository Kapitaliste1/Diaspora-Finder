package com.esprit.diasporafinder.adapters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.esprit.diasporafinder.R;
import com.esprit.diasporafinder.fragments.DetailsFragment;
import com.esprit.diasporafinder.models.Comments;
import com.esprit.diasporafinder.util.DownloadImageTask;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Bourgeois on 21/03/2016.
 */
public class CardViewDetailsAdapter extends RecyclerView.Adapter<CardViewDetailsAdapter.ViewHolder> {
    private List<Comments> mDataset = new ArrayList<>();
    private Context mcontext;
    private Button updateComment,deleteComment,saveChangedComment;

    private boolean editeOnce = false;
    private String idObject;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View mView;

        public ViewHolder(View v) {
            super(v);
            mView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CardViewDetailsAdapter(Context context,List<Comments> CommentsBackendlessCollection,String idObject2) {
        mDataset = CommentsBackendlessCollection;
        mcontext = context;
        idObject = idObject2;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CardViewDetailsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_details_post, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);





        return vh;
    }





    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        // holder.mView.setText(mDataset[position]);
        final ImageView profilCommentator;
        final EditText contentCommentEdit;
        final TextView contentCommentator2,timeCommented,nameCommentator;

        contentCommentator2 = (TextView) holder.mView.findViewById(R.id.contentCommentator);
         timeCommented = (TextView) holder.mView.findViewById(R.id.timeCommented);
          nameCommentator = (TextView) holder.mView.findViewById(R.id.nameCommentator);
          profilCommentator = (ImageView) holder.mView.findViewById(R.id.profilCommentator);
        deleteComment = (Button) holder.mView.findViewById(R.id.deleteComment);
        updateComment = (Button) holder.mView.findViewById(R.id.updateComment);
        saveChangedComment = (Button) holder.mView.findViewById(R.id.saveChangedComment);
        saveChangedComment.setVisibility(View.GONE);

        contentCommentEdit = (EditText) holder.mView.findViewById(R.id.contentCommentEdit);
        contentCommentEdit.setVisibility(View.GONE);




        deleteComment.setVisibility(View.GONE);
        updateComment.setVisibility(View.GONE);

        if(mDataset.get(position).getOwnerId().equalsIgnoreCase(Backendless.UserService.CurrentUser().getObjectId())){
            deleteComment.setVisibility(View.VISIBLE);
            updateComment.setVisibility(View.VISIBLE);
        }


        saveChangedComment.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Comments cm = new Comments();
                cm.setContentComment(contentCommentEdit.getText().toString());
                cm.setOwnerId(Backendless.UserService.CurrentUser().getObjectId());
                cm.setObjectId(mDataset.get(position).getObjectId());
                cm.setPostID(mDataset.get(position).getPostID());

                if(editeOnce == false){
                    editeOnce =true;

                    Backendless.Persistence.save(cm, new AsyncCallback<Comments>() {
                        public void handleResponse(Comments response) {
                            editeOnce =false;
                            saveChangedComment.setVisibility(View.GONE);
                            contentCommentEdit.setVisibility(View.GONE);
                            contentCommentator2.setVisibility(View.VISIBLE);
                            contentCommentator2.setText(response.getContentComment());
                             deleteComment.setVisibility(View.VISIBLE);

                            FragmentManager fm = ((FragmentActivity)mcontext).getSupportFragmentManager();
                            FragmentTransaction transaction = fm.beginTransaction();
                            DetailsFragment startFragment = new DetailsFragment();
                            startFragment.idObject = idObject;
                            transaction.replace(R.id.linearLayoutBase, startFragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }

                        public void handleFault(BackendlessFault fault) {
                            // an error has occurred, the error code can be retrieved with fault.getCode()
                            Toast.makeText(holder.mView.getContext(), "Internet connexion is needed!", Toast.LENGTH_LONG).show();

                        }
                    });
                }


            }
        });

        updateComment.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                saveChangedComment.setVisibility(View.VISIBLE);
                contentCommentEdit.setVisibility(View.VISIBLE);
                contentCommentator2.setVisibility(View.GONE);
                contentCommentEdit.setText(mDataset.get(position).getContentComment());
                deleteComment.setVisibility(View.GONE);

            }
        });
        deleteComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mDataset.get(position).getOwnerId().equalsIgnoreCase(Backendless.UserService.CurrentUser().getObjectId())){

                    Backendless.Persistence.of( Comments.class ).remove( mDataset.get(position), new AsyncCallback<Long>()
                    {
                        public void handleResponse( Long response )
                        {
                            FragmentManager fm = ((FragmentActivity)mcontext).getSupportFragmentManager();
                            FragmentTransaction transaction = fm.beginTransaction();
                            DetailsFragment startFragment = new DetailsFragment();
                            startFragment.idObject = idObject;
                            transaction.replace(R.id.linearLayoutBase, startFragment);
                            transaction.addToBackStack(null);
                            transaction.commit();

                        }
                        public void handleFault( BackendlessFault fault )
                        {
                            Toast.makeText(holder.mView.getContext(), "Internet connexion is needed!", Toast.LENGTH_LONG).show();
                        }
                    } );
                }
            }
        });


        contentCommentator2.setText(mDataset.get(position).getContentComment());



        long diff = new Date().getTime() - mDataset.get(position).getCreated().getTime();
        long diffMinutes = diff / (60 * 1000);
        long diffHours = diff / (60 * 60 * 1000);
        int diffInDays = (int) ((diff) / (1000 * 60 * 60 * 24));

        if (diffInDays > 1) {
            System.err.println("Difference in number of days (2) : " + diffInDays);
            timeCommented.setText(diffInDays+" Days ago");

        }
        else  if (diffInDays <= 1) {

            if ((int)diffHours < 24) {

                if((int)diffMinutes < 60){
                    timeCommented.setText((int) diffMinutes+" Minutes ago");

                }else if (((int)diffMinutes >= 60)) {
                    timeCommented.setText(diffHours+" Hours ago");

                }



            } else if ((diffHours == 24) && (diffMinutes >= 1)) {
                timeCommented.setText(diffInDays+" Days ago");

            }

        }


        Backendless.Data.of( BackendlessUser.class ).find(new AsyncCallback<BackendlessCollection<BackendlessUser>>() {
            @Override
            public void handleResponse(BackendlessCollection<BackendlessUser> users) {
                Iterator<BackendlessUser> userIterator = users.getCurrentPage().iterator();

                while (userIterator.hasNext()) {
                    BackendlessUser user = userIterator.next();

                    if (user.getObjectId().equalsIgnoreCase(mDataset.get(position).getOwnerId())) {
                        new DownloadImageTask(profilCommentator).execute("" + user.getProperty("picture"));
                        nameCommentator.setText("" + user.getProperty("name"));
                    }
                }
            }

            @Override
            public void handleFault(BackendlessFault backendlessFault) {
                Toast.makeText(holder.mView.getContext(), "Internet connexion is needed!", Toast.LENGTH_LONG).show();
            }
        });












    }



    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
