<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_GET['userId']) && $db->dbConnect()) {
    $userId = intval($_GET['userId']); 
    $result = $db->carbCalc("answers", $userId);
    echo json_encode($result);
} else {
    echo json_encode(["error" => "Error: Database connection or invalid userId"]);
}
?>