<?php
require "auth.php";

// Returns every user's answers (ids and option indexes only) so the app can
// cluster users. Requires a valid token.
header('Content-Type: application/json');
list($db, $userId) = requireAuthenticatedUser();
echo json_encode($db->clusterCalc());
?>
