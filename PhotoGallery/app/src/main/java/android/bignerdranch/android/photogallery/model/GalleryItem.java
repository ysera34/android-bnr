package android.bignerdranch.android.photogallery.model;

/**
 * Created by yoon on 2016. 11. 26..
 */

public class GalleryItem {

    private String mCaption;
    private String mId;
    private String mUrl;

    public String getCaption() {
        return mCaption;
    }

    public void setCaption(String caption) {
        mCaption = caption;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    @Override
    public String toString() {
        return "GalleryItem{" +
                "mCaption='" + mCaption + '\'' +
                ", mId='" + mId + '\'' +
                ", mUrl='" + mUrl + '\'' +
                '}';
    }
}
