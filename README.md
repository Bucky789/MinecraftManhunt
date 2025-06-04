🧭 Minecraft Manhunt Plugin (1.16.1)
A custom Minecraft plugin that brings Dream-style Manhunt gameplay to your local Spigot server! One player is the Speedrunner, and the rest are Hunters equipped with compasses that track the runner — even in the Nether.

🎮 Features
🔄 Automatically assigns 1 speedrunner and multiple hunters
🧭 Hunters receive compasses that track the speedrunner
(Compass works in both Overworld and Nether using Lodestone mechanics)
💀 Hunters automatically receive a new compass when they respawn
📍 /startmanhunt command to start the game with online players
♻️ Resets player gamemodes and teleports everyone to the starter

📦 Requirements:
1)Minecraft version: 1.16.1
2)Server type: Spigot
3)Java version: Java 8+
4)Recommended IDE: Eclipse or IntelliJ

🚀 Setup Instructions:
1)Clone this repository
2)Import the project into Eclipse (or any IDE that supports Maven/Spigot)
3)Compile and export the plugin as a .jar file
4)Place the jar into your Spigot server’s /plugins folder
5)Run the server and use /startmanhunt to play!

⚙️ Commands:
Command	Description
/startmanhunt	Randomly assigns roles and begins the game

📌 Notes
1)Compass tracking in the Nether uses Lodestone API to simulate pointing
2)Make sure both the speedrunner and hunter are in the same world for compass updates
3)Tested on Spigot 1.16.1 — compatibility with other versions may vary

🧠 Inspiration
Inspired by Dream’s Manhunt series on YouTube — built by a Minecraft gamer who wanted to try coding for fun!

