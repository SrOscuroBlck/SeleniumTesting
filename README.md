# Taller de Automatización con Selenium WebDriver

## Descripción del Proyecto
Este proyecto implementa la automatización de flujos de **login** y **compra de productos** utilizando **Selenium WebDriver** con **Java**. Los datos de usuario y productos se consumen desde un archivo **Excel**, y se aplican técnicas como esperas implícitas y explícitas, así como aserciones para validar los pasos clave. El proyecto fue desarrollado como parte del taller de automatización basado en la página de demostración de [OpenCart](https://opencart.abstracta.us).

## Particularidades
1. **Cuenta proporcionada no funcional**: La cuenta de prueba proporcionada por el profesor (test@example.com y contraseña password123) no funcionó, por lo que fue necesario crear una nueva cuenta para completar los casos de prueba.
2. **Producto agotado**: El **MacBook** incluido en los datos de prueba estaba agotado al momento de la ejecución. En su lugar, se utilizó un **iMac**, manteniendo la consistencia del flujo de compra.

## Objetivos del Taller
1. Automatizar el flujo de **login** y **compra de productos** utilizando Selenium WebDriver con Java.
2. Aplicar:
    - **Esperas implícitas** y **explícitas**.
    - **Aserciones** para validar pasos clave.
    - **Consumo de datos desde un archivo Excel**.
3. Generar reportes de resultados en consola y Excel.

## Actividades Implementadas
### 1. **Configuración del Proyecto**
- Proyecto estructurado como un **Java Maven** siguiendo el patrón **POM (Page Object Model)**:
    - **src/main/java**: Clases para manejar páginas.
    - **src/test/java**: Clases de prueba.
    - **src/test/resources**: Archivo `testdata.xlsx` para credenciales y productos.
- Dependencias utilizadas:
    - **Selenium WebDriver**
    - **Apache POI** para manejo de Excel.
    - **TestNG** para estructurar casos de prueba.

### 2. **Flujo de Login**
- Credenciales leídas desde el archivo `testdata.xlsx`.
- Flujo implementado:
    - Navegar a "My Account" > "Login".
    - Ingresar las credenciales y realizar login.
    - Validar redirección exitosa a la página de cuenta.
- Técnicas utilizadas:
    - **Esperas implícitas** para la carga de elementos.
    - **Aserciones** para confirmar éxito del login.

### 3. **Flujo de Compra**
- Productos leídos desde `testdata.xlsx`.
- Flujo implementado:
    1. Buscar y agregar productos al carrito:
        - Validar existencia del producto.
        - Establecer cantidad desde el archivo Excel.
    2. Completar la compra:
        - Navegar al carrito y proceder al checkout.
        - Completar detalles de facturación y envío.
        - Confirmar la orden.
- Técnicas utilizadas:
    - **Esperas explícitas** para elementos dinámicos.
    - **Aserciones** para confirmar el éxito de cada paso.

### 4. **Reporte de Resultados**
- **Reporte en consola**:
    - Productos procesados correctamente.
    - Productos no encontrados.
- **Reporte en Excel**: Archivo de salida que registra los resultados (`output_report.xlsx`).

## Resultados de los Test
### Login
- **Caso de login exitoso**:
    - Login realizado correctamente con credenciales válidas.
    - Redirección a la página de cuenta verificada.
- **Caso de login fallido**:
    - Intento con credenciales inválidas correctamente identificado como fallido.

### Compra
- Productos procesados:
    - **iMac**: Cantidad: 1.
    - **iPhone**: Cantidad: 2.
- Sin errores durante el flujo de compra.
- Compra completada exitosamente.

## Estructura del Proyecto
- **Clases POM**:
    - `LoginPage`: Manejo de la página de login.
    - `SearchPage`: Búsqueda de productos.
    - `ProductPage`: Configuración de cantidad y agregar al carrito.
    - `CartPage`: Navegación al carrito y checkout.
    - `CheckoutPage`: Flujo de compra.
- **Clases de Test**:
    - `LoginTest`: Flujo de login.
    - `PurchaseTest`: Flujo de compra.
- **Utilidades**:
    - `ExcelUtils`: Manejo de lectura y escritura de datos en Excel.

## Instrucciones para Ejecutar
1. Clonar el repositorio:
   ```bash
   git clone https://github.com/SrOscuroBlck/SeleniumTesting.git
    ```
2. Importar el proyecto en **Eclipse** o **IntelliJ IDEA**.
3. Ejecutar las clases de prueba:
    - `LoginTest.java`
    - `PurchaseTest.java`
    - **Nota**: Asegurarse de tener **Java** y **Maven** instalados.


## Autor
- Trabajo realizado únicamente por **Gustavo Adolfo Camargo Pineda**, estudiante de la **Universidad San Buenaventura Cali**.

## Aclaraciones
- El proyecto fue desarrollado con fines académicos y de aprendizaje.
- El proyecto fue desarrollado sin basarse en código previo, y se garantiza que cumple con los objetivos planteados.

## Consideraciones Finales
- El proyecto fue desarrollado siguiendo las instrucciones del taller impartido por el profesor **Jorge Luis Fonseca Baldrich**.
- Se agradece la oportunidad de aprender y aplicar las técnicas de automatización con Selenium WebDriver.
- Se espera que el proyecto cumpla con los objetivos y expectativas planteadas.
- Cualquier duda o comentario, favor comunicarse con el autor.

## Contacto
- **Nombre**: Gustavo Adolfo Camargo Pineda
- **Correo**: camargogustavoa@gmail.com
- **GitHub**: [SrOscuroBlck](https://github.com/SrOscuroBlck)
- **LinkedIn**: [Gustavo Camargo](https://www.linkedin.com/in/gustavoadolfocamargopineda/)

## Licencia
No se permite la reproducción total o parcial de este proyecto sin autorización.

© 2024 Gustavo Camargo. Todos los derechos reservados.
