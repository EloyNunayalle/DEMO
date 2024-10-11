# Backend - MarketExchange

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
El intercambio de objetos está adquiriendo relevancia en un mundo donde el consumismo excesivo se convierte en una preocupación creciente. MarketExchange es una plataforma que responde a esta necesidad al permitir a las personas intercambiar bienes de forma directa, promoviendo un consumo más consciente y sostenible. Este proyecto se centra en optimizar las interacciones entre usuarios, facilitando el proceso de intercambio mediante una interfaz intuitiva que permite a los usuarios negociar y acordar intercambios de forma transparente y segura. Además, la plataforma cuenta con un sistema de retroalimentación, donde los participantes pueden evaluar el proceso de intercambio, fomentando la confianza entre los usuarios y asegurando la calidad de los productos intercambiados. Así, MarketExchange no solo ofrece una alternativa al consumismo tradicional, sino que también impulsa un modelo de economía circular, donde el valor de los objetos se preserva a través del reuso.

### Objetivos del Proyecto
- Facilitar a los clientes la visualización del menú y la realización de pedidos mediante el escaneo de un código QR en la mesa, sin reemplazar el rol del mesero, quien continuará recepcionando a los clientes, entregando los pedidos y atendiéndolos en sus necesidades adicionales.
- Proporcionar una vía para que los clientes califiquen el servicio de los meseros al finalizar su consumo, mediante un segundo código QR que estará en la boleta, permitiendo a los administradores obtener comentarios y calificaciones de manera rápida y efectiva.
- Otorgar al administrador herramientas para la gestión del desempeño de los meseros, basadas en los comentarios y calificaciones de los clientes, para incentivar buenas prácticas mediante bonificaciones o menciones especiales.

## Identificación del Problema o Necesidad

### Descripción del Problema
El sistema busca resolver la necesidad de eficiencia y precisión en la toma de pedidos, a la vez que mantiene el contacto personal entre el mesero y el cliente. La intervención del mesero sigue siendo clave, ya que es quien recibe a los clientes, ofrece la atención durante su estancia y entrega los pedidos. Al mismo tiempo, la implementación de un sistema de evaluación mediante códigos QR permite al administrador recopilar comentarios precisos sobre el desempeño de cada mesero, incentivando la mejora continua y asegurando una experiencia de calidad para los clientes.

### Justificación
Es relevante solucionar este problema porque permite agilizar el proceso de pedidos, reduciendo errores y tiempos de espera, lo que incrementa la satisfacción del cliente. Además, el sistema de evaluación fomenta una cultura de mejora continua en los empleados, lo que beneficia tanto al restaurante como a los meseros.

## Descripción de la Solución

### Actores del Negocio
1. **Manager**: Encargo de gestión de personal, atención al cliente, gestión de inventarios y manejo de caja. 
2. **Mesero**: Encargado de la atención personal al cliente en el local. 
3. **Repartidor**: Encargado de la entrega de pedidos por delivery. 
4. **Cliente registrado**: Cliente con una cuenta creada en la aplicación del restaurante. 
5. **Cliente general**: Cliente de "al paso" que no está registrado en la aplicación del restaurante. 
6. **Chef**: Recibe órdenes y las prepara.   


### Entidades del Negocio
1. **User**
2. **Client**
3. **Repartidor**
4. **Mesero**
5. **Mesa** 
6. **Orden**
7. **Reservation**
8. **Delivery**
9. **PedidoLocal**
10. **Product**
11. **ReviewMesero**
12. **ReviewDelivery**
