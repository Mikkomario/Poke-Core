-- 
-- Database structure for poke models
-- Version: v1.0
-- Last generated: 2023-12-02
--

CREATE DATABASE IF NOT EXISTS `poke_db` 
	DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;
USE `poke_db`;

--	Game	----------

-- Represents a pokemon game entry
-- name: Name of this game
CREATE TABLE `game`(
	`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	`name` VARCHAR(16), 
	INDEX g_name_idx (`name`)
)Engine=InnoDB DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

-- Represents a special ability that a poke can have
-- game_id:       Id of the game in which this ability appears
-- index_in_game: Index of this ability in the game
-- name:          Name of this ability in the game
CREATE TABLE `ability`(
	`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	`game_id` INT NOT NULL, 
	`index_in_game` INT NOT NULL, 
	`name` VARCHAR(16), 
	INDEX a_combo_1_idx (game_id, index_in_game), 
	CONSTRAINT a_g_game_ref_fk FOREIGN KEY a_g_game_ref_idx (game_id) REFERENCES `game`(`id`) ON DELETE CASCADE
)Engine=InnoDB DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

-- Represents an item in a game
-- game_id:       Id of the game in which this item appears
-- index_in_game: Index of this item within the game
-- name:          Name of this item in the game
CREATE TABLE `item`(
	`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	`game_id` INT NOT NULL, 
	`index_in_game` SMALLINT(4) NOT NULL, 
	`name` VARCHAR(16), 
	INDEX i_combo_1_idx (game_id, index_in_game), 
	CONSTRAINT i_g_game_ref_fk FOREIGN KEY i_g_game_ref_idx (game_id) REFERENCES `game`(`id`) ON DELETE CASCADE
)Engine=InnoDB DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;


--	Randomization	----------

-- Represents a single, typically randomized, game version / ROM
-- game_id:     Id of the targeted game / ROM
-- created:     Time when this randomization was added to the database
-- is_original: True if this represents an unmodified copy of the game
CREATE TABLE `randomization`(
	`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	`game_id` INT NOT NULL, 
	`created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, 
	`is_original` BOOLEAN NOT NULL DEFAULT FALSE, 
	INDEX r_created_idx (`created`), 
	CONSTRAINT r_g_game_ref_fk FOREIGN KEY r_g_game_ref_idx (game_id) REFERENCES `game`(`id`) ON DELETE CASCADE
)Engine=InnoDB DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

-- Represents a possibly randomized move
-- randomization_id: Id of the randomization where these details apply
-- index_in_game:    Index of this move in the game data
-- name:             Name of this move in the game
-- move_type_id:     Type of this move
-- 		References enumeration PokeType
-- 		Possible values are: 1 = normal, 2 = fighting, 3 = flying, 4 = grass, 5 = water, 6 = fire, 7 = rock, 8 = ground, 9 = psychic, 10 = bug, 11 = dragon, 12 = electric, 13 = ghost, 14 = poison, 15 = ice, 16 = steel, 17 = dark, 18 = fairy
-- category_id:      Category of this move, whether physical or special. None if neither.
-- damage:           The amount of damage inflicted by this move, in game units
-- hit_count:        The (average) hit count of this move
-- hit_ratio:        The ratio of how often this move hits, where 100 is 100% hit without accuracy or evasion mods
-- critical_rate_id: Rate of critical hits applied to this move
-- 		References enumeration CriticalRate
-- 		Possible values are: 1 = normal, 2 = increased, 3 = guaranteed
CREATE TABLE `move`(
	`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	`randomization_id` INT NOT NULL, 
	`index_in_game` SMALLINT(4) NOT NULL, 
	`name` VARCHAR(16), 
	`move_type_id` TINYINT NOT NULL, 
	`category_id` TINYINT, 
	`damage` INT NOT NULL, 
	`hit_count` DOUBLE NOT NULL DEFAULT 1.0, 
	`hit_ratio` DOUBLE NOT NULL DEFAULT 100.0, 
	`critical_rate_id` TINYINT NOT NULL, 
	INDEX m_combo_1_idx (randomization_id, name), 
	CONSTRAINT m_r_randomization_ref_fk FOREIGN KEY m_r_randomization_ref_idx (randomization_id) REFERENCES `randomization`(`id`) ON DELETE CASCADE
)Engine=InnoDB DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

-- Represents a set of (3) starters that appears in a game
-- randomization_id: Id of the randomization where these starters appear
-- placement:        Relative set index within the game, 0-based
CREATE TABLE `starter_set`(
	`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	`randomization_id` INT NOT NULL, 
	`placement` INT NOT NULL, 
	INDEX ss_combo_1_idx (randomization_id, placement), 
	CONSTRAINT ss_r_randomization_ref_fk FOREIGN KEY ss_r_randomization_ref_idx (randomization_id) REFERENCES `randomization`(`id`) ON DELETE CASCADE
)Engine=InnoDB DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;


--	Poke	----------

-- Represents a randomized poke from one of the games
-- randomization_id:  The randomization in which this poke appears
-- number:            The pokedex-number of this poke in the game in which it appears
-- forme_index:       0-based index of the specific poke "forme" represented by this instance. 0 is the default forme.
-- name:              Name of this poke in the english translation / game data
-- primary_type_id:   The primary type of this poke
-- 		References enumeration PokeType
-- 		Possible values are: 1 = normal, 2 = fighting, 3 = flying, 4 = grass, 5 = water, 6 = fire, 7 = rock, 8 = ground, 9 = psychic, 10 = bug, 11 = dragon, 12 = electric, 13 = ghost, 14 = poison, 15 = ice, 16 = steel, 17 = dark, 18 = fairy
-- secondary_type_id: The secondary type of this poke. None if this poke only has one type
CREATE TABLE `poke`(
	`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	`randomization_id` INT NOT NULL, 
	`number` SMALLINT(4) NOT NULL, 
	`forme_index` INT NOT NULL DEFAULT 0, 
	`name` VARCHAR(16) NOT NULL, 
	`primary_type_id` TINYINT NOT NULL, 
	`secondary_type_id` TINYINT, 
	INDEX p_name_idx (`name`), 
	INDEX p_combo_1_idx (randomization_id, name), 
	CONSTRAINT p_r_randomization_ref_fk FOREIGN KEY p_r_randomization_ref_idx (randomization_id) REFERENCES `randomization`(`id`) ON DELETE CASCADE
)Engine=InnoDB DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

-- Represents a permanent change from one poke form to another
-- from_id:         Id of the poke from which this evo originates
-- to_id:           Id of the poke to which this evo leads
-- level_threshold: The level at which this evo is enabled. None if not level-based
-- item_id:         Id of the item associated with this evo
CREATE TABLE `evo`(
	`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	`from_id` INT NOT NULL,
	`to_id` INT NOT NULL,
	`level_threshold` TINYINT(3),
	`item_id` INT,
	CONSTRAINT e_p_from_ref_fk FOREIGN KEY e_p_from_ref_idx (from_id) REFERENCES `poke`(`id`) ON DELETE CASCADE,
	CONSTRAINT e_p_to_ref_fk FOREIGN KEY e_p_to_ref_idx (to_id) REFERENCES `poke`(`id`) ON DELETE CASCADE,
	CONSTRAINT e_i_item_ref_fk FOREIGN KEY e_i_item_ref_idx (item_id) REFERENCES `item`(`id`) ON DELETE SET NULL
)Engine=InnoDB DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

-- Represents a poke's ability to learn a move when evolving
-- poke_id: Id of the poke (form) learning this move
-- move_id: id of the move learnt
CREATE TABLE `evo_move`(
	`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	`poke_id` INT NOT NULL, 
	`move_id` INT NOT NULL, 
	CONSTRAINT em_p_poke_ref_fk FOREIGN KEY em_p_poke_ref_idx (poke_id) REFERENCES `poke`(`id`) ON DELETE CASCADE, 
	CONSTRAINT em_m_move_ref_fk FOREIGN KEY em_m_move_ref_idx (move_id) REFERENCES `move`(`id`) ON DELETE CASCADE
)Engine=InnoDB DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

-- Represents a poke's ability to learn a move
-- poke_id: Id of the poke that learns the move
-- move_id: Id of the move learnt
-- level:   Level at which this move is learnt, if applicable.
CREATE TABLE `move_learn`(
	`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	`poke_id` INT NOT NULL, 
	`move_id` INT NOT NULL, 
	`level` INT NOT NULL, 
	INDEX ml_combo_1_idx (poke_id, level), 
	CONSTRAINT ml_p_poke_ref_fk FOREIGN KEY ml_p_poke_ref_idx (poke_id) REFERENCES `poke`(`id`) ON DELETE CASCADE, 
	CONSTRAINT ml_m_move_ref_fk FOREIGN KEY ml_m_move_ref_idx (move_id) REFERENCES `move`(`id`) ON DELETE CASCADE
)Engine=InnoDB DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

-- Represents an ability that's possible for a poke to have
-- poke_id:    Id of the poke that may have this ability
-- ability_id: Id of the ability this poke may have
-- is_hidden:  True if this is a hidden ability. Hidden abilities may only be acquired with special measures.
CREATE TABLE `poke_ability`(
	`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	`poke_id` INT NOT NULL,
	`ability_id` INT NOT NULL,
	`is_hidden` BOOLEAN NOT NULL DEFAULT FALSE,
	CONSTRAINT pa_p_poke_ref_fk FOREIGN KEY pa_p_poke_ref_idx (poke_id) REFERENCES `poke`(`id`) ON DELETE CASCADE,
	CONSTRAINT pa_a_ability_ref_fk FOREIGN KEY pa_a_ability_ref_idx (ability_id) REFERENCES `ability`(`id`) ON DELETE CASCADE
)Engine=InnoDB DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

-- Represents a (base) stat assigned to a poke
-- poke_id: Id of the described poke
-- stat_id: Described stat / attribute
-- 		References enumeration Stat
-- 		Possible values are: 1 = hp, 2 = attack, 3 = special attack, 4 = defense, 5 = special defense, 6 = speed
-- value:   Assigned value, between 10 and 255
CREATE TABLE `poke_stat`(
	`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	`poke_id` INT NOT NULL,
	`stat_id` TINYINT NOT NULL,
	`value` INT NOT NULL,
	INDEX ps_combo_1_idx (poke_id, stat_id),
	CONSTRAINT ps_p_poke_ref_fk FOREIGN KEY ps_p_poke_ref_idx (poke_id) REFERENCES `poke`(`id`) ON DELETE CASCADE
)Engine=InnoDB DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;


--	Randomization	----------

-- Represents a scripted encounter with a poke in a trainer battle
-- randomization_id:     Id of the randomization where this encounter applies
-- poke_id:              Id of the encountered poke
-- level:                Level at which this poke is encountered
-- number_of_encounters: Number of individual encounters represented by this instance
CREATE TABLE `battle_encounter`(
	`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	`randomization_id` INT NOT NULL, 
	`poke_id` INT NOT NULL, 
	`level` INT NOT NULL, 
	`number_of_encounters` INT NOT NULL DEFAULT 1, 
	INDEX be_combo_1_idx (randomization_id, level), 
	CONSTRAINT be_r_randomization_ref_fk FOREIGN KEY be_r_randomization_ref_idx (randomization_id) REFERENCES `randomization`(`id`) ON DELETE CASCADE, 
	CONSTRAINT be_p_poke_ref_fk FOREIGN KEY be_p_poke_ref_idx (poke_id) REFERENCES `poke`(`id`) ON DELETE CASCADE
)Engine=InnoDB DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

-- Represents a starter poke-assignment in a game
-- set_id:    Id of the starter set to which this starter belongs
-- poke_id:   Id of the poke that appears as a starter
-- placement: A zero-based index that shows where this starter appears relative to the others. The following index is typically strong against the previous index.
CREATE TABLE `starter_assignment`(
	`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	`set_id` INT NOT NULL, 
	`poke_id` INT NOT NULL, 
	`placement` INT NOT NULL, 
	CONSTRAINT sa_ss_set_ref_fk FOREIGN KEY sa_ss_set_ref_idx (set_id) REFERENCES `starter_set`(`id`) ON DELETE CASCADE, 
	CONSTRAINT sa_p_poke_ref_fk FOREIGN KEY sa_p_poke_ref_idx (poke_id) REFERENCES `poke`(`id`) ON DELETE CASCADE
)Engine=InnoDB DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

-- Represents a random encounter with a wild poke
-- randomization_id:     Randomization in which this encounter applies
-- zone_index:           Index of the zone / map in which this encounter applies
-- poke_id:              Id of the poke that may be encountered
-- level range (min_level, max_level): Range of levels that are possible for this encounter
-- number_of_encounters: Number of individual encounters represented by this instance
CREATE TABLE `wild_encounter`(
	`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	`randomization_id` INT NOT NULL, 
	`zone_index` INT NOT NULL, 
	`poke_id` INT NOT NULL, 
	`min_level` INT NOT NULL, 
	`max_level` INT NOT NULL, 
	`number_of_encounters` INT NOT NULL DEFAULT 1, 
	INDEX we_combo_1_idx (randomization_id, min_level), 
	CONSTRAINT we_r_randomization_ref_fk FOREIGN KEY we_r_randomization_ref_idx (randomization_id) REFERENCES `randomization`(`id`) ON DELETE CASCADE, 
	CONSTRAINT we_p_poke_ref_fk FOREIGN KEY we_p_poke_ref_idx (poke_id) REFERENCES `poke`(`id`) ON DELETE CASCADE
)Engine=InnoDB DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

