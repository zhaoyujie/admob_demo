package com.demo.admob;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.ads.formats.NativeContentAd;
import com.google.android.gms.ads.formats.NativeContentAdView;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;

import java.util.List;

/**
 * Created by vbusani on 3/1/16.
 */
public class RecyclerViewAdaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String ADMOB_AD_UNIT_ID = "ca-app-pub-3940256099942544/2247696110";
    private static final String ADMOB_APP_ID = "ca-app-pub-3940256099942544~3347511713";

    private Context mContext;
    private List<MyListModel> mList;

    public RecyclerViewAdaptor(Context mContext, List<MyListModel> mList) {
        this.mList = mList;
        this.mContext = mContext;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.listView_name);
        }
    }

    public class ViewHolderAdMob extends RecyclerView.ViewHolder {
        UnifiedNativeAdView adView;

        ViewHolderAdMob(View view) {
            super(view);
            adView = view.findViewById(R.id.native_ad_id);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        Log.d("zhaoyujie", "  viewType = " + viewType);
        switch (viewType) {
            case 1: {
                View v = inflater.inflate(R.layout.list_item_1, parent, false);
                viewHolder = new MyViewHolder(v);
                break;
            }
            case 2: {
                View v = inflater.inflate(R.layout.list_item_admob, parent, false);
                viewHolder = new ViewHolderAdMob(v);
                break;
            }
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        MyListModel model = mList.get(holder.getAdapterPosition());

        switch (holder.getItemViewType()) {
            case 1:
                MyViewHolder viewHolder = (MyViewHolder) holder;
                viewHolder.name.setText(model.getName());
                break;

            case 2:
                final ViewHolderAdMob viewHolderAdMob = (ViewHolderAdMob) holder;
                requestAd(viewHolderAdMob);
                break;
        }
    }

    private void requestAd(final ViewHolderAdMob viewHolderAdMob) {
        long start = System.currentTimeMillis();
        AdLoader.Builder builder = new AdLoader.Builder(mContext, ADMOB_AD_UNIT_ID);
        builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            @Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                populateContentAdView(unifiedNativeAd, viewHolderAdMob.adView);
            }
        }).withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int i) {
                Log.d("zhaoyujie", "   errorCode = " + i);
            }
        });
        final AdLoader loader = builder.build();
        //TODO  this method make main thread carton at main thread or other thread
        //Runnable runnable = new Runnable() {
        //    @Override
        //    public void run() {
        //        loader.loadAd(new AdRequest.Builder().build());
        //    }
        //};
        //new Thread(runnable).start();
        //TODO  this method make main thread carton at main thread or other thread
        loader.loadAd(new AdRequest.Builder().build());
        long duration = System.currentTimeMillis() - start;
        Log.d("zhaoyujie", " request duration = " + duration);
    }

    private void populateContentAdView(UnifiedNativeAd nativeContentAd, UnifiedNativeAdView adView) {
        // mVideoStatus.setText("Video status: Ad does not contain a video asset.");

        adView.setHeadlineView(adView.findViewById(R.id.contentad_headline));
        adView.setBodyView(adView.findViewById(R.id.contentad_body));
        adView.setCallToActionView(adView.findViewById(R.id.contentad_call_to_action));
        adView.setAdvertiserView(adView.findViewById(R.id.contentad_advertiser));

        // Some assets are guaranteed to be in every NativeContentAd.
        ((TextView) adView.getHeadlineView()).setText(nativeContentAd.getHeadline());
        ((TextView) adView.getBodyView()).setText(nativeContentAd.getBody());
        ((TextView) adView.getCallToActionView()).setText(nativeContentAd.getCallToAction());
        ((TextView) adView.getAdvertiserView()).setText(nativeContentAd.getAdvertiser());

        adView.setMediaView((MediaView) adView.findViewById(R.id.mediaView));

        // Assign native ad object to the native view.

        long start = System.currentTimeMillis();
        //TODO this method takes 40~100 ms
        adView.setNativeAd(nativeContentAd);
        long duration = System.currentTimeMillis() - start;
        Log.d("zhaoyujie", " setNativeAd  duration = " + duration);
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getViewType();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
