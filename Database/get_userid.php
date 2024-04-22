<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_GET['username']) && $db->dbConnect()) {
    $username = $_GET['username'];
    $userId = $db->getUserIdByUsername($username); 
    echo json_encode(['userId' => $userId]);
} else {
    echo json_encode(["error" => "Error: Database connection or invalid username"]);
}