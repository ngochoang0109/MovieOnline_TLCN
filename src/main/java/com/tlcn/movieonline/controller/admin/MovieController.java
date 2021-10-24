package com.tlcn.movieonline.controller.admin;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.tlcn.movieonline.dto.MovieRequest;
import com.tlcn.movieonline.model.*;
import com.tlcn.movieonline.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.FileOutputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping(value = "/admin")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private YearReleaseService releaseService;

    @Autowired
    private CastService castService;

    @Autowired
    private DirectorService directorService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private GenreService genreService;

    @GetMapping(value = "/movies")
    public String getAllMovie(Model model) {
        List<Movie> lstMovie = movieService.getAll();
        model.addAttribute("lstMovie", lstMovie);
        return "admin/movie-manager";
    }

    @GetMapping(value = "/movies/add")
    public String add(Model model) {
        Movie movie = new Movie();
        model.addAttribute("movie", movie);
//        List<ReleaseYear> lstRelease = releaseService.findAll();
//        model.addAttribute("lstRelease", lstRelease);
        model.addAttribute("movieRequest", new MovieRequest());

        List<Director> lstDirector= directorService.getAllDirector();
        model.addAttribute("lstDirector", lstDirector);
        List<Genre> lstGenre= genreService.findAll();
        model.addAttribute("lstGenre", lstGenre);
        List<Country> lstCountry= countryService.findAll();
        model.addAttribute("lstCountry", lstCountry);
        return "admin/movie-add";
    }

//    @PostMapping(value = "/movies/add")
//    public String add(@ModelAttribute("movieRequest") MovieRequest movieRequest){
//        ReleaseYear releaseYear= new ReleaseYear();
//        Movie movie= new Movie();
////        Image image= new Image();
//        Video video= new Video();
//
////        String urlImage=doUpload(movieRequest.getImage());
////        if (urlImage!=""){
////            image.setSource(urlImage);
////        }
////        Set<MovieImage> images= new HashSet<>();
////        images.add(image);
//
//        Set<Video> videos= new HashSet<>();
//        video.setSource(movieRequest.getVideoTrailer());
//        video.setType("trailer");
//        videos.add(video);
//
//        // clear space here
//        String[] strCasts= movieRequest.getCast().split(",");
//        Set<Cast> casts= new HashSet<>();
//        for (String item: strCasts) {
//            Cast cast = castService.getCastByName(item.trim());
//            if (cast == null) {
//                Cast c = new Cast();
//                c.setName(item.trim());
//                casts.add(c);
//            } else {
//                casts.add(cast);
//            }
//        }
//
//        // clear space here
//        String[] strDirectors= movieRequest.getDirector().split(",");
//        Set<Director> directors= new HashSet<>();
//        for (String item: strDirectors) {
//            Director director = directorService.getDirectorByName(item);
//            if (director == null) {
//                Director d= new Director();
//                d.setName(item.trim());
//                directors.add(d);
//            }
//            else {
//                directors.add(director);
//            }
//        }
//
//        // clear space here
//        String[] strCountries= movieRequest.getCountry().split(",");
//        Set<Country> countries= new HashSet<>();
//        for (String item: strCountries) {
//            Country country = countryService.getCountryByName(item);
//            if (country == null) {
//                Country c= new Country();
//                c.setName(item);
//                countries.add(c);
//            }
//            else {
//                countries.add(country);
//            }
//        }
//
//        // clear space here
//        String[] strGenre= movieRequest.getGenre().split(",");
//        Set<Genre> genres= new HashSet<>();
//        for (String item: strGenre) {
//            Genre genre = genreService.getGenreByName(item);
//            if (genre == null) {
//                Genre g= new Genre();
//                g.setName(item);
//                genres.add(g);
//            }
//            else {
//                genres.add(genre);
//            }
//        }
//
//
//        //do something
//        releaseYear = releaseService.getYearReleaseById(movieRequest.getReleaseYear());
//
//        movie.setTitle(movieRequest.getTitle());
//        movie.setDescription(movieRequest.getDescription());
//        movie.setDuration(movieRequest.getDuration());
//        movie.setNumber(movieRequest.getNumber());
//        movie.setView(0);
//        movie.setStatus(movieRequest.isStatus());
//        movie.setRelYearId(releaseYear);
////        movie.setImages(images);
//        movie.setVideos(videos);
//        movie.setCasts(casts);
//        movie.setDirectors(directors);
//        movie.setCountries(countries);
//        movie.setGenres(genres);
//
//        movieService.addMove(movie);
//
//        return "admin/movie-manager";
//    }

    public String doUpload(MultipartFile params){
        String url="";
        try{
            Map jsonResult= cloudinary.uploader().uploadLarge(params.getBytes(),
                    ObjectUtils.asMap("resource_type","auto","chunk_size",100000000));
            url=(String) jsonResult.get("secure_url");
            return url;
        }
        catch (Exception e){
            return url;
        }
    }

    public File convertMultipartFile(MultipartFile multipartFile){
        File file= new File(multipartFile.getOriginalFilename());
        try {
            FileOutputStream fileOutputStream= new FileOutputStream(file);
            fileOutputStream.write(multipartFile.getBytes());
            fileOutputStream.close();
            return file;
        }
        catch (Exception e){
            return file;
        }
    }


}
