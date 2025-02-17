# Tic-Tac-Toe Game

Java-based networked Tic-Tac-Toe implementation supporting real-time multiplayer battles.

## Features
-  2-player online battles
-  Real-time board sync
-  Win detection
-  Connection resilience

## Quick Start
1. **Compile**:
  
javac -d out TicTacToe/*.java

2. **Start server** (first terminal):

java -cp out TicTacToe.Server

3. **Launch clients** (separate terminals):

java -cp out TicTacToe.Client

## Tech Stack
Client: Java Swing GUI

Server: TCP sockets

Port: 327
