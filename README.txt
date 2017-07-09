Project: Lorenzo Il Magnifico

Team members:
- Simone Crippa 833405
- Alessio Cantina 826946

We have implemented:
- Complete Rules
- Command Line Interface
- Socket

The project can be compiled using maven plugin, the main classes are :
- Client side -> it.polimi.LM39.client.SartGame
- Server side -> it.polimi.LM39.server.Server

Configuration files can be found in jsonfiles/config folder.
- Default player move timeout: 600 seconds
- Default room start timeout: 300 seconds

The server uses port 3421 it can be manually edited in 
it.polimi.LM39.server.Server at line 15 (SOCKET_PORT)