<?php
require "DataBase.php";
$db = new DataBase();
if ($db->dbConnect()) {
    $result = $db->clusterCalc("answers");
    echo json_encode($result);
} else {
    echo json_encode(["error" => "Error: Database connection or invalid userId"]);
}
?>