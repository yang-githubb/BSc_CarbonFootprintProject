<?php
require_once "DataBase.php";

/**
 * Connects to the database and resolves the request's API token (GET or
 * POST "token") to a user id. Responds with an error and exits when the
 * connection fails or the token is missing/invalid.
 * Returns [DataBase, int userId].
 */
function requireAuthenticatedUser()
{
    $db = new DataBase();
    if (!$db->dbConnect()) {
        http_response_code(500);
        echo json_encode(["error" => "Error: Database connection"]);
        exit;
    }

    $token = $_POST['token'] ?? $_GET['token'] ?? '';
    $userId = $db->getUserIdByToken($token);
    if ($userId === null) {
        http_response_code(401);
        echo json_encode(["error" => "Unauthorized"]);
        exit;
    }

    return [$db, $userId];
}
?>
