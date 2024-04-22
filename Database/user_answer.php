<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['userId']) && isset($_POST['questionId']) && isset($_POST['optionId'])) {
    if ($db->dbConnect()) {
        if ($db->insertAns("answers", $_POST['userId'], $_POST['questionId'], $_POST['optionId'])) {
            echo "Insert Success";
        } else {
            echo "Insert Failed";
        }
    } else {
        echo "Error: Database connection";
    }
} else {
    echo "All fields are required";
}
?>