package be.vinci;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Path("films")
public class FilmResource {
    private Film[] defaultFilms = {
            new Film(1, "No Time To Die", 163, 301, "https://en.wikipedia.org/wiki/No_Time_to_Die"),
            new Film(2, "Dune", 156, 165, "https://en.wikipedia.org/wiki/Dune_(2021_film)"),
            new Film(3, "Shang-Chi and the Legend of the Ten Rings", 132, 200, "https://en.wikipedia.org/wiki/Shang-Chi_and_the_Legend_of_the_Ten_Rings"),
            new Film(4, "Peter Rabbit 2: The Runaway", 93, 45, "https://en.wikipedia.org/wiki/Peter_Rabbit_2:_The_Runaway")
    };
    private List<Film> films = new ArrayList<>(Arrays.asList(defaultFilms)); // to get a changeable list, asList is fixed size


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Film> getAll() {
        return films;
    }
}