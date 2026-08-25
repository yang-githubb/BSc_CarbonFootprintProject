<?php
require "DataBase.php";

header('Content-Type: application/json');
$db = new DataBase();
if (!isset($_POST['username']) || !isset($_POST['password'])) {
    echo json_encode(["status" => "error", "message" => "All fields are required"]);
    exit;
}
if (!$db->dbConnect()) {
    echo json_encode(["status" => "error", "message" => "Error: Database connection"]);
    exit;
}

$result = $db->logIn($_POST['username'], $_POST['password']);
if ($result === null) {
    echo json_encode(["status" => "error", "message" => "Username or Password wrong"]);
} else {
    echo json_encode([
        "status" => "success",
        "userId" => $result['userId'],
        "token" => $result['token'],
        "needsSurvey" => !$result['hasAnswers'],
    ]);
}
?>
