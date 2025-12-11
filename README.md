# 🚀 Examen Final: Sistema Enterprise ERP - Patrones de Diseño

Este proyecto consiste en una aplicación web completa para la gestión de inventarios y pedidos, desarrollada con **Spring Boot** y **Java 21**. El objetivo principal es demostrar la implementación práctica de diversos **Patrones de Diseño de Software** en un escenario empresarial realista.

---

## 📋 Tabla de Contenidos
1. [Descripción General](#-descripción-general)
2. [Patrones de Diseño Implementados](#-patrones-de-diseño-implementados)
3. [Requisitos Previos](#-requisitos-previos)
4. [Instalación y Ejecución](#-instalación-y-ejecución)
5. [Guía de Pruebas (Walkthrough)](#-guía-de-pruebas-walkthrough)
6. [Arquitectura del Proyecto](#-arquitectura-del-proyecto)

---

## 📖 Descripción General
El sistema **Enterprise ERP** simula una tienda tecnológica con un panel administrativo integrado (Backoffice). Permite a los usuarios navegar por un catálogo, gestionar pedidos, gestionar el stock en tiempo real y acceder a reportes financieros protegidos, habilitar o desabilitar metodos de pago, todo ello orquestado mediante patrones de diseño.

**Tecnologías:**
* **Backend:** Java 21, Spring Boot 3.2.6 (Web).
* **Frontend:** HTML5, CSS3 (Glassmorphism UI), JavaScript (Vanilla).
* **Persistencia:** Repositorios en memoria (`ConcurrentHashMap`) para simplicidad y rapidez en la demostración.
* **Build Tool:** Maven.

---

## 🧩 Patrones de Diseño Implementados

Este proyecto implementa **7 patrones de diseño** clave para resolver problemas específicos:

| Patrón | Tipo | Aplicación en el Proyecto | Ubicación Clave en Código |
| :--- | :--- | :--- | :--- |
| **Strategy** | Comportamiento | Define cómo se calcula el precio final de un producto (Estándar, Descuento, Dinámico). | `com.proyecto.patrones.patrones.strategy` |
| **Observer** | Comportamiento | Monitorea el stock. Si baja del mínimo, notifica automáticamente a los roles interesados. | `com.proyecto.patrones.patrones.observer` |
| **Command** | Comportamiento | Encapsula la creación de un pedido como una orden ejecutable y reversible. | `com.proyecto.patrones.servicio.ServicioComando` |
| **Memento** | Comportamiento | Guarda el estado del stock antes de un pedido para permitir la función **"Deshacer"**. | `com.proyecto.patrones.patrones.memento` |
| **Iterator** | Comportamiento | Permite recorrer el catálogo de productos con paginación sin exponer la estructura subyacente. | `com.proyecto.patrones.controlador.CatalogoControlador` |
| **Proxy** | Estructural | Protege el acceso a los reportes financieros, validando roles y contraseñas antes de llegar al servicio real. | `com.proyecto.patrones.patrones.proxy` |
| **Adapter** | Estructural | Unifica la interfaz de múltiples pasarelas de pago (PayPal, Yape, Plin) para que el sistema las trate de forma homogénea. | `com.proyecto.patrones.patrones.adapter` |

---

## 🛠 Requisitos Previos

Asegúrese de tener instalado:
* **Java JDK 21** (o superior).
* **Maven** 3.6+ (o usar el wrapper incluido `mvnw`).
* Un navegador web moderno.

---

## 🚀 Instalación y Ejecución

1.  **Clonar o descomprimir el proyecto:**
    Asegúrese de estar en la carpeta raíz (`ExamenFinal`).
    
2.  **Acceder a la Aplicación:**
    Una vez que la consola muestre `Started ExamFinalApplication`, abra su navegador y vaya a:
    
    👉 **http://localhost:8080/index.html**

---

## 🧪 Guía de Pruebas (Walkthrough)

Para evaluar los patrones implementados, siga estos pasos en la interfaz:

### 1. Probando el Patrón **Strategy** (Precios)
* Vaya a la columna **"Catálogo de Productos"**.
* En cualquier tarjeta de producto, busque el selector de "ESTRATEGIA".
* Cambie de `Estándar` a `Descuento`. Modifique el descuento y verá que el precio baja inmediatamente.
* Cambie a `Dinámico` y coloque un valor (ej: `0.20` para aumentar 20% o `-0.10` para bajar).
* **Resultado:** El algoritmo de precios cambia en caliente sin modificar el código del producto.

### 2. Probando **Command** y **Memento** (Pedidos y Deshacer)
* Seleccione un producto en el panel **"Nueva Orden"** y haga clic en `+`.
* Seleccione metodo de pago
* Haga clic en **"CONFIRMAR PEDIDO"**.
* Observe el panel **"Historial"** abajo a la derecha. Aparecerá el nuevo pedido.
* Haga clic en el botón rojo **"DESHACER"** (o "UNDO") en el historial.
* **Resultado:** El pedido cambia a estado "CANCELADO" y, lo más importante, **el stock se restaura automáticamente** a su valor original (Memento).

### 3. Probando **Observer** (Stock y Alertas)
* Vaya al panel **"Gestión Stock (Observer)"** (columna derecha).
* Seleccione un producto.
* Cambie el **Stock Real** a un número bajo (ej: `2`) y guarde.
* Asegúrese de que el **Mínimo** sea mayor (ej: `5`).
* **Resultado:** En el catalogo se colocara en alerta en rojo y en la terminal negra de abajo ("Logs"), aparecerá inmediatamente un mensaje de alerta del sistema, indicando que los observadores han sido notificados.

### 4. Probando **Proxy** (Seguridad)
* Vaya al panel **"Reportes (Proxy)"**.
* Intente acceder con el Rol `VENDEDOR`. **Resultado:** Acceso denegado (Bloqueo Proxy).
* Cambie al Rol `GERENTE`.
* Ingrese la contraseña correcta: `gerente123`.
* Haga clic en "Solicitar Acceso".
* **Resultado:** El Proxy valida las credenciales y permite el paso al servicio real, mostrando el reporte financiero.

### 5. Probando **Adapter** (Pasarelas de Pago)
* Vaya al panel **"Pasarelas (Singleton)"** (columna derecha inferior).
* Desactive una pasarela, por ejemplo **"YAPE"** (botón OFF).
* Intente crear un pedido seleccionando **Yape** como método de pago.
* **Resultado:** El sistema rechaza el pedido indicando que el método no está disponible (gestión unificada de pasarelas).
* Active **"YAPE"** (ON) e intente de nuevo.
* **Resultado:** El pedido se procesa correctamente usando la implementación del adaptador correspondiente.

---

## 📂 Arquitectura del Proyecto

La estructura de paquetes sigue una arquitectura en capas limpia:

```text
com.proyecto.patrones
├── controlador       # REST Controllers (API Endpoints)
├── dominio           # Entidades del negocio (Producto, Pedido, Usuario)
├── dto               # Data Transfer Objects
├── patrones          # IMPLEMENTACIÓN DE PATRONES
│   ├── adapter       # Adaptadores de pago (PayPal, Yape, Plin)
│   ├── command       # Interfaz comando
│   ├── iterator      # Lógica de iteración
│   ├── memento       # Instantáneas de estado
│   ├── observer      # Sistema de notificaciones
│   ├── proxy         # Seguridad de reportes
│   └── strategy      # Algoritmos de precio
├── repositorio       # Persistencia en memoria (HashMap)
└── servicio          # Lógica de negocio y orquestación

Autor: Jeysson Fernando Perez Rafael

Curso: Patrones de Diseño de Software

Fecha: 2025
