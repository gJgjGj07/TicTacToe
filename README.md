# Multiplayer Tic-Tac-Toe with Java Swing

[![Java Version](https://img.shields.io/badge/Java-16%2B-orange)](https://www.oracle.com/java/technologies/)

Online tic-tac-toe game supporting real-time multiplayer battles via socket communication, featuring responsive Swing GUI.

## 🚀 Features
- 🖥️ Cross-platform Java Swing interface
- 🔌 Persistent TCP socket connection (Port 327)
- 🤖 Game state synchronization through server arbitration
- 🏆 Win detection with three different patterns

## ⚙️ Architecture
```plaintext
Client (GUI)
  │
  │ Via Socket (127.0.0.1:327)
  ▼
Server (Game Logic)
  ├── PlayerHandler 1
  └── PlayerHandler 2
