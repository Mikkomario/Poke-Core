# Poke
Version: **v1.0**  
Updated: 2023-12-01

## Table of Contents
- [Enumerations](#enumerations)
  - [Critical Rate](#critical-rate)
  - [Move Category](#move-category)
  - [Poke Type](#poke-type)
  - [Stat](#stat)
- [Packages & Classes](#packages-and-classes)
  - [Game](#game)
    - [Ability](#ability)
    - [Game](#game)
    - [Item](#item)
  - [Poke](#poke)
    - [Evo](#evo)
    - [Evo Move](#evo-move)
    - [Move Learn](#move-learn)
    - [Poke](#poke)
    - [Poke Ability](#poke-ability)
    - [Poke Stat](#poke-stat)
  - [Randomization](#randomization)
    - [Battle Encounter](#battle-encounter)
    - [Move](#move)
    - [Randomization](#randomization)
    - [Starter Assignment](#starter-assignment)
    - [Starter Set](#starter-set)
    - [Wild Encounter](#wild-encounter)

## Enumerations
Below are listed all enumerations introduced in Poke, in alphabetical order  

### Critical Rate

Key: `id: Int`  
Default Value: **Normal**

**Values:**
- **Normal** (1)
- **Increased** (2)
- **Guaranteed** (3)

Utilized by the following 1 classes:
- [Move](#move)

### Move Category
An enumeration for whether something is physical-focused or special-focused

Key: `id: Int`  

**Values:**
- **Physical** (1)
- **Special** (2)

### Poke Type
Represents a damage type in the game

Key: `id: Int`  

**Values:**
- **Normal** (1)
- **Fighting** (2)
- **Flying** (3)
- **Grass** (4)
- **Water** (5)
- **Fire** (6)
- **Rock** (7)
- **Ground** (8)
- **Psychic** (9)
- **Bug** (10)
- **Dragon** (11)
- **Electric** (12)
- **Ghost** (13)
- **Poison** (14)
- **Ice** (15)
- **Steel** (16)
- **Dark** (17)
- **Fairy** (18)

Utilized by the following 2 classes:
- [Move](#move)
- [Poke](#poke)

### Stat
Represents a poke's attribute in the game

Key: `id: Int`  

**Values:**
- **Hp** (1)
- **Attack** (2)
- **Special Attack** (3)
- **Defense** (4)
- **Special Defense** (5)
- **Speed** (6)

Utilized by the following 1 classes:
- [Poke Stat](#poke-stat)

## Packages and Classes
Below are listed all classes introduced in Poke, grouped by package and in alphabetical order.  
There are a total number of 3 packages and 15 classes

### Game
This package contains the following 3 classes: [Ability](#ability), [Game](#game), [Item](#item)

#### Ability
Represents a special ability that a poke can have

##### Details
- Uses a **combo index**: `game_id` => `index`

##### Properties
Ability contains the following 3 properties:
- **Game Id** - `gameId: Int` - Id of the game in which this ability appears
  - Refers to [Game](#game)
- **Index** - `index: Int` - Index of this ability in the game
- **Name** - `name: String` - Name of this ability in the game

##### Referenced from
- [Poke Ability](#poke-ability).`abilityId`

#### Game
Represents a pokemon game entry

##### Details

##### Properties
Game contains the following 1 properties:
- **Name** - `name: String` - Name of this game

##### Referenced from
- [Ability](#ability).`gameId`
- [Item](#item).`gameId`
- [Randomization](#randomization).`gameId`

#### Item
Represents an item in a game

##### Details
- Uses a **combo index**: `game_id` => `index`

##### Properties
Item contains the following 3 properties:
- **Game Id** - `gameId: Int` - Id of the game in which this item appears
  - Refers to [Game](#game)
- **Index** - `index: Int` - Index of this item within the game
- **Name** - `name: String` - Name of this item in the game

##### Referenced from
- [Evo](#evo).`itemId`

### Poke
This package contains the following 6 classes: [Evo](#evo), [Evo Move](#evo-move), [Move Learn](#move-learn), [Poke](#poke), [Poke Ability](#poke-ability), [Poke Stat](#poke-stat)

#### Evo
Represents a permanent change from one poke form to another

##### Details

##### Properties
Evo contains the following 4 properties:
- **From Id** - `fromId: Int` - Id of the poke from which this evo originates
  - Refers to [Poke](#poke)
- **To Id** - `toId: Int` - Id of the poke to which this evo leads
  - Refers to [Poke](#poke)
- **Level Threshold** - `levelThreshold: Option[Int]` - The level at which this evo is enabled. None if not level-based
- **Item Id** - `itemId: Option[Int]` - Id of the item associated with this evo
  - Refers to [Item](#item)

#### Evo Move
Represents a poke's ability to learn a move when evolving

##### Details

##### Properties
Evo Move contains the following 2 properties:
- **Poke Id** - `pokeId: Int` - Id of the poke (form) learning this move
  - Refers to [Poke](#poke)
- **Move Id** - `moveId: Int` - id of the move learnt
  - Refers to [Move](#move)

#### Move Learn
Represents a poke's ability to learn a move

##### Details
- Uses a **combo index**: `poke_id` => `level`

##### Properties
Move Learn contains the following 3 properties:
- **Poke Id** - `pokeId: Int` - Id of the poke that learns the move
  - Refers to [Poke](#poke)
- **Move Id** - `moveId: Int` - Id of the move learnt
  - Refers to [Move](#move)
- **Level** - `level: Int` - Level at which this move is learnt, if applicable.

#### Poke
Represents a randomized poke from one of the games

##### Details
- Combines with [Starter Assignment](#starter-assignment), creating a **Starter**
- Uses a **combo index**: `randomization_id` => `name`

##### Properties
Poke contains the following 6 properties:
- **Randomization Id** - `randomizationId: Int` - The randomization in which this poke appears
  - Refers to [Randomization](#randomization)
- **Number** - `number: Int` - The pokedex-number of this poke in the game in which it appears
- **Forme Index** - `formeIndex: Int`, `0` by default - 0-based index of the specific poke "forme" represented by this instance. 0 is the default forme.
- **Name** - `name: String` - Name of this poke in the english translation / game data
- **Primary Type** - `primaryType: PokeType` - The primary type of this poke
- **Secondary Type** - `secondaryType: Option[PokeType]` - The secondary type of this poke. None if this poke only has one type

##### Referenced from
- [Battle Encounter](#battle-encounter).`pokeId`
- [Evo](#evo).`fromId`
- [Evo](#evo).`toId`
- [Evo Move](#evo-move).`pokeId`
- [Move Learn](#move-learn).`pokeId`
- [Poke Ability](#poke-ability).`pokeId`
- [Poke Stat](#poke-stat).`pokeId`
- [Starter Assignment](#starter-assignment).`pokeId`
- [Wild Encounter](#wild-encounter).`pokeId`

#### Poke Ability
Represents an ability that's possible for a poke to have

##### Details

##### Properties
Poke Ability contains the following 3 properties:
- **Poke Id** - `pokeId: Int` - Id of the poke that may have this ability
  - Refers to [Poke](#poke)
- **Ability Id** - `abilityId: Int` - Id of the ability this poke may have
  - Refers to [Ability](#ability)
- **Is Hidden** - `isHidden: Boolean` - True if this is a hidden ability. Hidden abilities may only be acquired with special measures.

#### Poke Stat
Represents a (base) stat assigned to a poke

##### Details
- Uses a **combo index**: `poke_id` => `stat_id`

##### Properties
Poke Stat contains the following 3 properties:
- **Poke Id** - `pokeId: Int` - Id of the described poke
  - Refers to [Poke](#poke)
- **Stat** - `stat: Stat` - Described stat / attribute
- **Value** - `value: Int` - Assigned value, between 10 and 255

### Randomization
This package contains the following 6 classes: [Battle Encounter](#battle-encounter), [Move](#move), [Randomization](#randomization), [Starter Assignment](#starter-assignment), [Starter Set](#starter-set), [Wild Encounter](#wild-encounter)

#### Battle Encounter
Represents a scripted encounter with a poke in a trainer battle

##### Details
- Uses a **combo index**: `randomization_id` => `level`

##### Properties
Battle Encounter contains the following 4 properties:
- **Randomization Id** - `randomizationId: Int` - Id of the randomization where this encounter applies
  - Refers to [Randomization](#randomization)
- **Poke Id** - `pokeId: Int` - Id of the encountered poke
  - Refers to [Poke](#poke)
- **Level** - `level: Int` - Level at which this poke is encountered
- **Number Of Encounters** - `numberOfEncounters: Int`, `1` by default - Number of individual encounters represented by this instance

#### Move
Represents a possibly randomized move

##### Details
- Uses a **combo index**: `randomization_id` => `name`

##### Properties
Move contains the following 9 properties:
- **Randomization Id** - `randomizationId: Int` - Id of the randomization where these details apply
  - Refers to [Randomization](#randomization)
- **Index** - `index: Int` - Index of this move in the game data
- **Name** - `name: String` - Name of this move in the game
- **Move Type** - `moveType: PokeType` - Type of this move
- **Category** - `category: Option[MoveCategory]` - Category of this move, whether physical or special. None if neither.
- **Damage** - `damage: Int` - The amount of damage inflicted by this move, in game units
- **Hit Count** - `hitCount: Double`, `1.0` by default - The (average) hit count of this move
- **Hit Ratio** - `hitRatio: Double`, `100.0` by default - The ratio of how often this move hits, where 100 is 100% hit without accuracy or evasion mods
- **Critical Rate** - `criticalRate: CriticalRate` - Rate of critical hits applied to this move

##### Referenced from
- [Evo Move](#evo-move).`moveId`
- [Move Learn](#move-learn).`moveId`

#### Randomization
Represents a single, typically randomized, game version / ROM

##### Details
- **Chronologically** indexed
- Uses a **combo index**: `is_original`
- Uses **index**: `created`

##### Properties
Randomization contains the following 3 properties:
- **Game Id** - `gameId: Int` - Id of the targeted game / ROM
  - Refers to [Game](#game)
- **Created** - `created: Instant` - Time when this randomization was added to the database
- **Is Original** - `isOriginal: Boolean` - True if this represents an unmodified copy of the game

##### Referenced from
- [Battle Encounter](#battle-encounter).`randomizationId`
- [Move](#move).`randomizationId`
- [Poke](#poke).`randomizationId`
- [Starter Set](#starter-set).`randomizationId`
- [Wild Encounter](#wild-encounter).`randomizationId`

#### Starter Assignment
Represents a starter poke-assignment in a game

##### Details

##### Properties
Starter Assignment contains the following 3 properties:
- **Set Id** - `setId: Int` - Id of the starter set to which this starter belongs
  - Refers to [Starter Set](#starter-set)
- **Poke Id** - `pokeId: Int` - Id of the poke that appears as a starter
  - Refers to [Poke](#poke)
- **Index** - `index: Int` - A zero-based index that shows where this starter appears relative to the others. The following index is typically strong against the previous index.

#### Starter Set
Represents a set of (3) starters that appears in a game

##### Details
- Combines with multiple [Starter Assignments](#starter-assignment), creating a **Detailed Starter Set**
- Uses a **combo index**: `randomization_id` => `index`

##### Properties
Starter Set contains the following 2 properties:
- **Randomization Id** - `randomizationId: Int` - Id of the randomization where these starters appear
  - Refers to [Randomization](#randomization)
- **Index** - `index: Int` - Relative set index within the game, 0-based

##### Referenced from
- [Starter Assignment](#starter-assignment).`setId`

#### Wild Encounter
Represents a random encounter with a wild poke

##### Details
- Uses a **combo index**: `randomization_id` => `min_level`

##### Properties
Wild Encounter contains the following 5 properties:
- **Randomization Id** - `randomizationId: Int` - Randomization in which this encounter applies
  - Refers to [Randomization](#randomization)
- **Zone Index** - `zoneIndex: Int` - Index of the zone / map in which this encounter applies
- **Poke Id** - `pokeId: Int` - Id of the poke that may be encountered
  - Refers to [Poke](#poke)
- **Level Range** - `levelRange: NumericSpan[Int]` - Range of levels that are possible for this encounter
- **Number Of Encounters** - `numberOfEncounters: Int`, `1` by default - Number of individual encounters represented by this instance
