package com.asi.init;

import java.lang.reflect.Array;
import java.util.Arrays;

import com.asi.model.Card;
import com.asi.model.Family;
import com.asi.repository.CardRepository;
import com.asi.repository.FamilyRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DBOperationRunner implements CommandLineRunner {
    @Autowired
    FamilyRepository familyRepository;
    @Autowired
    CardRepository cardRepository;



    @Override
    public void run(String... args) throws Exception {
        Family DC = new Family(0, "DC COMICS");
        Family Marvel = new Family(1, "Marvel");

        familyRepository.saveAll(Arrays.asList(
            DC,
            Marvel
        ));

        cardRepository.saveAll(Arrays.asList(
            new Card(1, "DC COMICS", "Bruce Wayne, alias Batman, est un héros de fiction appartenant à l''univers de DC Comics", 80, "Batman", DC, 170, 50, 80,"https://static.fnac-static.com/multimedia/Images/8F/8F/7D/66/6716815-1505-1540-1/tsp20171122191008/Lego-lgtob12b-lego-batman-movie-lampe-torche-batman.jpg"),
            new Card(2, "MARVEL", "C dedpool", 100, "Deadpool", Marvel, 15, 999999, 15,"https://static.hitek.fr/img/actualite/2017/06/27/i_deadpool-2.jpg"),
            new Card(3, "DC COMICS", "Description de superman [insérer un jeu de mot]", 100, "Superman", DC, 50, 500, 50,"http://www.superherobroadband.com/app/themes/superhero/assets/img/superhero.gif")
        ));
        

        // userService.registerUser(new User("t", "t", "t", 1000, "t"));
        // userService.registerUser(new User(6, "r", "r", "r", 1000, "r"));
        

        System.out.println("----------All Data saved into Database----------------------");
    }

}