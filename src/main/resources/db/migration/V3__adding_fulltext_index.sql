ALTER TABLE `blogengine`.`posts`
ADD FULLTEXT INDEX `fulltext_search_index` (`title`, `text`) VISIBLE;