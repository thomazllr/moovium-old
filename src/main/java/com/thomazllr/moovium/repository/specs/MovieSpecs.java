package com.thomazllr.moovium.repository.specs;

import com.thomazllr.moovium.model.Movie;
import org.springframework.data.jpa.domain.Specification;

public class MovieSpecs {

    public static Specification<Movie> titleContains(String title) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    public static Specification<Movie> isFeatured(Boolean featured) {
        return (root, query, cb) -> cb.equal(root.get("featured"), featured);
    }

}
