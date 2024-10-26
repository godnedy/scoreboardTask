# scoreboardTask

In order to use this library, you have to create GameServiceImpl instance with any GameStorage implementation (in this simple project I've used InMemoryGameStorage). This implementation should return games that are already sorted by totalScore (or startTime if totalScore is equal).