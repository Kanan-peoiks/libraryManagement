# 📚 Library Management System (REST API)

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Spring Security](https://img.shields.io/badge/Spring%20Security-JWT-blue.svg)](https://spring.io/projects/spring-security)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue.svg)](https://www.postgresql.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

Müasir, təhlükəsiz və genişləndirilə bilən **İstifadəçi və Kitabxana İdarəetmə Sistemi** (Library Management System). Bu layihə **Spring Boot 3.x** və **PostgreSQL** bazasında qurulmuş RESTful API backend arxitekturasını ehtiva edir.

---

## 📑 Məzmun

1. [Layihə Haqqında](#-layihə-haqqında)
2. [Əsas Funksionallıqlar](#-əsas-funksionallıqlar)
3. [Texnologiya Steki](#-texnologiya-steki)
4. [Verilənlər Bazası Arxitekturası](#-verilənlər-bazası-arxitekturası)
5. [REST API Sənədləşməsi (Endpoints)](#-rest-api-sənədləşməsi-endpoints)
6. [Təhlükəsizlik və JWT Mexanizmi](#-təhlükəsizlik-və-jwt-mexanizmi)
7. [Quraşdırılma və İşə Salınma](#-quraşdırılma-və-işə-salınma)
8. [Problem Həlli (Troubleshooting)](#-problem-həlli-troubleshooting)
9. [Müəllif](#-müəllif)

---

## 🏛 Layihə Haqqında

**Library Management System** kitabxanaların rəqəmsal idarə edilməsi, kitab kataloqunun saxlanılması, müəlliflər üzrə kateqoriyalaşdırılması, üzvlərin borc götürmə (Borrow Orders) əməliyyatlarının izlənilməsi və rol əsaslı giriş hüquqlarının (RBAC) tənzimlənməsi üçün nəzərdə tutulmuş komplekt həlldir.

---

## ✨ Əsas Funksionallıqlar

### 🔑 İdentifikasiya və Autorizasiya (Auth Engine)
* **İstifadəçi Qeydiyyatı və Giriş:** JWT (JSON Web Token) vasitəsilə stateless autentifikasiya.
* **Rol Əsaslı Giriş (RBAC):**
    * `USER`: Kitabları nəzərdən keçirmək, axtarış etmək, borc götürmə sifarişi yaratmaq.
    * `ADMIN`: Kitab, Müəllif, Üzv və Borc Sifarişləri üzərində tam CRUD (Create, Read, Update, Delete) nəzarəti.
* **Şifrələrin Təhlükəsizliyi:** Parollar bazada BCrypt haşləmə alqoritmi ilə saxlanılır.

### 📚 Kitab və Müəllif İdarəetməsi
* Kitabların dinamik grid şəklində siyahılanması, üzlük şəkillərinin yüklənməsi və göstərilməsi.
* Real-vaxt axtarış və filtrasiya (kitab adı, müəllif və ya ISBN üzrə).
* Müəllif profillərinin idarə olunması və kitablarla əlaqələndirilməsi.

### 📋 Borc Sifarişləri (Borrow Management)
* İstifadəçilərin seçilmiş kitablar üçün götürmə müddəti (məsələn: 14 gün) təyin edərək sifariş yaratması.
* Borc statuslarının (`PENDING`, `APPROVED`, `RETURNED`, `REJECTED`) idarə edilməsi.

---

## 🛠 Texnologiya Steki

### Backend
* **Dil:** Java 17
* **Framework:** Spring Boot 3.x (Spring Data JPA, Spring Security, Spring Web)
* **Təhlükəsizlik:** JWT (jjwt 0.11.5+), BCrypt Password Encoder
* **Verilənlər Bazası:** PostgreSQL
* **Utilities & Docs:** Lombok, OpenAPI 3.0 / Swagger UI

---

## 🗄 Verilənlər Bazası Arxitekturası

### Cədvəllər və Əlaqələr
* **`users`**: `id`, `email`, `password`, `role` (`ROLE_USER`, `ROLE_ADMIN`), `created_at`
* **`authors`**: `id`, `name`, `biography`, `birth_date`
* **`books`**: `id`, `title`, `isbn`, `cover_image_url`, `author_id` (FK), `available_copies`
* **`members`**: `id`, `first_name`, `last_name`, `phone`, `user_id` (FK)
* **`borrow_orders`**: `id`, `member_id` (FK), `order_date`, `status`
* **`borrow_items`**: `id`, `order_id` (FK), `book_id` (FK), `days_requested`

---

## 🌐 REST API Sənədləşməsi (Endpoints)

### 🔑 Authentication (`/api/v1/auth`)
| Method | Endpoint | İcazə | Təsvir |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/v1/auth/register` | `PermitAll` | Yeni istifadəçi qeydiyyatı |
| `POST` | `/api/v1/auth/login` | `PermitAll` | Giriş və JWT Token alışı |

### 📖 Kitablar (`/api/v1/books`)
| Method | Endpoint | İcazə | Təsvir |
| :--- | :--- | :--- | :--- |
| `GET` | `/api/v1/books` | `PermitAll` | Bütün kitabların siyahısı |
| `GET` | `/api/v1/books/{id}` | `PermitAll` | Konkret kitabın təfərrüatı |
| `GET` | `/api/v1/books/search` | `PermitAll` | Kitab adına/ISBN-ə görə axtarış |
| `POST` | `/api/v1/books` | `ADMIN` | Yeni kitab əlavə etmək |
| `PUT` | `/api/v1/books/{id}` | `ADMIN` | Kitab məlumatlarını yeniləmək |
| `DELETE` | `/api/v1/books/{id}` | `ADMIN` | Kitabı silmək |

### ✍️ Müəlliflər (`/api/v1/authors`)
| Method | Endpoint | İcazə | Təsvir |
| :--- | :--- | :--- | :--- |
| `GET` | `/api/v1/authors` | `PermitAll` | Müəlliflərin siyahısı |
| `POST` | `/api/v1/authors` | `ADMIN` | Yeni müəllif əlavə etmək |
| `PUT` | `/api/v1/authors/{id}` | `ADMIN` | Müəllif məlumatını yeniləmək |
| `DELETE` | `/api/v1/authors/{id}` | `ADMIN` | Müəllifi silmək |

### 📦 Borc Sifarişləri (`/api/v1/borrow-orders`)
| Method | Endpoint | İcazə | Təsvir |
| :--- | :--- | :--- | :--- |
| `GET` | `/api/v1/borrow-orders` | `USER`, `ADMIN` | Sifarişlərin siyahısı |
| `POST` | `/api/v1/borrow-orders` | `USER`, `ADMIN` | Yeni borc sifarişi yaratmaq |
| `PUT` | `/api/v1/borrow-orders/{id}/status`| `ADMIN` | Sifariş statusunu dəyişmək |

---

## 🔒 Təhlükəsizlik və JWT Mexanizmi

Spring Security konfiqurasiyası `SecurityConfig.java` daxilində aşağıdakı prinsiplərlə işləyir:
1. **Stateless Session:** Session yaratmır, hər sorğu müstəqil olaraq `Authorization: Bearer <token>` başlığı ilə yoxlanılır.
2. **CORS və CSRF:** REST API üçün CSRF mühafizəsi söndürülüb (`AbstractHttpConfigurer::disable`).
3. **Filter Chain:** `JwtAuthenticationFilter` bütün sorğulardan əvvəl JWT tokenin imzasını və vaxtını yoxlayır.

---

## 🚀 Quraşdırılma və İşə Salınma

### Pre-requisites (Tələblər)
* JDK 17+
* PostgreSQL 13+
* Maven 3.8+
* IDE (IntelliJ IDEA)

### Addım 1: Repozitoriyanı Kloun edin
```bash
git clone [https://github.com/Kanan-peoiks/libraryManagement.git](https://github.com/Kanan-peoiks/libraryManagement.git)
cd libraryManagement