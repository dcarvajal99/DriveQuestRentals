---

## Requisitos

- ** Java 17+ ** (recomendado Java 21)
- **Maven** para la gestión de dependencias y compilación
- **IntelliJ IDEA** (opcional, recomendado)

---

## 🚀 Ejecución

1. **Clona el repositorio**  
   `git clone <url-del-repo>`

2. **Compila el proyecto**  
   `mvn clean compile`

3. **Ejecuta la aplicación**  
   `mvn exec:java -Dexec.mainClass="com.dcarvajal.drivequestrentals.App"`

---

## 📝 Uso

Al iniciar, verás un menú con opciones:

1. **Arrendar vehículo**: Selecciona tipo, vehículo, usuario y días. Se genera y guarda la boleta.
2. **Listar vehículos**: Muestra todos los vehículos registrados.
3. **Agregar vehículo manualmente**: Ingresa los datos y se valida la patente.
4. **Buscar vehículo por patente**: Busca y muestra los datos de un vehículo.
5. **Eliminar vehículo**: Elimina un vehículo por patente.
6. **Cotizar arriendo**: Muestra vehículos y permite cotizar un arriendo (mínimo 7 días).
7. **Mostrar vehículos con arriendos largos**: Lista vehículos arrendados por 7 días o más.
8. **Buscar boletas por correo**: Muestra todas las boletas asociadas a un correo.
9. **Salir y guardar**: Guarda los datos y termina la aplicación.

---

## 📑 Archivos de datos

- `vehiculos.csv`: Lista de vehículos (tipo, patente, marca, modelo, precio, capacidad/pasajeros, días arrendados, arrendado, correo usuario).
- `usuarios.csv`: Lista de usuarios (nombre, correo, teléfono).
- `boletas.csv`: Historial de boletas (correo, patente, días, subtotal, descuento, IVA, total).

---

## 🛡️ Validaciones

- **Patente**: 6 caracteres alfanuméricos, única.
- **Correo**: Formato válido.
- **Precio y capacidad/pasajeros**: Números positivos.
- **Días de arriendo**: Mínimo 1 (cotización: mínimo 7).

---

## 👨‍💻 Autores

- Diego Carvajal (dcarvajal99)

---

## 📄 Licencia

Este proyecto es solo para fines educativos.

---

## 📝 Notas

- Si editas los archivos CSV manualmente, asegúrate de que cada registro esté en una línea separada.
- El archivo `.gitignore` ya está configurado para excluir archivos temporales, de compilación y de entorno.

---

¡Gracias por usar **DriveQuestRentals**! 🚗🚚
