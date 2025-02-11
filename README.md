<h1 style="text-align: center">
    Shopmate
</h1>

The Shopmate E-commerce Backend is a robust RESTful API built with Spring Boot and PostgreSQL. It supports core
e-commerce features, such as product catalog management, shopping carts, wishlists, and order processing. The
application is designed to handle secure authentication and high scalability, making it ideal for powering online
stores.

## Features

- Product Management: Create, read, update, and delete products with attributes such as name, description, price, and
  brand.
- Cart and Wishlist Management: Add, remove, and view products in user-specific carts and wishlists.
- Order Processing: Manage order placement, order items, and order status.
- User Authentication and Authorization: Secure registration and login using JWT.
- Category and Brand Management: Organize products into categories and brands for easy navigation.
- Scalable Design: Optimized for performance with a well-structured data model.

## Tech Stack

- Backend Framework: Spring Boot
- Database: PostgreSQL
- Authentication: JWT (JSON Web Token)
- Build Tool: Maven

## Project Structure

```
/shopmate
  ├── logs                     # Application log files
  ├── src
  │   └── main
  │       ├── java
  │       │   └── dev.shashiirk.shopmate
  │       │       ├── aop             # Aspect-Oriented Programming (cross-cutting concerns)
  │       │       ├── config          # Configuration files
  │       │       ├── context         # Application-specific contexts (e.g., initializations)
  │       │       ├── controller      # REST controllers for handling API requests
  │       │       ├── domain          # Entity classes representing database tables
  │       │       ├── dto             # Data transfer objects (request/response)
  │       │       ├── enumeration     # Enumerations for fixed sets of values
  │       │       ├── exception       # Custom exceptions and handlers
  │       │       ├── mapper          # Mappers for entity-to-DTO conversion
  │       │       ├── repository      # Interfaces for database interactions
  │       │       ├── security        # Security configurations and JWT handling
  │       │       ├── service         # Business logic implementation
  │       │       ├── util            # Utility classes and helper functions
  │       │       └── ShopmateApplication  # Main application entry point
  │       └── resources
  │           ├── application.yml    # Application configuration
  │           ├── application-dev.yml    # Application configuration (Dev)
  │           ├── application-prod.yml    # Application configuration (Prod)
  │           └── data.sql         # Initial Data
  ├── target                    # Compiled bytecode and generated files
  ├── .gitignore                # Ignored files in version control
  ├── pom.xml                   # Maven build and dependency configuration
```

## ER Diagram

The following diagram represents the relationships between key entities such as users, products, orders, carts, and
more.

![ER Diagram](https://github.com/user-attachments/assets/5688b1e1-4268-4943-81ef-af323cca018a)

## License

This project is licensed under [MIT](LICENSE).
