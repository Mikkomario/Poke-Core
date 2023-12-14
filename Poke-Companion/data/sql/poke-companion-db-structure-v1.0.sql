-- 
-- Database structure for poke companion models
-- Version: v1.0
-- Last generated: 2023-12-13
--

CREATE DATABASE IF NOT EXISTS `poke_db` 
	DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;
USE `poke_db`;

--	Gameplay	----------

-- Represents a gameplay run of a randomized game version
-- name:             Name of this run, for example consisting of the player name
-- randomization_id: Id of the randomization that's applied to this game run
-- created:          Time when this run was added to the database
CREATE TABLE `run`(
	`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	`name` VARCHAR(16), 
	`randomization_id` INT NOT NULL, 
	`created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, 
	INDEX ru_name_idx (`name`), 
	INDEX ru_created_idx (`created`), 
	CONSTRAINT ru_ra_randomization_ref_fk FOREIGN KEY ru_ra_randomization_ref_idx (randomization_id) REFERENCES `randomization`(`id`) ON DELETE CASCADE
)Engine=InnoDB DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

-- Represents an attempt to attack an opposing poke with some damage type. Used for revealing information about the opponent's typing.
-- run_id:         Id of the game run on which this attempt was made
-- opponent_id:    Id of the opposing poke
-- attack_type_id: Type of attack used against the poke
-- 		References enumeration PokeType
-- 		Possible values are: 
-- created:        Time when this attack experiment was added to the database
CREATE TABLE `attack_experiment`(
	`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	`run_id` INT NOT NULL, 
	`opponent_id` INT NOT NULL, 
	`attack_type_id` TINYINT NOT NULL, 
	`created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, 
	INDEX ae_combo_1_idx (opponent_id, attack_type_id), 
	CONSTRAINT ae_ru_run_ref_fk FOREIGN KEY ae_ru_run_ref_idx (run_id) REFERENCES `run`(`id`) ON DELETE CASCADE, 
	CONSTRAINT ae_p_opponent_ref_fk FOREIGN KEY ae_p_opponent_ref_idx (opponent_id) REFERENCES `poke`(`id`) ON DELETE CASCADE
)Engine=InnoDB DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

-- Represents the event of capturing some poke
-- run_id:  Id of the game run on which this capture was made
-- poke_id: Id of the captured poke
-- level:   Level at which this capture was made
-- created: Time when this poke capture was added to the database
CREATE TABLE `poke_capture`(
	`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	`run_id` INT NOT NULL, 
	`poke_id` INT NOT NULL, 
	`level` TINYINT(3) NOT NULL, 
	`created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, 
	CONSTRAINT pc_ru_run_ref_fk FOREIGN KEY pc_ru_run_ref_idx (run_id) REFERENCES `run`(`id`) ON DELETE CASCADE, 
	CONSTRAINT pc_p_poke_ref_fk FOREIGN KEY pc_p_poke_ref_idx (poke_id) REFERENCES `poke`(`id`) ON DELETE CASCADE
)Engine=InnoDB DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

-- Represents an event / recording of a trained poke reaching a specific level
-- run_id:           Id of the run on which this event occurred
-- poke_id:          Id of the trained poke
-- level:            Reached level
-- created:          Time when this poke training was added to the database
-- deprecated_after: Time when this event was replaced with a more recent recording
CREATE TABLE `poke_training`(
	`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	`run_id` INT NOT NULL, 
	`poke_id` INT NOT NULL, 
	`level` TINYINT(3) NOT NULL, 
	`created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, 
	`deprecated_after` DATETIME, 
	INDEX pt_deprecated_after_idx (`deprecated_after`), 
	CONSTRAINT pt_ru_run_ref_fk FOREIGN KEY pt_ru_run_ref_idx (run_id) REFERENCES `run`(`id`) ON DELETE CASCADE, 
	CONSTRAINT pt_p_poke_ref_fk FOREIGN KEY pt_p_poke_ref_idx (poke_id) REFERENCES `poke`(`id`) ON DELETE CASCADE
)Engine=InnoDB DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

-- Represents the user's guess at a poke's type
-- run_id:           Id of the game run on which this guess was made
-- poke_id:          Id of the described poke
-- guessed_type_id:  Poke type of this type guess
-- 		References enumeration PokeType
-- 		Possible values are: 
-- created:          Time when this type guess was added to the database
-- deprecated_after: Time when this guess was replaced or when it became invalid
CREATE TABLE `type_guess`(
	`id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT, 
	`run_id` INT NOT NULL, 
	`poke_id` INT NOT NULL, 
	`guessed_type_id` TINYINT NOT NULL, 
	`created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, 
	`deprecated_after` DATETIME, 
	INDEX tg_deprecated_after_idx (`deprecated_after`), 
	CONSTRAINT tg_ru_run_ref_fk FOREIGN KEY tg_ru_run_ref_idx (run_id) REFERENCES `run`(`id`) ON DELETE CASCADE, 
	CONSTRAINT tg_p_poke_ref_fk FOREIGN KEY tg_p_poke_ref_idx (poke_id) REFERENCES `poke`(`id`) ON DELETE CASCADE
)Engine=InnoDB DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

