[venecotienda.txt](https://github.com/user-attachments/files/21688657/venecotienda.txt)
SRS VENECOTIENDA


Identificación del Problema
En Argentina, los amantes de la comida venezolana enfrentan una oferta limitada: los restaurantes locales no logran capturar el sabor auténtico de Venezuela debido a ingredientes de baja calidad o recetas adaptadas. Además, la experiencia de pedir comida suele ser poco eficiente y carece de personalización. Esto deja un vacío para quienes buscan una conexión genuina con la gastronomía venezolana, ya sea por nostalgia o curiosidad.
Nuestra Solución
Venecotienda es más que un restaurante: es un puente directo entre Venezuela y Argentina. Importamos ingredientes y productos auténticos desde Venezuela para ofrecer comida de calidad insuperable, combinada con una página web moderna que hace que pedir sea simple, personalizado y eficiente. Nuestro objetivo es:
* Traer el verdadero sabor venezolano con ingredientes originales imposibles de replicar localmente.

* Facilitar una experiencia de usuario moderna, accesible y comparativa con sabores locales argentinos.

* Conectar a los clientes con la cultura venezolana a través de cada plato, mientras los invitamos a descubrir similitudes con su gastronomía.

* Ofrecer una experiencia completa: desde pedir hasta disfrutar, todo está diseñado para ser fácil y memorable.

* Crear una conexión cultural: más que comida, ofrecer un pedazo de Venezuela en cada bocado.







Beneficios Clave
   * Autenticidad: Ingredientes y recetas 100% venezolanos.

   * Personalización: Recomendaciones basadas en gustos y compras previas.

   * Facilidad: Plataforma intuitiva, proceso de compra rápido y eficiente.

   * Cultura: Comparación entre gastronomía venezolana y argentina, fortaleciendo el vínculo cultural.

   * Confiabilidad: Gestión eficiente de pedidos y stock de productos importados.

Enfoque en el Sistema (Página Web)
Nuestra plataforma incluye varias funcionalidades clave para una experiencia de usuario superior:
Roles en el Sistema
      * Usuario (Cliente): Puede registrarse, realizar pedidos, guardar preferencias, recibir recomendaciones y ver historial de compras.

      * Administrador: Gestiona productos, inventario importado, pedidos y usuarios. Supervisa alertas de stock bajo (ej: harina PAN, queso telita).

      * Delivery: Visualiza pedidos asignados y actualiza el estado de entrega en tiempo real.

Recomendaciones de Comida y Productos
         * El sistema analiza el historial y preferencias del cliente (ejemplo: "Siempre pide arepa de queso telita") y sugiere ítems del menú y productos relacionados.
Ejemplos:

            * Si el cliente pide arepas con queso telita, el sistema recomienda cachapas o tajadas como acompañantes.

            * Si compra harina PAN, sugiere un "Kit Arepa Venezolana" (harina + queso telita + guayaba) o un quesillo como postre.

               * Sección destacada "Te recomendamos" con imágenes atractivas.

Comparación Cultural: Comida Venezolana y Argentina
                  * Arepa venezolana ↔ Chipa paraguayo-argentino: Ambos son panes tradicionales, pero la arepa es de maíz precocido y se abre para rellenar, mientras la chipa es de almidón de mandioca y queso.

                  * Cachapa ↔ Tortilla de choclo: Ambas usan maíz tierno; la cachapa es más dulce y gruesa.

                  * Tequeños ↔ Bastones de queso fritos: Similar en concepto, pero el tequeño tiene una masa distinta y un queso característico.

El sistema incluye una sección donde los usuarios pueden descubrir estas comparaciones de forma interactiva, enriqueciendo su experiencia.
Experiencia del Usuario
                     * Plataforma: Solo página web, con diseño cálido que evoque Venezuela (colores de la bandera, imágenes como El Ávila o los Andes venezolanos).

                     * Idiomas: Español como base; opción de cambiar a inglés.

                     * Cuentas de usuario: Permiten guardar preferencias, historial de pedidos y recibir sugerencias más personalizadas.

Sugerencias Técnicas para Implementación
                        * Frontend: React (diseño responsivo, rápido y atractivo).

                        * Backend: Node.js con base de datos MongoDB (para gestión de usuarios, pedidos, productos, inventario importado).

                        * APIs: Integración con servicios de pagos online y, si se simula, servicios de logística para envíos.

                        * Gestión de Inventario: El sistema rastrea productos importados y alerta cuando el stock de ingredientes esenciales está bajo.

Prototipo y Flujo de Usuario
                           * Pantalla "Nuestros Ingredientes": muestra fotos reales de productos llegando desde Venezuela.

                           * Flujo de compra simulado: el cliente elige una arepa → personaliza (relleno, acompañantes) → paga → cocina recibe el pedido → delivery lo entrega.

Pitch Mejorado
"Venecotienda trae Venezuela a tu mesa. Importamos los mejores ingredientes directamente desde Caracas para que cada arepa, cada cachapa, tenga el auténtico sabor de casa. Con nuestra página web moderna, pedir es tan fácil como disfrutar. Gestionamos cada detalle para ofrecer más que comida: entregamos cultura, recuerdos y calidad en cada bocado.
























Venecotienda
             Historial de versiones del documento
Versión
	Fecha
	Descripción
	Autor
	1.0
	10/04/2025
	Creación del  documento
	Manuel Blanco Lievano Moron 
	________________


   Tabla de Contenidos
                              1. Objetivo
                              2. Beneficios
                              3. Alcance
                              4. Limitaciones
                              5. Requisitos no funcionales globales
                              6. Módulos
 6.1. Módulo de Cliente
  6.1.1. Requisitos funcionales
  6.1.2. Requisitos no funcionales
 6.2. Módulo de Administrador
  6.2.1. Requisitos funcionales
  6.2.2. Requisitos no funcionales
 6.3. Módulo de Delivery
  6.3.1. Requisitos funcionales
  6.3.2. Requisitos no funcionales
                              7. Prototipos de interfaz
                              8. Glosario

________________


1. Objetivo
Venecotienda es una página web que conecta a los amantes de la gastronomía venezolana en Argentina con productos y platos auténticos importados directamente desde Venezuela. Permite realizar pedidos de comida, comprar productos originales, recibir recomendaciones personalizadas, y disfrutar de una experiencia cultural completa.
________________


2. Beneficios
                                 * Ofrece sabores auténticos venezolanos imposibles de replicar localmente.

                                 * Plataforma moderna, accesible y personalizada.

                                 * Experiencia de usuario fluida y sencilla.

                                 * Conexión cultural entre Venezuela y Argentina.

                                 * Recomendaciones inteligentes basadas en historial de pedidos.

                                 * Gestión eficiente de inventario de productos importados.

________________


3. Alcance
El sistema contempla:
                                    * Cliente: registro, inicio de sesión, pedido de comida/productos, recomendaciones personalizadas, historial de pedidos.

                                    * Administrador: gestión de usuarios, productos, pedidos y stock de inventario.

                                    * Delivery: gestión de órdenes asignadas y actualizaciones de estado de entrega.

________________


4. Limitaciones
                                       * Solo disponible como página web (no aplicación móvil).

                                       * Importación de productos depende de terceros (logística externa).

                                       * Cobertura de entregas limitada a zonas específicas de Argentina.

________________


5. Requisitos no funcionales globales
                                          * RNFG1: El sitio debe cargar en menos de 3 segundos.

                                          * RNFG2: El sistema debe ser accesible 24/7 excepto en mantenimientos programados.

                                          * RNFG3: La página debe ser responsive (adaptable a dispositivos móviles y de escritorio).

                                          * RNFG4: Seguridad de datos personales mediante protocolos HTTPS y cifrado de contraseñas.

________________


6. Módulos
6.1. Módulo de Cliente
6.1.1. Requisitos funcionales
                                             * RF-C1: El cliente debe poder registrarse y crear una cuenta.

                                             * RF-C2: El cliente debe poder iniciar sesión en su cuenta.

                                             * RF-C3: El cliente debe poder buscar y navegar productos/comidas.

                                             * RF-C4: El sistema debe mostrar recomendaciones basadas en historial de pedidos.

                                             * RF-C5: El cliente debe poder personalizar sus pedidos (ej: agregar queso telita).

                                             * RF-C6: El cliente debe poder pagar su pedido mediante múltiples métodos de pago.

                                             * RF-C7: El cliente debe poder ver su historial de pedidos.

6.1.2. Requisitos no funcionales
                                                * RNF-C1: El proceso de registro debe tomar menos de 2 minutos.

                                                * RNF-C2: El login debe autenticarse en menos de 1 segundo.

                                                * RNF-C3: El motor de recomendaciones debe actualizarse en tiempo real tras cada compra.

________________


6.2. Módulo de Administrador
6.2.1. Requisitos funcionales
                                                   * RF-A1: El administrador debe poder ver, agregar, editar y eliminar productos.

                                                   * RF-A2: El administrador debe poder gestionar usuarios (bloquear, activar).

                                                   * RF-A3: El sistema debe alertar al administrador cuando el stock de un producto esté bajo (ej: harina PAN).

                                                   * RF-A4: El administrador debe poder ver todos los pedidos y su estado.

6.2.2. Requisitos no funcionales
                                                      * RNF-A1: La gestión de productos debe reflejar cambios en el sistema en menos de 30 segundos.

                                                      * RNF-A2: Las alertas de bajo stock deben enviarse automáticamente al panel de administración.

________________


6.3. Módulo de Delivery
6.3.1. Requisitos funcionales
                                                         * RF-D1: El delivery debe iniciar sesión en su cuenta.

                                                         * RF-D2: El delivery debe poder ver sus órdenes asignadas.

                                                         * RF-D3: El delivery debe actualizar el estado de cada pedido (ej: "en camino", "entregado").

6.3.2. Requisitos no funcionales
                                                            * RNF-D1: El cambio de estado de un pedido debe reflejarse en el sistema en tiempo real.

                                                            * RNF-D2: La sesión de delivery debe mantenerse activa al menos 24 horas salvo cierre manual.

________________


7. Prototipos de Interfaz
                                                               * Página principal con menú de productos destacados.

                                                               * Página de recomendaciones personalizadas ("Te recomendamos").

                                                               * Formulario de registro/login.

                                                               * Panel de administración de productos, usuarios y stock.

                                                               * Panel de delivery para gestión de pedidos.

(Se incluirán mockups gráficos en versión extendida del proyecto).
________________


8. Glosario
Término
	Definición
	Cliente
	Usuario que compra productos o pide comida en Venecotienda.
	Administrador
	Usuario con permisos para gestionar el sistema (productos, stock, usuarios).
	Delivery
	Usuario encargado de entregar los pedidos.
	Stock
	Cantidad disponible de un producto importado.
	Producto Importado
	Producto traído directamente desde Venezuela (ej: harina PAN, queso telita).
	Pedido
	Solicitud de comida o productos realizada por el cliente.
	Motor de Recomendaciones
	Sistema que sugiere productos basados en el historial de compras.
	Inventario
	Registro total de productos disponibles en el sistema.
	Responsive
	Adaptabilidad de la página web a distintos tamaños de pantalla.
	HTTPS
	Protocolo de seguridad para proteger la transmisión de datos.
	Usuario
	Toda persona que interactúa con el sistema, sea cliente, administrador o delivery.
	Página Web
	Plataforma en línea de Venecotienda donde se realizan pedidos y gestiones.
	Sistema
	Conjunto de módulos, interfaces y funcionalidades que componen Venecotienda.
	Recomendaciones Personalizadas
	Sugerencias específicas ofrecidas al usuario basadas en sus compras anteriores.
	Arepa
	Plato tradicional venezolano hecho a base de masa de maíz precocido.
	Queso Telita
	Tipo de queso fresco venezolano, suave y elástico, muy utilizado en arepas.
	Harina PAN
	Marca de harina de maíz precocida esencial para preparar arepas, cachapas y otros platos venezolanos.
	Cachapa
	Tortilla gruesa y dulce hecha de maíz tierno, típica de la gastronomía venezolana.
	Empanada Venezolana
	Masa de maíz rellena de diversos ingredientes, frita hasta quedar crujiente.
	Tequeño
	Palito de queso envuelto en masa de trigo, frito hasta dorar, típico en celebraciones venezolanas.
	Golfeado
	Panecillo dulce en espiral, relleno de papelón (azúcar de caña) y queso, tradicional en Venezuela.
	Papelón
	Endulzante natural venezolano hecho a base de jugo de caña de azúcar solidificado.
	Guasacaca
	Salsa venezolana a base de palta (aguacate), ajo, cilantro y otros condimentos.
	Reina Pepiada
	Arepa rellena de ensalada de pollo con palta, uno de los rellenos más icónicos.
	Maíz Precocido
	Maíz que ha sido parcialmente cocido y luego procesado, base de muchos platos venezolanos.
