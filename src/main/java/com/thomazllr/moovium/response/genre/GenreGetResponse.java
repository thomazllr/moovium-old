package com.thomazllr.moovium.response.genre;


import com.thomazllr.moovium.model.Genre;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GenreGetResponse {

    private String name;
    private String label;

    public static GenreGetResponse toGenreGetResponse(Genre genre) {
        return GenreGetResponse.builder()
                .name(genre.name())
                .label(formatLabel(genre.name()))
                .build();
    }

    private static String formatLabel(String name) {
        return name.replace("_", " ").toLowerCase()
                .replaceFirst(String.valueOf(name.charAt(0)).toLowerCase(),
                        String.valueOf(name.charAt(0)).toUpperCase());
    }
}
