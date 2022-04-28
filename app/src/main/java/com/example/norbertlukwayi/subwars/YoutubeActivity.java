package com.example.norbertlukwayi.subwars;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTubeRequestInitializer;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.ChannelListResponse;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class YoutubeActivity  {
    static final String GOOGLE_API_KEY = "AIzaSyD_8-XdxXIA3NKk1VPaSSM8iT9Re96tOkg";
    String pewId ="UC-lHJZR3Gqxm24_Vd_AJ5Yw";
    String tSeriesId = "UCq-Fj5jknLsUf-MWSy4_brA";

    public static void main(String[] args) throws Exception {
        YouTube youtube = new YouTube.Builder(
                new NetHttpTransport(),
                new JacksonFactory(),
                new HttpRequestInitializer() {
                    public void initialize(HttpRequest request) throws IOException {
                    }
                })
                .setApplicationName("youtube-cmdline-search-sample")
                .setYouTubeRequestInitializer(new YouTubeRequestInitializer(GOOGLE_API_KEY))
                .build();


        Scanner input = new Scanner(System.in);

        System.out.println("Search for channel:");
        String queryTerm = input.nextLine();

        YouTube.Search.List search = youtube.search().list("snippet");
        search.setQ(queryTerm);
        search.setType("channel");

        SearchListResponse searchResponse = search.execute();
        List<SearchResult> searchResultList = searchResponse.getItems();
        if (searchResultList != null) {
            for (SearchResult searchResult : searchResultList) {
                String channelId = searchResult.getSnippet().getChannelId();

                YouTube.Channels.List channels = youtube.channels().list("snippet, statistics");
                channels.setId(channelId);

                ChannelListResponse channelResponse = channels.execute();

                for (Channel c : channelResponse.getItems()) {
                    System.out.println("Name: " + c.getSnippet().getTitle());
                    System.out.println("Subs: " + c.getStatistics().getSubscriberCount());
                    System.out.println();
                }
            }
        }

    }
}
