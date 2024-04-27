<?php
require "DataBase.php";
$db = new DataBase();
if ($db->dbConnect()) {
    if (isset($_GET['userQues'])) {
        $result = $db->getAction("actions");
        if ($result) {
            $userQuestions = $_GET['userQues'];
            $json_data = json_encode($result);

            // Temp file paths
            $userQuestionsFile = 'temp_user_questions.json';
            $jsonDataFile = 'temp_json_data.json';

            file_put_contents($userQuestionsFile, $userQuestions);
            file_put_contents($jsonDataFile, $json_data);

            $python_script_path = escapeshellarg("C:/xampp/htdocs/CarbonFootprintFYP/clusterAction.py");
            $command = "python $python_script_path $userQuestionsFile $jsonDataFile";
            exec($command, $output, $return_var);

            if ($return_var === 0) {
                echo implode("\n", $output);
            } else {
                echo "Error executing Python script";
            }
        } else {
            echo "No data found";
        }
    } else {
        echo "User questions not set";
    }
} else {
    echo "Failed to connect to database";
}
?>
