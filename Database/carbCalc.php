<?php
require "auth.php";

// Returns the authenticated user's footprint-relevant answers.
header('Content-Type: application/json');
list($db, $userId) = requireAuthenticatedUser();
echo json_encode($db->carbCalc($userId));
?>
