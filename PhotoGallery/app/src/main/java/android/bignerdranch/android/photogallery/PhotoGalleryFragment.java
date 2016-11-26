package android.bignerdranch.android.photogallery;

import android.bignerdranch.android.photogallery.model.GalleryItem;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yoon on 2016. 11. 26..
 */
public class PhotoGalleryFragment extends Fragment {

    private static final String TAG = PhotoGalleryFragment.class.getSimpleName();

    private RecyclerView mPhotoRecyclerView;
    private List<GalleryItem> mGalleryItems;

    private int lastFetchedPage = 1;

    public static PhotoGalleryFragment newInstance() {

        Bundle args = new Bundle();

        PhotoGalleryFragment fragment = new PhotoGalleryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mGalleryItems = new ArrayList<>();
        new FetchItemsTask().execute(lastFetchedPage);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_photo_gallery, container, false);
        mPhotoRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_photo_gallery_recycler_view);
        mPhotoRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        setupAdapter();
        mPhotoRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastPosition =  ((PhotoAdapter) mPhotoRecyclerView.getAdapter()).getLastPosition();
                int loadBufferPosition = 1;
                if (lastPosition >= mPhotoRecyclerView.getAdapter().getItemCount()
                        - ((GridLayoutManager) mPhotoRecyclerView.getLayoutManager()).getSpanCount()
                        - loadBufferPosition) {
                    new FetchItemsTask().execute(++lastPosition);
                }
            }
        });



        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void setupAdapter() {
        if (isAdded()) {
            mPhotoRecyclerView.setAdapter(new PhotoAdapter(mGalleryItems));
        }
    }

    private class PhotoAdapter extends RecyclerView.Adapter<PhotoHolder> {

        private List<GalleryItem> mGalleryItems;
        private int lastPosition;

        public int getLastPosition() {
            return lastPosition;
        }

        public PhotoAdapter(List<GalleryItem> galleryItems) {
            mGalleryItems = galleryItems;
        }

        @Override
        public PhotoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            TextView textView = new TextView(getActivity());
            return new PhotoHolder(textView);
        }

        @Override
        public void onBindViewHolder(PhotoHolder holder, int position) {
            GalleryItem galleryItem = mGalleryItems.get(position);
            holder.bindGalleryItem(galleryItem);
            lastPosition = position;
            Log.i(TAG, "last position is " + String.valueOf(lastPosition));
        }

        @Override
        public int getItemCount() {
            return mGalleryItems.size();
        }
    }

    private class PhotoHolder extends RecyclerView.ViewHolder {

        private GalleryItem mGalleryItem;
        private TextView mTitleTextView;

        public PhotoHolder(View itemView) {
            super(itemView);

            mTitleTextView = (TextView) itemView;
        }

        public void bindGalleryItem(GalleryItem item) {
            mGalleryItem = item;
            mTitleTextView.setText(mGalleryItem.toString());
        }
    }

    private class FetchItemsTask extends AsyncTask<Integer, Void, List<GalleryItem>> {
        @Override
        protected List<GalleryItem> doInBackground(Integer... integers) {
//            return new FlickrFetchr().fetchItems(getString(R.string.flickr_api_key));
            return new FlickrFetchr().fetchItems(getString(R.string.flickr_api_key), integers[0]);
        }

        //        @Override
//        protected List<GalleryItem> doInBackground(Integer... params) {
//            new FlickrFetchr().fetchItems(getString(R.string.flickr_api_key));
//            return null;
//            return new FlickrFetchr().fetchItems(getString(R.string.flickr_api_key));
//        }

        @Override
        protected void onPostExecute(List<GalleryItem> galleryItems) {
            super.onPostExecute(galleryItems);
//            mGalleryItems = galleryItems;
//            setupAdapter();

            if (lastFetchedPage > 1) {
                mGalleryItems.addAll(galleryItems);
                mPhotoRecyclerView.getAdapter().notifyDataSetChanged();
            } else {
                mGalleryItems = galleryItems;
                setupAdapter();
            }
            lastFetchedPage++;
        }
    }
}