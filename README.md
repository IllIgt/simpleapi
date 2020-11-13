[![Build Status](https://travis-ci.com/IllIgt/simpleapi.svg?branch=master)](https://travis-ci.com/IllIgt/simpleapi)

## Технологии разработки программного обеспечения

### Лабораторная работа №1: создание микросервиса на Spring Boot с базой данных

**Ратников Алексей Михайлович 3МБД2001**

Цель: знакомство с проектированием многослойной архитектуры Web-API (веб-приложений, микро-сервисов).

## Сборка и запуск

### Клонировать проект

`git clone git@github.com:IllIgt/simpleapi.git`

### Сборка проекта

```
cd ./simpleapi
mvn package -Dmaven.test.skip=true
```
Собранный проект находится в директории `./target` проекта simpleapi

### Сборка Docker образа
Сборка образа выполняется из корневой директории проекта, 
либо с указанием пути до директории,
содержащей Dockerfile

`docker build . -t simpleapi:latest`

### Запуск Docker контейнера
Запуск контейнера осуществляется с указанием 
о перенаправлении обращений к порту 8080 хоста 
на порт 8080 контейнера

`docker run -p 8080:8080 simpleapi:latest`

### Endpoints

**HEROKU**

`http://ratnikovsimpleapi.herokuapp.com/api/v1/`

**LOCALHOST**

`http://localhost:8080/api/v1/`

1. Получение имени хоста `curl -X GET */status`

2. Получение списка студентов
`curl -X GET */students`

3. Получение информации о студенте по id
`curl -X GET */students/{id}`

4. Добавление информации о студенте
`curl -X POST */students -d '{"name": "Имярек", "surname": "Имярекович", "groupId": 2}' -H "Content-Type: application/json"
`
    - *not required*:
      - groupId
5. Удаление информации о студенте по id
`curl -X DELETE */students/{id}`

6. Получение всех групп студентов
`curl -X GET */groups`

7. Получение информации о группе по id
`curl -X GET */groups/{id}`

8. Получение информации о студентах конкретной группы
`curl -X GET */groups/{id}/students`

9. Получение информации о курсах конкретной группы
`curl -X GET */groups/{id}/courses`

10. Добавление новой группы
`curl -X POST */groups -d '{"code": "МБМД2020", "specialization": "Информационные системы", "studentsId": [1, 2, 3,], "coursesId": [1, 7, 12]}' -H "Content-Type: application/json"
`
      - *not required*:
        - studentsId
        - coursesId
    
11. Удаление группы по id
`curl -X DELETE */groups/{id}`

12. Добавление нового курса
`curl -X POST */courses -d '{"code": "ЛААГ2", "name": "Линейная алгебра и аналитиечская геометрия", "elestive": false, "hours": 72, "groupId": 2}' -H "Content-Type: application/json"
`
      - *not required*:
        - groups
        
13. Получение списка курсов
`curl -X GET */courses`

14. Получение конкретного курса по id
`curl -X GET */courses/id`

15. Получение списка групп конкретного курса
`curl -X GET */courses/{id}/groups`

16. Удаление курса по id
`curl -X DELETE */courses/{id}`


[**Лабораторная работа №2**](https://github.com/IllIgt/simpleapi/blob/master/Kubernetes.md)



### Лабораторная работа №3: CI/CD и деплой приложения в Heroku

Цель: знакомство с CI/CD и его реализацией на примере Travis CI и Heroku

[**HEROKU**](https://ratnikovsimpleapi.herokuapp.com/api/v1/status)

### Extras

[**Sonar code analyze**](https://sonarcloud.io/dashboard?id=IllIgt_simpleapi)