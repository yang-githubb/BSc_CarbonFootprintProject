<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['userId']) && isset($_POST['questionId']) && isset($_POST['optionId'])) {
    if ($db->dbConnect()) {
        if ($db->updateAns("answers", $_POST['userId'], $_POST['questionId'], $_POST['optionId'])) {
            echo "Update Success";
        } else {
            echo "Update Failed";
        }
    } else {
        echo "Error: Database connection";
    }
} else {
    echo "All fields are required";
}
?>