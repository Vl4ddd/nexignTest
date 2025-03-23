package com.nexign.service.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nexign.service.Entity.CDR;

/**
 * Репозиторий для работы с сущностями CDR.
 * Предоставляет методы для поиска записей.
 * @author Влад
 */
public interface CDRRepository extends JpaRepository<CDR, Long> {
    /**
     * Находит все записи CDR по номеру вызывающего абонента и диапазону времени.
     *
     * @param callerNumber Номер вызывающего абонента.
     * @param startTime Дата и время начала диапазона.
     * @param endTime Дата и время окончания диапазона.
     * @return Список записей CDR, соответствующих заданным параметрам.
     */
    List<CDR> findByCallerNumberAndStartTimeBetween(String callerNumber, LocalDateTime startTime, LocalDateTime endTime);
    /**
     * Находит все записи CDR по диапазону времени.
     *
     * @param startTime Дата и время начала диапазона.
     * @param endTime Дата и время окончания диапазона.
     * @return Список записей CDR, соответствующих заданным параметрам.
     */
    List<CDR> findByStartTimeBetween(LocalDateTime startTime, LocalDateTime endTime);
}
