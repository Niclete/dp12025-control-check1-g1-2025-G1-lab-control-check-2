# 🧩 Control Check 2 – Grupo G1 

## 🧾 Enunciado

En este ejercicio, añadiremos la funcionalidad de **soporte al uso de asistentes de IA para el aprendizaje del ajedrez**.

Concretamente, se proporciona una clase `AIAgent` que representa un agente de ayuda al juego de Inteligencia Artificial que será usado ambos usuarios a lo largo de las partidas. La clase `AIAgentEvaluation` representa la evaluación del usuario para el agente que puede emitirse una vez terminada cada partida por cada jugador. Además se proporciona el enumerado `AIAgentType` que toma los valores `ExpertSystem`, `RuleBasedSystem`, y `LargeLanguageModel` se usa para clasificar el tipo de agente de IA usado.

Estas clases se relacionan a su vez con las clases existentes en el dominio como son `User`, `ChessEvent`, `Federation` y `ChessMatch` (esta última representa una partida).

El diagrama UML que describe las clases y relaciones con las que vamos a trabajar es el siguiente: 

![Class diagram](https://kroki.io/plantuml/svg/bLNRRXen47ttLupeGzi7q58fLRMbKI2XLIHIHI7v06FFBbQyzihs9gLD_xtZXGKlf5AxJtZEcJbpixdmWRlGL9gzXGNVs2OmZKM0OC6fSXr0AeSYA6kOVrIcvevN83JtVXg2KwicuBMmPgb-8cG9nQ-vjC_Ab51mxJ51PArWV9QnjP88Fg136pOrRiz2EQ6HKJBBFJ9SSOyIXDNMnO3l79s-n0mhNd-6bMwGZbmxv78JRJNAzyVPmYy6CEFYiNIsCV8o1fjCxkPNv-VmfYYAhCWYmJg9hWyAaT7tclTiyJ1ltRFYOr4SktTeniy-2Sv-CyQscNpd5Sgv2IfiObQ6ZlaonD8YQSjvyEXW7uDO3HbQ9koeK1FlcGJtbhtpvHgzdp-X2RqGxQHpM5Z1zHKFUAygP50ee_pwnApf6AT-6lqhKeqjbaO_bDUgt_0WrXtydzfxfUdrjAGYvazSDvrWEvGuIm1Rew7Cfaf-SrnIPwvDYRGrBLYHbTgLWW7B7OZcB-2nT2BORtxSoxnZtSVrXSRf0owi3xVeRhYWygviiz99NJFQvyFybUpfescgD30aNQHl_gD65vORkZXLQxXhDCQ0Ch4jk2jnmKtPy19lh4GT8oThCSX68pE0oN3O_XmFagdcO5t93Lrel_DfXzdvf4wxD7EwTCI8VCRQpHsC3y6Zp-aa8GTE5rv6Uazbt4cCHwCFWrFFdLWYrD8xgKEe7EZvCHIzbz6hlBKAUAlvvb_8AqtFm8vCprGBpIntSklHeqOpSMuL2kprV3n87NEe8yEpP34cByHhau5-Uuu7bqnrFmTWfmqV3b_E8LszZlCAagxA5atUB9Oy0AVW1Tsj-6_o1m00)

<!-- 

https://editor.plantuml.com/uml/bLNRRXen47ttLupeGzi7q58fLRMbKI2XLIHIHI7v06FFBbQyzihs9gLD_xtZXGKlf5AxJtZEcJbpixdmWRlGL9gzXGNVs2OmZKM0OC6fSXr0AeSYA6kOVrIcvevN83JtVXg2KwicuBMmPgb-8cG9nQ-vjC_Ab51mxJ51PArWV9QnjP88Fg136pOrRiz2EQ6HKJBBFJ9SSOyIXDNMnO3l79s-n0mhNd-6bMwGZbmxv78JRJNAzyVPmYy6CEFYiNIsCV8o1fjCxkPNv-VmfYYAhCWYmJg9hWyAaT7tclTiyJ1ltRFYOr4SktTeniy-2Sv-CyQscNpd5Sgv2IfiObQ6ZlaonD8YQSjvyEXW7uDO3HbQ9koeK1FlcGJtbhtpvHgzdp-X2RqGxQHpM5Z1zHKFUAygP50ee_pwnApf6AT-6lqhKeqjbaO_bDUgt_0WrXtydzfxfUdrjAGYvazSDvrWEvGuIm1Rew7Cfaf-SrnIPwvDYRGrBLYHbTgLWW7B7OZcB-2nT2BORtxSoxnZtSVrXSRf0owi3xVeRhYWygviiz99NJFQvyFybUpfescgD30aNQHl_gD65vORkZXLQxXhDCQ0Ch4jk2jnmKtPy19lh4GT8oThCSX68pE0oN3O_XmFagdcO5t93Lrel_DfXzdvf4wxD7EwTCI8VCRQpHsC3y6Zp-aa8GTE5rv6Uazbt4cCHwCFWrFFdLWYrD8xgKEe7EZvCHIzbz6hlBKAUAlvvb_8AqtFm8vCprGBpIntSklHeqOpSMuL2kprV3n87NEe8yEpP34cByHhau5-Uuu7bqnrFmTWfmqV3b_E8LszZlCAagxA5atUB9Oy0AVW1Tsj-6_o1m00

@startuml
' Layout
left to right direction
skinparam classAttributeIconSize 0
skinparam shadowing false
skinparam dpi 150
hide stereotype
hide circle

' Stereotype-based coloring (red = to map; blue = already mapped)
skinparam class {
  BackgroundColor<<RED>> #fff0f0
  BorderColor<<RED>> #cc0000
  BackgroundColor<<BLUE>> #f0f6ff
  BorderColor<<BLUE>> #0a58ca
}


class NamedEntity {
 name:String
}

class User <<BLUE>> {
 username: String
 password: String
}

class ChessEvent <<BLUE>> {
 start: LocalDateTime
 finish: LocalDateTime
 location: String
}

class Federation <<BLUE>> {
}


class ChessMatch<<BLUE>>{
 start: LocalDateTime
 finish: LocalDateTime
}

class AIAgentEvaluation <<RED>>{
    openingAidGrade: Ingeter
    midGameAidGrade: Integer
    endGameAidGrade: Integer
}

class AIAgent <<RED>>{
 name: String
 type: AIAgentType
 url: String
 costPerMach: Double
}

class BaseEntity {
 id: Integer
}

enum AIAgentType <<BLUE>>{
    ExpertSystem
    RuleBasedSystem
    LargeLanguageModel
}

ChessEvent "0..n" <-- "0..1" Federation: organizes

ChessMatch "0..n"  <-- "0..1" AIAgent: matches



User "1" <-- "0..n"  AIAgentEvaluation  : aidedUser
ChessMatch "1"  <-- "1..2" AIAgentEvaluation: match

User "1"  <--  "0..n" ChessMatch: winner
User "1" <--  "0..n" ChessMatch: whitePlayer
User "1" <--  "0..n" ChessMatch: blackPlayer

' ChessBoard <--  ChessMatch: board
' Piece "0..n" <-- "1" ChessBoard : pieces
BaseEntity <|-- User  
ChessMatch "1..n" <-- "0..1" ChessEvent : matches 
AIAgentEvaluation --|>  BaseEntity 
AIAgent --|>  BaseEntity 

ChessEvent --|>  NamedEntity
Federation --|>  NamedEntity
@enduml

-->
Las clases marcadas en **rojo** deben ser mapeadas por el alumno como entidades JPA; las clases **azules** son entidades que ya están mapeadas pero que se usarán en algún ejercicio del presente control.


## 🧪 Instrucciones

Tu primera tarea en este control será clonar este repositorio. Si estás utilizando los ordenadores del aula, tendrás que usar un token de autenticación de GitHub como contraseña. En el propio repositorio se incluye un documento de ayuda que explica cómo configurar dicho token. A continuación, deberás importar el proyecto en tu entorno de desarrollo preferido y comenzar a trabajar en los ejercicios que se detallan más abajo. Al importar el proyecto, es posible que inicialmente aparezcan errores de compilación; no te preocupes. Estos errores irán desapareciendo a medida que implementes los ejercicios requeridos para este control.

La funcionalidad implementada en cada uno de los ejercicios será validada mediante pruebas unitarias. Las pruebas pueden ejecutarse desde tu IDE o utilizando:

```bash
./mvnw test
```

Cada ejercicio vale **2 puntos**. En número de casos de prueba varía de un ejericio a otro. Tu nota en cada ejericio dependerá del porcentaje de tests que pasan (no fallan). Por ejemplo:

| Test que pasan | Nota |
|---------------|--------|
| 100% | 2.0 |
| 50% | 1.0 |
| 10% | 0.2 |


### 📤 Envío

La entrega de este control se realizará en **dos pasos:**
1. **Sube la actividad en la plataforma EV asociada al control, proporcionando como texto la URL de tu repositorio personal.** Puedes hacerlo al inicio de la sesión.
2. **Realiza un único *git push* a tu repositorio individual** (el que contiene este documento) una vez que hayas completado todos los ejercicios.

Recuerda que **debes hacer *push* de tu trabajo antes de cerrar sesión en el ordenador y abandonar el aula;** de lo contrario, tu intento será calificado como no entregado.


### ⚠️ Notas importantes

1. **No cambies los nombres de las clases, los nombres de los métodos, los tipos de retorno ni los parámetros.**  
   Las pruebas dependen de que las firmas sean exactamente las mismas.
2. **No modifiques las pruebas unitarias.**  
   Cualquier cambio local en las pruebas será ignorado; tu repositorio se volverá a evaluar desde cero.
3. Mientras los ejercicios no estén completos, **algunas pruebas fallarán** — esto es lo esperado.  
   Solo los proyectos con una calificación de 10 puntos ejecutan todas las pruebas sin fallos.
4. **El uso de Git forma parte de la evaluación.** No se permite pedir ayuda sobre el uso de Git o GitHub.
5. Los proyectos que **no compilen o no arranquen Spring** recibirán **0 puntos**.
6. Excepto el **Ejercicio 2** (depende del 1), los ejercicios son **independientes**. Puedes resolverlos en cualquier orden.
---

## 🧩 Ejercicio 1 – Creación de las entidades Commentary y EngineEvaluation, sus repositorios asociados y relaciones (2 puntos)

### Parte 1.a AIAgent, AIAgentEvaluation y repositorios (1 punto)
Modifique las clases `AIAgent` y `AIAgentEvaluation` para que sean entidades. Estas clases están alojadas en el paquete `es.us.dp1.chess.tournament.ai` y deben tener los siguientes atributos.

Para la clase `AIAgent`:

 * El atributo de tipo entero (`Integer`) llamado '**id**' actuará como clave primaria en la tabla de la base de datos relacional asociada a la entidad.
 * El atributo de tipo cadena de texto (`String`) obligatorio llamado '**name**', que almacenará un título para el comentario. Es un atributo con un tamaño mínimo de `3` caracteres y un tamaño máximo de `50` caracteres, y que no puede estar formado por espacios o tabuladores.
 * El atributo de tipo cadena de texto (`String`) obligatorio llamado '**url**', que almacenará el texto del comentario. Es un atributo con un tamaño mínimo de `10` caracteres, y que no puede estar formado por espacios o tabuladores.
 * El atributo de tipo `AIAgentType` obligatorio llamado '**type**', que indicará el tipo de agente del que se trata, que debe maperse como un enumerado (@Enumerated).
 * Un atributo de tipo numerido decimal (`Double`) obligatorio llamado '**costPerMatch**', que almacenará el coste por partida de uso del agente, su valor debe ser positivo o cero.

Para la clase `AIAgentEvaluation`:

 * El atributo de tipo entero (`Integer`) llamado '**id**' actuará como clave primaria en la tabla de la base de datos relacional asociada a la entidad.
 * El atributo de tipo entero (`Integer`) obligatorio llamado '**opeingAidGrade**', cuyo valor debe estar entre `0` y `10` y representa la evaluación del usuario para la ayuda prestada por el agente durante la fase de apertura de la partida.
 * El atributo de tipo entero (`Integer`) obligatorio llamado '**midGameAidGrade**', cuyo valor debe estar entre `0` y `10` y representa la evaluación del usuario para la ayuda prestada por el agente durante la fase de intermedia de la partida.
 * El atributo de tipo entero (`Integer`) obligatorio llamado '**endGameAidGrade**', cuyo valor debe estar entre `0` y `10` y representa la evaluación del usuario para la ayuda prestada por el agente durante la fase de final de la partida.

No modifique por ahora las anotaciones `@Transient` de las clases. 
Modifique las interfaces `AIAgentRepository` y `AIAgentEvaluationRepository` alojadas en el paquete `es.us.dp1.chess.analysis` respectivamente para que extiendan de `CrudRepository`. No olvide especificar sus parámetros de tipo.


### Parte 1.b Creación de relaciones entre las entidades (1 punto)

Elimine las anotaciones `@Transient` de los métodos y atributos que las tengan en las entidades creadas en el ejercicio anterior, se pide crear las siguientes relaciones:

Para la entidad `AIAgent` cree una  relación unidireccional hacia `ChessMatch` usando el atributo `matches` que exprese la relación que aparecen en el diagrama UML (mostrado en la primera página de este enunciado).

Para la entidad `AIAgentEvaluation` cree dos relaciones unidireccionales, una hacia  `ChessMatch` usando el atributo `match`, y otra hacia `User` usando el atributo `aidedUser` que expresen las relaciones que aparecen en el diagrama UML (mostrado en la primera página de este enunciado).

Debe asegurarse de que todas las relaciones expresan adecuadamente la cardinalidad que muestra el diagrama UML considerando que algunos atributos pueden ser nulos (puesto que la cardinalidad en el extremo navegable es `0..1` o `0..n`) u obligatorios si la cardinalidad es `1` o `1..n`.

## 🧩 Ejercicio 2 – Modificación del script de inicialización de la base de datos (2 puntos)
Modifique el script para crear los siguientes agentes de IA y evaluaciones de agentes de IA:

### Agentes de IA:

#### IAAgent 1
```text
id: 1
name: DeepBlue
url: https://www.ibm.com/history/deep-blue
type: ExpertSystem
costPerMatch: 0.5
```
El agente debe estar asociado a las partidas 5.



#### IAAgent 2
```text
id: 2
name: StockFish
url: https://stockfishchess.org
type: RuleBasedSystem
costPerMatch: 0.0
```
El comentario debe estar asociado a la partida con id = 6

### IAAgentEvaluations

#### IAAgentEval 1
```text
id: 1
openingAidGrade: 6
midGameAidGrade: 8
endGameAidGrade: 3
```
Asociada con la partida con id = 5 y el usuario con id = 4

#### EngineEval 2
```text
id: 2
openingAidGrade: 9
midGameAidGrade: 6
endGameAidGrade: 8
```
La evaluación debe estar asociada con la partida con id = 5 y el usuario con id = 5

> ⚠️ **Recuerde:** el orden en la inserciones es importante para el correcto funcionamiento del script de inicialización de base de datos, especialmente cuando se insertan los datos relativos a las relaciones entre las entidades.

## 🧩 Ejercicio 3 – Controlador y componente frontend para visualizar análisis (2 puntos)

### Parte 3A – Controlador GameAIController (1 punto)

Modificar la clase `GameAIController` para responder a:

```bash
GET /api/v1/games/{gameId}/ai
```

El controlador debe usar GameAnalysisService para obtener todos los datos necesarios (de la partida, de los agentes de IA, y de sus evaluaciones y así como del evento al que está asociada la partida), y debe devolver datos con siguiente formato en JSON:

```json
{
  "gameId": 5,
  "event": "European Rapid Championship 2025",
  "players": ["player3", "player12"],
  "aiAgent": {"name":"...","type":"...",..},   // puede ser nulo
  "evaluations": [                          // puede estar vacío
    {"opeingAidGrade": 2, "midGameAidGrade": 4, "endGameAidGrade": 7,...},
    ...
  ]
}
```

Nótese que en el atributo event aparece el nombre del evento al que pertenece la partida (o null en su defecto). En el array players aparecen los nombre de usuario de los jugadores de la partida (primero el jugador con blancas y después el jugador con negras).

>⚠️ **Nota importante:** ES posible que necesite modificar las consultas de los repositorios de agentes IA y sus evaluaciones para que devuelvan los datos adecuados.

#### Requisitos:

Endpoint disponible solo para usuarios tipo `ADMIN` o `COACH`, en caso contrario debe devolverse un código forbidden (403).
Si no existe la partida, devolver un código not found (404).

### 3.B - Componente React (1 punto)

Modificar el componente `frontend/src/games/ai/index.js` para que:

Reciba un id como propiedad (con nombre gameId), y haga una petición `GET` a `/api/v1/games/{gameId}/ai`.


El componente debe mostrar un título `<h1>` con el texto 'Game AI Data - <Nombre del Evento>', donde el nombre del evento corresponde con la propiedad event del análisis proporcionado por la API.

El componente debe mostrar un título `<h2>` con el texto 'Players' y una lista ordenada con los nombres de usuario de los jugadores.

Además el componente debe mostrar un título  `<h2>` que indique el texto 'No AI agent used in this match' si el agente IA era nulo, y o el texto: 'AI Agent - <name>' si el agente no era nulo. Finalmente el componente debe mostrar una  tabla (con reactstrap), con las evaluaciones realizadas por los jugadores para el agente.
La tabla de evaluaciones debe contener las siguientes columnas:   'Opening', 'Mid Game', 'End Game' y 'Average'. Este última columna debe contener la media de las evaluaciones realizadas para las otras tres.

Para ejecutar las pruebas asociadas a este ejercicio y comprobar su resultado debe colocarse en la carpeta frontend y ejecutar el comando:

```bash
npm test
```
Una vez se muestre el menú de ejecución de pruebas de Jest, pulse 'a' para ejecutar todas las pruebas.

Tenga en cuenta que debe haber ejecutado antes el comando :
```bash
npm install
```
al menos una vez, de manera que todas las librerías de Node necesarias estén instaladas.

## 🧩 Ejercicio 4 – Consulta compleja en GameAnalysisRepository (2 puntos)

Modifique la consulta `findMostWinners` en `GameAnalysisRepository`.
La signatura de la consulta es la siguiente:

```java
List<User> findMostWinners(
    Set<Federation> federations,
    LocalDateTime from
)
```

 Debe devolver los usuarios que han ganado partidas en eventos organizados por las federaciones indicadas como parámetro con partidas a partir de la fecha proporcionada ordenados descendentemente por número de particas partidas ganadas.


## 🧩 Ejercicio 5 – Creación de pruebas unitarias para el algoritmo de recomendación de entrenamiento (2 puntos).

Se proporciona la interfaz `IARecommendationAlgorithm`
que produce una recomendación de tipo de agente IA  a usar por el usuario dependiendo de las partidas ganadas por el ususario mediante el siguiente método:
```java
AIAgentType recommendAIAgentForUser(Integer userId);
```
 Concretamente, se supone que si en el conjunto de partidas del jugador, ha ganado las mismas o más de las que ha perdido no se genera ninguna recomendación el  tipo de IA devuelto sera `null`. Si el usuario ha ganado entre un `49.9%` y un `15%` de las partidas jugadas se le recomienda una  IA básica basada en reglas `RULE_BASED_SYSTEM`. Si el jugador gana menos el `15%` de las partidas jugadas, el tipo de IA recomendada sera `EXPERT_SYSTEM`.
Si el usuario no ha jugado niguna partida se le recomienda el entrenamiento de tipo `LARGE_LANGUAGE_MODEL` para iniciarse en el ajedrez.
Si el usuario tiene partidas sin ganador todavía, se considerarán perdidas (aunque el ganador sea null).
Su prueba debe ser capaz de comprobar el comportamiento y fallar para las implementaciones incorrectas del algoritmo (hay al menos una correcta). Para ello debe completar la clase `IARecommendationAlgorithmTest`.
Recuerde que la prueba de ejecutarse sin hacer uso de mocks, ni Spring (`@DataJpaTest`, `@SpringBootTest`, etc.).

Además los métodos anotados con `@Test` que cree deben ser sin parámetros.
