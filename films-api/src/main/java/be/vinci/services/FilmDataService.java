package be.vinci.services;

import be.vinci.domain.Film;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.commons.text.StringEscapeUtils;

import java.util.List;

public class FilmDataService {
    public List<Film> getAll(int minimumDuration) {
        var films = Json.parse();
        if (minimumDuration != -1) {
            List<Film> filmsFiltered = films.stream().filter(film -> film.getDuration() >= minimumDuration)
                    .toList();
            return filmsFiltered;
        }
        return films;
    }


    public Film getOne(int id) {
        var films = Json.parse();
        Film filmFound = films.stream().filter(film -> film.getId() == id).findAny().orElse(null);
        return filmFound;
    }

    public Film createOne(Film film) {
        var films = Json.parse();
        film.setId(nextFilmId());
        film.setTitle(StringEscapeUtils.escapeHtml4(film.getTitle()));
        film.setLink(StringEscapeUtils.escapeHtml4(film.getLink()));
        films.add(film);
        Json.serialize(films);
        return film;
    }


    public Film deleteOne(int id) {
        var films = Json.parse();
        Film filmToDelete = films.stream().filter(film -> film.getId() == id).findAny().orElse(null);
        films.remove(filmToDelete);
        Json.serialize(films);
        return filmToDelete;
    }

    public Film updateOne(Film film, int id) {
        var films = Json.parse();
        Film filmToUpdate = films.stream().filter(f -> f.getId() == id).findAny().orElse(null);
        film.setId(id);
        film.setTitle(StringEscapeUtils.escapeHtml4(film.getTitle()));
        film.setLink(StringEscapeUtils.escapeHtml4(film.getLink()));
        films.remove(film); // thanks to equals(), films is found via its id
        films.add(film);
        Json.serialize(films);
        return film;
    }

    public int nextFilmId() {
        var films = Json.parse();
        if (films.size() == 0)
            return 1;
        return films.get(films.size() - 1).getId() + 1;
    }
}
