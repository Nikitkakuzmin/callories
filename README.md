🍽 REST API для учета калорий  

Этот сервис позволяет пользователям:  
**Рассчитывать дневную норму калорий** (по формуле Харриса-Бенедикта)  
**Добавлять приемы пищи**  
**Отслеживать потребленные калории**  
**Просматривать историю питания**  

**Java 17 (JetBrains Runtime 17.0.6)**  
**Spring Boot 3.4.3**  

**Сервер:** `6932`  
**База данных:** `1488` 

Запустить  docker-compose.yml - ввести команду docker-compose up -d - сразу запустится и приложение, и база данных

Всвязи с тем , что используется формула Харриса-Бенедикта, добавил выбор гендера.

API Эндпоинты:

Доступные цели: (LOSS, MAINTENANCE, GAIN)
LOSS – похудение
MAINTENANCE – поддержание веса
GAIN – набор массы

 Пользователи (Users):
 Создать пользователя:
 POST 
 Body-raw-json
 http://localhost:6932/users
{
  "name": "Иван",
  "email": "ivan@mail.ru",
  "age": 28,
  "weight": 75.0,
  "height": 180.0,
  "goal": "MAINTENANCE",
  "gender": "MALE"
}
{
  "name": "Алина",
  "email": "alina@mail.ru",
  "age": 23,
  "weight": 92.0,
  "height": 150.0,
  "goal": "LOSS",
  "gender": "FEMALE"
}

Ответ (201 Created)
{
  "id": 1,
  "name": "Иван",
  "email": "ivan@example.com",
  "age": 28,
  "weight": 75.0,
  "height": 180.0,
  "goal": "MAINTENANCE",
  "gender": "MALE",
  "dailyCalorieNorm": 1800.0
}
/////////////////////////////////////////////////////

Получить пользователя по ID:
GET
http://localhost:6932/users

 Ответ (200 OK)

 {
  "id": 1,
  "name": "Иван",
  "email": "ivan@example.com",
  "age": 28,
  "weight": 75.0,
  "height": 180.0,
  "goal": "MAINTENANCE",
  "gender": "MALE",
  "dailyCalorieNorm": 1800.0
}

////////////////////////////////////////////////////////

Блюда (Dishes):
Добавить блюдо:
POST 
 Body-raw-json
 http://localhost:6932/dishes

 {
  "name": "Дошик",
  "calories": 150,
  "proteins": 5.0,
  "fats": 3.0,
  "carbohydrates": 27.0
}
{
  "name": "Роллтон",
  "calories": 120,
  "proteins": 9.0,
  "fats": 12.0,
  "carbohydrates": 32.0
}
{
  "name": "Мастер Кан",
  "calories": 260,
  "proteins": 15.0,
  "fats": 32.0,
  "carbohydrates": 11.0
}

 Ответ (201 Created)

  {
  "name": "Дошик",
  "calories": 150,
  "proteins": 5.0,
  "fats": 3.0,
  "carbohydrates": 27.0
}

///////////////////////////////////////////////////////////

Получить все блюда:
GET
http://localhost:6932/dishes

Ответ (200 OK)
{
  "name": "Дошик",
  "calories": 150,
  "proteins": 5.0,
  "fats": 3.0,
  "carbohydrates": 27.0
}
{
  "name": "Роллтон",
  "calories": 120,
  "proteins": 9.0,
  "fats": 12.0,
  "carbohydrates": 32.0
}
{
  "name": "Мастер Кан",
  "calories": 260,
  "proteins": 15.0,
  "fats": 32.0,
  "carbohydrates": 11.0
}

///////////////////////////////////////////////////////////////

Прием пищи (Meals):
POST 
 Body-raw-json
 http://localhost:6932/meals/{userId}
  {
  "dishes": [
    { "id": 1 },
    { "id": 2 }
  ]
}
 Ответ (201 Created)

 {
  "id": 1,
  "user": {
    "id": 1,
    "name": "Иван"
  },
  "dishes": [
    {
      "id": 1,
      "name": "Овсянка",
      "calories": 150
    },
    {
      "id": 2,
      "name": "Курица с рисом",
      "calories": 400
    }
  ],
  "date": "2025-03-18"
}

////////////////////////////////////////////////////////////////

Получить отчет за день:
GET
http://localhost:6932/meals/{userId}/report?date=YYYY-MM-DD (Дата во всех апи будет ставиться сама, здесь нужно будет подставить дату когда добавлялись meals)
http://localhost:6932/meals/1/report?date=2025-03-18(Пример)

Ответ (200 OK)

{
  "date": "2025-03-18",
  "consumedCalories": 550,
  "withinNorm": true
}

//////////////////////////////////////////////////////////////

Получить историю питания за период
GET
http://localhost:6932/meals/{userId}/history?startDate=YYYY-MM-DD&endDate=YYYY-MM-DD
http://localhost:6932/meals/1/history?startDate=2025-03-01&endDate=2025-03-10(пример)

Ответ (200 OK)

[
  {
    "date": "2025-03-01",
    "consumedCalories": 1800,
    "withinNorm": true
  },
  {
    "date": "2025-03-02",
    "consumedCalories": 2200,
    "withinNorm": false
  }
]

///////////////////////////////////////////////////

Пользователь не найден:
GET 
http://localhost:6932/users/999

Ответ (404):

{
  "status": 404,
  "error": "User not found"
}

////////////////////////////////////////////////


 
