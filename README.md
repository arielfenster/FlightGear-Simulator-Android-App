# FlightGear-Simulator-Android-App
An Android application designed to fly a FlightGear aircraft using a custom joystick.

### Prerequities
* An Android environment to run the application

### Installing 
* Download and install the simulator on your computer- https://www.flightgear.org/download/
* Add the  generic_small.xml file to the /data/Protocol directory where you installed the simulator
* Config the following settings in the 'Settings' tab in the simulator:
```
--telnet=socket,in,10,127.0.0.1,5402,tcp
--generic=socket,out,10,127.0.0.1,5400,tcp,generic_small
```
![simulator-settings-config](https://user-images.githubusercontent.com/45856261/58368127-4a489680-7ef1-11e9-81ca-b17badca7f8e.PNG)

This will open two communication sockets - 'in' where you send commands to the simulator, and 'out' where you receive data from it.

## Flying the aircraft

### Connecting to the simulator
Run the simulator, click the 'Fly!' icon on the bottom and run the application. A login window will appear where you can enter connection IP and port:

![Login Screen](https://user-images.githubusercontent.com/45856261/63440676-f2aac000-c438-11e9-968e-88a527ef226b.PNG)

Notice how you can only enter numbers and dots.
In order to connect from your phone to the computer, enter the following details and click Connect:
```
IP: 10.0.2.2
Port: 5402 (corresponding to the port connected to the 'in' socket)
```
Once you are connected, the joystick screen will appear:

![JoystickView](https://user-images.githubusercontent.com/45856261/63444708-cd6d8000-c43f-11e9-9540-636905a91a65.PNG)


### Preparing to lift off
a. First, you may need to adjust the time in the simulator from nighttime to daytime in order to see clearly.
Click on the Environment tab in the toolbar shown below, and click on Time Settings. Adjust the time to your liking.

![Toolbar](https://user-images.githubusercontent.com/45856261/63440757-1241e880-c439-11e9-9623-ed96e7eae199.PNG)

b. Next, in order to help you speed things up and bypass the take-off procedures, click on the Cessna C172P tab, and click on Autostart. This will start the engine and prepare the aircraft to lift off.

### Controlling the aircraft
Now we are ready to take off!
Push the throttle to 100% to start accelerating:
![throttle_0%](https://user-images.githubusercontent.com/45856261/63443520-9a29f180-c43d-11e9-8cf3-4aa2cf522693.PNG)

![throttle_100%](https://user-images.githubusercontent.com/45856261/63443541-a746e080-c43d-11e9-89eb-d50054231692.PNG)

Use your joystick to maintain the aircraft in a straight line until you have gained enough speed and push down on the joystick to take off. Notice how moving the joystick causes the steering to move as well:

![moving1](https://user-images.githubusercontent.com/45856261/63445420-fe9a8000-c440-11e9-8e08-c83712977624.png)

![moving2](https://user-images.githubusercontent.com/45856261/63445419-fe01e980-c440-11e9-979d-840097604adf.png)


Enjoy your flight!
