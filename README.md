# Vending Machine

College Project, May 2020

Github: https://github.com/endamccarthy/VendingMachine

Built using:
- **Java** - Version 13
- **JavaFX** - Version 14
- **Maven** - Version 3.6.3
- **IntelliJ IDEA** - 2020.1.1 (Community Edition)

## Setup Instructions
**On Mac**
- Download zip file ([target/VendingMachine.zip](target/VendingMachine.zip)) containing an executable file.
- Right click on downloaded (and un-zipped) folder and open a new terminal window.
- Type `sh bin/launch.sh` into the terminal and press enter.
- If app does not run because of unidentified developer, try this:
    - Open System preferences...
    - Security & Privacy
    - Under General tab, click where it says allow
    - try running again

**On Windows**
- Setup a new project in IntelliJ (or similar IDE) using the build attributes listed above.
- Copy java, css and dat files into the project.
- Run the project in the IDE.

## Run Instructions
- Login as a **customer**:
    - username: testC
    - password: 111111

- Login as an **admin**:
    - username: testA
    - password: 111111

## Classes Used
- VendingMachineSimulation (main)
- VendingMachine
- VendingMachineMenu
- CustomerMenu
- AdminMenu
- Product
- User
- Customer
- Admin
- FileInputService
- FileOutputService
- ConfirmMenu
- AlertBox

