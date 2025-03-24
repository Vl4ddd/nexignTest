package com.nexign.service.Entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CDR {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String callType; 
    private String callerNumber;
    private String receiverNumber;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public CDR(String callType, String callerNumber, String receiverNumber, LocalDateTime startTime, LocalDateTime endTime) {
        this.callType = callType;
        this.callerNumber = callerNumber;
        this.receiverNumber = receiverNumber;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return callType + ", " +
               callerNumber + ", " +
               receiverNumber + ", " +
               (startTime != null ? startTime.format(formatter) : "null") + ", " +
               (endTime != null ? endTime.format(formatter) : "null");
    }
    
}
