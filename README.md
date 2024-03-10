## Restaurant app for ordering food and providing a food control service for employees.

### Start:

1. To begin the project, you need to clone this repo. To do this, you need to run the command in the terminal:

```git
git clone https://github.com/Eraaalex/RestaurantApp.git
```

Or you can paste the following link (https://github.com/Eraaalex/RestaurantApp.git) into your IntelJ IDEA 
by clicking File -> New -> Project from Version Control.... Then paste the link and click Clone.

#### Docker

2. I definitely recommend using Docker to run the project. Just open console and run the following command:

```Docker
docker-compose build
docker-compose up
```

Then you can open the browser and paste the following link: http://localhost:8080/

To finish the work, you need to run the following command in the console:

```Docker
docker-compose down
```

Consider that you need to have Docker installed and Docker Daemon run on your computer.

#### Without Docker

2. Then you need to open the project in IntelJ IDEA and run the main method in the class `RestaurantApplication`.

It's important to note that the app is deployed on port: 8080. So, you need to make sure that this port is not used by another application.

3. Next, you need to open the browser and paste the following link: http://localhost:8080

4. Then the project is successfully deployed.

###  How to use app

1. It is important to note that the app has two roles: `ADMIN` and `USER`. The `ADMIN` role has the ability to add, delete and update dishes, also it has ability to add new users with role `ADMIN`.
The `USER` role has the ability to order dishes and see the list of all dishes and their prices.

2. To use the app, you need to register. To do this, you need input **_unique_** username and password. Then you can log in.

3. The first user who registers will have the roles `ADMIN` and `USER`. The next users will have the role `USER`.

4. In navbar, you may see all functionality of the app. You can make order.

If you want to check the db state I recommend to use the following pattern command in the terminal:

```Docker
docker-compose exec db psql -U postgres -d restaurant_app_db -c 'SELECT * FROM human;'
```

###  Features:

In addition to the condition that the first user is always an admin, 
it is worth clarifying the implementation of the interaction of cart models and orders. 
At the beginning of making an order, the cart is created. 
When the user adds a dish to the cart, the dish is added to the cart. 
All carts are temporary objects stored simply in the list, 
which is done intentionally so as not to overload the database in case of constant additions
of dishes to the order while it is being prepared or cancellations of orders. 
When the "cart" is prepared, it is converted into an order model and is already saved to the database.
When the user makes an order, the cart is converted to the order.

I also have AppControllerAdvice, which is responsible for handling exceptions and returning the appropriate response.


### Design patterns:

Adapter : To convert cart model to order model.

Singleton : To create only one instance of services etc.

Builder : To create complex DishOrder objects.

Observer : To notify that order completed.

**Why these patterns?**


In the context of services in a Spring Boot application, Singleton pattern is used by default for Spring Beans.

The builder pattern is used for greater code readability.

Adapter pattern is used to convert one model to another, so I chose it to convert cart model to order model.


### Stack:

Spring Boot, Spring Security, 
Spring Data JPA, PostgreSQL, Hibernate, 
Thymeleaf, HTML, 
CSS (a bit), 
Docker, 
Lombok, Gradle.






