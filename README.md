# Bazar API

API con Spring Boot para bazar hipotético

## Escenario 

Un bazar ha incrementado en gran medida sus ventas. Dado esto y que le está siendo casi imposible registrar las mismas y manejar el stock de sus productos de forma manual, necesita del desarrollo de una aplicación que le permita realizar esta tarea. 
La dueña del bazar manifiesta que todas las operaciones que tenga la aplicación se deben poder realizar mediante dos tipos de clientes http distintos:

- Una aplicación web, cuyo frontend desarrollará un programador amigo (no será parte de nuestra tarea como desarrolladores backend). 
- Una aplicación Mobile que será implementada a futuro.

Cada una de estas app representa a los dispositivos que ella y sus empleados manejan actualmente. En síntesis: una computadora y varios celulares. 
Dada esta situación particular y de que necesita utilizar el mismo backend para ambas opciones, solicita el desarrollo de una API. 

## Modelado 

A partir del relevamiento que ha llevado a cabo un analista funcional, se detectaron que serán necesarias las siguientes clases: 

- Producto 
- Venta 
- Cliente 

Cada venta posee una lista de productos y uno y solo un cliente asociado. Además de eso, cada clase debe tener los siguientes atributos:

#### Producto

|Atributo|Tipo|
|-|-|
|codigo_producto|Long|
|nombre|String|
|marca|String|
|costo|Double|
|cantidad_disponible|Double|

#### Venta

|Atributo|Tipo|
|-|-|
|codigo_venta|Long|
|fecha_venta|LocalDate|
|total|Double|
|listaProductos|List\<Producto>|
|unCliente|Cliente|

#### Cliente 

|Atributo|Tipo|
|-|-|
|id_cliente|Long|
|nombre|String|
|apellido|String|
|dni|String|

## Requerimientos

A partir del relevamiento realizado respecto al modelado, la dueña del bazar especificó que tiene los siguientes requerimientos: 

#### 1. Poder realizar un CRUD completo de productos
   
- Métodos HTTP: GET, POST, DELETE, PUT
- Endpoints:

  |Acción|Método|URL|
  |-|-|-|
  |Creación|POST|/productos/crear|
  |Lista completa de productos|GET|/productos|
  |Traer un producto en particular|GET|/productos/{codigo_producto}|
  |Eliminación|DELETE|/productos/eliminar/{codigo_producto}|
  |Edición|PUT|/productos/editar/{codigo_producto}|

#### 2. Poder realizar un CRUD completo de clientes

- Métodos HTTP: GET, POST, DELETE, PUT
- Endpoints:  

  |Acción|Método|URL|
  |-|-|-|
  |Creación|POST|/clientes/crear|
  |Lista completa de clientes|GET|/clientes|
  |Traer un cliente en particular|GET|/clientes/{id_cliente}|
  |Eliminación|DELETE|/clientes/eliminar/{id_cliente}|
  |Edición|PUT|/clientes/editar/{id_cliente}|

#### 3. Poder realizar un CRUD completo de ventas

- Métodos HTTP: GET, POST, DELETE, PUT
- Endpoints:

  |Acción|Método|URL|
  |-|-|-|
  |Creación|POST|/ventas/crear |
  |Lista completa de ventas realizadas|GET|/ventas |
  |Traer una venta en particular|GET|/ventas/{codigo_venta} |
  |Eliminación|DELETE|/ventas/eliminar/{codigo_venta} |
  |Edición|PUT|/ventas/editar/{codigo_venta} |

#### 4. Obtener todos los productos cuya cantidad_disponible sea menor a 5 

- Método HTTP: GET
- Endpoint: /productos/falta_stock

#### 5. Obtener la lista de productos de una determinada venta 

- Método HTTP: GET
- Endpoint: /ventas/productos/{codigo_venta}

#### 6. Obtener la sumatoria del monto y también cantidad total de ventas de un determinado día 

- Método HTTP: GET
- Endpoint: /ventas/{fecha_venta}

#### 7. Obtener el codigo_venta, el total, la cantidad de productos, el nombre del cliente y el apellido del cliente de la venta con el monto más alto de todas

- Método HTTP: GET
- Endpoint: /ventas/mayor_venta
