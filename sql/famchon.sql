-- phpMyAdmin SQL Dump
-- version 4.2.12deb2+deb8u1
-- http://www.phpmyadmin.net
--
-- Client :  localhost
-- Généré le :  Jeu 20 Octobre 2016 à 09:21
-- Version du serveur :  5.5.49-0+deb8u1
-- Version de PHP :  5.6.23-0+deb8u1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données :  `famchon`
--

-- --------------------------------------------------------

--
-- Structure de la table `bureau`
--

CREATE TABLE IF NOT EXISTS `bureau` (
  `id_bureau` int(11) NOT NULL,
  `description` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Contenu de la table `bureau`
--

INSERT INTO `bureau` (`id_bureau`, `description`) VALUES
(0, 'bureau0'),
(1, 'bureau1'),
(2, 'bureau2'),
(3, 'bureau3');

-- --------------------------------------------------------

--
-- Structure de la table `personne`
--

CREATE TABLE IF NOT EXISTS `personne` (
  `id_personne` int(11) NOT NULL,
  `nom` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `telephone` varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL,
  `domaine` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `qualification` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `formation` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `typePersonne` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `id_bureau` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Contenu de la table `personne`
--

INSERT INTO `personne` (`id_personne`, `nom`, `telephone`, `domaine`, `qualification`, `formation`, `typePersonne`, `id_bureau`) VALUES
(0, 'Blue', '0612131415', 'Arts-plastiques', NULL, NULL, 'Chercheur', 0),
(1, 'Yellow', '0616171819', 'Arts-plastiques', NULL, NULL, 'Chercheur', 0),
(2, 'Brown', '0620212223', 'Arts-plastiques', NULL, NULL, 'Chercheur', 0),
(3, 'Romarin', '0612345678', 'Agro Alimentaire', NULL, NULL, 'Chercheur', 1),
(4, 'Thym', '0609101112', 'Agro Alimentaire', NULL, NULL, 'Chercheur', 1),
(5, 'Paprika', '0613141516', 'Agro Alimentaire', NULL, NULL, 'Chercheur', 1),
(6, 'Strap', '0601010101', 'Informatique', NULL, NULL, 'Chercheur', 2),
(7, 'Script', '0610101010', 'Informatique', NULL, NULL, 'Chercheur', 2),
(8, 'Time', '0698876543', NULL, 'Secretaire', 'M1MIAGE', 'Administratif', 3),
(9, 'Paper', '0615487635', NULL, 'Assistante', 'M1INFO', 'Administratif', 3),
(10, 'Alone', NULL, NULL, 'Orientation', 'L3SPH', 'Administratif', NULL);

--
-- Index pour les tables exportées
--

--
-- Index pour la table `bureau`
--
ALTER TABLE `bureau`
 ADD PRIMARY KEY (`id_bureau`);

--
-- Index pour la table `personne`
--
ALTER TABLE `personne`
 ADD PRIMARY KEY (`id_personne`), ADD KEY `fk_pers_bureau` (`id_bureau`);

--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `personne`
--
ALTER TABLE `personne`
ADD CONSTRAINT `fk_pers_bureau` FOREIGN KEY (`id_bureau`) REFERENCES `bureau` (`id_bureau`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
