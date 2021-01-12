package dev.keader.tmdbmovies.view.adapters;

import android.content.Context;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import dev.keader.tmdbmovies.Constants;
import dev.keader.tmdbmovies.R;

public class BindingAdapters {

    @BindingAdapter({"tumbUrl"})
    public static void loadImage(ImageView imageView, String url) {
        if (url == null || url.isEmpty())
            return;

        Context context = imageView.getContext();
        String config = "";
        int divider = context.getResources().getDisplayMetrics().widthPixels / 3;

        if (divider >= 0 && divider <= 500)
            config = "w300";
        else if (divider > 500 && divider <= 1000)
            config = "w780";
        else if (divider > 1000 && divider <= 1600)
            config = "w1280";
        else
            config = "original";

        String completeUrl = Constants.TMDB_BASE_IMAGE_URL + config + url;

        Glide.with(context)
                .load(completeUrl)
                .transform(new CenterCrop(), new RoundedCorners(16))
                .placeholder(R.drawable.loading)
                .into(imageView);
    }
}
