package com.nexign.service.Component;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.nexign.service.Entity.Subscriber;
import com.nexign.service.Repository.SubscriberRepository;

@Component
public class SubscriberInitializer implements CommandLineRunner{
 
    @Autowired
    private SubscriberRepository subscriberRepository;

    @Override
    public void run(String... args) {
        Subscriber[] subscribers = {
            new Subscriber("1234567890"),
            new Subscriber("1231231231"),
            new Subscriber("2588522582"),
            new Subscriber("8800553535"),
            new Subscriber("1400768822"),
            new Subscriber("3223223220"),
            new Subscriber("1111111111"),
            new Subscriber("2222222222"),
            new Subscriber("3333333333"),
            new Subscriber("4444444444")
        };
        subscriberRepository.saveAll(Arrays.asList(subscribers));
    }
}
