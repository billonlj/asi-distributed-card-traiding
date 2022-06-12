package com.asi.service;

import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import com.asi.dto.user.BalanceUserDto;
import com.asi.model.Sale;
import com.asi.repository.SaleRepository;
import com.asi.rest.card.CardRestConsumer;
import com.asi.rest.user.UserRestConsumer;


@ExtendWith(MockitoExtension.class)
class SaleServiceTest {

    @InjectMocks
    @Spy
    SaleService saleService;

    @Mock
    SaleRepository saleRepositoryMock;

    @Mock
    CardRestConsumer cardRestConsumerMock;

    @Mock
    UserRestConsumer userRestConsumerMock;


    // =========== Test Buy ===========

    @Test
    void testBuyOk() {
        Optional<Sale> oSale = Optional.of(new Sale(1, 1, 10));

        when(saleRepositoryMock.findById(anyInt())).thenReturn(oSale);
        
        //when(userRestConsumerMock.balanceUserMoney(any(BalanceUserDto.class))).thenReturn(new ResponseEntity<Boolean>(true, HttpStatus.OK));
        //when(userRestConsumerMock.balanceUserMoney(any(BalanceUserDto.class))).thenReturn(new ResponseEntity<Boolean>(true, HttpStatus.OK));
        
        //when(cardRestConsumerMock.buyCard(anyInt(), anyInt())).thenReturn(new ResponseEntity<Boolean>(true, HttpStatus.OK));

        Assertions.assertThat(saleService.buy(1, 1) == true);
    }

    @Test
    void testBuyBuyDidNotHappened() {
        Optional<Sale> oSale = Optional.of(new Sale(1, 1, 10));

        when(saleRepositoryMock.findById(anyInt())).thenReturn(oSale);
        
        //doReturn(ResponseEntity.ok(false)).when(userRestConsumerMock).balanceUserMoney(any(BalanceUserDto.class));
        //when(userRestConsumerMock.balanceUserMoney(new BalanceUserDto())).thenReturn(new ResponseEntity<Boolean>(false, HttpStatus.OK));
        //when(userRestConsumerMock.balanceUserMoney(new BalanceUserDto())).thenReturn(new ResponseEntity<Boolean>(false, HttpStatus.OK));
        
        //when(cardRestConsumerMock.buyCard(anyInt(), anyInt())).thenReturn(new ResponseEntity<Boolean>(false, HttpStatus.OK));

        System.out.println(saleService.buy(1, 1));

        Assertions.assertThat(saleService.buy(1, 1)).isFalse();
    }
}
