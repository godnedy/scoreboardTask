# scoreboardTask

In order to use this library, you have to create GameServiceImpl instance with any GameStorage implementation (in this simple project I've used InMemoryGameStorage). 

I've decided to return already sorted results from the Storage, but if you want to sort it after fetching games in service, you have to use GameComparator there.

I've used RuntimeException for other exceptions than IllegalArgumentException, but if I had more time I'd implement a custom exception here.

For service layer I've commited the changes using TDD, for other classes I've commited all the changes together because I didn't want to spend additional time on it.