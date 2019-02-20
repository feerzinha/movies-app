package com.arctouch.codechallenge.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.arctouch.codechallenge.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

public class GlideUtils {

    public static void requestGlide(Context context, String url, ImageView view, RequestOptions options) {
        Glide.with(context)
                .load(url)
                .apply(options.placeholder(R.drawable.ic_image_placeholder))
                .into(view);
    }

    public static void requestGlideWithListener(Context context, String url, ImageView view, RequestOptions option, GlideUtilsListener glideUtilsListener) {
        Glide.with(context)
                .asBitmap()
                .load(url)
                .apply(option.placeholder(R.drawable.ic_image_placeholder))
                .listener(new RequestListener<Bitmap>() {

                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        glideUtilsListener.onResourceReady(resource);

                        return false;
                    }
                })
                .into(view);
    }

    public interface GlideUtilsListener {

        void onResourceReady(Bitmap resource);

    }
}
