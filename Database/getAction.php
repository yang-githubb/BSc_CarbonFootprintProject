<?php
require "auth.php";

// Runs the Python recommender for the flagged questions passed in "userQues"
// (a JSON object of question text => category) and relays its JSON output.
header('Content-Type: application/json');
list($db, $userId) = requireAuthenticatedUser();

if (!isset($_GET['userQues'])) {
    echo json_encode(["error" => "User questions not set"]);
    exit;
}

$userQuestions = json_decode($_GET['userQues'], true);
if (!is_array($userQuestions)) {
    echo json_encode(["error" => "userQues must be a JSON object"]);
    exit;
}

$actions = $db->getAction();
if (!$actions) {
    echo json_encode(["error" => "No data found"]);
    exit;
}

$userQuestionsFile = tempnam(sys_get_temp_dir(), 'user_questions_');
$jsonDataFile = tempnam(sys_get_temp_dir(), 'actions_');
file_put_contents($userQuestionsFile, json_encode($userQuestions));
file_put_contents($jsonDataFile, json_encode($actions));

$command = "python " . escapeshellarg(__DIR__ . "/clusterAction.py")
    . " " . escapeshellarg($userQuestionsFile)
    . " " . escapeshellarg($jsonDataFile);
exec($command, $output, $return_var);

unlink($userQuestionsFile);
unlink($jsonDataFile);

if ($return_var === 0) {
    echo implode("\n", $output);
} else {
    echo json_encode(["error" => "Error executing Python script"]);
}
?>
