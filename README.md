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
Собранный проект находится в директории `./target` проекта simpleape

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

1. Получение имени хоста `curl -X GET http://127.0.0.1:8080/api/v1/status`

2. Получение списка студентов
`curl -X GET http://127.0.0.1:8080/api/v1/students`

3. Получение информации о студенте по id
`curl -X GET http://127.0.0.1:8080/api/v1/students/1`

4. Добавление информации о студенте
`curl -X POST http://127.0.0.1:8080/api/v1/students -d '{"name": "Имярек", "surname": "Имярекович", "group_id": 112}' -H "Content-Type: application/json"
`
5. Удаление информации о студенте по id
`curl -X DELETE http://127.0.0.1:8080/api/v1/students/3`


[**Лабораторная работа №2**](https://github.com/IllIgt/simpleapi/blob/master/Kubernetes.md)