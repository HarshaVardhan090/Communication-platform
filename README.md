# 🌐 [Next Gen Communication Platform With Intilligent Colloboration and Enhanced Privacy]

## 📝 Project Description
**Project Name:** **NextGen Communication Platform with Intelligent Collaboration and Enhanced Privacy**

The **NextGen Communication Platform with Intelligent Collaboration and Enhanced Privacy** is a communication platform similar to WhatsApp that enables users to exchange messages, share large files, and collaborate effectively through **Communities** and **Channels**.

The project was developed using **Java, Spring Boot, MySQL, HTML, CSS, and JavaScript**.

We developed this project to address the limitations of existing messaging applications, such as restricted file sharing, limited collaboration features, inadequate search functionality, weak privacy management, and the lack of intelligent communication features.

Our platform overcomes these limitations by providing:

* Large file sharing of up to **1 GB**
* End-to-end encryption for secure communication
* Communities with multiple channels for better collaboration
* Advanced message search and filtering
* Flexible storage options
* Role-based access control for secure user management

Due to limited time and resources, we focused on implementing the core architecture of the platform. The completed modules include:

* User Management
* Communities
* Channels
* Role-Based Access Control (RBAC)
* File Sharing
* Search Module
* Messaging Module

---

## 🛠️ Tech Stack
* **Frontend:** Html,Css,JavaScript
* **Backend:** Java,Spring Boot(Spring data jpa,Spring mvc)
* **Database:** MySql(Relational Schema Design, Query Optimization)
* **Tools & IDEs:** IntelliJ IDEA , Git, GitHub, Apache Maven, Postman (API Testing)

---

## 📂 Project Directory Structure

The backend is architected using a standard Spring Boot layered design. It cleanly decouples network entry points (Controllers), business intelligence logic (Services), and database abstractions (Repositories).
```text
root/
├── .mvn/                                      # Maven wrapper configuration settings
├── mvnw                                       # Maven wrapper script for Linux/macOS
├── mvnw.cmd                                   # Maven wrapper script for Windows
├── pom.xml                                    # Maven project configuration and dependencies
├── .gitattributes                             # Git configuration settings
├── .gitignore                                 # System paths and caches to ignore
└── src/                                       # Main application source directory
    ├── main/                                  # Production source code core
    │   ├── java/com/project/Communication/platform/
    │   │   ├── Controller/                    # REST API Entry Points
    │   │   │   ├── ChannelController.java     # Controls sub-channel creation & feeds
    │   │   │   ├── CommunityController.java   # Manages team workspaces & networks
    │   │   │   ├── FileController.java        # Manages secure data uploads & transfers
    │   │   │   ├── MessageController.java     # Directs secure text messaging flows
    │   │   │   ├── RoleController.java        # Processes security access modifications
    │   │   │   ├── SearchController.java      # Routes contextual data lookups
    │   │   │   └── UserController.java        # Manages user accounts & authentications
    │   │   │
    │   │   ├── Service/                       # Business Logic & Core Analytics Layer
    │   │   │   ├── ChannelService.java        # Validates multi-channel isolation business logic
    │   │   │   ├── CommunityService.java      # Manages systemic workspace associations
    │   │   │   ├── FileStorageService.java    # Handles byte validation & storage targets
    │   │   │   ├── MessageService.java        # Applies privacy logic to communications
    │   │   │   ├── RoleService.java           # Computes role permissions matrix checks
    │   │   │   ├── SearchService.java         # Runs data queries against indexed tables
    │   │   │   └── UserService.java           # Processes profile creation & credential security
    │   │   │
    │   │   ├── Entity/                        # JPA Database Domain Entities
    │   │   │   ├── Channel.java               # Database relational mapping for channels
    │   │   │   ├── Community.java             # Database relational mapping for workspaces
    │   │   │   ├── FileDetails.java           # Stores size metadata and file pointers
    │   │   │   ├── Message.java               # Handles core communication schemas
    │   │   │   ├── User.java                  # Stores identity data and baseline state
    │   │   │   └── UserRole.java              # Maps security authority roles to users
    │   │   │
    │   │   ├── dto/                           # Data Transfer Objects (Payload Validation)
    │   │   │   ├── AccessCheckResponse.java   ├── ChannelRequest.java
    │   │   │   ├── ChannelResponse.java       ├── CommunityRequest.java
    │   │   │   ├── CommunityResponse.java     ├── FileResponse.java
    │   │   │   ├── LoginRequest.java          ├── MessageRequest.java
    │   │   │   ├── MessageResponse.java       ├── RegisterRequest.java
    │   │   │   ├── RoleResponse.java          ├── RoleUpdateRequest.java
    │   │   │   ├── SearchResponse.java        └── UserResponse.java
    │   │   │
    │   │   ├── repository/                    # Spring Data JPA Query Repositories
    │   │   │   ├── ChannelRepository.java     # Executes persistent channel operations
    │   │   │   ├── CommunityRepository.java   # Runs data lookups for platform communities
    │   │   │   ├── FileDetailsRepository.java # Updates transactional file parameters
    │   │   │   ├── MessageRepository.java     # Stores and retrieves messaging data
    │   │   │   └── UserRepository.java        # Checks database records for user records
    │   │   │
    │   │   ├── exception/                     # Global Functional Fault Handlers
    │   │   │   ├── ChannelNotFoundException.java
    │   │   │   ├── CommunityNotFoundException.java
    │   │   │   ├── EmailAlreadyExistsException.java
    │   │   │   ├── FileNotFoundException.java
    │   │   │   ├── FileStorageException.java
    │   │   │   ├── GlobalExceptionHandler.java # Catches uncaught runtime errors application-wide
    │   │   │   ├── InvalidCredentialsException.java
    │   │   │   ├── InvalidRoleException.java
    │   │   │   ├── MessageNotFoundException.java
    │   │   │   └── UserNotFoundException.java
    │   │   │
    │   │   └── CommunicationPlatformApplication.java # Spring Boot Primary Initializer
    │   │
    │   └── resources/                         # Infrastructure & App Configurations
    │       ├── static/                        # Frontend UI Client Assets
    │       │   ├── css/                       
    │       │   │   └── style.css              # Global platform theme and interface styles
    │       │   ├── js/                        # Asynchronous Business Logic Handlers
    │       │   │   ├── auth.js                # Manages user signup, login, and sessions
    │       │   │   ├── channel.js             # Controls channel streams and UI rendering
    │       │   │   ├── common.js              # Houses shared helper functions and base setups
    │       │   │   ├── community.js           # Handles workspace network switching logic
    │       │   │   ├── file.js                # Directs file drop operations and progress bars
    │       │   │   ├── message.js             # Feeds live text chat records into DOM panels
    │       │   │   └── search.js              # Handles search input event listeners and queries
    │       │   └── pages/                     # Application Interface Layouts
    │       │       ├── channels.html          # Individual text/topic feed UI
    │       │       ├── communities.html       # Parent server/workspace management hub
    │       │       ├── dashboard.html         # Main user dashboard interface layout
    │       │       ├── files.html             # Document sharing and library layout
    │       │       ├── login.html             # Secure user portal interface entry
    │       │       ├── messages.html          # Direct message (DM) interface view
    │       │       ├── register.html          # New profile user registration form
    │       │       └── search.html            # Global deep message filtering panel
    │       │
    │       ├── index.html                     # Default base page entry point
    │       └── application.properties         # Server ports, local DB details, and flags
    │
    └── test/java/com/project/Communication/platform/ # Automated Testing Core Framework
```
----
### 🧩 Architectural Layer Breakdown

*   **`Controller` Layer:** Operates as your system entry point. These classes manage HTTP requests and endpoints without knowing *how* the underlying logic computes.
*   **`Service` Layer:** The operational brain. This layer coordinates your **intelligent collaboration tools** and invokes the privacy scripts before passing anything to storage.
*   **`Entity` & `Repository` Layers:** The persistent database foundation. Entities structure your schemas securely, while Repositories perform safe database read/write queries.
---
---

## ⚙️ How to Run Locally

### 🛠️ Tools Required
* **Java:** JDK 17 or higher
* **Database Management:** MySQL Workbench
* **IDE:** IntelliJ IDEA

### 🚀 Steps to Run
1. **Create Database:** Open MySQL Workbench and create a new database named `communicationdb`.
2. **Open Project:** Launch IntelliJ IDEA, click **Open**, and select the project
3. **Configure Application Properties:** Open `src/main/resources/application.properties` inside IntelliJ and update your database credentials:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/communicationdb
   spring.datasource.username=your_mysql_username
   spring.datasource.password=your_mysql_password
   ```
4. **Start the Application:** Find `CommunicationPlatformApplication.java` in IntelliJ, right-click inside the file, and select **Run**.
5. **Access the App:** Open your web browser and navigate to `http://localhost:8080/pages/login.html`.


## 🚀 Future Enhancements
* Spring Security & JWT
* Web Sockets Integration
* Flexible Storage with both Cloud and Local Storage
* End to End Encryption
* AI-Driven Intelligent Collaboration
