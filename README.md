# VIRTUAL PET

Final project of the IT Academy Java & SpringBoot Bootcamp.
The main goal of this activity is to connect a SpringBoot API REST with a frontend using the help of artificial intelligence. 

This repository includes:

- a backend project
- a frontend project
- a PDF file with the presentation
  

## :hammer_and_wrench: Tools
- Java, Maven, SpringBoot, MySQL, Node, React.
  

## :otter: Virtual Pet Application Description

The Virtual Pets Application allows users to care for and nurture their own virtual pets. It offers the following features:

### Register: 
Allows new users to sign up by creating a username and password.

### Login:
Allows existing users to log in to the application using their username and password to obtain a JWT token.

### Create:
Enables users to create new virtual pets to care for and nurture. They can choose from a variety of creatures. Users can then customize their pet’s color, name, and unique characteristics.

### Read:
Displays all existing virtual pets. Users can interact with their pets, check their mood, energy levels, and needs.

### Update:
Allows users to care for and customize their virtual pets. They can feed them, play with them, purchase fun accessories, and modify their virtual environment to keep their pets happy and healthy.

### Delete:
Gives users the option to remove virtual pets they no longer wish to care for. Once the deletion is confirmed, the pet is removed from the virtual environment, but users can create a new one at any time.


## :man_beard: :red_haired_woman: Roles Implementation

The roles implementation differentiates functionalities and permissions between regular users and administrators:

### User (ROLE_USER):
Has access only to their own virtual pets, allowing them to create, view, update, and delete their pets.

### Admin (ROLE_ADMIN):
Has access to all virtual pets in the system, enabling them to view, update, and delete pets regardless of ownership. The administrator can also access the data of all the registered users.
