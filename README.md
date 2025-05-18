# SistemaGestionTechLab
Sistema de Gestión de Productos y Pedidos
Este proyecto es una aplicación de consola en Java para la gestión de productos y pedidos, con manejo robusto de stock, validaciones y estructura modular.
Incluye manejo de excepciones personalizadas y está organizado en paquetes para facilitar su mantenimiento y escalabilidad.

🚀 Características
Gestión de productos: Alta, baja, modificación y listado de productos.

Gestión de pedidos: Creación de pedidos, verificación de stock, confirmación y listado de pedidos realizados.

Validaciones robustas: No permite stock ni precios negativos, ni ventas sin stock suficiente.

Excepciones personalizadas: Para manejo claro de errores de stock.

Interfaz de consola amigable: Menús claros y confirmaciones de cada acción.

Estructura modular: Separación en paquetes main, modelo, servicio y excepciones.

📁 Estructura del Proyecto
src/
└── com/
└── techlab/
├── main/
│   └── SistemaGestion.java         # Clase principal con el menú
├── modelo/
│   ├── Producto.java
│   ├── Pedido.java
│   └── LineaPedido.java
├── servicio/
│   └── GestionProductosPedidos.java
└── excepciones/
└── StockInsuficienteException.java

🛠️ Requisitos
Java
(Opcional) IDE como IntelliJ IDEA, Eclipse o VS Code

🧩 Paquetes y Clases
com.techlab.main: Clase principal (SistemaGestion.java o Main.java)

com.techlab.modelo: Entidades de dominio (Producto, Pedido, LineaPedido)

com.techlab.servicio: Lógica de negocio y gestión (GestionProductosPedidos)

com.techlab.excepciones: Excepciones personalizadas (StockInsuficienteException)

💡 Ejemplo de Uso

=================================== SISTEMA DE GESTIÓN - TECHLAB ==================================
1) Agregar producto
2) Listar productos
3) Buscar/Actualizar producto
4) Eliminar producto
5) Crear un pedido
6) Listar pedidos
7) Salir
   Elija una opción:

📝 Notas
Puedes cancelar la carga de un pedido en cualquier momento ingresando 0 como ID o cantidad.

El sistema evita ventas sin stock suficiente y muestra mensajes claros ante cualquier error.

Si necesitas agregar persistencia (guardar en archivos), puedes extender la clase de servicio.

🖥️ Contribución
¡Pull requests y sugerencias son bienvenidas!

## 📄 Licencia

Este proyecto fue desarrollado por Claudia Oliverio.  


