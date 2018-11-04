# ECEN424_UNO

//YO CAN U GUYS SEE THIS

## Reese
- Server
  - Create card class that holds an enumerated 
  - Create lobby, requiring 3 players to join before playing
    - Once three have joined, set 15 second timer for new players to join
      - New player connecting resets timer

    


## Kyle
Game-Master

- Make draw card function
  - no input args, return card
  - generate random number between 0 and 107 (108 card deck)
    - Could also choose color and card type (1, draw 2, skip, etc.) using separate random numbers
  - make probability distribution to return card type
	
- Make Play card function
  - input card as argument, manipulate game state accordingly
    - Will need to meet to discuss how we format the game state (probably gets its own class)


## Rejath George
- Client (meet to talk about specifics)
  - connect to specified IP and port (fine to use main thread like MP2)
  - setup input stream reader, output print writer, and scanner (keyboard input) objects
    - get reader and writer from port
  - parse input from reader for game state and player's hand; display to console
    - activate scanner whenever it is users turn (contained w/i game state)
      - provide user input to server via writer
  - listen to from server for acceptance of card player
    - if refused, reopen scanner and send again
    - if accepted, wait for next normal game state
 
