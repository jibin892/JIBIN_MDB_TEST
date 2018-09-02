package smoovie.apps.com.kayatech.smoovie.view;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import smoovie.apps.com.kayatech.smoovie.model.Movie;
import smoovie.apps.com.kayatech.smoovie.R;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    //Adapter Class
    private final Context context;
    private List<Movie> MovieList;
    private static MovieClickHandler mMovieClickHandler;


    MoviesAdapter(Context context, List<Movie> movies, MovieClickHandler MovieClickHandler) {
        this.context = context;
        this.MovieList = movies;
        mMovieClickHandler = MovieClickHandler;


    }

    public void setMovieList(List<Movie> movieList) {
        this.MovieList = movieList;
        notifyDataSetChanged();
    }

    public void clearMovies() {
        //called when user sorts list starting from page 1
        MovieList.clear();
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.movie_grid_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new MoviesViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder holder, int position) {

        holder.bind(MovieList.get(position));
    }

    @Override
    public int getItemCount() {
        //check for null
        return (MovieList == null) ? 0 : MovieList.size();
    }


    // Provide a reference to the views for each data item
    public static class MoviesViewHolder extends RecyclerView.ViewHolder {

        Movie movies;
        private Context ctx;
        private final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w185";
        @BindView(R.id.tv_movie_card_title)
        TextView mMovieTitle;
        @BindView(R.id.iv_poster_image)
        ImageView mPosterImage;
        @BindView(R.id.tv_rating_cardlabel)
        TextView mMovieRatings;

        MoviesViewHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            ButterKnife.bind(this, itemView);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMovieClickHandler.onClick(movies);
                }
            });
        }

        private void bind(Movie movie) {

            this.movies = movie;

            //Main Activity UI
            //Movie Title and Typeface
            ctx = itemView.getContext();
            final Typeface custom_font = Typeface.createFromAsset(ctx.getAssets(), "fonts/Roboto-Thin.ttf");
            mMovieTitle.setTypeface(custom_font);
            mMovieTitle.setText(movie.getMovieTitle());

            //Poster Image
            Picasso.with(ctx)
                    .load(IMAGE_BASE_URL + movie.getMoviePoster())
                    .error(R.drawable.test)
                    .placeholder(R.drawable.test)
                    .into(mPosterImage);

            //Movie Rating
            String rating = " " + Float.toString(movie.getVoterAverage()) + " ";
            mMovieRatings.setText(rating);

        }
    }

}