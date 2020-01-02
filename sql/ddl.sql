
CREATE TABLE `portfolio` (
  `no` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(150) COLLATE utf8_bin NOT NULL,
  `leader` varchar(30) COLLATE utf8_bin NOT NULL,
  `members` varchar(100) COLLATE utf8_bin NOT NULL,
  `content` varchar(500) COLLATE utf8_bin NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `reg_date` date NOT NULL,
  PRIMARY KEY (`no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `portfolio_data` (
  `no` int(11) NOT NULL AUTO_INCREMENT,
  `original_file_name` varchar(100) COLLATE utf8_bin NOT NULL,
  `real_file_name` varchar(100) COLLATE utf8_bin NOT NULL,
  `portfolio_no` int(11) NOT NULL,
  PRIMARY KEY (`no`),
  KEY `fk_portfolio_no_idx` (`portfolio_no`),
  CONSTRAINT `fk_portfolio_no` FOREIGN KEY (`portfolio_no`) REFERENCES `portfolio` (`no`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;