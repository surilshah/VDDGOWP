## Voice Driven Dynamic Generation Of Web Pages

#Steps to Install and Run

- You need NetBeans IDE (https://netbeans.org/downloads/index.html) to run this project

- Download the files in the folder VDDGOWP

- Add the project in NetBeans where on adding, a prompt will occur telling you about "Project Problems"

- Click on the "Resolve Problems" button where you will have to choose the missing JAR files to resolve the problems from the VDDGOWP/JAR folder

- Follow the following steps to gain access to the "Google Speech to Text API" :-
	
	You have to be a member of chromium-dev discussion list before you can enable the Speech API in your Google Developers Console.
	
	- Go to Chromium Dev group (https://groups.google.com/a/chromium.org/forum/?fromgroups#!forum/chromium-dev) and click Join the list. Do not post to the group regarding the Google Speech API, as it is completely off topic.
	
	- Go back to Google Developers Console (https://console.developers.google.com/), select your project, enter APIs & Auth / APIs. You'll now see Speech API. Click to enable it.
	
	- Go to Credentials, Create new Key, Server Key. You may optionally specify a list of IPs, for security.
	
	You now may make queries to Google Speech API v2. Keep in mind that this is an experimental API, and limited to 50 queries per day per project.

- Once you have the key insert the key on line 49 of the SpeechToText.java file

- After doing the above steps you should be able to run the project if running on a MAC. If you are using a Windows machine you need to do one more thing before running the project :-

	- You need to change the file path format in all the java files by replacing '/' with '\\'.

	- The fastest way to do is by selecting "Replace in Projects" in the Edit Menu.

	- In 'Containing Text' enter "/src/finalyearproject/".

	- Enter "\\src\\finalyearproject\\" in 'Replace With'.

	- Select Current Project in 'Scope', then click on Continue.

	- Click on Replace and then you are ready to run the project.

- Now the project is ready to run. Click on the Play button in NetBeans or run the JavaFXApplication.java file