# Инструкция по запуску и использованию

## Требования

Для запуска понадобится **API ChatGPT** и **Docker**.
> ⚠️ Для пользователей из Беларуси и России может потребоваться **VPN**.

---

## Установка и запуск

#### 1. Установите API key для ChatGPT в файле

`src/main/resources/env.properties`  
в формате:

   ```
   properties
   OPEN_AI_KEY=real_key
   ```

#### 2. Запустите приложение при помощи docker-compose:

`docker-compose up`

#### 3. После запуска база данных будет доступна по адресу:

   ```
   jdbc:postgresql://localhost:5432/mydatabase
   user: myuser
   password: mypassword
   ```

    (если вы не измените docker-compose.yml и application.properties).

## Полезные ссылки

- **Презентация:**  
  [Google Slides](https://docs.google.com/presentation/d/1UI6TTUh7HDTQoTWl30wigrqNTBrLXFoTKKbIV_24SYc/edit?usp=sharing)

- **Основное приложение:**  
  [GitHub - Khodyko/mcp](https://github.com/Khodyko/mcp)

- **Mcp client:**  
  [GitHub - Khodyko/mcpClient](https://github.com/Khodyko/mcpClient)

---

## HTTPie примеры запросов

Для выполнения запросов через httpie установите утилиту **HTTPie**:  
[https://httpie.io/docs/cli](https://httpie.io/docs/cli)

---
### 1. Наполнение базы данных

**HTTPie:**
```bash

http GET http://localhost:8080/ai/support/12/orchestrator/simple question=="Загрузи пользователей и продукты из базы данных"
```

---

### 2. Получение продукта

**HTTPie:**
```bash

http GET http://localhost:8080/ai/support/12/orchestrator/simple question=="Найди название кофе"
```

```bash

http GET http://localhost:8080/ai/support/12/orchestrator/simple question=="Найди название чего-то сладенького"
```
---

### 3. Заказ продукта по имени
(ошибочно вызовет тул с загрузкой из БД)
**HTTPie:**
```bash

http GET http://localhost:8080/ai/support/12/orchestrator/simple question=="закажи 44 Espresso"
```
---

### 4. Определение имени, а потом заказ
(ошибочно вызовет тул с загрузкой из БД)

**HTTPie:**
```bash

http GET http://localhost:8080/ai/support/12/orchestrator/simple question=="закажи мне напиток со вспененным молоком 106 штук"
```
---

### 5. Вызов через client (порт 9494, должен быть запущен  проект   [GitHub - Khodyko/mcpClient](https://github.com/Khodyko/mcpClient)
) 

**HTTPie:**
```bash

http GET http://localhost:9494/call question=="найди описание кофе"
```

```bash

http GET http://localhost:9494/call question=="Найди название чего-то сладенького"
```
---

### 6. Вызов через AiBasedOrchestrator

**HTTPie:**
```bash

http GET http://localhost:8080/ai/support/12/orchestrator/aibased question=="закажи мне напиток со вспененным молоком 106 штук"
```
---

### 7. Вызов через ToolBasedOrchestrator

**HTTPie:**
```bash

http GET http://localhost:8080/ai/support/12/orchestrator/toolbased question=="закажи мне напиток со вспененным молоком 106 штук"
```