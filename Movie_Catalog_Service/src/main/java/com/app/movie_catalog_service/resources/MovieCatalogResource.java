package com.app.movie_catalog_service.resources;

import com.app.movie_catalog_service.models.CatalogItem;
import com.app.movie_catalog_service.models.Movie;
import com.app.movie_catalog_service.models.Rating;
import com.app.movie_catalog_service.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    // here we used a java bean, to not create a restTemplate every time we make a rest call to catalog
    @Autowired
    private RestTemplate restTemplate;

    /*
    *what we want to do
    * get all the movie IDs
    * for each movie ID, call movie info service and get details
    * put them all together
     */

    /*
    *now we just gonna hardcode the rating list part
     */

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){
      //  webClient.Builder: you can use it like restTemplate but this one is asynchronous

        UserRating ratings = restTemplate.getForObject("http://localhost:8083/ratingsData/users/"+ userId, UserRating.class);
        return ratings.getUserRating().stream().map(rating ->{
            // this is a synchronous call, you can make it asynchronous if you use web client
            Movie movie = restTemplate.getForObject("http://localhost:8082/movies/" + rating.getMovieID(), Movie.class);
            return new CatalogItem(movie.getName(), "Test", rating.getRating());
        }).collect(Collectors.toList());
    //    return Collections.singletonList(
    //            new CatalogItem("Transformers", "Nice Film", 4)
    //    );
    }
}

/*
* webClientBuilder.build() // the asynchrounous method
* .get() // get what is in the uri
* .uri("http://localhost:8082/movies/" +rating.getMovieId()) // the uri where the service is presents
* .retrieve() // et the data from the service
* .bodyToMono() // to say wait for the data
* .block(); // make the code synchrounous "block the code untile getting the data"
* */
