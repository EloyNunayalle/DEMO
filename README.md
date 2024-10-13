# Plataforma de Trueque Digital - MarketExchange

## CS 2031 Desarrollo Basado en Plataforma

**Integrantes:**
- Romero Padilla, Luis Anthony
- Nunayalle Brañes, Llorent Eloy
- Vargas Iglesias, Hanks Jean Pierce

## Índice
- [Introducción](#introducción)
- [Identificación del Problema o Necesidad](#identificación-del-problema-o-necesidad)
- [Descripción de la Solución](#descripción-de-la-solución)
- [Modelo de Entidades](#modelo-de-entidades)
- [Testing y Manejo de Errores](#testing-y-manejo-de-errores)
- [Medidas de Seguridad Implementadas](#medidas-de-seguridad-implementadas)
- [Eventos y Asincronía](#eventos-y-asincronía)
- [GitHub](#github)
- [Conclusión](#conclusión)
- [Apéndices](#apéndices)

## Introducción

### Contexto
En un mundo cada vez más preocupado por los efectos del consumismo desmedido, el intercambio de objetos emerge como una solución sostenible y consciente. MarketExchange es una plataforma diseñada para responder a esta necesidad, permitiendo a los usuarios intercambiar bienes de manera directa y eficiente. El proyecto se enfoca en mejorar las interacciones entre los usuarios, proporcionando una interfaz intuitiva que facilita la negociación y cierre de acuerdos de manera transparente y segura. Además, cuenta con un sistema de evaluación, donde los participantes pueden calificar sus experiencias y garantizar la calidad de los productos intercambiados, fomentando la confianza dentro de la comunidad. MarketExchange no solo propone una alternativa al consumo tradicional, sino que impulsa un modelo de economía circular, prolongando la vida útil de los objetos a través del intercambio y el reuso.

### Objetivos del Proyecto
- Facilitar el intercambio directo de bienes entre usuarios.
- Proporcionar un sistema seguro y transparente de acuerdos de intercambio.
- Implementar un sistema de retroalimentación que permita evaluar a los usuarios después de cada intercambio.
- Fomentar la confianza y seguridad en los acuerdos entre usuarios.

## Identificación del Problema o Necesidad

### Descripción del Problema
El sistema busca resolver la necesidad de eficiencia y precisión en la toma de pedidos, a la vez que mantiene el contacto personal entre el mesero y el cliente. La intervención del mesero sigue siendo clave, ya que es quien recibe a los clientes, ofrece la atención durante su estancia y entrega los pedidos. Al mismo tiempo, la implementación de un sistema de evaluación mediante códigos QR permite al administrador recopilar comentarios precisos sobre el desempeño de cada mesero, incentivando la mejora continua y asegurando una experiencia de calidad para los clientes.

### Justificación
El aumento del consumismo ha generado la necesidad de explorar alternativas más sostenibles. Muchos productos en buen estado pierden su valor en el mercado tradicional, mientras que otras personas podrían necesitarlos. MarketExchange ofrece una plataforma digital que facilita el trueque directo entre usuarios, permitiendo intercambiar productos sin dinero de por medio, fomentando así una economía circular.


## Descripción de la Solución

### Actores del Negocio
1. **Usuario registrado**: Persona que se registra en la plataforma para intercambiar productos.
2. **Administrador**: Persona encargada de gestionar la plataforma y velar por el cumplimiento de las políticas de uso.
3. **Participante en un intercambio**: Usuario que publica o recibe solicitudes de intercambio.

### Funcionalidades Clave
1. **Publicación de productos para intercambio**: Los usuarios pueden publicar los productos que desean intercambiar, especificando condiciones y características.
2. **Sistema de acuerdos**: Los usuarios pueden negociar y acordar un intercambio de manera directa.
3. **Sistema de retroalimentación**: Después de cada intercambio, los usuarios pueden dejar comentarios y calificaciones para evaluar la calidad del trueque.
4. **Historial de intercambios**: Cada usuario tiene acceso a un historial de los intercambios que ha realizado en la plataforma.
5. **Notificaciones de intercambios**: Los usuarios reciben notificaciones sobre el estado de sus intercambios.


### Entidades del Negocio
1. **Usuario**
2. **Shipment**
3. **Raiting**
4. **Item**
5. **Category** 
6. **Agreement**

### Casos de uso del Negocio
1.Publicación de productos para intercambio: Los usuarios pueden publicar productos que desean intercambiar, especificando detalles como la descripción, estado y condiciones del producto. Esto les permite mostrar lo que ofrecen a otros usuarios interesados en intercambiar.

2.Negociación de acuerdos: Los usuarios pueden negociar directamente a través de la plataforma, estableciendo términos para los intercambios de manera transparente. Las negociaciones pueden incluir la selección de productos a intercambiar y la confirmación de las condiciones.

2.Sistema de retroalimentación: Tras cada intercambio, los usuarios tienen la opción de calificar y dejar comentarios sobre la experiencia. Este sistema de retroalimentación ayuda a mantener la confianza en la plataforma y asegura que los intercambios sean satisfactorios para ambas partes.

4.Historial de intercambios: Cada usuario puede acceder a su historial de intercambios, donde se registran todos los acuerdos completados. Esto permite a los usuarios revisar y rastrear sus actividades de trueque, lo que fomenta un mayor control sobre sus transacciones.

5.Sistema de notificaciones: La plataforma notifica a los usuarios sobre el estado de sus intercambios, desde la confirmación de un acuerdo hasta la actualización del estado del envío (Shipment), asegurando que los usuarios estén siempre al tanto del proceso.



### Tecnologías Utilizadas
**Lenguajes de Programación:
Java: Utilizado para desarrollar la lógica de negocio y las funcionalidades principales del backend.
Frameworks:

**Spring Boot: Implementado en el backend para manejar el flujo de datos y la interacción con las entidades de la plataforma.
Bootstrap: Usado para crear una interfaz de usuario moderna y responsiva, facilitando la interacción intuitiva entre los usuarios.
Bases de Datos:

**PostgreSQL: Utilizada para almacenar toda la información relacionada con los usuarios, productos, intercambios y calificaciones.
APIs Externas:


## Modelo de Entidades

### Diagrama de Entidades
![Diagrama de Entidades](./DiagramaDeClases-DBP.png)

### Descripción de Entidades


### Usuario
Representa a los usuarios del sistema en la plataforma MarketExchange.


| **Atributo**        | **Tipo de Variable**   | **Descripción**                                               | **Criterios Limitantes**                                               |
|---------------------|------------------------|---------------------------------------------------------------|------------------------------------------------------------------------|
| `id`                | `Long`                 | Identificador único del usuario.                              | Debe ser único y generado automáticamente.                             |
| `firstname`         | `String`               | Nombre del usuario.                                           | No puede estar vacío, máximo 50 caracteres.                            |
| `lastname`          | `String`               | Apellido del usuario.                                         | No puede estar vacío, máximo 50 caracteres.                            |
| `email`             | `String`               | Correo electrónico del usuario.                               | Debe tener un formato válido de email, debe ser único.                 |
| `password`          | `String`               | Contraseña del usuario para autenticación.                    | Mínimo 8 caracteres, debe estar encriptada.                            |
| `phone`             | `String`               | Número de teléfono del usuario.                               | Longitud entre 7 y 15 dígitos.                                         |
| `address`           | `String`               | Dirección del usuario.                                        | No puede estar vacía, máximo 100 caracteres.                           |
| `role`              | `Enum (ADMIN, USER)`   | Rol del usuario dentro del sistema.                           | Debe ser uno de los valores permitidos: `ADMIN`, `USER`.               |
| `createdAt`         | `LocalDateTime`        | Fecha y hora de creación del usuario.                         | No puede ser nula, se genera automáticamente al momento de registro.   |

---

#### Métodos del endpoint `/usuarios`:

| **Método**           | **Ruta**          | **Roles Permitidos** | **Descripción**                                                   | **Excepciones**                                         | **Métodos de Service**                             |
|----------------------|-------------------|----------------------|-------------------------------------------------------------------|---------------------------------------------------------|----------------------------------------------------|
| `GET`                | `/{id}`           | `ADMIN`              | Devuelve el `UsuarioResponseDto` del usuario por su id.           | `ResourceNotFoundException`                             | `buscarUsuarioPorId(Long id)`                     |
| `GET`                | `/listar`         | `ADMIN`              | Devuelve la lista de todos los usuarios en formato `UsuarioResponseDto`. | -                                                       | `listarUsuarios()`                                |
| `PUT`                | `/{id}`           | `ADMIN`, `USER`      | Actualiza los datos del usuario identificado por su id.           | `ResourceNotFoundException`, `UnauthorizeOperationException`, `InvalidUserFieldException` | `actualizarUsuario(Long id, UsuarioRequestDto dto)`|
| `DELETE`             | `/{id}`           | `ADMIN`, `USER`      | Elimina el usuario identificado por su id.                       | `ResourceNotFoundException`, `UnauthorizeOperationException` | `eliminarUsuario(Long id)`                        |
| `GET`                | `/me`             | `USER`               | Devuelve el `UsuarioResponseDto` del usuario autenticado.         | `ResourceNotFoundException`, `UnauthorizeOperationException` | `getUsuarioOwnInfo()`                             |

---

#### Métodos adicionales del service:

| **Método**                | **Descripción**                                                                                   |
|---------------------------|---------------------------------------------------------------------------------------------------|
| `registrarUsuario`         | Crea un nuevo usuario validando los datos de entrada y disparando el evento `UsuarioCreadoEvent`. |
| `getUsuarioOwnInfo`        | Devuelve la información del usuario autenticado basado en el contexto de seguridad.              |

#### 2. Item
Representa los productos que los usuarios desean intercambiar.

| **Atributo**  | **Tipo de Variable** | **Descripción**                                 | **Criterios Limitantes**                                     |
|---------------|----------------------|-------------------------------------------------|--------------------------------------------------------------|
| id            | Long                 | Identificador único del producto.               | Debe ser único, generado automáticamente.                    |
| name          | String               | Nombre del producto.                            | Máximo 100 caracteres.                                       |
| description   | String               | Descripción del producto.                       | Máximo 255 caracteres.                                       |
| category      | String               | Categoría del producto.                         | Valores permitidos: ELECTRONICS, CLOTHING, TOYS, etc.         |
| condition     | String               | Estado del producto (nuevo, usado).             | Valores permitidos: NEW, USED.                               |

---

#### 3. Agreement
Registra los acuerdos de intercambio entre usuarios.

| **Atributo**  | **Tipo de Variable** | **Descripción**                                   | **Criterios Limitantes**                                     |
|---------------|----------------------|---------------------------------------------------|--------------------------------------------------------------|
| id            | Long                 | Identificador único del acuerdo.                  | Debe ser único, generado automáticamente.                    |
| status        | Enum                 | Estado del acuerdo.                               | Valores permitidos: PENDING, ACCEPTED, REJECTED.              |
| tradeDate     | LocalDateTime        | Fecha en la que se realiza el intercambio.        | No puede ser nula.                                            |
| initiatorUser | User                 | Usuario que inicia el intercambio.                | No puede ser nulo.                                            |
| receiveUser   | User                 | Usuario que recibe la propuesta de intercambio.   | No puede ser nulo.                                            |
| initiatorItem | Product              | Producto ofrecido por el usuario que inicia.      | No puede ser nulo.                                            |
| receiveItem   | Product              | Producto ofrecido por el usuario que recibe.      | No puede ser nulo.                                            |

---

#### 4. Category
Clasificación de los productos.

| **Atributo**  | **Tipo de Variable** | **Descripción**                                   | **Criterios Limitantes**                                     |
|---------------|----------------------|---------------------------------------------------|--------------------------------------------------------------|
| id            | Long                 | Identificador único de la categoría.              | Debe ser único, generado automáticamente.                    |
| name          | String               | Nombre de la categoría.                           | Máximo 50 caracteres.                                         |

---

#### 5. Rating
Evaluación del intercambio y comportamiento del usuario.

| **Atributo**  | **Tipo de Variable** | **Descripción**                                   | **Criterios Limitantes**                                     |
|---------------|----------------------|---------------------------------------------------|--------------------------------------------------------------|
| id            | Long                 | Identificador único de la calificación.           | Debe ser único, generado automáticamente.                    |
| rating        | Integer              | Puntuación del intercambio.                       | Valores entre 1 y 5.                                          |
| comment       | String               | Comentario del usuario sobre el intercambio.      | Opcional, máximo 255 caracteres.                              |
| user          | User                 | Usuario que realizó la calificación.              | No puede ser nulo.                                            |
| agreement     | Agreement            | Acuerdo asociado a la calificación.               | No puede ser nulo.                                            |

---

#### 6. Shipment
Registro de los envíos asociados a los acuerdos.

| **Atributo**  | **Tipo de Variable** | **Descripción**                                   | **Criterios Limitantes**                                     |
|---------------|----------------------|---------------------------------------------------|--------------------------------------------------------------|
| id            | Long                 | Identificador único del envío.                    | Debe ser único, generado automáticamente.                    |
| initiatorAddress | String            | Dirección del usuario que inicia el intercambio.  | Máximo 255 caracteres, no puede ser nulo.                     |
| receiveAddress  | String            | Dirección del usuario que recibe el intercambio.  | Máximo 255 caracteres, no puede ser nulo.                     |
| deliveryDate    | LocalDate          | Fecha de entrega del intercambio.                 | No puede ser nula.                                            |
| agreement       | Agreement          | Acuerdo asociado al envío.                        | No puede ser nulo.                                            |

---

## Testing y Manejo de Errores

### Niveles de Testing Realizados
1. **Pruebas Unitarias**: Se realizan para cada endpoint de la API, evaluando respuestas HTTP y validaciones de datos.
2. **Pruebas de Sistema**: Simulan los flujos de negocio, como la creación de acuerdos y calificación de intercambios.

### Manejo de Errores
El sistema utiliza un manejo global de excepciones para capturar errores inesperados, con mensajes claros para los usuarios y registros detallados para los administradores.

## Medidas de Seguridad Implementadas

### Seguridad de Datos
- **Cifrado de contraseñas**: Se utiliza bcrypt para el hash de contraseñas.
- **Autenticación**: Implementación de JWT (JSON Web Tokens) para autenticar usuarios.

## Eventos y Asincronía
Los eventos se manejan de manera asincrónica para operaciones que no requieren una respuesta inmediata, como la generación de envíos cuando se acepta un acuerdo.

## GitHub

### GitHub Projects
Se utilizó GitHub Projects para la asignación de tareas y seguimiento del progreso del proyecto.

### GitHub Actions
Se configuró un pipeline de CI/CD para ejecutar pruebas y despliegues automáticos.

## Conclusión

El sistema de trueque digital implementado permite a los usuarios intercambiar productos de manera eficiente, mejorando la reutilización y fomentando una economía colaborativa.

---





