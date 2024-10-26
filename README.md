# scoreboardTask

In order to use this library, you have to create GameServiceImpl instance with any GameStorage implementation (in this simple project I've used InMemoryGameStorage). This implementation should return games that are already sorted by totalScore (or startTime if totalScore is equal).

I've decided to save games that are already sorted in Storage implementation, but if you want to sort it after fetching games, I would implement it using this code: 

``       
``

I've used RuntimeException in case the game for the given teams is already started, but if I have more time I'd implement CustomException here.

There might be additional try catch in end method if the game does not exist.
