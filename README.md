# 🛒 Market-Manager

> This project is a simple market management application developed in Java. It allows users to manage products, customers, and orders efficiently. 

---

## 📑 Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Installation](#installation)
- [Team](#team)
- [Licence](#licence)

---

## 🎯 Introduction
**Market-Manager** is designed to help small businesses keep track of their inventory for the Staff team, manage customer information for Admins, and process orders seamlessly for Clients. The application is built for CLI (Command Line Interface).

---

## ✨ Features
| Feature | Description |
|---------|-------------|
| 🔐 **Authentication** | Users can log in with their credentials to access the system with saved JSON files. |
| 🛡️ **Security** | Passwords are securely hashed using the **BCrypt** library to ensure user data protection. |
| 👥 **Permissions** | Role-based access control with different levels for Admin, Staff, and Client users. |
| 👑 **Admin** | Can add/remove users, list them, change passwords, usernames, and permissions. |
| 🏪 **Staff** | Can add, remove, list products, and change name, price, or quantity of products. |
| 🛍️ **Client** | Can add products to their cart, remove them, list them, and place orders. |
| ❓ **Common commands** | All users can use `help` to list commands, `exit` to quit, `clear` the console, and view the stock. |
| 💾 **Data Persistence** | All data is stored in JSON files (user info, product details, user carts). |

---

## Installation
Requirements:
- Java Development Kit (JDK) 25 or higher
- Gson library
- Bcrypt library

**1. Clone the repository :**
```bash 
git clone https://github.com/Joshua31400/Market-Manager.git
cd Market-Manager
```

**2. Run the project :**
```
mvn -q compile exec:java
```

---

## Team

Realized by :

- **Pedro MARTINS** - [@pmartins22](https://github.com/pmartins22)
- **Tom PASSERMAN** - [@tompass](https://github.com/tompass)
- **Lucas KOCHEIDA** - [@lucas-aww](https://github.com/lucas-aww)
- **Joshua BUDGEN** - [@joshua31400](https://github.com/joshua31400)

*Ynov Campus Toulouse - 2026*

---

## Licence

This project was realized by the students of Ynov Campus Toulouse as part of their curriculum. It is intended for educational purposes only and is not licensed for commercial use.
 
