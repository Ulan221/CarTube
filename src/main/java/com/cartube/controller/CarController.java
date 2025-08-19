package com.cartube.controller;

import com.cartube.entity.Car;
import com.cartube.entity.User;
import com.cartube.service.CarService;
import com.cartube.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;


@Controller
public class CarController {
    private final CarService carService;
    private final UserService userService;

    @Autowired
    public CarController(final CarService carService, final UserService userService) {
        this.carService = carService;
        this.userService = userService;
    }

    @GetMapping
    public String Home(@RequestParam(required = false) final String carBrand, final Model modelAttribute) {
         final List<Car> cars;

        if (carBrand != null && !carBrand.isEmpty()) {
            cars = carService.searchByBrand(carBrand);
        } else {
            cars = carService.getAllCars();
        }

        modelAttribute.addAttribute("cars", cars);
        return "home";
    }

    @GetMapping("/admin")
    public String admin(final Model model) {
        final List<Car> cars = carService.getAllCars();
        model.addAttribute("cars", cars);
        return "admin-page";
    }

    @GetMapping("/add")
    public String carAdd() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            return "redirect:/register";
        }
        return "car-add";
    }

    @PostMapping("/add-car")
    public String addCar(@RequestParam("carBrand") final String carBrand,
                         @RequestParam("carModel") final String carModel,
                         @RequestParam("carYear") final String carYear,
                         @RequestParam("carMileage") final Integer carMileage,
                         @RequestParam("video") final MultipartFile video) throws IOException {


        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final String username = authentication.getName();
        final User currentUser = userService.findUser(username);

        if (currentUser == null) {
            return "redirect:/login";
        }

        final String videoPath = "http://localhost:8000/videos/" + video.getOriginalFilename();
        final File videoFile = new File("D:/Projects/CarTube/videos/" + video.getOriginalFilename());
        video.transferTo(videoFile);

        final Car car = new Car(carBrand, carModel, carYear, carMileage, videoPath);
        car.setVideoPath("/videos/" + video.getOriginalFilename());
        car.setOwner(currentUser);


        carService.saveCar(car);

        return "redirect:/";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteCar(@PathVariable final Long id) {
        carService.deleteCarById(id);
        return "redirect:/";
    }
}
