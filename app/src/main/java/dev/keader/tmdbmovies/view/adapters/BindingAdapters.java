package dev.keader.tmdbmovies.view.adapters;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

import dev.keader.tmdbmovies.Constants;
import dev.keader.tmdbmovies.R;
import dev.keader.tmdbmovies.api.tmdb.Company;
import dev.keader.tmdbmovies.api.tmdb.Genre;
import dev.keader.tmdbmovies.database.model.MovieDTO;
import dev.keader.tmdbmovies.database.model.MovieWithRelations;

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

    @BindingAdapter({"bgUrl"})
    public static void loadBgImage(ImageView imageView, String url) {
        if (url == null || url.isEmpty())
            return;

        Context context = imageView.getContext();
        String completeUrl = Constants.TMDB_BASE_IMAGE_URL + "original" + url;
        Glide.with(context)
                .load(completeUrl)
                .transform(new CenterCrop(), new GlideBlurTransformation(context))
                .into(imageView);
    }

    @BindingAdapter({"genre"})
    public static void setGenre(TextView textView, MovieWithRelations movieWithRelations) {
        if (movieWithRelations == null)
            return;

        String text =  movieWithRelations.getGenres().stream()
                .map(Genre::getName)
                .collect(Collectors.joining(" | "));

        textView.setText(text);
    }

    @BindingAdapter({"company"})
    public static void setCompany(TextView textView, MovieWithRelations movieWithRelations) {
        if (movieWithRelations == null)
            return;

        if (movieWithRelations.getCompanies().isEmpty()) {
            textView.setText(textView.getResources().getString(R.string.missing_company));
            return;
        }

        String text =  movieWithRelations.getCompanies().stream()
                .map(Company::getName)
                .collect(Collectors.joining(" | "));
        textView.setText(text);
    }

    @BindingAdapter({"rating"})
    public static void setRating(TextView textView, MovieDTO movie) {
        if (movie == null)
            return;

        String date = LocalDate.parse(movie.getReleaseDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        String text = textView.getResources().getString(
                R.string.popularity_format,
                Double.toString(movie.getVoteAverage()),
                Integer.toString(movie.getVoteCount()),
                date);

        textView.setText(text);
    }

    @BindingAdapter({"ratingText"})
    public static void setRatingText(TextView textView, MovieDTO movie) {
        if (movie == null)
            return;

        textView.setText(Double.toString(movie.getVoteAverage()));
    }

    @BindingAdapter({"releaseDate"})
    public static void setReleaseDate(TextView textView, MovieDTO movie) {
        if (movie == null)
            return;

        String releaseDate = movie.getReleaseDate();
        if (releaseDate == null || releaseDate.isEmpty())
            return;

        String date = LocalDate.parse(releaseDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        textView.setText(date);
    }

    @BindingAdapter({"overview"})
    public static void setOverview(TextView textView, MovieDTO movie) {
        if (movie == null)
            return;

        String overview = movie.getOverview();
        if (overview == null || overview.isEmpty())
            overview = textView.getResources().getString(R.string.empty_overview);

        textView.setText(overview);
    }

    @BindingAdapter({"originalTitle"})
    public static void setOriginalTitle(TextView textView, MovieWithRelations movieWithRelations) {
        if (movieWithRelations == null)
            return;
        String originalTitle = movieWithRelations.getMovie().getOriginalTitle();
        if (originalTitle == null || originalTitle.isEmpty())
            textView.setText("...");
        else {
            originalTitle = textView.getResources().getString(R.string.original_title_format, originalTitle);
            textView.setText(originalTitle);
        }
    }
}
