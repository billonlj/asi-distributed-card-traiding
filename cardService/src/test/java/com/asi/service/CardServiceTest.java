package com.asi.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import javax.persistence.MapKeyColumn;

import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.asi.model.Card;
import com.asi.model.CardInstance;
import com.asi.repository.CardInstanceRepository;
import com.asi.repository.CardRepository;
import com.jayway.jsonpath.Option;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {
    
    @InjectMocks
    @Spy
    CardService cardService;

    @Mock
    CardRepository cardRepositoryMock;

    @Mock
    CardInstanceRepository cardInstanceRepositoryMock;

    private List<CardInstance> listCardsInstance = Arrays.asList(new CardInstance[]{new CardInstance(),new CardInstance(),new CardInstance(), new CardInstance(), new CardInstance()});

    private ArrayList<Card> listCards = new ArrayList<>(Arrays.asList(new Card(),new Card(),new Card(), new Card(), new Card()));

    private List<CardInstance> listNoCardsInstance = Arrays.asList(new CardInstance[]{});

    // =========== getOneCard ===========
    @Test
    void testGetOneCard_validId() {
        Card card = new Card(1,  "Superman", "Test de superman", "Feu", 100, "/");
        //CardInstance cardInstance = new CardInstance(card, 10, 10, 10, 10);
        Optional<Card> oCard = Optional.of(card);
        //when(cardInstanceRepositoryMock.save(any(CardInstance.class))).thenReturn(cardInstance);

        when(cardRepositoryMock.findById(1)).thenReturn(oCard);

        Assertions.assertThat(cardService.getCard(1) != null);
    }

    @Test
    void testGetOneCardInValidIdThrowException() {

        Exception exception = assertThrows(RuntimeException.class, () -> {
            cardService.getCard(1);
        });

        assertTrue(exception.getMessage().contains("Ressource not found"));
    }

    // =========== getCardByUser ===========

    @Test
    void testGetCardsByUserValidUser() {
        Optional<List<CardInstance>> oList = Optional.of(listCardsInstance);
        when(cardInstanceRepositoryMock.findByIdUser(1)).thenReturn(oList);

        Assertions.assertThat(!cardService.getCardsByUser(1).isEmpty());
    }
    
    @Test
    void testGetCardsByUserNonExistantUser() {
        Optional<List<CardInstance>> oList = Optional.of(listNoCardsInstance);

        when(cardInstanceRepositoryMock.findByIdUser(1)).thenReturn(oList);

        Assertions.assertThat(cardService.getCardsByUser(1).isEmpty());
    }

    @Test
    void testRegisterNewUserOk() {
        when(cardService.getAll()).thenReturn(listCards);
    
        when(cardInstanceRepositoryMock.save(any(CardInstance.class))).thenReturn(new CardInstance());

        Assertions.assertThat(!cardService.registerNewUserCards(1).isEmpty());
    }

    @Test
    void testRegisterNewUserNotEnoughCards() {
        when(cardService.getAll()).thenReturn(new ArrayList<>());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            cardService.registerNewUserCards(1);
        });

    }
}
