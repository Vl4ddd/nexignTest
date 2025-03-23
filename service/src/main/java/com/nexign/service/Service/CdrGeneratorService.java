package com.nexign.service.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nexign.service.Entity.CDR;
import com.nexign.service.Entity.Subscriber;
import com.nexign.service.Repository.CDRRepository;
import com.nexign.service.Repository.SubscriberRepository;

/**
 * Service для работы с CDR.
 * Предоставляет методы для генерации и записи cdr в БД.
 * @author Влад 
 */
@Service
public class CdrGeneratorService {

    /** Репозиторий для работы с абонентами */
    @Autowired
    private SubscriberRepository subscriberRepository;
     
    /** Репозиторий для работы с CDR записями */
    @Autowired
    private CDRRepository cdrRepository;

    /** Поле для генеарции случайных чисел */
    private final Random random = new Random();

    /**
    * Создает случайные записи cdr и сохраняет их в БД.
    *
    * @param countOfRecords количество записей, которые будут созданы.
    */
    public void generateCDR(int countOfRecords){

        List<Subscriber> subscribers = subscriberRepository.findAll();

        if (subscribers.isEmpty()){
            throw new IllegalStateException("Абоненты не найдены");
        }

        LocalDateTime currentTime = LocalDateTime.now();
        for (int i = 0; i < countOfRecords; i++){

            Subscriber caller = subscribers.get(random.nextInt(subscribers.size()));
            Subscriber receiver = subscribers.get(random.nextInt(subscribers.size()));

            String callType = random.nextBoolean() ? "01" : "02"; 
            LocalDateTime startTime = currentTime.minusDays(random.nextInt(365)).minusHours(random.nextInt(24)).minusMinutes(random.nextInt(60));
            int duration = random.nextInt(10000) + 60; 
            LocalDateTime endTime = startTime.plusSeconds(duration);

            CDR cdr = new CDR(callType, caller.getPhoneNumber(), receiver.getPhoneNumber(), startTime, endTime);
            cdrRepository.save(cdr);
        }

    }


}
