
# Aplicacion para calcular la compensacion de CO2 para el proyecto Tree Nations

## Requisitos minimos:

Java 11 y Maven 3.6.3 instalado para compilar el proyecto.
El proyecto se entrega con el jar compilado en la carpeta target.

## Instrucciones para ejecutar el proyecto
1. Descomprimir el archivo .zip en un directorio de su preferencia.

2. Situarse en el directorio
 
``cd [directorio del proyecto]``

3. Para compilar el proyecto se debe ejecutar el siguiente comando:

``mvn clean install``

4. Para ejecutar los tests del proyecto se debe ejecutar el siguiente comando:

``mvn test``

5. Para ejecutar la aplicacion modo CLI para el back el proyecto se debe ejecutar el siguiente comando:

``java -jar target/treenations-0.0.1-SNAPSHOT.jar cli  [RUTA ARCHIVO] [TONS A MINIMAS CO A COMPENSAR]``

Ejemplo:

``java -jar target/treenations-0.0.1-SNAPSHOT.jar cli 2022-COtonesInfo.csv 200``

_puede encoentrar ficheros  de prueba en la carpeta raiz del proyecto con el nombre de 2022-COtonesInfo.csv y 2022-COtonesInfo2.json_

6. Para ejecutar el proyecto modo web para el back el proyecto se debe ejecutar el siguiente comando:

``java -jar target/treenations-0.0.1-SNAPSHOT.jar``

# Frontend

## Requisitos minimos:
Para ejectuar el front del proyecto se debe tener instalado nodejs (v>12) y npm.

## Instrucciones para ejecutar el proyecto

1. Situarse en el directorio
``cd [directorio del proyecto]`` 
``cd treena-front```

2. para instalar las dependencias del proyecto se debe ejecutar el siguiente comando:
``npm install``

3. Para ejecutar el proyecto se debe ejecutar el siguiente comando:
``npm start``

4. La aplicación se ejecutará en el puerto 3000, ingrese a http://localhost:3000/ para ver la aplicacion.

_Puede encontrar ficheros de prueba para subir en la carpeta raiz del proyecto con el nombre de 2022-COtonesInfo.csv y 2022-COtonesInfo2.json_


# Descripción del diseño de la solución

El proyecto se ha dividido en dos partes, el backend y el frontend.
El backend se ha desarrollado en Java 11 con Spring Boot y Maven.
El frontend se ha desarrollado en React con Typescript.

El backend consiste de un modelo de objetos sencillo, un servicio que se encarga de filtrar aquellos meses donde la compensación de CO2 no llega al mínimo de toneladas especificado y un adaptador para cada tipo de fichero de entrada.
En este caso se ha implementado un adaptador para ficheros csv y otro para ficheros json usando bibliotecas específicas para parsear ambos formatos. Bien se podrían seguir desarrollando más adaptadores para otros tipos de ficheros de entrada
Una factory se encarga de devolver el adaptador adecuado en función del tipo de fichero de entrada.
Cada objeto tiene una sola responsabilidad, es decir una sola razón para cambiar. La parte del modelo de adaptadores es extensible mediante polimorfismo implementando una interfaz sin modificar el resto de los adaptadores existentes, como no hay comportamiento común no se ha utilizado una clase abstracta.
Se usa inyección de dependencias para reducir el acoplamiento entre clases. 
Se cumplen los principios SOLID. 

El servicio realiza la lectura del fichero de entrada procesa el contenido con el adaptador, aplica la regla de negocio y devuelve una lista de objetos de tipo compensación CO de aquellos meses que no acumulan el mínimo de toneladas de CO a compensar.
Luego la app de spring boot se encarga de imprimir en pantalla una tabla con aquellos meses que no hayan cumplido el mínimo de toneladas de CO a compensar.

El funcionamiento de la aplicación en modo web o rest api es similar, la diferencia es que en el modo web un endpoint que recibe el fichero y la cantidad de toneladas mínimas a compensar retorna un json con la lista de meses que no cumplen el mínimo de toneladas de CO a compensar.
Este listado se muestra en la aplicación front.


