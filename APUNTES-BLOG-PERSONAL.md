# 📚 APUNTES - Blog Personal Project

**Autor:** Andrés Rojas  
**Fecha:** 2025-11-06  
**Proyecto:** Blog Personal - Aprendiendo Java Full Stack

---

## 📑 ÍNDICE

1. [Java](#java)
2. [JPA (Jakarta Persistence API)](#jpa)
3. [Hibernate](#hibernate)
4. [Spring Boot](#spring-boot)
5. [Docker](#docker)
6. [PostgreSQL](#postgresql)
7. [Maven](#maven)
8. [Arquitectura del Proyecto](#arquitectura)

---

## ☕ JAVA

### **Conceptos Usados**

#### **POO - Programación Orientada a Objetos**
```java
// Clase: Plantilla para crear objetos
public class User {
    // Campos/Atributos: Características del objeto
    private String username;
    private String email;
    
    // Constructor: Crea instancias de la clase
    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }
    
    // Métodos: Comportamientos del objeto
    public String getUsername() {
        return username;
    }
}
```

#### **Encapsulamiento**
- **¿Qué es?** Ocultar los detalles internos de una clase
- **¿Cómo?** Usar `private` en campos y `public` en getters/setters
```java
private String password;  // ✅ No accesible directamente

public String getPassword() {  // ✅ Acceso controlado
    return password;
}
```

#### **Tipos de Datos**
```java
Long id;                    // Números enteros grandes (64 bits)
String username;            // Cadenas de texto
LocalDateTime createdAt;    // Fecha y hora
List<Post> posts;          // Lista de objetos
```

#### **Colecciones - List**
```java
import java.util.List;

// Lista de posts
List<Post> posts;           // Interfaz List
// Implementaciones: ArrayList, LinkedList

// Uso:
posts.add(post);           // Agregar
posts.get(0);              // Obtener por índice
posts.size();              // Cantidad de elementos
```

#### **Anotaciones (@)**
- **¿Qué son?** Metadatos que añaden información al código
- **¿Para qué?** Configurar comportamientos sin código extra
```java
@Entity                    // "Esta clase es una tabla"
@Id                        // "Este campo es clave primaria"
```

---

## 🗄️ JPA (Jakarta Persistence API)

### **¿Qué es JPA?**
- **Especificación** estándar de Java para ORM (Object-Relational Mapping)
- **NO es una implementación**, es una interfaz
- Implementaciones: Hibernate, EclipseLink, OpenJPA

### **ORM - Object-Relational Mapping**
```
Clase Java (User) ←→ Tabla BD (users)
Objeto (user1)    ←→ Fila en tabla
Campo (username)  ←→ Columna
```

### **Anotaciones JPA Usadas**

#### **@Entity**
```java
@Entity
public class User { }
```
- **¿Qué hace?** Marca la clase como una entidad JPA
- **Resultado:** JPA crea una tabla en la base de datos
- **Tabla creada:** `user` (nombre de la clase en minúscula)

---

#### **@Table**
```java
@Table(name = "users")
```
- **¿Qué hace?** Personaliza el nombre de la tabla
- **Uso:** Cuando quieres un nombre diferente al de la clase
- **Ejemplo:** Clase `User` → tabla `users` (plural)

---

#### **@Id**
```java
@Id
private Long id;
```
- **¿Qué hace?** Define la clave primaria (Primary Key)
- **Obligatorio:** Toda entidad debe tener un @Id
- **Tipo común:** Long, Integer, String (UUID)

---

#### **@GeneratedValue**
```java
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
```
- **¿Qué hace?** Indica que el valor se genera automáticamente
- **Estrategias:**
  - `IDENTITY`: Base de datos genera el ID (PostgreSQL, MySQL)
  - `SEQUENCE`: Usa secuencias de BD
  - `AUTO`: JPA decide automáticamente
  - `TABLE`: Usa tabla auxiliar para IDs

---

#### **@Column**
```java
@Column(nullable = false, unique = true, length = 50)
private String username;
```
- **¿Qué hace?** Configura restricciones de la columna
- **Parámetros:**
  - `nullable = false`: NO puede ser NULL (obligatorio)
  - `unique = true`: Debe ser único en la tabla
  - `length = 50`: Máximo de caracteres (para String)
  - `name = "user_name"`: Nombre personalizado de columna

---

#### **@Lob**
```java
@Lob
private String content;
```
- **¿Qué es?** Large Object (Objeto Grande)
- **¿Para qué?** Guardar texto largo o archivos binarios
- **Sin @Lob:** VARCHAR(255) - máximo 255 caracteres
- **Con @Lob:** TEXT/CLOB - texto ilimitado
- **Cuándo usar:**
  - Contenido de posts/artículos
  - Descripciones largas
  - Archivos (imágenes como BLOB)

---

#### **@ManyToOne**
```java
@ManyToOne
@JoinColumn(name = "user_id")
private User user;
```
- **¿Qué hace?** Define relación "Muchos a Uno"
- **Ejemplo:** Muchos posts pertenecen a UN usuario
- **Resultado:** Crea columna Foreign Key en la tabla
- **Cuándo usar:** Cuando un objeto pertenece a otro

---

#### **@OneToMany**
```java
@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
private List<Post> posts;
```
- **¿Qué hace?** Define relación "Uno a Muchos"
- **Ejemplo:** UN usuario tiene muchos posts
- **Parámetros:**
  - `mappedBy = "user"`: Apunta al campo en la otra clase
  - `cascade`: Propaga operaciones (guardar, eliminar)
  - `orphanRemoval = true`: Elimina huérfanos de BD
- **Resultado:** NO crea columna, es lado "inverso" de la relación

---

#### **@JoinColumn**
```java
@JoinColumn(name = "user_id", nullable = false)
```
- **¿Qué hace?** Configura la columna Foreign Key
- **Parámetros:**
  - `name`: Nombre de la columna FK
  - `nullable`: Si puede ser null
- **Uso:** Siempre con @ManyToOne

---

### **Relaciones - Resumen**

#### **Relación Unidireccional**
```java
// Solo Post conoce a User
// Post.java
@ManyToOne
private User user;

// User.java - NO tiene referencia a posts
```

#### **Relación Bidireccional**
```java
// Ambos se conocen mutuamente

// Post.java
@ManyToOne
@JoinColumn(name = "user_id")
private User user;

// User.java
@OneToMany(mappedBy = "user")
private List<Post> posts;
```

#### **Tipos de Relaciones**
```
@OneToOne:   1 ←→ 1   (User ←→ Profile)
@OneToMany:  1 ←→ N   (User ←→ Posts)
@ManyToOne:  N ←→ 1   (Posts ←→ User)
@ManyToMany: N ←→ N   (Students ←→ Courses)
```

---

### **Cascade Types**
```java
cascade = CascadeType.ALL
```
- **¿Qué hace?** Propaga operaciones a entidades relacionadas

**Tipos:**
```java
CascadeType.PERSIST   // Al guardar, guarda relaciones
CascadeType.MERGE     // Al actualizar, actualiza relaciones
CascadeType.REMOVE    // Al eliminar, elimina relaciones
CascadeType.REFRESH   // Al refrescar, refresca relaciones
CascadeType.DETACH    // Al separar, separa relaciones
CascadeType.ALL       // Todas las anteriores
```

**Ejemplo:**
```java
User user = new User("andres", "andres@mail.com", "123");
Post post = new Post("Título", "Contenido", user);

userRepository.save(user);  // Con cascade, guarda user Y post
```

---

### **orphanRemoval**
```java
@OneToMany(mappedBy = "post", orphanRemoval = true)
private List<Comment> comments;
```
- **¿Qué hace?** Elimina de BD los objetos removidos de la lista
```java
Post post = postRepository.findById(1L).get();
post.getComments().remove(0);  // Eliminar comentario de lista
postRepository.save(post);     // Con orphanRemoval, se borra de BD
```

---

## 🔥 HIBERNATE

### **¿Qué es Hibernate?**
- **Implementación** de JPA más popular
- **ORM Framework** que convierte objetos Java en tablas SQL
- **Ventajas:**
  - No escribes SQL manualmente
  - Independiente de la base de datos
  - Maneja relaciones automáticamente

### **Anotaciones Hibernate Usadas**

#### **@CreationTimestamp**
```java
import org.hibernate.annotations.CreationTimestamp;

@CreationTimestamp
private LocalDateTime createdAt;
```
- **¿Qué hace?** Asigna automáticamente la fecha/hora de CREACIÓN
- **Cuándo:** Al hacer `save()` por primera vez
- **Uso:** Saber cuándo se creó un registro
- **NO necesitas:** Hacer `setCreatedAt(LocalDateTime.now())`

---

#### **@UpdateTimestamp**
```java
import org.hibernate.annotations.UpdateTimestamp;

@UpdateTimestamp
private LocalDateTime updatedAt;
```
- **¿Qué hace?** Actualiza automáticamente fecha/hora al EDITAR
- **Cuándo:** Cada vez que haces `save()` en un objeto existente
- **Uso:** Saber cuándo se modificó un registro por última vez
- **Diferencia con @CreationTimestamp:**
  - `createdAt`: Se asigna UNA VEZ (nunca cambia)
  - `updatedAt`: Se actualiza CADA VEZ que editas

---

### **DDL Auto (Hibernate)**
```properties
# application.properties
spring.jpa.hibernate.ddl-auto=update
```
- **¿Qué hace?** Gestiona automáticamente el esquema de BD

**Opciones:**
```
none:        No hace nada
validate:    Solo valida que el esquema coincida
update:      Actualiza tablas (agrega columnas, NO elimina)
create:      Borra y recrea todas las tablas (¡CUIDADO! Pierdes datos)
create-drop: Igual que create, pero borra al cerrar la app
```

**Recomendación:**
- **Desarrollo:** `update` (crea/actualiza tablas automáticamente)
- **Producción:** `validate` o `none` (usas migrations con Flyway/Liquibase)

---

### **SQL Logging**
```properties
# Ver SQL generado por Hibernate
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```
- **show-sql:** Muestra queries en consola
- **format_sql:** Formatea el SQL para que sea legible

**Ejemplo de salida:**
```sql
Hibernate: 
    insert 
    into
        users (email, password, username, id) 
    values
        (?, ?, ?, ?)
```

---

## 🍃 SPRING BOOT

### **¿Qué es Spring Boot?**
- **Framework** para crear aplicaciones Java fácilmente
- **Auto-configuración:** Configura automáticamente según dependencias
- **Embedded Server:** Incluye Tomcat (no necesitas instalar servidor)

### **Anotaciones Spring Boot Usadas**

#### **@SpringBootApplication**
```java
@SpringBootApplication
public class BlogPersonalApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogPersonalApplication.class, args);
    }
}
```
- **¿Qué hace?** Marca la clase principal de Spring Boot
- **Incluye 3 anotaciones:**
  - `@Configuration`: Clase de configuración
  - `@EnableAutoConfiguration`: Auto-configuración
  - `@ComponentScan`: Escanea componentes en el paquete

---

#### **@Repository**
```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
```
- **¿Qué hace?** Marca la clase como repositorio de datos
- **Ventajas:**
  - Spring maneja excepciones de BD automáticamente
  - Se puede inyectar con @Autowired
- **Uso:** Para interfaces que acceden a la base de datos

---

### **Spring Data JPA**

#### **JpaRepository<T, ID>**
```java
public interface UserRepository extends JpaRepository<User, Long> {
    // Métodos automáticos (no necesitas implementarlos):
    save(user)              // Guardar o actualizar
    findById(id)            // Buscar por ID
    findAll()               // Obtener todos
    deleteById(id)          // Eliminar por ID
    existsById(id)          // Verificar si existe
    count()                 // Contar registros
}
```

**Parámetros:**
- `User`: Entidad que maneja
- `Long`: Tipo del ID

**Ventajas:**
- ✅ NO escribes código de BD
- ✅ NO escribes SQL
- ✅ Spring lo implementa automáticamente

---

### **application.properties**
```properties
# Configuración de Base de Datos
spring.datasource.url=jdbc:postgresql://localhost:5432/blogpersonal
spring.datasource.username=postgres
spring.datasource.password=tu_password

# Configuración JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# Puerto del servidor
server.port=8080
```

**¿Qué hace cada línea?**
- `datasource.url`: URL de conexión a PostgreSQL
- `datasource.username/password`: Credenciales de BD
- `hibernate.ddl-auto`: Cómo gestiona las tablas
- `show-sql`: Mostrar SQL en consola
- `dialect`: Dialecto SQL específico de PostgreSQL
- `server.port`: Puerto donde corre la aplicación

---

## 🐳 DOCKER

### **¿Qué es Docker?**
- **Plataforma** para crear y ejecutar contenedores
- **Contenedor:** Entorno aislado con todo lo necesario (app + dependencias)
- **Imagen:** Plantilla para crear contenedores

### **Docker Desktop**
- **Aplicación** gráfica para gestionar Docker en Windows/Mac
- **Incluye:**
  - Docker Engine
  - Docker Compose
  - Interfaz gráfica para contenedores

### **Contenedor PostgreSQL**
```bash
# Crear contenedor PostgreSQL
docker run --name portafolio-db \
  -e POSTGRES_PASSWORD=tu_password \
  -e POSTGRES_DB=blogpersonal \
  -p 5432:5432 \
  -d postgres
```

**Parámetros:**
- `--name`: Nombre del contenedor
- `-e`: Variables de entorno
- `-p 5432:5432`: Puerto (host:contenedor)
- `-d`: Modo detached (segundo plano)
- `postgres`: Imagen a usar

### **Comandos Docker Básicos**
```bash
docker ps                    # Ver contenedores corriendo
docker ps -a                 # Ver todos los contenedores
docker start portafolio-db   # Iniciar contenedor
docker stop portafolio-db    # Detener contenedor
docker logs portafolio-db    # Ver logs
docker rm portafolio-db      # Eliminar contenedor
```

---

## 🐘 POSTGRESQL

### **¿Qué es PostgreSQL?**
- **Sistema de base de datos** relacional (RDBMS)
- **Open source** y muy potente
- **Ventajas:**
  - Maneja relaciones complejas
  - Soporta JSON, arrays, tipos personalizados
  - Muy usado en producción

### **Tipos de Datos SQL vs Java**

| **Java** | **PostgreSQL** | **Con @Lob** |
|----------|----------------|--------------|
| `Long` | `BIGINT` | - |
| `String` | `VARCHAR(255)` | `TEXT` |
| `LocalDateTime` | `TIMESTAMP` | - |
| `boolean` | `BOOLEAN` | - |

### **Convenciones de Nombres**
```
Clase Java:  User         → Tabla:  users (plural, minúscula)
Campo Java:  firstName    → Columna: first_name (snake_case)
```

---

## 📦 MAVEN

### **¿Qué es Maven?**
- **Herramienta** de gestión de proyectos Java
- **Funciones:**
  - Gestiona dependencias (librerías)
  - Compila el proyecto
  - Ejecuta tests
  - Empaqueta en JAR/WAR

### **pom.xml**
- **Archivo** de configuración de Maven
- **Define:**
  - Dependencias del proyecto
  - Versión de Java
  - Plugins

### **Dependencias Usadas**
```xml
<!-- Spring Boot Starter Web -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<!-- Spring Data JPA -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<!-- PostgreSQL Driver -->
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>
```

---

## 🏗️ ARQUITECTURA DEL PROYECTO

### **Estructura de Paquetes**
```
com.example.blogpersonal/
├── entity/              # Entidades JPA (User, Post, Comment)
├── repository/          # Interfaces Spring Data JPA
├── service/            # Lógica de negocio (próximo paso)
├── controller/         # REST Controllers (próximo paso)
├── dto/               # Data Transfer Objects (próximo paso)
│   ├── request/       # DTOs para recibir datos
│   └── response/      # DTOs para enviar datos
├── exception/         # Excepciones personalizadas
└── config/            # Configuraciones
```

### **Patrón de Capas (Layered Architecture)**
```
┌─────────────────────┐
│    CONTROLLER       │  ← REST API (recibe peticiones HTTP)
├─────────────────────┤
│      SERVICE        │  ← Lógica de negocio
├─────────────────────┤
│    REPOSITORY       │  ← Acceso a datos
├─────────────────────┤
│      ENTITY         │  ← Modelo de datos
├─────────────────────┤
│   BASE DE DATOS     │  ← PostgreSQL
└─────────────────────┘
```

**Flujo de datos:**
```
Request HTTP → Controller → Service → Repository → Database
                    ↓           ↓          ↓
Response HTTP ← DTO ← Lógica ← Entity ← SQL
```

---

## 🎯 CONCEPTOS CLAVE APRENDIDOS

### **1. Separación de Responsabilidades**
- **Entity:** Solo representación de datos
- **Repository:** Solo acceso a BD
- **Service:** Solo lógica de negocio
- **Controller:** Solo manejo de HTTP

### **2. Inyección de Dependencias**
```java
@Service
public class UserService {
    @Autowired  // Spring inyecta automáticamente
    private UserRepository userRepository;
}
```

### **3. Convención sobre Configuración**
- Spring Boot asume configuraciones por defecto
- Solo configuras lo que es diferente
- Ejemplo: `@Entity` → automáticamente crea tabla

### **4. Relaciones Bidireccionales**
```java
// Puedes navegar en ambas direcciones
User user = post.getUser();      // Post → User
List<Post> posts = user.getPosts();  // User → Posts
```

### **5. Lazy vs Eager Loading**
```java
@ManyToOne(fetch = FetchType.LAZY)   // Se carga bajo demanda
@ManyToOne(fetch = FetchType.EAGER)  // Se carga inmediatamente
```
- **Default @ManyToOne:** EAGER
- **Default @OneToMany:** LAZY

---

## 📌 GLOSARIO

| **Término** | **Definición** |
|-------------|----------------|
| **Entity** | Clase Java que representa una tabla en BD |
| **Repository** | Interfaz para operaciones CRUD en BD |
| **DTO** | Objeto para transferir datos (no es entidad) |
| **ORM** | Object-Relational Mapping (Mapeo Objeto-Relacional) |
| **JPA** | Jakarta Persistence API (estándar de persistencia) |
| **Hibernate** | Implementación de JPA |
| **Primary Key** | Identificador único de un registro (ID) |
| **Foreign Key** | Campo que referencia a otra tabla |
| **Cascade** | Propagar operaciones a entidades relacionadas |
| **Fetch** | Estrategia de carga de datos relacionados |
| **Transient** | Campo que NO se guarda en BD |

---

## 🚀 PRÓXIMOS PASOS

### **Fase Actual: COMPLETADA ✅**
- [x] Entidades creadas (User, Post, Comment)
- [x] Repositorios básicos (UserRepository, PostRepository)
- [x] Relaciones configuradas
- [x] Docker PostgreSQL funcionando

### **Siguiente Fase: Services y Controllers**
- [ ] Crear DTOs (request/response)
- [ ] Implementar Services (UserService, PostService, CommentService)
- [ ] Crear Controllers REST
- [ ] Probar con Postman
- [ ] Manejo de excepciones

---

## 📚 RECURSOS RECOMENDADOS

### **Documentación Oficial**
- Spring Boot: https://spring.io/projects/spring-boot
- Spring Data JPA: https://spring.io/projects/spring-data-jpa
- Hibernate: https://hibernate.org/orm/documentation/
- PostgreSQL: https://www.postgresql.org/docs/

### **Tutoriales**
- Baeldung: https://www.baeldung.com/ (excelente para Spring)
- Official Spring Guides: https://spring.io/guides

---

**Última actualización:** 2025-11-06  
**Autor:** Andrés Rojas (@ANDRESROJAS00)