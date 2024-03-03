### Restaurant app for ordering food and providing a food control service for employees.

#### Start:

1. To begin the project, you need to clone this repo. To do this, you need to run the command in the terminal:

```git
git clone https://github.com/Eraaalex/RestaurantApp.git
```

Or you can paste the following link (https://github.com/Eraaalex/RestaurantApp.git) into your IntelJ IDEA 
by clicking File -> New -> Project from Version Control.... Then paste the link and click Clone.

# Docker

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

# Without Docker

2. Then you need to open the project in IntelJ IDEA and run the main method in the class `RestaurantApplication`.

It's important to note that the app is deployed on port: 8080. So, you need to make sure that this port is not used by another application.

3. Next, you need to open the browser and paste the following link: http://localhost:8080

4. If you see the following message: "Welcome to the restaurant app!", then the project is successfully deployed.

For the reason that lack of time, I didn't have time to implement the pleasant front-end part of the project (even Bootstrap).


