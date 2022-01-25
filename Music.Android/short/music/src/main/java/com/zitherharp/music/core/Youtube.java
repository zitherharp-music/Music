package com.zitherharp.music.core;

import com.zitherharp.music.model.Artist;
import com.zitherharp.music.type.Language;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class Youtube extends Spreadsheet {
    protected String artistId;
    protected int duration = 0;
    protected List<Artist> artists;

    public enum Image {
        DEFAULT("120x90"),
        MQDEFAULT("320x180"),
        HQDEFAULT("480x360"),
        SDDEFAULT("640x480"),
        MAXRESDEFAULT("1280x720");
        String size;
        Image(String size) {
            this.size = size;
        }
        public String getSize() {
            return size;
        }
    }

    public List<Artist> getArtists() {
        if (artists == null) {
            artists = new ArrayList<>();
            for (String id : artistId.split(splitChar)) {
                for (Artist artist : Repository.getArtists()) {
                    if (artist.Id.equals(id)) {
                        artists.add(artist); break;
                    }
                }
            }
        }
        return artists;
    }

    public String getImageUrl(@NotNull Image image) {
        return String.format("https://i.ytimg.com/vi/%s/%s.jpg", Id, image.name().toLowerCase());
    }

    public String getShareUrl(boolean isEmbed) {
        return !isEmbed ? "https://youtu.be/" + Id : "https://www.youtube.com/embed/" + Id;
    }

    public String toString(Language language) {
        return getName(language) + " - ";
    }
}
