# 🛡️ MyLocker - Digital Document Vault

**MyLocker** is a secure, full-stack web application designed for digital document management. It allows users to register, login, and securely store their important documents. The project features a unique external file storage system to ensure data persistence and a built-in QR code generator for instant candidate verification.

---

## 🚀 Key Features

* **🔒 Secure Authentication:** Built with Spring Security to ensure user data and documents are protected.
* **📂 Persistent External Storage:** Documents are stored in an external directory (`D:/Interview/My-locker-storage/`), preventing data loss during application restarts or redeploys.
* **📊 Dynamic Dashboard:** User-friendly interface to upload, view, and manage documents seamlessly.
* **🔳 QR Code Integration:** Automatically generates a unique QR code for each user profile for quick identification.
* **⚡ Real-time Verification:** Integrated verification tokens for secure user registration and password resets.
* **📄 PDF & Image Support:** Robust file handling for various document formats.

---

## 🛠️ Tech Stack

* **Backend:** Java 21, Spring Boot 3.x
* **Database:** MySQL / H2 Database
* **ORM:** Spring Data JPA (Hibernate)
* **Frontend:** HTML5, CSS3, JavaScript, Thymeleaf
* **Security:** Spring Security
* **Build Tool:** Maven

---

## 📁 Project Architecture

The application follows a clean MVC (Model-View-Controller) architecture:
* **Model:** Handles data entities and JPA mappings.
* **Controller:** Manages HTTP requests and business logic flow.
* **Service:** Handles file storage operations and secure processing.
* **Repository:** Interface for database interactions.

---

## ⚙️ Installation & Setup

1. **Clone the repository:**
   ```bash
   git clone [https://github.com/Vivek-k01/My-Locker.git](https://github.com/Vivek-k01/My-Locker.git)
