# nexignTest

Решение представляет собой серверное приложение, которое генерирует CDR записи в локальной БД H2, позволяет с помощью HTTP-методов получать список всех записей, записи по конкретному абоненту, создавать CDR отчет в txt файле. Для удобства тестирования используется swagger-ui.

## Инструкция для запуска  
git clone https://github.com/Vl4ddd/nexignTest  
cd .\nexignTest\service  
mvn clean install    
mvn spring-boot:run 

Сервис запускается на localhost:8080

## Endpoints 
Post /api/v1/cdr/generate  
Get /api/v1/udr/all  
Get /api/v1/udr/subscriber  

## Swagger
/swagger-ui/index.html

