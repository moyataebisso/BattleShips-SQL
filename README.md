# Project-1: Battle Ships Board Game

Battle Ships is a classic board game where boats are randomly placed on a grid, and the player must guess their locations in the least number of turns. The game includes multiple difficulty levels (Beginner, Intermediate, Expert) and special abilities like missiles, drones, and submarines to make the gameplay more strategic and engaging.

## Features
- **Randomized Boat Placement**: Boats are placed dynamically based on the selected difficulty level.
- **Interactive Gameplay**: Players can fire at coordinates, use special abilities, and receive feedback for hits, misses, and sunk boats.
- **Debug Mode**: Developers can enable debug mode to view the board's internal state for testing and troubleshooting.
- **Database Integration**: Game data, such as player actions and game states, is logged to a MySQL database for tracking and analysis.

## MySQL Integration
The game is integrated with a MySQL database to store and manage gameplay data. The `game_state` table tracks the start, progress, and end states of the game. The database connection is managed using Java Database Connectivity (JDBC), with SQL operations to insert, update, and retrieve data dynamically during gameplay. This integration ensures seamless backend functionality and real-time data recording.

### Setting Up the Database
1. **Database Configuration**: Create a MySQL database named `game_database` and a table named `game_state` to store game logs.
2. **Connector Setup**: Ensure the MySQL Connector for Java is properly added to the project's classpath for database communication.
3. **Logging Gameplay**: The game automatically logs start and end events, as well as important actions, into the `game_state` table.

## How to Compile and Run
1. Ensure you have the required dependencies set up, including the MySQL Connector for Java.
2. Use the following Maven command to clean and build the project:



