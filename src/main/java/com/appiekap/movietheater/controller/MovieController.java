package com.appiekap.movietheater.controller;

import com.appiekap.movietheater.exception.NotFoundException;
import com.appiekap.movietheater.model.Movie;
import com.appiekap.movietheater.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * MovieController
 * @author Sjakie Kannietcoderen
 */
@RestController
@RequestMapping("api/movie/")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    /**
     * createMovie movie
     * @param movie to be created.
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public void createMovie(@RequestBody Movie movie) {
        this.movieRepository.save(movie);
    }

    /**
     * getAllMovies movies
     * @return Iterable of movies.
     */
    @RequestMapping(value = "all", method = RequestMethod.GET)
    public Iterable<Movie> getAllMovies() {
        List<Movie> movies = new ArrayList<>();
        Movie dummy = new Movie();
        dummy.setId(1);
        dummy.setTitle("Dummy movie");
        dummy.setWatched(true);
        movies.add(dummy);
        return movies;

        // TODO uncomment for production:
        // return this.movieRepository.findAll();
    }

    /**
     * getMovieByTitle movies
     * @param title String of the movie title.
     * @return Iterable of movies.
     */
    @RequestMapping(value = "title/{title}", method = RequestMethod.GET)
    public Iterable<Movie> getMovieByTitle(@PathVariable String title) {
        List<Movie> movies = this.movieRepository.findByTitel(title);
        if(movies == null  || movies.size() == 0)
            throw new NotFoundException();

        return movies;
    }

    /**
     * toggleMovieWatched movie
     * @param id Id of the movie.
     */
    @RequestMapping(value = "watched/{id}", method = RequestMethod.PUT)
    public void toggleMovieWatched(@PathVariable long id) {
        Movie movie = movieRepository.findOne(id);

        if (movie == null)
            throw new NotFoundException();

        // Flip da shit.
        movie.setWatched(!movie.isWatched());

        movieRepository.save(movie);
    }

    /**
     * deleteMovie
     * @param id Id of the movie.
     */
    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    public void deleteMovie(@PathVariable long id) {
        Movie movie = movieRepository.findOne(id);

        if (movie == null)
            throw new NotFoundException();

        movieRepository.delete(movie);
    }
}