-- Adds the API token column used by the token-based authentication.
-- Run this against an existing `carbonfootprint` database; fresh installs
-- created from finalDB_carbonfootprint.sql already include it.

ALTER TABLE `users`
  ADD COLUMN `api_token` varchar(64) DEFAULT NULL,
  ADD UNIQUE KEY `api_token` (`api_token`);
