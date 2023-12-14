# Poke Companion
Version: **v1.0**  
Updated: 2023-12-13

## Table of Contents
- [Packages & Classes](#packages-and-classes)
  - [Gameplay](#gameplay)
    - [Attack Experiment](#attack-experiment)
    - [Poke Capture](#poke-capture)
    - [Poke Training](#poke-training)
    - [Run](#run)
    - [Type Guess](#type-guess)

## Packages and Classes
Below are listed all classes introduced in Poke Companion, grouped by package and in alphabetical order.  
There are a total number of 1 packages and 5 classes

### Gameplay
This package contains the following 5 classes: [Attack Experiment](#attack-experiment), [Poke Capture](#poke-capture), [Poke Training](#poke-training), [Run](#run), [Type Guess](#type-guess)

#### Attack Experiment
Represents an attempt to attack an opposing poke with some damage type. Used for revealing information about the opponent's typing.

##### Details
- Uses a **combo index**: `opponent_id` => `attack_type_id`

##### Properties
Attack Experiment contains the following 4 properties:
- **Run Id** - `runId: Int` - Id of the game run on which this attempt was made
  - Refers to [Run](#run)
- **Opponent Id** - `opponentId: Int` - Id of the opposing poke
  - Refers to *poke* from another module
- **Attack Type** - `attackType: PokeType` - Type of attack used against the poke
- **Created** - `created: Instant` - Time when this attack experiment was added to the database

#### Poke Capture
Represents the event of capturing some poke

##### Details

##### Properties
Poke Capture contains the following 4 properties:
- **Run Id** - `runId: Int` - Id of the game run on which this capture was made
  - Refers to [Run](#run)
- **Poke Id** - `pokeId: Int` - Id of the captured poke
  - Refers to *poke* from another module
- **Level** - `level: Int` - Level at which this capture was made
- **Created** - `created: Instant` - Time when this poke capture was added to the database

#### Poke Training
Represents an event / recording of a trained poke reaching a specific level

##### Details
- Preserves **history**
- Uses **index**: `deprecated_after`

##### Properties
Poke Training contains the following 5 properties:
- **Run Id** - `runId: Int` - Id of the run on which this event occurred
  - Refers to [Run](#run)
- **Poke Id** - `pokeId: Int` - Id of the trained poke
  - Refers to *poke* from another module
- **Level** - `level: Int` - Reached level
- **Created** - `created: Instant` - Time when this poke training was added to the database
- **Deprecated After** - `deprecatedAfter: Option[Instant]` - Time when this event was replaced with a more recent recording

#### Run
Represents a gameplay run of a randomized game version

##### Details
- **Chronologically** indexed
- Uses 2 database **indices**: `name`, `created`

##### Properties
Run contains the following 3 properties:
- **Name** - `name: String` - Name of this run, for example consisting of the player name
- **Randomization Id** - `randomizationId: Int` - Id of the randomization that's applied to this game run
  - Refers to *randomization* from another module
- **Created** - `created: Instant` - Time when this run was added to the database

##### Referenced from
- [Attack Experiment](#attack-experiment).`runId`
- [Poke Capture](#poke-capture).`runId`
- [Poke Training](#poke-training).`runId`
- [Type Guess](#type-guess).`runId`

#### Type Guess
Represents the user's guess at a poke's type

##### Details
- Preserves **history**
- Uses **index**: `deprecated_after`

##### Properties
Type Guess contains the following 5 properties:
- **Run Id** - `runId: Int` - Id of the game run on which this guess was made
  - Refers to [Run](#run)
- **Poke Id** - `pokeId: Int` - Id of the described poke
  - Refers to *poke* from another module
- **Guessed Type** - `guessedType: PokeType` - Poke type of this type guess
- **Created** - `created: Instant` - Time when this type guess was added to the database
- **Deprecated After** - `deprecatedAfter: Option[Instant]` - Time when this guess was replaced or when it became invalid
