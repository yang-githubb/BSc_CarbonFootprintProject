package com.example.carbonfootprint;

import static java.lang.Math.round;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class facts extends Fragment {

    ImageView imageView;

    private final Handler scrollHandler = new Handler();
    private Runnable scrollRunnable;
    private LinearLayoutManager layoutManager;
    private TextAdapter adapter;
    private List<String> videoIds = Arrays.asList(
            "J_iDcKDAwbA", "Dwkh46MZuIc", "bYb7YLsXvzg", "a9yO-K8mwL0",
            "Mvp97__BP84", "rByHiqc0K9k", "sTvqIijqvTg"
    );
    private static final String ARG_USER_ID = "USER_ID";

    public facts() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_facts, container, false);

        imageView = view.findViewById(R.id.imageView);

        Context context = requireContext();

        Animation animation = AnimationUtils.loadAnimation(context, R.anim.blink_anim);
        imageView.startAnimation(animation);

        YouTubePlayerView youTubePlayerView = view.findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                int randomIndex = new Random().nextInt(videoIds.size());
                String randomVideoId = videoIds.get(randomIndex);
                youTubePlayer.cueVideo(randomVideoId, 0);
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.textListRecyclerView);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        List<String> texts = getDummyTexts();
        adapter = new TextAdapter(context,texts);
        recyclerView.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        Drawable dividerDrawable = ContextCompat.getDrawable(getContext(), R.drawable.divider);
        dividerItemDecoration.setDrawable(dividerDrawable);
        recyclerView.addItemDecoration(dividerItemDecoration);
        initializeAutoScroll();

        TextView textView = view.findViewById(R.id.textViewRight);
        long equivalent_tree = round(Session.getLastFootprintTonnes() * 6);
        String result = "Your yearly footprint equals the carbon stored by " + equivalent_tree + " trees!";
        textView.setText(result);

        return view;
    }

    private void initializeAutoScroll() {
        scrollRunnable = new Runnable() {
            @Override
            public void run() {
                if (layoutManager != null && adapter != null) {
                    int lastVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition();
                    int nextItemPosition = lastVisibleItemPosition + 1;

                    if (layoutManager != null) {
                        SlowSmoothScroller smoothScroller = new SlowSmoothScroller(getContext());
                        if (lastVisibleItemPosition < adapter.getItemCount() - 1) {
                            smoothScroller.setTargetPosition(nextItemPosition);
                        } else {
                            smoothScroller.setTargetPosition(0);
                        }
                        layoutManager.startSmoothScroll(smoothScroller);
                    }
                }
                scrollHandler.postDelayed(this, 50);
            }
        };
    }

    @Override
    public void onPause() {
        super.onPause();
        if (scrollRunnable != null) {
            scrollHandler.removeCallbacks(scrollRunnable);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (scrollRunnable != null) {
            scrollHandler.postDelayed(scrollRunnable, 2000);
        }
    }

    private List<String> getDummyTexts() {
        return Arrays.asList(
                "Reducing our carbon footprint means not only minimizing the energy we consume but also adopting a lifestyle that supports sustainability.",
                "The greatest threat to our planet is the belief that someone else will save it. - Robert Swan",
                "Every action we take impacts the world around us. A single person choosing to reduce their carbon footprint can inspire others to follow suit.",
                "Sustainability is no longer about doing less harm. It's about doing more good. - Jochen Zeitz",
                "The carbon footprint of our daily choices doesn't just influence climate change; it's a direct reflection of our relationship with the planet.",
                "Climate change is a global challenge that demands a global response. Reducing your carbon footprint is a step towards a healthier planet for future generations.",
                "Energy conservation is the foundation of energy independence. - Tom Allen",
                "We don't inherit the earth from our ancestors, we borrow it from our children. - Native American Proverb",
                "Adopting a plant-based diet is one of the most impactful ways to reduce your carbon footprint and save resources.",
                "Transportation is a significant part of our carbon footprint. Opting for public transit, biking, or walking can make a considerable difference.",
                "The future of our planet depends on the actions we take today. Reducing our carbon footprint is not just an option; it's a necessity.",
                "Recycling, reusing, and reducing are more than just words; they are actions that contribute to a sustainable future.",
                "Investing in renewable energy sources not only reduces carbon emissions but also promotes a sustainable and independent energy future.",
                "Water conservation plays a crucial role in reducing our carbon footprint, as treating and pumping water requires a significant amount of energy.",
                "Every watt saved reduces your carbon footprint. Energy efficiency is a simple way to combat climate change."
        );
    }

}